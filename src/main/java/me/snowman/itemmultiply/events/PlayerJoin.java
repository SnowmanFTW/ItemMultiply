package me.snowman.itemmultiply.events;

import me.snowman.itemmultiply.ItemMultiply;
import me.snowman.itemmultiply.managers.ConfigManager;
import me.snowman.itemmultiply.managers.SaveManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    private final SaveManager saveManager = ItemMultiply.saveManager;
    private final ConfigManager configManager = ItemMultiply.configManager;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("itemmultiply.use")) return;
        configManager.setupPlayer(player.getUniqueId());
        saveManager.loadPlayer(player);
    }
}
