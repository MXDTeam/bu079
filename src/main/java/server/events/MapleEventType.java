package server.events;

public enum MapleEventType
{
    打果子("打果子", new int[] { 109080000 }), 
    打瓶盖("打瓶盖", new int[] { 109080010 }), 
    爬绳子("爬绳子", new int[] { 109030001, 109030002, 109030003 }), 
    终极忍耐("终极忍耐", new int[] { 109040000, 109040001, 109040002, 109040003, 109040004 }), 
    是非题大考验("是非题大考验", new int[] { 109020001 }), 
    滚雪球("滚雪球", new int[] { 109060000 }), 
    寻宝("寻宝", new int[] { 109010000, 109010100, 109010102, 109010103, 109010104, 109010105, 109010106, 109010107, 109010108, 109010109, 109010110, 109010200, 109010201, 109010202, 109010203, 109010204, 109010205, 109010206 });
    
    public String command;
    public int[] mapids;
    
    private MapleEventType(final String comm, final int[] mapids) {
        this.command = comm;
        this.mapids = mapids;
    }
    
    public static final MapleEventType getByString(final String splitted) {
        for (final MapleEventType t : values()) {
            if (t.command.equalsIgnoreCase(splitted)) {
                return t;
            }
        }
        return null;
    }
}
