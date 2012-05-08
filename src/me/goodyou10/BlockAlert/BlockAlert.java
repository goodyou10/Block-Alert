package me.goodyou10.BlockAlert;

import java.util.logging.Level;
import java.util.logging.Logger;

import me.goodyou10.BlockAlert.Listeners.BreakListener;
import me.goodyou10.BlockAlert.Listeners.PlaceListener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockAlert extends JavaPlugin {
	
	Logger log = Logger.getLogger("Minecraft");
	
	public boolean debug = false;
	public static double version = 0.1;
	
	public void onEnable() {
		//Listeners
		new BreakListener(this);
		new PlaceListener(this);
		
		log("Plugin loaded!");
	}
	
	public void onDisable() {
		log("Plugin unloaded!");
	}
	
	public void msgAdmins(String message) {
		for (Player player : this.getServer().getOnlinePlayers()) {
			if(player.hasPermission("blockalert.admin")) {
				player.sendMessage(ChatColor.GREEN + "[BlockAlert] " + message);
			}
		}
	}
	
	public void log(String message) {
		log.info("[BlockAlert " + version + "]: " + message);
	}
	
	public void debug(String message) {
		if(debug) {
			log.log(Level.WARNING, "[BlockAlert Debug]: " + message);
		}
	}
	
	public void debug(Exception e) {
		if(debug) {
			log.log(Level.SEVERE, "[BlockAlert Exception]: " + e.toString());
		}
	}
}
