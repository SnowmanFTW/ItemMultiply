package me.snowman.itemmultiply.managers;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public class MessageManager {

    public String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public List<String> color(List<String> list) {
        return list.stream().map(this::color).collect(Collectors.toList());
    }
}
