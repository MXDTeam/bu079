package server;

import java.util.LinkedHashSet;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.ArrayList;
import database.DBConPool;
import java.util.Iterator;
import client.inventory.ModifyInventory;
import client.SkillFactory;
import client.inventory.IItem;
import constants.PiPiConfig;
import tools.FileoutputUtil;
import client.inventory.MapleInventoryType;
import client.inventory.MaplePet;
import client.inventory.MapleInventoryIdentifier;
import constants.GameConstants;
import server.life.MapleNPC;
import tools.MaplePacketCreator;
import server.life.MapleLifeFactory;
import client.MapleClient;
import java.util.LinkedList;
import gui.Start;
import java.util.List;
import java.util.Set;

public class MapleShop
{
    private static final Set<Integer> rechargeableItems;
    private final int id;
    private final int npcId;
    private final List<MapleShopItem> items;
    
    public static void 重载商店() {
        MapleShop.rechargeableItems.clear();
        for (int i = 2070000; i <= 2070013; ++i) {
            MapleShop.rechargeableItems.add(Integer.valueOf(i));
        }
        for (int i = 2330000; i <= 2330005; ++i) {
            MapleShop.rechargeableItems.add(Integer.valueOf(i));
        }
        if (((Integer)Start.ConfigValuesMap.get("子弹扩充开关")).intValue() > 0) {
            for (int a = 0; a < Start.子弹列表.size(); ++a) {
                MapleShop.rechargeableItems.add(Integer.valueOf(Integer.parseInt((String)Start.子弹列表.get(a))));
            }
        }
    }
    
    private MapleShop(final int id, final int npcId) {
        this.id = id;
        this.npcId = npcId;
        this.items = (List<MapleShopItem>)new LinkedList();
    }
    
    public void addItem(final MapleShopItem item) {
        this.items.add(item);
    }
    
    public void sendShop(final MapleClient c) {
        if (c != null && c.getPlayer() != null) {
            final MapleNPC npc = MapleLifeFactory.getNPC(this.getNpcId());
            if (npc == null || npc.getName().equals("MISSINGNO")) {
                c.getPlayer().dropMessage(1, "商店" + this.id + "找不到此代码为" + this.getNpcId() + "的Npc");
                return;
            }
            if (c.getPlayer().isAdmin()) {
                c.getPlayer().dropMessage(5, "您已建立与商店" + this.id + "的连接,NPC代码:" + this.getNpcId() + "");
                MapleShopFactory.getInstance().clear();
            }
            c.getPlayer().setShop(this);
            c.sendPacket(MaplePacketCreator.getNPCShop(c, this.getNpcId(), this.items));
        }
    }
    
