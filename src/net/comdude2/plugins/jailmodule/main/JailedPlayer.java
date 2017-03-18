package net.comdude2.plugins.jailmodule.main;

import java.util.UUID;

public class JailedPlayer {
	
	private UUID playerId = null;
	private long jailedAt = 0L;
	private long jailTime = 0L;
	private long timeServed = 0L;
	private String reason = null;
	private boolean going_to_jail = true;
	
	public JailedPlayer(UUID playerId, long jailedAt, long jailTime, String reason){//No time served as this is object creation
		this.playerId = playerId;
		this.jailedAt = jailedAt;
		this.jailTime = jailTime;
		this.timeServed = 0L;
	}
	
	public UUID getPlayerId(){
		return this.playerId;
	}
	
	public long getJailedAt(){
		return this.jailedAt;
	}
	
	public long getJailTime(){
		return this.jailTime;
	}
	
	public void setJailTime(long time){
		this.jailTime = time;
	}
	
	public long getTimeServed(){
		return this.timeServed;
	}
	
	public void setTimeServed(long timeServed){
		this.timeServed = timeServed;
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
