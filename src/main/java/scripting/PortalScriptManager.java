package scripting;

import javax.script.ScriptEngineManager;
import client.MapleClient;
import server.MaplePortal;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import java.io.InputStream;
import javax.script.Invocable;
import java.io.IOException;
import javax.script.ScriptException;
import tools.FilePrinter;
import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;
import javax.script.Compilable;
import java.util.stream.Collectors;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import tools.EncodingDetect;
import java.io.FileInputStream;
import java.io.File;
import java.util.HashMap;
import javax.script.ScriptEngineFactory;
import java.util.Map;

public class PortalScriptManager{
    private static final PortalScriptManager instance = new PortalScriptManager();
    private final Map<String, PortalScript> scripts;
    private static final ScriptEngineFactory sef = new ScriptEngineManager().getEngineByName("nashorn").getFactory();
    
    public PortalScriptManager() {
        this.scripts = (Map<String, PortalScript>)new HashMap();
    }
    
    public static final PortalScriptManager getInstance() {
        return PortalScriptManager.instance;
    }
    
    private final PortalScript getPortalScript(final String scriptName) {
        if (this.scripts.containsKey(scriptName)) {
            return (PortalScript)this.scripts.get(scriptName);
        }
        final File scriptFile = new File("scripts/portal/" + scriptName + ".js");
        if (!scriptFile.exists()) {
            this.scripts.put(scriptName, null);
            return null;
        }
        InputStream in = null;
        final ScriptEngine portal = PortalScriptManager.sef.getScriptEngine();
        try {
            in = new FileInputStream(scriptFile);
            final BufferedReader bf = new BufferedReader((Reader)new InputStreamReader(in, EncodingDetect.getJavaEncode(scriptFile)));
            final String lines = "load('nashorn:mozilla_compat.js');" + (String)bf.lines().collect(Collectors.joining((CharSequence)System.lineSeparator()));
            final CompiledScript compiled = ((Compilable)portal).compile(lines);
            compiled.eval();
        }
        catch (FileNotFoundException ex) {}
        catch (UnsupportedEncodingException ex2) {}
        catch (ScriptException e) {
            System.err.println("Error executing Portalscript: " + scriptName + ":" + e);
            FilePrinter.printError("PortalScriptManager.txt", (Throwable)e);
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            }
            catch (IOException ex3) {}
        }
        final PortalScript script = (PortalScript)((Invocable)portal).getInterface(PortalScript.class);
        this.scripts.put(scriptName, script);
        return script;
    }
    
    public final void executePortalScript(final MaplePortal portal, final MapleClient c) {
        final PortalScript script = this.getPortalScript(portal.getScriptName());
        if (c != null && c.getPlayer() != null && c.getPlayer().hasGmLevel(2)) {
            c.getPlayer().dropMessage("您已经建立与传送门脚本: " + portal.getScriptName() + ".js 的关联。");
        }
        if (script != null) {
            try {
                script.enter(new PortalPlayerInteraction(c, portal));
            }
            catch (Exception e) {
                System.err.println("进入传送脚本失败: " + portal.getScriptName() + ":" + e);
            }
        }
        this.clearScripts();
    }
    
    public final void clearScripts() {
        this.scripts.clear();
    }
}
