package me.snowman.itemmultiply.managers;

import me.snowman.itemmultiply.ItemMultiply;
import me.snowman.itemmultiply.events.BlockBreak;
import me.snowman.itemmultiply.events.EntityDeath;
import me.snowman.itemmultiply.events.PlayerJoin;

public class PluginManager {

    public ItemMultiply getPlugin() {
        return ItemMultiply.getPlugin(ItemMultiply.class);
    }

    public void loadEvents() {
        getPlugin().getServer().getPluginManager().registerEvents(new BlockBreak(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents(new PlayerJoin(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents(new EntityDeath(), getPlugin());
    }

    public void loadCommands() {
        getPlugin().getCommand("itemmultiply").setExecutor(new me.snowman.itemmultiply.commands.ItemMultiply());
    }
}
