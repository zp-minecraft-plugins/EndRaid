package me.zackpollard.endraid.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.zackpollard.endraid.EndRaid;

public class Countdown implements Runnable{
	private EndRaid plugin;
	
	public Countdown(EndRaid plugin){
		this.plugin = plugin;
	}
	
	public void run(){
		
		if(plugin.countdownTimer != 0){
		
			for(Player player : plugin.endRaiders.keySet()){
				
				player.sendMessage(ChatColor.GOLD + "[EndRaid] " + ChatColor.AQUA + plugin.countdownTimer + " seconds to EndRaid start!");
				
				plugin.countdownTimer = plugin.countdownTimer--;
				
				plugin.playerManager.tpPlayers();
			}
			
		} else {
			
			for(Player player : plugin.endRaiders.keySet()){
				
				player.sendMessage(ChatColor.RED + "[EndRaid]" + ChatColor.GOLD + "Go get that nasty EnderDragon!");
				
			}
			
			plugin.raidStarted = true;
			
			Bukkit.getScheduler().cancelTask(plugin.countdownTaskId);
			
			plugin.countdownTimer = 20;
		}
	}
}