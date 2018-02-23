package me.zackpollard.endraid;

public class ERTeleport implements Runnable{
	private EndRaid plugin;
	
	public ERTeleport(EndRaid plugin){
		this.plugin = plugin;	
	}
	public void run(){
		
		plugin.playerManager.tpPlayers();
		plugin.raidManager.startCountdown();
		
	}
}