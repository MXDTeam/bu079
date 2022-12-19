package handling.login.handler;

import handling.login.LoginServer;
import client.LoginCrypto;
import java.util.Calendar;
import gui.Start;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import tools.FileoutputUtil;
import database.DBConPool;

public class AutoRegister{
    public static boolean autoRegister = LoginServer.getAutoReg();
    public static boolean success = false;
    public static boolean mac = true;
    
    public static boolean getAccountExists(final String login) {
        boolean accountExists = false;
        try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection()) {
            final PreparedStatement ps = con.prepareStatement("SELECT name FROM accounts WHERE name = ?");
            ps.setString(1, login);
            final ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                accountExists = true;
            }
            rs.close();
            ps.close();
        }
        catch (SQLException ex) {
            System.err.println("[getAccountExists]" + ex);
            FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)ex);
        }
        return accountExists;
    }
    
    public static void createAccount(final String login, final String pwd, final String eip, final String mac) {
        final String sockAddr = eip;
        try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection()) {
            ResultSet rs;
            try (final PreparedStatement ipc = con.prepareStatement("SELECT Macs FROM accounts WHERE macs = ?")) {
                ipc.setString(1, mac);
                rs = ipc.executeQuery();
                Label_0506: {
                    Label_0502: {
                        if (rs.first()) {
                            if (!rs.last() || rs.getRow() >= ((Integer)Start.ConfigValuesMap.get("MAC注册上限")).intValue()) {
                                break Label_0502;
                            }
                        }
                        try {
                            try (final PreparedStatement ps = con.prepareStatement("INSERT INTO accounts (name, password, email, birthday, macs, SessionIP) VALUES (?, ?, ?, ?, ?, ?)")) {
                                final Calendar c = Calendar.getInstance();
                                final int year = c.get(1);
                                final int month = c.get(2) + 1;
                                final int dayOfMonth = c.get(5);
                                ps.setString(1, login);
                                ps.setString(2, LoginCrypto.hexSha1(pwd));
                                ps.setString(3, "autoregister@mail.com");
                                ps.setString(4, year + "-" + month + "-" + dayOfMonth);
                                ps.setString(5, mac);
                                ps.setString(6, "/" + sockAddr.substring(1, sockAddr.lastIndexOf(58)));
                                ps.executeUpdate();
                                ps.close();
                            }
                            AutoRegister.success = true;
                            rs.close();
                            break Label_0506;
                        }
                        catch (SQLException ex) {
                            System.err.println("createAccount" + ex);
                            FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)ex);
                            return;
                        }
                    }
                    AutoRegister.success = false;
                }
            }
            rs.close();
        }
        catch (SQLException ex2) {
            System.err.println("[createAccount]" + ex2);
            FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)ex2);
        }
    }
}
