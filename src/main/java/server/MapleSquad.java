package server;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.List;
import server.maps.MapleMap;
import tools.MaplePacketCreator;
import handling.world.World.Find;
import handling.channel.ChannelServer;
import tools.Pair;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import client.MapleClient;
import java.util.concurrent.ScheduledFuture;
import java.util.Map;
import client.MapleCharacter;
import java.lang.ref.WeakReference;

public class MapleSquad{
    private static int 扎昆组队人数 = Integer.parseInt(ServerProperties.getProperty("Lzj.扎昆组队人数"));
    private static int 黑龙组队人数 = Integer.parseInt(ServerProperties.getProperty("Lzj.黑龙组队人数"));
    private static int 品克宾组队人数 = Integer.parseInt(ServerProperties.getProperty("Lzj.品克宾组队人数"));
    private WeakReference<MapleCharacter> leader;
    private final String leaderName;
    private final String toSay;
    private final Map<String, String> members;
    private final Map<String, String> bannedMembers;
    private final int ch;
    private final long startTime;
    private final int expiration;
    private final int beginMapId;
    private final MapleSquadType type;
    private byte status;
    private ScheduledFuture<?> removal;
    private MapleClient c;
    
    public MapleSquad(final int ch, final String type, final MapleCharacter leader, final int expiration, final String toSay) {
        this.members = (Map<String, String>)new LinkedHashMap();
        this.bannedMembers = (Map<String, String>)new LinkedHashMap();
        this.status = 0;
        this.leader = new WeakReference(leader);
        this.members.put(leader.getName(), MapleCarnivalChallenge.getJobNameById((int)leader.getJob()));
        this.leaderName = leader.getName();
        this.ch = ch;
        this.toSay = toSay;
        this.type = MapleSquadType.valueOf(type.toLowerCase());
        this.status = 1;
        this.beginMapId = leader.getMapId();
        leader.getMap().setSquad(this.type);
        if (this.type.queue.get(Integer.valueOf(ch)) == null) {
            this.type.queue.put(Integer.valueOf(ch), new ArrayList());
            this.type.queuedPlayers.put(Integer.valueOf(ch), new ArrayList());
        }
        this.startTime = System.currentTimeMillis();
        this.expiration = expiration;
    }
    
    public void copy() {
        while ((this.type.queue.get(Integer.valueOf(this.ch))).size() > 0 && ChannelServer.getInstance(this.ch).getMapleSquad(this.type) == null) {
            int index = 0;
            long lowest = 0L;
            for (int i = 0; i < (this.type.queue.get(Integer.valueOf(this.ch))).size(); ++i) {
                if (lowest == 0L || ((Long)((this.type.queue.get(Integer.valueOf(this.ch))).get(i)).right).longValue() < lowest) {
                    index = i;
                    lowest = ((Long)((this.type.queue.get(Integer.valueOf(this.ch))).get(i)).right).longValue();
                }
            }
            final String nextPlayerId = (String)((this.type.queue.get(Integer.valueOf(this.ch))).remove(index)).left;
            final int theirCh = Find.findChannel(nextPlayerId);
            if (theirCh > 0) {
                final MapleCharacter lead = ChannelServer.getInstance(theirCh).getPlayerStorage().getCharacterByName(nextPlayerId);
                if (lead != null && lead.getMapId() == this.beginMapId && lead.getClient().getChannel() == this.ch) {
                    final MapleSquad squad = new MapleSquad(this.ch, this.type.name(), lead, this.expiration, this.toSay);
                    if (ChannelServer.getInstance(this.ch).addMapleSquad(squad, this.type.name())) {
                        this.getBeginMap().broadcastMessage(MaplePacketCreator.getClock(this.expiration / 1000));
                        this.getBeginMap().broadcastMessage(MaplePacketCreator.serverNotice(6, nextPlayerId + this.toSay));
                        (this.type.queuedPlayers.get(Integer.valueOf(this.ch))).add(new Pair(nextPlayerId, "成功"));
                        break;
                    }
                    squad.clear();
                    (this.type.queuedPlayers.get(Integer.valueOf(this.ch))).add(new Pair(nextPlayerId, "跳过"));
                    break;
                }
                else {
                    if (lead != null) {
                        lead.dropMessage(6, "远征队已经结束了，由于沒有在正确的频道里。");
                    }
                    this.getBeginMap().broadcastMessage(MaplePacketCreator.serverNotice(6, nextPlayerId + "远征队已经结束了，由于有成员沒有在地图內"));
                    (this.type.queuedPlayers.get(Integer.valueOf(this.ch))).add(new Pair(nextPlayerId, "不在地图內"));
                }
            }
            else {
                this.getBeginMap().broadcastMessage(MaplePacketCreator.serverNotice(6, nextPlayerId + "'远征队已经结束了，由于有成员沒有在线上"));
                (this.type.queuedPlayers.get(Integer.valueOf(this.ch))).add(new Pair(nextPlayerId, "沒有上线"));
            }
        }
    }
    
