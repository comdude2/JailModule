package net.comdude2.plugins.jailmodule.main;

import java.util.LinkedList;

public class JailController {
	
	private LinkedList <String> allowed_commands = new LinkedList <String> ();
	
	public JailController(){
		load();
	}
	
	public void save(){
		
	}
	
	public void load(){
		
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
