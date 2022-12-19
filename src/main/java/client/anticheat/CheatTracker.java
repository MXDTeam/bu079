package client.anticheat;

import java.util.Iterator;
import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;
import tools.StringUtil;
import client.MapleCharacterUtil;
import constants.PiPiConfig;
import handling.world.World.Broadcast;
import tools.MaplePacketCreator;
import constants.WorldConstants;
import client.SkillFactory;
import tools.FileoutputUtil;
import gui.Start;
import client.MapleBuffStat;
import constants.GameConstants;
import server.Timer.CheatTimer;
import java.util.LinkedHashMap;
import java.util.concurrent.ScheduledFuture;
import java.awt.Point;
import client.MapleCharacter;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CheatTracker
{
    private final ReentrantReadWriteLock lock;
    private final Lock rL;
    private final Lock wL;
    private final Map<CheatingOffense, CheatingOffenseEntry> offenses;
    private final WeakReference<MapleCharacter> chr;
    private long lastAttackTime;
    private int inMapIimeCount;
    private int lastAttackTickCount;
    private byte Attack_tickResetCount;
    private long Server_ClientAtkTickDiff;
    private long lastDamage;
    private long takingDamageSince;
    private int numSequentialDamage;
    private long lastDamageTakenTime;
    private byte numZeroDamageTaken;
    private int numSequentialSummonAttack;
    private long summonSummonTime;
    private int numSameDamage;
    private Point lastMonsterMove;
    private int monsterMoveCount;
    private int attacksWithoutHit;
    private byte dropsPerSecond;
    private long lastDropTime;
    private byte msgsPerSecond;
    private long lastMsgTime;
    private ScheduledFuture<?> invalidationTask;
    private int gm_message;
    private int lastTickCount;
    private int tickSame;
    private long lastASmegaTime;
    private long[] lastTime;
    private long lastSaveTime;
    private long lastLieDetectorTime;
    private long lastLieTime;
    private long TV = 0;
    public CheatTracker(final MapleCharacter chr) {
        this.lock = new ReentrantReadWriteLock();
        this.rL = this.lock.readLock();
        this.wL = this.lock.writeLock();
        this.offenses = (Map<CheatingOffense, CheatingOffenseEntry>)new LinkedHashMap();
        this.lastAttackTime = 0L;
        this.inMapIimeCount = 0;
        this.lastAttackTickCount = 0;
        this.Attack_tickResetCount = 0;
        this.Server_ClientAtkTickDiff = 0L;
        this.lastDamage = 0L;
        this.numSequentialDamage = 0;
        this.lastDamageTakenTime = 0L;
        this.numZeroDamageTaken = 0;
        this.numSequentialSummonAttack = 0;
        this.summonSummonTime = 0L;
        this.numSameDamage = 0;
        this.attacksWithoutHit = 0;
        this.dropsPerSecond = 0;
        this.lastDropTime = 0L;
        this.msgsPerSecond = 0;
        this.lastMsgTime = 0L;
        this.gm_message = 100;
        this.lastTickCount = 0;
        this.tickSame = 0;
        this.lastASmegaTime = 0L;
        this.lastTime = new long[6];
        this.lastSaveTime = 0L;
        this.lastLieDetectorTime = 0L;
        this.lastLieTime = 0L;
        this.chr = new WeakReference(chr);
        this.invalidationTask = CheatTimer.getInstance().register((Runnable)new InvalidationTask(), 60000L);
        this.takingDamageSince = System.currentTimeMillis();
    }
    
    public final void checkAttack(final int skillId, final int tickcount) {
        short AtkDelay = GameConstants.getAttackDelay(skillId);
        if (((MapleCharacter)this.chr.get()).getBuffedValue(MapleBuffStat.BODY_PRESSURE) != null) {
            AtkDelay /= 6;
        }
        if (((MapleCharacter)this.chr.get()).getBuffedValue(MapleBuffStat.BOOSTER) != null) {
            AtkDelay = (short)(int)((double)AtkDelay / 1.5);
        }
        if (((MapleCharacter)this.chr.get()).getBuffedValue(MapleBuffStat.SPEED_INFUSION) != null) {
            AtkDelay = (short)(int)((double)AtkDelay / 1.35);
        }
        if (GameConstants.isAran((int)((MapleCharacter)this.chr.get()).getJob())) {
            AtkDelay = (short)(int)((double)AtkDelay / 1.4);
        }
        if (((MapleCharacter)this.chr.get()).getJob() >= 500 && ((MapleCharacter)this.chr.get()).getJob() <= 512) {
            AtkDelay = 0;
        }
        if (skillId == 21101003 || skillId == 5110001 || skillId == 1311001) {
            AtkDelay = 0;
        }
        if (tickcount - this.lastAttackTickCount < AtkDelay && GameConstants.getWuYanChi(skillId) && ((Integer)Start.ConfigValuesMap.get("攻速检测")).intValue() > 0) {
            FileoutputUtil.logToFile("logs/Hack/攻击速度异常.txt", "\r\n " + FileoutputUtil.NowTime() + " 玩家：" + ((MapleCharacter)this.chr.get()).getName() + " 职业:" + (int)((MapleCharacter)this.chr.get()).getJob() + "\u3000技能: " + skillId + "(" + SkillFactory.getSkillName(skillId) + ") check: " + (tickcount - this.lastAttackTickCount) + " AtkDelay: " + (int)AtkDelay);
            if (WorldConstants.WUYANCHI) {
                Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM密语]  ID " + ((MapleCharacter)this.chr.get()).getId() + " " + ((MapleCharacter)this.chr.get()).getName() + " 攻击速度异常，技能: " + skillId + "(" + SkillFactory.getSkillName(skillId) + ")"));
            }
        }
        if (((MapleCharacter)this.chr.get()).getDebugMessage()) {
            ((MapleCharacter)this.chr.get()).dropMessage("Delay [" + skillId + "] = " + (tickcount - this.lastAttackTickCount) + ", " + (int)AtkDelay);
        }
        if (WorldConstants.LieDetector) {
            this.lastAttackTime = System.currentTimeMillis();
            if (this.chr.get() != null && this.lastAttackTime - ((MapleCharacter)this.chr.get()).getChangeTime() > 60000L) {
                ((MapleCharacter)this.chr.get()).setChangeTime(false);
                if (!GameConstants.isBossMap(((MapleCharacter)this.chr.get()).getMapId()) && ((MapleCharacter)this.chr.get()).getEventInstance() == null && ((MapleCharacter)this.chr.get()).getMap().getMobsSize() >= 1) {
                    ++this.inMapIimeCount;
                    if (this.inMapIimeCount >= 30) {
                        Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM密语]  ID " + ((MapleCharacter)this.chr.get()).getId() + " " + ((MapleCharacter)this.chr.get()).getName() + " 打怪时间超过 30 分钟，该玩家可能在挂机。 "));
                    }
                    if (this.inMapIimeCount >= 30) {
                        this.inMapIimeCount = 0;
                        ((MapleCharacter)this.chr.get()).startLieDetector(false);
                        Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM密语]  ID " + ((MapleCharacter)this.chr.get()).getId() + " " + ((MapleCharacter)this.chr.get()).getName() + " 打怪时间超过 30 分钟，系统启动测谎仪系统。 "));
                    }
                }
            }
        }
        final long STime_TC = System.currentTimeMillis() - (long)tickcount;
        if (this.Server_ClientAtkTickDiff - STime_TC > 1000L && GameConstants.getWuYanChi(skillId) && ((Integer)Start.ConfigValuesMap.get("攻速检测")).intValue() > 0 && WorldConstants.WUYANCHI) {
            Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM密语]  ID " + ((MapleCharacter)this.chr.get()).getId() + " " + ((MapleCharacter)this.chr.get()).getName() + " 攻击速度异常，技能: " + skillId + "(" + SkillFactory.getSkillName(skillId) + ")"));
        }
        this.Server_ClientAtkTickDiff = STime_TC;
        ((MapleCharacter)this.chr.get()).updateTick(tickcount);
        this.lastAttackTickCount = tickcount;
    }
    
    public final void checkTakeDamage(final int damage) {
        ++this.numSequentialDamage;
        this.lastDamageTakenTime = System.currentTimeMillis();
        if (this.lastDamageTakenTime - this.takingDamageSince / 500L < (long)this.numSequentialDamage) {}
        if (this.lastDamageTakenTime - this.takingDamageSince > 4500L) {
            this.takingDamageSince = this.lastDamageTakenTime;
            this.numSequentialDamage = 0;
        }
        if (damage == 0) {
            ++this.numZeroDamageTaken;
            if (this.numZeroDamageTaken >= 35) {
                this.numZeroDamageTaken = 0;
                this.registerOffense(CheatingOffense.HIGH_AVOID, "回避率过高 ");
            }
        }
        else if (damage != -1) {
            this.numZeroDamageTaken = 0;
        }
    }
    
    public final void checkSameDamage(final int dmg, final double expected) {
        if (dmg > 2000 && this.lastDamage == (long)dmg && this.chr.get() != null && (((MapleCharacter)this.chr.get()).getLevel() < 175 || (double)dmg > expected * 2.0)) {
            ++this.numSameDamage;
            if (this.numSameDamage > 5) {
                this.numSameDamage = 0;
                this.registerOffense(CheatingOffense.SAME_DAMAGE, this.numSameDamage + " 次, 攻击伤害: " + dmg + ", 预计伤害: " + expected + " [等级: " + (int)((MapleCharacter)this.chr.get()).getLevel() + ", 职业: " + (int)((MapleCharacter)this.chr.get()).getJob() + "]");
            }
        }
        else {
            this.lastDamage = (long)dmg;
            this.numSameDamage = 0;
        }
    }
    
    public final void checkMoveMonster(final Point pos) {
        if (pos == this.lastMonsterMove) {
            ++this.monsterMoveCount;
            if (this.monsterMoveCount > 10) {
                this.monsterMoveCount = 0;
            }
        }
        else {
            this.lastMonsterMove = pos;
            this.monsterMoveCount = 1;
        }
    }
    
    public final void resetSummonAttack() {
        this.summonSummonTime = System.currentTimeMillis();
        this.numSequentialSummonAttack = 0;
    }
    
    public final boolean checkSummonAttack() {
        ++this.numSequentialSummonAttack;
        return true;
    }
    
    public final void checkDrop() {
        this.checkDrop(false);
    }
    
    public final void checkDrop(final boolean dc) {
        if (System.currentTimeMillis() - this.lastDropTime < 1000L) {
            ++this.dropsPerSecond;
            if (this.dropsPerSecond >= (dc ? 32 : 16) && this.chr.get() != null && !((MapleCharacter)this.chr.get()).isGM()) {
                if (dc) {
                    ((MapleCharacter)this.chr.get()).getClient().getSession().close();
                }
                else {
                    ((MapleCharacter)this.chr.get()).getClient().setMonitored(true);
                }
            }
        }
        else {
            this.dropsPerSecond = 0;
        }
        this.lastDropTime = System.currentTimeMillis();
    }
    
    public boolean canAvatarSmega2() {
        long time = 10000L;
        if (this.chr.get() != null) {
            if (((MapleCharacter)this.chr.get()).getId() == 845 || ((MapleCharacter)this.chr.get()).getId() == 5247 || ((MapleCharacter)this.chr.get()).getId() == 12048) {
                time = 20000L;
            }
            if (this.lastASmegaTime + time > System.currentTimeMillis() && !((MapleCharacter)this.chr.get()).isGM()) {
                return false;
            }
        }
        this.lastASmegaTime = System.currentTimeMillis();
        return true;
    }
    
    public synchronized boolean GMSpam(final int limit, int type) {
        if (type < 0 || this.lastTime.length < type) {
            type = 1;
        }
        if (System.currentTimeMillis() < (long)limit + this.lastTime[type]) {
            return true;
        }
        this.lastTime[type] = System.currentTimeMillis();
        return false;
    }
    
    public final void checkMsg() {
        if (System.currentTimeMillis() - this.lastMsgTime < 1000L) {
            ++this.msgsPerSecond;
        }
        else {
            this.msgsPerSecond = 0;
        }
        this.lastMsgTime = System.currentTimeMillis();
    }
    
    public final int getAttacksWithoutHit() {
        return this.attacksWithoutHit;
    }
    
    public final void setAttacksWithoutHit(final boolean increase) {
        if (increase) {
            ++this.attacksWithoutHit;
        }
        else {
            this.attacksWithoutHit = 0;
        }
    }
    
    public final void registerOffense(final CheatingOffense offense) {
        this.registerOffense(offense, null);
    }
    
    public final void registerOffense(final CheatingOffense offense, final String param) {
        final MapleCharacter chrhardref = (MapleCharacter)this.chr.get();
        if (chrhardref == null || !offense.isEnabled() || chrhardref.isClone()) {
            return;
        }
        if (((MapleCharacter)this.chr.get()).hasGmLevel(5)) {}
        CheatingOffenseEntry entry = null;
        this.rL.lock();
        try {
            entry = (CheatingOffenseEntry)this.offenses.get(offense);
        }
        finally {
            this.rL.unlock();
        }
        if (entry != null && entry.isExpired()) {
            this.expireEntry(entry);
            entry = null;
        }
        if (entry == null) {
            entry = new CheatingOffenseEntry(offense, chrhardref.getId());
        }
        if (param != null) {
            entry.setParam(param);
        }
        entry.incrementCount();
        if (offense.shouldAutoban(entry.getCount())) {
            final byte type = offense.getBanType();
            if (type != 1) {
                if (type == 2) {
                    final String outputFileName = "断线";
                    Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM密语] " + chrhardref.getName() + " 自动断线 类別: " + offense.toString() + " 原因: " + ((param == null) ? "" : (" - " + param))));
                    FileoutputUtil.logToFile("logs/Hack/" + outputFileName + ".txt", "\r\n " + FileoutputUtil.NowTime() + " 玩家：" + ((MapleCharacter)this.chr.get()).getName() + " 项目：" + offense.toString() + " 原因： " + ((param == null) ? "" : (" - " + param)));
                    chrhardref.getClient().getSession().close();
                }
                else if (type == 3) {
                    boolean ban = true;
                    String outputFileName = "封锁";
                    final String show = "使用违法程式练功";
                    String real = "";
                    if (offense.toString() == "ITEMVAC_SERVER") {
                        outputFileName = "全图吸物";
                        real = "使用全图吸物";
                        if (!PiPiConfig.getAutoban()) {
                            ban = false;
                        }
                    }
                    else if (offense.toString() == "FAST_SUMMON_ATTACK") {
                        outputFileName = "召唤兽无延迟";
                        real = "使用召唤兽无延迟攻击";
                    }
                    else if (offense.toString() == "MOB_VAC") {
                        outputFileName = "吸怪";
                        real = "使用吸怪";
                        if (!PiPiConfig.getAutoban()) {
                            ban = false;
                        }
                    }
                    else if (offense.toString() == "ATTACK_FARAWAY_MONSTER_BAN") {
                        outputFileName = "全图打";
                        real = "使用全图打";
                        if (!PiPiConfig.getAutoban()) {
                            ban = false;
                        }
                    }
                    else {
                        ban = false;
                        Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM密语] " + MapleCharacterUtil.makeMapleReadable(chrhardref.getName()) + " (编号: " + chrhardref.getId() + " )使用外挂! " + StringUtil.makeEnumHumanReadable(offense.name()) + ((param == null) ? "" : (" - " + param))));
                    }
                    if (!((MapleCharacter)this.chr.get()).hasGmLevel(1)) {
                        if (ban) {
                            if (((Integer)Start.ConfigValuesMap.get("全服通告")).intValue() > 0) {
                                FileoutputUtil.logToFile("logs/Hack/Ban/" + outputFileName + ".txt", "\r\n " + FileoutputUtil.NowTime() + " 玩家：" + ((MapleCharacter)this.chr.get()).getName() + " 项目：" + offense.toString() + " 原因： " + ((param == null) ? "" : (" - " + param)));
                                Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[封锁系统] " + chrhardref.getName() + " 因为" + show + "而被管理员永久停权。"));
                                Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM密语] " + chrhardref.getName() + " " + real + "自动封锁! "));
                            }
                            if (((Integer)Start.ConfigValuesMap.get("封停IP")).intValue() > 0) {
                                chrhardref.ban(chrhardref.getName() + real, true, true, false);
                            }
                            if (((Integer)Start.ConfigValuesMap.get("封停MAC")).intValue() > 0) {
                                chrhardref.getClient().banMacs();
                            }
                            if (((Integer)Start.ConfigValuesMap.get("封停账号")).intValue() > 0) {
                                chrhardref.getClient().getSession().close();
                            }
                        }
                    }
                }
            }
            this.gm_message = 100;
            return;
        }
        this.wL.lock();
        try {
            this.offenses.put(offense, entry);
        }
        finally {
            this.wL.unlock();
        }
        CheatingOffensePersister.getInstance().persistEntry(entry);
    }
    
    public void updateTick(final int newTick) {
        if (newTick == this.lastTickCount) {
            ++this.tickSame;
        }
        else {
            this.tickSame = 0;
        }
        this.lastTickCount = newTick;
    }
    
    public final void expireEntry(final CheatingOffenseEntry coe) {
        this.wL.lock();
        try {
            this.offenses.remove(coe.getOffense());
        }
        finally {
            this.wL.unlock();
        }
    }
    
    public final int getPoints() {
        int ret = 0;
        this.rL.lock();
        CheatingOffenseEntry[] offenses_copy;
        try {
            offenses_copy = (CheatingOffenseEntry[])this.offenses.values().toArray(new CheatingOffenseEntry[this.offenses.size()]);
        }
        finally {
            this.rL.unlock();
        }
        for (final CheatingOffenseEntry entry : offenses_copy) {
            if (entry.isExpired()) {
                this.expireEntry(entry);
            }
            else {
                ret += entry.getPoints();
            }
        }
        return ret;
    }
    
    public final Map<CheatingOffense, CheatingOffenseEntry> getOffenses() {
        return Collections.unmodifiableMap(this.offenses);
    }
    
    public final String getSummary() {
        final StringBuilder ret = new StringBuilder();
        final List<CheatingOffenseEntry> offenseList = (List<CheatingOffenseEntry>)new ArrayList();
        this.rL.lock();
        try {
            for (final CheatingOffenseEntry entry : this.offenses.values()) {
                if (!entry.isExpired()) {
                    offenseList.add(entry);
                }
            }
        }
        finally {
            this.rL.unlock();
        }
        Collections.sort(offenseList, new Comparator<CheatingOffenseEntry>() {
            @Override
            public final int compare(final CheatingOffenseEntry o1, final CheatingOffenseEntry o2) {
                final int thisVal = o1.getPoints();
                final int anotherVal = o2.getPoints();
                return (thisVal < anotherVal) ? 1 : ((thisVal == anotherVal) ? 0 : -1);
            }
        });
        for (int to = Math.min(offenseList.size(), 4), x = 0; x < to; ++x) {
            ret.append(StringUtil.makeEnumHumanReadable(((CheatingOffenseEntry)offenseList.get(x)).getOffense().name()));
            ret.append(": ");
            ret.append(((CheatingOffenseEntry)offenseList.get(x)).getCount());
            if (x != to - 1) {
                ret.append(" ");
            }
        }
        return ret.toString();
    }
    
    public final void dispose() {
        if (this.invalidationTask != null) {
            this.invalidationTask.cancel(false);
        }
        this.invalidationTask = null;
    }
    
    public boolean canSaveDB() {
        if (System.currentTimeMillis() - this.lastSaveTime < 300000L) {
            return false;
        }
        this.lastSaveTime = System.currentTimeMillis();
        return true;
    }
    
    public int getlastSaveTime() {
        return (int)((System.currentTimeMillis() - this.lastSaveTime) / 1000L);
    }
    
    public int getlastLieTime() {
        return (int)((System.currentTimeMillis() - this.lastLieTime) / 1000L);
    }
    
    public boolean canLieDetector() {
        if (this.lastLieDetectorTime + 300000L > System.currentTimeMillis() && this.chr.get() != null) {
            return false;
        }
        this.lastLieDetectorTime = System.currentTimeMillis();
        return true;
    }
    
    public void resetInMapIimeCount() {
        this.inMapIimeCount = 0;
    }

    public boolean canTV() {
        if (TV + 60000 > System.currentTimeMillis()) {
            return false;
        }
        TV = System.currentTimeMillis();
        return true;
    }
    
    private final class InvalidationTask implements Runnable
    {
        @Override
        public final void run() {
            CheatTracker.this.rL.lock();
            CheatingOffenseEntry[] offenses_copy;
            try {
                offenses_copy = (CheatingOffenseEntry[])CheatTracker.this.offenses.values().toArray((Object[])new CheatingOffenseEntry[CheatTracker.this.offenses.size()]);
            }
            finally {
                CheatTracker.this.rL.unlock();
            }
            for (final CheatingOffenseEntry offense : offenses_copy) {
                if (offense.isExpired()) {
                    CheatTracker.this.expireEntry(offense);
                }
            }
            if (CheatTracker.this.chr.get() == null) {
                CheatTracker.this.dispose();
            }
        }
    }
}
