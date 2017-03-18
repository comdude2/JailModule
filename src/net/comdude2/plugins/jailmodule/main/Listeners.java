package net.comdude2.plugins.jailmodule.main;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

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
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
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
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerTeleport(PlayerTeleportEvent event){
		if (jm.getJailController().isJailedPlayer(event.getPlayer().getUniqueId())){
			event.setCancelled(true);
			event.getPlayer().sendMessage(JailModule.me + ChatColor.RED + "You're still shackled in " + JailModule.jail_name + ", you can't teleport!");
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoinEvent(PlayerJoinEvent event){
		JailedPlayer jp = jm.getJailController().load(event.getPlayer().getUniqueId());
		if (jp != null){
			if (jp.isGoingToJail()){
				event.getPlayer().sendMessage(JailModule.me + ChatColor.RED + "You are in " + JailModule.jail_name + " for " + ChatColor.YELLOW + JailModule.toSeconds(jp.getJailTime() - jp.getTimeServed()) + ChatColor.RED + " seconds.");
			}else{
				event.getPlayer().sendMessage(JailModule.me + ChatColor.RED + "You are still in " + JailModule.jail_name + " for another " + ChatColor.YELLOW + JailModule.toSeconds(jp.getJailTime() - jp.getTimeServed()) + ChatColor.RED + " seconds.");
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuitEvent(PlayerQuitEvent event){
		JailedPlayer jp = jm.getJailController().getJailedPlayer(event.getPlayer().getUniqueId());
		if (jp != null){
			jm.getJailController().save(jp);
			jm.getJailController().unload(jp);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerKickEvent(PlayerKickEvent event){
		JailedPlayer jp = jm.getJailController().getJailedPlayer(event.getPlayer().getUniqueId());
		if (jp != null){
			jm.getJailController().save(jp);
			jm.getJailController().unload(jp);
		}
	}
	
}
