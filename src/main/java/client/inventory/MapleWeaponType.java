package client.inventory;

public enum MapleWeaponType
{
    沒有武器(0.0f, 0), 
    闪亮克魯(1.2f, 25), 
    灵魂射手(1.7f, 15), 
    魔剑(1.3f, 20), 
    能量剑(1.3125f, 20), 
    幻兽棍棒(1.34f, 20), 
    单手剑(1.2f, 20), 
    单手斧(1.2f, 20), 
    单手棍(1.2f, 20), 
    短剑(1.3f, 20), 
    双刀(1.3f, 20), 
    手杖(1.3f, 20), 
    短杖(1.0f, 25), 
    长杖(1.0f, 25), 
    双手剑(1.34f, 20), 
    双手斧(1.34f, 20), 
    双手棍(1.34f, 20), 
    枪(1.49f, 20), 
    矛(1.49f, 20), 
    弓(1.3f, 15), 
    弩(1.35f, 15), 
    拳套(1.75f, 15), 
    指虎(1.7f, 20), 
    火枪(1.5f, 15), 
    双弩枪(1.3f, 15), 
    加農炮(1.5f, 15), 
    太刀(1.25f, 20), 
    扇子(1.35f, 25), 
    琉(1.49f, 20), 
    璃(1.34f, 20), 
    ESP限制器(1.0f, 20), 
    BIG_SWORD(1.3f, 15),
    LONG_SWORD(1.3f, 15),
    未知(0.0f, 0);
    
    private final float damageMultiplier;
    private final int baseMastery;
    
    private MapleWeaponType(final float maxDamageMultiplier, final int baseMastery) {
        this.damageMultiplier = maxDamageMultiplier;
        this.baseMastery = baseMastery;
    }
    
    public final float getMaxDamageMultiplier() {
        return this.damageMultiplier;
    }
    
    public final int getBaseMastery() {
        return this.baseMastery;
    }
}
