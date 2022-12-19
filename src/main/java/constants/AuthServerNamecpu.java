package constants;

public enum AuthServerNamecpu {
    benji("115ce8c390577e3ab1938ae7982aa438974a3742"),
    benji1("9fdd05217969866505d69f4526628676b1b2e31a"),
    benji2("221da9844326909d11aafd6d55bff0668a99733f"),
    benji3("8b85ac99038a1382b0031cb0dd0aa9df2101d62a"),
    ;
    
    String mac;
    private AuthServerNamecpu(String mac) {
        this.mac = mac;
    }
    
    public String getMac() {
        return mac;
    }
    
    public static AuthServerNamecpu getName(String mac) {
        for (AuthServerNamecpu n : AuthServerNamecpu.values()) {
            if (n.getMac().equals(mac)) {
                return n;
            }
        }
        return null;
    }
}
