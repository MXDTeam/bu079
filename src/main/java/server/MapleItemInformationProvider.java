package server;

import client.inventory.MapleInventoryIdentifier;
import client.inventory.MaplePet;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import database.DBConPool;
import client.inventory.MaplePet.PetFlag;
import tools.StringUtil;
import client.inventory.ItemFlag;
import client.MapleCharacter;
import client.inventory.IItem;
import java.util.Map.Entry;
import java.util.LinkedHashMap;
import gui.Start;
import constants.GameConstants;
import client.MapleClient;
import provider.MapleDataFileEntry;
import provider.MapleDataDirectoryEntry;
import client.inventory.MapleWeaponType;
import java.util.Iterator;
import provider.MapleDataTool;
import tools.FileoutputUtil;
import java.util.ArrayList;
import java.util.HashMap;
import provider.MapleDataProviderFactory;
import client.inventory.MapleInventoryType;
import tools.Pair;
import client.inventory.Equip;
import java.util.List;
import java.util.Map;
import provider.MapleData;
import provider.MapleDataProvider;

public class MapleItemInformationProvider{
    private static final MapleItemInformationProvider instance = new MapleItemInformationProvider();
    protected final MapleDataProvider etcData;
    protected final MapleDataProvider itemData;
    protected final MapleDataProvider equipData;
    protected final MapleDataProvider stringData;
    protected final MapleData cashStringData;
    protected final MapleData consumeStringData;
    protected final MapleData eqpStringData;
    protected final MapleData etcStringData;
    protected final MapleData insStringData;
    protected final MapleData petStringData;
    protected Map<Integer, Boolean> onEquipUntradableCache;
    protected final Map<Integer, List<Integer>> scrollReqCache;
    protected final Map<Integer, Short> slotMaxCache;
    protected final Map<Integer, Integer> getExpCache;
    protected final Map<Integer, List<StructPotentialItem>> potentialCache;
    protected final Map<Integer, MapleStatEffect> itemEffects;
    protected final Map<Integer, Map<String, Integer>> equipStatsCache;
    protected final Map<Integer, Map<String, Byte>> itemMakeStatsCache;
    protected final Map<Integer, Short> itemMakeLevel;
    protected final Map<Integer, Equip> equipCache;
    protected final Map<Integer, Double> priceCache;
    protected final Map<Integer, Integer> wholePriceCache;
    protected final Map<Integer, Integer> projectileWatkCache;
    protected final Map<Integer, Integer> monsterBookID;
    protected final Map<Integer, String> nameCache;
    protected final Map<Integer, String> descCache;
    protected final Map<Integer, Short> petFlagInfo;
    protected final Map<Integer, Integer> petLimitLifeInfo;
    protected final Map<Integer, Integer> petLifeInfo;
    protected final Map<Integer, String> msgCache;
    protected final Map<Integer, Map<String, Integer>> SkillStatsCache;
    protected final Map<Integer, Byte> consumeOnPickupCache;
    protected final Map<Integer, Boolean> dropRestrictionCache;
    protected final Map<Integer, Boolean> accCache;
    protected final Map<Integer, Boolean> pickupRestrictionCache;
    protected final Map<Integer, Integer> stateChangeCache;
    protected final Map<Integer, Integer> mesoCache;
    protected final Map<Integer, Boolean> notSaleCache;
    protected final Map<Integer, Integer> karmaEnabledCache;
    protected final Map<Integer, Boolean> isQuestItemCache;
    protected final Map<Integer, Boolean> blockPickupCache;
    protected final Map<Integer, List<Integer>> petsCanConsumeCache;
    protected final Map<Integer, Boolean> logoutExpireCache;
    protected final Map<Integer, List<Pair<Integer, Integer>>> summonMobCache;
    protected final List<Pair<Integer, String>> itemNameCache;
    protected final Map<Integer, Map<Integer, Map<String, Integer>>> equipIncsCache;
    protected final Map<Integer, Map<Integer, List<Integer>>> equipSkillsCache;
    protected final Map<Integer, Pair<Integer, List<StructRewardItem>>> RewardItem;
    protected final Map<Byte, StructSetItem> setItems;
    protected final Map<Integer, Pair<Integer, List<Integer>>> questItems;
    protected final Map<Integer, String> faceList;
    public static final Map<Integer, String> faceLists = new HashMap();
    public static final Map<Integer, String> hairList = new HashMap();
    protected Map<Integer, MapleInventoryType> inventoryTypeCache;
    protected final Map<Integer, Integer> chairMountId;
    
    protected MapleItemInformationProvider() {
        this.etcData = MapleDataProviderFactory.getDataProvider("Etc.wz");
        this.itemData = MapleDataProviderFactory.getDataProvider("Item.wz");
        this.equipData = MapleDataProviderFactory.getDataProvider("Character.wz");
        this.stringData = MapleDataProviderFactory.getDataProvider("String.wz");
        this.cashStringData = this.stringData.getData("Cash.img");
        this.consumeStringData = this.stringData.getData("Consume.img");
        this.eqpStringData = this.stringData.getData("Eqp.img");
        this.etcStringData = this.stringData.getData("Etc.img");
        this.insStringData = this.stringData.getData("Ins.img");
        this.petStringData = this.stringData.getData("Pet.img");
        this.onEquipUntradableCache = (Map<Integer, Boolean>)new HashMap();
        this.scrollReqCache = (Map<Integer, List<Integer>>)new HashMap();
        this.slotMaxCache = (Map<Integer, Short>)new HashMap();
        this.getExpCache = (Map<Integer, Integer>)new HashMap();
        this.potentialCache = (Map<Integer, List<StructPotentialItem>>)new HashMap();
        this.itemEffects = (Map<Integer, MapleStatEffect>)new HashMap();
        this.equipStatsCache = (Map<Integer, Map<String, Integer>>)new HashMap();
        this.itemMakeStatsCache = (Map<Integer, Map<String, Byte>>)new HashMap();
        this.itemMakeLevel = (Map<Integer, Short>)new HashMap();
        this.equipCache = (Map<Integer, Equip>)new HashMap();
        this.priceCache = (Map<Integer, Double>)new HashMap();
        this.wholePriceCache = (Map<Integer, Integer>)new HashMap();
        this.projectileWatkCache = (Map<Integer, Integer>)new HashMap();
        this.monsterBookID = (Map<Integer, Integer>)new HashMap();
        this.nameCache = (Map<Integer, String>)new HashMap();
        this.descCache = (Map<Integer, String>)new HashMap();
        this.petFlagInfo = (Map<Integer, Short>)new HashMap();
        this.petLimitLifeInfo = (Map<Integer, Integer>)new HashMap();
        this.petLifeInfo = (Map<Integer, Integer>)new HashMap();
        this.msgCache = (Map<Integer, String>)new HashMap();
        this.SkillStatsCache = (Map<Integer, Map<String, Integer>>)new HashMap();
        this.consumeOnPickupCache = (Map<Integer, Byte>)new HashMap();
        this.dropRestrictionCache = (Map<Integer, Boolean>)new HashMap();
        this.accCache = (Map<Integer, Boolean>)new HashMap();
        this.pickupRestrictionCache = (Map<Integer, Boolean>)new HashMap();
        this.stateChangeCache = (Map<Integer, Integer>)new HashMap();
        this.mesoCache = (Map<Integer, Integer>)new HashMap();
        this.notSaleCache = (Map<Integer, Boolean>)new HashMap();
        this.karmaEnabledCache = (Map<Integer, Integer>)new HashMap();
        this.isQuestItemCache = (Map<Integer, Boolean>)new HashMap();
        this.blockPickupCache = (Map<Integer, Boolean>)new HashMap();
        this.petsCanConsumeCache = (Map<Integer, List<Integer>>)new HashMap();
        this.logoutExpireCache = (Map<Integer, Boolean>)new HashMap();
        this.summonMobCache = (Map<Integer, List<Pair<Integer, Integer>>>)new HashMap();
        this.itemNameCache = (List<Pair<Integer, String>>)new ArrayList();
        this.equipIncsCache = (Map<Integer, Map<Integer, Map<String, Integer>>>)new HashMap();
        this.equipSkillsCache = (Map<Integer, Map<Integer, List<Integer>>>)new HashMap();
        this.RewardItem = (Map<Integer, Pair<Integer, List<StructRewardItem>>>)new HashMap();
        this.setItems = (Map<Byte, StructSetItem>)new HashMap();
        this.questItems = (Map<Integer, Pair<Integer, List<Integer>>>)new HashMap();
        this.faceList = (Map<Integer, String>)new HashMap();
        this.inventoryTypeCache = (Map<Integer, MapleInventoryType>)new HashMap();
        this.chairMountId = (Map<Integer, Integer>)new HashMap();
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:初始化wz所有道具信息");
    }
    
    public final void load() {
        if (!this.setItems.isEmpty() || !this.potentialCache.isEmpty()) {
            return;
        }
        this.getAllItems();
    }
    
    public final List<StructPotentialItem> getPotentialInfo(final int potId) {
        return this.potentialCache.get(Integer.valueOf(potId));
    }
    
    public final Map<Integer, List<StructPotentialItem>> getAllPotentialInfo() {
        return this.potentialCache;
    }
    
    public final int getTotalStat(final Equip equip) {
        return equip.getStr() + equip.getDex() + equip.getInt() + equip.getLuk() + equip.getMatk() + equip.getWatk() + equip.getAcc() + equip.getAvoid() + equip.getJump() + equip.getHands() + equip.getSpeed() + equip.getHp() + equip.getMp() + equip.getWdef() + equip.getMdef();
    }
    
    public int getHands(final Equip equip) {
        return equip.getHands();
    }
    
    public final MapleStatEffect getItemEffect1(final int itemId) {
        MapleStatEffect ret = (MapleStatEffect)this.itemEffects.get(Integer.valueOf(itemId));
        if (ret == null) {
            final MapleData item = this.getItemData(itemId);
            if (item == null) {
                return null;
            }
            ret = MapleStatEffect.loadItemEffectFromData1(item.getChildByPath("spec"), itemId);
            this.itemEffects.put(Integer.valueOf(itemId), ret);
        }
        return ret;
    }
    
    public final MapleStatEffect getItemEffect2(final int itemId, final int skilllevel) {
        MapleStatEffect ret = (MapleStatEffect)this.itemEffects.get(Integer.valueOf(itemId));
        if (ret == null) {
            final MapleData item = this.getItemData(itemId);
            if (item == null) {
                return null;
            }
            ret = MapleStatEffect.loadItemEffectFromData1(item.getChildByPath("spec"), itemId, skilllevel);
            this.itemEffects.put(Integer.valueOf(itemId), ret);
        }
        return ret;
    }
    
    public static final MapleItemInformationProvider getInstance() {
        return MapleItemInformationProvider.instance;
    }
    
    public boolean isEquip(final int itemId) {
        return itemId / 1000000 == 1;
    }
    
    public List<Pair<Integer, String>> getAllItems2() {
        final List<Pair<Integer, String>> itemPairs = (List<Pair<Integer, String>>)new ArrayList();
        MapleData itemsData = this.stringData.getData("Cash.img");
        for (final MapleData itemFolder : itemsData.getChildren()) {
            itemPairs.add(new Pair(Integer.valueOf(Integer.parseInt(itemFolder.getName())), MapleDataTool.getString("name", itemFolder, "NO-NAME")));
        }
        itemsData = this.stringData.getData("Consume.img");
        for (final MapleData itemFolder : itemsData.getChildren()) {
            itemPairs.add(new Pair(Integer.valueOf(Integer.parseInt(itemFolder.getName())), MapleDataTool.getString("name", itemFolder, "NO-NAME")));
        }
        itemsData = this.stringData.getData("Eqp.img").getChildByPath("Eqp");
        for (final MapleData eqpType : itemsData.getChildren()) {
            for (final MapleData itemFolder2 : eqpType.getChildren()) {
                itemPairs.add(new Pair(Integer.valueOf(Integer.parseInt(itemFolder2.getName())), MapleDataTool.getString("name", itemFolder2, "NO-NAME")));
            }
        }
        itemsData = this.stringData.getData("Etc.img").getChildByPath("Etc");
        for (final MapleData itemFolder : itemsData.getChildren()) {
            itemPairs.add(new Pair(Integer.valueOf(Integer.parseInt(itemFolder.getName())), MapleDataTool.getString("name", itemFolder, "NO-NAME")));
        }
        itemsData = this.stringData.getData("Ins.img");
        for (final MapleData itemFolder : itemsData.getChildren()) {
            itemPairs.add(new Pair(Integer.valueOf(Integer.parseInt(itemFolder.getName())), MapleDataTool.getString("name", itemFolder, "NO-NAME")));
        }
        itemsData = this.stringData.getData("Pet.img");
        for (final MapleData itemFolder : itemsData.getChildren()) {
            itemPairs.add(new Pair(Integer.valueOf(Integer.parseInt(itemFolder.getName())), MapleDataTool.getString("name", itemFolder, "NO-NAME")));
        }
        return itemPairs;
    }
    
