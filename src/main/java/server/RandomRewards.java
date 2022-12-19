package server;

import java.util.Collections;
import constants.GameConstants;
import java.util.ArrayList;
import tools.FileoutputUtil;
import java.util.List;

public class RandomRewards{
    private static final RandomRewards instance = new RandomRewards();
    private static List<Integer> compiledGold = null;
    private static List<Integer> compiledJxbox = null;
    private static List<Integer> compiledSilver = null;
    private static List<Integer> compiledFishing = null;
    private static List<Integer> compiledEvent = null;
    private static List<Integer> compiledEventC = null;
    private static List<Integer> compiledEventB = null;
    private static List<Integer> compiledEventA = null;
    
    public static RandomRewards getInstance() {
        return RandomRewards.instance;
    }
    
    protected RandomRewards() {
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:初始化随机奖励内存");
        List<Integer> returnArray = (List<Integer>)new ArrayList();
        this.processRewards(returnArray, GameConstants.goldrewards);
        RandomRewards.compiledGold = returnArray;
        returnArray = (List<Integer>)new ArrayList();
        this.processRewards(returnArray, GameConstants.Jxboxrewards);
        RandomRewards.compiledJxbox = returnArray;
        returnArray = (List<Integer>)new ArrayList();
        this.processRewards(returnArray, GameConstants.silverrewards);
        RandomRewards.compiledSilver = returnArray;
        returnArray = (List<Integer>)new ArrayList();
        this.processRewards(returnArray, GameConstants.fishingReward);
        RandomRewards.compiledFishing = returnArray;
        returnArray = (List<Integer>)new ArrayList();
        this.processRewards(returnArray, GameConstants.eventCommonReward);
        RandomRewards.compiledEventC = returnArray;
        returnArray = (List<Integer>)new ArrayList();
        this.processRewards(returnArray, GameConstants.eventUncommonReward);
        RandomRewards.compiledEventB = returnArray;
        returnArray = (List<Integer>)new ArrayList();
        this.processRewards(returnArray, GameConstants.eventRareReward);
        RandomRewards.compiledEventA = returnArray;
        returnArray = (List<Integer>)new ArrayList();
        this.processRewards(returnArray, GameConstants.eventSuperReward);
        RandomRewards.compiledEvent = returnArray;
    }
    
    private void processRewards(final List<Integer> returnArray, final int[] list) {
        int lastitem = 0;
        for (int i = 0; i < list.length; ++i) {
            if (i % 2 == 0) {
                lastitem = list[i];
            }
            else {
                for (int j = 0; j < list[i]; ++j) {
                    returnArray.add(Integer.valueOf(lastitem));
                }
            }
        }
        Collections.shuffle(returnArray);
    }
    
    public static final int getGoldBoxReward() {
        return ((Integer)RandomRewards.compiledGold.get(Randomizer.nextInt(RandomRewards.compiledGold.size()))).intValue();
    }
    
    public static final int getJxBoxReward() {
        return ((Integer)RandomRewards.compiledJxbox.get(Randomizer.nextInt(RandomRewards.compiledJxbox.size()))).intValue();
    }
    
    public static final int getSilverBoxReward() {
        return ((Integer)RandomRewards.compiledSilver.get(Randomizer.nextInt(RandomRewards.compiledSilver.size()))).intValue();
    }
    
    public static final int getFishingReward() {
        return ((Integer)RandomRewards.compiledFishing.get(Randomizer.nextInt(RandomRewards.compiledFishing.size()))).intValue();
    }
    
    public static final int getEventReward() {
        final int chance = Randomizer.nextInt(100);
        if (chance < 50) {
            return ((Integer)RandomRewards.compiledEventC.get(Randomizer.nextInt(RandomRewards.compiledEventC.size()))).intValue();
        }
        if (chance < 80) {
            return ((Integer)RandomRewards.compiledEventB.get(Randomizer.nextInt(RandomRewards.compiledEventB.size()))).intValue();
        }
        if (chance < 95) {
            return ((Integer)RandomRewards.compiledEventA.get(Randomizer.nextInt(RandomRewards.compiledEventA.size()))).intValue();
        }
        return ((Integer)RandomRewards.compiledEvent.get(Randomizer.nextInt(RandomRewards.compiledEvent.size()))).intValue();
    }
}
