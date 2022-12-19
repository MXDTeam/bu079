package tools.wztosql;

import provider.MapleDataProviderFactory;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Collection;
import java.sql.SQLException;
import client.inventory.MapleInventoryType;
import java.util.Map;
import provider.MapleDataTool;
import provider.MapleData;
import java.util.HashMap;
import java.util.ArrayList;
import server.CashItemFactory;
import server.MapleItemInformationProvider;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import tools.FileoutputUtil;
import database.DBConPool;
import server.CashItemInfo.CashModInfo;
import provider.MapleDataProvider;

public class DumpCashShop
{
    private static final MapleDataProvider data = MapleDataProviderFactory.getDataProvider(new File(((System.getProperty("wzpath") != null) ? System.getProperty("wzpath") : "") + "wz/Etc.wz"));
    
    public static final CashModInfo getModInfo(final int sn) {
        CashModInfo ret = null;
        try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
             final PreparedStatement ps = con.prepareStatement("SELECT * FROM cashshop_modified_items WHERE serial = ?")) {
            ps.setInt(1, sn);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ret = new CashModInfo(sn, rs.getInt("discount_price"), rs.getInt("mark"), rs.getInt("showup") > 0, rs.getInt("itemid"), rs.getInt("priority"), rs.getInt("package") > 0, rs.getInt("period"), rs.getInt("gender"), rs.getInt("count"), rs.getInt("meso"), rs.getInt("unk_1"), rs.getInt("unk_2"), rs.getInt("unk_3"), rs.getInt("extra_flags"), rs.getInt("type"));
                }
                rs.close();
            }
            ps.close();
            con.close();
        }
        catch (Exception ex) {
            FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)ex);
        }
        return ret;
    }
    
    public static void main(final String[] args) {
        MapleItemInformationProvider.getInstance().load();
        final CashModInfo m = getModInfo(20000393);
        CashItemFactory.getInstance().initialize();
        final Collection<CashModInfo> list = CashItemFactory.getInstance().getAllModInfo();
        try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection()) {
            final List<Integer> itemids = (List<Integer>)new ArrayList();
            final List<Integer> qq = (List<Integer>)new ArrayList();
            final Map<Integer, Map<String, Integer>> itemConmes = (Map<Integer, Map<String, Integer>>)new HashMap();
            for (final MapleData field : DumpCashShop.data.getData("Commodity.img").getChildren()) {
                final Map<String, Integer> itemConme = (Map<String, Integer>)new HashMap();
                final int itemId = MapleDataTool.getIntConvert("ItemId", field, 0);
                final int sn = MapleDataTool.getIntConvert("SN", field, 0);
                final int count = MapleDataTool.getIntConvert("Count", field, 0);
                final int price = MapleDataTool.getIntConvert("Price", field, 0);
                final int priority = MapleDataTool.getIntConvert("Priority", field, 0);
                final int period = MapleDataTool.getIntConvert("Period", field, 0);
                final int gender = MapleDataTool.getIntConvert("Gender", field, -1);
                final int meso = MapleDataTool.getIntConvert("Meso", field, 0);
                if (price == 0) {
                    continue;
                }
                if ((sn / 10000000 == 2 || sn / 10000000 == 3 || sn / 10000000 == 5 || sn / 10000000 == 6 || sn / 10000000 == 7) && itemConmes.containsKey(Integer.valueOf(itemId)) && ((Integer)(itemConmes.get(Integer.valueOf(itemId))).get("price")).intValue() > price) {
                    continue;
                }
                itemConme.put("itemId", Integer.valueOf(itemId));
                itemConme.put("sn", Integer.valueOf(sn));
                itemConme.put("count", Integer.valueOf(count));
                itemConme.put("price", Integer.valueOf(price));
                itemConme.put("priority", Integer.valueOf(priority));
                itemConme.put("period", Integer.valueOf(period));
                itemConme.put("gender", Integer.valueOf(gender));
                itemConme.put("meso", Integer.valueOf(meso));
                itemConmes.put(Integer.valueOf(itemId), itemConme);
            }
            final Map<Integer, List<String>> dics = (Map<Integer, List<String>>)new HashMap();
            for (final Map<String, Integer> field2 : itemConmes.values()) {
                try {
                    final int itemId = ((Integer)field2.get("itemId")).intValue();
                    final int sn = ((Integer)field2.get("sn")).intValue();
                    final int count = ((Integer)field2.get("count")).intValue();
                    final int price = ((Integer)field2.get("price")).intValue();
                    final int priority = ((Integer)field2.get("priority")).intValue();
                    final int period = ((Integer)field2.get("period")).intValue();
                    final int gender = ((Integer)field2.get("gender")).intValue();
                    final int meso = ((Integer)field2.get("meso")).intValue();
                    if (itemId == 0) {
                        continue;
                    }
                    if (sn > 80000000) {
                        continue;
                    }
                    if (sn / 100000 == 102) {
                        continue;
                    }
                    if (sn / 100000 == 101) {
                        continue;
                    }
                    if (sn / 100000 == 100) {
                        continue;
                    }
                    if (sn / 100000 == 700) {
                        continue;
                    }
                    final int cat = itemId / 10000;
                    if (dics.get(Integer.valueOf(cat)) == null) {
                        dics.put(Integer.valueOf(cat), new ArrayList());
                    }
                    boolean check = false;
                    if (meso > 0) {
                        check = true;
                    }
                    if (MapleItemInformationProvider.getInstance().getInventoryTypeCS(itemId) == MapleInventoryType.EQUIP && !MapleItemInformationProvider.getInstance().isCashItem(itemId)) {
                        check = true;
                    }
                    if (MapleItemInformationProvider.getInstance().getInventoryTypeCS(itemId) == MapleInventoryType.EQUIP && period > 0) {
                        check = true;
                    }
                    if (check) {
                        System.out.println(MapleItemInformationProvider.getInstance().getName(itemId));
                    }
                    else {
                        final PreparedStatement ps = con.prepareStatement("INSERT INTO cashshop_modified_items (serial, showup,itemid,priority,period,gender,count,meso,discount_price,mark, unk_1, unk_2, unk_3, name) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                        ps.setInt(1, sn);
                        ps.setInt(2, 1);
                        ps.setInt(3, itemId);
                        ps.setInt(4, 0);
                        ps.setInt(5, 0);
                        ps.setInt(6, gender);
                        ps.setInt(7, (count > 1) ? count : 0);
                        ps.setInt(8, meso);
                        ps.setInt(9, price);
                        qq.add(Integer.valueOf(itemId));
                        ps.setInt(10, 0);
                        ps.setInt(11, 0);
                        ps.setInt(12, 0);
                        ps.setInt(13, 0);
                        ps.setString(14, MapleItemInformationProvider.getInstance().getName(itemId));
                        ps.executeUpdate();
                        ps.toString();
                        ps.close();
                    }
                }
                catch (SQLException ex) {
                    FileoutputUtil.outError("logs/CashShopDumper.txt", (Throwable)ex);
                }
            }
            con.close();
        }
        catch (SQLException e) {
            FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)e);
        }
    }
}
