package net.comdude2.plugins.jailmodule.main;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class Listeners implements Listener{
	
	private JailModule jm = null;
	
	public Listeners(JailModule jm){
		this.jm = jm;
	}
	
	public void register(){
		jm.getServer().getPluginManager().registerEvents(this, jm);
	}
	
	public void unregister(){
		HandlerList.unregisterAll();
	}
	
	/*
	 * Listeners
	 */
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCommandPreProcess(PlayerCommandPreprocessEvent event){
		if (event.getPlayer().hasPermission("comcore.modules.jail.bypass")){
			//Allow
		}else if (jm.getJailController().isAllowedCommand(event.getMessage())){
			//Allow
		}else{
			event.setCancelled(true);
			event.getPlayer().sendMessage(JailModule.me + ChatColor.RED + "You are in " + JailModule.jail_name + ", you can't perform any commands until you're released.");
		}
	}
	
}
