package client.inventory;

public enum ItemFlag2
{
    notSale(16), 
    pickUpBlock(64), 
    only(128), 
    accountSharable(256), 
    tradeBlock(1024);
    
    private final int i;
    
    private ItemFlag2(final int i) {
        this.i = i;
    }
    
    public final int getValue() {
        return this.i;
    }
    
    public final boolean check(final int flag) {
        return (flag & this.i) == this.i;
    }
}
