package tools;

import java.util.Iterator;
import server.MapleItemInformationProvider;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import database.DBConPool;
import java.util.ArrayList;
import java.util.List;

public class FixShopItemsPrice
{
    private List<Integer> loadFromDB() {
        final List<Integer> shopItemsId = (List<Integer>)new ArrayList();
        try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection()) {
            final PreparedStatement ps = con.prepareStatement("SELECT itemid FROM shopitems ORDER BY itemid");
            final ResultSet rs = ps.executeQuery();
            int itemId = 0;
            while (rs.next()) {
                if (itemId != rs.getInt("itemid")) {
                    itemId = rs.getInt("itemid");
                    shopItemsId.add(Integer.valueOf(itemId));
                }
            }
            rs.close();
            ps.close();
            con.close();
        }
        catch (SQLException e) {
            System.err.println("无法载入商店");
            FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)e);
        }
        return shopItemsId;
    }
    
    private void changePrice(final int itemId) {
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection()) {
            final PreparedStatement ps = con.prepareStatement("SELECT shopid, price FROM shopitems WHERE itemid = ? ORDER BY price");
            ps.setInt(1, itemId);
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (ii.getPrice(itemId) != (double)rs.getLong("price") && rs.getLong("price") != 0L && ii.getPrice(itemId) > 1.0) {
                    System.out.println("道具: " + MapleItemInformationProvider.getInstance().getName(itemId) + "道具ID: " + itemId + " 商店: " + rs.getInt("shopid") + " 原价格: " + rs.getLong("price") + " 新价格:" + (long)ii.getPrice(itemId));
                    final PreparedStatement pp = con.prepareStatement("UPDATE shopitems SET price = ? WHERE itemid = ? AND shopid = ?");
                    pp.setLong(1, (long)ii.getPrice(itemId));
                    pp.setInt(2, itemId);
                    pp.setInt(3, rs.getInt("shopid"));
                    pp.execute();
                    pp.close();
                }
            }
            rs.close();
            ps.close();
            con.close();
        }
        catch (SQLException e) {
            System.out.println("处理商品失败, 道具ID:" + itemId);
            FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)e);
        }
    }
    
    public static void main(final String[] args) {
        System.setProperty("wzpath", System.getProperty("wzpath"));
        final FixShopItemsPrice i = new FixShopItemsPrice();
        System.out.println("正在加载道具数据......");
        MapleItemInformationProvider.getInstance().load();
        System.out.println("正在读取商店所有商品......");
        final List<Integer> list = i.loadFromDB();
        System.out.println("正在处理商店商品价格......");
        final Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            final int ii = ((Integer)iterator.next()).intValue();
            i.changePrice(ii);
        }
        System.out.println("处理商店商品价格异常结束。");
    }
}
