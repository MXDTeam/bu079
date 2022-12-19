package handling.channel;

import handling.cashshop.CashShopServer;
import client.MapleClient;
import gui.Start;
import tools.CollectionUtil;
import handling.world.CheaterData;
import constants.ServerConfig;
import java.util.LinkedList;
import java.util.List;
import server.shops.HiredMerchantSave;
import java.util.Iterator;
import server.maps.MapleMapObject;
import java.util.Map.Entry;
import java.util.Collections;
import constants.WorldConstants;
import client.MapleCharacter;
import handling.login.LoginServer;
import tools.MaplePacketCreator;
import tools.FileoutputUtil;
import server.ServerProperties;
import server.events.MapleJewel;
import server.events.MapleSnowball;
import server.events.MapleOxQuiz;
import server.events.MapleOla;
import server.events.MapleFitness;
import server.events.MapleCoconut;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.EnumMap;
import java.util.HashMap;
import tools.ConcurrentEnumMap;
import server.events.MapleEvent;
import server.events.MapleEventType;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import server.life.PlayerNPC;
import server.shops.MaplePlayerShop;
import server.shops.HiredMerchant;
import server.MapleSquad;
import server.MapleSquad.MapleSquadType;
import java.util.Map;
import scripting.EventScriptManager;
import server.maps.MapleMapFactory;
import handling.mina.ServerConnection;
import java.io.Serializable;

public class ChannelServer implements Serializable
{
    public static long serverStartTime;
    private short port;
    private static final short DEFAULT_PORT = 17575;
    private final int channel;
    private int running_MerchantID;
    private int running_PlayerShopID;
    private String socket;
    private boolean shutdown;
    private boolean finishedShutdown;
    private boolean MegaphoneMuteState;
    private PlayerStorage players;
    private ServerConnection acceptor;
    private final MapleMapFactory mapFactory;
    private EventScriptManager eventSM;
    private EventScriptManager eventSMA;
    private EventScriptManager eventSMB;
    private EventScriptManager eventSMC;
    private static final Map<Integer, ChannelServer> instances = new HashMap();
    private final Map<MapleSquadType, MapleSquad> mapleSquads;
    private final Map<Integer, HiredMerchant> merchants;
    private final Map<Integer, MaplePlayerShop> playershops;
    private final Map<Integer, PlayerNPC> playerNPCs;
    private final ReentrantReadWriteLock merchLock;
    private final ReentrantReadWriteLock squadLock;
    private int eventmap;
    private final Map<MapleEventType, MapleEvent> events;
    private static String 初始ip = ServerProperties.getProperty("Lzj.IPAddress");
    
    private ChannelServer(final int channel) {
        this.port = 17575;
        this.running_MerchantID = 0;
        this.running_PlayerShopID = 0;
        this.shutdown = false;
        this.finishedShutdown = false;
        this.MegaphoneMuteState = false;
        this.mapleSquads = new ConcurrentEnumMap(MapleSquadType.class);
        this.merchants = (Map<Integer, HiredMerchant>)new HashMap();
        this.playershops = (Map<Integer, MaplePlayerShop>)new HashMap();
        this.playerNPCs = (Map<Integer, PlayerNPC>)new HashMap();
        this.merchLock = new ReentrantReadWriteLock();
        this.squadLock = new ReentrantReadWriteLock();
        this.eventmap = -1;
        this.events = new EnumMap(MapleEventType.class);
        this.channel = channel;
        (this.mapFactory = new MapleMapFactory()).setChannel(channel);
    }
    
    public static Set<Integer> getAllChannels() {
        return new HashSet(ChannelServer.instances.keySet());
    }
    
    public final void loadEvents() {
        if (!this.events.isEmpty()) {
            return;
        }
        this.events.put(MapleEventType.打瓶盖, new MapleCoconut(this.channel, MapleEventType.打瓶盖.mapids));
        this.events.put(MapleEventType.打果子, new MapleCoconut(this.channel, MapleEventType.打果子.mapids));
        this.events.put(MapleEventType.终极忍耐, new MapleFitness(this.channel, MapleEventType.终极忍耐.mapids));
        this.events.put(MapleEventType.爬绳子, new MapleOla(this.channel, MapleEventType.爬绳子.mapids));
        this.events.put(MapleEventType.是非题大考验, new MapleOxQuiz(this.channel, MapleEventType.是非题大考验.mapids));
        this.events.put(MapleEventType.滚雪球, new MapleSnowball(this.channel, MapleEventType.滚雪球.mapids));
        this.events.put(MapleEventType.寻宝, new MapleJewel(this.channel, MapleEventType.寻宝.mapids));
    }
    
