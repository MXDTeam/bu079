package client;

public class MapleBeans
{
    private final int number;
    private final int type;
    private final int pos;
    
    public MapleBeans(final int pos, final int type, final int number) {
        this.pos = pos;
        this.number = number;
        this.type = type;
    }
    
    public int getType() {
        return this.type;
    }
    
    public int getNumber() {
        return this.number;
    }
    
    public int getPos() {
        return this.pos;
    }
    
    public enum BeansType
    {
        开始打豆豆(0), 
        暂停打豆豆(1), 
        顏色求进洞(3), 
        进洞旋转(4), 
        奖励豆豆效果(5), 
        未知效果(6), 
        黄金狗(7), 
        奖励豆豆效果B(8), 
        领奖npc(9);
        
        final byte type;
        
        private BeansType(final int type) {
            this.type = (byte)type;
        }
        
        public byte getType() {
            return this.type;
        }
    }
}
