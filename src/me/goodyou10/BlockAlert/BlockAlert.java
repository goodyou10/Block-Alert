package me.goodyou10.BlockAlert;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.goodyou10.BlockAlert.Listeners.BreakListener;
import me.goodyou10.BlockAlert.Listeners.PlaceListener;
import me.goodyou10.BlockAlert.Listeners.QuitListener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockAlert extends JavaPlugin {

	Logger log = Logger.getLogger("Minecraft");

	public boolean debug = false;
	public boolean logFile = true;
	public String logFileName = "log.txt";
	public static double version = 0.3;
	public List<Integer> placeAlert;
	public List<Integer> breakAlert;
	public Map<Player, Boolean> ignore = new HashMap<Player, Boolean>();

	public static Configuration config;
	public static Commands cmds;

	public void onEnable() {
		// Listeners
		new BreakListener(this);
		new PlaceListener(this);
		new QuitListener(this);

		// Load configuration
		config = new Configuration(this);
		config.load();

		// Load commands
		cmds = new Commands(this);
		this.getCommand("blockalert").setExecutor(cmds);

		// Log file
		if (this.logFile) {
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(
						new File(getDataFolder(), this.logFileName), true));
				DateFormat dateFormat = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");
				Date date = new Date();
				writer.write("Logging started at " + dateFormat.format(date));
				writer.newLine();
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		log("Plugin loaded!");
	}

	public void onDisable() {
		if (this.logFile) {
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(
						new File(getDataFolder(), this.logFileName), true));
				DateFormat dateFormat = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");
				Date date = new Date();
				writer.write("Logging stopped at " + dateFormat.format(date));
				writer.newLine();
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		log("Plugin unloaded!");
	}

	public void msgAdmins(String message) {
		debug("Sent \"" + message + "\" to admins.");
		this.log(message);
		for (Player player : this.getServer().getOnlinePlayers()) {
			if (player.hasPermission("blockalert.admin")) {
				if (!this.ignore.containsKey(player)
						|| !this.ignore.get(player)) {
					player.sendMessage(ChatColor.GREEN + "[BlockAlert]: "
							+ message);
				}
			}
		}
		if (this.logFile) {
			debug("Writing to log file");
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(
						new File(getDataFolder(), this.logFileName), true));
				DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
				Date date = new Date();
				writer.write("[" + dateFormat.format(date) + "] " + message);
				writer.newLine();
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
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