    public final void setup() {
        this.setChannel(this.channel);
        try {
            this.eventSM = new EventScriptManager(this, ServerProperties.getProperty("Lzj.events").split(","));
            this.port = (short)(ServerProperties.getProperty("Lzj.ChannelPort", (short)17575) + this.channel - 1);
        }
        catch (Exception e) {
            throw new RuntimeException((Throwable)e);
        }
        this.socket = ChannelServer.初始ip + ":" + (int)this.port;
        this.players = new PlayerStorage(this.channel);
        this.loadEvents();
        (this.acceptor = new ServerConnection((int)this.port, 0, this.channel)).run();
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:频道" + this.getChannel() + ".    - 监听端口: " + (int)this.port + "");
        this.eventSM.init();
    }
    
    public final void shutdown() {
        if (this.finishedShutdown) {
            return;
        }
        this.broadcastPacket(MaplePacketCreator.serverNotice(0, "【频道" + this.getChannel() + "】 这个频道正在关闭中."));
        this.shutdown = true;
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:[频道" + this.getChannel() + "] 储存角色资料");
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:[频道" + this.getChannel() + "] 解除端口綁定中");
        try {
            if (this.acceptor != null) {
                this.acceptor.close();
                System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:[频道" + this.getChannel() + "] 解除端口成功");
            }
        }
        catch (Exception e) {
            System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:[频道" + this.getChannel() + "] 解除端口失败" + e);
        }
        ChannelServer.instances.remove(Integer.valueOf(this.channel));
        LoginServer.removeChannel(this.channel);
        this.setFinishShutdown();
    }
    
    public final boolean hasFinishedShutdown() {
        return this.finishedShutdown;
    }
    
    public final MapleMapFactory getMapFactory() {
        return this.mapFactory;
    }
    
    public final void addPlayer(final MapleCharacter chr) {
        this.getPlayerStorage().registerPlayer(chr);
        chr.getClient().sendPacket(MaplePacketCreator.serverMessage(this.getServerMessage()));
    }
    
    public final PlayerStorage getPlayerStorage() {
        if (this.players == null) {
            this.players = new PlayerStorage(this.channel);
        }
        return this.players;
    }
    
    public final void removePlayer(final MapleCharacter chr) {
        this.getPlayerStorage().deregisterPlayer(chr);
    }
    
    public final void removePlayer(final int idz, final String namez) {
        this.getPlayerStorage().deregisterPlayer(idz, namez);
    }
    
    public final String getServerMessage() {
        return WorldConstants.SCROLL_MESSAGE;
    }
    
    public final void setServerMessage(final String newMessage) {
        WorldConstants.SCROLL_MESSAGE = newMessage;
    }
    
    public final void broadcastPacket(final byte[] data) {
        this.getPlayerStorage().broadcastPacket(data);
    }
    
    public final void broadcastSmegaPacket(final byte[] data) {
        this.getPlayerStorage().broadcastSmegaPacket(data);
    }
    
    public final void broadcastGashponmegaPacket(final byte[] data) {
        this.getPlayerStorage().broadcastGashponmegaPacket(data);
    }
    
    public final void broadcastGMPacket(final byte[] data) {
        this.getPlayerStorage().broadcastGMPacket(data);
    }
    
    public final void broadcastGMPacket(final byte[] data, final boolean 吸怪) {
        this.getPlayerStorage().broadcastGMPacket(data, 吸怪);
    }
    
    public final int getExpRate() {
        return WorldConstants.EXP_RATE;
    }
    
    public final void setExpRate(final int expRate) {
        WorldConstants.EXP_RATE = expRate;
    }
    
    public final int getMesoRate() {
        return WorldConstants.MESO_RATE;
    }
    
    public final void setMesoRate(final int mesoRate) {
        WorldConstants.MESO_RATE = mesoRate;
    }
    
