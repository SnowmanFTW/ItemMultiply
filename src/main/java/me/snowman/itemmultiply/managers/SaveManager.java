package me.snowman.itemmultiply.managers;

import me.snowman.itemmultiply.ItemMultiply;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class SaveManager {
    private final ItemManager itemManager = ItemMultiply.itemManager;
    private final ConfigManager configManager = ItemMultiply.configManager;

    public void savePlayers() {
        for (UUID uuid : itemManager.getXp().keySet()) {
            HashMap<String, Integer> itemXp = itemManager.getXp().get(uuid);
            for (String string : itemXp.keySet()) {
                configManager.getPlayer(uuid).set(string + ".xp", itemXp.get(string));
                configManager.savePlayer();
            }
        }

        for (UUID uuid : itemManager.getLevel().keySet()) {
            HashMap<String, Integer> itemLevel = itemManager.getLevel().get(uuid);
            for (String string : itemLevel.keySet()) {
                configManager.getPlayer(uuid).set(string + ".level", itemLevel.get(string));
                configManager.savePlayer();
            }
        }
    }

    public void loadPlayer(Player player) {
        if (!player.hasPermission("itemmultiply.use")) return;
        for (String string : configManager.getPlayer(player.getUniqueId()).getKeys(false)) {
            int xp = configManager.getPlayer(player.getUniqueId()).getInt(string + ".xp");
            int level = configManager.getPlayer(player.getUniqueId()).getInt(string + ".level");

            itemManager.setXP(player, string, xp);
            itemManager.setLevel(player, string, level);
        }
    }

    public void loadOnlinePlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.hasPermission("itemmultiply.use")) continue;
            for (String string : configManager.getPlayer(player.getUniqueId()).getKeys(false)) {
                int xp = configManager.getPlayer(player.getUniqueId()).getInt(string + ".xp");
                int level = configManager.getPlayer(player.getUniqueId()).getInt(string + ".level");

                itemManager.setXP(player, string, xp);
                itemManager.setLevel(player, string, level);
            }
        }
    }
}
