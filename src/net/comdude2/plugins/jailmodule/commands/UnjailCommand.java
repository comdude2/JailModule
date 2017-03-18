package net.comdude2.plugins.jailmodule.commands;

import net.comdude2.plugins.jailmodule.main.JailModule;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UnjailCommand implements CommandExecutor {
	
	private JailModule jm = null;
	
	public UnjailCommand(JailModule jm){
		this.jm = jm;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		return false;
	}

}
