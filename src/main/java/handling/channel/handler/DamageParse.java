package handling.channel.handler;

import LzjSetting.Game;
import java.util.HashMap;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import database.DBConPool;
import tools.packet.MobPacket;
import server.SkillType;
import server.MapleItemInformationProvider;
import client.inventory.Equip;
import tools.data.LittleEndianAccessor;
import java.util.Collection;
import java.util.List;
import client.MapleJob;
import java.util.ArrayList;
import java.awt.Point;
import server.life.Element;
import client.anticheat.CheatTracker;
import client.inventory.IItem;
import server.life.MapleMonsterStats;
import server.life.MapleMonster;
import client.PlayerStats;
import java.util.Iterator;
import server.maps.MapleMap;
import java.util.Map.Entry;
import client.inventory.MapleInventoryType;
import client.status.MonsterStatusEffect;
import server.Randomizer;
import constants.tzjc;
import client.SkillFactory;
import java.awt.geom.Point2D;
import constants.ServerConfig;
import tools.Pair;
import client.status.MonsterStatus;
import server.maps.MapleMapObject;
import server.maps.MapleMapItem;
import server.maps.MapleMapObjectType;
import tools.AttackPair;
import gui.Start;
import tools.FileoutputUtil;
import handling.world.World.Broadcast;
import client.MapleBuffStat;
import constants.GameConstants;
import tools.MaplePacketCreator;
import client.anticheat.CheatingOffense;
import server.MapleStatEffect;
import client.MapleCharacter;
import client.ISkill;
import java.util.Map;

public class DamageParse
{
    public static final Map<Integer, Integer> MobRedDam = new HashMap();
    