    public MapleMap getBeginMap() {
        return ChannelServer.getInstance(this.ch).getMapFactory().getMap(this.beginMapId);
    }
    
    public void clear() {
        if (this.removal != null) {
            this.getBeginMap().broadcastMessage(MaplePacketCreator.stopClock());
            this.removal.cancel(false);
            this.removal = null;
        }
        this.members.clear();
        this.bannedMembers.clear();
        this.leader = null;
        ChannelServer.getInstance(this.ch).removeMapleSquad(this.type);
        this.status = 0;
    }
    
    public MapleCharacter getChar(final String name) {
        return ChannelServer.getInstance(this.ch).getPlayerStorage().getCharacterByName(name);
    }
    
    public long getTimeLeft() {
        return (long)this.expiration - (System.currentTimeMillis() - this.startTime);
    }
    
    public void scheduleRemoval() {
        this.removal = server.Timer.EtcTimer.getInstance().schedule((Runnable)new Runnable() {
            @Override
            public void run() {
                if (MapleSquad.this.status != 0 && MapleSquad.this.leader != null && (MapleSquad.this.getLeader() == null || MapleSquad.this.status == 1)) {
                    MapleSquad.this.clear();
                    MapleSquad.this.copy();
                }
            }
        }, (long)this.expiration);
    }
    
    public String getLeaderName() {
        return this.leaderName;
    }
    
    public List<Pair<String, Long>> getAllNextPlayer() {
        return this.type.queue.get(Integer.valueOf(this.ch));
    }
    
    public String getNextPlayer() {
        final StringBuilder sb = new StringBuilder("\n排队成员 : ");
        sb.append("#b").append((this.type.queue.get(Integer.valueOf(this.ch))).size()).append(" #k ").append("与远征队名单 : \n\r ");
        int i = 0;
        for (final Pair<String, Long> chr : this.type.queue.get(Integer.valueOf(this.ch))) {
            ++i;
            sb.append(i).append(" : ").append((String)chr.left);
            sb.append(" \n\r ");
        }
        sb.append("你是否想要 #e当下一个#n 在远征队排队中\u3000或者 #e移除#n 在远征队? 如果你想的话...");
        return sb.toString();
    }
    
    public void setNextPlayer(final String i) {
        Pair<String, Long> toRemove = null;
        for (final Pair<String, Long> s : this.type.queue.get(Integer.valueOf(this.ch))) {
            if (((String)s.left).equals(i)) {
                toRemove = s;
                break;
            }
        }
        if (toRemove != null) {
            (this.type.queue.get(Integer.valueOf(this.ch))).remove(toRemove);
            return;
        }
        for (final ArrayList<Pair<String, Long>> v : this.type.queue.values()) {
            for (final Pair<String, Long> s2 : v) {
                if (((String)s2.left).equals(i)) {
                    return;
                }
            }
        }
        (this.type.queue.get(Integer.valueOf(this.ch))).add(new Pair(i, Long.valueOf(System.currentTimeMillis())));
    }
    
    public MapleCharacter getLeader() {
        if (this.leader == null || this.leader.get() == null) {
            if (this.members.size() <= 0 || this.getChar(this.leaderName) == null) {
                if (this.status != 0) {
                    this.clear();
                }
                return null;
            }
            this.leader = new WeakReference(this.getChar(this.leaderName));
        }
        return (MapleCharacter)this.leader.get();
    }
    
