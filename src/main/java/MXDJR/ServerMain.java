package MXDJR;

class ServerMain
{
    public static void LogError(final Exception e) {
        final String a = "[错误]";
        System.out.println(a);
        e.printStackTrace();
    }
    
    public static void LogError(String a) {
        a = "[错误]" + a;
        System.out.println(a);
    }
    
    public static void LogOut(String a) {
        a = "[信息]" + a;
        System.out.println(a);
    }
}