    public static void applyAttack(final AttackInfo attack, final ISkill theSkill, final MapleCharacter player, int attackCount, final double maxDamagePerMonster, final MapleStatEffect effect, final AttackType attack_type) {
        if (!player.isAlive()) {
            player.getCheatTracker().registerOffense(CheatingOffense.ATTACKING_WHILE_DEAD);
            return;
        }
        if (attack.real) {
            player.getCheatTracker().checkAttack(attack.skill, attack.lastAttackTickCount);
        }
        if (attack.skill != 0) {
            if (effect == null) {
                player.getClient().sendPacket(MaplePacketCreator.enableActions());
                return;
            }
            if (GameConstants.isMulungSkill(attack.skill)) {
                if (player.getmulungEnergy() < 10000) {
                    return;
                }
                if (player.getMapId() / 10000 != 92502) {
                    return;
                }
                player.mulungEnergyModify(false);
            }
            if (GameConstants.isPyramidSkill(attack.skill) && (player.getMapId() / 1000000 != 926 || player.getPyramidSubway() == null || !player.getPyramidSubway().onSkillUse(player))) {
                return;
            }
            if (GameConstants.isAran((int)player.getJob())) {
                final int reduce = player.Aran_ReduceCombo(attack.skill);
                if (reduce > 0) {
                    player.setCombo(player.getCombo() - reduce);
                }
            }
            int last = attackCount;
            boolean mirror_fix = false;
            if (player.getJob() >= 411 && player.getJob() <= 412) {
                mirror_fix = true;
            }
            if (player.getJob() >= 1400 && player.getJob() <= 1412) {
                mirror_fix = true;
            }
            if (attack.skill == 11101004) {
                last = 2;
            }
            if (attack.skill == 15111007) {
                last = 3;
            }
            last = 技能段数(attack.skill);
            if (mirror_fix || player.getBuffedValue(MapleBuffStat.SHADOWPARTNER) != null) {
                last *= 2;
            }
            if (attack.hits > last) {
                if (player.hasGmLevel(1)) {
                    player.dropMessage("攻击次数异常攻击次数 " + (int)attack.hits + " 服务端判断正常攻击次数 " + last + " 技能ID " + attack.skill);
                }
                else {
                    Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 密语系统] " + player.getName() + " (等级 " + (int)player.getLevel() + ") 攻击次数异常已自动封锁。 玩家攻击次数 " + (int)attack.hits + " 服务端判断正常攻击次数 " + last + " 技能ID " + attack.skill));
                    FileoutputUtil.logToFile("logs/Hack/Ban/技能攻击次数.txt", "\r\n" + FileoutputUtil.NowTime() + "玩家: " + player.getName() + "(" + (int)player.getLevel() + ") 地图: " + player.getMapId() + " 技能代码: " + attack.skill + " 技能等级: " + player.getSkillLevel(attack.skill) + " 攻击次数 : " + (int)attack.hits + " 正常攻击次数 :" + last);
                    if (((Integer)Start.ConfigValuesMap.get("封停IP")).intValue() > 0) {
                        player.ban(player.getName() + "技能攻击次数异常", true, true, false);
                    }
                    if (((Integer)Start.ConfigValuesMap.get("封停MAC")).intValue() > 0) {
                        player.getClient().banMacs();
                    }
                    if (((Integer)Start.ConfigValuesMap.get("封停账号")).intValue() > 0) {
                        player.getClient().getSession().close();
                    }
                    final String reason = "使用违法程式练功";
                    if (((Integer)Start.ConfigValuesMap.get("全服通告")).intValue() > 0) {
                        Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[封锁系统] " + player.getName() + " 因为" + reason + "而被管理员永久停权。"));
                    }
                }
            }
            int CheckCount = effect.getMobCount();
            CheckCount = 技能个数(attack.skill);
            if (attack.targets > CheckCount) {
                if (!player.hasGmLevel(1)) {
                    FileoutputUtil.logToFile("logs/Hack/Ban/打怪数量异常.txt", "\r\n " + FileoutputUtil.NowTime() + " 玩家: " + player.getName() + "(" + (int)player.getLevel() + ") 地图: " + player.getMapId() + "技能代码: " + attack.skill + " 技能等级: " + player.getSkillLevel(effect.getSourceId()) + " 封包怪物量 : " + (int)attack.targets + " 服务端怪物量 :" + CheckCount);
                    Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 密语系统] " + player.getName() + " (等级 " + (int)player.getLevel() + ") 攻击怪物数量异常。 封包怪物量 " + (int)attack.targets + " 服务端怪物量 " + CheckCount + " 技能ID " + attack.skill));
                    if (((Integer)Start.ConfigValuesMap.get("封停IP")).intValue() > 0) {
                        player.ban(player.getName() + "打怪数量异常", true, true, false);
                    }
                    if (((Integer)Start.ConfigValuesMap.get("封停MAC")).intValue() > 0) {
                        player.getClient().banMacs();
                    }
                    if (((Integer)Start.ConfigValuesMap.get("封停账号")).intValue() > 0) {
                        player.getClient().getSession().close();
                    }
                    final String reason2 = "使用违法程式练功";
                    if (((Integer)Start.ConfigValuesMap.get("全服通告")).intValue() > 0) {
                        Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[封锁系统] " + player.getName() + " 因为" + reason2 + "而被管理员永久停权。"));
                    }
                    return;
                }
                player.dropMessage("打怪数量异常,技能代码: " + attack.skill + " 封包怪物量 : " + (int)attack.targets + " 服务端怪物量 :" + CheckCount);
            }
        }
        if (attack.hits > 0 && attack.targets > 0 && !player.getStat().checkEquipDurabilitys(player, -1)) {
            player.dropMessage(5, "耐久度已经归零");
            return;
        }
        int totDamage = 0;
        final MapleMap map = player.getMap();
        if (attack.skill == 4211006) {
            for (final AttackPair oned : attack.allDamage) {
                if (oned.attack != null) {
                    continue;
                }
                final MapleMapObject mapobject = map.getMapObject(oned.objectid, MapleMapObjectType.ITEM);
                if (mapobject == null) {
                    player.getCheatTracker().registerOffense(CheatingOffense.EXPLODING_NONEXISTANT);
                    return;
                }
                final MapleMapItem mapitem = (MapleMapItem)mapobject;
                mapitem.getLock().lock();
                try {
                    if (mapitem.getMeso() <= 0) {
                        player.getCheatTracker().registerOffense(CheatingOffense.ETC_EXPLOSION);
                        return;
                    }
                    if (mapitem.isPickedUp()) {
                        return;
                    }
                    map.removeMapObject((MapleMapObject)mapitem);
                    map.broadcastMessage(MaplePacketCreator.explodeDrop(mapitem.getObjectId()));
                    mapitem.setPickedUp(true);
                }
                finally {
                    mapitem.getLock().unlock();
                }
            }
        }
        int totDamageToOneMonster = 0;
        long newtotDamageToOneMonster = 0L;
        long hpMob = 0L;
        final PlayerStats stats = player.getStat();
        final int CriticalDamage = stats.passive_sharpeye_percent();
        byte ShdowPartnerAttackPercentage = 0;
        if (attack_type == AttackType.RANGED_WITH_SHADOWPARTNER || attack_type == AttackType.NON_RANGED_WITH_MIRROR) {
            MapleStatEffect shadowPartnerEffect;
            if (attack_type == AttackType.NON_RANGED_WITH_MIRROR) {
                shadowPartnerEffect = player.getStatForBuff(MapleBuffStat.MIRROR_IMAGE);
            }
            else {
                shadowPartnerEffect = player.getStatForBuff(MapleBuffStat.SHADOWPARTNER);
            }
            if (shadowPartnerEffect != null) {
                if (attack.skill != 0 && attack_type != AttackType.NON_RANGED_WITH_MIRROR) {
                    ShdowPartnerAttackPercentage = (byte)shadowPartnerEffect.getY();
                }
                else {
                    ShdowPartnerAttackPercentage = (byte)shadowPartnerEffect.getX();
                }
            }
            attackCount /= 2;
        }
        double maxDamagePerHit = 0.0;
        for (final AttackPair oned2 : attack.allDamage) {
            final MapleMonster monster = map.getMonsterByOid(oned2.objectid);
            if (monster != null) {
                totDamageToOneMonster = 0;
                newtotDamageToOneMonster = 0L;
                hpMob = monster.getHp();
                final MapleMonsterStats monsterstats = monster.getStats();
                final int fixeddmg = monsterstats.getFixedDamage();
                final boolean Tempest = monster.getStatusSourceID(MonsterStatus.FREEZE) == 21120006;
                maxDamagePerHit = calculateMaxWeaponDamagePerHit(player, monster, attack, theSkill, effect, maxDamagePerMonster, Integer.valueOf(CriticalDamage));
                byte overallAttackCount = 0;
                for (final Pair<Integer, Boolean> eachde : oned2.attack) {
                    Integer eachd = (Integer)eachde.left;
                    ++overallAttackCount;
                    if (!GameConstants.isElseSkill(attack.skill)) {
                        if (GameConstants.Novice_Skill(attack.skill) && eachd.intValue() > 40) {
                            boolean apple = false;
                            if (player.getBuffSource(MapleBuffStat.WATK) == 2022179 || player.getBuffSource(MapleBuffStat.MATK) == 2022179 || player.getBuffSource(MapleBuffStat.WDEF) == 2022179) {
                                apple = true;
                            }
                            if (!player.hasGmLevel(1)) {
                                FileoutputUtil.logToFile("logs/Hack/Ban/伤害异常.txt", "\r\n " + FileoutputUtil.NowTime() + " 玩家<" + (int)player.getLevel() + ">: " + player.getName() + " 怪物 " + monster.getId() + " 地图: " + player.getMapId() + " 技能代码: " + attack.skill + " 最高伤害: 40 本次伤害 :" + eachd + " 预计伤害: " + (int)maxDamagePerHit + "是否为BOSS: " + monster.getStats().isBoss() + " 紫色蘋果: " + apple);
                                Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 密语系统] " + player.getName() + " (等级 " + (int)player.getLevel() + ") 伤害异常。 最高伤害 40 本次伤害 " + eachd + " 技能ID " + attack.skill));
                                if (((Integer)Start.ConfigValuesMap.get("封停IP")).intValue() > 0) {
                                    player.ban(player.getName() + "伤害异常", true, true, false);
                                }
                                if (((Integer)Start.ConfigValuesMap.get("封停MAC")).intValue() > 0) {
                                    player.getClient().banMacs();
                                }
                                if (((Integer)Start.ConfigValuesMap.get("封停账号")).intValue() > 0) {
                                    player.getClient().getSession().close();
                                }
                                if (((Integer)Start.ConfigValuesMap.get("全服通告")).intValue() > 0) {
                                    Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[封锁系统] " + player.getName() + " 因为伤害异常而被管理员永久停权。"));
                                }
                                return;
                            }
                            player.dropMessage("[GM 密语系统] " + player.getName() + " (等级 " + (int)player.getLevel() + ") 伤害异常。 最高伤害 40 本次伤害 " + eachd + " 技能ID " + attack.skill);
                        }
                        boolean ban = false;
                        int atk = 2140000001;
                        if (eachd.intValue() < 0) {
                            if (!player.hasGmLevel(1)) {
                                FileoutputUtil.logToFile("logs/Hack/Ban/伤害异常.txt", "\r\n " + FileoutputUtil.NowTime() + " 玩家<" + (int)player.getLevel() + ">: " + player.getName() + " 怪物 " + monster.getId() + " 地图: " + player.getMapId() + " 技能代码: " + attack.skill + " 伤害过低导致越界 本次伤害 :" + eachd + " 预计伤害: " + (int)maxDamagePerHit + "是否为BOSS: " + monster.getStats().isBoss());
                                Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 密语系统] " + player.getName() + " (等级 " + (int)player.getLevel() + ") 伤害异常。 伤害过低导致越界本次伤害 " + eachd + " 技能ID " + attack.skill));
                                if (((Integer)Start.ConfigValuesMap.get("封停IP")).intValue() > 0) {
                                    player.ban(player.getName() + "伤害异常", true, true, false);
                                }
                                if (((Integer)Start.ConfigValuesMap.get("封停MAC")).intValue() > 0) {
                                    player.getClient().banMacs();
                                }
                                if (((Integer)Start.ConfigValuesMap.get("封停账号")).intValue() > 0) {
                                    player.getClient().getSession().close();
                                }
                                if (((Integer)Start.ConfigValuesMap.get("全服通告")).intValue() > 0) {
                                    Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[封锁系统] " + player.getName() + " 因为伤害异常而被管理员永久停权。"));
                                }
                                return;
                            }
                            player.dropMessage("[GM 密语系统] " + player.getName() + " (等级 " + (int)player.getLevel() + ") 伤害异常。 伤害过低导致越界本次伤害 " + eachd + " 技能ID " + attack.skill);
                        }
                        if (!GameConstants.isAran((int)player.getJob())) {
                            if (((Integer)Start.ConfigValuesMap.get("伤害检测")).intValue() > 0) {
                                atk = ((Integer)Start.ConfigValuesMap.get("伤害上限")).intValue();
                            }
                            if (eachd.intValue() >= atk && (double)eachd.intValue() > Math.ceil(maxDamagePerHit * 1.2)) {
                                ban = true;
                            }
                            if ((long)eachd.intValue() == monster.getMobMaxHp()) {
                                ban = false;
                            }
                            if (ban) {
                                boolean apple2 = false;
                                if (player.getBuffSource(MapleBuffStat.WATK) == 2022179 || player.getBuffSource(MapleBuffStat.MATK) == 2022179 || player.getBuffSource(MapleBuffStat.WDEF) == 2022179) {
                                    apple2 = true;
                                }
                                if (!player.hasGmLevel(1)) {
                                    FileoutputUtil.logToFile("logs/Hack/Ban/伤害异常.txt", "\r\n " + FileoutputUtil.NowTime() + " 玩家<" + (int)player.getLevel() + ">: " + player.getName() + " 怪物 " + monster.getId() + " 地图: " + player.getMapId() + " 技能代码: " + attack.skill + " 最高伤害: " + atk + " 本次伤害 :" + eachd + " 预计伤害: " + (int)maxDamagePerHit + "是否为BOSS: " + monster.getStats().isBoss() + " 紫色蘋果: " + apple2);
                                    Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 密语系统] " + player.getName() + " (等级 " + (int)player.getLevel() + ") 伤害异常。 最高伤害 " + atk + " 本次伤害 " + eachd + " 技能ID " + attack.skill + "预计伤害: " + (int)maxDamagePerHit));
                                    if (((Integer)Start.ConfigValuesMap.get("封停IP")).intValue() > 0) {
                                        player.ban(player.getName() + "伤害异常", true, true, false);
                                    }
                                    if (((Integer)Start.ConfigValuesMap.get("封停MAC")).intValue() > 0) {
                                        player.getClient().banMacs();
                                    }
                                    if (((Integer)Start.ConfigValuesMap.get("封停账号")).intValue() > 0) {
                                        player.getClient().getSession().close();
                                    }
                                    if (((Integer)Start.ConfigValuesMap.get("全服通告")).intValue() > 0) {
                                        Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[封锁系统] " + player.getName() + " 因为伤害异常而被管理员永久停权。"));
                                    }
                                    return;
                                }
                                player.dropMessage("[GM 密语系统] " + player.getName() + " (等级 " + (int)player.getLevel() + ") 伤害异常。 最高伤害 " + atk + " 本次伤害 " + eachd + " 技能ID " + attack.skill + "预计伤害: " + (int)maxDamagePerHit);
                            }
                        }
                        atk = GameConstants.getMaxDamage((int)player.getLevel(), (int)player.getJob(), attack.skill);
                        if (GameConstants.isAran((int)player.getJob())) {
                            if (player.getLevel() > 10 && eachd.intValue() > atk && (double)eachd.intValue() > maxDamagePerHit * 2.0) {
                                FileoutputUtil.logToFile("logs/Hack/伤害异常_狂狼.txt", "\r\n " + FileoutputUtil.NowTime() + " 玩家<" + (int)player.getLevel() + ">: " + player.getName() + " 技能代码: " + attack.skill + " 怪物: " + monster.getId() + " 最高伤害: " + atk + " 本次伤害 :" + eachd + " 预计伤害: " + (int)maxDamagePerHit + "是否为BOSS: " + monster.getStats().isBoss());
                            }
                            if (player.getLevel() <= 20) {
                                atk = 1000;
                                if (eachd.intValue() >= atk && (double)eachd.intValue() > maxDamagePerHit) {
                                    boolean apple2 = false;
                                    if (player.getBuffSource(MapleBuffStat.WATK) == 2022179 || player.getBuffSource(MapleBuffStat.MATK) == 2022179 || player.getBuffSource(MapleBuffStat.WDEF) == 2022179) {
                                        apple2 = true;
                                    }
                                    ban = true;
                                    FileoutputUtil.logToFile("logs/Hack/Ban/伤害异常.txt", "\r\n " + FileoutputUtil.NowTime() + " 玩家<" + (int)player.getLevel() + ">: " + player.getName() + " 怪物 " + monster.getId() + " 地图: " + player.getMapId() + " 技能代码: " + attack.skill + " 最高伤害: " + atk + " 本次伤害 :" + eachd + " 预计伤害: " + (int)maxDamagePerHit + "是否为BOSS: " + monster.getStats().isBoss() + " 紫色蘋果: " + apple2);
                                    Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 密语系统] " + player.getName() + " (等级 " + (int)player.getLevel() + ") 伤害异常。 最高伤害 " + atk + " 本次伤害 " + eachd + " 技能ID " + attack.skill));
                                    if (((Integer)Start.ConfigValuesMap.get("封停账号")).intValue() > 0) {
                                        Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[封锁系统] " + player.getName() + " 因为伤害异常而被管理员永久停权。"));
                                        player.ban(player.getName() + "伤害异常", true, true, false);
                                        player.getClient().getSession().close();
                                        return;
                                    }
                                }
                            }
                        }
                        else if (eachd.intValue() > atk && (double)eachd.intValue() > maxDamagePerHit * 2.0) {
                            FileoutputUtil.logToFile("logs/Hack/伤害异常.txt", "\r\n " + FileoutputUtil.NowTime() + " 玩家<" + (int)player.getLevel() + ">: " + player.getName() + " 技能代码: " + attack.skill + " 怪物: " + monster.getId() + " 最高伤害: " + atk + " 本次伤害 :" + eachd + " 预计伤害: " + (int)maxDamagePerHit + "是否为BOSS: " + monster.getStats().isBoss());
                        }
                    }
                    if (overallAttackCount - 1 == attackCount) {
                        double min = maxDamagePerHit;
                        final double shadow = ((double)ShdowPartnerAttackPercentage == 0.0) ? 1.0 : ((double)ShdowPartnerAttackPercentage);
                        if (ShdowPartnerAttackPercentage != 0) {
                            min = maxDamagePerHit / 100.0;
                        }
                        final double dam = monsterstats.isBoss() ? stats.bossdam_r : stats.dam_r;
                        final double last2 = maxDamagePerHit = min * (shadow * dam / 100.0);
                    }
                    if (fixeddmg != -1) {
                        if (monsterstats.getOnlyNoramlAttack()) {
                            eachd = Integer.valueOf((attack.skill != 0) ? 0 : fixeddmg);
                        }
                        else {
                            eachd = Integer.valueOf(fixeddmg);
                        }
                    }
                    else if (monsterstats.getOnlyNoramlAttack()) {
                        eachd = Integer.valueOf((attack.skill != 0) ? 0 : Math.min(eachd.intValue(), (int)maxDamagePerHit));
                    }
                    else if (!player.isGM() && ((Integer)Start.ConfigValuesMap.get("伤害修正")).intValue() > 0) {
                        if (!Tempest) {
                            if (!monster.isBuffed(MonsterStatus.DAMAGE_IMMUNITY) && !monster.isBuffed(MonsterStatus.WEAPON_IMMUNITY) && !monster.isBuffed(MonsterStatus.WEAPON_DAMAGE_REFLECT)) {
                                if ((double)eachd.intValue() > maxDamagePerHit) {
                                    player.getCheatTracker().registerOffense(CheatingOffense.HIGH_DAMAGE, "[伤害: " + eachd + ", 预期: " + maxDamagePerHit + ", 怪物: " + monster.getId() + "] [职业: " + (int)player.getJob() + ", 等级: " + (int)player.getLevel() + ", 使用的技能: " + attack.skill + "]");
                                    if ((double)eachd.intValue() > maxDamagePerHit * 2.0) {
                                        if ((double)eachd.intValue() > maxDamagePerHit * 2.0 && maxDamagePerHit != 1.0) {
                                            if (ServerConfig.LOG_DAMAGE) {
                                                FileoutputUtil.logToFile("Logs/hack/伤害计算/伤害计算修正_" + monster.getId() + "_" + attack.skill + ".txt", "\r\n " + FileoutputUtil.NowTime() + " 玩家: " + player.getName() + "(" + (int)player.getLevel() + ") 职业: " + (int)player.getJob() + " 怪物:" + monster.getId() + " 封包伤害 :" + eachd + " 预计伤害 :" + (int)maxDamagePerHit + " 是否为BOSS: " + monster.getStats().isBoss(), false, false);
                                            }
                                            player.getCheatTracker().registerOffense(CheatingOffense.HIGH_DAMAGE_2, "[伤害: " + eachd + ", 预计伤害: " + (int)maxDamagePerHit + ", 怪物: " + monster.getId() + "] [职业: " + (int)player.getJob() + ", 等级: " + (int)player.getLevel() + ", 技能: " + attack.skill + "]");
                                        }
                                        eachd = Integer.valueOf((int)(maxDamagePerHit * 2.0));
                                        player.getCheatTracker().registerOffense(CheatingOffense.HIGH_DAMAGE_2, "[伤害: " + eachd + ", 预期: " + maxDamagePerHit + ", 怪物: " + monster.getId() + "] [职业: " + (int)player.getJob() + ", 等级: " + (int)player.getLevel() + ", 使用的技能: " + attack.skill + "]");
                                        if (eachd.intValue() >= 10000) {
                                            player.getCheatTracker().registerOffense(CheatingOffense.HIGH_DAMAGE_2, "[伤害: " + eachd + ", 预期: " + maxDamagePerHit + ", 怪物: " + monster.getId() + "] [职业: " + (int)player.getJob() + ", 等级: " + (int)player.getLevel() + ", 使用的技能: " + attack.skill + "]");
                                        }
                                    }
                                }
                            }
                            else if ((double)eachd.intValue() > maxDamagePerHit) {
                                eachd = Integer.valueOf((int)maxDamagePerHit);
                                if ((double)eachd.intValue() > maxDamagePerHit * 2.0 && maxDamagePerHit != 1.0) {
                                    if (ServerConfig.LOG_DAMAGE) {
                                        FileoutputUtil.logToFile("Logs/hack/伤害计算/伤害计算修正_" + monster.getId() + "_" + attack.skill + ".txt", "\r\n " + FileoutputUtil.NowTime() + " 玩家: " + player.getName() + "(" + (int)player.getLevel() + ") 职业: " + (int)player.getJob() + " 怪物:" + monster.getId() + " 封包伤害 :" + eachd + " 预计伤害 :" + (int)maxDamagePerHit + " 是否为BOSS: " + monster.getStats().isBoss());
                                    }
                                    player.getCheatTracker().registerOffense(CheatingOffense.HIGH_DAMAGE_2, "[伤害: " + eachd + ", 预计伤害: " + (int)maxDamagePerHit + ", 怪物: " + monster.getId() + "] [职业: " + (int)player.getJob() + ", 等级: " + (int)player.getLevel() + ", 技能: " + attack.skill + "]");
                                }
                            }
                        }
                    }
                    totDamageToOneMonster += eachd.intValue();
                    newtotDamageToOneMonster += (long)eachd.intValue();
                    if (monster.getId() == 9300021 && player.getPyramidSubway() != null) {
                        player.getPyramidSubway().onMiss(player);
                    }
                }
                totDamage += totDamageToOneMonster;
                player.checkMonsterAggro(monster);
                final double range = player.getPosition().distanceSq((Point2D)monster.getPosition());
                double SkillRange = GameConstants.getAttackRange(player, effect, attack);
                if (((Integer)Start.ConfigValuesMap.get("重置技能范围开关")).intValue() > 0) {
                    SkillRange = (double)((Integer)Start.ConfigValuesMap.get("重置技能总范围")).intValue();
                }
                if (player.getDebugMessage() && range > SkillRange) {
                    player.dropMessage("技能[" + attack.skill + "] 预计范围: " + (int)SkillRange + " 实際范围: " + (int)range + "");
                }
                if (range > SkillRange && !player.inBossMap() && attack.skill != 5221004 && attack.skill != 21120006) {
                    player.getCheatTracker().registerOffense(CheatingOffense.ATTACK_FARAWAY_MONSTER, "攻击范围异常,技能:" + attack.skill + "(" + SkillFactory.getName(attack.skill) + ")\u3000怪物:" + monster.getId() + " 正常范围:" + (int)SkillRange + " 计算范围:" + (int)range);
                    if (range > SkillRange * 2.0 && ((Integer)Start.ConfigValuesMap.get("全屏检测")).intValue() > 0) {
                        player.getCheatTracker().registerOffense(CheatingOffense.ATTACK_FARAWAY_MONSTER_BAN, "超大攻击范围,技能:" + attack.skill + "(" + SkillFactory.getName(attack.skill) + ")\u3000怪物:" + monster.getId() + " 正常范围:" + (int)SkillRange + " 计算范围:" + (int)range);
                    }
                    return;
                }
                if (player.getBuffedValue(MapleBuffStat.PICKPOCKET) != null) {
                    switch (attack.skill) {
                        case 0:
                        case 4001334:
                        case 4201005:
                        case 4211002:
                        case 4211004:
                        case 4221003:
                        case 4221007: {
                            handlePickPocket(player, monster, oned2);
                            break;
                        }
                    }
                }
                final MapleStatEffect ds = player.getStatForBuff(MapleBuffStat.DARKSIGHT);
                if (ds != null && ds.getSourceId() != 9001004 && (ds.getSourceId() != 4330001 || !ds.makeChanceResult())) {
                    player.cancelEffectFromBuffStat(MapleBuffStat.DARKSIGHT);
                }
                final MapleStatEffect wd = player.getStatForBuff(MapleBuffStat.WIND_WALK);
                if (wd != null && player.getJob() >= 1300 && player.getJob() <= 1312) {
                    player.cancelEffectFromBuffStat(MapleBuffStat.WIND_WALK);
                }
                if (newtotDamageToOneMonster <= 0L) {
                    continue;
                }
                final tzjc t = new tzjc();
                newtotDamageToOneMonster = t.star_damage(player, newtotDamageToOneMonster, monster);
                String mobname=monster.getStats().getName();
                long mobhp=monster.getHp();
                long mobhp1=monster.getMobMaxHp();
                //String 计算 = String.format("%.3f", (mobhp*1.0/mobhp1)*100);
                //double 计算2 = Math.floor((mobhp*1.0/mobhp1)*50);
                //String 计算3 = String.format("%.2f",mobhp*1.0/100000000);

                if (attack.skill != 1221011) {
                    long newDamage = 0L;
                    newDamage = 额外伤害(player, newtotDamageToOneMonster, monster);
                    newtotDamageToOneMonster += newDamage;
                    newtotDamageToOneMonster = 伤害减伤(monster.getId(), newtotDamageToOneMonster);
                    monster.damage(player, newtotDamageToOneMonster, true, attack.skill);
                    if(monster.getStats().isBoss()||Game.ismyboss(monster.getId()))
                    {
//                        String 内容 = "";
//                        for(int i=0;i<计算2;i++){
//                        内容 += "■";
//                        }
//                         for(int i=0;i<(50-计算2);i++){
//                        内容 += "□";
//                        }
                        if(mobhp<100000000){
                                player.dropMessage(-1, "当前攻击怪物:"+mobname+"  剩余血量: "+mobhp+"");
                        }else{
                            player.dropMessage(-1, "当前攻击怪物:"+mobname+"  剩余血量: "+String.format("%.2f",mobhp*1.0/100000000)+" E");
                        }
                        //player.dropMessage(-1, "当前攻击怪物:"+mobname+"  剩余血量: "+计算3+" E");
                        //player.dropMessage(-1, "当前攻击怪物:"+mobname+"  剩余血量: "+计算+" %"+"   "+mobhp+"   "+mobhp1+"");
                        
                       
                        //player.getMap().startExtendedMapEffect("血量："+内容+计算+"%", 5121010);

                       
                        
                    }
                }
                else {
                    monster.damage(player, monster.getStats().isBoss() ? 500000L : (monster.getHp() - 1L), true, attack.skill);
                }
                if (monster.isBuffed(MonsterStatus.WEAPON_DAMAGE_REFLECT)) {
                    player.addHP(-(7000 + Randomizer.nextInt(8000)));
                }
                if (stats.hpRecoverProp > 0 && Randomizer.nextInt(100) <= stats.hpRecoverProp) {
                    player.healHP(stats.hpRecover);
                }
                if (stats.mpRecoverProp > 0 && Randomizer.nextInt(100) <= stats.mpRecoverProp) {
                    player.healMP(stats.mpRecover);
                }
                if (player.getBuffedValue(MapleBuffStat.COMBO_DRAIN) != null) {
                    stats.setHp(stats.getHp() + (int)Math.min(monster.getMobMaxHp(), (long)Math.min((int)((double)totDamage * (double)player.getStatForBuff(MapleBuffStat.COMBO_DRAIN).getX() / 100.0), stats.getMaxHp() / 2)), true);
                }
                final int[] array;
                final int[] skillsl = array = new int[] { 4120005, 4220005, 14110004 };
                final int length = array.length;
                int k = 0;
                while (k < length) {
                    final int i = array[k];
                    final ISkill skill = SkillFactory.getSkill(i);
                    if (player.getSkillLevel(skill) > 0) {
                        final MapleStatEffect venomEffect = skill.getEffect((int)player.getSkillLevel(skill));
                        if (venomEffect.makeChanceResult()) {
                            monster.applyStatus(player, new MonsterStatusEffect(MonsterStatus.POISON, Integer.valueOf(1), i, null, false), true, (long)venomEffect.getDuration(), monster.getStats().isBoss(), venomEffect);
                            break;
                        }
                        break;
                    }
                    else {
                        ++k;
                    }
                }
                switch (attack.skill) {
                    case 4101005:
                    case 5111004:
                    case 15111001: {
                        final int getHP = (int)Math.min(monster.getMobMaxHp(), (long)Math.min((int)((double)totDamage * (double)theSkill.getEffect((int)player.getSkillLevel(theSkill)).getX() / 100.0), stats.getMaxHp() / 2));
                        stats.setHp(stats.getHp() + getHP, true);
                        break;
                    }
                    case 5211006:
                    case 5220011: {
                        player.setLinkMid(monster.getObjectId());
                        break;
                    }
                    case 1311005: {
                        final int remainingHP = stats.getHp() - totDamage * ((effect != null) ? effect.getX() : 0) / 100;
                        stats.setHp((remainingHP > 1) ? remainingHP : 1);
                        break;
                    }
                    case 4001002:
                    case 4001334:
                    case 4001344:
                    case 4111005:
                    case 4121007:
                    case 4201005:
                    case 4211002:
                    case 4211004:
                    case 4221001:
                    case 4221007:
                    case 14001002:
                    case 14001004:
                    case 14111002:
                    case 14111005: {
                        if (player.hasBuffedValue(MapleBuffStat.WK_CHARGE) && !monster.getStats().isBoss()) {
                            final MapleStatEffect eff = player.getStatForBuff(MapleBuffStat.WK_CHARGE);
                            if (eff != null) {
                                monster.applyStatus(player, new MonsterStatusEffect(MonsterStatus.SPEED, Integer.valueOf(eff.getX()), eff.getSourceId(), null, false), false, (long)(eff.getY() * 1000), monster.getStats().isBoss(), eff);
                            }
                        }
                        if (player.hasBuffedValue(MapleBuffStat.BODY_PRESSURE) && !monster.getStats().isBoss()) {
                            final MapleStatEffect eff = player.getStatForBuff(MapleBuffStat.BODY_PRESSURE);
                            if (eff != null && eff.makeChanceResult() && !monster.isBuffed(MonsterStatus.NEUTRALISE)) {
                                monster.applyStatus(player, new MonsterStatusEffect(MonsterStatus.NEUTRALISE, Integer.valueOf(1), eff.getSourceId(), null, false), false, (long)(eff.getX() * 1000), monster.getStats().isBoss(), eff);
                            }
                        }
                        final int[] array2;
                        final int[] skills = array2 = new int[] { 4120005, 4220005, 14110004 };
                        k = array2.length;
                        int l = 0;
                        while (l < k) {
                            final int j = array2[l];
                            final ISkill skill2 = SkillFactory.getSkill(j);
                            if (player.getSkillLevel(skill2) > 0) {
                                final MapleStatEffect venomEffect2 = skill2.getEffect((int)player.getSkillLevel(skill2));
                                if (venomEffect2.makeChanceResult()) {
                                    monster.applyStatus(player, new MonsterStatusEffect(MonsterStatus.POISON, Integer.valueOf(1), j, null, false), true, (long)venomEffect2.getDuration(), monster.getStats().isBoss(), venomEffect2);
                                    break;
                                }
                                break;
                            }
                            else {
                                ++l;
                            }
                        }
                        break;
                    }
                    case 4201004: {
                        monster.handleSteal(player);
                        break;
                    }
                    case 21000002:
                    case 21100001:
                    case 21100002:
                    case 21100004:
                    case 21110002:
                    case 21110003:
                    case 21110004:
                    case 21110006:
                    case 21110007:
                    case 21110008:
                    case 21120002:
                    case 21120005:
                    case 21120006:
                    case 21120009:
                    case 21120010: {
                        if (player.getBuffedValue(MapleBuffStat.WK_CHARGE) != null && !monster.getStats().isBoss()) {
                            final MapleStatEffect eff = player.getStatForBuff(MapleBuffStat.WK_CHARGE);
                            if (eff != null) {
                                monster.applyStatus(player, new MonsterStatusEffect(MonsterStatus.SPEED, Integer.valueOf(eff.getX()), eff.getSourceId(), null, false), false, (long)(eff.getY() * 1000), monster.getStats().isBoss(), eff);
                            }
                        }
                        if (player.getBuffedValue(MapleBuffStat.BODY_PRESSURE) == null || monster.getStats().isBoss()) {
                            break;
                        }
                        final MapleStatEffect eff = player.getStatForBuff(MapleBuffStat.BODY_PRESSURE);
                        if (eff != null && eff.makeChanceResult() && !monster.isBuffed(MonsterStatus.NEUTRALISE)) {
                            monster.applyStatus(player, new MonsterStatusEffect(MonsterStatus.NEUTRALISE, Integer.valueOf(1), eff.getSourceId(), null, false), false, (long)(eff.getX() * 1000), true, eff);
                            break;
                        }
                        break;
                    }
                }
                if (totDamageToOneMonster > 0) {
                    final IItem weapon_ = player.getInventory(MapleInventoryType.EQUIPPED).getItem((short)(-11));
                    if (weapon_ != null) {
                        final MonsterStatus stat = GameConstants.getStatFromWeapon(weapon_.getItemId());
                        if (stat != null && Randomizer.nextInt(100) < GameConstants.getStatChance()) {
                            final MonsterStatusEffect monsterStatusEffect = new MonsterStatusEffect(stat, Integer.valueOf(GameConstants.getXForStat(stat)), GameConstants.getSkillForStat(stat), null, false);
                            monster.applyStatus(player, monsterStatusEffect, false, 10000L, monster.getStats().isBoss(), null);
                        }
                    }
                    if (player.hasBuffedValue(MapleBuffStat.BLIND)) {
                        final MapleStatEffect eff2 = player.getStatForBuff(MapleBuffStat.BLIND);
                        if (eff2 != null && eff2.makeChanceResult()) {
                            final MonsterStatusEffect monsterStatusEffect = new MonsterStatusEffect(MonsterStatus.ACC, Integer.valueOf(eff2.getX()), eff2.getSourceId(), null, false);
                            monster.applyStatus(player, monsterStatusEffect, false, (long)(eff2.getY() * 1000), monster.getStats().isBoss(), eff2);
                        }
                    }
                    if (player.hasBuffedValue(MapleBuffStat.HAMSTRING)) {
                        final MapleStatEffect eff2 = player.getStatForBuff(MapleBuffStat.HAMSTRING);
                        if (eff2 != null && eff2.makeChanceResult()) {
                            final MonsterStatusEffect monsterStatusEffect = new MonsterStatusEffect(MonsterStatus.SPEED, Integer.valueOf(eff2.getX()), 3121007, null, false);
                            monster.applyStatus(player, monsterStatusEffect, false, (long)(eff2.getY() * 1000), monster.getStats().isBoss(), eff2);
                        }
                    }
                    if (player.getJob() == 121) {
                        ISkill skill3 = SkillFactory.getSkill(1211006);
                        if (player.isBuffFrom(MapleBuffStat.WK_CHARGE, skill3)) {
                            final MapleStatEffect eff3 = skill3.getEffect((int)player.getSkillLevel(skill3));
                            final MonsterStatusEffect monsterStatusEffect2 = new MonsterStatusEffect(MonsterStatus.FREEZE, Integer.valueOf(1), skill3.getId(), null, false);
                            monster.applyStatus(player, monsterStatusEffect2, false, (long)(eff3.getY() * 2000), monster.getStats().isBoss(), eff3);
                        }
                        skill3 = SkillFactory.getSkill(1211005);
                        if (player.isBuffFrom(MapleBuffStat.WK_CHARGE, skill3)) {
                            final MapleStatEffect eff3 = skill3.getEffect((int)player.getSkillLevel(skill3));
                            final MonsterStatusEffect monsterStatusEffect2 = new MonsterStatusEffect(MonsterStatus.FREEZE, Integer.valueOf(1), skill3.getId(), null, false);
                            monster.applyStatus(player, monsterStatusEffect2, false, (long)(eff3.getY() * 2000), monster.getStats().isBoss(), eff3);
                        }
                    }
                }
                if (effect == null || effect.getMonsterStati().size() <= 0 || !effect.makeChanceResult()) {
                    continue;
                }
                for (final Entry<client.status.MonsterStatus, java.lang.Integer> z : effect.getMonsterStati().entrySet()) {
                    monster.applyStatus(player, new MonsterStatusEffect((MonsterStatus)z.getKey(), (Integer)z.getValue(), theSkill.getId(), null, false), effect.isPoison(), (long)effect.getDuration(), monster.getStats().isBoss(), effect);
                }
            }
        }
        if (effect != null && attack.skill != 0 && (attack.targets > 0 || (attack.skill != 4331003 && attack.skill != 4341002)) && attack.skill != 21101003 && attack.skill != 5110001 && attack.skill != 15100004 && attack.skill != 11101002 && attack.skill != 13101002 && attack.skill != 14111006) {
            effect.applyTo(player, attack.position);
        }
        if (attack.skill == 14111006) {
            effect.applyTo(player, attack.positionxy);
        }
        if (totDamage > 1) {
            final CheatTracker tracker = player.getCheatTracker();
            tracker.setAttacksWithoutHit(true);
            if (tracker.getAttacksWithoutHit() > 50) {
                tracker.registerOffense(CheatingOffense.ATTACK_WITHOUT_GETTING_HIT, Integer.toString(tracker.getAttacksWithoutHit()));
            }
        }
    }
    
    public static final void applyAttackMagic(final AttackInfo attack, final ISkill theSkill, final MapleCharacter player, final MapleStatEffect effect) {
        if (!player.isAlive()) {
            player.getCheatTracker().registerOffense(CheatingOffense.ATTACKING_WHILE_DEAD);
            return;
        }
        if (attack.real) {
            player.getCheatTracker().checkAttack(attack.skill, attack.lastAttackTickCount);
        }
        int last = (effect.getAttackCount() > effect.getBulletCount()) ? effect.getAttackCount() : effect.getBulletCount();
        boolean mirror_fix = false;
        if (player.getJob() >= 411 && player.getJob() <= 412) {
            mirror_fix = true;
        }
        if (player.getJob() >= 1400 && player.getJob() <= 1412) {
            mirror_fix = true;
        }
        if (attack.skill == 11101004) {
            last = 2;
        }
        if (attack.skill == 15111007) {
            last = 3;
        }
        last = 技能段数(attack.skill);
        if (mirror_fix || player.getBuffedValue(MapleBuffStat.SHADOWPARTNER) != null) {
            last *= 2;
        }
        if (attack.hits > last) {
            if (!player.hasGmLevel(1)) {
                Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 密语系统] " + player.getName() + " (等级 " + (int)player.getLevel() + ") 攻击次数异常已自动封锁。 玩家攻击次数 " + (int)attack.hits + " 服务端判断正常攻击次数 " + last + " 技能ID " + attack.skill));
                FileoutputUtil.logToFile("logs/Hack/Ban/技能攻击次数.txt", "\r\n" + FileoutputUtil.NowTime() + "玩家2: " + player.getName() + "(" + (int)player.getLevel() + ") 技能代码: " + attack.skill + " 技能等级: " + player.getSkillLevel(attack.skill) + " 攻击次数 : " + (int)attack.hits + " 正常攻击次数 :" + last);
                if (((Integer)Start.ConfigValuesMap.get("封停IP")).intValue() > 0) {
                    player.ban(player.getName() + "技能攻击次数异常", true, true, false);
                }
                if (((Integer)Start.ConfigValuesMap.get("封停MAC")).intValue() > 0) {
                    player.getClient().banMacs();
                }
                if (((Integer)Start.ConfigValuesMap.get("封停账号")).intValue() > 0) {
                    player.getClient().getSession().close();
                }
                final String reason = "使用违法程式练功";
                if (((Integer)Start.ConfigValuesMap.get("全服通告")).intValue() > 0) {
                    Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[封锁系统] " + player.getName() + " 因为" + reason + "而被管理员永久停权。"));
                }
                return;
            }
            player.dropMessage("攻击次数异常攻击次数 " + (int)attack.hits + " 服务端判断正常攻击次数 " + last + " 技能ID " + attack.skill);
        }
        int CheckCount = effect.getMobCount();
        CheckCount = 技能个数(attack.skill);
        if (attack.targets > CheckCount) {
            if (!player.hasGmLevel(1)) {
                FileoutputUtil.logToFile("logs/Hack/Ban/打怪数量异常.txt", "\r\n " + FileoutputUtil.NowTime() + " 玩家2: " + player.getName() + "(" + (int)player.getLevel() + ") 技能代码: " + attack.skill + " 技能等级: " + player.getSkillLevel(effect.getSourceId()) + " 封包怪物量 : " + (int)attack.targets + " 服务端怪物量 :" + CheckCount);
                Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 密语系统] " + player.getName() + " (等级 " + (int)player.getLevel() + ") 攻击怪物数量异常。 封包怪物量 " + (int)attack.targets + " 服务端怪物量 " + CheckCount + " 技能ID " + attack.skill));
                if (((Integer)Start.ConfigValuesMap.get("封停IP")).intValue() > 0) {
                    player.ban(player.getName() + "打怪数量异常", true, true, false);
                }
                if (((Integer)Start.ConfigValuesMap.get("封停MAC")).intValue() > 0) {
                    player.getClient().banMacs();
                }
                if (((Integer)Start.ConfigValuesMap.get("封停账号")).intValue() > 0) {
                    player.getClient().getSession().close();
                }
                final String reason2 = "使用违法程式练功";
                if (((Integer)Start.ConfigValuesMap.get("全服通告")).intValue() > 0) {
                    Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[封锁系统] " + player.getName() + " 因为" + reason2 + "而被管理员永久停权。"));
                }
                return;
            }
            player.dropMessage("打怪数量异常,技能代码: " + attack.skill + " 封包怪物量 : " + (int)attack.targets + " 服务端怪物量 :" + CheckCount);
        }
        if (attack.hits > 0 && attack.targets > 0 && !player.getStat().checkEquipDurabilitys(player, -1)) {
            player.dropMessage(5, "An item has run out of durability but has no inventory room to go to.");
            return;
        }
        if (GameConstants.isMulungSkill(attack.skill)) {
            if (player.getMapId() / 10000 != 92502) {
                return;
            }
            player.mulungEnergyModify(false);
        }
        if (GameConstants.isPyramidSkill(attack.skill)) {
            if (player.getMapId() / 1000000 != 926) {
                return;
            }
            if (player.getPyramidSubway() == null || !player.getPyramidSubway().onSkillUse(player)) {
                return;
            }
        }
        final PlayerStats stats = player.getStat();
        double maxDamagePerHit;
        if (attack.skill == 1000 || attack.skill == 10001000 || attack.skill == 20001000 || attack.skill == 20011000 || attack.skill == 30001000) {
            maxDamagePerHit = 40.0;
        }
        else if (GameConstants.isPyramidSkill(attack.skill)) {
            maxDamagePerHit = 1.0;
        }
        else {
            final double v75 = (double)effect.getMatk() * 0.058;
            maxDamagePerHit = (double)stats.getTotalMagic() * ((double)stats.getInt() * 0.5 + v75 * v75 + (double)effect.getMatk() * 3.3) / 100.0;
        }
        maxDamagePerHit *= 1.04;
        final Element element = (player.getBuffedValue(MapleBuffStat.ELEMENT_RESET) != null) ? Element.NEUTRAL : theSkill.getElement();
        double MaxDamagePerHit = 0.0;
        int totDamage = 0;
        final int CriticalDamage = stats.passive_sharpeye_percent();
        final ISkill eaterSkill = SkillFactory.getSkill(GameConstants.getMPEaterForJob((int)player.getJob()));
        final int eaterLevel = player.getSkillLevel(eaterSkill);
        final MapleMap map = player.getMap();
        int 循环 = 0;
        for (final AttackPair oned : attack.allDamage) {
            final MapleMonster monster = map.getMonsterByOid(oned.objectid);
            ++循环;
            if (monster != null) {
                final boolean Tempest = monster.getStatusSourceID(MonsterStatus.FREEZE) == 21120006 && !monster.getStats().isBoss();
                int totDamageToOneMonster = 0;
                long newtotDamageToOneMonster = 0L;
                final MapleMonsterStats monsterstats = monster.getStats();
                final int fixeddmg = monsterstats.getFixedDamage();
                MaxDamagePerHit = calculateMaxMagicDamagePerHit(player, theSkill, monster, monsterstats, stats, element, Integer.valueOf(CriticalDamage), maxDamagePerHit);
                byte overallAttackCount = 0;
                for (final Pair<Integer, Boolean> eachde : oned.attack) {
                    Integer eachd = (Integer)eachde.left;
                    ++overallAttackCount;
                    if (!GameConstants.isElseSkill(attack.skill)) {
                        if (GameConstants.Novice_Skill(attack.skill) && eachd.intValue() > 40) {
                            if (!player.hasGmLevel(1)) {
                                FileoutputUtil.logToFile("logs/Hack/Ban/伤害异常.txt", "\r\n " + FileoutputUtil.NowTime() + " 玩家2<" + (int)player.getLevel() + ">: " + player.getName() + " 怪物 " + monster.getId() + " 地图: " + player.getMapId() + " 技能代码: " + attack.skill + " 最高伤害: 40 本次伤害 :" + eachd + " 预计伤害: " + (int)maxDamagePerHit + "是否为BOSS: " + monster.getStats().isBoss());
                                Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 密语系统] " + player.getName() + " (等级 " + (int)player.getLevel() + ") 伤害异常。 最高伤害 40 本次伤害 " + eachd + " 技能ID " + attack.skill));
                                if (((Integer)Start.ConfigValuesMap.get("封停IP")).intValue() > 0) {
                                    player.ban(player.getName() + "伤害异常", true, true, false);
                                }
                                if (((Integer)Start.ConfigValuesMap.get("封停MAC")).intValue() > 0) {
                                    player.getClient().banMacs();
                                }
                                if (((Integer)Start.ConfigValuesMap.get("封停账号")).intValue() > 0) {
                                    player.getClient().getSession().close();
                                }
                                if (((Integer)Start.ConfigValuesMap.get("全服通告")).intValue() > 0) {
                                    Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[封锁系统] " + player.getName() + " 因为伤害异常而被管理员永久停权。"));
                                }
                                return;
                            }
                            player.dropMessage("[GM 密语系统] " + player.getName() + " (等级 " + (int)player.getLevel() + ") 伤害异常。 最高伤害 40 本次伤害 " + eachd + " 技能ID " + attack.skill);
                        }
                        int atk = 2140000001;
                        if (eachd.intValue() < 0) {
                            if (!player.hasGmLevel(1)) {
                                FileoutputUtil.logToFile("logs/Hack/Ban/伤害异常.txt", "\r\n " + FileoutputUtil.NowTime() + " 玩家<" + (int)player.getLevel() + ">: " + player.getName() + " 怪物 " + monster.getId() + " 地图: " + player.getMapId() + " 技能代码: " + attack.skill + " 伤害过低导致越界 本次伤害 :" + eachd + " 预计伤害: " + (int)maxDamagePerHit + "是否为BOSS: " + monster.getStats().isBoss());
                                Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 密语系统] " + player.getName() + " (等级 " + (int)player.getLevel() + ") 伤害异常。 伤害过低导致越界本次伤害 " + eachd + " 技能ID " + attack.skill));
                                if (((Integer)Start.ConfigValuesMap.get("封停IP")).intValue() > 0) {
                                    player.ban(player.getName() + "伤害异常", true, true, false);
                                }
                                if (((Integer)Start.ConfigValuesMap.get("封停MAC")).intValue() > 0) {
                                    player.getClient().banMacs();
                                }
                                if (((Integer)Start.ConfigValuesMap.get("封停账号")).intValue() > 0) {
                                    player.getClient().getSession().close();
                                }
                                if (((Integer)Start.ConfigValuesMap.get("全服通告")).intValue() > 0) {
                                    Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[封锁系统] " + player.getName() + " 因为伤害异常而被管理员永久停权。"));
                                }
                                return;
                            }
                            player.dropMessage("[GM 密语系统] " + player.getName() + " (等级 " + (int)player.getLevel() + ") 伤害异常。 伤害过低导致越界本次伤害 " + eachd + " 技能ID " + attack.skill);
                        }
                        if (!GameConstants.isAran((int)player.getJob()) && player.getLevel() > 10) {
                            boolean ban = false;
                            if (((Integer)Start.ConfigValuesMap.get("伤害检测")).intValue() > 0) {
                                atk = ((Integer)Start.ConfigValuesMap.get("伤害上限")).intValue();
                            }
                            if (eachd.intValue() >= atk && (double)eachd.intValue() > Math.ceil(maxDamagePerHit * 1.2)) {
                                ban = true;
                            }
                            if ((long)eachd.intValue() == monster.getMobMaxHp()) {
                                ban = false;
                            }
                            if (ban) {
                                boolean apple = false;
                                if (player.getBuffSource(MapleBuffStat.WATK) == 2022179 || player.getBuffSource(MapleBuffStat.MATK) == 2022179 || player.getBuffSource(MapleBuffStat.WDEF) == 2022179) {
                                    apple = true;
                                }
                                if (!player.hasGmLevel(1)) {
                                    FileoutputUtil.logToFile("logs/Hack/Ban/伤害异常.txt", "\r\n " + FileoutputUtil.NowTime() + " 玩家2<" + (int)player.getLevel() + ">: " + player.getName() + " 怪物 " + monster.getId() + " 地图: " + player.getMapId() + " 技能代码: " + attack.skill + " 最高伤害: " + atk + " 本次伤害 :" + eachd + " 预计伤害: " + (int)maxDamagePerHit + "是否为BOSS: " + monster.getStats().isBoss() + " 紫色蘋果: " + apple);
                                    Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 密语系统] " + player.getName() + " (等级 " + (int)player.getLevel() + ") 伤害异常。 最高伤害 " + atk + " 本次伤害 " + eachd + " 技能ID " + attack.skill + "预计伤害: " + (int)maxDamagePerHit));
                                    if (((Integer)Start.ConfigValuesMap.get("封停IP")).intValue() > 0) {
                                        player.ban(player.getName() + "伤害异常", true, true, false);
                                    }
                                    if (((Integer)Start.ConfigValuesMap.get("封停MAC")).intValue() > 0) {
                                        player.getClient().banMacs();
                                    }
                                    if (((Integer)Start.ConfigValuesMap.get("封停账号")).intValue() > 0) {
                                        player.getClient().getSession().close();
                                    }
                                    if (((Integer)Start.ConfigValuesMap.get("全服通告")).intValue() > 0) {
                                        Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[封锁系统] " + player.getName() + " 因为伤害异常而被管理员永久停权。"));
                                    }
                                    return;
                                }
                                player.dropMessage("[GM 密语系统] " + player.getName() + " (等级 " + (int)player.getLevel() + ") 伤害异常。 最高伤害 " + atk + " 本次伤害 " + eachd + " 技能ID " + attack.skill + "预计伤害: " + (int)maxDamagePerHit);
                            }
                        }
                    }
                    if (fixeddmg != -1) {
                        eachd = Integer.valueOf(monsterstats.getOnlyNoramlAttack() ? 0 : fixeddmg);
                    }
                    else if (monsterstats.getOnlyNoramlAttack()) {
                        eachd = Integer.valueOf(0);
                    }
                    else if (!player.isGM() && ((Integer)Start.ConfigValuesMap.get("伤害修正")).intValue() > 0) {
                        if (!Tempest) {
                            if (!monster.isBuffed(MonsterStatus.DAMAGE_IMMUNITY) && !monster.isBuffed(MonsterStatus.MAGIC_IMMUNITY) && !monster.isBuffed(MonsterStatus.MAGIC_DAMAGE_REFLECT)) {
                                if ((double)eachd.intValue() > maxDamagePerHit) {
                                    player.getCheatTracker().registerOffense(CheatingOffense.HIGH_DAMAGE, "[伤害: " + eachd + ", 预期: " + maxDamagePerHit + ", 怪物: " + monster.getId() + "] [职业: " + (int)player.getJob() + ", 等级: " + (int)player.getLevel() + ", 使用的技能: " + attack.skill + "]");
                                    if (attack.real) {
                                        player.getCheatTracker().checkSameDamage(eachd.intValue(), maxDamagePerHit);
                                    }
                                    if ((double)eachd.intValue() > MaxDamagePerHit * 2.0) {
                                        eachd = Integer.valueOf((int)(MaxDamagePerHit * 2.0));
                                        player.getCheatTracker().registerOffense(CheatingOffense.HIGH_DAMAGE_MAGIC_2, "[伤害: " + eachd + ", 预期: " + maxDamagePerHit + ", 怪物: " + monster.getId() + "] [职业: " + (int)player.getJob() + ", 等级: " + (int)player.getLevel() + ", 使用的技能: " + attack.skill + "]");
                                        if (eachd.intValue() >= 10000 && ServerConfig.LOG_DAMAGE) {
                                            FileoutputUtil.logToFile("Logs/hack/伤害计算/魔法伤害计算修正_" + attack.skill + ".txt", "\r\n " + FileoutputUtil.NowTime() + " 玩家: " + player.getName() + "(" + (int)player.getLevel() + ") 职业: " + (int)player.getJob() + " 怪物:" + monster.getId() + " 封包伤害 :" + eachd + " 预计伤害 :" + (int)maxDamagePerHit + "是否为BOSS: " + monster.getStats().isBoss(), false, false);
                                            player.getCheatTracker().registerOffense(CheatingOffense.HIGH_DAMAGE_MAGIC_2, "[伤害: " + eachd + ", 预期: " + maxDamagePerHit + ", 怪物: " + monster.getId() + "] [职业: " + (int)player.getJob() + ", 等级: " + (int)player.getLevel() + ", 使用的技能: " + attack.skill + "]");
                                            return;
                                        }
                                    }
                                }
                            }
                            else if ((double)eachd.intValue() > maxDamagePerHit) {
                                eachd = Integer.valueOf((int)maxDamagePerHit);
                                if ((double)eachd.intValue() > MaxDamagePerHit * 2.0 && maxDamagePerHit != 1.0 && ServerConfig.LOG_DAMAGE) {
                                    FileoutputUtil.logToFile("Logs/hack/伤害计算/魔法伤害计算修正_" + attack.skill + ".txt", "\r\n " + FileoutputUtil.NowTime() + " 玩家: " + player.getName() + "(" + (int)player.getLevel() + ") 职业: " + (int)player.getJob() + " 怪物:" + monster.getId() + " 封包伤害 :" + eachd + " 预计伤害 :" + (int)maxDamagePerHit + "是否为BOSS: " + monster.getStats().isBoss(), false, false);
                                }
                            }
                        }
                    }
                    totDamageToOneMonster += eachd.intValue();
                    newtotDamageToOneMonster += (long)eachd.intValue();
                }
                totDamage += totDamageToOneMonster;
                player.checkMonsterAggro(monster);
                final double range = player.getPosition().distanceSq((Point2D)monster.getPosition());
                double SkillRange = GameConstants.getAttackRange(player, effect, attack);
                if (((Integer)Start.ConfigValuesMap.get("重置技能范围开关")).intValue() > 0) {
                    SkillRange = (double)((Integer)Start.ConfigValuesMap.get("重置技能总范围")).intValue();
                }
                if (player.getDebugMessage() && range > SkillRange) {
                    player.dropMessage("技能[" + attack.skill + "] 预计范围: " + (int)SkillRange + " 实際范围: " + (int)range);
                }
                if (range > SkillRange && !player.inBossMap() && attack.skill != 5221004 && attack.skill != 21120006) {
                    player.getCheatTracker().registerOffense(CheatingOffense.ATTACK_FARAWAY_MONSTER, "攻击范围异常,技能:" + attack.skill + "(" + SkillFactory.getName(attack.skill) + ")\u3000正常范围:" + (int)SkillRange + " 计算范围:" + (int)range);
                    if (range > SkillRange * 2.0 && ((Integer)Start.ConfigValuesMap.get("全屏检测")).intValue() > 0) {
                        player.getCheatTracker().registerOffense(CheatingOffense.ATTACK_FARAWAY_MONSTER_BAN, "超大攻击范围,技能:" + attack.skill + "(" + SkillFactory.getName(attack.skill) + ")\u3000怪物:" + monster.getId() + " 正常范围:" + (int)SkillRange + " 计算范围:" + (int)range);
                    }
                    return;
                }
                if (attack.skill == 2301002 && !monsterstats.getUndead()) {
                    player.getCheatTracker().registerOffense(CheatingOffense.HEAL_ATTACKING_UNDEAD);
                    FileoutputUtil.logToFile("logs/Hack/Ban/技能异常.txt", "\r\n " + FileoutputUtil.NowTime() + " 玩家2<" + (int)player.getLevel() + ">: " + player.getName() + " 怪物 " + monster.getId() + " 地图: " + player.getMapId() + " 技能代码: " + attack.skill + " 使用群体治愈攻击非不死系怪物");
                    Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 密语系统] " + player.getName() + " (等级 " + (int)player.getLevel() + ") 技能异常。 使用群体治愈攻击非不死系怪物 技能ID " + attack.skill));
                    if (((Integer)Start.ConfigValuesMap.get("封停账号")).intValue() > 0) {
                        Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[封锁系统] " + player.getName() + " 因为技能异常而被管理员永久停权。"));
                        player.ban(player.getName() + "修改WZ", true, true, false);
                        player.getClient().getSession().close();
                        return;
                    }
                }
                if (newtotDamageToOneMonster <= 0L) {
                    continue;
                }
                final tzjc t = new tzjc();
                long newDamage = 0L;
                String mobname=monster.getStats().getName();
                long mobhp=monster.getHp();
                //String 计算4 = String.format("%.2f",mobhp*1.0/100000000);
                newDamage = 额外伤害(player, newtotDamageToOneMonster, monster);
                newtotDamageToOneMonster += newDamage;
                newtotDamageToOneMonster = t.star_damage(player, newtotDamageToOneMonster, monster);
                newtotDamageToOneMonster = 伤害减伤(monster.getId(), newtotDamageToOneMonster);
                monster.damage(player, newtotDamageToOneMonster, true, attack.skill);
                if(monster.getStats().isBoss()||Game.ismyboss(monster.getId()))
                    {
                        //player.dropMessage(-1, "当前攻击怪物:"+mobname+"  剩余血量:"+mobhp+"");
                        //player.dropMessage(-1, "当前攻击怪物:"+mobname+"  剩余血量: "+计算4+" E");
                         if(mobhp<100000000){
                                player.dropMessage(-1, "当前攻击怪物:"+mobname+"  剩余血量: "+mobhp+"");
                        }else{
                            player.dropMessage(-1, "当前攻击怪物:"+mobname+"  剩余血量: "+String.format("%.2f",mobhp*1.0/100000000)+" E");
                        }
                    }
                if (monster.isBuffed(MonsterStatus.MAGIC_DAMAGE_REFLECT)) {
                    player.addHP(-(7000 + Randomizer.nextInt(8000)));
                }
                switch (attack.skill) {
                    case 2221003: {
                        monster.setTempEffectiveness(Element.FIRE, (long)theSkill.getEffect((int)player.getSkillLevel(theSkill)).getDuration());
                        break;
                    }
                    case 2121003: {
                        monster.setTempEffectiveness(Element.ICE, (long)theSkill.getEffect((int)player.getSkillLevel(theSkill)).getDuration());
                        break;
                    }
                }
                if (effect.getMonsterStati().size() >= 0 && effect.makeChanceResult()) {
                    for (final Entry<MonsterStatus, Integer> z : effect.getMonsterStati().entrySet()) {
                        monster.applyStatus(player, new MonsterStatusEffect((MonsterStatus)z.getKey(), (Integer)z.getValue(), theSkill.getId(), null, false), effect.isPoison(), (long)effect.getDuration(), monster.getStats().isBoss(), effect);
                    }
                }
                if (eaterLevel <= 0) {
                    continue;
                }
                eaterSkill.getEffect(eaterLevel).applyPassive(player, (MapleMapObject)monster);
            }
        }
        if (attack.skill != 2301002) {
            effect.applyTo(player);
        }
        if (totDamage > 1) {
            final CheatTracker tracker = player.getCheatTracker();
            tracker.setAttacksWithoutHit(true);
            if (tracker.getAttacksWithoutHit() > 1000) {
                tracker.registerOffense(CheatingOffense.ATTACK_WITHOUT_GETTING_HIT, Integer.toString(tracker.getAttacksWithoutHit()));
            }
        }
    }
    
    private static double calculateMaxMagicDamagePerHit(final MapleCharacter chr, final ISkill skill, final MapleMonster monster, final MapleMonsterStats mobstats, final PlayerStats stats, final Element elem, final Integer sharpEye, final double maxDamagePerMonster) {
        final int dLevel = Math.max(mobstats.getLevel() - chr.getLevel(), 0);
        final int Accuracy = (int)(Math.floor((double)stats.getTotalInt() / 10.0) + Math.floor((double)stats.getTotalLuk() / 10.0));
        final int MinAccuracy = mobstats.getEva() * (dLevel * 2 + 51) / 120;
        if (MinAccuracy > Accuracy && skill.getId() != 1000 && skill.getId() != 10001000 && skill.getId() != 20001000 && skill.getId() != 20011000 && skill.getId() != 30001000 && !GameConstants.isPyramidSkill(skill.getId())) {
            return 0.0;
        }
        double elemMaxDamagePerMob = 0.0;
        switch (monster.getEffectiveness(elem)) {
            case IMMUNE: {
                elemMaxDamagePerMob = 1.0;
                break;
            }
            case NORMAL: {
                elemMaxDamagePerMob = ElementalStaffAttackBonus(elem, maxDamagePerMonster, stats);
                break;
            }
            case WEAK: {
                elemMaxDamagePerMob = ElementalStaffAttackBonus(elem, maxDamagePerMonster * 1.5, stats);
                break;
            }
            case STRONG: {
                elemMaxDamagePerMob = ElementalStaffAttackBonus(elem, maxDamagePerMonster * 0.5, stats);
                break;
            }
            default: {
                throw new RuntimeException("Unknown enum constant");
            }
        }
        elemMaxDamagePerMob -= (double)mobstats.getMagicDefense() * 0.5;
        elemMaxDamagePerMob += elemMaxDamagePerMob / 100.0 * (double)sharpEye.intValue();
        if (skill.getId() == 21120006) {
            elemMaxDamagePerMob *= 15.0;
        }
        if (skill.getId() == 2211006) {
            elemMaxDamagePerMob *= 2.0;
        }
        elemMaxDamagePerMob += elemMaxDamagePerMob * (mobstats.isBoss() ? stats.bossdam_r : stats.dam_r) / 100.0;
        switch (skill.getId()) {
            case 1000:
            case 10001000:
            case 20001000: {
                elemMaxDamagePerMob = 40.0;
                break;
            }
            case 1020:
            case 10001020:
            case 20001020: {
                elemMaxDamagePerMob = 1.0;
                break;
            }
        }
        if (elemMaxDamagePerMob <= 500000.0) {
            if (elemMaxDamagePerMob < 0.0) {
                elemMaxDamagePerMob = 1.0;
            }
        }
        return elemMaxDamagePerMob;
    }
    
    private static final double ElementalStaffAttackBonus(final Element elem, final double elemMaxDamagePerMob, final PlayerStats stats) {
        switch (elem) {
            case FIRE: {
                return elemMaxDamagePerMob / 100.0 * (double)stats.element_fire;
            }
            case ICE: {
                return elemMaxDamagePerMob / 100.0 * (double)stats.element_ice;
            }
            case LIGHTING: {
                return elemMaxDamagePerMob / 100.0 * (double)stats.element_light;
            }
            case POISON: {
                return elemMaxDamagePerMob / 100.0 * (double)stats.element_psn;
            }
            default: {
                return elemMaxDamagePerMob / 100.0 * (double)stats.def;
            }
        }
    }
    
    private static void handlePickPocket(final MapleCharacter player, final MapleMonster mob, final AttackPair oned) {
        final int maxmeso = player.getBuffedValue(MapleBuffStat.PICKPOCKET).intValue();
        final ISkill skill = SkillFactory.getSkill(4211003);
        final MapleStatEffect s = skill.getEffect((int)player.getSkillLevel(skill));
        for (final Pair eachde : oned.attack) {
            final Integer eachd = (Integer)eachde.left;
            if (s.makeChanceResult()) {
                player.getMap().spawnMesoDrop(Math.min((int)Math.max((double)eachd.intValue() / 20000.0 * (double)maxmeso, 1.0), maxmeso), new Point((int)(mob.getTruePosition().getX() + (double)Randomizer.nextInt(100) - 50.0), (int)mob.getTruePosition().getY()), (MapleMapObject)mob, player, false, (byte)0);
            }
        }
    }
    
    private static double calculateMaxWeaponDamagePerHit(final MapleCharacter player, final MapleMonster monster, final AttackInfo attack, final ISkill theSkill, final MapleStatEffect attackEffect, double maximumDamageToMonster, final Integer CriticalDamagePercent) {
        if (player.getMapId() / 1000000 == 914) {
            return 5.0E8;
        }
        final List<Element> elements = (List<Element>)new ArrayList();
        boolean defined = false;
        if (theSkill != null) {
            elements.add(theSkill.getElement());
            if (monster.getStatusSourceID(MonsterStatus.FREEZE) == 21120006) {
                defined = true;
            }
            switch (theSkill.getId()) {
                case 3001004:
                case 3221001: {
                    defined = true;
                    break;
                }
                case 1000:
                case 10001000:
                case 20001000:
                case 20011000:
                case 30001000: {
                    maximumDamageToMonster = 40.0;
                    defined = true;
                    break;
                }
                case 1020:
                case 10001020:
                case 20001020:
                case 20011020:
                case 30001020: {
                    maximumDamageToMonster = 1.0;
                    defined = true;
                    break;
                }
                case 3221007: {
                    maximumDamageToMonster = (double)(monster.getStats().isBoss() ? 500000L : monster.getMobMaxHp());
                    defined = true;
                    break;
                }
                case 1221011: {
                    maximumDamageToMonster = (double)(monster.getStats().isBoss() ? 500000L : (monster.getHp() - 1L));
                    defined = true;
                    break;
                }
                case 4211006: {
                    maximumDamageToMonster = (double)(monster.getStats().isBoss() ? 500000L : monster.getMobMaxHp());
                    defined = true;
                    break;
                }
                case 1009:
                case 10001009:
                case 20001009:
                case 20011009:
                case 30001009: {
                    defined = true;
                    maximumDamageToMonster = (double)(monster.getStats().isBoss() ? (monster.getMobMaxHp() / 30L * 100L) : monster.getMobMaxHp());
                    break;
                }
                case 3211006: {
                    if (monster.getStatusSourceID(MonsterStatus.FREEZE) == 3211003) {
                        defined = true;
                        maximumDamageToMonster = (double)monster.getHp();
                        break;
                    }
                    break;
                }
                case 5121007: {
                    maximumDamageToMonster *= 2.8;
                    break;
                }
                case 1111008:
                case 1121006:
                case 1311001:
                case 1311006:
                case 4201005: {
                    maximumDamageToMonster *= 3.0;
                    break;
                }
                case 1001004:
                case 1121008:
                case 4221001: {
                    maximumDamageToMonster *= 2.5;
                    break;
                }
                case 1001005:
                case 1311004:
                case 3121004: {
                    maximumDamageToMonster *= 2.0;
                    break;
                }
            }
        }
        if (MapleJob.is战神((int)player.getJob())) {
            maximumDamageToMonster *= 2.0;
        }
        else if (MapleJob.is拳霸((int)player.getJob())) {
            maximumDamageToMonster *= 1.1;
        }
        if (player.getBuffedValue(MapleBuffStat.WK_CHARGE) != null) {
            final int chargeSkillId = player.getBuffSource(MapleBuffStat.WK_CHARGE);
            switch (chargeSkillId) {
                case 1211003:
                case 1211004: {
                    elements.add(Element.FIRE);
                    break;
                }
                case 1211005:
                case 1211006:
                case 21111005: {
                    elements.add(Element.ICE);
                    break;
                }
                case 1211007:
                case 1211008:
                case 15101006: {
                    elements.add(Element.LIGHTING);
                    break;
                }
                case 1221003:
                case 1221004:
                case 11111007: {
                    elements.add(Element.HOLY);
                    break;
                }
                case 12101005: {
                    elements.clear();
                    break;
                }
            }
        }
        if (player.getBuffedValue(MapleBuffStat.LIGHTNING_CHARGE) != null) {
            elements.add(Element.LIGHTING);
        }
        double elementalMaxDamagePerMonster = maximumDamageToMonster;
        if (elements.size() > 0) {
            double elementalEffect = 0.0;
            switch (attack.skill) {
                case 3111003:
                case 3211003: {
                    elementalEffect = (double)attackEffect.getX() / 200.0;
                    break;
                }
                default: {
                    elementalEffect = 0.5;
                    break;
                }
            }
            for (final Element element : elements) {
                switch (monster.getEffectiveness(element)) {
                    case IMMUNE: {
                        elementalMaxDamagePerMonster = 1.0;
                        continue;
                    }
                    case WEAK: {
                        elementalMaxDamagePerMonster *= 1.0 + elementalEffect;
                        continue;
                    }
                    case STRONG: {
                        elementalMaxDamagePerMonster *= 1.0 - elementalEffect;
                        continue;
                    }
                }
            }
        }
        final short moblevel = monster.getStats().getLevel();
        final short d = (short)((moblevel > player.getLevel()) ? ((short)(moblevel - player.getLevel())) : 0);
        elementalMaxDamagePerMonster = elementalMaxDamagePerMonster * (1.0 - 0.01 * (double)d) - (double)monster.getStats().getPhysicalDefense() * 0.5;
        elementalMaxDamagePerMonster += elementalMaxDamagePerMonster / 100.0 * (double)CriticalDamagePercent.intValue();
        if (theSkill != null && theSkill.isChargeSkill() && player.getKeyDownSkill_Time() == 0L && theSkill.getId() != 4111005) {
            return 0.0;
        }
        final MapleStatEffect homing = player.getStatForBuff(MapleBuffStat.HOMING_BEACON);
        if (homing != null && player.getLinkMid() == monster.getObjectId() && homing.getSourceId() == 5220011) {
            elementalMaxDamagePerMonster += elementalMaxDamagePerMonster * (double)homing.getX();
        }
        final PlayerStats stat = player.getStat();
        elementalMaxDamagePerMonster += elementalMaxDamagePerMonster * (monster.getStats().isBoss() ? (stat.bossdam_r * 2.0) : stat.dam_r) / 100.0;
        switch (monster.getId()) {
            case 1110101: {
                elementalMaxDamagePerMonster *= 2.0;
                break;
            }
        }
        if (player.getDebugMessage()) {
            player.dropMessage("[伤害计算]属性伤害：" + (int)Math.ceil(elementalMaxDamagePerMonster) + " BOSS伤害：" + (int)Math.ceil((monster.getStats().isBoss() ? player.getStat().bossdam_r : player.getStat().dam_r) - 100.0) + "%");
        }
        if (elementalMaxDamagePerMonster > 500000.0) {
            if (!defined) {}
        }
        else if (elementalMaxDamagePerMonster < 0.0) {
            elementalMaxDamagePerMonster = 1.0;
        }
        return elementalMaxDamagePerMonster;
    }
    
    public static final AttackInfo DivideAttack(final AttackInfo attack, final int rate) {
        attack.real = false;
        for (final AttackPair p : attack.allDamage) {
            if (p.attack != null) {
                for (final Pair<Integer, Boolean> eachd : p.attack) {
                    long 伤害 = 0L;
                    伤害 = (long)((Integer)eachd.left).intValue() * (long)(((Integer)Start.ConfigValuesMap.get("克隆基础伤害")).intValue() + rate) / 100L;
                    if (伤害 >= 2100000000L) {
                        eachd.left = Integer.valueOf(2100000000);
                    }
                    else {
                        eachd.left = Integer.valueOf((int)伤害);
                    }
                }
            }
        }
        return attack;
    }
    
    public static final AttackInfo Modify_AttackCrit(final AttackInfo attack, final MapleCharacter chr, final int type) {
        final int criticalRate = chr.getStat().passive_sharpeye_rate();
        final boolean shadow = (type == 2 && chr.getBuffedValue(MapleBuffStat.SHADOWPARTNER) != null) || (type == 1 && chr.getBuffedValue(MapleBuffStat.MIRROR_IMAGE) != null);
        if (attack.skill != 4211006 && attack.skill != 3211003 && attack.skill != 4111004 && (criticalRate > 0 || attack.skill == 4221001 || attack.skill == 3221007)) {
            for (final AttackPair attackPair : attack.allDamage) {
                if (attackPair.attack != null) {
                    int hit = 0;
                    final int midAtt = attackPair.attack.size() / 2;
                    final List<Pair<Integer, Boolean>> eachd_copy = new ArrayList(attackPair.attack);
                    for (final Pair<Integer, Boolean> eachd : attackPair.attack) {
                        ++hit;
                        if (!((Boolean)eachd.right).booleanValue()) {
                            if (attack.skill == 4221001) {
                                eachd.right = Boolean.valueOf(hit == 4 && Randomizer.nextInt(100) < 90);
                            }
                            else if (attack.skill == 3221007 || ((Integer)eachd.left).intValue() > 199999) {
                                eachd.right = Boolean.valueOf(true);
                            }
                            else if (shadow && hit > midAtt) {
                                eachd.right = (eachd_copy.get(hit - 1 - midAtt)).right;
                            }
                            else {
                                eachd.right = Boolean.valueOf(Randomizer.nextInt(100) < criticalRate);
                            }
                            (eachd_copy.get(hit - 1)).right = eachd.right;
                        }
                    }
                }
            }
        }
        return attack;
    }
    
    public static final AttackInfo parseDmgMa(final LittleEndianAccessor lea, final MapleCharacter chr) {
        final AttackInfo ret = new AttackInfo();
        lea.skip(1);
        lea.skip(8);
        ret.tbyte = lea.readByte();
        ret.targets = (byte)(ret.tbyte >>> 4 & 0xF);
        ret.hits = (byte)(ret.tbyte & 0xF);
        lea.skip(8);
        ret.skill = lea.readInt();
        lea.skip(12);
        switch (ret.skill) {
            case 2121001:
            case 2221001:
            case 2321001:
            case 22121000:
            case 22151001: {
                ret.charge = lea.readInt();
                break;
            }
            default: {
                ret.charge = -1;
                break;
            }
        }
        lea.skip(1);
        ret.unk = 0;
        ret.display = lea.readByte();
        ret.animation = lea.readByte();
        lea.skip(1);
        ret.speed = lea.readByte();
        if (ret.display == 2126 && ret.speed == 5 && ret.skill == 0) {
            Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[系统警告] ID " + chr.getId() + " " + chr.getName() + " 疑似使用路飞霸气。 "));
        }
        ret.lastAttackTickCount = lea.readInt();
        ret.allDamage = (List<AttackPair>)new ArrayList();
        for (int i = 0; i < ret.targets; ++i) {
            final int oid = lea.readInt();
            lea.skip(14);
            final List<Pair<Integer, Boolean>> allDamageNumbers = (List<Pair<Integer, Boolean>>)new ArrayList();
            for (int j = 0; j < ret.hits; ++j) {
                int damage = lea.readInt();
                int 系统最大破功 = 199999;
                if (((Integer)Start.ConfigValuesMap.get("伤害突破开关")).intValue() > 0) {
                    系统最大破功 = ((Integer)Start.ConfigValuesMap.get("突破上线")).intValue();
                }
                int maxdamage = 0;
                if (((Integer)Start.ConfigValuesMap.get("装备卡破功开关")).intValue() > 0) {
                    int 破攻值 = 0;
                    int 装备破攻值 = 0;
                    破攻值 = 装备破功(chr);
                    装备破攻值 = 装备基础破功(chr);
                    maxdamage = 装备破攻值 + 破攻值;
                }
                if (((Integer)Start.ConfigValuesMap.get("装备卡破功开关")).intValue() > 0 && damage > maxdamage) {
                    damage = maxdamage;
                }
                if (((Integer)Start.ConfigValuesMap.get("表单卡破功开关")).intValue() > 0) {
                    maxdamage = (int)chr.getLimitBreak();
                }
                if (((Integer)Start.ConfigValuesMap.get("表单卡破功开关")).intValue() > 0 && damage > maxdamage) {
                    damage = maxdamage;
                }
                if (damage > 系统最大破功) {
                    damage = 系统最大破功;
                }
                if ((ret.skill == 3221007 || ret.skill == 4221001) && damage >= 199999) {
                    damage = 199999;
                }
                if (chr.isGM() && ((Integer)Start.ConfigValuesMap.get("GM固伤开关")).intValue() > 0) {
                    damage = GM伤害(chr);
                }
                if (((Integer)Start.ConfigValuesMap.get("所有显示开关")).intValue() > 0 && ((Integer)Start.ConfigValuesMap.get("突破显示开关")).intValue() > 0) {
                    chr.dropMessage(-1, "客户端传输实际伤害1: " + damage);
                }
                allDamageNumbers.add(new Pair(Integer.valueOf(damage), Boolean.valueOf(false)));
            }
            lea.skip(4);
            ret.allDamage.add(new AttackPair(Integer.valueOf(oid).intValue(), allDamageNumbers));
        }
        ret.position = lea.readPos();
        return ret;
    }
    
    public static final AttackInfo parseDmgM(final LittleEndianAccessor lea, final MapleCharacter chr) {
        final AttackInfo ret = new AttackInfo();
        lea.skip(1);
        lea.skip(8);
        ret.tbyte = lea.readByte();
        ret.targets = (byte)(ret.tbyte >>> 4 & 0xF);
        ret.hits = (byte)(ret.tbyte & 0xF);
        lea.skip(8);
        ret.skill = lea.readInt();
        lea.skip(12);
        switch (ret.skill) {
            case 5101004:
            case 5201002:
            case 14111006:
            case 15101003: {
                ret.charge = lea.readInt();
                break;
            }
            default: {
                ret.charge = 0;
                break;
            }
        }
        ret.unk = lea.readByte();
        ret.display = lea.readByte();
        ret.animation = lea.readByte();
        lea.skip(1);
        ret.speed = lea.readByte();
        if (ret.display == 2126 && ret.speed == 5 && ret.skill == 0) {
            Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[系统警告] ID " + chr.getId() + " " + chr.getName() + " 疑似使用路飞霸气。 "));
        }
        ret.lastAttackTickCount = lea.readInt();
        ret.allDamage = (List<AttackPair>)new ArrayList();
        if (ret.skill == 4211006) {
            return parseExplosionAttack(lea, ret);
        }
        for (int i = 0; i < ret.targets; ++i) {
            final int oid = lea.readInt();
            lea.skip(14);
            final List<Pair<Integer, Boolean>> allDamageNumbers = (List<Pair<Integer, Boolean>>)new ArrayList();
            for (int j = 0; j < ret.hits; ++j) {
                int damage = lea.readInt();
                int 系统最大破功 = 199999;
                if (((Integer)Start.ConfigValuesMap.get("伤害突破开关")).intValue() > 0) {
                    系统最大破功 = ((Integer)Start.ConfigValuesMap.get("突破上线")).intValue();
                }
                int maxdamage = 0;
                if (((Integer)Start.ConfigValuesMap.get("装备卡破功开关")).intValue() > 0) {
                    int 破攻值 = 0;
                    int 装备破攻值 = 0;
                    破攻值 = 装备破功(chr);
                    装备破攻值 = 装备基础破功(chr);
                    maxdamage = 装备破攻值 + 破攻值;
                }
                if (((Integer)Start.ConfigValuesMap.get("装备卡破功开关")).intValue() > 0 && damage > maxdamage) {
                    damage = maxdamage;
                }
                if (((Integer)Start.ConfigValuesMap.get("表单卡破功开关")).intValue() > 0) {
                    maxdamage = (int)chr.getLimitBreak();
                }
                if (((Integer)Start.ConfigValuesMap.get("表单卡破功开关")).intValue() > 0 && damage > maxdamage) {
                    damage = maxdamage;
                }
                if (damage > 系统最大破功) {
                    damage = 系统最大破功;
                }
                if ((ret.skill == 3221007 || ret.skill == 4221001) && damage >= 199999) {
                    damage = 199999;
                }
                if (chr.isGM() && ((Integer)Start.ConfigValuesMap.get("GM固伤开关")).intValue() > 0) {
                    damage = GM伤害(chr);
                }
                if (((Integer)Start.ConfigValuesMap.get("所有显示开关")).intValue() > 0 && ((Integer)Start.ConfigValuesMap.get("突破显示开关")).intValue() > 0) {
                    chr.dropMessage(-1, "客户端传输实际伤害2: " + damage);
                }
                allDamageNumbers.add(new Pair(Integer.valueOf(damage), Boolean.valueOf(false)));
            }
            lea.skip(4);
            ret.allDamage.add(new AttackPair(Integer.valueOf(oid).intValue(), allDamageNumbers));
        }
        ret.position = lea.readPos();
        if (ret.skill == 14111006) {
            ret.positionxy = lea.readPos();
        }
        return ret;
    }
    
    public static final AttackInfo parseDmgR(final LittleEndianAccessor lea, final MapleCharacter chr) {
        final AttackInfo ret = new AttackInfo();
        lea.skip(1);
        lea.skip(8);
        ret.tbyte = lea.readByte();
        ret.targets = (byte)(ret.tbyte >>> 4 & 0xF);
        ret.hits = (byte)(ret.tbyte & 0xF);
        lea.skip(8);
        ret.skill = lea.readInt();
        lea.skip(12);
        switch (ret.skill) {
            case 3121004:
            case 3221001:
            case 5221004:
            case 13111002: {
                lea.skip(4);
                break;
            }
        }
        ret.charge = -1;
        ret.unk = lea.readByte();
        ret.display = lea.readByte();
        ret.animation = lea.readByte();
        lea.skip(1);
        ret.speed = lea.readByte();
        if (ret.display == 2126 && ret.speed == 5 && ret.skill == 0) {
            Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[系统警告] ID " + chr.getId() + " " + chr.getName() + " 疑似使用路飞霸气。 "));
        }
        ret.lastAttackTickCount = lea.readInt();
        ret.slot = (byte)lea.readShort();
        ret.csstar = (byte)lea.readShort();
        ret.AOE = lea.readByte();
        ret.allDamage = (List<AttackPair>)new ArrayList();
        for (int i = 0; i < ret.targets; ++i) {
            final int oid = lea.readInt();
            lea.skip(14);
            final List<Pair<Integer, Boolean>> allDamageNumbers = (List<Pair<Integer, Boolean>>)new ArrayList();
            for (int j = 0; j < ret.hits; ++j) {
                int damage = lea.readInt();
                int 系统最大破功 = 199999;
                if (((Integer)Start.ConfigValuesMap.get("伤害突破开关")).intValue() > 0) {
                    系统最大破功 = ((Integer)Start.ConfigValuesMap.get("突破上线")).intValue();
                }
                int maxdamage = 0;
                if (((Integer)Start.ConfigValuesMap.get("装备卡破功开关")).intValue() > 0) {
                    int 破攻值 = 0;
                    int 装备破攻值 = 0;
                    破攻值 = 装备破功(chr);
                    装备破攻值 = 装备基础破功(chr);
                    maxdamage = 装备破攻值 + 破攻值;
                }
                if (((Integer)Start.ConfigValuesMap.get("装备卡破功开关")).intValue() > 0 && damage > maxdamage) {
                    damage = maxdamage;
                }
                if (((Integer)Start.ConfigValuesMap.get("表单卡破功开关")).intValue() > 0) {
                    maxdamage = (int)chr.getLimitBreak();
                }
                if (((Integer)Start.ConfigValuesMap.get("表单卡破功开关")).intValue() > 0 && damage > maxdamage) {
                    damage = maxdamage;
                }
                if (damage > 系统最大破功) {
                    damage = 系统最大破功;
                }
                if ((ret.skill == 3221007 || ret.skill == 4221001) && damage >= 199999) {
                    damage = 199999;
                }
                if (chr.isGM() && ((Integer)Start.ConfigValuesMap.get("GM固伤开关")).intValue() > 0) {
                    damage = GM伤害(chr);
                }
                if (((Integer)Start.ConfigValuesMap.get("所有显示开关")).intValue() > 0 && ((Integer)Start.ConfigValuesMap.get("突破显示开关")).intValue() > 0) {
                    chr.dropMessage(-1, "客户端传输实际伤害3: " + damage);
                }
                allDamageNumbers.add(new Pair(Integer.valueOf(damage), Boolean.valueOf(false)));
            }
            lea.skip(4);
            ret.allDamage.add(new AttackPair(Integer.valueOf(oid).intValue(), allDamageNumbers));
        }
        lea.skip(4);
        ret.position = lea.readPos();
        return ret;
    }
    
    public static final AttackInfo parseExplosionAttack(final LittleEndianAccessor lea, final AttackInfo ret) {
        if (ret.hits == 0) {
            lea.skip(4);
            final byte bullets = lea.readByte();
            for (int j = 0; j < bullets; ++j) {
                ret.allDamage.add(new AttackPair(lea.readInt(), null));
                lea.skip(1);
            }
            lea.skip(2);
            return ret;
        }
        for (int i = 0; i < ret.targets; ++i) {
            final int oid = lea.readInt();
            lea.skip(12);
            final byte bullets2 = lea.readByte();
            final List<Pair<Integer, Boolean>> allDamageNumbers = (List<Pair<Integer, Boolean>>)new ArrayList();
            for (int k = 0; k < bullets2; ++k) {
                allDamageNumbers.add(new Pair(Integer.valueOf(lea.readInt()), Boolean.valueOf(false)));
            }
            ret.allDamage.add(new AttackPair(oid, allDamageNumbers));
            lea.skip(4);
        }
        lea.skip(4);
        final byte bullets = lea.readByte();
        for (int j = 0; j < bullets; ++j) {
            ret.allDamage.add(new AttackPair(lea.readInt(), null));
            lea.skip(1);
        }
        lea.skip(2);
        return ret;
    }
    
    public static int GM伤害(final MapleCharacter chr) {
        final int 伤害 = ((Integer)Start.ConfigValuesMap.get("GM固伤伤害")).intValue();
        return 伤害;
    }
    
    public static int 装备破功(final MapleCharacter chr) {
        int 破攻值 = 0;
        int 破攻系数 = 0;
        final IItem 装备 = chr.getInventory(MapleInventoryType.EQUIPPED).getItem((short)(-11));
        if (装备 != null && 装备 instanceof Equip) {
            破攻系数 = MapleItemInformationProvider.getInstance().getHands((Equip)装备);
            破攻值 = 破攻系数 * 10000;
        }
        return 破攻值;
    }
    
    public static int 技能段数(final int skill) {
        int last = 99;
        if (((Integer)Start.ConfigValuesMap.get("段数检测")).intValue() > 0) {
            for (int a = 0; a < Start.SkillType.size(); ++a) {
                if (skill == ((SkillType)Start.SkillType.get(a)).getSkillId()) {
                    last = ((SkillType)Start.SkillType.get(a)).getAttackCount();
                    break;
                }
            }
        }
        return last;
    }
    
    public static int 技能个数(final int skill) {
        int last = 99;
        if (((Integer)Start.ConfigValuesMap.get("个数检测")).intValue() > 0) {
            for (int a = 0; a < Start.SkillType.size(); ++a) {
                if (skill == ((SkillType)Start.SkillType.get(a)).getSkillId()) {
                    last = ((SkillType)Start.SkillType.get(a)).getMobCount();
                    break;
                }
            }
        }
        return last;
    }
    
    public static long 伤害减伤(final int mobid, final long damage) {
        long 数值 = 0L;
        if (((Integer)Start.ConfigValuesMap.get("怪物减伤开关")).intValue() > 0) {
            数值 = (long)Math.floor((double)(damage / (long)getMobRedDam(mobid)));
        }
        else {
            数值 = damage;
        }
        return 数值;
    }
    
    public static long 额外伤害(final MapleCharacter play, final long damage, final MapleMonster monster) {
        long 数值 = 0L;
        try {
            if (((Integer)Start.ConfigValuesMap.get("自定义伤害加成开关")).intValue() < 1) {
                return 数值;
            }
            if (damage >= (long)((Integer)Start.ConfigValuesMap.get("伤害高于次数值")).intValue() || (((Integer)Start.ConfigValuesMap.get("道具加成自定义伤害开关")).intValue() > 0 && play.getItemQuantity(((Integer)Start.ConfigValuesMap.get("自定义伤害加成道具代码")).intValue(), false) > 0)) {
                数值 = (long)play.getComStr() * (long)((Integer)Start.ConfigValuesMap.get("自定义力量加成比例")).intValue() + (long)play.getComDex() * (long)((Integer)Start.ConfigValuesMap.get("自定义敏捷加成比例")).intValue() + (long)play.getComInt() * (long)((Integer)Start.ConfigValuesMap.get("自定义智力加成比例")).intValue() + (long)play.getComLuk() * (long)((Integer)Start.ConfigValuesMap.get("自定义运气加成比例")).intValue() + (long)play.getComWatk() * (long)((Integer)Start.ConfigValuesMap.get("自定义物攻加成比例")).intValue() + (long)play.getComMatk() * (long)((Integer)Start.ConfigValuesMap.get("自定义魔攻加成比例")).intValue() + (long)play.getComWdef() * (long)((Integer)Start.ConfigValuesMap.get("自定义物防加成比例")).intValue() + (long)play.getComMdef() * (long)((Integer)Start.ConfigValuesMap.get("自定义魔防加成比例")).intValue() + (long)play.getComHp() * (long)((Integer)Start.ConfigValuesMap.get("自定义血量加成比例")).intValue() + (long)play.getComMp() * (long)((Integer)Start.ConfigValuesMap.get("自定义魔量加成比例")).intValue();
            }
            if (((Integer)Start.ConfigValuesMap.get("扣除21E伤害")).intValue() > 0) {
                数值 -= 2147483647L;
            }
            if (((Integer)Start.ConfigValuesMap.get("自定义伤害气泡显示")).intValue() > 0) {
                play.showInstruction("【#r额外伤害 → " + 数值 + "#k】", 240, 10);
            }
            if (((Integer)Start.ConfigValuesMap.get("自定义伤害黄字喇叭显示")).intValue() > 0) {
                play.dropMessage(-1, "【额外伤害 →" + 数值 + "】");
            }
            if (((Integer)Start.ConfigValuesMap.get("自定义伤害段数显示")).intValue() > 0) {
                while (数值 > 2147483647L) {
                    play.getMap().broadcastMessage(MobPacket.damageMonster(monster.getObjectId(), 2147483647L));
                    数值 -= 2147483647L;
                }
                play.getMap().broadcastMessage(MobPacket.damageMonster(monster.getObjectId(), (long)(int)数值));
            }
        }
        catch (Exception e) {
            FileoutputUtil.outError("logs/额外伤害异常.txt", (Throwable)e);
            return 数值;
        }
        return 数值;
    }
    
    public static int 装备基础破功(final MapleCharacter chr) {
        int 破攻值1 = 0;
        int 装备等级 = 0;
        final IItem 装备 = chr.getInventory(MapleInventoryType.EQUIPPED).getItem((short)(-11));
        if (装备 != null) {
            装备等级 = MapleItemInformationProvider.getInstance().getReqLevel(装备.getItemId());
        }
        if (装备等级 < 120) {
            破攻值1 = 199999;
        }
        else if (装备等级 >= 120 && 装备等级 < 125) {
            破攻值1 = 249999;
        }
        else if (装备等级 >= 125 && 装备等级 < 130) {
            破攻值1 = 299999;
        }
        else if (装备等级 >= 130 && 装备等级 < 135) {
            破攻值1 = 349999;
        }
        else if (装备等级 >= 135 && 装备等级 < 140) {
            破攻值1 = 399999;
        }
        else if (装备等级 >= 140 && 装备等级 < 145) {
            破攻值1 = 449999;
        }
        else if (装备等级 >= 145 && 装备等级 < 150) {
            破攻值1 = 499999;
        }
        else if (装备等级 >= 150 && 装备等级 < 160) {
            破攻值1 = 549999;
        }
        else if (装备等级 >= 160 && 装备等级 < 180) {
            破攻值1 = 599999;
        }
        else if (装备等级 >= 180 && 装备等级 < 200) {
            破攻值1 = 699999;
        }
        else if (装备等级 >= 200 && 装备等级 < 230) {
            破攻值1 = 799999;
        }
        else if (装备等级 >= 230 && 装备等级 <= 255) {
            破攻值1 = 999999;
        }
        return 破攻值1;
    }
    
    public static void readMobRedDam() {
        DamageParse.MobRedDam.clear();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            ps = con.prepareStatement("SELECT * FROM mobreddam");
            rs = ps.executeQuery();
            while (rs.next()) {
                final Integer sn = Integer.valueOf(rs.getInt("mobid"));
                final Integer numb = Integer.valueOf(rs.getInt("numb"));
                DamageParse.MobRedDam.put(sn, numb);
            }
            rs.close();
            ps.close();
        }
        catch (Exception e) {
            FileoutputUtil.outError("logs/减伤读取异常.txt", (Throwable)e);
            DBConPool.cleanUP(rs, ps, con);
            e.printStackTrace();
        }
        finally {
            DBConPool.cleanUP(rs, ps, con);
        }
    }
    
    public static int getMobRedDam(final int mobid) {
        if (DamageParse.MobRedDam.get(Integer.valueOf(mobid)) != null) {
            return ((Integer)DamageParse.MobRedDam.get(Integer.valueOf(mobid))).intValue();
        }
        return 1;
    }
    
    static {
        readMobRedDam();
    }
}