    public final int getDropRate() {
        return WorldConstants.DROP_RATE;
    }
    
    public final void setDropRate(final int dropRate) {
        WorldConstants.DROP_RATE = dropRate;
    }
    
    public final int getChannel() {
        return this.channel;
    }
    
    public final void setChannel(final int channel) {
        ChannelServer.instances.put(Integer.valueOf(channel), this);
        LoginServer.addChannel(channel);
    }
    
    public static final Collection<ChannelServer> getAllInstances() {
        return Collections.unmodifiableCollection(ChannelServer.instances.values());
    }
    
    public final String getSocket() {
        return this.socket;
    }
    
    public final boolean isShutdown() {
        return this.shutdown;
    }
    
    public final int getLoadedMaps() {
        return this.mapFactory.getLoadedMaps();
    }
    
    public final EventScriptManager getEventSM() {
        return this.eventSM;
    }
    
    public final EventScriptManager getEventSMA() {
        return this.eventSMA;
    }
    
    public final EventScriptManager getEventSMB() {
        return this.eventSMB;
    }
    
    public final EventScriptManager getEventSMC() {
        return this.eventSMC;
    }
    
    public final void reloadEvents() {
        this.eventSM.cancel();
        (this.eventSM = new EventScriptManager(this, ServerProperties.getProperty("Lzj.events").split(","))).init();
    }
    
    public final void reloadEventsa() {
        this.eventSMA.cancel();
        (this.eventSMA = new EventScriptManager(this, ServerProperties.getProperty("Lzj.活动事件脚本").split(","))).init();
    }
    
    public final void reloadEventsb() {
        this.eventSMB.cancel();
        (this.eventSMB = new EventScriptManager(this, ServerProperties.getProperty("Lzj.BOSS事件脚本").split(","))).init();
    }
    
    public final void reloadEventsc() {
        this.eventSMC.cancel();
        (this.eventSMC = new EventScriptManager(this, ServerProperties.getProperty("Lzj.自定义事件脚本").split(","))).init();
    }
    
    public Map<MapleSquadType, MapleSquad> getAllSquads() {
        return Collections.unmodifiableMap(this.mapleSquads);
    }
    
    public final MapleSquad getMapleSquad(final String type) {
        return this.getMapleSquad(MapleSquadType.valueOf(type.toLowerCase()));
    }
    
    public final MapleSquad getMapleSquad(final MapleSquadType type) {
        return (MapleSquad)this.mapleSquads.get(type);
    }
    
    public final boolean addMapleSquad(final MapleSquad squad, final String type) {
        final MapleSquadType types = MapleSquadType.valueOf(type.toLowerCase());
        if (types != null && !this.mapleSquads.containsKey(types)) {
            this.mapleSquads.put(types, squad);
            squad.scheduleRemoval();
            return true;
        }
        return false;
    }
    
    public final boolean removeMapleSquad(final MapleSquadType types) {
        if (types != null && this.mapleSquads.containsKey(types)) {
            this.mapleSquads.remove(types);
            return true;
        }
        return false;
    }
    
    public final int closeAllPlayerShop() {
        int ret = 0;
        this.merchLock.writeLock().lock();
        try {
            final Iterator<Entry<Integer, MaplePlayerShop>> playershops_ = this.playershops.entrySet().iterator();
            while (playershops_.hasNext()) {
                final MaplePlayerShop hm = (MaplePlayerShop)((Entry<Integer, MaplePlayerShop>)playershops_.next()).getValue();
                hm.closeShop(true, false);
                hm.getMap().removeMapObject((MapleMapObject)hm);
                playershops_.remove();
                ++ret;
            }
        }
        finally {
            this.merchLock.writeLock().unlock();
        }
        return ret;
    }
    
