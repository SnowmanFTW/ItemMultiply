package me.snowman.itemmultiply.events;

import me.snowman.itemmultiply.ItemMultiply;
import me.snowman.itemmultiply.managers.ItemManager;
import me.snowman.itemmultiply.managers.MessageManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreak implements Listener {
    private final ItemManager itemManager = ItemMultiply.itemManager;
    private final MessageManager messageManager = ItemMultiply.messageManager;

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("itemmultiply.use")) return;
        String material = event.getBlock().getType().name();
        int timesDropped = 1;

        if (itemManager.getXP(player, material) == null) {
            itemManager.setXP(player, material, 0);
        }
        if (itemManager.getLevel(player, material) == null) {
            itemManager.setLevel(player, material, 1);
        }

        itemManager.addXP(player, material);


        for (int i = 1; i < itemManager.getLevel(player, material); i++) {
            timesDropped *= 2;
        }


        event.setDropItems(false);
        for (ItemStack item : event.getBlock().getDrops()) {
            item.setAmount(item.getAmount() * timesDropped);
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), item);
        }
        int xp = itemManager.getXP(player, material);
        int level = itemManager.getLevel(player, material);
        double nextLevel = itemManager.nextLevel(player, material);
        double percentage = Math.round(xp * 100 / nextLevel * 10) / 10.0;
        if (xp == nextLevel) {
            itemManager.addLevel(player, material);
            itemManager.setXP(player, material, 0);
            player.sendMessage(messageManager.color("&aLEVEL UP! Level: &f" + itemManager.getLevel(player, material)));
            return;
        }
        String actionBar = "&aLevel: " + level + " &f[" + itemManager.getProgressBar(xp, (int) nextLevel, 40, '|', "&a", "&7") + "&f] " + "&a" + percentage + "%";

        messageManager.sendActionBar(player, actionBar);
    }
}