    public final List<Pair<Integer, String>> getAllItems() {
        if (!this.itemNameCache.isEmpty()) {
            return this.itemNameCache;
        }
        final List<Pair<Integer, String>> itemPairs = (List<Pair<Integer, String>>)new ArrayList();
        MapleData itemsData = this.stringData.getData("Cash.img");
        for (final MapleData itemFolder : itemsData.getChildren()) {
            itemPairs.add(new Pair(Integer.valueOf(Integer.parseInt(itemFolder.getName())), MapleDataTool.getString("name", itemFolder, "NO-NAME")));
        }
        itemsData = this.stringData.getData("Consume.img");
        for (final MapleData itemFolder : itemsData.getChildren()) {
            itemPairs.add(new Pair(Integer.valueOf(Integer.parseInt(itemFolder.getName())), MapleDataTool.getString("name", itemFolder, "NO-NAME")));
        }
        itemsData = this.stringData.getData("Eqp.img").getChildByPath("Eqp");
        for (final MapleData eqpType : itemsData.getChildren()) {
            for (final MapleData itemFolder2 : eqpType.getChildren()) {
                try {
                    itemPairs.add(new Pair(Integer.valueOf(Integer.parseInt(itemFolder2.getName())), MapleDataTool.getString("name", itemFolder2, "NO-NAME")));
                }
                catch (Exception Ex) {
                    System.out.println("错误：" + itemFolder2.getName());
                }
            }
        }
        itemsData = this.stringData.getData("Etc.img").getChildByPath("Etc");
        for (final MapleData itemFolder : itemsData.getChildren()) {
            itemPairs.add(new Pair(Integer.valueOf(Integer.parseInt(itemFolder.getName())), MapleDataTool.getString("name", itemFolder, "NO-NAME")));
        }
        itemsData = this.stringData.getData("Ins.img");
        for (final MapleData itemFolder : itemsData.getChildren()) {
            itemPairs.add(new Pair(Integer.valueOf(Integer.parseInt(itemFolder.getName())), MapleDataTool.getString("name", itemFolder, "NO-NAME")));
        }
        itemsData = this.stringData.getData("Pet.img");
        for (final MapleData itemFolder : itemsData.getChildren()) {
            itemPairs.add(new Pair(Integer.valueOf(Integer.parseInt(itemFolder.getName())), MapleDataTool.getString("name", itemFolder, "NO-NAME")));
        }
        return itemPairs;
    }
    