    public boolean containsMember(final MapleCharacter member) {
        for (final String mmbr : this.members.keySet()) {
            if (mmbr.equalsIgnoreCase(member.getName())) {
                return true;
            }
        }
        return false;
    }
    
    public List<String> getMembers() {
        return new LinkedList(this.members.keySet());
    }
    
    public List<String> getBannedMembers() {
        return new LinkedList(this.bannedMembers.keySet());
    }
    
    public int getSquadSize() {
        return this.members.size();
    }
    
    public boolean isBanned(final MapleCharacter member) {
        return this.bannedMembers.containsKey(member.getName());
    }
    
    public int addMember(final MapleCharacter member, final boolean join) {
        if (this.getLeader() == null) {
            return -1;
        }
        final String job = MapleCarnivalChallenge.getJobNameById((int)member.getJob());
        if (join) {
            if (this.containsMember(member) || this.getAllNextPlayer().contains(member.getName())) {
                return -1;
            }
            if (this.members.size() <= 30) {
                this.members.put(member.getName(), job);
                this.getLeader().dropMessage(6, member.getName() + " (" + job + ") 加入了远征队!");
                return 1;
            }
            return 2;
        }
        else {
            if (this.containsMember(member)) {
                this.members.remove(member.getName());
                this.getLeader().dropMessage(6, member.getName() + " (" + job + ") 离开了远征队.");
                return 1;
            }
            return -1;
        }
    }
    
    public void acceptMember(final int pos) {
        if (pos < 0 || pos >= this.bannedMembers.size()) {
            return;
        }
        final List<String> membersAsList = this.getBannedMembers();
        final String toadd = (String)membersAsList.get(pos);
        if (toadd != null && this.getChar(toadd) != null) {
            this.members.put(toadd, this.bannedMembers.get(toadd));
            this.bannedMembers.remove(toadd);
            this.getChar(toadd).dropMessage(5, this.getLeaderName() + " 允许你重新回来远征队");
        }
    }
    
    public void reAddMember(final MapleCharacter chr) {
        this.removeMember(chr);
        this.members.put(chr.getName(), MapleCarnivalChallenge.getJobNameById((int)chr.getJob()));
    }
    
    public void removeMember(final MapleCharacter chr) {
        if (this.members.containsKey(chr.getName())) {
            this.members.remove(chr.getName());
        }
    }
    
    public void removeMember(final String chr) {
        if (this.members.containsKey(chr)) {
            this.members.remove(chr);
        }
    }
    
    public void banMember(final int pos) {
        if (pos <= 0 || pos >= this.members.size()) {
            return;
        }
        final List<String> membersAsList = this.getMembers();
        final String toban = (String)membersAsList.get(pos);
        if (toban != null && this.getChar(toban) != null) {
            this.bannedMembers.put(toban, this.members.get(toban));
            this.members.remove(toban);
            this.getChar(toban).dropMessage(5, this.getLeaderName() + " 从远征队中刪除您.");
        }
    }
    
    public void setStatus(final byte status) {
        this.status = status;
        if (status == 2 && this.removal != null) {
            this.removal.cancel(false);
            this.removal = null;
        }
    }
    
    public int getStatus() {
        return this.status;
    }
    
    public int getBannedMemberSize() {
        return this.bannedMembers.size();
    }
    
