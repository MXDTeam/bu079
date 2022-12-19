package handling.channel.handler;

import java.util.List;
import java.util.Map;
import tools.Pair;
import java.util.ArrayList;
import client.inventory.ItemLoader;
import client.inventory.MapleInventoryType;
import constants.GameConstants;
import client.MapleCharacter;
import java.util.Iterator;
import server.MerchItemPackage;
import constants.ServerConfig;
import server.MapleInventoryManipulator;
import client.inventory.IItem;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import tools.FileoutputUtil;
import database.DBConPool;
import tools.packet.PlayerShopPacket;
import constants.WorldConstants;
import handling.world.World;
import server.maps.MapleMap;
import gui.Start;
import client.MapleClient;
import tools.data.LittleEndianAccessor;

public class HiredMerchantHandler
{
    public static final void UseHiredMerchant(final LittleEndianAccessor slea, final MapleClient c) {
        if (c == null || c.getPlayer() == null || c.getPlayer().getMap() == null) {
            if (c != null && c.getPlayer() != null) {
                c.getPlayer().dropMessage("发生未知的错误，请稍后再试。");
            }
            return;
        }
        if (((Integer)Start.ConfigValuesMap.get("雇佣商人")).intValue() < 1) {
            c.getPlayer().dropMessage(1, "管理员已经从后台关闭了雇佣商人功能");
            return;
        }
        if (c.getPlayer().getMap().allowPersonalShop()) {
            final byte state = checkExistance(c.getPlayer().getAccountID(), c.getPlayer().getId());
            final int HMCH = MapleMap.getMerchantChannel(c.getPlayer());
            switch (state) {
                case 1: {
                    c.getPlayer().dropMessage(1, "请先去找弗兰德里领取你之前摆的東西");
                    break;
                }
                case 0: {
                    final boolean merch = World.hasMerchant(c.getPlayer().getAccountID());
                    if (merch) {
                        c.getPlayer().dropMessage(1, "请先关闭在" + HMCH + "频的精灵商人。");
                        break;
                    }
                    if (slea.available() <= 0L) {
                        break;
                    }
                    if (!WorldConstants.JZSD) {
                        if (c.getPlayer().getBossLog("今日摆摊") <= 0) {
                            c.getPlayer().setBossLog("今日摆摊");
                        }
                        c.sendPacket(PlayerShopPacket.sendTitleBox());
                        break;
                    }
                    c.getPlayer().dropMessage(1, "当前服务器无法使用精灵商人。");
                    break;
                }
                default: {
                    c.getPlayer().dropMessage(1, "发生了未知的错误.");
                    break;
                }
            }
        }
        else {
            c.getSession().close();
        }
    }
    
