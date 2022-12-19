package constants;

import server.ServerProperties;

public class JobConstants
{
    public static final boolean enableJobs = true;
    public static final int jobOrder = 184;
    
    public enum LoginJob
    {
        冒险家(0), 
        皇家骑士团(1), 
        战神(2);
        
        private final int jobType;
        private final boolean enableCreate = true;
        
        private LoginJob(final int jobType) {
            this.jobType = jobType;
        }
        
        public int getJobType() {
            return this.jobType;
        }
        
        public boolean enableCreate() {
            return Boolean.valueOf(ServerProperties.getProperty("JobEnableCreate" + this.jobType, String.valueOf(true))).booleanValue();
        }
        
        public void setEnableCreate(final boolean info) {
            if (info) {
                ServerProperties.removeProperty("JobEnableCreate" + this.jobType);
                return;
            }
            ServerProperties.setProperty("JobEnableCreate" + this.jobType, String.valueOf(info));
        }
    }
}
