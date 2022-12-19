package server;

import java.util.Iterator;
import handling.world.World.Broadcast;
import tools.MaplePacketCreator;
import tools.FileoutputUtil;
import constants.PiPiConfig;
import java.util.LinkedList;
import client.MapleClient;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Set;
import java.util.List;
import java.util.Map;

public class AutobanManager implements Runnable
{
    private Map<Integer, Integer> points;
    private Map<Integer, List<String>> reasons;
    private Set<ExpirationEntry> expirations;
    private static final int AUTOBAN_POINTS = 5000;
    private static AutobanManager instance = new AutobanManager();
    private final ReentrantLock lock;
    
    public AutobanManager() {
        this.points = (Map<Integer, Integer>)new HashMap();
        this.reasons = (Map<Integer, List<String>>)new HashMap();
        this.expirations = (Set<ExpirationEntry>)new TreeSet();
        this.lock = new ReentrantLock(true);
    }
    
    public static final AutobanManager getInstance() {
        return AutobanManager.instance;
    }
    
    public final void autoban(final MapleClient c, final String reason) {
        if (c.getPlayer().isGM() || c.getPlayer().isClone()) {
            c.getPlayer().dropMessage(5, "[警告] A/b 触发 原因 : " + reason);
            return;
        }
        this.addPoints(c, 5000, 0L, reason);
    }
    
    public final void addPoints(final MapleClient c, final int points, final long expiration, final String reason) {
        this.lock.lock();
        try {
            final int acc = c.getPlayer().getAccountID();
            if (this.points.containsKey(Integer.valueOf(acc))) {
                final int SavedPoints = ((Integer)this.points.get(Integer.valueOf(acc))).intValue();
                if (SavedPoints >= 5000) {
                    return;
                }
                this.points.put(Integer.valueOf(acc), Integer.valueOf(SavedPoints + points));
                final List<String> reasonList = this.reasons.get(Integer.valueOf(acc));
                reasonList.add(reason);
            }
            else {
                this.points.put(Integer.valueOf(acc), Integer.valueOf(points));
                final List<String> reasonList = (List<String>)new LinkedList();
                reasonList.add(reason);
                this.reasons.put(Integer.valueOf(acc), reasonList);
            }
            if (((Integer)this.points.get(Integer.valueOf(acc))).intValue() >= 5000) {
                if (c.getPlayer().isGM() || c.getPlayer().isClone()) {
                    c.getPlayer().dropMessage(5, "[警告] A/b 触发 原因 : " + reason);
                    return;
                }
                final StringBuilder sb = new StringBuilder("a/b ");
                sb.append(c.getPlayer().getName());
                sb.append(" (IP ");
                sb.append(c.getSession().remoteAddress().toString());
                sb.append("): ");
                sb.append(" (MAC ");
                sb.append(c.getMacs());
                sb.append("): ");
                for (final String s : this.reasons.get(Integer.valueOf(acc))) {
                    sb.append(s);
                    sb.append(", ");
                }
                if (PiPiConfig.getAutoban()) {
                    FileoutputUtil.logToFile("logs/Hack/Ban/AutoBan.txt", "\r\n" + FileoutputUtil.NowTime() + "玩家: " + c.getPlayer().getName() + " 因为 " + sb + " 而被系统封锁");
                    Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(0, "[GM密语] " + c.getPlayer().getName() + "因为使用" + sb + "而被管理员永久停权。"));
                    Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(0, "[封锁系统] " + c.getPlayer().getName() + "因为使用违法程式练功而被管理员永久停权。"));
                    FileoutputUtil.logToFile("logs/Hack/AutoBan.txt", "\r\n" + FileoutputUtil.NowTime() + "玩家: " + c.getPlayer().getName() + " 因为 " + sb + " 而被系统封锁");
                    c.getPlayer().ban(sb.toString(), false, true, false);
                    c.getSession().close();
                }
                else {
                    FileoutputUtil.logToFile("logs/Hack/AutoBan.txt", "\r\n" + FileoutputUtil.NowTime() + "玩家: " + c.getPlayer().getName() + " 因为 " + sb + " 而被系统封锁");
                }
            }
            else if (expiration > 0L) {
                this.expirations.add(new ExpirationEntry(System.currentTimeMillis() + expiration, acc, points));
            }
        }
        finally {
            this.lock.unlock();
        }
    }
    
    @Override
    public final void run() {
        final long now = System.currentTimeMillis();
        for (final ExpirationEntry e : this.expirations) {
            if (e.time > now) {
                return;
            }
            this.points.put(Integer.valueOf(e.acc), Integer.valueOf(((Integer)this.points.get(Integer.valueOf(e.acc))).intValue() - e.points));
        }
    }
    
    private static class ExpirationEntry implements Comparable<ExpirationEntry>
    {
        public long time;
        public int acc;
        public int points;
        
        public ExpirationEntry(final long time, final int acc, final int points) {
            this.time = time;
            this.acc = acc;
            this.points = points;
        }
        
        @Override
        public int compareTo(final ExpirationEntry o) {
            return (int)(this.time - o.time);
        }
        
        @Override
        public boolean equals(final Object oth) {
            if (!(oth instanceof ExpirationEntry)) {
                return false;
            }
            final ExpirationEntry ee = (ExpirationEntry)oth;
            return this.time == ee.time && this.points == ee.points && this.acc == ee.acc;
        }
    }
}
