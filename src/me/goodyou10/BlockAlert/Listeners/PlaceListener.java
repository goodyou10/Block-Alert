package me.goodyou10.BlockAlert.Listeners;

import me.goodyou10.BlockAlert.BlockAlert;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceListener implements Listener {

	private BlockAlert plugin;

	public PlaceListener(BlockAlert plugin) {
		this.plugin = plugin;
		this.plugin.getServer().getPluginManager()
				.registerEvents(this, this.plugin);
	}

	@EventHandler(ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event) {
		plugin.debug("A block has been placed.");

		// Get variables
		Player player = event.getPlayer();
		String name = player.getName();
		Block block = event.getBlockPlaced();
		Integer id = block.getType().getId();
		String bName = block.getType().name().toLowerCase()
				.replaceAll("_", " ");

		// Should we alert?
		for (Integer lId : plugin.placeAlert) {
			if (lId == id) {
				this.plugin.log(name + " has placed " + bName);
				this.plugin.msgAdmins(name + " has placed " + bName);
				break;
			}
		}
	}
}
