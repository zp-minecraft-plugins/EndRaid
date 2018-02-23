package me.zackpollard.endraid.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import me.zackpollard.endraid.EndRaid;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;


public class TeleportManager {
	
	private EndRaid plugin;
	public final Logger log = Logger.getLogger("Minecraft");
	
	public TeleportManager(EndRaid plugin){
		this.plugin = plugin;	
	}
	
	public void tpPlayers(){
		
		World world = Bukkit.getWorld("End-Raid");
		if (world == null) {
			log.log(Level.SEVERE, "EndRaid World WASN'T Found... Stopping EndRaid");
			return;
		}
		Location loc = new Location(world, 0, 64, 0);
		for(Player player : plugin.endRaiders.keySet()){
			
			plugin.startLocation.put(player, player.getLocation());
			
			player.teleport(loc);
			//TODO: Add a config option for the message
			player.sendMessage("Let the battle... Commence!");
		}
	}
	
	public void respawnPlayer(Player player){
		
		World world = Bukkit.getWorld("End-Raid");
		if(world == null) {
			log.log(Level.SEVERE, "EndRaid World WASN'T Found... Stopping EndRaid");
			return;
		}
		
		Integer lives = plugin.pointsManager.getLives(player);
		String grammar = " lives";
		
		if(lives == 1) grammar = " life";
		
		player.teleport(world.getSpawnLocation());
		player.sendMessage("You have just respawned and have " + lives + grammar + " remaining!");
		
	}
	
	public void playerDeath(Player player){
		
		Location location = plugin.startLocation.get(player);
		
		player.teleport(location);
		
	}
	
	public void tpPlayerHome(Player player){
		
		if(player.isOnline()){
			
			if(player.getWorld().getName().equals("End-Raid")){
			
				Location location = plugin.startLocation.get(player);
			
				player.teleport(location);
				
			}
		}
	}
	
	public void tpPlayersHome(){
		
		for(Player p : plugin.endRaiders.keySet()){
			
			if(p.isOnline()){
				
				if(p.getWorld().getName().equals("End-Raid")){
					
					Location location = plugin.startLocation.get(p);
					
					p.teleport(location);
					
				}
			}
		}
	}
}