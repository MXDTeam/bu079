package server;

import client.MapleDisease;
import server.life.MobSkillFactory;
import server.life.MobSkill;
import java.util.Iterator;
import provider.MapleDataTool;
import provider.MapleData;
import tools.FileoutputUtil;
import provider.MapleDataProviderFactory;
import java.util.HashMap;
import provider.MapleDataProvider;
import java.util.Map;

public class MapleCarnivalFactory
{
    private static final MapleCarnivalFactory instance = new MapleCarnivalFactory();
    private final Map<Integer, MCSkill> skills;
    private final Map<Integer, MCSkill> guardians;
    private final MapleDataProvider dataRoot;
    
    public MapleCarnivalFactory() {
        this.skills = (Map<Integer, MCSkill>)new HashMap();
        this.guardians = (Map<Integer, MCSkill>)new HashMap();
        this.dataRoot = MapleDataProviderFactory.getDataProvider("Skill.wz");
        this.initialize();
    }
    
    public static final MapleCarnivalFactory getInstance() {
        return MapleCarnivalFactory.instance;
    }
    
    private void initialize() {
        if (!this.skills.isEmpty()) {
            return;
        }
        System.out.println("[" + FileoutputUtil.CurrentReadable_Time() + "][信息]:初始化怪物技能");
        for (final MapleData z : this.dataRoot.getData("MCSkill.img")) {
            final int MCSkillId = Integer.parseInt(z.getName());
            final int spendCP = MapleDataTool.getInt("spendCP", z, 0);
            final int mobSkillID = MapleDataTool.getInt("mobSkillID", z, 0);
            final int level = MapleDataTool.getInt("level", z, 0);
            final boolean target = MapleDataTool.getInt("target", z, 1) > 1;
            this.skills.put(Integer.valueOf(MCSkillId), new MCSkill(spendCP, mobSkillID, level, target));
        }
        for (final MapleData z : this.dataRoot.getData("MCGuardian.img")) {
            this.guardians.put(Integer.valueOf(Integer.parseInt(z.getName())), new MCSkill(MapleDataTool.getInt("spendCP", z, 0), MapleDataTool.getInt("mobSkillID", z, 0), MapleDataTool.getInt("level", z, 0), true));
        }
    }
    
    public MCSkill getSkill(final int id) {
        return (MCSkill)this.skills.get(Integer.valueOf(id));
    }
    
    public MCSkill getGuardian(final int id) {
        return (MCSkill)this.guardians.get(Integer.valueOf(id));
    }
    
    public static class MCSkill
    {
        public int cpLoss;
        public int mobSkillId;
        public int level;
        public boolean targetsAll;
        
        public MCSkill(final int _cpLoss, final int _skillid, final int _level, final boolean _targetsAll) {
            this.cpLoss = _cpLoss;
            this.mobSkillId = _skillid;
            this.level = _level;
            this.targetsAll = _targetsAll;
        }
        
        public MobSkill getMobSkill() {
            return MobSkillFactory.getMobSkill(this.mobSkillId, this.level);
        }
        
        public MapleDisease getDisease() {
            if (this.mobSkillId <= 0) {
                return MapleDisease.getRandom();
            }
            return MapleDisease.getByMobSkill(this.mobSkillId);
        }
    }
}
