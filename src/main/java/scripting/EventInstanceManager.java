package scripting;

import tools.packet.UIPacket;
import server.MapleItemInformationProvider;
import server.MapleCarnivalParty;
import server.maps.MapleMapFactory;
import handling.world.CharacterTransfer;
import handling.cashshop.CashShopServer;
import handling.world.World.Find;
import java.util.Collections;
import client.MapleQuestStatus;
import server.quest.MapleQuest;
import java.util.Collection;
import server.MapleSquad;
import handling.channel.ChannelServer;
import handling.world.MaplePartyCharacter;
import server.maps.MapleMap;
import handling.world.MapleParty;
import java.util.Iterator;
import tools.MaplePacketCreator;
import server.Timer.EventTimer;
import javax.script.ScriptException;
import java.util.concurrent.RejectedExecutionException;
import tools.FilePrinter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.ScheduledFuture;
import java.util.Properties;
import java.util.Map;
import server.life.MapleMonster;
import client.MapleCharacter;
import java.util.List;

public class EventInstanceManager{
    private List<MapleCharacter> chars = new LinkedList<>();
    private List<Integer> dced = new LinkedList<>();
    private List<MapleMonster> mobs = new LinkedList<>();
    private Map<Integer, Integer> killCount = new HashMap<>();
    private final Map<String, String> infos = new HashMap();
    private final EventManager em;
    private final int channel;
    private final String name;
    private Properties props = new Properties();
    private long timeStarted = 0L;
    private long eventTime = 0L;
    private List<Integer> mapIds = new LinkedList<>();
    private List<Boolean> isInstanced = new LinkedList<>();
    private ScheduledFuture<?> eventTimer;
    private final ReentrantReadWriteLock mutex = new ReentrantReadWriteLock();
    private final Lock rL = mutex.readLock(), wL = mutex.writeLock();
    private boolean disposed = false;
    
    public EventInstanceManager(final EventManager em, final String name, final int channel) {
        this.em = em;
        this.name = name;
        this.channel = channel;
    }
    
    public void registerPlayer(final MapleCharacter chr) {
        if (this.disposed || chr == null) {
            return;
        }
        try {
            this.wL.lock();
            try {
                this.chars.add(chr);
            }
            finally {
                this.wL.unlock();
            }
            chr.setEventInstance(this);
            this.em.getIv().invokeFunction("playerEntry", this, chr);
        }
        catch (NullPointerException ex) {
            FilePrinter.printError("EventInstanceManager.txt", (Throwable)ex);
        }
        catch (RejectedExecutionException ex4) {}
        catch (ScriptException | NoSuchMethodException ex5) {
            FilePrinter.printError("EventInstanceManager.txt", "Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : playerEntry:\n" + ex5);
            System.err.println("Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : playerEntry:\n" + ex5);
        }
    }
    
    public void changedMap(final MapleCharacter chr, final int mapid) {
        if (this.disposed) {
            return;
        }
        try {
            this.em.getIv().invokeFunction("changedMap", this, chr, Integer.valueOf(mapid));
        }
        catch (NullPointerException ex2) {}
        catch (Exception ex) {
            FilePrinter.printError("EventInstanceManager.txt", "Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : changedMap:\n" + ex);
            System.err.println("Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : changedMap:\n" + ex);
        }
    }
    
    public void timeOut(final long delay, final EventInstanceManager eim) {
        if (this.disposed || eim == null) {
            return;
        }
        this.eventTimer = EventTimer.getInstance().schedule((Runnable)new Runnable() {
            @Override
            public void run() {
                if (EventInstanceManager.this.disposed || eim == null || EventInstanceManager.this.em == null) {
                    return;
                }
                try {
                    EventInstanceManager.this.em.getIv().invokeFunction("scheduledTimeout", eim);
                }
                catch (Exception ex) {
                    FilePrinter.printError("EventInstanceManager.txt", "Event name" + EventInstanceManager.this.em.getName() + ", Instance name : " + EventInstanceManager.this.name + ", method Name : scheduledTimeout:\n" + ex);
                    System.err.println("Event name" + EventInstanceManager.this.em.getName() + ", Instance name : " + EventInstanceManager.this.name + ", method Name : scheduledTimeout:\n" + ex);
                }
            }
        }, delay);
    }
    
