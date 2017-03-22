package net.comdude2.plugins.jailmodule.commands;

import java.util.Date;
import java.util.UUID;

import net.comdude2.plugins.jailmodule.main.JailModule;
import net.comdude2.plugins.jailmodule.main.JailedPlayer;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class JailCommand implements CommandExecutor {
	
	private JailModule jm = null;
	
	public JailCommand(JailModule jm){
		this.jm = jm;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("comcore.modules.jail.jail")){
			if (args.length > 0){
				if (args.length == 1){
					
				}else if (args.length == 2){
					
				}else if (args.length == 3){
					//   /jail <player> <time> <reason>
					
					long jailTime = 0L;
					if (JailModule.isNumeric(args[1])){
						int i = Integer.parseInt(args[1]);
						jailTime = (i * 60L) * 20L;
					}else{
						red(sender, "Time must be a number."); return true;
					}
					if (jailTime == 0L){red(sender, "Time must be a number."); return true;}
					
					Player online = null;
					UUID uuid = null;
					for (Player p : jm.getServer().getOnlinePlayers()){
						if (p.getName().equalsIgnoreCase(args[0])){
							if (p.hasPermission("comcore.modules.jail.bypass")){
								red(sender, "You can't " + JailModule.jail_name + " this person.");
								return true;
							}else{
								online = p;
								uuid = p.getUniqueId();
								break;
							}
						}
					}
					if (uuid == null){
						for (OfflinePlayer op : jm.getServer().getOfflinePlayers()){
							if (op.getName().equalsIgnoreCase(args[0])){
								uuid = op.getUniqueId();
								break;
							}
						}
					}
					if (uuid == null){red(sender, "Player not found."); return true;}
					
					//Jail player
					if (jm.getJailController().isJailedPlayer(uuid)){red(sender, "This player is already in " + JailModule.jail_name);}
					Date d = new Date();
					JailedPlayer jp = new JailedPlayer(uuid, d.getTime(), jailTime, d.getTime() + jailTime, args[2]);
					jm.getJailController().jailPlayer(jp);
					if (online != null){
						World w = jm.getServer().getWorld(jm.jail_world);
						if (w != null){online.teleport(new Location(w, jm.jail_x, jm.jail_y, jm.jail_z), TeleportCause.PLUGIN);}else{
							jm.getLogger().warning("Failed to teleport " + args[0] + " to " + JailModule.jail_name + "!");
						}
					}
					sender.sendMessage(JailModule.me + ChatColor.GREEN + "Jailed " + args[0] + " for " + args[1] + " minutes for: " + args[2]);
				}else{
					unknown(sender);
				}
			}else{
				red(sender, "Usage: /jail <args>");
			}
		}else{
			permissions(sender);
		}
		return true;
	}
	
	/*
	 * Messages
	 */
	
	private void permissions(CommandSender sender){
		sender.sendMessage(JailModule.me + ChatColor.RED + "You don't have permission to do this.");
	}
	
	private void red(CommandSender sender, String msg){
		sender.sendMessage(JailModule.me + ChatColor.RED + msg);
	}
	
	private void unknown(CommandSender sender){
		red(sender, "Unknown args.");
	}

}
