package tools;

class Encoding
{
    public static int GB2312;
    public static int GBK;
    public static int GB18030;
    public static int HZ;
    public static int BIG5;
    public static int CNS11643;
    public static int UTF8;
    public static int UTF8T;
    public static int UTF8S;
    public static int UNICODE;
    public static int UNICODET;
    public static int UNICODES;
    public static int ISO2022CN;
    public static int ISO2022CN_CNS;
    public static int ISO2022CN_GB;
    public static int EUC_KR;
    public static int CP949;
    public static int ISO2022KR;
    public static int JOHAB;
    public static int SJIS;
    public static int EUC_JP;
    public static int ISO2022JP;
    public static int ASCII;
    public static int OTHER;
    public static int TOTALTYPES;
    public static final int SIMP = 0;
    public static final int TRAD = 1;
    public static String[] javaname;
    public static String[] nicename;
    public static String[] htmlname;
    
    public Encoding() {
        Encoding.javaname = new String[Encoding.TOTALTYPES];
        Encoding.nicename = new String[Encoding.TOTALTYPES];
        Encoding.htmlname = new String[Encoding.TOTALTYPES];
        Encoding.javaname[Encoding.GB2312] = "GB2312";
        Encoding.javaname[Encoding.GBK] = "GBK";
        Encoding.javaname[Encoding.GB18030] = "GB18030";
        Encoding.javaname[Encoding.HZ] = "ASCII";
        Encoding.javaname[Encoding.ISO2022CN_GB] = "ISO2022CN_GB";
        Encoding.javaname[Encoding.BIG5] = "BIG5";
        Encoding.javaname[Encoding.CNS11643] = "EUC-TW";
        Encoding.javaname[Encoding.ISO2022CN_CNS] = "ISO2022CN_CNS";
        Encoding.javaname[Encoding.ISO2022CN] = "ISO2022CN";
        Encoding.javaname[Encoding.UTF8] = "UTF-8";
        Encoding.javaname[Encoding.UTF8T] = "UTF-8";
        Encoding.javaname[Encoding.UTF8S] = "UTF-8";
        Encoding.javaname[Encoding.UNICODE] = "Unicode";
        Encoding.javaname[Encoding.UNICODET] = "Unicode";
        Encoding.javaname[Encoding.UNICODES] = "Unicode";
        Encoding.javaname[Encoding.EUC_KR] = "EUC_KR";
        Encoding.javaname[Encoding.CP949] = "MS949";
        Encoding.javaname[Encoding.ISO2022KR] = "ISO2022KR";
        Encoding.javaname[Encoding.JOHAB] = "Johab";
        Encoding.javaname[Encoding.SJIS] = "SJIS";
        Encoding.javaname[Encoding.EUC_JP] = "EUC_JP";
        Encoding.javaname[Encoding.ISO2022JP] = "ISO2022JP";
        Encoding.javaname[Encoding.ASCII] = "ASCII";
        Encoding.javaname[Encoding.OTHER] = "ISO8859_1";
        Encoding.htmlname[Encoding.GB2312] = "GB2312";
        Encoding.htmlname[Encoding.GBK] = "GBK";
        Encoding.htmlname[Encoding.GB18030] = "GB18030";
        Encoding.htmlname[Encoding.HZ] = "HZ-GB-2312";
        Encoding.htmlname[Encoding.ISO2022CN_GB] = "ISO-2022-CN-EXT";
        Encoding.htmlname[Encoding.BIG5] = "BIG5";
        Encoding.htmlname[Encoding.CNS11643] = "EUC-TW";
        Encoding.htmlname[Encoding.ISO2022CN_CNS] = "ISO-2022-CN-EXT";
        Encoding.htmlname[Encoding.ISO2022CN] = "ISO-2022-CN";
        Encoding.htmlname[Encoding.UTF8] = "UTF-8";
        Encoding.htmlname[Encoding.UTF8T] = "UTF-8";
        Encoding.htmlname[Encoding.UTF8S] = "UTF-8";
        Encoding.htmlname[Encoding.UNICODE] = "UTF-16";
        Encoding.htmlname[Encoding.UNICODET] = "UTF-16";
        Encoding.htmlname[Encoding.UNICODES] = "UTF-16";
        Encoding.htmlname[Encoding.EUC_KR] = "EUC-KR";
        Encoding.htmlname[Encoding.CP949] = "x-windows-949";
        Encoding.htmlname[Encoding.ISO2022KR] = "ISO-2022-KR";
        Encoding.htmlname[Encoding.JOHAB] = "x-Johab";
        Encoding.htmlname[Encoding.SJIS] = "Shift_JIS";
        Encoding.htmlname[Encoding.EUC_JP] = "EUC-JP";
        Encoding.htmlname[Encoding.ISO2022JP] = "ISO-2022-JP";
        Encoding.htmlname[Encoding.ASCII] = "ASCII";
        Encoding.htmlname[Encoding.OTHER] = "ISO8859-1";
        Encoding.nicename[Encoding.GB2312] = "GB-2312";
        Encoding.nicename[Encoding.GBK] = "GBK";
        Encoding.nicename[Encoding.GB18030] = "GB18030";
        Encoding.nicename[Encoding.HZ] = "HZ";
        Encoding.nicename[Encoding.ISO2022CN_GB] = "ISO2022CN-GB";
        Encoding.nicename[Encoding.BIG5] = "Big5";
        Encoding.nicename[Encoding.CNS11643] = "CNS11643";
        Encoding.nicename[Encoding.ISO2022CN_CNS] = "ISO2022CN-CNS";
        Encoding.nicename[Encoding.ISO2022CN] = "ISO2022 CN";
        Encoding.nicename[Encoding.UTF8] = "UTF-8";
        Encoding.nicename[Encoding.UTF8T] = "UTF-8 (Trad)";
        Encoding.nicename[Encoding.UTF8S] = "UTF-8 (Simp)";
        Encoding.nicename[Encoding.UNICODE] = "Unicode";
        Encoding.nicename[Encoding.UNICODET] = "Unicode (Trad)";
        Encoding.nicename[Encoding.UNICODES] = "Unicode (Simp)";
        Encoding.nicename[Encoding.EUC_KR] = "EUC-KR";
        Encoding.nicename[Encoding.CP949] = "CP949";
        Encoding.nicename[Encoding.ISO2022KR] = "ISO 2022 KR";
        Encoding.nicename[Encoding.JOHAB] = "Johab";
        Encoding.nicename[Encoding.SJIS] = "Shift-JIS";
        Encoding.nicename[Encoding.EUC_JP] = "EUC-JP";
        Encoding.nicename[Encoding.ISO2022JP] = "ISO 2022 JP";
        Encoding.nicename[Encoding.ASCII] = "ASCII";
        Encoding.nicename[Encoding.OTHER] = "OTHER";
    }
    
    static {
        GB2312 = 0;
        GBK = 1;
        GB18030 = 2;
        HZ = 3;
        BIG5 = 4;
        CNS11643 = 5;
        UTF8 = 6;
        UTF8T = 7;
        UTF8S = 8;
        UNICODE = 9;
        UNICODET = 10;
        UNICODES = 11;
        ISO2022CN = 12;
        ISO2022CN_CNS = 13;
        ISO2022CN_GB = 14;
        EUC_KR = 15;
        CP949 = 16;
        ISO2022KR = 17;
        JOHAB = 18;
        SJIS = 19;
        EUC_JP = 20;
        ISO2022JP = 21;
        ASCII = 22;
        OTHER = 23;
        TOTALTYPES = 24;
    }
}
