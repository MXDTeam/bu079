package server.shops;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class HiredMerchantSave
{
    public static final int NumSavingThreads = 5;
    private static final TimingThread[] Threads;
    private static final AtomicInteger Distribute;
    
    public static void QueueShopForSave(final HiredMerchant hm) {
        final int Current = HiredMerchantSave.Distribute.getAndIncrement() % 5;
        HiredMerchantSave.Threads[Current].getRunnable().Queue(hm);
    }
    
    public static void Execute(final Object ToNotify) {
        for (final TimingThread Thread : HiredMerchantSave.Threads) {
            Thread.getRunnable().SetToNotify(ToNotify);
        }
        for (final TimingThread Thread : HiredMerchantSave.Threads) {
            Thread.start();
        }
    }
    
    static {
        Threads = new TimingThread[5];
        for (int i = 0; i < Threads.length; ++i) {
            Threads[i] = new TimingThread(new HiredMerchantSaveRunnable());
        }
        Distribute = new AtomicInteger(0);
    }
    
    private static class HiredMerchantSaveRunnable implements Runnable
    {
        private static final AtomicInteger RunningThreadID = new AtomicInteger(0);
        private final int ThreadID;
        private long TimeTaken;
        private int ShopsSaved;
        private Object ToNotify;
        private final ArrayBlockingQueue<HiredMerchant> Queue;
        
        private HiredMerchantSaveRunnable() {
            this.ThreadID = HiredMerchantSaveRunnable.RunningThreadID.incrementAndGet();
            this.TimeTaken = 0L;
            this.ShopsSaved = 0;
            this.Queue = (ArrayBlockingQueue<HiredMerchant>)new ArrayBlockingQueue(500);
        }
        
        @Override
        public void run() {
            try {
                while (!this.Queue.isEmpty()) {
                    final HiredMerchant next = (HiredMerchant)this.Queue.take();
                    final long Start = System.currentTimeMillis();
                    if (next.getMCOwner() != null && next.getMCOwner().getPlayerShop() == next) {
                        next.getMCOwner().setPlayerShop(null);
                    }
                    next.closeShop(true, false);
                    this.TimeTaken += System.currentTimeMillis() - Start;
                    ++this.ShopsSaved;
                }
                System.out.println("[HiredMerchantSave Thread " + this.ThreadID + "] Shops Saved: " + this.ShopsSaved + " | Time Taken: " + this.TimeTaken + " Milliseconds");
                synchronized (this.ToNotify) {
                    this.ToNotify.notify();
                }
            }
            catch (InterruptedException ex) {
                Logger.getLogger(HiredMerchantSave.class.getName()).log(Level.SEVERE, null, (Throwable)ex);
            }
        }
        
        private void Queue(final HiredMerchant hm) {
            this.Queue.add(hm);
        }
        
        private void SetToNotify(final Object o) {
            if (this.ToNotify == null) {
                this.ToNotify = o;
            }
        }
    }
    
    private static class TimingThread extends Thread
    {
        private final HiredMerchantSaveRunnable ext;
        
        public TimingThread(final HiredMerchantSaveRunnable r) {
            super((Runnable)r);
            this.ext = r;
        }
        
        public HiredMerchantSaveRunnable getRunnable() {
            return this.ext;
        }
    }
}
