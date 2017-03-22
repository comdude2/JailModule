package net.comdude2.plugins.jailmodule.main;

import java.io.Serializable;
import java.util.UUID;

public class JailedPlayer implements Serializable{
	
	private static final long serialVersionUID = 4906795534318081826L;
	private UUID playerId = null;
	private long jailedAt = 0L;
	private long jailTime = 0L;
	private long unjailAt = 0L;
	private String reason = null;
	private boolean going_to_jail = true;
	
	public JailedPlayer(UUID playerId, long jailedAt, long jailTime, long unjailAt, String reason){//No time served as this is object creation
		this.playerId = playerId;
		this.jailedAt = jailedAt;
		this.jailTime = jailTime;
		this.unjailAt = unjailAt;
	}
	
	public UUID getPlayerId(){
		return this.playerId;
	}
	
	public long getJailedAt(){
		return this.jailedAt;
	}
	
	public void setJailedAt(long jailedAt){
		this.jailedAt = jailedAt;
	}
	
	public long getJailTime(){
		return this.jailTime;
	}
	
	public void setJailTime(long time){
		this.jailTime = time;
	}
	
	public long getUnjailAt(){
		return this.unjailAt;
	}
	
	public void setUnjailAt(long unjailAt){
		this.unjailAt = unjailAt;
	}
	
	public String getReason(){
		return this.reason;
	}
	
	public boolean isGoingToJail(){
		return this.going_to_jail;
	}
	
	public void inJail(){
		this.going_to_jail = false;
	}
	
}
