package me.zackpollard.endraid.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import me.zackpollard.endraid.ERTeleport;
import me.zackpollard.endraid.EndRaid;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;


public class RaidManager {
	
	private EndRaid plugin;
	public final Logger log = Logger.getLogger("Minecraft");
	
	public RaidManager(EndRaid plugin){
		this.plugin = plugin;
	}
	
	public void createRaid(){
		
		plugin.worldManager.createWorld();
		//TODO: Add a config for the command waiting time
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new ERTeleport(plugin), 20 * 20L);
		
		plugin.raidInProgress = true;
		
		for(Player p : Bukkit.getOnlinePlayers()){
			
			//TODO: Add a config option for the message
			//TODO: Add a permission to see the message related to the permission to type /er join
			
			p.sendMessage(ChatColor.GOLD + "An EndRaid will begin in 2 minutes. Please type /er join to enter");
			
		}
	}
	
	public void startRaid(){
		
		for(Player player : plugin.endRaiders.keySet()){
			
			player.sendMessage(ChatColor.GOLD + "[EndRaid] " + ChatColor.GOLD + ChatColor.BOLD + "The EndRaid has begun! Go kill that dragon!");
			
		}
	}
	
	public void startCountdown(){
		
		plugin.countdownTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Countdown(plugin), 20, 20);
		
	}
	
	public void continueCountdown(){
		
		//delayed task with 1 second delay continues from the last delayed task until Integer Countdown is 0
		
	}
	
	public boolean stopRaid(){
		
		if(plugin.raidInProgress){
		
			plugin.playerManager.tpPlayersHome();
			
			Boolean worldDeleted = plugin.worldManager.deleteWorld();
			if(worldDeleted){
				
				resetVariables();
				
				Bukkit.getServer().getScheduler().cancelTasks(plugin);
				return true;
			} else {
				log.log(Level.SEVERE, "EndRaid world was not deleted... Plugin disabling to stop errors");
				Bukkit.getServer().getScheduler().cancelTasks(plugin);
				Bukkit.getPluginManager().disablePlugin(plugin);
				return false;
			}
		} else {
			log.log(Level.INFO, "No EndRaid in Progress so the raid could not be stopped");
			return true;
		}
	}
	
	public void hitEntity(EntityDamageByEntityEvent event){
		
		if(plugin.raidInProgress = false) return;
		
		if(event.getEntity().isDead()) return;
		
		if(event.getEntity() instanceof EnderDragon){
			
			if(event.getDamager() instanceof Player){
				
				Player player = (Player) event.getDamager();
				EnderDragon enderDragon = (EnderDragon) event.getEntity();
				
				if(enderDragon.getWorld().getName().equals("End-Raid")){
					
					plugin.pointsManager.hitDragon(player);
					
				}
			}
		}
	}
	
	public void playerDeath(PlayerDeathEvent event){
		
		Player player = event.getEntity();
		
		Integer lives = plugin.pointsManager.playerDeath(player);
		
		if(lives <= 0){
			
			plugin.pointsManager.playerOutOfLives(player);
			
			event.setDeathMessage(ChatColor.GOLD + "[EndRaid] " + ChatColor.RED + player.getName() + " has no lives remaining and is out of the end raid :(");
			
		} else if(lives >= 0){
			
			event.setDeathMessage(ChatColor.GOLD + "[EndRaid] " + ChatColor.RED + player.getName() + " died and has " + lives + "left... They will respawn soon :)");
			
		}
	}
	
	public void playerMove(PlayerMoveEvent event){
		
		if(plugin.raidStarted) return;
		if(!plugin.raidInProgress) return;
		Player player = event.getPlayer();
		World world = player.getWorld();
		if(!plugin.endRaiders.containsKey(player)) return;
		if(!world.getName().equals("End-Raid")) return;
					
		event.setCancelled(true);
	}
	
	public void playerDamage(EntityDamageEvent event){
		
		if(plugin.raidStarted) return;
		if(!plugin.raidInProgress) return;
		if(!(event.getEntity() instanceof Player)) return;
		Player player = (Player) event.getEntity();
		World world = player.getWorld();
		if(!plugin.endRaiders.containsKey(player)) return;
		if(!world.getName().equals("End-Raid")) return;
		
		event.setCancelled(true);
		
	}
	
	public void spawnDragon(World world, Location loc){
		
		world.spawnEntity(loc, EntityType.ENDER_DRAGON);
		
	}
	
	public void resetVariables(){
		
		plugin.dragonHits.clear();
		plugin.endRaiders.clear();
		plugin.leaderBoard.clear();
		plugin.startLocation.clear();
		plugin.raidInProgress = false;
		plugin.raidStarted = false;
		
	}
}