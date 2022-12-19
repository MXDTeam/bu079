package handling.world;

import java.util.concurrent.ConcurrentHashMap;
import client.MapleDiseaseValueHolder;
import client.MapleCoolDownValueHolder;
import java.util.List;
import java.util.Map;
import java.io.Serializable;

public class PlayerBuffStorage implements Serializable{
    private static final Map<Integer, List<PlayerBuffValueHolder>> buffs = new ConcurrentHashMap();
    private static final Map<Integer, List<MapleCoolDownValueHolder>> coolDowns = new ConcurrentHashMap();
    private static final Map<Integer, List<MapleDiseaseValueHolder>> diseases = new ConcurrentHashMap();
    
    public static final void addBuffsToStorage(final int chrid, final List<PlayerBuffValueHolder> toStore) {
        PlayerBuffStorage.buffs.put(Integer.valueOf(chrid), toStore);
    }
    
    public static final void addCooldownsToStorage(final int chrid, final List<MapleCoolDownValueHolder> toStore) {
        PlayerBuffStorage.coolDowns.put(Integer.valueOf(chrid), toStore);
    }
    
    public static final void addDiseaseToStorage(final int chrid, final List<MapleDiseaseValueHolder> toStore) {
        PlayerBuffStorage.diseases.put(Integer.valueOf(chrid), toStore);
    }
    
    public static final List<PlayerBuffValueHolder> getBuffsFromStorage(final int chrid) {
        return PlayerBuffStorage.buffs.remove(Integer.valueOf(chrid));
    }
    
    public static final List<MapleCoolDownValueHolder> getCooldownsFromStorage(final int chrid) {
        return PlayerBuffStorage.coolDowns.remove(Integer.valueOf(chrid));
    }
    
    public static final List<MapleDiseaseValueHolder> getDiseaseFromStorage(final int chrid) {
        return PlayerBuffStorage.diseases.remove(Integer.valueOf(chrid));
    }
}
