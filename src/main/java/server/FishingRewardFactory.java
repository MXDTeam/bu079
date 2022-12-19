package server;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import database.DBConPool;
import java.util.Iterator;
import java.util.LinkedList;
import tools.FileoutputUtil;
import java.util.Random;
import tools.Pair;
import java.util.List;

public class FishingRewardFactory
{
    private final List<Pair<Long, FishingReward>> rewards;
    private Long total;
    private final Random rand;
    private final int[] typesChance;
    private final int[] typesChanceAcc;
    private final int typesChanceTotal = 100;
    private static final FishingRewardFactory instance = new FishingRewardFactory();
    
    public FishingRewardFactory() {
        this.total = Long.valueOf(0L);
        this.typesChance = new int[] { 40, 40, 20 };
        this.typesChanceAcc = new int[] { 40, 80, 100 };
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:加载钓鱼获取物品信息");
        this.rewards = (List<Pair<Long, FishingReward>>)new LinkedList();
        this.rand = new Random(System.currentTimeMillis());
        this.loadItems();
    }
    
    public static FishingRewardFactory getInstance() {
        return FishingRewardFactory.instance;
    }
    
    public int getNextRewardType() {
        final Random rand = this.rand;
        this.getClass();
        final Integer n = Integer.valueOf(rand.nextInt(100));
        for (int i = 0; i < 3; ++i) {
            if (n.intValue() <= this.typesChanceAcc[i]) {
                return i;
            }
        }
        return 0;
    }
    
    public FishingReward getNextRewardItemId() {
        if (this.rewards.isEmpty()) {
            this.loadItems();
        }
        final Iterator<Pair<Long, FishingReward>> iterator = this.rewards.iterator();
        if (this.total.longValue() != 0L) {
            final Long n = Long.valueOf(Math.abs(this.rand.nextLong() * System.currentTimeMillis() + 47L * System.currentTimeMillis()) % this.total.longValue());
            while (iterator.hasNext()) {
                final Pair<Long, FishingReward> c = iterator.next();
                if (n.longValue() <= ((Long)c.left).longValue()) {
                    return (FishingReward)c.right;
                }
            }
        }
        return null;
    }
    
    public void reloadItems() {
        this.loadItems();
    }
    
    private void loadItems() {
        this.rewards.clear();
        Long acc = Long.valueOf(0L);
        try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection()) {
            try (final PreparedStatement ps = con.prepareStatement("SELECT * FROM fishing_rewards ORDER BY chance ASC");
                 final ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    final int itemid = rs.getInt("itemid");
                    final int chance = rs.getInt("chance");
                    final int expirtaion = rs.getInt("expiration");
                    acc = Long.valueOf(acc.longValue() + (long)chance);
                    this.rewards.add(new Pair(acc, new FishingReward(itemid, expirtaion)));
                }
                rs.close();
                ps.close();
            }
            con.close();
        }
        catch (SQLException e) {
            FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)e);
        }
        this.total = acc;
    }
    
    public class FishingReward
    {
        private final int itemid;
        private final int expiration;
        
        public FishingReward(final int itemid, final int expiration) {
            this.expiration = expiration;
            this.itemid = itemid;
        }
        
        public int getItemId() {
            return this.itemid;
        }
        
        public int getExpiration() {
            return this.expiration;
        }
    }
}
