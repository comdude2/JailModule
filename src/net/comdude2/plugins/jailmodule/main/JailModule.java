package net.comdude2.plugins.jailmodule.main;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import net.comdude2.plugins.minecraftcore.api.ComCoreModule;
import net.comdude2.plugins.minecraftcore.main.ComCore;

public class JailModule extends JavaPlugin implements ComCoreModule{
	
	public JailModule(){
		
	}
	
	public void onEnable(){
		
	}
	
	public void onDisable(){
		
	}
	
	public ComCore loadComCore() {
		Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
		if (plugin == null || !(plugin instanceof ComCore)) {
	    	return null;
	    }
	    return (ComCore) plugin;
	}
	
	public void reload() {
		
	}
	
	public void shutdown() {
		
	}
	
}