    public void buy(final MapleClient c, final int itemId, short quantity) {
        if (quantity <= 0) {
            AutobanManager.getInstance().addPoints(c, 1000, 0L, "Buying " + (int)quantity + " " + itemId);
            return;
        }
        if (!GameConstants.isMountItemAvailable(itemId, (int)c.getPlayer().getJob())) {
            c.getPlayer().dropMessage(1, "你不可以买这道具。");
            c.sendPacket(MaplePacketCreator.enableActions());
            return;
        }
        final MapleShopItem item = this.findById(itemId);
        if (item != null && item.getPrice() > 0 && item.getReqItem() == 0) {
            final int price = GameConstants.isRechargable(itemId) ? item.getPrice() : (item.getPrice() * quantity);
            if (price >= 0 && c.getPlayer().getMeso() >= price) {
                if (MapleInventoryManipulator.checkSpace(c, itemId, (int)quantity, "")) {
                    c.getPlayer().gainMeso(-price, false);
                    if (GameConstants.isPet(itemId)) {
                        MapleInventoryManipulator.addById(c, itemId, quantity, "", MaplePet.createPet(itemId, MapleInventoryIdentifier.getInstance()), -1L);
                    }
                    else {
                        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
                        if (GameConstants.isRechargable(itemId)) {
                            quantity = ii.getSlotMax(c, item.getItemId());
                        }
                        MapleInventoryManipulator.addById(c, itemId, quantity);
                    }
                }
                else {
                    c.getPlayer().dropMessage(1, "你的道具栏满了。");
                }
                c.sendPacket(MaplePacketCreator.confirmShopTransaction((byte)0));
            }
        }
        else if (item != null && item.getReqItem() > 0 && item.getPrice() > 0) {
            if (item.getReqItem() == 1) {
                if (c.getPlayer().getCSPoints(1) < item.getReqItemQ() * quantity) {
                    c.getPlayer().dropMessage(1, "你的点卷数量不足:" + item.getReqItemQ() * quantity + "");
                    c.sendPacket(MaplePacketCreator.confirmShopTransaction((byte)0));
                    return;
                }
            }
            else if (item.getReqItem() == 2) {
                if (c.getPlayer().getCSPoints(2) < item.getReqItemQ() * quantity) {
                    c.getPlayer().dropMessage(1, "你的抵用数量不足:" + item.getReqItemQ() * quantity + "");
                    c.sendPacket(MaplePacketCreator.confirmShopTransaction((byte)0));
                    return;
                }
            }
            else if (item.getReqItem() == 3) {
                if (c.getPlayer().getCSPoints(3) < item.getReqItemQ() * quantity) {
                    c.getPlayer().dropMessage(1, "你的豆豆数量不足:" + item.getReqItemQ() * quantity + "");
                    c.sendPacket(MaplePacketCreator.confirmShopTransaction((byte)0));
                    return;
                }
            }
            else if (c.getPlayer().getItemQuantity(item.getReqItem(), false) < item.getReqItemQ() * quantity) {
                c.getPlayer().dropMessage(1, "你的道具:" + c.getPlayer().getItemName(item.getReqItem()) + "数量不足:" + item.getReqItemQ() * quantity + "");
                c.sendPacket(MaplePacketCreator.confirmShopTransaction((byte)0));
                return;
            }
            if (MapleInventoryManipulator.checkSpace(c, itemId, (int)quantity, "")) {
                if (item.getReqItem() == 1) {
                    c.getPlayer().modifyCSPoints(1, -item.getReqItemQ() * quantity, true);
                }
                else if (item.getReqItem() == 2) {
                    c.getPlayer().modifyCSPoints(2, -item.getReqItemQ() * quantity, true);
                }
                else if (item.getReqItem() == 3) {
                    c.getPlayer().modifyCSPoints(3, -item.getReqItemQ() * quantity, true);
                }
                else {
                    MapleInventoryManipulator.removeById(c, GameConstants.getInventoryType(item.getReqItem()), item.getReqItem(), item.getReqItemQ() * quantity, false, false);
                }
                if (GameConstants.isPet(itemId)) {
                    MapleInventoryManipulator.addById(c, itemId, quantity, "", MaplePet.createPet(itemId, MapleInventoryIdentifier.getInstance()), -1L);
                }
                else {
                    final MapleItemInformationProvider ii2 = MapleItemInformationProvider.getInstance();
                    if (GameConstants.isRechargable(itemId)) {
                        quantity = ii2.getSlotMax(c, item.getItemId());
                    }
                    MapleInventoryManipulator.addById(c, itemId, quantity);
                }
            }
            else {
                c.getPlayer().dropMessage(1, "你的道具栏满了。");
            }
            c.sendPacket(MaplePacketCreator.confirmShopTransaction((byte)0));
        }
    }
    
    public void sell(final MapleClient c, final MapleInventoryType type, final byte slot, short quantity) {
        if (quantity == 65535 || quantity == 0) {
            quantity = 1;
        }
        final IItem item = c.getPlayer().getInventory(type).getItem((short)slot);
        if (item == null) {
            return;
        }
        if (GameConstants.isThrowingStar(item.getItemId()) || GameConstants.isBullet(item.getItemId())) {
            quantity = item.getQuantity();
        }
        if (quantity < 0) {
            c.getPlayer().ban(c.getPlayer().getName() + "修改封包", true, true, false);
            c.getSession().close();
            FileoutputUtil.logToFile("logs/Hack/商店.txt", "\r\n" + FileoutputUtil.NowTime() + " 玩家：" + c.getPlayer().getName() + " 遭到封锁，原因： 卖出" + item.getItemId() + "的数量小于0(为" + (int)quantity + ")");
            return;
        }
        short iQuant = item.getQuantity();
        if (iQuant == 65535) {
            iQuant = 1;
        }
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        if (ii.cantSell(item.getItemId())) {
            return;
        }
        if (quantity <= iQuant && (iQuant > 0 || GameConstants.isRechargable(item.getItemId()))) {
            MapleInventoryManipulator.removeFromSlot(c, type, (short)slot, quantity, false);
            double price;
            if (GameConstants.isThrowingStar(item.getItemId()) || GameConstants.isBullet(item.getItemId())) {
                price = (double)ii.getWholePrice(item.getItemId()) / (double)ii.getSlotMax(c, item.getItemId());
            }
            else {
                price = ii.getPrice(item.getItemId());
            }
            if (item.getItemId() == 2022195) {
                price = 1.0;
            }
            if (item.getItemId() == 4031348) {
                price = 1.0;
            }
            int recvMesos = (int)Math.max(Math.ceil(price * (double)quantity), 0.0);
            if (price != -1.0 && recvMesos > 0) {
                if (recvMesos > PiPiConfig.商店一次拍卖获得最大金币) {
                    recvMesos = 1;
                }
                c.getPlayer().gainMeso(recvMesos, false);
            }
            c.sendPacket(MaplePacketCreator.confirmShopTransaction((byte)8));
        }
    }
    
