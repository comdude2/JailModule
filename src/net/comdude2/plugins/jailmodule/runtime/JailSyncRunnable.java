package net.comdude2.plugins.jailmodule.runtime;

import java.util.Date;
import java.util.Iterator;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.comdude2.plugins.jailmodule.main.JailModule;
import net.comdude2.plugins.jailmodule.main.JailedPlayer;

public class JailSyncRunnable implements Runnable {
	
	private JailModule jm = null;
	
	public JailSyncRunnable(JailModule jm){
		this.jm = jm;
	}
	
	//@Override
	public void run() {
		if (!jm.getJailController().getJailedPlayers().isEmpty()){
			Iterator <JailedPlayer> it = jm.getJailController().getJailedPlayers().iterator();
			while(it.hasNext()){
				JailedPlayer jp = it.next();
				if (System.currentTimeMillis() >= jp.getUnjailAt()){
					//Unjail
					Player p = jm.getServer().getPlayer(jp.getPlayerId());
					if (p != null){
						p.sendMessage(JailModule.me + ChatColor.GREEN + "You have been unjailed.");
					}
					jm.getJailController().unjailPlayer(jp);
					jm.getServer().dispatchCommand(jm.getServer().getConsoleSender(), "spawn " + p.getName());
				}
			}
		}
	}

}
