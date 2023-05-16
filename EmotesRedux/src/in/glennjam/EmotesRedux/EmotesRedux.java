package in.glennjam.EmotesRedux;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class EmotesRedux extends JavaPlugin implements Listener {

	public HashMap<String, Long> cooldowns = new HashMap<String, Long>();

	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("EmotesRedux v1.5 is enabled, and emotes are enabled!");
	}

	@Override
	public void onDisable() {
		getLogger().info("Problems?  Suggestions?  DM @Simsnet#1754 on Discord!");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (args.length == 2) {
			final int cooldownTime = this.getConfig().getInt("cooldown");
			if (cooldowns.containsKey(sender.getName())) {
				final long secondsLeft = cooldowns.get(sender.getName()) / 1000 + cooldownTime - System.currentTimeMillis() / 1000;
				if (secondsLeft > 0) {
					// Still cooling down
					sender.sendMessage(ChatColor.RED + "Sorry!  You must wait " + secondsLeft + " seconds before you can emote!");
					Bukkit.getLogger().info("[EmotesRedux Logger] " + sender.getName() + " has " + secondsLeft + " seconds of cooldown time remaining.");
					return true;
				}
			}
		}

		if (cmd.getName().equalsIgnoreCase("emote")) {
			if (args.length == 2) {
				if (getConfig().contains("emotes." + args[0].toLowerCase())) {
					if (this.getServer().getPlayer(args[1]) != null) {
						final String emote1 = this.getConfig().getString("emotes." + args[0].toLowerCase());
						final String emote2 = emote1.replace("%sender%", sender.getName());
						final String emote3 = emote2.replace("%target%", getServer().getPlayer(args[1]).getName());
						final String fullemote = ChatColor.translateAlternateColorCodes('&', emote3);
						final Player target = Bukkit.getServer().getPlayer(args[1]);
						target.playSound(target.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
						Bukkit.broadcastMessage(fullemote);
						cooldowns.put(sender.getName(), System.currentTimeMillis());
						return true;
					} else {
						sender.sendMessage(ChatColor.RED + "You can't emote to a player that isn't online!");
						Bukkit.getLogger().info("[EmotesRedux Logger] " + sender.getName() + " tried to emote to a player that wasn't online.");
						return true;
					}
				} else {
					sender.sendMessage(ChatColor.RED + "That emote doesn't exist!  Run /emotes for a list of emotes.");
					Bukkit.getLogger().info("[EmotesRedux Logger] " + sender.getName() + " did not specify an emote.");
					return true;
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Usage: /emote <emote> <player>.  Run /emotes for a list of emotes.");
				Bukkit.getLogger().info("[EmotesRedux Logger] " + sender.getName() + " did not use the /emote command correctly.");
				return true;
			}
		}

		if (cmd.getName().equalsIgnoreCase("emoteop")) {
			if (args.length == 2) {
				if (getConfig().contains("emotes." + args[0].toLowerCase())) {
					if (this.getServer().getPlayer(args[1]) != null) {
						final String emote1 = this.getConfig().getString("emotes." + args[0].toLowerCase());
						final String emote2 = emote1.replace("%sender%", sender.getName());
						final String emote3 = emote2.replace("%target%", getServer().getPlayer(args[1]).getName());
						final String fullemote = ChatColor.translateAlternateColorCodes('&', emote3);
						final Player target = Bukkit.getServer().getPlayer(args[1]);
						target.playSound(target.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
						Bukkit.broadcastMessage(fullemote);
						return true;
					} else {
						sender.sendMessage(ChatColor.RED + "You can't emote to a player that isn't online!");
						Bukkit.getLogger().info("[EmotesRedux Logger - OP] " + sender.getName() + " tried to emote to a player that wasn't online.");
						return true;
					}
				} else {
					sender.sendMessage(ChatColor.RED + "That emote doesn't exist!  Run /emotes for a list of emotes.");
					Bukkit.getLogger().info("[EmotesRedux Logger - OP] " + sender.getName() + " did not specify an emote.");
					return true;
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Usage: /emoteop <emote> <player>.  Run /emotes for a list of emotes.");
				Bukkit.getLogger().info("[EmotesRedux Logger - OP] " + sender.getName() + " did not use the /emoteop command correctly.");
				return true;
			}
		}

		if (cmd.getName().equalsIgnoreCase("emoteimp")) {
			if (args.length == 3) {
				if (getConfig().contains("emotes." + args[1].toLowerCase())) {
					if (this.getServer().getPlayer(args[0]) != null && this.getServer().getPlayer(args[2]) != null) {
						final String emote1 = this.getConfig().getString("emotes." + args[1].toLowerCase());
						final String emote2 = emote1.replace("%sender%", getServer().getPlayer(args[0]).getName());
						final String emote3 = emote2.replace("%target%", getServer().getPlayer(args[2]).getName());
						final String fullemote = ChatColor.translateAlternateColorCodes('&', emote3);
						final Player target = Bukkit.getServer().getPlayer(args[2]);
						target.playSound(target.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
						Bukkit.broadcastMessage(fullemote);
						return true;
					} else {
						sender.sendMessage(ChatColor.RED + "You can't impersonate or emote to a player that isn't online!");
						Bukkit.getLogger().info("[EmotesRedux Logger - OP] " + sender.getName() + " entered player names that are not online.");
						return true;
					}
				} else {
					sender.sendMessage(ChatColor.RED + "That emote doesn't exist!  Run /emotes for a list of emotes.");
					Bukkit.getLogger().info("[EmotesRedux Logger - OP] " + sender.getName() + " did not specify an emote.");
					return true;
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Usage: /emoteimp <from player> <emote> <to player>.  Run /emotes for a list of emotes.");
				Bukkit.getLogger().info("[EmotesRedux Logger - OP] " + sender.getName() + " did not use the /emoteimp command correctly.");
				return true;
			}
		}

		if (cmd.getName().equalsIgnoreCase("erreload")) {
			Bukkit.getLogger().info("[EmotesRedux Logger - OP] " + sender.getName() + " has requested a reload of the EmotesRedux configuration!");
			this.reloadConfig();
			Bukkit.getLogger().info("[EmotesRedux Logger] The EmotesRedux configuration has been reloaded from the disk!");
			sender.sendMessage(ChatColor.GREEN + "EmotesRedux configuration reloaded from disk!");
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("emotes")) {
			final Set<String> emotes = this.getConfig().getConfigurationSection("emotes").getKeys(false);
			final String emotelist = String.join(", ", emotes);
			final char charArray[] = emotelist.toCharArray();
			final String emotesalpha = new String(charArray);
			sender.sendMessage(ChatColor.GREEN + "EmotesRedux v1.5");
			sender.sendMessage(ChatColor.DARK_GREEN + "Configurable emotes for your server!");
			sender.sendMessage(ChatColor.AQUA + "Made by TheRealSimShady");
			sender.sendMessage(ChatColor.DARK_RED + "Commands:");
			sender.sendMessage(ChatColor.GRAY + "/emote <emote> <player>");
			sender.sendMessage(ChatColor.RED + "   Send an emote to a player.");
			if (sender.hasPermission("emotes.admin")) {
				sender.sendMessage(ChatColor.GRAY + "/emoteop <emote> <player>");
				sender.sendMessage(ChatColor.RED + "   Send an emote to a player without cooldown.");
				sender.sendMessage(ChatColor.GRAY + "/emoteimp <from player> <emote> <to player>");
				sender.sendMessage(ChatColor.RED + "   Send an emote to a player impersonating another player.");
			}
			sender.sendMessage(ChatColor.GRAY + "/emotes");
			sender.sendMessage(ChatColor.RED + "   Show this menu.");
			if (sender.hasPermission("emotes.admin")) {
				sender.sendMessage(ChatColor.GRAY + "/erreload");
				sender.sendMessage(ChatColor.RED + "   Reload the configuration file.");
			}
			sender.sendMessage(ChatColor.GOLD + "Available emotes: " + ChatColor.WHITE + emotesalpha);
			Bukkit.getLogger().info("[EmotesRedux Logger] " + sender.getName() + " opened the help menu.");
			return true;
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		final List<String> l1 = new ArrayList<String>();
		final List<String> l2 = new ArrayList<String>();
		final List<String> l3 = new ArrayList<String>();
		if (cmd.getName().equalsIgnoreCase("emote") && args.length > 0 && sender instanceof Player) {
			if (args.length > 1) {
				for (final Player p : getServer().getOnlinePlayers()) {
					l2.add(p.getName());
				}
				return l2;
			} else {
				final Set<String> list = this.getConfig().getConfigurationSection("emotes").getKeys(false);
				l1.addAll(list);
			}
		}
		if (cmd.getName().equalsIgnoreCase("emoteop") && args.length > 0 && sender instanceof Player) {
			if (args.length > 1) {
				for (final Player p : getServer().getOnlinePlayers()) {
					l2.add(p.getName());
				}
				return l2;
			} else {
				final Set<String> list = this.getConfig().getConfigurationSection("emotes").getKeys(false);
				l1.addAll(list);
			}
		}
		if (cmd.getName().equalsIgnoreCase("emoteimp") && args.length > 0 && sender instanceof Player) {
			if (args.length > 2) {
				for (final Player p : getServer().getOnlinePlayers()) {
					l3.add(p.getName());
				}
				return l3;
			}
			else if (args.length > 1) {
				final Set<String> list = this.getConfig().getConfigurationSection("emotes").getKeys(false);
				l2.addAll(list);
				return l2;
			} else {
				for (final Player p : getServer().getOnlinePlayers()) {
					l1.add(p.getName());
				}
			}
		}
		return l1;

	}
}
