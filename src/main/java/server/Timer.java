package server;

import java.util.concurrent.ScheduledFuture;
import tools.FilePrinter;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public abstract class Timer
{
    private ScheduledThreadPoolExecutor ses;
    protected String file;
    protected String name;
    
    public void start() {
        if (this.ses != null && !this.ses.isShutdown() && !this.ses.isTerminated()) {
            return;
        }
        this.file = "Logs/Log_" + this.name + "_Except.rtf";
        final String tname = this.name + Randomizer.nextInt();
        final ThreadFactory thread = new ThreadFactory() {
            private final AtomicInteger threadNumber = new AtomicInteger(1);
            
            @Override
            public Thread newThread(final Runnable r) {
                final Thread t = new Thread(r);
                t.setName(tname + "-Worker-" + this.threadNumber.getAndIncrement());
                return t;
            }
        };
        final ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(7, thread);
        stpe.setKeepAliveTime(10L, TimeUnit.MINUTES);
        stpe.allowCoreThreadTimeOut(true);
        stpe.setCorePoolSize(7);
        stpe.setMaximumPoolSize(14);
        stpe.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
        this.ses = stpe;
    }
    
    public void stop() {
        try {
            this.ses.shutdownNow();
        }
        catch (Exception e) {
            FilePrinter.printError("Timer2.txt", (Throwable)e);
        }
    }
    
    public ScheduledFuture<?> register(final Runnable r, final long repeatTime, final long delay) {
        if (this.ses == null) {
            return null;
        }
        return this.ses.scheduleAtFixedRate((Runnable)new LoggingSaveRunnable(r, this.file), delay, repeatTime, TimeUnit.MILLISECONDS);
    }
    
    public ScheduledFuture<?> register(final Runnable r, final long repeatTime) {
        if (this.ses == null) {
            return null;
        }
        return this.ses.scheduleAtFixedRate((Runnable)new LoggingSaveRunnable(r, this.file), 0L, repeatTime, TimeUnit.MILLISECONDS);
    }
    
    public ScheduledFuture<?> schedule(final Runnable r, final long delay) {
        if (this.ses == null) {
            return null;
        }
        return this.ses.schedule((Runnable)new LoggingSaveRunnable(r, this.file), delay, TimeUnit.MILLISECONDS);
    }
    
    public ScheduledFuture<?> scheduleAtTimestamp(final Runnable r, final long timestamp) {
        return this.schedule(r, timestamp - System.currentTimeMillis());
    }
    
    public static class WorldTimer extends Timer
    {
        private static final WorldTimer instance = new WorldTimer();
        
        private WorldTimer() {
            this.name = "Worldtimer";
        }
        
        public static WorldTimer getInstance() {
            return WorldTimer.instance;
        }
    }
    
    public static class MapTimer extends Timer
    {
        private static final MapTimer instance = new MapTimer();
        
        private MapTimer() {
            this.name = "Maptimer";
        }
        
        public static MapTimer getInstance() {
            return MapTimer.instance;
        }
    }
    
    public static class BuffTimer extends Timer
    {
        private static final BuffTimer instance = new BuffTimer();
        
        private BuffTimer() {
            this.name = "Bufftimer";
        }
        
        public static BuffTimer getInstance() {
            return BuffTimer.instance;
        }
    }
    
    public static class EventTimer extends Timer
    {
        private static final EventTimer instance = new EventTimer();
        
        private EventTimer() {
            this.name = "Eventtimer";
        }
        
        public static EventTimer getInstance() {
            return EventTimer.instance;
        }
    }
    
    public static class CloneTimer extends Timer
    {
        private static final CloneTimer instance = new CloneTimer();
        
        private CloneTimer() {
            this.name = "Clonetimer";
        }
        
        public static CloneTimer getInstance() {
            return CloneTimer.instance;
        }
    }
    
    public static class EtcTimer extends Timer
    {
        private static final EtcTimer instance = new EtcTimer();
        
        private EtcTimer() {
            this.name = "Etctimer";
        }
        
        public static EtcTimer getInstance() {
            return EtcTimer.instance;
        }
    }
    
    public static class MobTimer extends Timer
    {
        private static final MobTimer instance = new MobTimer();
        
        private MobTimer() {
            this.name = "Mobtimer";
        }
        
        public static MobTimer getInstance() {
            return MobTimer.instance;
        }
    }
    
    public static class CheatTimer extends Timer
    {
        private static final CheatTimer instance = new CheatTimer();
        
        private CheatTimer() {
            this.name = "Cheattimer";
        }
        
        public static CheatTimer getInstance() {
            return CheatTimer.instance;
        }
    }
    
    public static class PingTimer extends Timer
    {
        private static final PingTimer instance = new PingTimer();
        
        private PingTimer() {
            this.name = "Pingtimer";
        }
        
        public static PingTimer getInstance() {
            return PingTimer.instance;
        }
    }
    
    private static class LoggingSaveRunnable implements Runnable
    {
        Runnable r;
        String file;
        
        public LoggingSaveRunnable(final Runnable r, final String file) {
            this.r = r;
            this.file = file;
        }
        
        @Override
        public void run() {
            try {
                this.r.run();
            }
            catch (Exception t) {
                FilePrinter.printError("Timer1.txt", (Throwable)t);
            }
        }
    }
}
