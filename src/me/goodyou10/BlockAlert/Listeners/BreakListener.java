package me.goodyou10.BlockAlert.Listeners;

import me.goodyou10.BlockAlert.BlockAlert;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakListener implements Listener {

	private BlockAlert plugin;

	public BreakListener(BlockAlert plugin) {
		this.plugin = plugin;
		this.plugin.getServer().getPluginManager()
				.registerEvents(this, this.plugin);
	}

	@EventHandler(ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event) {
		plugin.debug("A block has been broken.");

		// Get variables
		Player player = event.getPlayer();
		String name = player.getName();
		Block block = event.getBlock();
		Integer id = block.getType().getId();
		String bName = block.getType().name().toLowerCase()
				.replaceAll("_", " ");

		// Should we alert?
		for (Integer lId : plugin.breakAlert) {
			if (lId == id) {
				this.plugin.msgAdmins(name + " has broken " + bName);
				break;
			}
		}
	}
}
