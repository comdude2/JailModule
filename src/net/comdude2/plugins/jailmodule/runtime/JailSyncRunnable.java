package net.comdude2.plugins.jailmodule.runtime;

import java.util.Iterator;

import net.comdude2.plugins.jailmodule.main.JailModule;
import net.comdude2.plugins.jailmodule.main.JailedPlayer;

public class JailSyncRunnable implements Runnable {
	
	private JailModule jm = null;
	
	public JailSyncRunnable(JailModule jm){
		this.jm = jm;
	}
	
	@Override
	public void run() {
		if (jm.getJailController().getJailedPlayers().size() > 0){
			Iterator <JailedPlayer> it = jm.getJailController().getJailedPlayers().iterator();
			while(it.hasNext()){
				JailedPlayer jp = it.next();
				
			}
		}
	}

}
