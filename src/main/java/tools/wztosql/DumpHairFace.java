package tools.wztosql;

import java.util.HashMap;
import java.util.Iterator;
import java.sql.PreparedStatement;
import java.sql.Connection;
import provider.MapleDataProvider;
import tools.FileoutputUtil;
import provider.MapleDataTool;
import tools.StringUtil;
import provider.MapleData;
import database.DBConPool;
import provider.MapleDataProviderFactory;
import java.io.File;
import java.sql.SQLException;
import java.util.Map;

public class DumpHairFace
{
    private static final Map<Integer, String> chrNames = new HashMap();
    
    public static void main(final String[] args) throws SQLException {
        final DumpHairFace dump = new DumpHairFace();
        System.out.println("HairMySQL......");
        dump.dumpHairFaceData("Hair");
        System.out.println("FaceMySQL......");
        dump.dumpHairFaceData("Face");
        System.out.println("结束。");
    }
    
    public void dumpHairFaceData(final String type) throws SQLException {
        final File dataFile = new File(((System.getProperty("path") != null) ? System.getProperty("path") : "") + "wz/Character.wz/" + type);
        final File strDataFile = new File(((System.getProperty("path") != null) ? System.getProperty("path") : "") + "wz/String.wz");
        final MapleDataProvider chrData = MapleDataProviderFactory.getDataProvider(dataFile);
        final MapleDataProvider stringDataWZ = MapleDataProviderFactory.getDataProvider(strDataFile);
        final MapleData chrStringData = stringDataWZ.getData("Eqp.img").getChildByPath("Eqp/" + type);
        try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection()) {
            try (final PreparedStatement ps = con.prepareStatement("DELETE FROM `wz_" + type.toLowerCase() + "data`")) {
                ps.execute();
                ps.close();
            }
            for (final MapleData c : chrStringData) {
                final int chrid = Integer.parseInt(c.getName());
                final String n = StringUtil.getLeftPaddedStr(chrid + ".img", '0', 12);
                try {
                    if (chrData.getData(n) == null) {
                        continue;
                    }
                    final String name = MapleDataTool.getString("name", c, "无");
                    DumpHairFace.chrNames.put(Integer.valueOf(chrid), name);
                }
                catch (NullPointerException e) {
                    FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)e);
                }
                catch (RuntimeException e2) {
                    FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)e2);
                }
            }
            final Iterator<Integer> iterator2 = DumpHairFace.chrNames.keySet().iterator();
            while (iterator2.hasNext()) {
                final int key = ((Integer)iterator2.next()).intValue();
                try {
                    try (final PreparedStatement ps2 = con.prepareStatement("INSERT INTO `wz_" + type.toLowerCase() + "data` (`" + type.toLowerCase() + "id`, `name`) VALUES (?, ?)")) {
                        ps2.setInt(1, key);
                        ps2.setString(2, (String)DumpHairFace.chrNames.get(Integer.valueOf(key)));
                        ps2.execute();
                        ps2.close();
                    }
                    System.out.println("鍵值: " + key + " 名称: " + (String)DumpHairFace.chrNames.get(Integer.valueOf(key)));
                }
                catch (SQLException ex) {
                    FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)ex);
                    System.out.println("保存鍵值错误：" + key);
                }
            }
            DumpHairFace.chrNames.clear();
            con.close();
        }
        catch (SQLException es) {
            FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)es);
        }
    }
}
