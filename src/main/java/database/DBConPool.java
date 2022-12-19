package database;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import constants.ServerConstants;
import LzjSetting.Game;
import com.alibaba.druid.pool.DruidDataSource;

public class DBConPool
{
    private static DruidDataSource dataSource;
    public static final int RETURN_GENERATED_KEYS = 1;
    public static String dbUser;
    public static String dbPass;
    public static String dbIp;
    public static String dbName;
    public static int dbport;
    
    public static void InitDB() {
        DBConPool.dbName = Game.数据库名字;
        DBConPool.dbIp = Game.数据库IP;
        DBConPool.dbport = Integer.parseInt(Game.数据库端口);
        DBConPool.dbUser = Game.数据库用户名;
        DBConPool.dbPass = Game.数据库密码;
    }
    
    public static DBConPool getInstance() {
        return InstanceHolder.instance;
    }
    
    private DBConPool() {
    }
    
    public DruidDataSource getDataSource() {
        if (DBConPool.dataSource == null) {
            this.InitDBConPool();
        }
        return DBConPool.dataSource;
    }
    
    private void InitDBConPool() {
        (DBConPool.dataSource = new DruidDataSource()).setName("mysql_pool");
        DBConPool.dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        DBConPool.dataSource.setUrl("jdbc:mysql://127.0.0.1:" + ServerConstants.SQL_PORT + "/" + ServerConstants.SQL_DATABASE + "?useUnicode=true&characterEncoding=UTF8");
        DBConPool.dataSource.setUsername(ServerConstants.SQL_USER);
        DBConPool.dataSource.setPassword(ServerConstants.SQL_PASSWORD);
        DBConPool.dataSource.setInitialSize(50);
        DBConPool.dataSource.setMinIdle(20);
        DBConPool.dataSource.setMaxActive(50000);
        DBConPool.dataSource.setMaxWait(65000L);
        DBConPool.dataSource.setTimeBetweenEvictionRunsMillis(180000L);
        DBConPool.dataSource.setValidationQuery("SELECT 'x'");
        DBConPool.dataSource.setTestWhileIdle(true);
        DBConPool.dataSource.setTestOnBorrow(false);
        DBConPool.dataSource.setTestOnReturn(false);
        DBConPool.dataSource.setPoolPreparedStatements(true);
        DBConPool.dataSource.setMaxPoolPreparedStatementPerConnectionSize(93000);
        DBConPool.dataSource.setUseUnfairLock(true);
        DBConPool.dataSource.setMinEvictableIdleTimeMillis(300000L);
        DBConPool.dataSource.setLogAbandoned(false);
        DBConPool.dataSource.setRemoveAbandoned(true);
        DBConPool.dataSource.setRemoveAbandonedTimeout(120);
    }
    
    public static void cleanUP(final ResultSet rs, final PreparedStatement ps, final Connection con) {
        if (rs != null) {
            try {
                rs.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            }catch (SQLException ex) {
            }
        }
        try {
            if (con != null) {
                con.close();
            }
        }catch (SQLException ex2) {
        }
    }
    
    public static void cleanUP(final ResultSet rs, final PreparedStatement ps, final PreparedStatement psu, final Connection con) {
        if (rs != null) {
            try {
                rs.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            }
            catch (SQLException ex) {}
        }
        if (psu != null) {
            try {
                psu.close();
            }
            catch (SQLException ex2) {}
        }
        try {
            if (con != null) {
                con.close();
            }
        }
        catch (SQLException ex3) {}
    }
    
    static {
        dataSource = null;
        dbUser = "root";
        dbPass = "root";
        dbIp = "localhost";
        dbName = "v079";
        dbport = 3306;
        InitDB();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            System.out.println("[数据库信息] 找不到JDBC驱动.");
            System.exit(0);
        }
    }
    
    private static class InstanceHolder{
        public static final DBConPool instance = new DBConPool();
    }
}
