package client;

import constants.SkillConstants;

public enum MapleJob
{
    新手(0), 
    战士(100), 
    剑客(110), 
    勇士(111), 
    英雄(112), 
    准骑士(120), 
    骑士(121), 
    圣骑士(122), 
    枪战士(130), 
    龙骑士(131), 
    黑骑士(132), 
    魔法师(200), 
    火毒法师(210), 
    火毒巫师(211), 
    火毒魔导士(212), 
    冰雷法师(220), 
    冰雷巫师(221), 
    冰雷魔导士(222), 
    牧师(230), 
    祭司(231), 
    主教(232), 
    弓箭手(300), 
    猎人(310), 
    游俠(311), 
    神射手(312), 
    弩弓手(320), 
    游侠(321), 
    箭神(322), 
    盜贼(400), 
    刺客(410), 
    无影人(411), 
    隐士(412), 
    侠客(420), 
    独行客(421), 
    侠盗(422), 
    海盜(500), 
    拳手(510), 
    斗士(511), 
    冲锋队长(512), 
    火枪手(520), 
    大副(521), 
    船长(522), 
    巡查者(800), 
    管理员(900), 
    初心者(1000), 
    魂骑士1转(1100), 
    魂骑士2转(1110), 
    魂骑士3转(1111), 
    魂骑士4转(1112), 
    炎术士1转(1200), 
    炎术士2转(1210), 
    炎术士3转(1211), 
    炎术士4转(1212), 
    风灵使者1转(1300), 
    风灵使者2转(1310), 
    风灵使者3转(1311), 
    风灵使者4转(1312), 
    夜行者1转(1400), 
    夜行者2转(1410), 
    夜行者3转(1411), 
    夜行者4转(1412), 
    奇袭者1转(1500), 
    奇袭者2转(1510), 
    奇袭者3转(1511), 
    奇袭者4转(1512), 
    战童(2000), 
    战神1转(2100), 
    战神2转(2110), 
    战神3转(2111), 
    战神4转(2112), 
    未知(999999);
    
    private final int jobid;
    
    private MapleJob(final int id) {
        this.jobid = id;
    }
    
    public int getId() {
        return this.jobid;
    }
    
    public static String getName(final MapleJob mjob) {
        return mjob.name();
    }
    
    public static MapleJob getById(final int id) {
        for (final MapleJob l : values()) {
            if (l.getId() == id) {
                return l;
            }
        }
        return MapleJob.未知;
    }
    
