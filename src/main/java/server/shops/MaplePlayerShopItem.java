package server.shops;

import client.inventory.Item;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import client.inventory.IItem;

public class MaplePlayerShopItem
{
    public IItem item;
    public short bundles;
    public int price;
    public int lx;
    public int jyhid;
    private ReentrantLock lock;
    
    public Lock getLock() {
        return this.lock;
    }
    
    public MaplePlayerShopItem(final IItem item, final short bundles, final int price) {
        this.lx = 0;
        this.jyhid = -1;
        this.lock = new ReentrantLock();
        this.item = item;
        this.bundles = bundles;
        this.price = price;
    }
    
    public MaplePlayerShopItem(final Item item, final short bundles, final int price, final int 类型, final int 交易行id) {
        this.lx = 0;
        this.jyhid = -1;
        this.lock = new ReentrantLock();
        this.item = item;
        this.bundles = bundles;
        this.price = price;
        this.lx = 类型;
        this.jyhid = 交易行id;
    }
}