    public void recharge(final MapleClient c, final byte slot) {
        final IItem item = c.getPlayer().getInventory(MapleInventoryType.USE).getItem((short)slot);
        if (item == null || (!GameConstants.isThrowingStar(item.getItemId()) && !GameConstants.isBullet(item.getItemId()))) {
            return;
        }
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        short slotMax = ii.getSlotMax(c, item.getItemId());
        final int skill = GameConstants.getMasterySkill((int)c.getPlayer().getJob());
        if (skill != 0) {
            slotMax += (short)(c.getPlayer().getSkillLevel(SkillFactory.getSkill(skill)) * 10);
        }
        if (item.getQuantity() < slotMax) {
            final int price = (int)Math.round(ii.getPrice(item.getItemId()) * (double)(slotMax - item.getQuantity()));
            if (c.getPlayer().getMeso() >= price) {
                item.setQuantity(slotMax);
                c.sendPacket(MaplePacketCreator.modifyInventory(false, new ModifyInventory(1, item)));
                c.getPlayer().gainMeso(-price, false, true, false);
                c.sendPacket(MaplePacketCreator.confirmShopTransaction((byte)8));
            }
        }
    }
    
    protected MapleShopItem findById(final int itemId) {
        for (final MapleShopItem item : this.items) {
            if (item.getItemId() == itemId) {
                return item;
            }
        }
        return null;
    }
    
    public static MapleShop createFromDB(final int id, final boolean isShopId) {
        MapleShop ret = null;
        try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement(isShopId ? "SELECT * FROM shops WHERE shopid = ?" : "SELECT * FROM shops WHERE npcid = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                rs.close();
                ps.close();
                return null;
            }
            final int shopId = rs.getInt("shopid");
            ret = new MapleShop(shopId, rs.getInt("npcid"));
            rs.close();
            ps.close();
            ps = con.prepareStatement("SELECT * FROM shopitems WHERE shopid = ? ORDER BY position ASC");
            ps.setInt(1, shopId);
            rs = ps.executeQuery();
            final List<Integer> recharges = new ArrayList(MapleShop.rechargeableItems);
            while (rs.next()) {
                if (GameConstants.isThrowingStar(rs.getInt("itemid")) || GameConstants.isBullet(rs.getInt("itemid"))) {
                    final MapleShopItem starItem = new MapleShopItem((short)1, rs.getInt("itemid"), rs.getInt("price"), rs.getInt("reqitem"), rs.getInt("reqitemq"));
                    ret.addItem(starItem);
                    if (!MapleShop.rechargeableItems.contains(Integer.valueOf(starItem.getItemId()))) {
                        continue;
                    }
                    recharges.remove(Integer.valueOf(starItem.getItemId()));
                }
                else {
                    ret.addItem(new MapleShopItem((short)1000, rs.getInt("itemid"), rs.getInt("price"), rs.getInt("reqitem"), rs.getInt("reqitemq")));
                }
            }
            for (final Integer recharge : recharges) {
                ret.addItem(new MapleShopItem((short)1000, recharge.intValue(), 0, 0, 0));
            }
            rs.close();
            ps.close();
            con.close();
        }
        catch (SQLException e) {
            System.err.println("Could not load shop" + e);
            FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)e);
        }
        return ret;
    }
    
    public int getNpcId() {
        return this.npcId;
    }
    
    public int getId() {
        return this.id;
    }
    
    static {
        rechargeableItems = new LinkedHashSet();
        for (int i = 2070000; i <= 2070013; ++i) {
            rechargeableItems.add(Integer.valueOf(i));
        }
        for (int i = 2330000; i <= 2330005; ++i) {
            rechargeableItems.add(Integer.valueOf(i));
        }
        if (((Integer)Start.ConfigValuesMap.get("子弹扩充开关")).intValue() > 0) {
            for (int a = 0; a < Start.子弹列表.size(); ++a) {
                rechargeableItems.add(Integer.valueOf(Integer.parseInt((String)Start.子弹列表.get(a))));
            }
        }
    }
}
