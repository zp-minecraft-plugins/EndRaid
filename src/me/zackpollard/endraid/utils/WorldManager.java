package me.zackpollard.endraid.utils;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.zackpollard.endraid.EndRaid;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.World.Environment;


public class WorldManager {
	
	private EndRaid plugin;
	public final Logger log = Logger.getLogger("Minecraft");
	
	public WorldManager(EndRaid plugin){
		this.plugin = plugin;	
	}
	
	public void createWorld(){
		
		if(plugin.getConfig().getBoolean("EndRaid.CustomWorld?")){
			
			copyWorld();
			
		} else {
		
			WorldCreator creator = new WorldCreator("End-Raid");
			creator.environment(Environment.THE_END);
			creator.createWorld();
		}
	}
	
	public boolean deleteWorld(){
		
		World world = plugin.getServer().getWorld("End-Raid");
		plugin.getServer().unloadWorld(world, true);
		
        try {
            File worldFile = world.getWorldFolder();
            boolean deletedWorld = FileUtils.deleteFolder(worldFile);
            if (deletedWorld) {
            	log.info(ChatColor.RED + "[EndRaid]" + ChatColor.GOLD + "EndRaid was sucessfully cancelled and the world deleted");
            	return true;
            } else {
            	log.log(Level.WARNING, ChatColor.RED + "[EndRaid]" + ChatColor.GOLD + "Something happened and the EndRaid world couldn't be deleted");
            	log.log(Level.WARNING, ChatColor.RED + "[EndRaid]" + ChatColor.GOLD + "Did you already delete the world?");
            	return false;
            }
        } catch (Throwable e) {
            return false;
        }
	}
	
	public void copyWorld(){
		
		WorldCreator creator = new WorldCreator("End-Raid");
		String worldName = plugin.getConfig().getString("EndRaid.CustomWorldName");
		creator.generateStructures(false);
		
		File source = new File(plugin.getDataFolder().getAbsolutePath() + File.separator + "Worlds" + File.separator + worldName);
		File target = new File(Bukkit.getWorldContainer() + File.separator + "End-Raid");
		
		FileUtils.copyFolder(source, target, log);
		
		Bukkit.createWorld(creator);
		
	}
}