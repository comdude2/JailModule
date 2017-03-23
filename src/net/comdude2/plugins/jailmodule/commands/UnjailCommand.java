package net.comdude2.plugins.jailmodule.commands;

import java.io.File;

import net.comdude2.plugins.jailmodule.main.JailModule;
import net.comdude2.plugins.jailmodule.main.JailedPlayer;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnjailCommand implements CommandExecutor {
	
	private JailModule jm = null;
	
	public UnjailCommand(JailModule jm){
		this.jm = jm;
	}
	
	//@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		//  /unjail <player> <reason>
		if (sender.hasPermission("comcore.modules.jail.unjail")){
			if (args.length > 0){
				if (args.length == 2){
					Player online = null;
					for (Player p : jm.getServer().getOnlinePlayers()){
						if (p.getName().equalsIgnoreCase(args[0])){
							online = p;
							break;
						}
					}
					if (online == null){red(sender, "Player not found (To unjail a player, they must be online)."); return true;}
					jm.getLogger().info("DEBUG: " + jm.getJailController().getJailedPlayers().size());
					JailedPlayer jp = jm.getJailController().getJailedPlayer(online.getUniqueId());
					if (jp == null){red(sender, "This player isn't in " + JailModule.jail_name);return true;}
					jm.getJailController().unjailPlayer(jp);
					File f = new File(jm.getDataFolder() + "/JailedPlayers/" + online.getUniqueId().toString() + ".obj");
					if (f.exists()){f.delete();}
					jm.getServer().dispatchCommand(jm.getServer().getConsoleSender(), "spawn " + online.getName());
					sender.sendMessage(JailModule.me + ChatColor.GREEN + "Player unjailed.");
				}else{
					unknown(sender);
				}
			}else{
				red(sender, "Usage: /unjail <player> <reason>");
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
