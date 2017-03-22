package net.comdude2.plugins.jailmodule.main;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import net.comdude2.plugins.jailmodule.commands.JailCommand;
import net.comdude2.plugins.jailmodule.commands.UnjailCommand;
import net.comdude2.plugins.jailmodule.runtime.JailSyncRunnable;
import net.comdude2.plugins.minecraftcore.api.ComCoreModule;
import net.comdude2.plugins.minecraftcore.main.ComCore;
import net.md_5.bungee.api.ChatColor;

public class JailModule extends JavaPlugin implements ComCoreModule{
	
	public static final String me = ChatColor.WHITE + "[" + ChatColor.DARK_AQUA + "Jail" + ChatColor.WHITE + "] ";
	public static String jail_name = "Jail";
	
	private JailController jc = null;
	private Listeners listeners = null;
	private ComCore cc = null;
	
	public String jail_world = null;
	public int jail_x = 0;
	public int jail_y = 0;
	public int jail_z = 0;
	
	public JailModule(){
		
	}
	
	public void onEnable(){
		this.saveDefaultConfig();
		String name = this.getConfig().getString("jail_name");
		if (name != null){JailModule.jail_name = name;}
		
		//API
		ComCore cc = this.loadComCore();
		if (cc == null){this.getLogger().severe("Failed to load ComCore plugin, disabling myself.");this.getServer().getPluginManager().disablePlugin(this);return;}
		//this.registerModule();
		
		//Commands
		this.getCommand("jail").setExecutor(new JailCommand(this));
		this.getCommand("unjail").setExecutor(new UnjailCommand(this));
		
		//Normal loading
		this.jc = new JailController(this);
		this.listeners = new Listeners(this);
		this.listeners.register();
		
		//Runnables
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new JailSyncRunnable(this), 60L, 20L);
		this.getLogger().info(ChatColor.stripColor(me) + "version " + this.getDescription().getVersion() + " enabled!");
	}
	
	public void onDisable(){
		this.listeners.unregister();
		this.getServer().getScheduler().cancelTasks(this);
		this.jc.saveAll();
		this.jc.unloadAll();
		this.getLogger().info(ChatColor.stripColor(me) + "version " + this.getDescription().getVersion() + " disabled!");
	}
	
	public ComCore loadComCore() {
		Plugin plugin = getServer().getPluginManager().getPlugin("ComCore");
		if (plugin == null || !(plugin instanceof ComCore)) {
	    	return null;
	    }
	    return (ComCore) plugin;
	}
	
	public boolean registerModule() {
		if (cc != null){
			cc.test(this);
		}
		return false;
	}

	public void unregisterModule() {
		
	}
	
	public void reload() {
		this.listeners.unregister();
		this.getServer().getScheduler().cancelTasks(this);
		this.jc.saveAll();
		this.jc.unloadAll();
		this.jc.loadOnline();
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new JailSyncRunnable(this), 60L, 20L);
		this.listeners.register();
	}
	
	public void shutdown() {
		
	}
	
	/*
	 * Get and Set
	 */
	
	public JailController getJailController(){
		return this.jc;
	}
	
	/*
	 * Static Methods
	 */
	
	public static int toSeconds(long l){
		return (int)(l / 1000);
	}
	
	public static boolean isNumeric(String s){
		try{Integer.parseInt(s);return true;}catch(Exception e){return false;}
	}
	
}