    public void stopEventTimer() {
        this.eventTime = 0L;
        this.timeStarted = 0L;
        if (this.eventTimer != null) {
            this.eventTimer.cancel(false);
        }
    }
    
    public void restartEventTimer(final long time) {
        try {
            if (this.disposed) {
                return;
            }
            this.timeStarted = System.currentTimeMillis();
            this.eventTime = time;
            if (this.eventTimer != null) {
                this.eventTimer.cancel(false);
            }
            this.eventTimer = null;
            final int timesend = (int)time / 1000;
            for (final MapleCharacter chr : this.getPlayers()) {
                chr.getClient().sendPacket(MaplePacketCreator.getClock(timesend));
            }
            this.timeOut(time, this);
        }
        catch (Exception ex) {
            FilePrinter.printError("EventInstanceManager.txt", "Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : restartEventTimer:\n" + ex);
            System.err.println("Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : restartEventTimer:\n");
            ex.printStackTrace();
        }
    }
    
    public void startEventTimer(final long time) {
        this.restartEventTimer(time);
    }
    
    public boolean isTimerStarted() {
        return this.eventTime > 0L && this.timeStarted > 0L;
    }
    
    public long getTimeLeft() {
        return this.eventTime - (System.currentTimeMillis() - this.timeStarted);
    }
    
    public void registerParty(final MapleParty party, final MapleMap map) {
        if (this.disposed) {
            return;
        }
        for (final MaplePartyCharacter pc : party.getMembers()) {
            final MapleCharacter c = map.getCharacterById(pc.getId());
            this.registerPlayer(c);
        }
    }
    
    public void unregisterPlayer(final MapleCharacter chr) {
        if (this.disposed) {
            chr.setEventInstance(null);
            return;
        }
        this.wL.lock();
        try {
            this.unregisterPlayer_NoLock(chr);
        }
        finally {
            this.wL.unlock();
        }
    }
    
    private boolean unregisterPlayer_NoLock(final MapleCharacter chr) {
        if (this.name.equals("CWKPQ")) {
            final MapleSquad squad = ChannelServer.getInstance(this.channel).getMapleSquad("CWKPQ");
            if (squad != null) {
                squad.removeMember(chr.getName());
                if (squad.getLeaderName().equals(chr.getName())) {
                    this.em.setProperty("leader", "false");
                }
            }
        }
        chr.setEventInstance(null);
        if (this.disposed) {
            return false;
        }
        if (this.chars.contains(chr)) {
            this.chars.remove(chr);
            return true;
        }
        return false;
    }
    
    public boolean check() {
        for (final MapleCharacter chr : this.getPlayers()) {
            if (chr.getLevel() < 30 || chr.getLevel() > 50) {
                return false;
            }
        }
        return true;
    }
    
    public boolean check1() {
        for (final MapleCharacter chr : this.getPlayers()) {
            if (chr.getLevel() < 51 || chr.getLevel() > 120) {
                return false;
            }
        }
        return true;
    }
    
    public final boolean disposeIfPlayerBelow(final byte size, final int towarp) {
        if (this.disposed) {
            return true;
        }
        MapleMap map = null;
        if (towarp > 0) {
            map = this.getMapFactory().getMap(towarp);
        }
        this.wL.lock();
        try {
            if (this.chars.size() <= size) {
                final List<MapleCharacter> chrs = new LinkedList(this.chars);
                for (final MapleCharacter chr : chrs) {
                    this.unregisterPlayer_NoLock(chr);
                    if (towarp > 0) {
                        chr.changeMap(map, map.getPortal(0));
                    }
                }
                this.dispose_NoLock();
                return true;
            }
        }
        finally {
            this.wL.unlock();
        }
        return false;
    }
    
