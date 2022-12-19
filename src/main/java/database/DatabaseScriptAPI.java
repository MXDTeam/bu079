package database;

import java.sql.ResultSetMetaData;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class DatabaseScriptAPI
{
    public static List<Map<String, Object>> SQLSelect(final String sql, final Object... data) {
        final List<Map<String, Object>> ret = (List<Map<String, Object>>)new ArrayList();
        Connection con = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            try (final PreparedStatement ps = con.prepareStatement(sql)) {
                for (int i = 0; i < data.length; ++i) {
                    ps.setObject(i + 1, data[i]);
                }
                try (final ResultSet rs = ps.executeQuery()) {
                    final ResultSetMetaData metaData = ps.getMetaData();
                    while (rs.next()) {
                        final Map<String, Object> rsdata = (Map<String, Object>)new HashMap();
                        for (int j = 0; j < metaData.getColumnCount(); ++j) {
                            final String columnLabel = metaData.getColumnLabel(j + 1);
                            rsdata.put(columnLabel, rs.getObject(columnLabel));
                        }
                        if (!rsdata.isEmpty()) {
                            ret.add(rsdata);
                        }
                    }
                    rs.close();
                }
                ps.close();
                con.close();
            }
        }
        catch (SQLException e) {
            System.err.println("查询数据出错" + e);
        }
        finally {
            try {
                con.setAutoCommit(true);
                con.close();
            }
            catch (SQLException ex) {}
        }
        return ret;
    }
    
    public static int SQLInsert(final String sql, final Object... data) {
        int ret = 0;
        Connection con = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            con.setAutoCommit(false);
            try (final PreparedStatement ps = con.prepareStatement(sql, 1)) {
                for (int i = 0; i < data.length; ++i) {
                    ps.setObject(i + 1, data[i]);
                }
                ret = ps.executeUpdate();
                ps.close();
            }
            con.commit();
        }
        catch (SQLException e) {
            try {
                con.rollback();
            }
            catch (SQLException ex) {}
            System.err.println("添加数据出错" + e);
        }
        finally {
            try {
                con.setAutoCommit(true);
                con.close();
            }
            catch (SQLException ex2) {}
        }
        return ret;
    }
    
    public static int SQLUpdate(final String sql, final Object... data) {
        int ret = 0;
        Connection con = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            con.setAutoCommit(false);
            try (final PreparedStatement ps = con.prepareStatement(sql)) {
                for (int i = 0; i < data.length; ++i) {
                    ps.setObject(i + 1, data[i]);
                }
                ret = ps.executeUpdate();
                ps.close();
            }
            con.commit();
        }
        catch (SQLException e) {
            try {
                con.rollback();
            }
            catch (SQLException ex) {}
            System.err.println("更新数据出错" + e);
        }
        finally {
            try {
                con.setAutoCommit(true);
                con.close();
            }
            catch (SQLException ex2) {}
        }
        return ret;
    }
}
