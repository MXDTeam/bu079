package MXDJR;

class RobotTask
{
    public byte index;
    public String data;
    
    public RobotTask(final byte type, final String s) {
        this.index = type;
        this.data = s;
    }
}
