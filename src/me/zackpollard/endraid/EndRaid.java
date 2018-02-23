package me.zackpollard.endraid;

import java.io.File;
import java.util.HashMap;

import me.zackpollard.endraid.utils.TeleportManager;
import me.zackpollard.endraid.utils.PointsManager;
import me.zackpollard.endraid.utils.RaidManager;
import me.zackpollard.endraid.utils.WorldManager;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class EndRaid extends JavaPlugin {
	public HashMap<Player, Integer> dragonHits = new HashMap<Player, Integer>();
	public HashMap<Player, Integer> endRaiders = new HashMap<Player, Integer>();
	public HashMap<Player, Integer> leaderBoard = new HashMap<Player, Integer>();
	public HashMap<Player, Location> startLocation = new HashMap<Player, Location>();
	
	public boolean raidInProgress = false;
	public boolean raidStarted = false;
	
	public int countdownTimer = 0;
	public int countdownTaskId = 0;
	
	public WorldManager worldManager = new WorldManager(this);
	public TeleportManager playerManager = new TeleportManager(this);
	public PointsManager pointsManager = new PointsManager(this);
	public RaidManager raidManager = new RaidManager(this);
	
    public void onDisable() {
    	
    	raidManager.stopRaid();	
    }
    
    public void onEnable() {
    	
    	initializeExecutors();
    	initializeListeners();
   		generateConfig();
    }
    
    public void generateConfig(){
    	
    	File source = new File(this.getDataFolder().getAbsolutePath() + File.separator + "Worlds");
    	source.mkdirs();
    	
    	FileConfiguration config = this.getConfig();
   		config.options().header("Thanks for teaching me a bit of the configs :D");
   		config.addDefault("EndRaid.Messages.OnJoin", "Welcome to the EndRaid! Let the games begin!");
  		config.addDefault("EndRaid.Messages.OnStart", "The EndRaid is about to begin! Get Ready!");
  		config.addDefault("EndRaid.Messages.OnFinish", "The EndRaid is now complete! Thanks for playing!");
   		config.addDefault("EndRaid.CustomWorld?", false);
   		config.addDefault("EndRaid.CustomWorldName", "End-Raid");
  		config.options().copyDefaults(true);
   		saveConfig();
    	
    }
    
    public void initializeExecutors(){
    	
    	this.getCommand("er").setExecutor(new ERCommand(this));
    }
    
    public void initializeListeners(){
    	
    	new ERDamage(this);
    	
    }
}