    public final void saveBossQuest(final int points) {
        if (this.disposed) {
            return;
        }
        for (final MapleCharacter chr : this.getPlayers()) {
            final MapleQuestStatus record = chr.getQuestNAdd(MapleQuest.getInstance(150001));
            if (record.getCustomData() != null) {
                record.setCustomData(String.valueOf(points + Integer.parseInt(record.getCustomData())));
            }
            else {
                record.setCustomData(String.valueOf(points));
            }
        }
    }
    
    public final void saveNX(final int points) {
        if (this.disposed) {
            return;
        }
        for (final MapleCharacter chr : this.getPlayers()) {
            chr.modifyCSPoints(1, points, true);
        }
    }
    
    public List<MapleCharacter> getPlayers() {
        if (this.disposed) {
            return Collections.emptyList();
        }
        this.rL.lock();
        try {
            return new LinkedList(this.chars);
        }
        finally {
            this.rL.unlock();
        }
    }
    
    public List<Integer> getDisconnected() {
        return this.dced;
    }
    
    public final int getPlayerCount() {
        if (this.disposed) {
            return 0;
        }
        return this.chars.size();
    }
    
    public void registerMonster(final MapleMonster mob) {
        if (this.disposed) {
            return;
        }
        this.mobs.add(mob);
        mob.setEventInstance(this);
    }
    
    public void unregisterMonster(final MapleMonster mob) {
        mob.setEventInstance(null);
        if (this.disposed) {
            return;
        }
        this.mobs.remove(mob);
        if (this.mobs.isEmpty()) {
            try {
                this.em.getIv().invokeFunction("allMonstersDead", this);
            }
            catch (RejectedExecutionException ex3) {}
            catch (ScriptException | NoSuchMethodException ex4) {
                FilePrinter.printError("EventInstanceManager.txt", "Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : allMonstersDead:\n" + ex4);
                System.err.println("Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : allMonstersDead:\n" + ex4);
            }
        }
    }
    
    public void playerKilled(final MapleCharacter chr) {
        if (this.disposed) {
            return;
        }
        try {
            this.em.getIv().invokeFunction("playerDead", this, chr);
        }
        catch (RejectedExecutionException ex3) {}
        catch (ScriptException | NoSuchMethodException ex4) {
            FilePrinter.printError("EventInstanceManager.txt", "Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : playerDead:\n" + ex4);
            System.err.println("Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : playerDead:\n" + ex4);
        }
    }
    
    public boolean revivePlayer(final MapleCharacter chr) {
        if (this.disposed) {
            return false;
        }
        try {
            final Object b = this.em.getIv().invokeFunction("playerRevive", this, chr);
            if (b instanceof Boolean) {
                return ((Boolean)b).booleanValue();
            }
        }
        catch (RejectedExecutionException ex3) {}
        catch (ScriptException | NoSuchMethodException ex4) {
            FilePrinter.printError("EventInstanceManager.txt", "Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : playerRevive:\n" + ex4);
            System.err.println("Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : playerRevive:\n" + ex4);
        }
        return true;
    }
    
    public void playerDisconnected(final MapleCharacter chr, final int idz) {
        if (this.disposed) {
            return;
        }
        this.kill(chr);
        byte ret;
        try {
            ret = ((Double)this.em.getIv().invokeFunction("playerDisconnected", this, chr)).byteValue();
        }
        catch (Exception e) {
            ret = 0;
        }
        this.wL.lock();
        try {
            if (this.disposed) {
                return;
            }
            if (chr == null || chr.isAlive()) {
                this.dced.add(Integer.valueOf(idz));
            }
            if (chr != null) {
                this.unregisterPlayer_NoLock(chr);
            }
            if (ret == 0) {
                if (this.getPlayerCount() <= 0) {
                    this.dispose_NoLock();
                }
            }
            else if ((ret > 0 && this.getPlayerCount() < ret) || (ret < 0 && (this.isLeader(chr) || this.getPlayerCount() < ret * -1))) {
                final List<MapleCharacter> chrs = new LinkedList(this.chars);
                for (final MapleCharacter player : chrs) {
                    if (player.getId() != idz) {
                        this.removePlayer(player);
                    }
                }
                this.dispose_NoLock();
            }
            chr.getClient().getChannelServer().getPlayerStorage().deregisterPlayer(chr.getId(), chr.getName());
            chr.getClient().getChannelServer().getPlayerStorage().deregisterPendingPlayer(chr.getId());
            Find.forceDeregister(chr.getId(), chr.getName());
        }
        catch (Exception ex) {
            FilePrinter.printError("EventInstanceManager.txt", (Throwable)ex);
        }
        finally {
            this.wL.unlock();
        }
    }
    
