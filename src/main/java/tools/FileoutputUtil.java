package tools;

import java.util.TimeZone;
import client.MapleCharacter;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.text.SimpleDateFormat;

public class FileoutputUtil{
    private static final SimpleDateFormat sdfT = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
    public static final String Acc_Stuck = "logs\\Except\\Log_AccountStuck.txt";
    public static final String Login_Error = "logs\\Log_Login_Error.txt";
    public static final String IP_Log = "logs\\Log_AccountIP.txt";
    public static final String GMCommand_Log = "logs\\Log_GMCommand.txt";
    public static final String Zakum_Log = "logs\\Log_Zakum.txt";
    public static final String Horntail_Log = "logs\\Log_Horntail.txt";
    public static final String UnknownRevPacket_Log = "logs\\服务端数据包_未知.txt";
    public static final String UnknownSedPacket_Log = "logs\\客户端数据包_未知.txt";
    public static final String Packet_Log = "logs\\数据包收发\\Log.txt";
    public static final String ClientError_log = "logs/ClientError_log.txt";
    public static final String Pinkbean_Log = "logs\\Log_Pinkbean.txt";
    public static final String ScriptEx_Log = "logs\\Log_Script_Except.txt";
    public static final String PacketEx_Log = "logs\\Except\\Log_Packet_Except.txt";
    public static final String CodeEx_Log = "logs\\Except\\Log_Code_Except.txt";
    public static final String Donator_Log = "logs\\Shadier_Merchant.txt";
    public static final String Hacker_Log = "logs\\Log_Hacker.txt";
    public static final String Movement_Log = "logs\\Log_Movement.txt";
    public static final String Client_Error_2 = "logs\\Client\\用戶端_报错_非38.txt";
    public static final String Client_Error = "logs\\Client\\用戶端_报错.txt";
    public static final String CommandEx_Log = "logs\\Except\\Log_Command_Except.txt";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat sdfGMT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat sdf_ = new SimpleDateFormat("yyyy-MM-dd");
    private static final String FILE_PATH = "logs/";
    private static final String ERROR = "error/";
    
