package me.snowman.itemmultiply.events;

import me.snowman.itemmultiply.ItemMultiply;
import me.snowman.itemmultiply.managers.ItemManager;
import me.snowman.itemmultiply.managers.MessageManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class EntityDeath implements Listener {
    private final ItemManager itemManager = ItemMultiply.itemManager;
    private final MessageManager messageManager = ItemMultiply.messageManager;

    @EventHandler
    public void onKill(EntityDeathEvent event) {
        Player player = event.getEntity().getKiller();
        if (player == null) return;
        if (!player.hasPermission("itemmultiply.use")) return;

        String entity = event.getEntity().getName();
        int timesDropped = 1;

        if (itemManager.getXP(player, entity) == null) {
            itemManager.setXP(player, entity, 0);
        }
        if (itemManager.getLevel(player, entity) == null) {
            itemManager.setLevel(player, entity, 1);
        }

        itemManager.addXP(player, entity);


        for (int i = 1; i < itemManager.getLevel(player, entity); i++) {
            timesDropped *= 2;
        }


        for (ItemStack item : event.getDrops()) {
            item.setAmount(item.getAmount() * timesDropped);
            event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), item);
        }
        event.getDrops().clear();
        int xp = itemManager.getXP(player, entity);
        int level = itemManager.getLevel(player, entity);
        double nextLevel = itemManager.nextLevel(player, entity);
        double percentage = Math.round(xp * 100 / nextLevel * 10) / 10.0;
        if (xp == nextLevel) {
            itemManager.addLevel(player, entity);
            itemManager.setXP(player, entity, 0);
            player.sendMessage(messageManager.color("&aLEVEL UP! Level: &f" + itemManager.getLevel(player, entity)));
            return;
        }
        String actionBar = "&aLevel: " + level + " &f[" + itemManager.getProgressBar(xp, (int) nextLevel, 40, '|', "&a", "&7") + "&f] " + "&a" + percentage + "%";

        messageManager.sendActionBar(player, actionBar);
    }
}
