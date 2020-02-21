package me.snowman.itemmultiply;

import me.snowman.itemmultiply.managers.*;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemMultiply extends JavaPlugin {
    public static ItemManager itemManager;
    public static PluginManager pluginManager;
    public static MessageManager messageManager;
    public static ConfigManager configManager;
    public static SaveManager saveManager;

    public void onEnable() {
        loadManagers();
        pluginManager.loadEvents();
        configManager.setupConfig();
        saveManager.loadOnlinePlayers();
    }

    public void onDisable() {
        saveManager.savePlayers();
    }

    private void loadManagers() {
        messageManager = new MessageManager();
        itemManager = new ItemManager();
        pluginManager = new PluginManager();
        configManager = new ConfigManager();
        saveManager = new SaveManager();
    }
}
