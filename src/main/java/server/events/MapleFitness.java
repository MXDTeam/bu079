package server.events;

import java.util.Iterator;
import server.Timer.EventTimer;
import tools.MaplePacketCreator;
import client.MapleCharacter;
import java.util.concurrent.ScheduledFuture;

public class MapleFitness extends MapleEvent
{
    private static final long serialVersionUID = 845748950824L;
    private final long time = 600000L;
    private long timeStarted;
    private ScheduledFuture<?> fitnessSchedule;
    private ScheduledFuture<?> msgSchedule;
    
    public MapleFitness(final int channel, final int[] mapid) {
        super(channel, mapid);
        this.timeStarted = 0L;
    }
    
    @Override
    public void finished(final MapleCharacter chr) {
        this.givePrize(chr);
    }
    
    @Override
    public void onMapLoad(final MapleCharacter chr) {
        if (this.isTimerStarted()) {
            chr.getClient().sendPacket(MaplePacketCreator.getClock((int)(this.getTimeLeft() / 1000L)));
        }
    }
    
    @Override
    public void startEvent() {
        this.unreset();
        super.reset();
        this.broadcast(MaplePacketCreator.getClock(600));
        this.timeStarted = System.currentTimeMillis();
        this.checkAndMessage();
        final EventTimer instance = EventTimer.getInstance();
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < MapleFitness.this.mapid.length; ++i) {
                    for (final MapleCharacter chr : MapleFitness.this.getMap(i).getCharactersThreadsafe()) {
                        MapleFitness.this.warpBack(chr);
                    }
                }
                MapleFitness.this.unreset();
            }
        };
        this.getClass();
        this.fitnessSchedule = instance.schedule((Runnable)r, 600000L);
        this.broadcast(MaplePacketCreator.serverNotice(0, "活动已经开始，请通过中间的入口开始游戏。"));
    }
    
    public boolean isTimerStarted() {
        return this.timeStarted > 0L;
    }
    
    public long getTime() {
        return 600000L;
    }
    
    public void resetSchedule() {
        this.timeStarted = 0L;
        if (this.fitnessSchedule != null) {
            this.fitnessSchedule.cancel(false);
        }
        this.fitnessSchedule = null;
        if (this.msgSchedule != null) {
            this.msgSchedule.cancel(false);
        }
        this.msgSchedule = null;
    }
    
    @Override
    public void reset() {
        super.reset();
        this.resetSchedule();
        this.getMap(0).getPortal("join00").setPortalState(false);
        this.getMap(0).getPortal("in00").setPortalState(false);
    }
    
    @Override
    public void unreset() {
        super.unreset();
        this.resetSchedule();
        this.getMap(0).getPortal("join00").setPortalState(true);
        this.getMap(0).getPortal("in00").setPortalState(true);
    }
    
    public long getTimeLeft() {
        return 600000L - (System.currentTimeMillis() - this.timeStarted);
    }
    
    public void checkAndMessage() {
        this.msgSchedule = EventTimer.getInstance().register((Runnable)new Runnable() {
            @Override
            public void run() {
                final long timeLeft = MapleFitness.this.getTimeLeft();
                if (timeLeft > 9000L && timeLeft < 11000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "你还有10秒左右的时间，那些你不能击败的玩家，我希望你下次贏得胜利，回头见。"));
                }
                else if (timeLeft > 11000L && timeLeft < 101000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "好吧，你剩下沒有多少时间了，请抓紧时间冲向终点。"));
                }
                else if (timeLeft > 101000L && timeLeft < 241000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "这已经是最后了不要放弃，丰富的大奖等著你！"));
                }
                else if (timeLeft > 241000L && timeLeft < 301000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "这跳完就剩下一阶了加油！"));
                }
                else if (timeLeft > 301000L && timeLeft < 361000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "请小心掉落。"));
                }
                else if (timeLeft > 361000L && timeLeft < 501000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "请小心HP归零。"));
                }
                else if (timeLeft > 501000L && timeLeft < 601000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "请小心猴子。"));
                }
                else if (timeLeft > 601000L && timeLeft < 661000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "第二阶的技巧请利用猴子。"));
                }
                else if (timeLeft > 661000L && timeLeft < 701000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "请小心HP归零。"));
                }
                else if (timeLeft > 701000L && timeLeft < 781000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "大家知道 [终极忍耐] 很好玩的！"));
                }
                else if (timeLeft > 781000L && timeLeft < 841000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "有可能会小LAG一下不过不需要擔心。"));
                }
                else if (timeLeft > 841000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "[终极忍耐] 总共有四阶，如果你碰巧在游戏过程中死亡，你会从游戏中消失，所以请注意这一点。"));
                }
            }
        }, 90000L);
    }
}
