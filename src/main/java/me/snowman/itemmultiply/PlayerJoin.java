package me.snowman.itemmultiply;

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
        configManager.setupPlayer(player.getUniqueId());
        saveManager.loadPlayer(player);
    }
}
