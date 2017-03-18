package net.comdude2.plugins.jailmodule.main;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.bukkit.entity.Player;

import net.comdude2.plugins.minecraftcore.util.ObjectManager;

public class JailController {
	
	private JailModule jm = null;
	private String path = null;
	private LinkedList <String> allowed_commands = new LinkedList <String> ();
	private ConcurrentLinkedQueue <JailedPlayer> jailedPlayers = new ConcurrentLinkedQueue <JailedPlayer> ();
	
	@SuppressWarnings("unchecked")
	public JailController(JailModule jm){
		this.jm = jm;
		
		this.path = jm.getDataFolder() + "/JailedPlayers/";
		File f = new File(path);
		if (!f.exists()){f.mkdir();}
		
		List <String> allowed = (List<String>) jm.getConfig().getList("allowed_commands");
		if (allowed != null){
			LinkedList <String> allwd = new LinkedList <String> ();
			for(String s : allowed){
				allwd.add(s);
			}
			this.allowed_commands = allwd;
		}
	}
	
	public void jailPlayer(JailedPlayer jp){
		this.jailedPlayers.add(jp);
		this.save(jp);
	}
	
	public void save(JailedPlayer jp){
		File f = new File(path + jp.getPlayerId().toString() + ".obj");
		if (f.exists()){f.delete();}
		try {
			ObjectManager.writeObject(f, jp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveAll(){
		for (JailedPlayer jp : this.jailedPlayers){
			save(jp);
		}
	}
	
	public JailedPlayer load(UUID uuid){
		File f = new File(path + uuid.toString() + ".obj");
		if (f.exists()){
			try {
				Object o = ObjectManager.readObject(f).readObject();
				if (o instanceof JailedPlayer){
					JailedPlayer jp = (JailedPlayer) o;
					this.jailedPlayers.add(jp);
					return jp;
				}else{
					jm.getLogger().warning("File '" + f + "' is not a JailedPlayer class / object!");
					return null;
				}
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}
	
	public void loadOnline(){
		for (Player p : jm.getServer().getOnlinePlayers()){
			this.load(p.getUniqueId());
		}
	}
	
	public void unload(JailedPlayer jp){
		this.jailedPlayers.remove(jp);
	}
	
	public void unloadAll(){
		for (JailedPlayer jp : this.jailedPlayers){
			unload(jp);
		}
	}
	
	public ConcurrentLinkedQueue <JailedPlayer> getJailedPlayers(){
		return this.jailedPlayers;
	}
	
	public JailedPlayer getJailedPlayer(UUID uuid){
		for (JailedPlayer jp : this.jailedPlayers){
			if (jp.getPlayerId() == uuid){
				return jp;
			}
		}
		return null;
	}
	
	public boolean isJailedPlayer(UUID uuid){
		JailedPlayer jp = getJailedPlayer(uuid);
		if (jp != null){
			return true;
		}
		return false;
	}
	
	public boolean isAllowedCommand(String command){
		for (String cmd : this.allowed_commands){
			if (command.contains(cmd)){
				return true;
			}
		}
		return false;
	}
	
}