    public final boolean isTwoHanded(final int itemId) {
        switch (this.getWeaponType(itemId)) {
            case 双手斧:
            case 双手棍:
            case 弓:
            case 拳套:
            case 弩:
            case 枪:
            case 矛:
            case 双手剑:
            case 火枪:
            case 指虎: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public boolean isUntradeableOnEquip(final int itemId) {
        if (this.onEquipUntradableCache.containsKey(Integer.valueOf(itemId))) {
            return ((Boolean)this.onEquipUntradableCache.get(Integer.valueOf(itemId))).booleanValue();
        }
        final boolean untradableOnEquip = MapleDataTool.getIntConvert("info/equipTradeBlock", this.getItemData(itemId), 0) > 0;
        this.onEquipUntradableCache.put(Integer.valueOf(itemId), Boolean.valueOf(untradableOnEquip));
        return untradableOnEquip;
    }
    
    public int getExpCache(final int itemId) {
        if (this.getExpCache.containsKey(Integer.valueOf(itemId))) {
            return ((Integer)this.getExpCache.get(Integer.valueOf(itemId))).intValue();
        }
        final MapleData item = this.getItemData(itemId);
        if (item == null) {
            return 0;
        }
        int pEntry = 0;
        final MapleData pData = item.getChildByPath("spec/exp");
        if (pData == null) {
            return 0;
        }
        pEntry = MapleDataTool.getInt(pData);
        this.getExpCache.put(Integer.valueOf(itemId), Integer.valueOf(pEntry));
        return pEntry;
    }
    
    public MapleWeaponType getWeaponType(final int itemId) {
        final int cat = itemId / 10000 % 100;
        final MapleWeaponType[] type = { MapleWeaponType.单手剑, MapleWeaponType.单手斧, MapleWeaponType.单手棍, MapleWeaponType.短剑, MapleWeaponType.沒有武器, MapleWeaponType.沒有武器, MapleWeaponType.沒有武器, MapleWeaponType.长杖, MapleWeaponType.短杖, MapleWeaponType.沒有武器, MapleWeaponType.双手剑, MapleWeaponType.双手斧, MapleWeaponType.双手棍, MapleWeaponType.矛, MapleWeaponType.枪, MapleWeaponType.弓, MapleWeaponType.弩, MapleWeaponType.拳套, MapleWeaponType.指虎, MapleWeaponType.火枪 };
        if (cat < 30 || cat > 49) {
            return MapleWeaponType.沒有武器;
        }
        return type[cat - 30];
    }
    
    protected final MapleData getStringData(final int itemId) {
        String cat = null;
        MapleData data;
        if (itemId >= 5010000) {
            data = this.cashStringData;
        }
        else if (itemId >= 2000000 && itemId < 3000000) {
            data = this.consumeStringData;
        }
        else if ((itemId >= 1142000 && itemId < 1143200) || (itemId >= 1010000 && itemId < 1040000) || (itemId >= 1122000 && itemId < 1123000) || (itemId >= 1130000 && itemId < 1140000)) {
            data = this.eqpStringData;
            cat = "Accessory";
        }
        else if (itemId >= 1000000 && itemId < 1010000) {
            data = this.eqpStringData;
            cat = "Cap";
        }
        else if (itemId >= 1102000 && itemId < 1103000) {
            data = this.eqpStringData;
            cat = "Cape";
        }
        else if (itemId >= 1040000 && itemId < 1050000) {
            data = this.eqpStringData;
            cat = "Coat";
        }
        else if (itemId >= 20000 && itemId < 22000) {
            data = this.eqpStringData;
            cat = "Face";
        }
        else if (itemId >= 1080000 && itemId < 1090000) {
            data = this.eqpStringData;
            cat = "Glove";
        }
        else if (itemId >= 30000 && itemId < 32000) {
            data = this.eqpStringData;
            cat = "Hair";
        }
        else if (itemId >= 1050000 && itemId < 1060000) {
            data = this.eqpStringData;
            cat = "Longcoat";
        }
        else if (itemId >= 1060000 && itemId < 1070000) {
            data = this.eqpStringData;
            cat = "Pants";
        }
        else if (itemId >= 1610000 && itemId < 1660000) {
            data = this.eqpStringData;
            cat = "Mechanic";
        }
        else if (itemId >= 1802000 && itemId < 1810000) {
            data = this.eqpStringData;
            cat = "PetEquip";
        }
        else if (itemId >= 1920000 && itemId < 2000000) {
            data = this.eqpStringData;
            cat = "Dragon";
        }
        else if (itemId >= 1112000 && itemId < 1120000) {
            data = this.eqpStringData;
            cat = "Ring";
        }
        else if (itemId >= 1092000 && itemId < 1100000) {
            data = this.eqpStringData;
            cat = "Shield";
        }
        else if (itemId >= 1070000 && itemId < 1080000) {
            data = this.eqpStringData;
            cat = "Shoes";
        }
        else if (itemId >= 1900000 && itemId < 1920000) {
            data = this.eqpStringData;
            cat = "Taming";
        }
        else if (itemId >= 1300000 && itemId < 1800000) {
            data = this.eqpStringData;
            cat = "Weapon";
        }
        else if (itemId >= 4000000 && itemId < 5000000) {
            data = this.etcStringData;
            cat = "Etc";
        }
        else if (itemId >= 3000000 && itemId < 4000000) {
            data = this.insStringData;
        }
        else {
            if (itemId < 5000000 || itemId >= 5010000) {
                return null;
            }
            data = this.petStringData;
        }
        if (cat == null) {
            return data.getChildByPath(String.valueOf(itemId));
        }
        if (cat == "Etc") {
            return data.getChildByPath("Etc/" + itemId);
        }
        return data.getChildByPath("Eqp/" + cat + "/" + itemId);
    }
    
    protected final MapleData getItemData(final int itemId) {
        MapleData ret = null;
        final String idStr = "0" + String.valueOf(itemId);
        MapleDataDirectoryEntry root = this.itemData.getRoot();
        for (final MapleDataDirectoryEntry topDir : root.getSubdirectories()) {
            for (final MapleDataFileEntry iFile : topDir.getFiles()) {
                if (iFile.getName().equals(idStr.substring(0, 4) + ".img")) {
                    ret = this.itemData.getData(topDir.getName() + "/" + iFile.getName());
                    if (ret == null) {
                        return null;
                    }
                    ret = ret.getChildByPath(idStr);
                    return ret;
                }
                else {
                    if (iFile.getName().equals(idStr.substring(1) + ".img")) {
                        return this.itemData.getData(topDir.getName() + "/" + iFile.getName());
                    }
                    continue;
                }
            }
        }
        root = this.equipData.getRoot();
        for (final MapleDataDirectoryEntry topDir : root.getSubdirectories()) {
            for (final MapleDataFileEntry iFile : topDir.getFiles()) {
                if (iFile.getName().equals(idStr + ".img")) {
                    return this.equipData.getData(topDir.getName() + "/" + iFile.getName());
                }
            }
        }
        return ret;
    }
    
    public final short getSlotMax(final MapleClient c, final int itemId) {
        boolean 判定道具 = false;
        if (this.slotMaxCache.containsKey(Integer.valueOf(itemId))) {
            return ((Short)this.slotMaxCache.get(Integer.valueOf(itemId))).shortValue();
        }
        short ret = 0;
        final MapleData item = this.getItemData(itemId);
        if (item != null) {
            final MapleData smEntry = item.getChildByPath("info/slotMax");
            if (smEntry == null) {
                if (GameConstants.getInventoryType(itemId) == MapleInventoryType.EQUIP) {
                    ret = 1;
                }
                else {
                    ret = 100;
                }
            }
            else {
                ret = (short)MapleDataTool.getInt(smEntry);
            }
            if (((Integer)Start.ConfigValuesMap.get("叠加开关")).intValue() > 0) {
                if (itemId / 10000 == 207 || itemId / 10000 == 233) {
                    判定道具 = true;
                }
                if (((Integer)Start.ConfigValuesMap.get("不参与叠加开关")).intValue() > 0) {
                    for (int a = 0; a < Start.不参与叠加道具.size(); ++a) {
                        if (itemId == Integer.parseInt((String)Start.不参与叠加道具.get(a))) {
                            判定道具 = true;
                        }
                    }
                }
                if (判定道具) {
                    ret = (short)MapleDataTool.getInt(smEntry);
                }
                else {
                    ret = Short.valueOf(String.valueOf(Start.ConfigValuesMap.get("叠加上线"))).shortValue();
                }
            }
        }
        this.slotMaxCache.put(Integer.valueOf(itemId), Short.valueOf(ret));
        return ret;
    }
    
    public final int getWholePrice(final int itemId) {
        if (this.wholePriceCache.containsKey(Integer.valueOf(itemId))) {
            return ((Integer)this.wholePriceCache.get(Integer.valueOf(itemId))).intValue();
        }
        final MapleData item = this.getItemData(itemId);
        if (item == null) {
            return -1;
        }
        final MapleData pData = item.getChildByPath("info/price");
        if (pData == null) {
            return -1;
        }
        final int pEntry = MapleDataTool.getInt(pData);
        this.wholePriceCache.put(Integer.valueOf(itemId), Integer.valueOf(pEntry));
        return pEntry;
    }
    
    public final double getPrice(final int itemId) {
        if (this.priceCache.containsKey(Integer.valueOf(itemId))) {
            return ((Double)this.priceCache.get(Integer.valueOf(itemId))).doubleValue();
        }
        final MapleData item = this.getItemData(itemId);
        if (item == null) {
            return -1.0;
        }
        MapleData pData = item.getChildByPath("info/unitPrice");
        double pEntry;
        if (pData != null) {
            try {
                pEntry = MapleDataTool.getDouble(pData);
            }
            catch (Exception e) {
                pEntry = (double)MapleDataTool.getIntConvert(pData);
            }
        }
        else {
            pData = item.getChildByPath("info/price");
            if (pData == null) {
                return -1.0;
            }
            pEntry = (double)MapleDataTool.getIntConvert(pData);
        }
        if (itemId == 2070019 || itemId == 2330007) {
            pEntry = 1.0;
        }
        this.priceCache.put(Integer.valueOf(itemId), Double.valueOf(pEntry));
        return pEntry;
    }
    
    public final Map<String, Byte> getItemMakeStats(final int itemId) {
        if (this.itemMakeStatsCache.containsKey(Integer.valueOf(itemId))) {
            return this.itemMakeStatsCache.get(Integer.valueOf(itemId));
        }
        if (itemId / 10000 != 425) {
            return null;
        }
        final Map<String, Byte> ret = (Map<String, Byte>)new LinkedHashMap();
        final MapleData item = this.getItemData(itemId);
        if (item == null) {
            return null;
        }
        final MapleData info = item.getChildByPath("info");
        if (info == null) {
            return null;
        }
        ret.put("incPAD", Byte.valueOf((byte)MapleDataTool.getInt("incPAD", info, 0)));
        ret.put("incMAD", Byte.valueOf((byte)MapleDataTool.getInt("incMAD", info, 0)));
        ret.put("incACC", Byte.valueOf((byte)MapleDataTool.getInt("incACC", info, 0)));
        ret.put("incEVA", Byte.valueOf((byte)MapleDataTool.getInt("incEVA", info, 0)));
        ret.put("incSpeed", Byte.valueOf((byte)MapleDataTool.getInt("incSpeed", info, 0)));
        ret.put("incJump", Byte.valueOf((byte)MapleDataTool.getInt("incJump", info, 0)));
        ret.put("incMaxHP", Byte.valueOf((byte)MapleDataTool.getInt("incMaxHP", info, 0)));
        ret.put("incMaxMP", Byte.valueOf((byte)MapleDataTool.getInt("incMaxMP", info, 0)));
        ret.put("incSTR", Byte.valueOf((byte)MapleDataTool.getInt("incSTR", info, 0)));
        ret.put("incINT", Byte.valueOf((byte)MapleDataTool.getInt("incINT", info, 0)));
        ret.put("incLUK", Byte.valueOf((byte)MapleDataTool.getInt("incLUK", info, 0)));
        ret.put("incDEX", Byte.valueOf((byte)MapleDataTool.getInt("incDEX", info, 0)));
        ret.put("randOption", Byte.valueOf((byte)MapleDataTool.getInt("randOption", info, 0)));
        ret.put("randStat", Byte.valueOf((byte)MapleDataTool.getInt("randStat", info, 0)));
        this.itemMakeStatsCache.put(Integer.valueOf(itemId), ret);
        return ret;
    }
    
    private int rand(final int min, final int max) {
        return Math.abs(Randomizer.rand(min, max));
    }
    
    public Equip levelUpEquip(final Equip equip, final Map<String, Integer> sta) {
        final Equip nEquip = (Equip)equip.copy();
        try {
            for (final Entry<String, Integer> stat : sta.entrySet()) {
                final String s = (String)stat.getKey();
                switch (s) {
                    case "STRMin": {
                        nEquip.setStr((short)(nEquip.getStr() + this.rand(((Integer)stat.getValue()).intValue(), ((Integer)sta.get("STRMax")).intValue())));
                        continue;
                    }
                    case "DEXMin": {
                        nEquip.setDex((short)(nEquip.getDex() + this.rand(((Integer)stat.getValue()).intValue(), ((Integer)sta.get("DEXMax")).intValue())));
                        continue;
                    }
                    case "INTMin": {
                        nEquip.setInt((short)(nEquip.getInt() + this.rand(((Integer)stat.getValue()).intValue(), ((Integer)sta.get("INTMax")).intValue())));
                        continue;
                    }
                    case "LUKMin": {
                        nEquip.setLuk((short)(nEquip.getLuk() + this.rand(((Integer)stat.getValue()).intValue(), ((Integer)sta.get("LUKMax")).intValue())));
                        continue;
                    }
                    case "PADMin": {
                        nEquip.setWatk((short)(nEquip.getWatk() + this.rand(((Integer)stat.getValue()).intValue(), ((Integer)sta.get("PADMax")).intValue())));
                        continue;
                    }
                    case "PDDMin": {
                        nEquip.setWdef((short)(nEquip.getWdef() + this.rand(((Integer)stat.getValue()).intValue(), ((Integer)sta.get("PDDMax")).intValue())));
                        continue;
                    }
                    case "MADMin": {
                        nEquip.setMatk((short)(nEquip.getMatk() + this.rand(((Integer)stat.getValue()).intValue(), ((Integer)sta.get("MADMax")).intValue())));
                        continue;
                    }
                    case "MDDMin": {
                        nEquip.setMdef((short)(nEquip.getMdef() + this.rand(((Integer)stat.getValue()).intValue(), ((Integer)sta.get("MDDMax")).intValue())));
                        continue;
                    }
                    case "ACCMin": {
                        nEquip.setAcc((short)(nEquip.getAcc() + this.rand(((Integer)stat.getValue()).intValue(), ((Integer)sta.get("ACCMax")).intValue())));
                        continue;
                    }
                    case "EVAMin": {
                        nEquip.setAvoid((short)(nEquip.getAvoid() + this.rand(((Integer)stat.getValue()).intValue(), ((Integer)sta.get("EVAMax")).intValue())));
                        continue;
                    }
                    case "SpeedMin": {
                        nEquip.setSpeed((short)(nEquip.getSpeed() + this.rand(((Integer)stat.getValue()).intValue(), ((Integer)sta.get("SpeedMax")).intValue())));
                        continue;
                    }
                    case "JumpMin": {
                        nEquip.setJump((short)(nEquip.getJump() + this.rand(((Integer)stat.getValue()).intValue(), ((Integer)sta.get("JumpMax")).intValue())));
                        continue;
                    }
                    case "MHPMin": {
                        nEquip.setHp((short)(nEquip.getHp() + this.rand(((Integer)stat.getValue()).intValue(), ((Integer)sta.get("MHPMax")).intValue())));
                        continue;
                    }
                    case "MMPMin": {
                        nEquip.setMp((short)(nEquip.getMp() + this.rand(((Integer)stat.getValue()).intValue(), ((Integer)sta.get("MMPMax")).intValue())));
                        continue;
                    }
                    case "MaxHPMin": {
                        nEquip.setHp((short)(nEquip.getHp() + this.rand(((Integer)stat.getValue()).intValue(), ((Integer)sta.get("MaxHPMax")).intValue())));
                        continue;
                    }
                    case "MaxMPMin": {
                        nEquip.setMp((short)(nEquip.getMp() + this.rand(((Integer)stat.getValue()).intValue(), ((Integer)sta.get("MaxMPMax")).intValue())));
                        continue;
                    }
                }
            }
        }
        catch (NullPointerException ex) {}
        return nEquip;
    }
    
    public final Map<Integer, Map<String, Integer>> getEquipIncrements(final int itemId) {
        if (this.equipIncsCache.containsKey(Integer.valueOf(itemId))) {
            return this.equipIncsCache.get(Integer.valueOf(itemId));
        }
        final Map<Integer, Map<String, Integer>> ret = (Map<Integer, Map<String, Integer>>)new LinkedHashMap();
        final MapleData item = this.getItemData(itemId);
        if (item == null) {
            return null;
        }
        final MapleData info = item.getChildByPath("info/level/info");
        if (info == null) {
            return null;
        }
        for (final MapleData dat : info.getChildren()) {
            final Map<String, Integer> incs = (Map<String, Integer>)new HashMap();
            for (final MapleData data : dat.getChildren()) {
                if (data.getName().length() > 3) {
                    incs.put(data.getName().substring(3), Integer.valueOf(MapleDataTool.getIntConvert(data.getName(), dat, 0)));
                }
            }
            ret.put(Integer.valueOf(Integer.parseInt(dat.getName())), incs);
        }
        this.equipIncsCache.put(Integer.valueOf(itemId), ret);
        return ret;
    }
    
    public final Map<Integer, List<Integer>> getEquipSkills(final int itemId) {
        if (this.equipSkillsCache.containsKey(Integer.valueOf(itemId))) {
            return this.equipSkillsCache.get(Integer.valueOf(itemId));
        }
        final Map<Integer, List<Integer>> ret = (Map<Integer, List<Integer>>)new LinkedHashMap();
        final MapleData item = this.getItemData(itemId);
        if (item == null) {
            return null;
        }
        final MapleData info = item.getChildByPath("info/level/case");
        if (info == null) {
            return null;
        }
        for (final MapleData dat : info.getChildren()) {
            for (final MapleData data : dat.getChildren()) {
                if (data.getName().length() == 1) {
                    final List<Integer> adds = (List<Integer>)new ArrayList();
                    for (final MapleData skil : data.getChildByPath("Skill").getChildren()) {
                        adds.add(Integer.valueOf(MapleDataTool.getIntConvert("id", skil, 0)));
                    }
                    ret.put(Integer.valueOf(Integer.parseInt(data.getName())), adds);
                }
            }
        }
        this.equipSkillsCache.put(Integer.valueOf(itemId), ret);
        return ret;
    }
    
    public final Map<String, Integer> getEquipStats(final int itemId) {
        if (this.equipStatsCache.containsKey(Integer.valueOf(itemId))) {
            return this.equipStatsCache.get(Integer.valueOf(itemId));
        }
        final Map<String, Integer> ret = (Map<String, Integer>)new LinkedHashMap();
        final MapleData item = this.getItemData(itemId);
        if (item == null) {
            return null;
        }
        final MapleData info = item.getChildByPath("info");
        if (info == null) {
            return null;
        }
        for (final MapleData data : info.getChildren()) {
            if (data.getName().startsWith("inc")) {
                ret.put(data.getName().substring(3), Integer.valueOf(MapleDataTool.getIntConvert(data)));
            }
        }
        ret.put("tuc", Integer.valueOf(MapleDataTool.getInt("tuc", info, 0)));
        ret.put("reqLevel", Integer.valueOf(MapleDataTool.getInt("reqLevel", info, 0)));
        ret.put("reqJob", Integer.valueOf(MapleDataTool.getInt("reqJob", info, 0)));
        ret.put("reqSTR", Integer.valueOf(MapleDataTool.getInt("reqSTR", info, 0)));
        ret.put("reqDEX", Integer.valueOf(MapleDataTool.getInt("reqDEX", info, 0)));
        ret.put("reqINT", Integer.valueOf(MapleDataTool.getInt("reqINT", info, 0)));
        ret.put("reqLUK", Integer.valueOf(MapleDataTool.getInt("reqLUK", info, 0)));
        ret.put("reqPOP", Integer.valueOf(MapleDataTool.getInt("reqPOP", info, 0)));
        ret.put("cash", Integer.valueOf(MapleDataTool.getInt("cash", info, 0)));
        ret.put("canLevel", Integer.valueOf((int)((info.getChildByPath("level") != null) ? 1 : 0)));
        ret.put("cursed", Integer.valueOf(MapleDataTool.getInt("cursed", info, 0)));
        ret.put("success", Integer.valueOf(MapleDataTool.getInt("success", info, 0)));
        ret.put("setItemID", Integer.valueOf(MapleDataTool.getInt("setItemID", info, 0)));
        ret.put("equipTradeBlock", Integer.valueOf(MapleDataTool.getInt("equipTradeBlock", info, 0)));
        ret.put("durability", Integer.valueOf(MapleDataTool.getInt("durability", info, -1)));
        if (GameConstants.isMagicWeapon(itemId)) {
            ret.put("elemDefault", Integer.valueOf(MapleDataTool.getInt("elemDefault", info, 100)));
            ret.put("incRMAS", Integer.valueOf(MapleDataTool.getInt("incRMAS", info, 100)));
            ret.put("incRMAF", Integer.valueOf(MapleDataTool.getInt("incRMAF", info, 100)));
            ret.put("incRMAL", Integer.valueOf(MapleDataTool.getInt("incRMAL", info, 100)));
            ret.put("incRMAI", Integer.valueOf(MapleDataTool.getInt("incRMAI", info, 100)));
        }
        this.equipStatsCache.put(Integer.valueOf(itemId), ret);
        return ret;
    }
    
    public final boolean isCashItem(final int itemId) {
        return this.getEquipStats(itemId) != null && ((Integer)this.getEquipStats(itemId).get("cash")).intValue() == 1;
    }
    
    public final boolean canEquip(final Map<String, Integer> stats, final int itemid, final int level, final int job, final int fame, final int str, final int dex, final int luk, final int int_, final int supremacy) {
        if (level + supremacy >= ((Integer)stats.get("reqLevel")).intValue() && str >= ((Integer)stats.get("reqSTR")).intValue() && dex >= ((Integer)stats.get("reqDEX")).intValue() && luk >= ((Integer)stats.get("reqLUK")).intValue() && int_ >= ((Integer)stats.get("reqINT")).intValue()) {
            final int fameReq = ((Integer)stats.get("reqPOP")).intValue();
            return fameReq == 0 || fame >= fameReq;
        }
        return false;
    }
    
    public final int getReqSuccess(final int itemId) {
        if (this.getEquipStats(itemId) == null) {
            return 0;
        }
        return ((Integer)this.getEquipStats(itemId).get("success")).intValue();
    }
    
    public final int getReqLevel(final int itemId) {
        if (this.getEquipStats(itemId) == null) {
            return 0;
        }
        return ((Integer)this.getEquipStats(itemId).get("reqLevel")).intValue();
    }
    
    public final int getReqStr(final int itemId) {
        if (this.getEquipStats(itemId) == null) {
            return 0;
        }
        return ((Integer)this.getEquipStats(itemId).get("reqSTR")).intValue();
    }
    
    public final int getReqDex(final int itemId) {
        if (this.getEquipStats(itemId) == null) {
            return 0;
        }
        return ((Integer)this.getEquipStats(itemId).get("reqDEX")).intValue();
    }
    
    public final int getReqInt(final int itemId) {
        if (this.getEquipStats(itemId) == null) {
            return 0;
        }
        return ((Integer)this.getEquipStats(itemId).get("reqINT")).intValue();
    }
    
    public final int getReqLuk(final int itemId) {
        if (this.getEquipStats(itemId) == null) {
            return 0;
        }
        return ((Integer)this.getEquipStats(itemId).get("reqLUK")).intValue();
    }
    
    public final int getReqJob(final int itemId) {
        if (this.getEquipStats(itemId) == null) {
            return 0;
        }
        return ((Integer)this.getEquipStats(itemId).get("reqJob")).intValue();
    }
    
    public final int getSlots(final int itemId) {
        if (this.getEquipStats(itemId) == null) {
            return 0;
        }
        return ((Integer)this.getEquipStats(itemId).get("tuc")).intValue();
    }
    
    public final int getSetItemID(final int itemId) {
        if (this.getEquipStats(itemId) == null) {
            return 0;
        }
        return ((Integer)this.getEquipStats(itemId).get("setItemID")).intValue();
    }
    
    public final StructSetItem getSetItem(final int setItemId) {
        return (StructSetItem)this.setItems.get(Byte.valueOf((byte)setItemId));
    }
    
    public final List<Integer> getScrollReqs(final int itemId) {
        if (this.scrollReqCache.containsKey(Integer.valueOf(itemId))) {
            return this.scrollReqCache.get(Integer.valueOf(itemId));
        }
        final List<Integer> ret = (List<Integer>)new ArrayList();
        final MapleData data = this.getItemData(itemId).getChildByPath("req");
        if (data == null) {
            return ret;
        }
        for (final MapleData req : data.getChildren()) {
            ret.add(Integer.valueOf(MapleDataTool.getInt(req)));
        }
        this.scrollReqCache.put(Integer.valueOf(itemId), ret);
        return ret;
    }
    
    public final IItem scrollEquipWithId(final IItem equip, final IItem scrollId, final boolean ws, final MapleCharacter chr, final int vegas, final boolean checkIfGM) {
        if (equip.getType() == 1) {
            final Equip nEquip = (Equip)equip;
            final Map<String, Integer> stats = this.getEquipStats(scrollId.getItemId());
            final Map<String, Integer> eqstats = this.getEquipStats(equip.getItemId());
            final int succ = GameConstants.isTablet(scrollId.getItemId()) ? GameConstants.getSuccessTablet(scrollId.getItemId(), (int)nEquip.getLevel()) : ((GameConstants.isEquipScroll(scrollId.getItemId()) || GameConstants.isPotentialScroll(scrollId.getItemId())) ? 0 : ((Integer)stats.get("success")).intValue());
            final int curse = GameConstants.isTablet(scrollId.getItemId()) ? GameConstants.getCurseTablet(scrollId.getItemId(), (int)nEquip.getLevel()) : ((GameConstants.isEquipScroll(scrollId.getItemId()) || GameConstants.isPotentialScroll(scrollId.getItemId())) ? 0 : ((Integer)stats.get("cursed")).intValue());
            final int success = succ + ((vegas == 5610000 && succ == 10) ? 20 : ((vegas == 5610001 && succ == 60) ? 30 : 0));
            if (scrollId.getItemId() == 2041124 || scrollId.getItemId() == 2041125 || scrollId.getItemId() == 2041126 || scrollId.getItemId() == 2041127) {
                int pd = 0;
                pd = scrollId.getItemId() - 2041124;
                switch (pd) {
                    case 0: {
                        nEquip.setStr((short)(nEquip.getStr() + 7));
                        nEquip.setWatk((short)(nEquip.getWatk() + 7));
                        break;
                    }
                    case 1: {
                        nEquip.setDex((short)(nEquip.getDex() + 7));
                        nEquip.setWatk((short)(nEquip.getWatk() + 7));
                        break;
                    }
                    case 2: {
                        nEquip.setInt((short)(nEquip.getInt() + 7));
                        nEquip.setMatk((short)(nEquip.getMatk() + 7));
                        break;
                    }
                    case 3: {
                        nEquip.setLuk((short)(nEquip.getLuk() + 7));
                        nEquip.setWatk((short)(nEquip.getWatk() + 7));
                        break;
                    }
                }
                nEquip.setUpgradeSlots((byte)(nEquip.getUpgradeSlots() - 1));
                nEquip.setLevel((byte)(nEquip.getLevel() + 1));
                return equip;
            }
            if (scrollId.getItemId() == 2041120 || scrollId.getItemId() == 2041121 || scrollId.getItemId() == 2041122 || scrollId.getItemId() == 2041123) {
                int pd = 0;
                pd = scrollId.getItemId() - 2041120;
                switch (pd) {
                    case 0: {
                        nEquip.setStr((short)(nEquip.getStr() + Randomizer.nextInt(4) + 3));
                        nEquip.setWatk((short)(nEquip.getWatk() + Randomizer.nextInt(4) + 3));
                        break;
                    }
                    case 1: {
                        nEquip.setDex((short)(nEquip.getDex() + Randomizer.nextInt(4) + 3));
                        nEquip.setWatk((short)(nEquip.getWatk() + Randomizer.nextInt(4) + 3));
                        break;
                    }
                    case 2: {
                        nEquip.setInt((short)(nEquip.getInt() + Randomizer.nextInt(4) + 3));
                        nEquip.setMatk((short)(nEquip.getMatk() + Randomizer.nextInt(4) + 3));
                        break;
                    }
                    case 3: {
                        nEquip.setLuk((short)(nEquip.getLuk() + Randomizer.nextInt(4) + 3));
                        nEquip.setWatk((short)(nEquip.getWatk() + Randomizer.nextInt(4) + 3));
                        break;
                    }
                }
                nEquip.setUpgradeSlots((byte)(nEquip.getUpgradeSlots() - 1));
                nEquip.setLevel((byte)(nEquip.getLevel() + 1));
                return equip;
            }
            if (scrollId.getItemId() == 2049118) {
                final Equip ret = this.randomizeStats((Equip)this.getEquipById(nEquip.getItemId()));
                nEquip.setMpR((short)1);
                chr.dropMessage(5, "防爆卷轴使用成功,当前装备：" + this.getName(nEquip.getItemId()) + "添加了防爆功能.");
                return equip;
            }
            if (scrollId.getItemId() == 2049035) {
                final Equip ret = (Equip)this.getEquipById(nEquip.getItemId());
                nEquip.setStr(ret.getStr());
                nEquip.setDex(ret.getDex());
                nEquip.setInt(ret.getInt());
                nEquip.setLuk(ret.getLuk());
                nEquip.setWatk(ret.getWatk());
                nEquip.setWdef(ret.getWdef());
                nEquip.setMatk(ret.getMatk());
                nEquip.setMdef(ret.getMdef());
                nEquip.setAcc(ret.getAcc());
                nEquip.setAvoid(ret.getAvoid());
                nEquip.setSpeed(ret.getSpeed());
                nEquip.setJump(ret.getJump());
                nEquip.setHp(ret.getHp());
                nEquip.setMp(ret.getMp());
                nEquip.setViciousHammer((byte)0);
                if (nEquip.getLocked()) {
                    nEquip.setLocked((byte)0);
                }
                nEquip.setOwner(ret.getOwner());
                nEquip.setUpgradeSlots(ret.getUpgradeSlots());
                nEquip.setLevel((byte)0);
                if (((Integer)Start.ConfigValuesMap.get("成就还原上卷记录开关")).intValue() > 0) {
                    chr.setAccountidLog("还原装备", 1);
                }
                return equip;
            }
            if (scrollId.getItemId() == 2049117) {
                nEquip.setStr((short)(nEquip.getStr() + 2));
                nEquip.setDex((short)(nEquip.getDex() + 2));
                nEquip.setInt((short)(nEquip.getInt() + 2));
                nEquip.setLuk((short)(nEquip.getLuk() + 2));
                nEquip.setWdef((short)(nEquip.getWdef() + 10));
                nEquip.setMdef((short)(nEquip.getMdef() + 10));
                nEquip.setUpgradeSlots((byte)(nEquip.getUpgradeSlots() - 1));
                nEquip.setLevel((byte)(nEquip.getLevel() + 1));
                return equip;
            }
            if (GameConstants.isPotentialScroll(scrollId.getItemId()) || GameConstants.isEquipScroll(scrollId.getItemId()) || Randomizer.nextInt(100) <= success || checkIfGM) {
                switch (scrollId.getItemId()) {
                    case 2049000:
                    case 2049001:
                    case 2049002:
                    case 2049003:
                    case 2049004:
                    case 2049005: {
                        if (nEquip.getLevel() + nEquip.getUpgradeSlots() < ((Integer)eqstats.get("tuc")).intValue()) {
                            nEquip.setUpgradeSlots((byte)(nEquip.getUpgradeSlots() + 1));
                            break;
                        }
                        break;
                    }
                    case 2049006:
                    case 2049007:
                    case 2049008: {
                        if (nEquip.getLevel() + nEquip.getUpgradeSlots() < ((Integer)eqstats.get("tuc")).intValue()) {
                            nEquip.setUpgradeSlots((byte)(nEquip.getUpgradeSlots() + 2));
                            break;
                        }
                        break;
                    }
                    case 2040727: {
                        byte flag = nEquip.getFlag();
                        flag |= (byte)ItemFlag.SPIKES.getValue();
                        nEquip.setFlag(flag);
                        break;
                    }
                    case 2041058: {
                        byte flag = nEquip.getFlag();
                        flag |= (byte)ItemFlag.COLD.getValue();
                        nEquip.setFlag(flag);
                        break;
                    }
                    default: {
                        if (GameConstants.isQHHD(scrollId.getItemId())) {
                            final int z = GameConstants.getQHHD(scrollId.getItemId());
                            if (nEquip.getStr() > 0) {
                                nEquip.setStr((short)(nEquip.getStr() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getDex() > 0) {
                                nEquip.setDex((short)(nEquip.getDex() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getInt() > 0) {
                                nEquip.setInt((short)(nEquip.getInt() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getLuk() > 0) {
                                nEquip.setLuk((short)(nEquip.getLuk() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getWatk() > 0) {
                                nEquip.setWatk((short)(nEquip.getWatk() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getWdef() > 0) {
                                nEquip.setWdef((short)(nEquip.getWdef() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getMatk() > 0) {
                                nEquip.setMatk((short)(nEquip.getMatk() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getMdef() > 0) {
                                nEquip.setMdef((short)(nEquip.getMdef() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getAcc() > 0) {
                                nEquip.setAcc((short)(nEquip.getAcc() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getAvoid() > 0) {
                                nEquip.setAvoid((short)(nEquip.getAvoid() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getHp() > 0) {
                                nEquip.setHp((short)(nEquip.getHp() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getMp() > 0) {
                                nEquip.setMp((short)(nEquip.getMp() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                                break;
                            }
                            break;
                        }
                        else if (GameConstants.isZXHD(scrollId.getItemId())) {
                            final int z = GameConstants.getZXHD(scrollId.getItemId());
                            if (nEquip.getStr() > 0) {
                                nEquip.setStr((short)(nEquip.getStr() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getDex() > 0) {
                                nEquip.setDex((short)(nEquip.getDex() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getInt() > 0) {
                                nEquip.setInt((short)(nEquip.getInt() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getLuk() > 0) {
                                nEquip.setLuk((short)(nEquip.getLuk() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getWatk() > 0) {
                                nEquip.setWatk((short)(nEquip.getWatk() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getWdef() > 0) {
                                nEquip.setWdef((short)(nEquip.getWdef() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getMatk() > 0) {
                                nEquip.setMatk((short)(nEquip.getMatk() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getMdef() > 0) {
                                nEquip.setMdef((short)(nEquip.getMdef() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getAcc() > 0) {
                                nEquip.setAcc((short)(nEquip.getAcc() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getAvoid() > 0) {
                                nEquip.setAvoid((short)(nEquip.getAvoid() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getHp() > 0) {
                                nEquip.setHp((short)(nEquip.getHp() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getMp() > 0) {
                                nEquip.setMp((short)(nEquip.getMp() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : 1)));
                                break;
                            }
                            break;
                        }
                        else if (GameConstants.isJRZYHD(scrollId.getItemId())) {
                            final int z = GameConstants.getJRZYHD(scrollId.getItemId());
                            if (nEquip.getStr() > 0) {
                                nEquip.setStr((short)(nEquip.getStr() + (Randomizer.nextInt(z) + 5) * (Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getDex() > 0) {
                                nEquip.setDex((short)(nEquip.getDex() + (Randomizer.nextInt(z) + 5) * (Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getInt() > 0) {
                                nEquip.setInt((short)(nEquip.getInt() + (Randomizer.nextInt(z) + 5) * (Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getLuk() > 0) {
                                nEquip.setLuk((short)(nEquip.getLuk() + (Randomizer.nextInt(z) + 5) * (Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getWatk() > 0) {
                                nEquip.setWatk((short)(nEquip.getWatk() + (Randomizer.nextInt(z) + 5) * (Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getWdef() > 0) {
                                nEquip.setWdef((short)(nEquip.getWdef() + (Randomizer.nextInt(z) + 5) * (Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getMatk() > 0) {
                                nEquip.setMatk((short)(nEquip.getMatk() + (Randomizer.nextInt(z) + 5) * (Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getMdef() > 0) {
                                nEquip.setMdef((short)(nEquip.getMdef() + (Randomizer.nextInt(z) + 5) * (Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getAcc() > 0) {
                                nEquip.setAcc((short)(nEquip.getAcc() + (Randomizer.nextInt(z) + 5) * (Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getAvoid() > 0) {
                                nEquip.setAvoid((short)(nEquip.getAvoid() + (Randomizer.nextInt(z) + 5) * (Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getHp() > 0) {
                                nEquip.setHp((short)(nEquip.getHp() + (Randomizer.nextInt(z) + 5) * (Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getMp() > 0) {
                                nEquip.setMp((short)(nEquip.getMp() + (Randomizer.nextInt(z) + 5) * (Randomizer.nextBoolean() ? 1 : 1)));
                                break;
                            }
                            break;
                        }
                        else if (GameConstants.isZYHD(scrollId.getItemId())) {
                            final int z = GameConstants.getZYHD(scrollId.getItemId());
                            if (nEquip.getStr() > 0) {
                                nEquip.setStr((short)(nEquip.getStr() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getDex() > 0) {
                                nEquip.setDex((short)(nEquip.getDex() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getInt() > 0) {
                                nEquip.setInt((short)(nEquip.getInt() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getLuk() > 0) {
                                nEquip.setLuk((short)(nEquip.getLuk() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getWatk() > 0) {
                                nEquip.setWatk((short)(nEquip.getWatk() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getWdef() > 0) {
                                nEquip.setWdef((short)(nEquip.getWdef() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getMatk() > 0) {
                                nEquip.setMatk((short)(nEquip.getMatk() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getMdef() > 0) {
                                nEquip.setMdef((short)(nEquip.getMdef() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getAcc() > 0) {
                                nEquip.setAcc((short)(nEquip.getAcc() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getAvoid() > 0) {
                                nEquip.setAvoid((short)(nEquip.getAvoid() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getHp() > 0) {
                                nEquip.setHp((short)(nEquip.getHp() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : 1)));
                            }
                            if (nEquip.getMp() > 0) {
                                nEquip.setMp((short)(nEquip.getMp() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : 1)));
                                break;
                            }
                            break;
                        }
                        else if (GameConstants.isChaosScroll(scrollId.getItemId())) {
                            final int z = GameConstants.getChaosNumber(scrollId.getItemId());
                            if (nEquip.getStr() > 0) {
                                nEquip.setStr((short)(nEquip.getStr() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getDex() > 0) {
                                nEquip.setDex((short)(nEquip.getDex() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getInt() > 0) {
                                nEquip.setInt((short)(nEquip.getInt() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getLuk() > 0) {
                                nEquip.setLuk((short)(nEquip.getLuk() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getWatk() > 0) {
                                nEquip.setWatk((short)(nEquip.getWatk() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getWdef() > 0) {
                                nEquip.setWdef((short)(nEquip.getWdef() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getMatk() > 0) {
                                nEquip.setMatk((short)(nEquip.getMatk() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getMdef() > 0) {
                                nEquip.setMdef((short)(nEquip.getMdef() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getAcc() > 0) {
                                nEquip.setAcc((short)(nEquip.getAcc() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getAvoid() > 0) {
                                nEquip.setAvoid((short)(nEquip.getAvoid() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getHp() > 0) {
                                nEquip.setHp((short)(nEquip.getHp() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getMp() > 0) {
                                nEquip.setMp((short)(nEquip.getMp() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                                break;
                            }
                            break;
                        }
                        else if (GameConstants.isEquipScroll(scrollId.getItemId())) {
                            final int chanc = Math.max(((scrollId.getItemId() == 2049300) ? 100 : 80) - nEquip.getEnhance() * 10, 10);
                            if (Randomizer.nextInt(100) > chanc) {
                                return null;
                            }
                            if (nEquip.getStr() > 0 || Randomizer.nextInt(50) == 1) {
                                nEquip.setStr((short)(nEquip.getStr() + Randomizer.nextInt(5)));
                            }
                            if (nEquip.getDex() > 0 || Randomizer.nextInt(50) == 1) {
                                nEquip.setDex((short)(nEquip.getDex() + Randomizer.nextInt(5)));
                            }
                            if (nEquip.getInt() > 0 || Randomizer.nextInt(50) == 1) {
                                nEquip.setInt((short)(nEquip.getInt() + Randomizer.nextInt(5)));
                            }
                            if (nEquip.getLuk() > 0 || Randomizer.nextInt(50) == 1) {
                                nEquip.setLuk((short)(nEquip.getLuk() + Randomizer.nextInt(5)));
                            }
                            if (nEquip.getWatk() > 0 && GameConstants.isWeapon(nEquip.getItemId())) {
                                nEquip.setWatk((short)(nEquip.getWatk() + Randomizer.nextInt(5)));
                            }
                            if (nEquip.getWdef() > 0 || Randomizer.nextInt(40) == 1) {
                                nEquip.setWdef((short)(nEquip.getWdef() + Randomizer.nextInt(5)));
                            }
                            if (nEquip.getMatk() > 0 && GameConstants.isWeapon(nEquip.getItemId())) {
                                nEquip.setMatk((short)(nEquip.getMatk() + Randomizer.nextInt(5)));
                            }
                            if (nEquip.getMdef() > 0 || Randomizer.nextInt(40) == 1) {
                                nEquip.setMdef((short)(nEquip.getMdef() + Randomizer.nextInt(5)));
                            }
                            if (nEquip.getAcc() > 0 || Randomizer.nextInt(20) == 1) {
                                nEquip.setAcc((short)(nEquip.getAcc() + Randomizer.nextInt(5)));
                            }
                            if (nEquip.getAvoid() > 0 || Randomizer.nextInt(20) == 1) {
                                nEquip.setAvoid((short)(nEquip.getAvoid() + Randomizer.nextInt(5)));
                            }
                            if (nEquip.getSpeed() > 0 || Randomizer.nextInt(10) == 1) {
                                nEquip.setSpeed((short)(nEquip.getSpeed() + Randomizer.nextInt(5)));
                            }
                            if (nEquip.getJump() > 0 || Randomizer.nextInt(10) == 1) {
                                nEquip.setJump((short)(nEquip.getJump() + Randomizer.nextInt(5)));
                            }
                            if (nEquip.getHp() > 0 || Randomizer.nextInt(5) == 1) {
                                nEquip.setHp((short)(nEquip.getHp() + Randomizer.nextInt(5)));
                            }
                            if (nEquip.getMp() > 0 || Randomizer.nextInt(5) == 1) {
                                nEquip.setMp((short)(nEquip.getMp() + Randomizer.nextInt(5)));
                            }
                            nEquip.setEnhance((byte)(nEquip.getEnhance() + 1));
                            break;
                        }
                        else {
                            if (!GameConstants.isPotentialScroll(scrollId.getItemId())) {
                                for (final Entry<String, Integer> stat : stats.entrySet()) {
                                    final String s;
                                    final String key = s = (String)stat.getKey();
                                    switch (s) {
                                        case "STR": {
                                            nEquip.setStr((short)(nEquip.getStr() + ((Integer)stat.getValue()).intValue()));
                                            continue;
                                        }
                                        case "DEX": {
                                            nEquip.setDex((short)(nEquip.getDex() + ((Integer)stat.getValue()).intValue()));
                                            continue;
                                        }
                                        case "INT": {
                                            nEquip.setInt((short)(nEquip.getInt() + ((Integer)stat.getValue()).intValue()));
                                            continue;
                                        }
                                        case "LUK": {
                                            nEquip.setLuk((short)(nEquip.getLuk() + ((Integer)stat.getValue()).intValue()));
                                            continue;
                                        }
                                        case "PAD": {
                                            nEquip.setWatk((short)(nEquip.getWatk() + ((Integer)stat.getValue()).intValue()));
                                            continue;
                                        }
                                        case "PDD": {
                                            nEquip.setWdef((short)(nEquip.getWdef() + ((Integer)stat.getValue()).intValue()));
                                            continue;
                                        }
                                        case "MAD": {
                                            nEquip.setMatk((short)(nEquip.getMatk() + ((Integer)stat.getValue()).intValue()));
                                            continue;
                                        }
                                        case "MDD": {
                                            nEquip.setMdef((short)(nEquip.getMdef() + ((Integer)stat.getValue()).intValue()));
                                            continue;
                                        }
                                        case "ACC": {
                                            nEquip.setAcc((short)(nEquip.getAcc() + ((Integer)stat.getValue()).intValue()));
                                            continue;
                                        }
                                        case "EVA": {
                                            nEquip.setAvoid((short)(nEquip.getAvoid() + ((Integer)stat.getValue()).intValue()));
                                            continue;
                                        }
                                        case "Speed": {
                                            nEquip.setSpeed((short)(nEquip.getSpeed() + ((Integer)stat.getValue()).intValue()));
                                            continue;
                                        }
                                        case "Jump": {
                                            nEquip.setJump((short)(nEquip.getJump() + ((Integer)stat.getValue()).intValue()));
                                            continue;
                                        }
                                        case "MHP": {
                                            nEquip.setHp((short)(nEquip.getHp() + ((Integer)stat.getValue()).intValue()));
                                            continue;
                                        }
                                        case "MMP": {
                                            nEquip.setMp((short)(nEquip.getMp() + ((Integer)stat.getValue()).intValue()));
                                            continue;
                                        }
                                        case "MHPr": {
                                            nEquip.setHpR((short)(nEquip.getHpR() + ((Integer)stat.getValue()).intValue()));
                                            continue;
                                        }
                                        case "MMPr": {
                                            nEquip.setMpR((short)(nEquip.getMpR() + ((Integer)stat.getValue()).intValue()));
                                            continue;
                                        }
                                    }
                                }
                                break;
                            }
                            if (nEquip.getState() != 0) {
                                break;
                            }
                            final int chanc = (scrollId.getItemId() == 2049400) ? 90 : 70;
                            if (Randomizer.nextInt(100) > chanc) {
                                return null;
                            }
                            nEquip.resetPotential();
                            break;
                        }
                    }
                }
                if (!GameConstants.isCleanSlate(scrollId.getItemId()) && !GameConstants.isSpecialScroll(scrollId.getItemId()) && !GameConstants.isEquipScroll(scrollId.getItemId()) && !GameConstants.isPotentialScroll(scrollId.getItemId())) {
                    nEquip.setUpgradeSlots((byte)(nEquip.getUpgradeSlots() - 1));
                    nEquip.setLevel((byte)(nEquip.getLevel() + 1));
                    if (nEquip.getMpR() > 0) {
                        nEquip.setMpR((short)0);
                        chr.dropMessage(5, "上卷成功，防爆消失。");
                    }
                    if (((Integer)Start.ConfigValuesMap.get("成就上卷加三记录开关")).intValue() > 0 && nEquip.getLevel() == 3) {
                        chr.setAccountidLog("上卷加三", 1);
                    }
                    if (((Integer)Start.ConfigValuesMap.get("成就上卷加七记录开关")).intValue() > 0 && nEquip.getLevel() == 7) {
                        chr.setAccountidLog("上卷加七", 1);
                    }
                }
            }
            else {
                if (!ws && !GameConstants.isCleanSlate(scrollId.getItemId()) && !GameConstants.isSpecialScroll(scrollId.getItemId()) && !GameConstants.isEquipScroll(scrollId.getItemId()) && !GameConstants.isPotentialScroll(scrollId.getItemId())) {
                    nEquip.setUpgradeSlots((byte)(nEquip.getUpgradeSlots() - 1));
                }
                if (Randomizer.nextInt(99) < curse) {
                    if (nEquip.getMpR() <= 0) {
                        return null;
                    }
                    nEquip.setMpR((short)0);
                    chr.dropMessage(5, "由于卷轴的效果，物品没有损坏,防爆消失");
                }
            }
        }
        return equip;
    }
    
    public final IItem getEquipById(final int equipId) {
        return this.getEquipById(equipId, -1);
    }
    
    public final IItem getEquipById(final int equipId, final int ringId) {
        final Equip nEquip = new Equip(equipId, (short)0, ringId, (byte)0);
        nEquip.setQuantity((short)1);
        final Map<String, Integer> stats = this.getEquipStats(equipId);
        if (stats != null) {
            for (final Entry<String, Integer> stat : stats.entrySet()) {
                final String s;
                final String key = s = (String)stat.getKey();
                switch (s) {
                    case "STR": {
                        nEquip.setStr(((Integer)stat.getValue()).shortValue());
                        continue;
                    }
                    case "DEX": {
                        nEquip.setDex(((Integer)stat.getValue()).shortValue());
                        continue;
                    }
                    case "INT": {
                        nEquip.setInt(((Integer)stat.getValue()).shortValue());
                        continue;
                    }
                    case "LUK": {
                        nEquip.setLuk(((Integer)stat.getValue()).shortValue());
                        continue;
                    }
                    case "PAD": {
                        nEquip.setWatk(((Integer)stat.getValue()).shortValue());
                        continue;
                    }
                    case "PDD": {
                        nEquip.setWdef(((Integer)stat.getValue()).shortValue());
                        continue;
                    }
                    case "MAD": {
                        nEquip.setMatk(((Integer)stat.getValue()).shortValue());
                        continue;
                    }
                    case "MDD": {
                        nEquip.setMdef(((Integer)stat.getValue()).shortValue());
                        continue;
                    }
                    case "ACC": {
                        nEquip.setAcc(((Integer)stat.getValue()).shortValue());
                        continue;
                    }
                    case "EVA": {
                        nEquip.setAvoid(((Integer)stat.getValue()).shortValue());
                        continue;
                    }
                    case "Speed": {
                        nEquip.setSpeed(((Integer)stat.getValue()).shortValue());
                        continue;
                    }
                    case "Jump": {
                        nEquip.setJump(((Integer)stat.getValue()).shortValue());
                        continue;
                    }
                    case "MHP": {
                        nEquip.setHp(((Integer)stat.getValue()).shortValue());
                        continue;
                    }
                    case "MMP": {
                        nEquip.setMp(((Integer)stat.getValue()).shortValue());
                        continue;
                    }
                    case "MHPr": {
                        nEquip.setHpR(((Integer)stat.getValue()).shortValue());
                        continue;
                    }
                    case "MMPr": {
                        nEquip.setMpR(((Integer)stat.getValue()).shortValue());
                        continue;
                    }
                    case "tuc": {
                        nEquip.setUpgradeSlots(((Integer)stat.getValue()).byteValue());
                        continue;
                    }
                    case "Craft": {
                        nEquip.setHands(((Integer)stat.getValue()).shortValue());
                        continue;
                    }
                    case "durability": {
                        nEquip.setDurability(((Integer)stat.getValue()).intValue());
                        continue;
                    }
                }
            }
        }
        this.equipCache.put(Integer.valueOf(equipId), nEquip);
        return nEquip.copy();
    }
    
    private short getRandStat(final short defaultValue, final int maxRange) {
        if (defaultValue == 0) {
            return 0;
        }
        final int lMaxRange = (int)Math.min(Math.ceil((double)defaultValue * 0.1), (double)maxRange);
        return (short)(int)((double)(defaultValue - lMaxRange) + Math.floor(Math.random() * (double)(lMaxRange * 2 + 1)));
    }
    
    public final Equip randomizeStats(final Equip equip) {
        equip.setStr(this.getRandStat(equip.getStr(), 5));
        equip.setDex(this.getRandStat(equip.getDex(), 5));
        equip.setInt(this.getRandStat(equip.getInt(), 5));
        equip.setLuk(this.getRandStat(equip.getLuk(), 5));
        equip.setMatk(this.getRandStat(equip.getMatk(), 5));
        equip.setWatk(this.getRandStat(equip.getWatk(), 5));
        equip.setAcc(this.getRandStat(equip.getAcc(), 5));
        equip.setAvoid(this.getRandStat(equip.getAvoid(), 5));
        equip.setJump(this.getRandStat(equip.getJump(), 5));
        equip.setHands(this.getRandStat(equip.getHands(), 5));
        equip.setSpeed(this.getRandStat(equip.getSpeed(), 5));
        equip.setWdef(this.getRandStat(equip.getWdef(), 10));
        equip.setMdef(this.getRandStat(equip.getMdef(), 10));
        equip.setHp(this.getRandStat(equip.getHp(), 10));
        equip.setMp(this.getRandStat(equip.getMp(), 10));
        return equip;
    }
    
    public final Equip randomizeStats(final Equip equip, final int id) {
        switch (id) {
            case 1112413: {
                equip.setStr((short)1);
                equip.setDex((short)1);
                equip.setInt((short)1);
                equip.setLuk((short)1);
                equip.setMatk((short)1);
                equip.setWatk((short)1);
                equip.setHp((short)10);
                equip.setMp((short)10);
                break;
            }
            case 1112414: {
                equip.setStr((short)2);
                equip.setDex((short)2);
                equip.setInt((short)2);
                equip.setLuk((short)2);
                equip.setMatk((short)2);
                equip.setWatk((short)2);
                equip.setHp((short)20);
                equip.setMp((short)20);
                break;
            }
            case 1112405: {
                equip.setStr((short)3);
                equip.setDex((short)3);
                equip.setInt((short)3);
                equip.setLuk((short)3);
                equip.setMatk((short)3);
                equip.setWatk((short)3);
                equip.setHp((short)30);
                equip.setMp((short)30);
                break;
            }
            default: {
                equip.setStr(this.getRandStat(equip.getStr(), 5));
                equip.setDex(this.getRandStat(equip.getDex(), 5));
                equip.setInt(this.getRandStat(equip.getInt(), 5));
                equip.setLuk(this.getRandStat(equip.getLuk(), 5));
                equip.setMatk(this.getRandStat(equip.getMatk(), 5));
                equip.setWatk(this.getRandStat(equip.getWatk(), 5));
                equip.setAcc(this.getRandStat(equip.getAcc(), 5));
                equip.setAvoid(this.getRandStat(equip.getAvoid(), 5));
                equip.setJump(this.getRandStat(equip.getJump(), 5));
                equip.setHands(this.getRandStat(equip.getHands(), 5));
                equip.setSpeed(this.getRandStat(equip.getSpeed(), 5));
                equip.setWdef(this.getRandStat(equip.getWdef(), 10));
                equip.setMdef(this.getRandStat(equip.getMdef(), 10));
                equip.setHp(this.getRandStat(equip.getHp(), 10));
                equip.setMp(this.getRandStat(equip.getMp(), 10));
                break;
            }
        }
        return equip;
    }
    
    public final MapleStatEffect getItemEffect(final int itemId) {
        MapleStatEffect ret = (MapleStatEffect)this.itemEffects.get(Integer.valueOf(itemId));
        if (ret == null) {
            final MapleData item = this.getItemData(itemId);
            if (item == null) {
                return null;
            }
            ret = MapleStatEffect.loadItemEffectFromData(item.getChildByPath("spec"), itemId);
            this.itemEffects.put(Integer.valueOf(itemId), ret);
        }
        return ret;
    }
    
    public final List<Pair<Integer, Integer>> getSummonMobs(final int itemId) {
        if (this.summonMobCache.containsKey(Integer.valueOf(itemId))) {
            return this.summonMobCache.get(Integer.valueOf(itemId));
        }
        if (!GameConstants.isSummonSack(itemId)) {
            return null;
        }
        final MapleData data = this.getItemData(itemId).getChildByPath("mob");
        if (data == null) {
            return null;
        }
        final List<Pair<Integer, Integer>> mobPairs = (List<Pair<Integer, Integer>>)new ArrayList();
        for (final MapleData child : data.getChildren()) {
            mobPairs.add(new Pair(Integer.valueOf(MapleDataTool.getIntConvert("id", child)), Integer.valueOf(MapleDataTool.getIntConvert("prob", child))));
        }
        this.summonMobCache.put(Integer.valueOf(itemId), mobPairs);
        return mobPairs;
    }
    
    public final int getCardMobId(final int id) {
        if (id == 0) {
            return 0;
        }
        if (this.monsterBookID.containsKey(Integer.valueOf(id))) {
            return ((Integer)this.monsterBookID.get(Integer.valueOf(id))).intValue();
        }
        final MapleData data = this.getItemData(id);
        final int monsterid = MapleDataTool.getIntConvert("info/mob", data, 0);
        if (monsterid == 0) {
            return 0;
        }
        this.monsterBookID.put(Integer.valueOf(id), Integer.valueOf(monsterid));
        return ((Integer)this.monsterBookID.get(Integer.valueOf(id))).intValue();
    }
    
    public final int getWatkForProjectile(final int itemId) {
        Integer atk = (Integer)this.projectileWatkCache.get(Integer.valueOf(itemId));
        if (atk != null) {
            return atk.intValue();
        }
        final MapleData data = this.getItemData(itemId);
        atk = Integer.valueOf(MapleDataTool.getInt("info/incPAD", data, 0));
        this.projectileWatkCache.put(Integer.valueOf(itemId), atk);
        return atk.intValue();
    }
    
    public final boolean canScroll(final int scrollid, final int itemid) {
        return scrollid / 100 % 100 == itemid / 10000 % 100;
    }
    
    public final String getName(final int itemId) {
        if (this.nameCache.containsKey(Integer.valueOf(itemId))) {
            return (String)this.nameCache.get(Integer.valueOf(itemId));
        }
        final MapleData strings = this.getStringData(itemId);
        if (strings == null) {
            return null;
        }
        final String ret = MapleDataTool.getString("name", strings, "(null)");
        this.nameCache.put(Integer.valueOf(itemId), ret);
        return ret;
    }
    
    public final String getDesc(final int itemId) {
        if (this.descCache.containsKey(Integer.valueOf(itemId))) {
            return (String)this.descCache.get(Integer.valueOf(itemId));
        }
        final MapleData strings = this.getStringData(itemId);
        if (strings == null) {
            return null;
        }
        final String ret = MapleDataTool.getString("desc", strings, null);
        this.descCache.put(Integer.valueOf(itemId), ret);
        return ret;
    }
    
    public final String getMsg(final int itemId) {
        if (this.msgCache.containsKey(Integer.valueOf(itemId))) {
            return (String)this.msgCache.get(Integer.valueOf(itemId));
        }
        final MapleData strings = this.getStringData(itemId);
        if (strings == null) {
            return null;
        }
        final String ret = MapleDataTool.getString("msg", strings, null);
        this.msgCache.put(Integer.valueOf(itemId), ret);
        return ret;
    }
    
    public final short getItemMakeLevel(final int itemId) {
        if (this.itemMakeLevel.containsKey(Integer.valueOf(itemId))) {
            return ((Short)this.itemMakeLevel.get(Integer.valueOf(itemId))).shortValue();
        }
        if (itemId / 10000 != 400) {
            return 0;
        }
        final short lvl = (short)MapleDataTool.getIntConvert("info/lv", this.getItemData(itemId), 0);
        this.itemMakeLevel.put(Integer.valueOf(itemId), Short.valueOf(lvl));
        return lvl;
    }
    
    public final byte isConsumeOnPickup(final int itemId) {
        if (this.consumeOnPickupCache.containsKey(Integer.valueOf(itemId))) {
            return ((Byte)this.consumeOnPickupCache.get(Integer.valueOf(itemId))).byteValue();
        }
        final MapleData data = this.getItemData(itemId);
        byte consume = (byte)MapleDataTool.getIntConvert("spec/consumeOnPickup", data, 0);
        if (consume == 0) {
            consume = (byte)MapleDataTool.getIntConvert("specEx/consumeOnPickup", data, 0);
        }
        if (consume == 1 && MapleDataTool.getIntConvert("spec/party", this.getItemData(itemId), 0) > 0) {
            consume = 2;
        }
        this.consumeOnPickupCache.put(Integer.valueOf(itemId), Byte.valueOf(consume));
        return consume;
    }
    
    public final boolean isDropRestricted(final int itemId) {
        if (this.dropRestrictionCache.containsKey(Integer.valueOf(itemId))) {
            return ((Boolean)this.dropRestrictionCache.get(Integer.valueOf(itemId))).booleanValue();
        }
        final MapleData data = this.getItemData(itemId);
        boolean trade = false;
        if (MapleDataTool.getIntConvert("info/tradeBlock", data, 0) == 1 || MapleDataTool.getIntConvert("info/quest", data, 0) == 1) {
            trade = true;
        }
        this.dropRestrictionCache.put(Integer.valueOf(itemId), Boolean.valueOf(trade));
        return trade;
    }
    
    public final boolean isPickupRestricted(final int itemId) {
        if (this.pickupRestrictionCache.containsKey(Integer.valueOf(itemId))) {
            return ((Boolean)this.pickupRestrictionCache.get(Integer.valueOf(itemId))).booleanValue();
        }
        final boolean bRestricted = MapleDataTool.getIntConvert("info/only", this.getItemData(itemId), 0) == 1;
        this.pickupRestrictionCache.put(Integer.valueOf(itemId), Boolean.valueOf(bRestricted));
        return bRestricted;
    }
    
    public final boolean isAccountShared(final int itemId) {
        if (this.accCache.containsKey(Integer.valueOf(itemId))) {
            return ((Boolean)this.accCache.get(Integer.valueOf(itemId))).booleanValue();
        }
        final boolean bRestricted = MapleDataTool.getIntConvert("info/accountSharable", this.getItemData(itemId), 0) == 1;
        this.accCache.put(Integer.valueOf(itemId), Boolean.valueOf(bRestricted));
        return bRestricted;
    }
    
    public final int getStateChangeItem(final int itemId) {
        if (this.stateChangeCache.containsKey(Integer.valueOf(itemId))) {
            return ((Integer)this.stateChangeCache.get(Integer.valueOf(itemId))).intValue();
        }
        final int triggerItem = MapleDataTool.getIntConvert("info/stateChangeItem", this.getItemData(itemId), 0);
        this.stateChangeCache.put(Integer.valueOf(itemId), Integer.valueOf(triggerItem));
        return triggerItem;
    }
    
    public final int getMeso(final int itemId) {
        if (this.mesoCache.containsKey(Integer.valueOf(itemId))) {
            return ((Integer)this.mesoCache.get(Integer.valueOf(itemId))).intValue();
        }
        final int triggerItem = MapleDataTool.getIntConvert("info/meso", this.getItemData(itemId), 0);
        this.mesoCache.put(Integer.valueOf(itemId), Integer.valueOf(triggerItem));
        return triggerItem;
    }
    
    public final boolean isKarmaEnabled(final int itemId) {
        if (this.karmaEnabledCache.containsKey(Integer.valueOf(itemId))) {
            return ((Integer)this.karmaEnabledCache.get(Integer.valueOf(itemId))).intValue() == 1;
        }
        final int iRestricted = MapleDataTool.getIntConvert("info/tradeAvailable", this.getItemData(itemId), 0);
        this.karmaEnabledCache.put(Integer.valueOf(itemId), Integer.valueOf(iRestricted));
        return iRestricted == 1;
    }
    
    public final boolean isPKarmaEnabled(final int itemId) {
        if (this.karmaEnabledCache.containsKey(Integer.valueOf(itemId))) {
            return ((Integer)this.karmaEnabledCache.get(Integer.valueOf(itemId))).intValue() == 2;
        }
        final int iRestricted = MapleDataTool.getIntConvert("info/tradeAvailable", this.getItemData(itemId), 0);
        this.karmaEnabledCache.put(Integer.valueOf(itemId), Integer.valueOf(iRestricted));
        return iRestricted == 2;
    }
    
    public final boolean isPickupBlocked(final int itemId) {
        if (this.blockPickupCache.containsKey(Integer.valueOf(itemId))) {
            return ((Boolean)this.blockPickupCache.get(Integer.valueOf(itemId))).booleanValue();
        }
        final boolean iRestricted = MapleDataTool.getIntConvert("info/pickUpBlock", this.getItemData(itemId), 0) == 1;
        this.blockPickupCache.put(Integer.valueOf(itemId), Boolean.valueOf(iRestricted));
        return iRestricted;
    }
    
    public MapleInventoryType getInventoryType(final int itemId) {
        final byte type = (byte)(itemId / 1000000);
        if (type < 1 || type > 5) {
            return MapleInventoryType.UNDEFINED;
        }
        return MapleInventoryType.getByType(type);
    }
    
    public final boolean isLogoutExpire(final int itemId) {
        if (this.logoutExpireCache.containsKey(Integer.valueOf(itemId))) {
            return ((Boolean)this.logoutExpireCache.get(Integer.valueOf(itemId))).booleanValue();
        }
        final boolean iRestricted = MapleDataTool.getIntConvert("info/expireOnLogout", this.getItemData(itemId), 0) == 1;
        this.logoutExpireCache.put(Integer.valueOf(itemId), Boolean.valueOf(iRestricted));
        return iRestricted;
    }
    
    public final boolean cantSell(final int itemId) {
        if (this.notSaleCache.containsKey(Integer.valueOf(itemId))) {
            return ((Boolean)this.notSaleCache.get(Integer.valueOf(itemId))).booleanValue();
        }
        final boolean bRestricted = MapleDataTool.getIntConvert("info/notSale", this.getItemData(itemId), 0) == 1;
        this.notSaleCache.put(Integer.valueOf(itemId), Boolean.valueOf(bRestricted));
        return bRestricted;
    }
    
    public final void loadStyles(final boolean reload) {
        if (reload) {
            this.faceList.clear();
        }
        if (!this.faceList.isEmpty()) {
            return;
        }
        final String[] array;
        final String[] types = array = new String[] { "Face" };
        for (final String type : array) {
            for (final MapleData c : this.stringData.getData("Eqp.img").getChildByPath("Eqp/" + type)) {
                if (this.equipData.getData(type + "/" + StringUtil.getLeftPaddedStr(c.getName() + ".img", '0', 12)) != null) {
                    final int dataid = Integer.parseInt(c.getName());
                    final String name = MapleDataTool.getString("name", c, "无名称");
                    if (!type.equals("Face")) {
                        continue;
                    }
                    this.faceList.put(Integer.valueOf(dataid), name);
                }
            }
        }
    }
    
    public boolean faceExists(final int face) {
        return this.faceList.containsKey(Integer.valueOf(face));
    }
    
    public final Map<Integer, String> getFaceList() {
        final Map<Integer, String> list = (Map<Integer, String>)new HashMap();
        list.putAll(this.faceList);
        return list;
    }
    
    public final Pair<Integer, List<StructRewardItem>> getRewardItem(final int itemid) {
        if (this.RewardItem.containsKey(Integer.valueOf(itemid))) {
            return this.RewardItem.get(Integer.valueOf(itemid));
        }
        final MapleData data = this.getItemData(itemid);
        if (data == null) {
            return null;
        }
        final MapleData rewards = data.getChildByPath("reward");
        if (rewards == null) {
            return null;
        }
        int totalprob = 0;
        final List<StructRewardItem> all = (List<StructRewardItem>)new ArrayList();
        for (final MapleData reward : rewards) {
            final StructRewardItem struct = new StructRewardItem();
            struct.setItemid(MapleDataTool.getInt("item", reward, 0));
            struct.setProb((short)MapleDataTool.getInt("prob", reward, 0));
            struct.setQuantity((short)MapleDataTool.getInt("count", reward, 0));
            struct.setEffect(MapleDataTool.getString("Effect", reward, ""));
            struct.setWorldmsg(MapleDataTool.getString("worldMsg", reward, null));
            struct.setPeriod((long)MapleDataTool.getInt("period", reward, -1));
            totalprob += struct.getProb();
            all.add(struct);
        }
        final Pair<Integer, List<StructRewardItem>> toreturn = new Pair(Integer.valueOf(totalprob), all);
        this.RewardItem.put(Integer.valueOf(itemid), toreturn);
        return toreturn;
    }
    
    public final Map<String, Integer> getSkillStats(final int itemId) {
        if (this.SkillStatsCache.containsKey(Integer.valueOf(itemId))) {
            return this.SkillStatsCache.get(Integer.valueOf(itemId));
        }
        if (itemId / 10000 != 228 && itemId / 10000 != 229 && itemId / 10000 != 562) {
            return null;
        }
        final MapleData item = this.getItemData(itemId);
        if (item == null) {
            return null;
        }
        final MapleData info = item.getChildByPath("info");
        if (info == null) {
            return null;
        }
        final Map<String, Integer> ret = (Map<String, Integer>)new LinkedHashMap();
        for (final MapleData data : info.getChildren()) {
            if (data.getName().startsWith("inc")) {
                ret.put(data.getName().substring(3), Integer.valueOf(MapleDataTool.getIntConvert(data)));
            }
        }
        ret.put("masterLevel", Integer.valueOf(MapleDataTool.getInt("masterLevel", info, 0)));
        ret.put("reqSkillLevel", Integer.valueOf(MapleDataTool.getInt("reqSkillLevel", info, 0)));
        ret.put("success", Integer.valueOf(MapleDataTool.getInt("success", info, 0)));
        final MapleData skill = info.getChildByPath("skill");
        for (int i = 0; i < skill.getChildren().size(); ++i) {
            ret.put("skillid" + i, Integer.valueOf(MapleDataTool.getInt(Integer.toString(i), skill, 0)));
        }
        this.SkillStatsCache.put(Integer.valueOf(itemId), ret);
        return ret;
    }
    
    public final List<Integer> petsCanConsume(final int itemId) {
        if (this.petsCanConsumeCache.get(Integer.valueOf(itemId)) != null) {
            return this.petsCanConsumeCache.get(Integer.valueOf(itemId));
        }
        final List<Integer> ret = (List<Integer>)new ArrayList();
        final MapleData data = this.getItemData(itemId);
        if (data == null || data.getChildByPath("spec") == null) {
            return ret;
        }
        for (final MapleData c : data.getChildByPath("spec")) {
            try {
                Integer.parseInt(c.getName());
            }
            catch (NumberFormatException e) {
                continue;
            }
            final int curPetId = MapleDataTool.getInt(c, 0);
            if (curPetId == 0) {
                break;
            }
            ret.add(Integer.valueOf(curPetId));
        }
        this.petsCanConsumeCache.put(Integer.valueOf(itemId), ret);
        return ret;
    }
    
    public final boolean isQuestItem(final int itemId) {
        if (this.isQuestItemCache.containsKey(Integer.valueOf(itemId))) {
            return ((Boolean)this.isQuestItemCache.get(Integer.valueOf(itemId))).booleanValue();
        }
        final boolean questItem = MapleDataTool.getIntConvert("info/quest", this.getItemData(itemId), 0) == 1;
        this.isQuestItemCache.put(Integer.valueOf(itemId), Boolean.valueOf(questItem));
        return questItem;
    }
    
    public final Pair<Integer, List<Integer>> questItemInfo(final int itemId) {
        if (this.questItems.containsKey(Integer.valueOf(itemId))) {
            return this.questItems.get(Integer.valueOf(itemId));
        }
        if (itemId / 10000 != 422 || this.getItemData(itemId) == null) {
            return null;
        }
        final MapleData itemD = this.getItemData(itemId).getChildByPath("info");
        if (itemD == null || itemD.getChildByPath("consumeItem") == null) {
            return null;
        }
        final List<Integer> consumeItems = (List<Integer>)new ArrayList();
        for (final MapleData consume : itemD.getChildByPath("consumeItem")) {
            consumeItems.add(Integer.valueOf(MapleDataTool.getInt(consume, 0)));
        }
        final Pair<Integer, List<Integer>> questItem = new Pair(Integer.valueOf(MapleDataTool.getIntConvert("questId", itemD, 0)), consumeItems);
        this.questItems.put(Integer.valueOf(itemId), questItem);
        return questItem;
    }
    
    public final boolean itemExists(final int itemId) {
        return GameConstants.getInventoryType(itemId) != MapleInventoryType.UNDEFINED && this.getItemData(itemId) != null;
    }
    
    public final boolean isCash(final int itemId) {
        if (this.getEquipStats(itemId) == null) {
            return GameConstants.getInventoryType(itemId) == MapleInventoryType.CASH;
        }
        return GameConstants.getInventoryType(itemId) == MapleInventoryType.CASH || ((Integer)this.getEquipStats(itemId).get("cash")).intValue() > 0;
    }
    
    public int getPetLimitLife(final int itemid) {
        if (this.petLimitLifeInfo.containsKey(Integer.valueOf(itemid))) {
            return ((Integer)this.petLimitLifeInfo.get(Integer.valueOf(itemid))).intValue();
        }
        if (!GameConstants.isPet(itemid)) {
            return 0;
        }
        final MapleData item = this.getItemData(itemid);
        final int limitLife = MapleDataTool.getIntConvert("info/limitedLife", item, 0);
        this.petLimitLifeInfo.put(Integer.valueOf(itemid), Integer.valueOf(limitLife));
        return limitLife;
    }
    
    public int getPetLife(final int itemid) {
        if (this.petLifeInfo.containsKey(Integer.valueOf(itemid))) {
            return ((Integer)this.petLifeInfo.get(Integer.valueOf(itemid))).intValue();
        }
        if (!GameConstants.isPet(itemid)) {
            return 0;
        }
        final MapleData item = this.getItemData(itemid);
        final int life = MapleDataTool.getIntConvert("info/life", item, 0);
        this.petLifeInfo.put(Integer.valueOf(itemid), Integer.valueOf(life));
        return life;
    }
    
    public short getPetFlagInfo(final int itemId) {
        if (this.petFlagInfo.containsKey(Integer.valueOf(itemId))) {
            return ((Short)this.petFlagInfo.get(Integer.valueOf(itemId))).shortValue();
        }
        short flag = 0;
        if (!GameConstants.isPet(itemId)) {
            return flag;
        }
        final MapleData item = this.getItemData(itemId);
        if (item == null) {
            return flag;
        }
        if (MapleDataTool.getIntConvert("info/pickupItem", item, 0) > 0) {
            flag |= (short)PetFlag.ITEM_PICKUP.getValue();
        }
        if (MapleDataTool.getIntConvert("info/longRange", item, 0) > 0) {
            flag |= (short)PetFlag.EXPAND_PICKUP.getValue();
        }
        if (MapleDataTool.getIntConvert("info/pickupAll", item, 0) > 0) {
            flag |= (short)PetFlag.AUTO_PICKUP.getValue();
        }
        if (MapleDataTool.getIntConvert("info/sweepForDrop", item, 0) > 0) {
            flag |= (short)PetFlag.LEFTOVER_PICKUP.getValue();
        }
        if (MapleDataTool.getIntConvert("info/consumeHP", item, 0) > 0) {
            flag |= (short)PetFlag.HP_CHARGE.getValue();
        }
        if (MapleDataTool.getIntConvert("info/consumeMP", item, 0) > 0) {
            flag |= (short)PetFlag.MP_CHARGE.getValue();
        }
        this.petFlagInfo.put(Integer.valueOf(itemId), Short.valueOf(flag));
        return flag;
    }
    
    public final boolean isOnlyTradeBlock(final int itemId) {
        final MapleData data = this.getItemData(itemId);
        boolean tradeblock = false;
        if (MapleDataTool.getIntConvert("info/tradeBlock", data, 0) == 1) {
            tradeblock = true;
        }
        return tradeblock;
    }
    
    public static void loadFaceHair() {
        try (final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection()) {
            try (final PreparedStatement ps = con.prepareStatement("SELECT * FROM wz_hairdata ORDER BY `hairid`");
                 final ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MapleItemInformationProvider.hairList.put(Integer.valueOf(rs.getInt("hairid")), rs.getString("name"));
                }
                rs.close();
                ps.close();
            }
            catch (SQLException ex) {
                System.out.println("读取发型数据失败：" + ex);
                FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)ex);
            }
            try (final PreparedStatement ps = con.prepareStatement("SELECT * FROM wz_facedata ORDER BY `faceid`");
                 final ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MapleItemInformationProvider.faceLists.put(Integer.valueOf(rs.getInt("faceid")), rs.getString("name"));
                }
                rs.close();
                ps.close();
            }
            catch (SQLException ex) {
                System.out.println("读取脸型数据失败：" + ex);
                FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)ex);
            }
            con.close();
        }
        catch (SQLException ex2) {
            System.out.println("读取脸型数据失败：" + ex2);
            FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)ex2);
        }
    }
    
    public MapleInventoryType getInventoryTypeCS(final int itemId) {
        if (this.inventoryTypeCache.containsKey(Integer.valueOf(itemId))) {
            return (MapleInventoryType)this.inventoryTypeCache.get(Integer.valueOf(itemId));
        }
        final String idStr = "0" + String.valueOf(itemId);
        MapleDataDirectoryEntry root = this.itemData.getRoot();
        for (final MapleDataDirectoryEntry topDir : root.getSubdirectories()) {
            for (final MapleDataFileEntry iFile : topDir.getFiles()) {
                if (iFile.getName().equals(idStr.substring(0, 4) + ".img")) {
                    final MapleInventoryType ret = MapleInventoryType.getByWZName(topDir.getName());
                    this.inventoryTypeCache.put(Integer.valueOf(itemId), ret);
                    return ret;
                }
                if (iFile.getName().equals(idStr.substring(1) + ".img")) {
                    final MapleInventoryType ret = MapleInventoryType.getByWZName(topDir.getName());
                    this.inventoryTypeCache.put(Integer.valueOf(itemId), ret);
                    return ret;
                }
            }
        }
        root = this.equipData.getRoot();
        for (final MapleDataDirectoryEntry topDir : root.getSubdirectories()) {
            for (final MapleDataFileEntry iFile : topDir.getFiles()) {
                if (iFile.getName().equals(idStr + ".img")) {
                    final MapleInventoryType ret = MapleInventoryType.EQUIP;
                    this.inventoryTypeCache.put(Integer.valueOf(itemId), ret);
                    return ret;
                }
            }
        }
        final MapleInventoryType ret = MapleInventoryType.UNDEFINED;
        this.inventoryTypeCache.put(Integer.valueOf(itemId), ret);
        return ret;
    }
    
    public static int getUniqueId(final int itemId, final MaplePet pet) {
        int uniqueid = -1;
        if (GameConstants.isPet(itemId)) {
            if (pet != null) {
                uniqueid = pet.getUniqueId();
            }
            else {
                uniqueid = MapleInventoryIdentifier.getInstance();
            }
        }
        else if (GameConstants.getInventoryType(itemId) == MapleInventoryType.CASH || getInstance().isCash(itemId)) {
            uniqueid = MapleInventoryIdentifier.getInstance();
        }
        return uniqueid;
    }
    
    public final int getChairMountId(final int itemId) {
        if (this.chairMountId.containsKey(Integer.valueOf(itemId))) {
            return ((Integer)this.chairMountId.get(Integer.valueOf(itemId))).intValue();
        }
        int ret = 0;
        final MapleData item = this.getItemData(itemId);
        if (item != null) {
            final MapleData smEntry = item.getChildByPath("info/tamingMob");
            if (smEntry == null) {
                ret = 0;
            }
            else {
                ret = MapleDataTool.getInt(smEntry);
            }
        }
        this.chairMountId.put(Integer.valueOf(itemId), Integer.valueOf(ret));
        return ret;
    }
}
