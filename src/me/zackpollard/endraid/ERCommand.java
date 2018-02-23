package me.zackpollard.endraid;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ERCommand implements CommandExecutor {
	EndRaid plugin;
	public ERCommand(EndRaid plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(args.length == 0){
			sender.sendMessage(ChatColor.RED + "Incorrect usage!");
			sender.sendMessage(ChatColor.RED + "Type " + ChatColor.AQUA + "/er help" + ChatColor.RED + " for help!");
			return true;
		}
		if(args.length == 1 && args[0].equalsIgnoreCase("help")){
			sender.sendMessage(ChatColor.YELLOW + "======== [" + ChatColor.AQUA + "EndRaid Help" + ChatColor.YELLOW + " ] ========");
			sender.sendMessage(ChatColor.AQUA + "/er create" + ChatColor.GREEN + " : Creates an EndRaid!");
			sender.sendMessage(ChatColor.AQUA + "/er join" + ChatColor.GREEN + " : Puts you in the queue to join the EndRaid that has been created!");
			sender.sendMessage(ChatColor.AQUA + "/er cancel" + ChatColor.GREEN + " : Cancels a queued EndRaid!");
			sender.sendMessage(ChatColor.YELLOW + "======== [" + ChatColor.AQUA + "EndRaid Help" + ChatColor.YELLOW + " ] ========");
			return true;
		}
		if(args.length == 1 && args[0].equalsIgnoreCase("create")){
			
			if(!plugin.raidInProgress){
			
				if(sender.hasPermission("er.create")){
					
					plugin.raidManager.createRaid();
					
					return true;
				}
				
			} else {
				
				sender.sendMessage(ChatColor.RED + "An EndRaid is already in progress, please wait for the current raid to finish");
				
				return true;
				
			}
		}
		
		if(args.length == 1 && args[0].equalsIgnoreCase("cancel")){
			if(sender.hasPermission("er.cancel")){
				if(plugin.raidInProgress){
					if(plugin.raidManager.stopRaid()){
		            	sender.sendMessage(ChatColor.GOLD + "[EndRaid]" + ChatColor.GREEN + "EndRaid was sucessfully cancelled and the world deleted");
		            	return true;
		            } else {
		            	sender.sendMessage(ChatColor.GOLD + "[EndRaid]" + ChatColor.GOLD + "Something happened and the EndRaid world couldn't be deleted");
		            	sender.sendMessage(ChatColor.GOLD + "[EndRaid]" + ChatColor.GOLD + "Did you already delete the world?");
		            	return true;
		            }
		    	} else {
		    		sender.sendMessage(ChatColor.RED + "[EndRaid]" + ChatColor.GOLD + "There was no endraid in progress");
		    		return true;
		    	}
			} else {
				sender.sendMessage(ChatColor.RED + "[EndRaid]" + ChatColor.GOLD + "You don't have permission to stop an EndRaid");
			}
		}
		if(args.length == 1 && args[0].equalsIgnoreCase("join")){
			if(plugin.raidInProgress){
				if(!plugin.raidStarted){
					Player player = (Player) sender;
					plugin.pointsManager.joinBattle(player);
					return true;
				} else{
					sender.sendMessage(ChatColor.RED + "[EndRaid]" + ChatColor.DARK_RED + "The EndRaid has already started, maybe next time");
					return true;
				}
			} else {
				sender.sendMessage(ChatColor.RED + "[EndRaid]" + ChatColor.DARK_RED + "No EndRaid Currently Active!");
				return true;
			}
		}
		if(args.length == 1 && args[0].equalsIgnoreCase("leave")){
			Player player = (Player) sender;
			plugin.pointsManager.leaveBattle(player);
			return true;
		}
		return false;
	}

}
