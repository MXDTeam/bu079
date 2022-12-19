package gui;

public class jyhss
{
    public static class jyhwpss
    {
        public int sl;
        public String beizhu;
        public int inventorytype;
        
        public jyhwpss(final int sl, final String beizhu, final int inventorytype) {
            this.sl = sl;
            this.beizhu = beizhu;
            this.inventorytype = inventorytype;
        }
        
        public int getsl() {
            return this.sl;
        }
        
        public String getbeizhu() {
            return this.beizhu;
        }
        
        public int getinventorytype() {
            return this.inventorytype;
        }
    }
}
