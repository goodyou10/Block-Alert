package me.goodyou10.BlockAlert;

import java.util.List;
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
	public static double version = 0.2;
	public List<Integer> placeAlert;
	public List<Integer> breakAlert;

	public static Configuration config;
	public static Commands cmds;

	public void onEnable() {
		// Listeners
		new BreakListener(this);
		new PlaceListener(this);

		// Load configuration
		config = new Configuration(this);
		config.load();

		// Load commands
		cmds = new Commands(this);
		this.getCommand("blockalert").setExecutor(cmds);

		log("Plugin loaded!");
	}

	public void onDisable() {
		log("Plugin unloaded!");
	}

	public void msgAdmins(String message) {
		debug("Sent \"" + message + "\" to admins.");
		for (Player player : this.getServer().getOnlinePlayers()) {
			if (player.hasPermission("blockalert.admin")) {
				player.sendMessage(ChatColor.GREEN + "[BlockAlert] " + message);
			}
		}
	}

	public void log(String message) {
		log.info("[BlockAlert " + version + "]: " + message);
	}

	public void debug(String message) {
		if (debug) {
			log.log(Level.WARNING, "[BlockAlert Debug]: " + message);
		}
	}

	public void debug(Exception e) {
		if (debug) {
			log.log(Level.SEVERE, "[BlockAlert Exception]: " + e.toString());
		}
	}
}