    public final int closeAllMerchant() {
        int ret = 0;
        this.merchLock.writeLock().lock();
        try {
            final Iterator<Entry<Integer, HiredMerchant>> merchants_ = this.merchants.entrySet().iterator();
            while (merchants_.hasNext()) {
                final HiredMerchant hm = (HiredMerchant)((Entry<Integer, HiredMerchant>)merchants_.next()).getValue();
                hm.closeShop(true, false);
                HiredMerchantSave.QueueShopForSave(hm);
                hm.getMap().removeMapObject((MapleMapObject)hm);
                merchants_.remove();
                ++ret;
            }
        }
        finally {
            this.merchLock.writeLock().unlock();
        }
        for (int i = 910000001; i <= 910000022; ++i) {
            for (final MapleMapObject mmo : this.mapFactory.getMap(i).getAllHiredMerchantsThreadsafe()) {
                HiredMerchantSave.QueueShopForSave((HiredMerchant)mmo);
                ((HiredMerchant)mmo).closeShop(true, false);
                ++ret;
            }
        }
        return ret;
    }
    
    public final int addPlayerShop(final MaplePlayerShop PlayerShop) {
        this.merchLock.writeLock().lock();
        int runningmer = 0;
        try {
            runningmer = this.running_PlayerShopID;
            this.playershops.put(Integer.valueOf(this.running_PlayerShopID), PlayerShop);
            ++this.running_PlayerShopID;
        }
        finally {
            this.merchLock.writeLock().unlock();
        }
        return runningmer;
    }
    
    public final int addMerchant(final HiredMerchant hMerchant) {
        this.merchLock.writeLock().lock();
        int runningmer = 0;
        try {
            runningmer = this.running_MerchantID;
            this.merchants.put(Integer.valueOf(this.running_MerchantID), hMerchant);
            ++this.running_MerchantID;
        }
        finally {
            this.merchLock.writeLock().unlock();
        }
        return runningmer;
    }
    
    public final void removeMerchant(final HiredMerchant hMerchant) {
        this.merchLock.writeLock().lock();
        try {
            this.merchants.remove(Integer.valueOf(hMerchant.getStoreId()));
        }
        finally {
            this.merchLock.writeLock().unlock();
        }
    }
    
    public final boolean containsMerchant(final int accid) {
        boolean contains = false;
        this.merchLock.readLock().lock();
        try {
            final Iterator itr = this.merchants.values().iterator();
            while (itr.hasNext()) {
                if (((HiredMerchant)itr.next()).getOwnerAccId() == accid) {
                    contains = true;
                    break;
                }
            }
        }
        finally {
            this.merchLock.readLock().unlock();
        }
        return contains;
    }
    
    public final List<HiredMerchant> searchMerchant(final int itemSearch) {
        final List<HiredMerchant> list = (List<HiredMerchant>)new LinkedList();
        this.merchLock.readLock().lock();
        try {
            for (final HiredMerchant hm : this.merchants.values()) {
                if (hm.searchItem(itemSearch).size() > 0) {
                    list.add(hm);
                }
            }
        }
        finally {
            this.merchLock.readLock().unlock();
        }
        return list;
    }
    
    public final void toggleMegaphoneMuteState() {
        this.MegaphoneMuteState = !this.MegaphoneMuteState;
    }
    
    public final boolean getMegaphoneMuteState() {
        return this.MegaphoneMuteState;
    }
    
    public int getEvent() {
        return this.eventmap;
    }
    
    public final void setEvent(final int ze) {
        this.eventmap = ze;
    }
    
    public MapleEvent getEvent(final MapleEventType t) {
        return (MapleEvent)this.events.get(t);
    }
    
    public final Collection<PlayerNPC> getAllPlayerNPC() {
        return this.playerNPCs.values();
    }
    
    public final PlayerNPC getPlayerNPC(final int id) {
        return (PlayerNPC)this.playerNPCs.get(Integer.valueOf(id));
    }
    
    public final void addPlayerNPC(final PlayerNPC npc) {
        if (this.playerNPCs.containsKey(Integer.valueOf(npc.getId()))) {
            this.removePlayerNPC(npc);
        }
        this.playerNPCs.put(Integer.valueOf(npc.getId()), npc);
        this.getMapFactory().getMap(npc.getMapId()).addMapObject((MapleMapObject)npc);
    }
    
    public final void removePlayerNPC(final PlayerNPC npc) {
        if (this.playerNPCs.containsKey(Integer.valueOf(npc.getId()))) {
            this.playerNPCs.remove(Integer.valueOf(npc.getId()));
            this.getMapFactory().getMap(npc.getMapId()).removeMapObject((MapleMapObject)npc);
        }
    }
    
