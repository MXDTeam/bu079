package MXDJR;

import java.nio.charset.StandardCharsets;
import com.alibaba.fastjson.JSON;

class BuildPack
{
    public static byte[] Build(final Object data, final int index) {
        final String str = JSON.toJSONString(data) + " ";
        final byte[] temp = str.getBytes(StandardCharsets.UTF_8);
        temp[temp.length - 1] = (byte)index;
        return temp;
    }
    
    public static byte[] BuildImage(final long qq, final long id, final long fid, final String img, final int index) {
        String temp = "";
        if (id != 0L) {
            temp = temp + "id=" + id + "&";
        }
        if (fid != 0L) {
            temp = temp + "fid=" + fid + "&";
        }
        temp = temp + "qq=" + qq + "&";
        temp = temp + "img=" + img;
        final String str = temp + " ";
        final byte[] temp2 = str.getBytes(StandardCharsets.UTF_8);
        temp2[temp2.length - 1] = (byte)index;
        return temp2;
    }
}
