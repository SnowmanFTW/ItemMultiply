package me.snowman.itemmultiply.managers;

import me.snowman.itemmultiply.ItemMultiply;
import me.snowman.itemmultiply.PlayerJoin;
import me.snowman.itemmultiply.events.BlockBreak;

public class PluginManager {

    public ItemMultiply getPlugin() {
        return ItemMultiply.getPlugin(ItemMultiply.class);
    }

    public void loadEvents() {
        getPlugin().getServer().getPluginManager().registerEvents(new BlockBreak(), getPlugin());
        getPlugin().getServer().getPluginManager().registerEvents(new PlayerJoin(), getPlugin());
    }
}
