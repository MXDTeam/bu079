package client.inventory;

import java.util.ListIterator;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.util.Map;
import java.sql.SQLException;
import tools.FileoutputUtil;
import handling.world.World.Broadcast;
import tools.MaplePacketCreator;
import server.MapleItemInformationProvider;
import tools.Quadra;
import database.DBConPool;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.ArrayList;
import server.Timer.WorldTimer;
import tools.Triple;
import java.util.List;

public class OnlyID{
    private static final OnlyID instance = new OnlyID();
    private static List<Triple<Integer, Long, Long>> OnlyIDList = (List<Triple<Integer, Long, Long>>)new ArrayList();
    
    public static OnlyID getInstance() {
        return OnlyID.instance;
    }
    
    protected OnlyID() {
        WorldTimer.getInstance().register((Runnable)new run(), 43200000L);
    }
    
    public static List<Triple<Integer, Long, Long>> getData() {
        return OnlyID.OnlyIDList;
    }
    
    public static void addData(final int chrid, final long inventoryitemid, final long ItemOnlyID) {
        OnlyID.OnlyIDList.add(new Triple(Integer.valueOf(chrid), Long.valueOf(inventoryitemid), Long.valueOf(ItemOnlyID)));
    }
    
    public static void removeData(final int chrid) {
        OnlyID.OnlyIDList.remove(chrid);
    }
    
    public static void clearData() {
        if (!OnlyID.OnlyIDList.isEmpty()) {
            OnlyID.OnlyIDList.clear();
        }
    }
    
    public void StartCheckings() {
        StartChecking();
    }
    
    public static void StartChecking() {
        if (!OnlyID.OnlyIDList.isEmpty()) {
            OnlyID.OnlyIDList.clear();
        }
        StringBuilder chrs = new StringBuilder();
        StringBuilder Sql = new StringBuilder();
        final List<Quadra<Integer, Integer, Long, Integer>> equipOnlyIds = (List<Quadra<Integer, Integer, Long, Integer>>)new ArrayList();
        final Map checkItems = new HashMap();
        final List<Integer> all = (List<Integer>)new LinkedList();
        final List<Integer> gm = (List<Integer>)new LinkedList();
        try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT characterid FROM inventoryitems WHERE equipOnlyId > 0");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                final int chr = rs.getInt("characterid");
                if (chr != 0) {
                    all.add(Integer.valueOf(chr));
                }
            }
            ps.close();
            rs.close();
            Sql = new StringBuilder();
            if (!all.isEmpty()) {
                Sql.append("and (id = ");
                for (int i = 0; i < all.size(); ++i) {
                    Sql.append(all.get(i));
                    if (i < all.size() - 1) {
                        Sql.append(" OR id = ");
                    }
                }
                Sql.append(")");
            }
            ps = con.prepareStatement("SELECT id FROM characters WHERE gm > 0 " + Sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                final int chr = rs.getInt("id");
                if (chr != 0) {
                    gm.add(Integer.valueOf(chr));
                }
            }
            ps.close();
            rs.close();
            Sql = new StringBuilder();
            if (!gm.isEmpty()) {
                Sql.append("and characterid != ");
                for (int i = 0; i < gm.size(); ++i) {
                    Sql.append(gm.get(i));
                    if (i < gm.size() - 1) {
                        Sql.append(" and characterid != ");
                    }
                }
            }
            ps = con.prepareStatement("SELECT * FROM inventoryitems WHERE equipOnlyId > 0 " + Sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                final int chr = rs.getInt("characterid");
                final int ac = rs.getInt("accountid");
                final int itemId = rs.getInt("itemId");
                final long equipOnlyId = rs.getLong("equipOnlyId");
                final long inventoryitemid = rs.getLong("inventoryitemid");
                if (equipOnlyId > 0L) {
                    if (checkItems.containsKey(Long.valueOf(equipOnlyId))) {
                        if (((Integer)checkItems.get(Long.valueOf(equipOnlyId))).intValue() != itemId) {
                            continue;
                        }
                        equipOnlyIds.add(new Quadra(Integer.valueOf(chr), Integer.valueOf(ac), Long.valueOf(equipOnlyId), Integer.valueOf(itemId)));
                        addData(chr, inventoryitemid, equipOnlyId);
                    }
                    else {
                        checkItems.put(Long.valueOf(equipOnlyId), Integer.valueOf(itemId));
                    }
                }
            }
            rs.close();
            ps.close();
            final ListIterator<Quadra<Integer, Integer, Long, Integer>> OnlyId = equipOnlyIds.listIterator();
            while (OnlyId.hasNext()) {
                chrs = new StringBuilder();
                final Quadra<Integer, Integer, Long, Integer> Only = OnlyId.next();
                final long itemonly = ((Long)Only.getThird()).longValue();
                final int item = ((Integer)Only.getForth()).intValue();
                ps = con.prepareStatement("SELECT characterid FROM inventoryitems WHERE equipOnlyId = " + itemonly);
                rs = ps.executeQuery();
                while (rs.next()) {
                    final int chr2 = rs.getInt("characterid");
                    if (chr2 != 0) {
                        chrs.append("角色 [").append(chr2).append("]");
                    }
                }
                rs.close();
                ps.close();
                String itemname = "null";
                itemname = MapleItemInformationProvider.getInstance().getName(item);
                final String msg = "发现复制,唯一ID [" + itemonly + "] " + chrs.toString() + " 物品[" + itemname + "](" + item + ")";
                System.out.println(msg);
                Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, msg));
                FileoutputUtil.logToFile("Hack/复制装备.txt", FileoutputUtil.CurrentReadable_TimeGMT() + " " + msg + "\r\n");
            }
            con.close();
        }
        catch (SQLException ex) {
            System.out.println("[EXCEPTION] 复制装备出现错误." + ex);
            FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)ex);
        }
    }
    
    public static class run implements Runnable{
        @Override
        public void run() {
            OnlyID.StartChecking();
        }
    }
}