    public static boolean readtxt(final String txt, final String existMsg) {
        final File file = new File(txt);
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        final String filePath = txt;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader((Reader)new InputStreamReader((InputStream)new FileInputStream(filePath), StringUtil.codeString(filePath)));
            final String str;
            if ((str = reader.readLine()) != null) {
                return str.contains((CharSequence)existMsg);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            }
            catch (IOException e3) {
                e3.printStackTrace();
            }
        }
        return false;
    }
    
    public static void print(final String name, final String s) {
        print(name, s, true);
    }
    
    public static void print(final String name, final String s, final boolean line) {
        logToFile("logs/" + name, s + (line ? "\r\n---------------------------------\r\n" : null));
    }
    
    public static void printError(final String name, final Throwable t, final String info) {
        printError(name, info + "\r\n" + getString(t));
    }
    
    public static void printError(final String name, final String s) {
        logToFile("logs/error/" + FileoutputUtil.sdf_.format(Calendar.getInstance().getTime()) + "/" + name, s + "\r\n---------------------------------\r\n");
    }
    
    public static void outputFileError(final String file, final Throwable t, final boolean size) {
        log(file, getString(t), size);
    }
    
    public static void outputFileError(final String file, final Throwable t) {
        log(file, getString(t));
    }
    
    public static void log(final String file, final String msg, final boolean size) {
        logToFile(file, "\r\n------------------------ " + CurrentReadable_Time() + " ------------------------\r\n" + msg, false, size);
    }
    
    public static void log(final String file, final String msg) {
        logToFile(file, "\r\n------------------------ " + CurrentReadable_Time() + " ------------------------\r\n" + msg);
    }
    
    public static void logToFile(final String file, final String[] msgs) {
        for (int i = 0; i < msgs.length; ++i) {
            logToFile(file, msgs[i], false);
            if (i < msgs.length - 1) {
                logToFile(file, "\r\n", false);
            }
        }
    }
    
    public static void logToFile(final String file, final String msg) {
        logToFile(file, msg, false);
    }
    
    public static void logToFileIfNotExists(final String file, final String msg) {
        logToFile(file, msg, true);
    }
    
    public static void logToFile(final String file, final String msg, final boolean notExists) {
        logToFile(file, msg, notExists, true);
    }
    
    public static void logToFile(final String file, final String msg, final boolean notExists, final boolean size) {
        FileOutputStream out = null;
        try {
            File outputFile = new File(file);
            if (outputFile.exists() && outputFile.isFile() && outputFile.length() >= 1024000L && size) {
                outputFile.renameTo(new File(file.substring(0, file.length() - 4) + "_" + FileoutputUtil.sdfT.format(Calendar.getInstance().getTime()) + file.substring(file.length() - 4, file.length())));
                outputFile = new File(file);
            }
            if (outputFile.getParentFile() != null) {
                outputFile.getParentFile().mkdirs();
            }
            out = new FileOutputStream(file, true);
            if (!out.toString().contains((CharSequence)msg) || !notExists) {
                final OutputStreamWriter osw = new OutputStreamWriter((OutputStream)out, "UTF-8");
                osw.write(msg);
                osw.flush();
            }
        }
        catch (IOException ex) {}
        finally {
            try {
                if (out != null) {
                    out.close();
                }
            }
            catch (IOException ex2) {}
        }
    }
    
    public static String CurrentReadable_Date() {
        return FileoutputUtil.sdf_.format(Calendar.getInstance().getTime());
    }
    
    public static String CurrentReadable_Time() {
        return FileoutputUtil.sdf.format(Calendar.getInstance().getTime());
    }
    
    public static String NowTime() {
        final Date now = new Date();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        final String hehe = dateFormat.format(now);
        return hehe;
    }
    
    public static String CurrentReadable_TimeGMT() {
        return FileoutputUtil.sdfGMT.format(new Date());
    }
    
    public static String getString(final Throwable e) {
        String retValue = null;
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter((Writer)sw);
            e.printStackTrace(pw);
            retValue = sw.toString();
        }
        finally {
            try {
                if (pw != null) {
                    pw.close();
                }
                if (sw != null) {
                    sw.close();
                }
            }
            catch (IOException ex) {}
        }
        return retValue;
    }
    
    public static void outError(final String file, final Throwable t) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file, true);
            out.write(("\n------------------------ " + CurrentReadable_Time() + " ------------------------\n").getBytes());
            out.write(getString(t).getBytes());
        }
        catch (IOException ex) {}
        finally {
            try {
                if (out != null) {
                    out.close();
                }
            }
            catch (IOException ex2) {}
        }
    }
    
    public static void packetLog(final String file, final String msg) {
        FileOutputStream out = null;
        try {
            final File outputFile = new File(file);
            if (outputFile.getParentFile() != null) {
                outputFile.getParentFile().mkdirs();
            }
            out = new FileOutputStream(file, true);
            out.write(msg.getBytes());
            out.write("\r\n\r\n".getBytes());
        }
        catch (IOException ex) {}
        finally {
            try {
                if (out != null) {
                    out.close();
                }
            }
            catch (IOException ex2) {}
        }
    }
    public static void outputFileKey(String key) {
        String newfile = "机器码.txt";
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(newfile, true);
            out.write(("[" + CurrentReadable_Time() + "] ").getBytes());
            out.write(key.getBytes());
            out.write(("\r\n").getBytes());
        } catch (IOException ess) {
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ignore) {
            }
        }
    }  
    public static void logToFile_chr(final MapleCharacter chr, final String file, final String msg) {
        logToFile(file, "\r\n" + CurrentReadable_Time() + " 账号 " + chr.getClient().getAccountName() + " 名称 " + chr.getName() + " (" + chr.getId() + ") 等级 " + (int)chr.getLevel() + " 地图 " + chr.getMapId() + " " + msg, false);
    }
    
    static {
        sdfGMT.setTimeZone(TimeZone.getTimeZone("UTC+8"));
    }
}
