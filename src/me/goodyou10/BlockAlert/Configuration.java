package me.goodyou10.BlockAlert;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

public class Configuration {

	private BlockAlert plugin;
	public String path = "plugins" + File.separator + "BlockAlert"
			+ File.separator + "config.yml";
	private FileConfiguration conf;

	public Configuration(BlockAlert plugin) {
		this.plugin = plugin;
		conf = this.plugin.getConfig();
	}

	public void load() {
		plugin.debug("Loading Configuration");
		if (exists()) {
			this.plugin.log("Configuration has been loaded!");
		} else {
			this.plugin.log("No configuration file found. Creating one now.");
		}
		conf.options().copyDefaults(true);
		this.plugin.saveConfig();
		loadVariables();
	}

	public void loadVariables() {
		this.plugin.debug("Getting variables");
		// Debug
		plugin.debug = conf.getBoolean("debug", false);
		this.plugin.debug("Debug: " + plugin.debug);
		// Place Alert
		plugin.placeAlert = conf.getIntegerList("alert.place");
		this.plugin.debug("Added ids " + plugin.placeAlert.toString()
				+ " to place alert.");
		// Break Alert
		plugin.breakAlert = conf.getIntegerList("alert.break");
		this.plugin.debug("Added ids " + plugin.breakAlert.toString()
				+ " to break alert.");
	}

	public boolean exists() {
		if (new File(path).exists()) {
			return true;
		} else {
			return false;
		}
	}
}
