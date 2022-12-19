package handling.world;

import server.ServerProperties;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.List;
import java.io.Serializable;

public class MapleParty implements Serializable
{
    public static int 活动经验倍率;
    public static int 活动爆率倍率;
    public static int 活动金币倍率;
    private static final long serialVersionUID = 9179541993413738569L;
    private MaplePartyCharacter leader;
    private final List<MaplePartyCharacter> members;
    private int id;
    private int averageLevel;
    public static int 捉鬼任务线程;
    public static int 捉鬼任务;
    public static int 捉鬼任务频道;
    public static int 捉鬼任务地图;
    public static int 捉鬼任务坐标X;
    public static int 捉鬼任务坐标Y;
    public static int 捉鬼任务时间;
    public static boolean 蘑菇剧情启动状态;
    public static boolean 蘑菇剧情第一阶段启动状态;
    public static long 蘑菇剧情第一阶段时间;
    public static int 蘑菇第一阶段配置设定间隔时间;
    
    private void calculateAverageLevel() {
        int value = 0;
        for (final MaplePartyCharacter chr : this.members) {
            value += chr.getLevel();
        }
        value = (int)((double)this.averageLevel / (double)this.members.size());
        this.averageLevel = value;
    }
    
    public MapleParty(final int id, final MaplePartyCharacter chrfor) {
        this.members = (List<MaplePartyCharacter>)new LinkedList();
        this.averageLevel = 0;
        this.leader = chrfor;
        this.members.add(this.leader);
        this.id = id;
        this.averageLevel = 0;
    }
    
    public boolean containsMembers(final MaplePartyCharacter member) {
        return this.members.contains(member);
    }
    
    public void addMember(final MaplePartyCharacter member) {
        this.members.add(member);
        this.calculateAverageLevel();
    }
    
    public void removeMember(final MaplePartyCharacter member) {
        this.members.remove(member);
        this.calculateAverageLevel();
    }
    
    public void updateMember(final MaplePartyCharacter member) {
        for (int i = 0; i < this.members.size(); ++i) {
            final MaplePartyCharacter chr = (MaplePartyCharacter)this.members.get(i);
            if (chr.equals(member)) {
                this.members.set(i, member);
            }
        }
        this.calculateAverageLevel();
    }
    
    public MaplePartyCharacter getMemberById(final int id) {
        for (final MaplePartyCharacter chr : this.members) {
            if (chr.getId() == id) {
                return chr;
            }
        }
        return null;
    }
    
    public MaplePartyCharacter getMemberByIndex(final int index) {
        return (MaplePartyCharacter)this.members.get(index);
    }
    
    public Collection<MaplePartyCharacter> getMembers() {
        return new LinkedList(this.members);
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public MaplePartyCharacter getLeader() {
        return this.leader;
    }
    
    public void setLeader(final MaplePartyCharacter nLeader) {
        this.leader = nLeader;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = 31 * result + this.id;
        return result;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final MapleParty other = (MapleParty)obj;
        return this.id == other.id;
    }
    
    public int getAverageLevel() {
        return this.averageLevel;
    }
    
    static {
        活动经验倍率 = 1;
        活动爆率倍率 = 1;
        活动金币倍率 = 1;
        捉鬼任务线程 = 0;
        捉鬼任务 = 0;
        捉鬼任务频道 = 0;
        捉鬼任务地图 = 10000;
        捉鬼任务坐标X = 0;
        捉鬼任务坐标Y = 0;
        捉鬼任务时间 = 0;
        蘑菇剧情启动状态 = false;
        蘑菇剧情第一阶段启动状态 = false;
        蘑菇剧情第一阶段时间 = 0L;
        蘑菇第一阶段配置设定间隔时间 = Integer.parseInt(ServerProperties.getProperty("Lzj.蘑菇第一阶段开启时间"));
    }
}
