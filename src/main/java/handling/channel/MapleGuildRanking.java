package handling.channel;

import tools.FileoutputUtil;
import java.util.Iterator;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import database.DBConPool;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;

public class MapleGuildRanking
{
    private static final MapleGuildRanking instance = new MapleGuildRanking();
    private final List<GuildRankingInfo> ranks;
    private final List<levelRankingInfo> ranks1;
    private final List<mesoRankingInfo> ranks2;
    private final Map<String, List<bossLogRankingInfo>> bossLogRanks;
    private final Map<Integer, List<JobRankingInfo>> JobRanks;
    
    public static MapleGuildRanking getInstance() {
        return MapleGuildRanking.instance;
    }
    
    public MapleGuildRanking() {
        this.ranks = (List<GuildRankingInfo>)new LinkedList();
        this.ranks1 = (List<levelRankingInfo>)new LinkedList();
        this.ranks2 = (List<mesoRankingInfo>)new LinkedList();
        this.JobRanks = (Map<Integer, List<JobRankingInfo>>)new HashMap();
        this.bossLogRanks = (Map<String, List<bossLogRankingInfo>>)new LinkedHashMap();
    }
    
    private void reloadBossLogRank(final String bossName, final boolean clear) {
        if (bossName == null && clear) {
            this.bossLogRanks.clear();
            return;
        }
        try {
            final Connection con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            try (final PreparedStatement ps = con.prepareStatement("SELECT DISTINCT bosslog.log, bosslog.sz1, characters.`name` FROM bosslog LEFT JOIN characters ON bosslog.id = characters.id WHERE bosslog.log = ? ORDER BY bosslog.sz1 DESC LIMIT 100")) {
                ps.setString(1, bossName);
                final ResultSet rs = ps.executeQuery();
                final List<bossLogRankingInfo> rank = (List<bossLogRankingInfo>)new LinkedList();
                while (rs.next()) {
                    final bossLogRankingInfo info = new bossLogRankingInfo(rs.getString("log"), rs.getString("name"), rs.getInt("sz1"));
                    if (info.getName() != null) {
                        rank.add(info);
                    }
                }
                this.bossLogRanks.put(bossName, rank);
                rs.close();
                ps.close();
                con.close();
            }
        }
        catch (SQLException e) {
            System.err.println("人物排行错误" + e);
        }
    }
    
    public List<bossLogRankingInfo> getBossLogRank(final String bossName, final int world) {
        if (this.bossLogRanks.isEmpty() || this.bossLogRanks.get(bossName) == null) {
            this.reloadBossLogRank(bossName, false);
        }
        final List<bossLogRankingInfo> ranks = (List<bossLogRankingInfo>)new LinkedList();
        for (final bossLogRankingInfo rank : this.bossLogRanks.get(bossName)) {
            ranks.add(rank);
        }
        return ranks;
    }
    
    public List<JobRankingInfo> getJobRank(final int type) {
        if (this.JobRanks.get(Integer.valueOf(type)) == null || (this.JobRanks.get(Integer.valueOf(type))).isEmpty()) {
            this.loadJobRank(type);
        }
        return this.JobRanks.get(Integer.valueOf(type));
    }
    
    public List<GuildRankingInfo> getGuildRank() {
        if (this.ranks.isEmpty()) {
            this.reload();
        }
        return this.ranks;
    }
    
    public List<levelRankingInfo> getLevelRank() {
        if (this.ranks1.isEmpty()) {
            this.showLevelRank();
        }
        return this.ranks1;
    }
    
    public List<mesoRankingInfo> getMesoRank() {
        if (this.ranks2.isEmpty()) {
            this.showMesoRank();
        }
        return this.ranks2;
    }
    
