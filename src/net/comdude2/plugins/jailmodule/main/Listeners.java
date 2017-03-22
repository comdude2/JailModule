package net.comdude2.plugins.jailmodule.main;

import java.util.Date;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
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
		jm.getLogger().info("DEBUG: " + jm.getJailController().getJailedPlayers().size());
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
		JailedPlayer jp = jm.getJailController().load(event.getPlayer().getUniqueId());
		if (jp != null){
			if (jp.isGoingToJail()){
				//Allow
			}else{
				event.setCancelled(true);
				event.getPlayer().sendMessage(JailModule.me + ChatColor.RED + "You're still shackled in " + JailModule.jail_name + ", you can't teleport!");
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoinEvent(PlayerJoinEvent event){
		JailedPlayer jp = jm.getJailController().load(event.getPlayer().getUniqueId());
		if (jp != null){
			Date d = new Date();
			long now = d.getTime();
			jp.setJailedAt(now);
			jp.setUnjailAt(now + jp.getJailTime());
			if (jp.isGoingToJail()){
				event.getPlayer().sendMessage(JailModule.me + ChatColor.RED + "You are in " + JailModule.jail_name + " for " + ChatColor.YELLOW + JailModule.toSeconds(jp.getJailTime()) + ChatColor.RED + " seconds.");
			}else{
				event.getPlayer().sendMessage(JailModule.me + ChatColor.RED + "You are still in " + JailModule.jail_name + " for another " + ChatColor.YELLOW + JailModule.toSeconds(jp.getJailTime()) + ChatColor.RED + " seconds.");
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuitEvent(PlayerQuitEvent event){
		JailedPlayer jp = jm.getJailController().getJailedPlayer(event.getPlayer().getUniqueId());
		if (jp != null){
			Date d = new Date();
			long now = d.getTime();
			if (jp.getUnjailAt() < now){
				//Still jailed - update times
				long difference = now - jp.getJailedAt();
				jp.setJailTime(jp.getJailTime() - difference);
				//Save and unload
				jm.getJailController().save(jp);
				jm.getJailController().unload(jp);
			}else{
				jm.getJailController().unjailPlayer(jp);
			}
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
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDeathEvent(PlayerDeathEvent event){
		JailedPlayer jp = jm.getJailController().getJailedPlayer(event.getEntity().getUniqueId());
		if (jp != null){
			event.setKeepInventory(true);
			event.setKeepLevel(true);
			event.setDeathMessage("You have somehow died in " + JailModule.jail_name + ", don't worry your items have been saved.");
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerRespawnEvent(PlayerRespawnEvent event){
		JailedPlayer jp = jm.getJailController().getJailedPlayer(event.getPlayer().getUniqueId());
		if (jp != null){
			World w = jm.getServer().getWorld(jm.jail_world);
			if (w != null){
				event.setRespawnLocation(new Location(w, jm.jail_x, jm.jail_y, jm.jail_z));
			}else{
				jm.getLogger().warning("Failed to teleport player to jail as couldn't find world '" + jm.jail_world + "'.");
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerFoodChange(FoodLevelChangeEvent event){
		JailedPlayer jp = jm.getJailController().getJailedPlayer(event.getEntity().getUniqueId());
		if (jp != null){
			event.setCancelled(true);
		}
	}
	
}