    private static byte checkExistance(final int accid, final int charid) {
        try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
             final PreparedStatement ps = con.prepareStatement("SELECT * from hiredmerch where accountid = ? OR characterid = ?")) {
            ps.setInt(1, accid);
            ps.setInt(2, charid);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rs.close();
                    ps.close();
                    con.close();
                    return 1;
                }
                rs.close();
            }
            ps.close();
            con.close();
            return 0;
        }
        catch (SQLException se) {
            FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)se);
            return -1;
        }
    }
    
    public static final void MerchantItemStore(final LittleEndianAccessor slea, final MapleClient c) {
        if (c.getPlayer() == null) {
            return;
        }
        final byte operation = slea.readByte();
        switch (operation) {
            case 20: {
                final String _2ndpw = slea.readMapleAsciiString();
                final int conv = c.getPlayer().getConversation();
                final int HMMap = MapleMap.getMerchantMap(c.getPlayer());
                final int HMChannel = MapleMap.getMerchantChannel(c.getPlayer());
                final boolean merch = World.hasMerchant(c.getPlayer().getAccountID());
                if (merch) {
                    c.sendPacket(PlayerShopPacket.ShowMerchItemStore(9030000, HMMap, HMChannel));
                    c.getPlayer().setConversation(0);
                    break;
                }
                if (conv == 3) {
                    final MerchItemPackage pack = loadItemFromDatabase(c.getPlayer().getId(), c.getPlayer().getAccountID());
                    if (pack == null) {
                        c.sendPacket(PlayerShopPacket.merchItemStore((byte)37));
                        c.getPlayer().setConversation(0);
                    }
                    else if (c.getPlayer().getMeso() + pack.getMesos() >= Integer.MAX_VALUE) {
                        c.getPlayer().dropMessage(1, "您的钱领取过后将会过多，请先将多余的钱放置仓库!");
                        c.getPlayer().setConversation(0);
                    }
                    else if (pack.getItems().size() <= 0) {
                        if (!check(c.getPlayer(), pack)) {
                            c.sendPacket(PlayerShopPacket.merchItem_Message((byte)33));
                            return;
                        }
                        for (final IItem item : pack.getItems()) {
                            MapleInventoryManipulator.addFromDrop(c, item, true);
                        }
                        if (deletePackage(c.getPlayer().getId(), c.getPlayer().getAccountID(), pack.getPackageid())) {
                            c.getPlayer().gainMeso(pack.getMesos(), true);
                            c.getPlayer().dropMessage(1, "你已经从精灵商人领取了" + pack.getMesos() + "金币");
                            c.getPlayer().setConversation(0);
                        }
                        else {
                            c.getPlayer().dropMessage(1, "发生未知的错误.");
                        }
                        String output = "";
                        for (final IItem item2 : pack.getItems()) {
                            output = output + item2.getItemId() + "(" + (int)item2.getQuantity() + "), ";
                        }
                        if (ServerConfig.LOG_MRECHANT) {
                            FileoutputUtil.logToFile("logs/Data/精灵商人领回.txt", FileoutputUtil.NowTime() + "账号角色名字:" + c.getAccountName() + " " + c.getPlayer().getName() + " 从精灵商人取回金币: " + pack.getMesos() + " 和" + pack.getItems().size() + "件物品[" + output + "]\r\n");
                        }
                        c.getPlayer().setConversation(0);
                    }
                    else {
                        c.sendPacket(PlayerShopPacket.merchItemStore_ItemData(pack));
                    }
                    break;
                }
                break;
            }
            case 25: {
                if (c.getPlayer().getConversation() != 3) {
                    return;
                }
                c.sendPacket(PlayerShopPacket.merchItemStore((byte)36));
                break;
            }
            case 26: {
                if (c.getPlayer().getConversation() != 3) {
                    return;
                }
                final MerchItemPackage pack2 = loadItemFromDatabase(c.getPlayer().getId(), c.getPlayer().getAccountID());
                if (pack2 == null) {
                    c.getPlayer().dropMessage(1, "未知的错误.");
                    return;
                }
                if (!check(c.getPlayer(), pack2)) {
                    c.sendPacket(PlayerShopPacket.merchItem_Message((byte)33));
                    return;
                }
                if (deletePackage(c.getPlayer().getId(), c.getPlayer().getAccountID(), pack2.getPackageid())) {
                    String output2 = "";
                    c.getPlayer().gainMeso(pack2.getMesos(), true);
                    for (final IItem item3 : pack2.getItems()) {
                        MapleInventoryManipulator.addFromDrop(c, item3, true);
                        output2 = output2 + item3.getItemId() + "(" + (int)item3.getQuantity() + "), ";
                    }
                    c.sendPacket(PlayerShopPacket.merchItem_Message((byte)29));
                    if (ServerConfig.LOG_MRECHANT) {
                        FileoutputUtil.logToFile("logs/Data/精灵商人领回.txt", FileoutputUtil.NowTime() + "账号角色名字:" + c.getAccountName() + " " + c.getPlayer().getName() + " 从精灵商人取回金币: " + pack2.getMesos() + " 和" + pack2.getItems().size() + "件物品[" + output2 + "]\r\n");
                    }
                    c.getPlayer().setConversation(0);
                    break;
                }
                c.getPlayer().dropMessage(1, "发生未知的错误.");
                break;
            }
            case 27: {
                c.getPlayer().setConversation(0);
                break;
            }
        }
    }
    
    private static final boolean check(final MapleCharacter chr, final MerchItemPackage pack) {
        if (chr.getMeso() + pack.getMesos() < 0) {
            return false;
        }
        byte eq = 0;
        byte use = 0;
        byte setup = 0;
        byte etc = 0;
        byte cash = 0;
        for (final IItem item : pack.getItems()) {
            final MapleInventoryType invtype = GameConstants.getInventoryType(item.getItemId());
            if (invtype == MapleInventoryType.EQUIP) {
                ++eq;
            }
            else if (invtype == MapleInventoryType.USE) {
                ++use;
            }
            else if (invtype == MapleInventoryType.SETUP) {
                ++setup;
            }
            else if (invtype == MapleInventoryType.ETC) {
                ++etc;
            }
            else {
                if (invtype != MapleInventoryType.CASH) {
                    continue;
                }
                ++cash;
            }
        }
        boolean slot = true;
        if (chr.getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() <= eq && eq != 0) {
            slot = false;
        }
        if (chr.getInventory(MapleInventoryType.USE).getNumFreeSlot() <= use && use != 0) {
            slot = false;
        }
        if (chr.getInventory(MapleInventoryType.SETUP).getNumFreeSlot() <= setup && setup != 0) {
            slot = false;
        }
        if (chr.getInventory(MapleInventoryType.ETC).getNumFreeSlot() <= etc && etc != 0) {
            slot = false;
        }
        if (chr.getInventory(MapleInventoryType.CASH).getNumFreeSlot() <= cash && cash != 0) {
            slot = false;
        }
        return slot;
    }
    
    private static boolean deletePackage(final int charid, final int accid, final int packageid) {
        try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
             final PreparedStatement ps = con.prepareStatement("DELETE from hiredmerch where characterid = ? OR accountid = ? OR packageid = ?")) {
            ps.setInt(1, charid);
            ps.setInt(2, accid);
            ps.setInt(3, packageid);
            ps.execute();
            ItemLoader.HIRED_MERCHANT.saveItems(null, Integer.valueOf(packageid));
            ps.close();
            con.close();
            return true;
        }
        catch (SQLException e) {
            FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)e);
            return false;
        }
    }
    
    private static MerchItemPackage loadItemFromDatabase(final int charid, final int accountid) {
        try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
             final PreparedStatement ps = con.prepareStatement("SELECT * from hiredmerch where characterid = ? OR accountid = ?")) {
            ps.setInt(1, charid);
            ps.setInt(2, accountid);
            final ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                ps.close();
                rs.close();
                return null;
            }
            final int packageid = rs.getInt("PackageId");
            final MerchItemPackage pack = new MerchItemPackage();
            pack.setPackageid(packageid);
            pack.setMesos(rs.getInt("Mesos"));
            pack.setSentTime(rs.getLong("time"));
            ps.close();
            rs.close();
            final Map<Long, Pair<IItem, MapleInventoryType>> items = ItemLoader.HIRED_MERCHANT.loadItems(false, Integer.valueOf(packageid), Integer.valueOf(accountid));
            if (items != null) {
                final List<IItem> iters = (List<IItem>)new ArrayList();
                for (final Pair<IItem, MapleInventoryType> z : items.values()) {
                    iters.add(z.left);
                }
                pack.setItems(iters);
            }
            con.close();
            return pack;
        }
        catch (SQLException e) {
            e.printStackTrace();
            FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)e);
            return null;
        }
    }
    
    public static void displayMerch(final MapleClient c) {
    }
}