    private void reload() {
        this.ranks.clear();
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            ps = con.prepareStatement("SELECT * FROM guilds ORDER BY `GP` DESC LIMIT 50");
            rs = ps.executeQuery();
            while (rs.next()) {
                final GuildRankingInfo rank = new GuildRankingInfo(rs.getString("name"), rs.getInt("GP"), rs.getInt("logo"), rs.getInt("logoColor"), rs.getInt("logoBG"), rs.getInt("logoBGColor"));
                this.ranks.add(rank);
            }
            rs.close();
            ps.close();
            con.close();
        }
        catch (SQLException e) {
            System.err.println("Error handling guildRanking");
            FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)e);
        }
        finally {
            DBConPool.cleanUP(rs, ps, con);
        }
    }
    
    private void showLevelRank() {
        this.ranks1.clear();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            ps = con.prepareStatement("SELECT * FROM characters WHERE gm < 1 ORDER BY `level` DESC LIMIT 100");
            rs = ps.executeQuery();
            while (rs.next()) {
                final levelRankingInfo rank1 = new levelRankingInfo(rs.getString("name"), rs.getInt("level"), rs.getInt("str"), rs.getInt("dex"), rs.getInt("int"), rs.getInt("luk"));
                this.ranks1.add(rank1);
            }
            rs.close();
            ps.close();
            con.close();
        }
        catch (SQLException e) {
            System.err.println("未能显示等级排行");
            FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)e);
        }
        finally {
            DBConPool.cleanUP(rs, ps, con);
        }
    }
    
    private void showMesoRank() {
        this.ranks2.clear();
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            ps = con.prepareStatement("SELECT *, ( chr.meso + s.meso ) as money FROM `characters` as chr , `storages` as s WHERE chr.gm < 1  AND s.accountid = chr.accountid ORDER BY money DESC LIMIT 20");
            rs = ps.executeQuery();
            while (rs.next()) {
                final mesoRankingInfo rank2 = new mesoRankingInfo(rs.getString("name"), rs.getLong("money"), rs.getInt("str"), rs.getInt("dex"), rs.getInt("int"), rs.getInt("luk"));
                this.ranks2.add(rank2);
            }
            rs.close();
            ps.close();
            con.close();
        }
        catch (SQLException e) {
            System.err.println("未能显示财产排行");
            FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)e);
        }
        finally {
            DBConPool.cleanUP(rs, ps, con);
        }
    }
    
    private void loadJobRank(final int type) {
        if (this.JobRanks.get(Integer.valueOf(type)) != null) {
            (this.JobRanks.get(Integer.valueOf(type))).clear();
        }
        String jobRange = "";
        if (type == 1) {
            jobRange = "and job >= '100' and job <= '132'";
        }
        else if (type == 2) {
            jobRange = "and job >= '200' and job <= '232'";
        }
        else if (type == 3) {
            jobRange = "and job >= '300' and job <= '322'";
        }
        else if (type == 4) {
            jobRange = "and job >= '400' and job <= '422'";
        }
        else if (type == 5) {
            jobRange = "and job >= '500' and job <= '522'";
        }
        else if (type == 6) {
            jobRange = "and job >= '2000' and job <= '2112'";
        }
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = (Connection)DBConPool.getInstance().getDataSource().getConnection();
            ps = con.prepareStatement("SELECT * FROM characters WHERE gm = 0 " + jobRange + " and accountid in (select id from accounts where banned= '0') ORDER BY `level` DESC LIMIT 10");
            rs = ps.executeQuery();
            final LinkedList<JobRankingInfo> JobRankList = (LinkedList<JobRankingInfo>)new LinkedList();
            while (rs.next()) {
                final JobRankingInfo JobRank = new JobRankingInfo(rs.getString("name"), rs.getInt("level"), rs.getInt("job"), rs.getInt("str"), rs.getInt("dex"), rs.getInt("int"), rs.getInt("luk"));
                JobRankList.add(JobRank);
            }
            this.JobRanks.put(Integer.valueOf(type), JobRankList);
            rs.close();
            ps.close();
            con.close();
        }
        catch (SQLException e) {
            FileoutputUtil.outError("logs/资料库异常.txt", (Throwable)e);
            System.err.println("未能显示职业" + type + "排行");
        }
        finally {
            DBConPool.cleanUP(rs, ps, con);
        }
    }
    
    public static class mesoRankingInfo{
        private final String name;
        private final long meso;
        private final int str;
        private final int dex;
        private final int _int;
        private final int luk;
        
        public mesoRankingInfo(final String name, final long meso, final int str, final int dex, final int intt, final int luk) {
            this.name = name;
            this.meso = meso;
            this.str = str;
            this.dex = dex;
            this._int = intt;
            this.luk = luk;
        }
        
        public String getName() {
            return this.name;
        }
        
        public long getMeso() {
            return this.meso;
        }
        
        public int getStr() {
            return this.str;
        }
        
        public int getDex() {
            return this.dex;
        }
        
        public int getInt() {
            return this._int;
        }
        
        public int getLuk() {
            return this.luk;
        }
    }
    
    public static class JobRankingInfo
    {
        private final String name;
        private final int level;
        private final int str;
        private final int dex;
        private final int _int;
        private final int luk;
        private final int job;
        
        public JobRankingInfo(final String name, final int level, final int job, final int str, final int dex, final int intt, final int luk) {
            this.name = name;
            this.level = level;
            this.job = job;
            this.str = str;
            this.dex = dex;
            this._int = intt;
            this.luk = luk;
        }
        
        public String getName() {
            return this.name;
        }
        
        public int getLevel() {
            return this.level;
        }
        
        public int getStr() {
            return this.str;
        }
        
        public int getDex() {
            return this.dex;
        }
        
        public int getInt() {
            return this._int;
        }
        
        public int getLuk() {
            return this.luk;
        }
        
        public int getJob() {
            return this.job;
        }
    }
    
    public static class levelRankingInfo
    {
        private final String name;
        private final int level;
        private final int str;
        private final int dex;
        private final int _int;
        private final int luk;
        
        public levelRankingInfo(final String name, final int level, final int str, final int dex, final int intt, final int luk) {
            this.name = name;
            this.level = level;
            this.str = str;
            this.dex = dex;
            this._int = intt;
            this.luk = luk;
        }
        
        public String getName() {
            return this.name;
        }
        
        public int getLevel() {
            return this.level;
        }
        
        public int getStr() {
            return this.str;
        }
        
        public int getDex() {
            return this.dex;
        }
        
        public int getInt() {
            return this._int;
        }
        
        public int getLuk() {
            return this.luk;
        }
    }
    
    public static class GuildRankingInfo
    {
        private final String name;
        private final int gp;
        private final int logo;
        private final int logocolor;
        private final int logobg;
        private final int logobgcolor;
        
        public GuildRankingInfo(final String name, final int gp, final int logo, final int logocolor, final int logobg, final int logobgcolor) {
            this.name = name;
            this.gp = gp;
            this.logo = logo;
            this.logocolor = logocolor;
            this.logobg = logobg;
            this.logobgcolor = logobgcolor;
        }
        
        public String getName() {
            return this.name;
        }
        
        public int getGP() {
            return this.gp;
        }
        
        public int getLogo() {
            return this.logo;
        }
        
        public int getLogoColor() {
            return this.logocolor;
        }
        
        public int getLogoBg() {
            return this.logobg;
        }
        
        public int getLogoBgColor() {
            return this.logobgcolor;
        }
    }
    
    public static class bossLogRankingInfo
    {
        private final String name;
        private final String bossName;
        private final int count;
        
        public bossLogRankingInfo(final String bossName, final String name, final int count) {
            this.bossName = bossName;
            this.name = name;
            this.count = count;
        }
        
        public String getBossName() {
            return this.bossName;
        }
        
        public String getName() {
            return this.name;
        }
        
        public int getCount() {
            return this.count;
        }
    }
}
