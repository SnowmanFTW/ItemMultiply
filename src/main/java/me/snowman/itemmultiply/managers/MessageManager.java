package me.snowman.itemmultiply.managers;

import me.snowman.itemmultiply.ItemMultiply;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class MessageManager {
    private final ConfigManager configManager = ItemMultiply.configManager;

    public String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public List<String> color(List<String> list) {
        return list.stream().map(this::color).collect(Collectors.toList());
    }

    public String getMessage(String message) {
        return color(configManager.getMessages().getString(message));
    }

    public String getPrefix() {
        return getMessage("Prefix");
    }

    public void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(color(message)));
    }
}
