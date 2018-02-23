package me.zackpollard.endraid.utils;

import java.util.logging.Logger;

import me.zackpollard.endraid.EndRaid;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class PointsManager {
	
	private EndRaid plugin;
	public final Logger log = Logger.getLogger("Minecraft");
	
	public PointsManager(EndRaid plugin){
		this.plugin = plugin;	
	}
	
	public void joinBattle(Player player){
		
		plugin.dragonHits.put(player, 0);
		//TODO: Add a config option for amount of lives
		plugin.endRaiders.put(player, 3);
		player.sendMessage(ChatColor.DARK_GREEN + "You have succesfully joined the EndRaid!");
		
	}
	
	public void leaveBattle(Player player){
		
		plugin.playerManager.tpPlayerHome(player);
		this.removePlayer(player);
		
		player.sendMessage(ChatColor.RED +"You have left the EndRaid and have lost all points");
		
	}
	
	public int playerDeath(Player player){
		
		Integer lives = this.takeLife(player);
		
		return lives;
		
	}
	
	public int playerRespawn(Player player){
		
		Integer lives = plugin.endRaiders.get(player);
		
		return lives;
		
	}
	
	public int takeLife(Player player){
		
		Integer lives = plugin.endRaiders.get(player);
		lives = lives--;
		plugin.endRaiders.put(player, lives);
		
		return lives;
	}
	
	public int getLives(Player player){
		
		Integer lives = plugin.endRaiders.get(player);
		
		return lives;
	}
	
	public int hitDragon(Player player){
		
		Integer points = plugin.dragonHits.get(player);
		//TODO: Make amount of points customizable in the config
		points = points++;
		plugin.dragonHits.put(player, points);
		
		return points;
	
	}
	
	public int destroyCrystal(Player player){
		
		Integer points = plugin.dragonHits.get(player);
		//TODO: Make amount of points customizable in the config
		points = points++;
		plugin.dragonHits.put(player, points);
		
		return points;
		
	}
	
	public void removePlayer(Player player){
		
		plugin.dragonHits.remove(player);
		plugin.endRaiders.remove(player);
		plugin.startLocation.remove(player);
		
	}
	
	public void playerOutOfLives(Player player){
		
		plugin.endRaiders.remove(player);
		plugin.startLocation.remove(player);
		
	}
	
	public void updateLeaderBoard(){
		
		Integer iFirst = 0;
		Player pFirst = null;
		Integer iSecond = 0;
		Player pSecond = null;
		Integer iThird = 0;
		Player pThird = null;
		
		for(Player player : plugin.dragonHits.keySet()){
			
			Integer points = plugin.dragonHits.get(player);
			
			if(points > iFirst){
				iFirst = points;
				pFirst = player;
				continue;
			} else if(points > iSecond){
				iSecond = points;
				pSecond = player;
				continue;
			} else if(points > iThird){
				iThird = points;
				pThird = player;
				continue;
			}
		}
		plugin.leaderBoard.put(pFirst, iFirst);
		plugin.leaderBoard.put(pSecond, iSecond);
		plugin.leaderBoard.put(pThird, iThird);
	}
}