    public String getSquadMemberString(final byte type) {
        switch (type) {
            case 0: {
                final StringBuilder sb = new StringBuilder("目前远征队总人数 : ");
                sb.append("#b").append(this.members.size()).append(" #k ").append("\r\n远征队名单 : \n\r ");
                int i = 0;
                for (final Entry<String, String> chr : this.members.entrySet()) {
                    ++i;
                    sb.append(i).append(" : ").append((String)chr.getKey()).append(" (").append((String)chr.getValue()).append(") ");
                    if (i == 1) {
                        sb.append("(远征队领袖)");
                    }
                    sb.append(" \n\r ");
                }
                while (i < 30) {
                    ++i;
                    sb.append(i).append(" : ").append(" \n\r ");
                }
                return sb.toString();
            }
            case 1: {
                final StringBuilder sb = new StringBuilder("目前远征队总人数 : ");
                sb.append("#b").append(this.members.size()).append(" #k ").append("\r\n远征队名单 : \n\r ");
                int i = 0;
                int selection = 0;
                for (final Entry<String, String> chr2 : this.members.entrySet()) {
                    ++i;
                    sb.append("#b#L").append(selection).append("#");
                    ++selection;
                    sb.append(i).append(" : ").append((String)chr2.getKey()).append(" (").append((String)chr2.getValue()).append(") ");
                    if (i == 1) {
                        sb.append("(远征队领袖)");
                    }
                    sb.append("#l").append(" \n\r ");
                }
                while (i < 30) {
                    ++i;
                    sb.append(i).append(" : ").append(" \n\r ");
                }
                return sb.toString();
            }
            case 2: {
                final StringBuilder sb = new StringBuilder("目前远征队总人数 : ");
                sb.append("#b").append(this.members.size()).append(" #k ").append("\r\n远征队名单 : \n\r ");
                int i = 0;
                int selection = 0;
                for (final Entry<String, String> chr2 : this.bannedMembers.entrySet()) {
                    ++i;
                    sb.append("#b#L").append(selection).append("#");
                    ++selection;
                    sb.append(i).append(" : ").append((String)chr2.getKey()).append(" (").append((String)chr2.getValue()).append(") ");
                    sb.append("#l").append(" \n\r ");
                }
                while (i < 30) {
                    ++i;
                    sb.append(i).append(" : ").append(" \n\r ");
                }
                return sb.toString();
            }
            case 3: {
                final StringBuilder sb = new StringBuilder("职业 : ");
                final Map<String, Integer> jobs = this.getJobs();
                for (final Entry<String, Integer> chr3 : jobs.entrySet()) {
                    sb.append("\r\n").append((String)chr3.getKey()).append(" : ").append(chr3.getValue());
                }
                return sb.toString();
            }
            default: {
                return null;
            }
        }
    }
    
    public final MapleSquadType getType() {
        return this.type;
    }
    
    public final Map<String, Integer> getJobs() {
        final Map<String, Integer> jobs = (Map<String, Integer>)new LinkedHashMap();
        for (final Entry<String, String> chr : this.members.entrySet()) {
            if (jobs.containsKey(chr.getValue())) {
                jobs.put(chr.getValue(), Integer.valueOf(((Integer)jobs.get(chr.getValue())).intValue() + 1));
            }
            else {
                jobs.put(chr.getValue(), Integer.valueOf(1));
            }
        }
        return jobs;
    }
    
    public boolean isAllJobs() {
        final List<String> jobs = (List<String>)new ArrayList();
        for (final Entry<String, String> chr : this.members.entrySet()) {
            if (jobs.equals(chr.getValue())) {
                jobs.add(chr.getValue());
            }
        }
        for (int b = 1; b < 5; ++b) {
            boolean pd = false;
            for (int a = 0; a < jobs.size(); ++a) {
                if (Math.floor((double)(MapleCarnivalChallenge.getIdByJobName((String)jobs.get(a)) / 100)) == (double)b) {
                    pd = true;
                }
            }
            if (!pd) {
                return false;
            }
        }
        return true;
    }
    
    public enum MapleSquadType
    {
        bossbalrog(2), 
        zak(MapleSquad.扎昆组队人数), 
        chaoszak(3), 
        horntail(MapleSquad.黑龙组队人数), 
        chaosht(3), 
        pinkbean(MapleSquad.品克宾组队人数), 
        nmm_squad(2), 
        vergamot(2), 
        dunas(2), 
        nibergen_squad(2), 
        dunas2(2), 
        core_blaze(2), 
        aufheben(2), 
        cwkpq(10), 
        vonleon(3), 
        scartar(2), 
        cygnus(3);
        
        public int i;
        public HashMap<Integer, ArrayList<Pair<String, String>>> queuedPlayers;
        public HashMap<Integer, ArrayList<Pair<String, Long>>> queue;
        
        private MapleSquadType(final int i) {
            this.queuedPlayers = (HashMap<Integer, ArrayList<Pair<String, String>>>)new HashMap();
            this.queue = (HashMap<Integer, ArrayList<Pair<String, Long>>>)new HashMap();
            this.i = i;
        }
    }
}
