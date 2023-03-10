package server;

import java.util.HashMap;
import java.util.Map;

public class MapleShopFactory
{
    private Map<Integer, MapleShop> shops;
    private Map<Integer, MapleShop> npcShops;
    private static MapleShopFactory instance = new MapleShopFactory();
    
    public MapleShopFactory() {
        this.shops = (Map<Integer, MapleShop>)new HashMap();
        this.npcShops = (Map<Integer, MapleShop>)new HashMap();
    }
    
    public static MapleShopFactory getInstance() {
        return MapleShopFactory.instance;
    }
    
    public void clear() {
        this.shops.clear();
        this.npcShops.clear();
    }
    
    public MapleShop getShop(final int shopId) {
        if (this.shops.containsKey(Integer.valueOf(shopId))) {
            return (MapleShop)this.shops.get(Integer.valueOf(shopId));
        }
        return this.loadShop(shopId, true);
    }
    
    public MapleShop getShopForNPC(final int npcId) {
        if (this.npcShops.containsKey(Integer.valueOf(npcId))) {
            return (MapleShop)this.npcShops.get(Integer.valueOf(npcId));
        }
        return this.loadShop(npcId, false);
    }
    
    private MapleShop loadShop(final int id, final boolean isShopId) {
        final MapleShop ret = MapleShop.createFromDB(id, isShopId);
        if (ret != null) {
            this.shops.put(Integer.valueOf(ret.getId()), ret);
            this.npcShops.put(Integer.valueOf(ret.getNpcId()), ret);
        }
        else if (isShopId) {
            this.shops.put(Integer.valueOf(id), null);
        }
        else {
            this.npcShops.put(Integer.valueOf(id), null);
        }
        return ret;
    }
}