    public static boolean isExist(final int id) {
        for (final MapleJob job : values()) {
            if (job.getId() == id) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean is冒险家(final int job) {
        return job / 1000 == 0;
    }
    
    public static boolean is英雄(final int job) {
        return job / 10 == 11;
    }
    
    public static boolean is圣骑士(final int job) {
        return job / 10 == 12;
    }
    
    public static boolean is黑骑士(final int job) {
        return job / 10 == 13;
    }
    
    public static boolean is大魔导士_火毒(final int job) {
        return job / 10 == 21;
    }
    
    public static boolean is大魔导士_冰雷(final int job) {
        return job / 10 == 22;
    }
    
    public static boolean is主教(final int job) {
        return job / 10 == 23;
    }
    
    public static boolean is箭神(final int job) {
        return job / 10 == 31;
    }
    
    public static boolean is神射手(final int job) {
        return job / 10 == 32;
    }
    
    public static boolean is隐士(final int job) {
        return job / 10 == 41;
    }
    
    public static boolean is侠盗(final int job) {
        return job / 10 == 42;
    }
    
    public static boolean is影武者(final int job) {
        return job / 10 == 43;
    }
    
    public static boolean is拳霸(final int job) {
        return job / 10 == 51;
    }
    
    public static boolean is枪神(final int job) {
        return job / 10 == 52;
    }
    
    public static boolean is管理员(final int job) {
        return job == 800 || job == 900 || job == 910;
    }
    
    public static boolean is皇家骑士团(final int job) {
        return job / 1000 == 1;
    }
    
    public static boolean is魂骑士(final int job) {
        return job / 100 == 11;
    }
    
    public static boolean is炎术士(final int job) {
        return job / 100 == 12;
    }
    
    public static boolean is风灵使者(final int job) {
        return job / 100 == 13;
    }
    
    public static boolean is夜行者(final int job) {
        return job / 100 == 14;
    }
    
    public static boolean is奇袭者(final int job) {
        return job / 100 == 15;
    }
    
    public static boolean is英雄团(final int job) {
        return job / 1000 == 2;
    }
    
    public static boolean is战神(final int job) {
        return job / 100 == 21 || job == 2000;
    }
    
    public static boolean is剑士(final int job) {
        return getJobBranch(job) == 1;
    }
    
    public static boolean is魔法师(final int job) {
        return getJobBranch(job) == 2;
    }
    
    public static boolean is弓箭手(final int job) {
        return getJobBranch(job) == 3;
    }
    
    public static boolean is盜贼(final int job) {
        return getJobBranch(job) == 4 || getJobBranch(job) == 6;
    }
    
    public static boolean is海盜(final int job) {
        return getJobBranch(job) == 5 || getJobBranch(job) == 6;
    }
    
    public static short getBeginner(final short job) {
        if (job % 1000 < 10) {
            return job;
        }
        switch (job / 100) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 8:
            case 9: {
                return (short)MapleJob.新手.getId();
            }
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15: {
                return (short)MapleJob.初心者.getId();
            }
            case 20: {
                return (short)MapleJob.战童.getId();
            }
            case 21: {
                return (short)MapleJob.战童.getId();
            }
            default: {
                return (short)MapleJob.新手.getId();
            }
        }
    }
    
    public static boolean is初心者(final int jobid) {
        Label_0084: {
            if (jobid <= 5000) {
                if (jobid != 5000) {
                    if (jobid < 2001) {
                        break Label_0084;
                    }
                    if (jobid > 2005) {
                        if (jobid <= 3000) {
                            break Label_0084;
                        }
                        if (jobid > 3002) {
                            if (jobid <= 4000) {
                                break Label_0084;
                            }
                            if (jobid > 4002) {
                                break Label_0084;
                            }
                        }
                    }
                }
                return true;
            }
            if (jobid >= 6000 && (jobid <= 6001 || jobid == 13000)) {
                return true;
            }
        }
        boolean result = isJob12000(jobid);
        if (jobid % 1000 == 0 || jobid / 100 == 8000 || jobid == 8001 || result) {
            result = true;
        }
        return result;
    }
    
    public static boolean isJob12000(final int job) {
        boolean result = isJob12000HighLv(job);
        if (isJob12000LowLv(job) || result) {
            result = true;
        }
        return result;
    }
    
    public static boolean isJob12000HighLv(final int job) {
        return job == 12003 || job == 12004;
    }
    
    public static boolean isJob12000LowLv(final int job) {
        return job == 12000 || job == 12001 || job == 12002;
    }
    
    public static boolean isJob8000(final int job) {
        final int v1 = SkillConstants.getJobBySkill(job);
        return (v1 >= 800000 && v1 <= 800099) || v1 == 8001;
    }
    
    public static boolean isJob9500(final int job) {
        final boolean result = job >= 0 && SkillConstants.getJobBySkill(job) == 9500;
        return result;
    }
    
    public static int get转数(final int jobid) {
        int result;
        if (is初心者(jobid) || jobid % 100 == 0 || jobid == 501 || jobid == 3101 || jobid == 508) {
            result = 1;
        }
        else {
            final int v1 = jobid % 10;
            if (jobid / 10 == 43) {
                final int v2 = v1 / 2 + 2;
            }
            else {
                final int v2 = v1 + 2;
            }
            result = 0;
        }
        return result;
    }
    
    public static boolean isBeginner(final int job) {
        return getJobGrade(job) == 0;
    }
    
    public static boolean isSameJob(final int job, final int job2) {
        final int jobNum = getJobGrade(job);
        final int job2Num = getJobGrade(job2);
        if (jobNum == 0 || job2Num == 0) {
            return getBeginner((short)job) == getBeginner((short)job2);
        }
        if (getJobGroup(job) != getJobGroup(job2)) {
            return false;
        }
        if (is管理员(job) || is管理员(job)) {
            return is管理员(job2) && is管理员(job2);
        }
        if (jobNum == 1 || job2Num == 1) {
            return job / 100 == job2 / 100;
        }
        return job / 10 == job2 / 10;
    }
    
    public static int getJobGroup(final int job) {
        return job / 1000;
    }
    
    public static int getJobBranch(final int job) {
        if (job / 100 == 27) {
            return 2;
        }
        return job % 1000 / 100;
    }
    
    public static int getJobBranch2nd(final int job) {
        if (job / 100 == 27) {
            return 2;
        }
        return job % 1000 / 100;
    }
    
    public static int getJobGrade(final int jobz) {
        final int job = jobz % 1000;
        if (job / 10 == 0) {
            return 0;
        }
        if (job / 10 % 10 == 0) {
            return 1;
        }
        return job % 10 + 2;
    }
}
