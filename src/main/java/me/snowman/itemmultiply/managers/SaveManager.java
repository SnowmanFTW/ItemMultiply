package me.snowman.itemmultiply.managers;

import me.snowman.itemmultiply.ItemMultiply;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class SaveManager {
    private final ItemManager itemManager = ItemMultiply.itemManager;
    private final ConfigManager configManager = ItemMultiply.configManager;

    public void savePlayers() {
        for (UUID uuid : itemManager.getItemxp().keySet()) {
            HashMap<Material, Integer> itemXp = itemManager.getItemxp().get(uuid);
            for (Material material : itemXp.keySet()) {
                configManager.getPlayer(uuid).set(material.name() + ".xp", itemXp.get(material));
                configManager.savePlayer();
            }
        }

        for (UUID uuid : itemManager.getItemlevel().keySet()) {
            HashMap<Material, Integer> itemLevel = itemManager.getItemlevel().get(uuid);
            for (Material material : itemLevel.keySet()) {
                configManager.getPlayer(uuid).set(material.name() + ".level", itemLevel.get(material));
                configManager.savePlayer();
            }
        }
    }

    public void loadPlayer(Player player) {
        for (String string : configManager.getPlayer(player.getUniqueId()).getKeys(false)) {
            Material material = Material.getMaterial(string);
            int xp = configManager.getPlayer(player.getUniqueId()).getInt(string + ".xp");
            int level = configManager.getPlayer(player.getUniqueId()).getInt(string + ".level");

            itemManager.setXP(player, material, xp);
            itemManager.setLevel(player, material, level);
        }
    }

    public void loadOnlinePlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (String string : configManager.getPlayer(player.getUniqueId()).getKeys(false)) {
                Material material = Material.getMaterial(string);
                int xp = configManager.getPlayer(player.getUniqueId()).getInt(string + ".xp");
                int level = configManager.getPlayer(player.getUniqueId()).getInt(string + ".level");

                itemManager.setXP(player, material, xp);
                itemManager.setLevel(player, material, level);
            }
        }
    }
}
