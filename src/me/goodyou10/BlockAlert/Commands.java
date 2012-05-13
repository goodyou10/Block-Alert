package me.goodyou10.BlockAlert;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

	private BlockAlert plugin;

	public Commands(BlockAlert plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		if (cmd.getName().equalsIgnoreCase("blockalert")) {
			if (args.length < 1) {
				help(sender);
				return true;
			}

			if (args[0].equalsIgnoreCase("reload")) {
				if (player != null) {
					sender.sendMessage(ChatColor.RED
							+ "BlockAlert can only be reloaded through the console!");
					return true;
				}
				this.plugin.reloadConfig();
				BlockAlert.config.load();
				sender.sendMessage("[BlockAlert]: Configuration has been reloaded!");
			} else if (args[0].equalsIgnoreCase("ignore")) {
				if (player == null) {
					sender.sendMessage(ChatColor.RED
							+ "This command can only be used by players.");
					return true;
				}
				if (plugin.ignore.containsKey(player)) {
					if(plugin.ignore.get(player)) {
						plugin.ignore.put(player, false);
						sender.sendMessage("[BlockAlert]: Alert ignore off.");
					} else {
						plugin.ignore.put(player, true);
						sender.sendMessage("[BlockAlert]: Alert ignore on.");
					}
				} else {
					plugin.ignore.put(player, true);
					sender.sendMessage("[BlockAlert]: Alert ignore on.");
				}
			} else if (args[0].equalsIgnoreCase("version")
					|| args[0].equalsIgnoreCase("about")) {
				sender.sendMessage("Block Alert " + BlockAlert.version
						+ ", made by Goodyou10 and tested by Zavabia");
			} else {
				help(sender);
			}
			return true;
		}
		return false;
	}

	public void help(CommandSender sender) {
		sender.sendMessage("Avalible commands are:");
		sender.sendMessage("/blockalert version - Get the version.");
		sender.sendMessage("/blockalert about - About Block Alert.");
		sender.sendMessage("/blockalert reload - Reload the configuration.");
		sender.sendMessage("/blockalert ignore - (Un)Ignore admin messages.");
	}
}
