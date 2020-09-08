package me.snowman.itemmultiply.commands;

import me.snowman.itemmultiply.managers.ConfigManager;
import me.snowman.itemmultiply.managers.MessageManager;
import me.snowman.itemmultiply.managers.StatsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ItemMultiply implements CommandExecutor {
    private final MessageManager messageManager = me.snowman.itemmultiply.ItemMultiply.messageManager;
    private final ConfigManager configManager = me.snowman.itemmultiply.ItemMultiply.configManager;
    private final StatsManager statsManager = me.snowman.itemmultiply.ItemMultiply.statsManager;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("itemmultiply.use")) {
            sender.sendMessage(messageManager.getMessage("NoPermission"));
        }
        if (args.length == 0) {
            for (String string : configManager.getMessages().getStringList("Help")) {
                sender.sendMessage(messageManager.color(string).replace("%command%", label));
            }
            return true;
        }
        Player player = Bukkit.getPlayer(args[0]);
        if (player != null) {
            if (args.length == 1) {
                statsManager.getStats(sender, player, label, 1);
            }
            if (args.length == 2) {
                statsManager.getStats(sender, player, label, Integer.valueOf(args[1]));
            }
        }

        switch (args[0]) {
            case "help":
                for (String string : configManager.getMessages().getStringList("Help")) {
                    sender.sendMessage(messageManager.color(string).replace("%command%", label));
                }
                break;
            case "reload":
                configManager.reloadConfig();
                configManager.reloadMessages();
                sender.sendMessage(messageManager.color(messageManager.getPrefix() + configManager.getMessages().getString("Reload")));
                break;
        }
        return true;
    }
}
