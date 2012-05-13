package me.goodyou10.BlockAlert.Listeners;

import me.goodyou10.BlockAlert.BlockAlert;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

	private BlockAlert plugin;

	public QuitListener(BlockAlert plugin) {
		this.plugin = plugin;
		this.plugin.getServer().getPluginManager()
				.registerEvents(this, this.plugin);
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if(plugin.ignore.containsKey(player)) {
			plugin.debug("Removing ignore for " + player.getName());
			plugin.ignore.remove(player);
		}
	}
}
