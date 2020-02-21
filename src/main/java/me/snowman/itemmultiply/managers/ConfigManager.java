package me.snowman.itemmultiply.managers;

import me.snowman.itemmultiply.ItemMultiply;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ConfigManager {
    private final PluginManager pluginManager = ItemMultiply.pluginManager;
    private final ItemMultiply plugin = pluginManager.getPlugin();
    private File folderData = new File(plugin.getDataFolder(), "data" + File.separator);
    private File playerFile;
    private FileConfiguration playerCfg;


    public void setupPlayer(UUID uuid) {
        String name = Bukkit.getPlayer(uuid).getName();
        playerFile = new File(plugin.getDataFolder(), "data" + File.separator + uuid + ".yml");
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        if (!folderData.exists()) {
            folderData.mkdir();
        }
        if (!playerFile.exists()) {
            try {
                playerFile.createNewFile();
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Playerfile for &c" + name + " &6created."));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        playerCfg = YamlConfiguration.loadConfiguration(playerFile);
    }

    public FileConfiguration getPlayer(UUID uuid) {
        if (playerFile == null || !playerFile.getName().equalsIgnoreCase(uuid + ".yml")) {
            playerFile = new File(plugin.getDataFolder(), "data" + File.separator + uuid + ".yml");
            playerCfg = YamlConfiguration.loadConfiguration(playerFile);
        }
        return playerCfg;
    }

    public void savePlayer() {
        try {
            playerCfg.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setupConfig() {
        plugin.saveDefaultConfig();
    }

    public FileConfiguration getConfig() {
        return plugin.getConfig();
    }

    public void saveConfig() {
        plugin.saveConfig();
    }

    public void reloadConfig() {
        plugin.reloadConfig();
    }
}
