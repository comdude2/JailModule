package net.comdude2.plugins.jailmodule.main;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import net.comdude2.plugins.minecraftcore.api.ComCoreModule;
import net.comdude2.plugins.minecraftcore.main.ComCore;
import net.md_5.bungee.api.ChatColor;

public class JailModule extends JavaPlugin implements ComCoreModule{
	
	public static final String me = ChatColor.WHITE + "[" + ChatColor.DARK_AQUA + "Jail" + ChatColor.WHITE + "] ";
	public static final String jail_name = "Jail";
	
	public JailController jc = null;
	
	public JailModule(){
		
	}
	
	public void onEnable(){
		this.jc = new JailController();
	}
	
	public void onDisable(){
		
	}
	
	public ComCore loadComCore() {
		Plugin plugin = getServer().getPluginManager().getPlugin("ComCore");
		if (plugin == null || !(plugin instanceof ComCore)) {
	    	return null;
	    }
	    return (ComCore) plugin;
	}
	
	public void reload() {
		
	}
	
	public void shutdown() {
		
	}
	
	/*
	 * Get and Set
	 */
	
	public JailController getJailController(){
		return this.jc;
	}
	
}
