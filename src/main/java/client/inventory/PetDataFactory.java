package client.inventory;

import java.util.HashMap;
import provider.MapleDataProviderFactory;
import provider.MapleData;
import provider.MapleDataTool;
import tools.Pair;
import java.util.Map;
import provider.MapleDataProvider;

public class PetDataFactory{
    private static final MapleDataProvider dataRoot = MapleDataProviderFactory.getDataProvider("Item.wz");
    private static final Map<Pair<Integer, Integer>, PetCommand> petCommands = new HashMap();
    private static final Map<Integer, Integer> petHunger = new HashMap();
    
    public static final PetCommand getPetCommand(final int petId, final int skillId) {
        PetCommand ret = (PetCommand)PetDataFactory.petCommands.get(new Pair(Integer.valueOf(petId), Integer.valueOf(skillId)));
        if (ret != null) {
            return ret;
        }
        final MapleData skillData = PetDataFactory.dataRoot.getData("Pet/" + petId + ".img");
        int prob = 0;
        int inc = 0;
        if (skillData != null) {
            prob = MapleDataTool.getInt("interact/" + skillId + "/prob", skillData, 0);
            inc = MapleDataTool.getInt("interact/" + skillId + "/inc", skillData, 0);
        }
        ret = new PetCommand(petId, skillId, prob, inc);
        PetDataFactory.petCommands.put(new Pair(Integer.valueOf(petId), Integer.valueOf(skillId)), ret);
        return ret;
    }
    
    public static final int getHunger(final int petId) {
        Integer ret = (Integer)PetDataFactory.petHunger.get(Integer.valueOf(petId));
        if (ret != null) {
            return ret.intValue();
        }
        final MapleData hungerData = PetDataFactory.dataRoot.getData("Pet/" + petId + ".img").getChildByPath("info/hungry");
        ret = Integer.valueOf(MapleDataTool.getInt(hungerData, 1));
        PetDataFactory.petHunger.put(Integer.valueOf(petId), ret);
        return ret.intValue();
    }
    
}
