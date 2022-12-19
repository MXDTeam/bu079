package MXDJR;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.io.IOException;
import com.alibaba.fastjson.JSON;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.io.FileInputStream;
import java.io.File;

public class ConfigRead
{
    private static File ConfigFile;
    
    public static boolean ReadStart(final String local) {
        try {
            ConfigRead.ConfigFile = new File(local + "AnswerConfig.json");
            if (!ConfigRead.ConfigFile.exists()) {
                ConfigRead.ConfigFile.createNewFile();
                MXDJR.Config = new ConfigObj();
                Save();
                return true;
            }
            final InputStreamReader reader = new InputStreamReader((InputStream)new FileInputStream(ConfigRead.ConfigFile), StandardCharsets.UTF_8);
            final BufferedReader bf = new BufferedReader((Reader)reader);
            final char[] buf = new char[1024];
            final StringBuilder data = new StringBuilder();
            int length;
            while ((length = bf.read(buf)) != -1) {
                data.append(new String(buf, 0, length));
            }
            MXDJR.Config = (ConfigObj)JSON.parseObject(data.toString(), ConfigObj.class);
            if (MXDJR.Config.GJC == null) {
                MXDJR.Config = new ConfigObj();
                Save();
            }
            bf.close();
            reader.close();
            for (final String item : MXDJR.Config.GJC) {
                System.out.println("关键词：" + item);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static void Save() {
        try {
            final FileOutputStream out = new FileOutputStream(ConfigRead.ConfigFile);
            final OutputStreamWriter write = new OutputStreamWriter((OutputStream)out, StandardCharsets.UTF_8);
            write.write(JSON.toJSONString(MXDJR.Config));
            write.close();
            out.close();
        }
        catch (Exception e) {
            System.out.println("配置文件保存失败:" + e);
            e.printStackTrace();
        }
    }
}