    private void kill(final MapleCharacter chr) {
        this.unregisterPlayer(chr);
        chr.saveToDB(false, false);
        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
            final CharacterTransfer transfer2 = cserv.getPlayerStorage().getPendingCharacter(chr.getId());
            final CharacterTransfer transfer3 = CashShopServer.getPlayerStorage().getPendingCharacter(chr.getId());
            if (transfer2 != null) {
                cserv.getPlayerStorage().deregisterPlayer(chr.getId(), chr.getName());
                cserv.getPlayerStorage().deregisterPendingPlayer(chr.getId());
            }
            if (transfer3 != null) {
                CashShopServer.getPlayerStorage().deregisterPlayer(chr.getId(), chr.getName());
                CashShopServer.getPlayerStorage().deregisterPendingPlayer(chr.getId());
            }
        }
        chr.getClient().disconnect(true, true);
        chr.getMap().removePlayer(chr);
        this.removePlayer(chr);
        Find.forceDeregister(chr.getId(), chr.getName());
        ChannelServer.forceRemovePlayerByAccId(chr.getClient(), chr.getAccountID());
    }
    
    public void monsterKilled(final MapleCharacter chr, final MapleMonster mob) {
        if (this.disposed) {
            return;
        }
        try {
            try {
                Integer kc = (Integer)this.killCount.get(Integer.valueOf(chr.getId()));
                final int inc = ((Integer)this.em.getIv().invokeFunction("monsterValue", this, Integer.valueOf(mob.getId()))).intValue();
                if (this.disposed) {
                    return;
                }
                if (kc == null) {
                    kc = Integer.valueOf(inc);
                }
                else {
                    kc = Integer.valueOf(kc.intValue() + inc);
                }
                this.killCount.put(Integer.valueOf(chr.getId()), kc);
                if (chr.getCarnivalParty() != null && (mob.getStats().getPoint() > 0 || mob.getStats().getCP() > 0)) {
                    this.em.getIv().invokeFunction("monsterKilled", this, chr, Integer.valueOf((mob.getStats().getCP() > 0) ? mob.getStats().getCP() : mob.getStats().getPoint()));
                }
                return;
            }
            catch (RejectedExecutionException ex2) {}
            catch (NoSuchMethodException ex) {
                System.err.println("Event name" + ((this.em == null) ? "null" : this.em.getName()) + ", Instance name : " + this.name + ", method Name : monsterValue:\n" + ex);
                FilePrinter.printError("EventInstanceManager.txt", "Event name" + ((this.em == null) ? "null" : this.em.getName()) + ", Instance name : " + this.name + ", method Name : monsterValue:\n" + ex);
            }
        }
        catch (ScriptException ex3) {}
    }
    
    public void monsterDamaged(final MapleCharacter chr, final MapleMonster mob, final int damage) {
        if (this.disposed) {
            return;
        }
        try {
            this.em.getIv().invokeFunction("monsterDamaged", this, chr, Integer.valueOf(mob.getId()), Integer.valueOf(damage));
        }
        catch (RejectedExecutionException ex4) {}
        catch (ScriptException ex) {
            System.err.println("Event name" + ((this.em == null) ? "null" : this.em.getName()) + ", Instance name : " + this.name + ", method Name : monsterValue:\n" + ex);
            FilePrinter.printError("EventInstanceManager.txt", "Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : restartEventTimer:\n" + ex);
        }
        catch (NoSuchMethodException ex2) {
            System.err.println("Event name" + ((this.em == null) ? "null" : this.em.getName()) + ", Instance name : " + this.name + ", method Name : monsterValue:\n" + ex2);
            FilePrinter.printError("EventInstanceManager.txt", "Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : restartEventTimer:\n" + ex2);
        }
        catch (Exception ex3) {
            ex3.printStackTrace();
            FilePrinter.printError("EventInstanceManager.txt", "Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : restartEventTimer:\n" + ex3);
        }
    }
    
    public int getKillCount(final MapleCharacter chr) {
        if (this.disposed) {
            return 0;
        }
        final Integer kc = (Integer)this.killCount.get(Integer.valueOf(chr.getId()));
        if (kc == null) {
            return 0;
        }
        return kc.intValue();
    }
    
    public void dispose_NoLock() {
        if (this.disposed || this.em == null) {
            return;
        }
        final String emN = this.em.getName();
        try {
            this.disposed = true;
            for (final MapleCharacter chr : this.chars) {
                chr.setEventInstance(null);
            }
            this.chars.clear();
            this.chars = null;
            if (this.mobs.size() >= 1) {
                for (final MapleMonster mob : this.mobs) {
                    mob.setEventInstance(null);
                }
            }
            this.mobs.clear();
            this.mobs = null;
            this.killCount.clear();
            this.killCount = null;
            this.dced.clear();
            this.infos.clear();
            this.dced = null;
            this.timeStarted = 0L;
            this.eventTime = 0L;
            this.props.clear();
            this.props = null;
            for (int i = 0; i < this.mapIds.size(); ++i) {
                if (((Boolean)this.isInstanced.get(i)).booleanValue()) {
                    this.getMapFactory().removeInstanceMap(((Integer)this.mapIds.get(i)).intValue());
                }
            }
            this.mapIds.clear();
            this.mapIds = null;
            this.isInstanced.clear();
            this.isInstanced = null;
            this.em.disposeInstance(this.name);
        }
        catch (Exception e) {
            System.err.println("Caused by : " + emN + " instance name: " + this.name + " method: dispose: " + e);
            FilePrinter.printError("EventInstanceManager.txt", "Caused by : " + emN + " instance name: " + this.name + " method: dispose: " + e);
        }
    }
    
    public void dispose() {
        this.wL.lock();
        try {
            this.dispose_NoLock();
        }
        finally {
            this.wL.unlock();
        }
    }
    
    public ChannelServer getChannelServer() {
        return ChannelServer.getInstance(this.channel);
    }
    
    public List<MapleMonster> getMobs() {
        return this.mobs;
    }
    
    public final void broadcastPlayerMsg(final int type, final String msg) {
        if (this.disposed) {
            return;
        }
        for (final MapleCharacter chr : this.getPlayers()) {
            chr.getClient().sendPacket(MaplePacketCreator.serverNotice(type, msg));
        }
    }
    
    public final MapleMap createInstanceMap(final int mapid) {
        if (this.disposed) {
            return null;
        }
        final int assignedid = this.getChannelServer().getEventSM().getNewInstanceMapId();
        this.mapIds.add(Integer.valueOf(assignedid));
        this.isInstanced.add(Boolean.valueOf(true));
        return this.getMapFactory().CreateInstanceMap(mapid, true, true, true, assignedid);
    }
    
    public final MapleMap createInstanceMapS(final int mapid) {
        if (this.disposed) {
            return null;
        }
        final int assignedid = this.getChannelServer().getEventSM().getNewInstanceMapId();
        this.mapIds.add(Integer.valueOf(assignedid));
        this.isInstanced.add(Boolean.valueOf(true));
        return this.getMapFactory().CreateInstanceMap(mapid, false, false, false, assignedid);
    }
    
    public final MapleMap setInstanceMap(final int mapid) {
        if (this.disposed) {
            return this.getMapFactory().getMap(mapid);
        }
        this.mapIds.add(Integer.valueOf(mapid));
        this.isInstanced.add(Boolean.valueOf(false));
        return this.getMapFactory().getMap(mapid);
    }
    
    public final MapleMapFactory getMapFactory() {
        return this.getChannelServer().getMapFactory();
    }
    
    public MapleMap getMapFactoryMap(final int mapid) {
        return this.getMapFactory().getMap(mapid);
    }
    
    public final MapleMap getMapInstance(final int args) {
        if (this.disposed) {
            return null;
        }
        try {
            boolean instanced = false;
            int trueMapID;
            if (args >= this.mapIds.size()) {
                trueMapID = args;
            }
            else {
                trueMapID = ((Integer)this.mapIds.get(args)).intValue();
                instanced = ((Boolean)this.isInstanced.get(args)).booleanValue();
            }
            MapleMap map;
            if (!instanced) {
                map = this.getMapFactory().getMap(trueMapID);
                if (map == null) {
                    return null;
                }
                if (map.getCharactersSize() == 0 && this.em.getProperty("shuffleReactors") != null && this.em.getProperty("shuffleReactors").equals("true")) {
                    map.shuffleReactors();
                }
            }
            else {
                map = this.getMapFactory().getInstanceMap(trueMapID);
                if (map == null) {
                    return null;
                }
                if (map.getCharactersSize() == 0 && this.em.getProperty("shuffleReactors") != null && this.em.getProperty("shuffleReactors").equals("true")) {
                    map.shuffleReactors();
                }
            }
            return map;
        }
        catch (NullPointerException ex) {
            FilePrinter.printError("EventInstanceManager.txt", (Throwable)ex);
            return null;
        }
    }
    
    public Map<String, String> getInfoStats() {
        return this.infos;
    }
    
    public void setInfoStats(final Map<String, String> infos) {
        this.infos.clear();
        this.infos.putAll(infos);
    }
    
    public final void schedule(final String methodName, final long delay) {
        if (this.disposed) {
            return;
        }
        EventTimer.getInstance().schedule((Runnable)new Runnable() {
            @Override
            public void run() {
                if (EventInstanceManager.this.disposed || EventInstanceManager.this == null || EventInstanceManager.this.em == null) {
                    return;
                }
                try {
                    EventInstanceManager.this.em.getIv().invokeFunction(methodName, EventInstanceManager.this);
                }
                catch (NullPointerException ex3) {}
                catch (RejectedExecutionException ex4) {}
                catch (ScriptException | NoSuchMethodException ex5) {
                    System.err.println("Event name" + EventInstanceManager.this.em.getName() + ", Instance name : " + EventInstanceManager.this.name + ", method Name : " + methodName + ":\n" + ex5);
                    FilePrinter.printError("EventInstanceManager.txt", "Event name" + EventInstanceManager.this.em.getName() + ", Instance name : " + EventInstanceManager.this.name + ", method Name : " + methodName + ":\n" + ex5);
                }
            }
        }, delay);
    }
    
    public final String getName() {
        return this.name;
    }
    
    public final void setProperty(final String key, final String value) {
        if (this.disposed) {
            return;
        }
        this.props.setProperty(key, value);
    }
    
    public final Object setProperty(final String key, final String value, final boolean prev) {
        if (this.disposed) {
            return null;
        }
        return this.props.setProperty(key, value);
    }
    
    public final String getProperty(final String key) {
        if (this.disposed) {
            return "";
        }
        return this.props.getProperty(key);
    }
    
    public final Properties getProperties() {
        return this.props;
    }
    
    public final void leftParty(final MapleCharacter chr) {
        if (this.disposed) {
            return;
        }
        try {
            this.em.getIv().invokeFunction("leftParty", this, chr);
        }
        catch (Exception ex) {
            System.err.println("Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : leftParty:\n" + ex);
            FilePrinter.printError("EventInstanceManager.txt", "Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : leftParty:\n" + ex);
        }
    }
    
    public final void disbandParty() {
        if (this.disposed) {
            return;
        }
        try {
            this.em.getIv().invokeFunction("disbandParty", this);
        }
        catch (Exception ex) {
            System.out.println("Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : disbandParty:\n" + ex);
            FilePrinter.printError("EventInstanceManager.txt", "Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : disbandParty:\n" + ex);
        }
    }
    
    public final void finishPQ() {
        if (this.disposed) {
            return;
        }
        try {
            this.em.getIv().invokeFunction("clearPQ", this);
        }
        catch (RejectedExecutionException ex3) {}
        catch (ScriptException | NoSuchMethodException ex4) {
            System.err.println("Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : clearPQ:\n" + ex4);
            FilePrinter.printError("EventInstanceManager.txt", "Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : clearPQ:\n" + ex4);
        }
    }
    
    public final void removePlayer(final MapleCharacter chr) {
        if (this.disposed) {
            return;
        }
        try {
            this.em.getIv().invokeFunction("playerExit", this, chr);
        }
        catch (RejectedExecutionException ex3) {}
        catch (ScriptException | NoSuchMethodException ex4) {
            System.err.println("Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : playerExit:\n" + ex4);
            FilePrinter.printError("EventInstanceManager.txt", "Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : playerExit:\n" + ex4);
        }
    }
    
    public final void registerCarnivalParty(final MapleCharacter leader, final MapleMap map, final byte team) {
        if (this.disposed) {
            return;
        }
        leader.clearCarnivalRequests();
        final List<MapleCharacter> characters = (List<MapleCharacter>)new LinkedList();
        final MapleParty party = leader.getParty();
        if (party == null) {
            return;
        }
        for (final MaplePartyCharacter pc : party.getMembers()) {
            final MapleCharacter c = map.getCharacterById(pc.getId());
            if (c != null) {
                characters.add(c);
                this.registerPlayer(c);
                c.resetCP();
            }
        }
        final MapleCarnivalParty carnivalParty = new MapleCarnivalParty(leader, characters, team);
        for (final MapleCharacter chr : characters) {
            chr.setCarnivalParty(carnivalParty);
        }
        try {
            this.em.getIv().invokeFunction("registerCarnivalParty", this, carnivalParty);
        }
        catch (RejectedExecutionException ex2) {}
        catch (ScriptException ex) {
            System.err.println("Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : registerCarnivalParty:\n" + ex);
            FilePrinter.printError("EventInstanceManager.txt", "Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : registerCarnivalParty:\n" + ex);
        }
        catch (NoSuchMethodException ex3) {}
    }
    
    public void onMapLoad(final MapleCharacter chr) {
        if (this.disposed) {
            return;
        }
        try {
            this.em.getIv().invokeFunction("onMapLoad", this, chr);
        }
        catch (RejectedExecutionException ex2) {}
        catch (ScriptException ex) {
            System.err.println("Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : onMapLoad:\n" + ex);
            FilePrinter.printError("EventInstanceManager.txt", "Event name" + this.em.getName() + ", Instance name : " + this.name + ", method Name : onMapLoad:\n" + ex);
        }
        catch (NoSuchMethodException ex3) {}
    }
    
    public boolean isLeader(final MapleCharacter chr) {
        return chr != null && chr.getParty() != null && chr.getParty().getLeader().getId() == chr.getId();
    }
    
    public void registerSquad(final MapleSquad squad, final MapleMap map, final int questID) {
        if (this.disposed) {
            return;
        }
        final int mapid = map.getId();
        for (final String chr : squad.getMembers()) {
            final MapleCharacter player = squad.getChar(chr);
            if (player != null && player.getMapId() == mapid) {
                if (questID > 0) {
                    player.getQuestNAdd(MapleQuest.getInstance(questID)).setCustomData(String.valueOf(System.currentTimeMillis()));
                }
                this.registerPlayer(player);
            }
        }
        squad.setStatus((byte)2);
        squad.getBeginMap().broadcastMessage(MaplePacketCreator.stopClock());
    }
    
    public boolean isDisconnected(final MapleCharacter chr) {
        return !this.disposed && this.dced.contains(Integer.valueOf(chr.getId()));
    }
    
    public void removeDisconnected(final int id) {
        if (this.disposed) {
            return;
        }
        this.dced.remove(id);
    }
    
    public EventManager getEventManager() {
        return this.em;
    }
    
    public void applyBuff(final MapleCharacter chr, final int id) {
        MapleItemInformationProvider.getInstance().getItemEffect(id).applyTo(chr);
        chr.getClient().sendPacket(UIPacket.getStatusMsg(id));
    }
}