    public final String getServerName() {
        return ServerConfig.SERVER_NAME;
    }
    
    public final void setServerName(final String sn) {
        ServerConfig.SERVER_NAME = sn;
    }
    
    public final int getPort() {
        return this.port;
    }
    
    public final void setPrepareShutdown() {
        this.shutdown = true;
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:[频道" + this.getChannel() + "] 正在准备关闭");
    }
    
    public final void setFinishShutdown() {
        this.finishedShutdown = true;
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:[频道" + this.getChannel() + "] 已经关闭完毕");
    }
    
    public final boolean isAdminOnly() {
        return WorldConstants.ADMIN_ONLY;
    }
    
    public static Map<Integer, Integer> getChannelLoad() {
        final Map<Integer, Integer> ret = (Map<Integer, Integer>)new HashMap();
        for (final ChannelServer cs : ChannelServer.instances.values()) {
            ret.put(Integer.valueOf(cs.getChannel()), Integer.valueOf(cs.getConnectedClients()));
        }
        return ret;
    }
    
    public int getConnectedClients() {
        final double bfb = (double)LoginServer.getRSGS() / 100.0 * (double)this.getPlayerStorage().getConnectedClients();
        return this.getPlayerStorage().getConnectedClients() + (int)Math.ceil(bfb);
    }
    
    public List<CheaterData> getCheaters() {
        final List<CheaterData> cheaters = this.getPlayerStorage().getCheaters();
        Collections.sort(cheaters);
        return CollectionUtil.copyFirst(cheaters, 20);
    }
    
    public void broadcastMessage(final byte[] message) {
        this.broadcastPacket(message);
    }
    
    public void broadcastSmega(final byte[] message) {
        this.broadcastSmegaPacket(message);
    }
    
    public void broadcastGashponmega(final byte[] message) {
        this.broadcastGashponmegaPacket(message);
    }
    
    public void broadcastGMMessage(final byte[] message, final boolean 吸怪) {
        this.broadcastGMPacket(message, 吸怪);
    }
    
    public void broadcastGMMessage(final byte[] message) {
        this.broadcastGMPacket(message);
    }
    
