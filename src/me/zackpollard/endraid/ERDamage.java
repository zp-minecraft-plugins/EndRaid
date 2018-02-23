package me.zackpollard.endraid;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class ERDamage implements Listener{
	
	public final Logger logger = Logger.getLogger("Minecraft");
	EndRaid plugin;
	
	public ERDamage(EndRaid plugin) {
		this.plugin = plugin;
		Bukkit.getServer().getPluginManager().registerEvents(this,plugin);
	}
	@EventHandler(ignoreCancelled = true)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		plugin.raidManager.hitEntity(event);
	}
	@EventHandler(ignoreCancelled = true)
	public void onPlayerDeath(PlayerDeathEvent event){
		plugin.raidManager.playerDeath(event);
	}
	@EventHandler(ignoreCancelled = true)
	public void onPlayerDamage(EntityDamageEvent event){
		plugin.raidManager.playerDamage(event);
	}
	@EventHandler(ignoreCancelled = true)
	public void onPlayerMove(PlayerMoveEvent event){
		plugin.raidManager.playerMove(event);
	}
}