package client.anticheat;

import server.Timer.CheatTimer;
import java.util.concurrent.locks.ReentrantLock;
import java.util.LinkedHashSet;
import java.util.concurrent.locks.Lock;
import java.util.Set;

public class CheatingOffensePersister
{
    private static final CheatingOffensePersister instance = new CheatingOffensePersister();
    private final Set<CheatingOffenseEntry> toPersist;
    private final Lock mutex;
    
    private CheatingOffensePersister() {
        this.toPersist = (Set<CheatingOffenseEntry>)new LinkedHashSet();
        this.mutex = new ReentrantLock();
        CheatTimer.getInstance().register((Runnable)new PersistingTask(), 61000L);
    }
    
    public static CheatingOffensePersister getInstance() {
        return CheatingOffensePersister.instance;
    }
    
    public void persistEntry(final CheatingOffenseEntry coe) {
        this.mutex.lock();
        try {
            this.toPersist.remove(coe);
            this.toPersist.add(coe);
        }
        finally {
            this.mutex.unlock();
        }
    }
    
    public class PersistingTask implements Runnable{
        @Override
        public void run() {
            CheatingOffensePersister.this.mutex.lock();
            try {
                CheatingOffensePersister.this.toPersist.clear();
            }
            finally {
                CheatingOffensePersister.this.mutex.unlock();
            }
        }
    }
}