    public void saveAll() {
        int ppl = 0;
        final List<MapleCharacter> all = this.players.getAllCharactersThreadSafe();
        for (final MapleCharacter chr : all) {
            try {
                final int res = chr.saveToDB(false, false);
                if (((Integer)Start.ConfigValuesMap.get("离线泡点开关")).intValue() > 0) {
                    chr.updateOfflineTime();
                }
                if (res == 1) {
                    ++ppl;
                }
                else {
                    System.out.println("[自动存档] 角色:" + chr.getName() + " 储存失败.");
                }
            }
            catch (Exception e) {
                FileoutputUtil.logToFile("logs/saveAll存档保存数据异常.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + chr.getClient().getSession().remoteAddress().toString().split(":")[0] + " 账号 " + chr.getClient().getAccountName() + " 账号ID " + chr.getClient().getAccID() + " 角色名 " + chr.getName() + " 角色ID " + chr.getId());
                FileoutputUtil.outError("logs/saveAll存档保存数据异常.txt", (Throwable)e);
            }
        }
    }
    
    public boolean CanGMItem() {
        return WorldConstants.GMITEMS;
    }
    
    public final int getMerchantMap(final MapleCharacter chr) {
        final int ret = -1;
        for (int i = 910000001; i <= 910000022; ++i) {
            for (final MapleMapObject mmo : this.mapFactory.getMap(i).getAllHiredMerchantsThreadsafe()) {
                if (((HiredMerchant)mmo).getOwnerId() == chr.getId()) {
                    return this.mapFactory.getMap(i).getId();
                }
            }
        }
        return ret;
    }
    
    public static final int getChannelCount() {
        return ChannelServer.instances.size();
    }
    
    public static void forceRemovePlayerByAccId(final MapleClient client, final int accid) {
        for (final ChannelServer ch : getAllInstances()) {
            Collection<MapleCharacter> chrs = ch.getPlayerStorage().getAllCharactersThreadSafe();
            for (final MapleCharacter c : chrs) {
                if (c.getAccountID() == accid) {
                    try {
                        if (c.getClient() != null && c.getClient() != client) {
                            c.getClient().unLockDisconnect();
                        }
                    }
                    catch (Exception ex) {}
                    chrs = ch.getPlayerStorage().getAllCharactersThreadSafe();
                    if (!chrs.contains(c)) {
                        continue;
                    }
                    ch.removePlayer(c);
                }
            }
        }
        try {
            final Collection<MapleCharacter> chrs2 = CashShopServer.getPlayerStorage().getAllCharactersThreadSafe();
            for (final MapleCharacter c2 : chrs2) {
                if (c2.getAccountID() == accid) {
                    try {
                        if (c2.getClient() == null || c2.getClient() == client) {
                            continue;
                        }
                        c2.getClient().unLockDisconnect();
                    }
                    catch (Exception ex2) {}
                }
            }
        }
        catch (Exception ex3) {}
    }
    
    public static final Set<Integer> getChannels() {
        return new HashSet(ChannelServer.instances.keySet());
    }
    
    public static final ChannelServer newInstance(final int channel) {
        return new ChannelServer(channel);
    }
    
    public static final ChannelServer getInstance(final int channel) {
        return (ChannelServer)ChannelServer.instances.get(Integer.valueOf(channel));
    }
    
    public static final void startAllChannels() {
        ChannelServer.serverStartTime = System.currentTimeMillis();
        for (int channelCount = WorldConstants.CHANNEL_COUNT, i = 1; i <= Math.min(20, (channelCount > 0) ? channelCount : 1); ++i) {
            newInstance(i).setup();
        }
    }
    
    public static final void startChannel(final int channel) {
        ChannelServer.serverStartTime = System.currentTimeMillis();
        if (channel <= WorldConstants.CHANNEL_COUNT) {
            newInstance(channel).setup();
        }
    }
    
    public static void forceRemovePlayerByCharName(final MapleClient client, final String Name) {
        for (final ChannelServer ch : getAllInstances()) {
            Collection<MapleCharacter> chrs = ch.getPlayerStorage().getAllCharactersThreadSafe();
            for (final MapleCharacter c : chrs) {
                if (c.getName().equalsIgnoreCase(Name)) {
                    try {
                        if (c.getClient() != null && c.getClient() != client) {
                            c.getClient().unLockDisconnect();
                        }
                    }
                    catch (Exception ex) {}
                    chrs = ch.getPlayerStorage().getAllCharactersThreadSafe();
                    if (chrs.contains(c)) {
                        ch.removePlayer(c);
                    }
                    c.getMap().removePlayer(c);
                }
            }
        }
    }
    
    public static void forceRemovePlayerByCharNameFromDataBase(final MapleClient client, final List<String> Name) {
        for (final ChannelServer ch : getAllInstances()) {
            for (final String name : Name) {
                if (ch.getPlayerStorage().getCharacterByName(name) != null) {
                    final MapleCharacter c = ch.getPlayerStorage().getCharacterByName(name);
                    try {
                        if (c.getClient() != null && c.getClient() != client) {
                            c.getClient().unLockDisconnect();
                        }
                    }
                    catch (Exception ex) {}
                    if (ch.getPlayerStorage().getAllCharactersThreadSafe().contains(c)) {
                        ch.removePlayer(c);
                    }
                    c.getMap().removePlayer(c);
                }
            }
        }
        for (final String name2 : Name) {
            if (CashShopServer.getPlayerStorage().getCharacterByName(name2) != null) {
                final MapleCharacter c2 = CashShopServer.getPlayerStorage().getCharacterByName(name2);
                try {
                    if (c2.getClient() == null || c2.getClient() == client) {
                        continue;
                    }
                    c2.getClient().unLockDisconnect();
                }
                catch (Exception ex2) {}
            }
        }
    }
    
    public void AutoNx(final int dy) {
        this.mapFactory.getMap(910000000).AutoNxmht(dy);
    }
    
    public void AutoTime(final int dy) {
        for (final ChannelServer chan : getAllInstances()) {
            for (final MapleCharacter chr : chan.getPlayerStorage().getAllCharacters()) {
                if (chr != null) {
                    chr.gainGamePoints(1);
                    if (chr.getGamePoints() >= 5) {
                        continue;
                    }
                    chr.resetGamePointsPD();
                }
            }
        }
    }
}
