package me.snowman.itemmultiply.managers;

import me.snowman.itemmultiply.ItemMultiply;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatsManager {
    private final ItemManager itemManager = ItemMultiply.itemManager;
    private final ConfigManager configManager = ItemMultiply.configManager;
    private final MessageManager messageManager = ItemMultiply.messageManager;

    public void getStats(CommandSender sender, Player player, String label, int page) {
        HashMap<String, Integer> allXp = itemManager.getXp().get(player.getUniqueId());
        HashMap<String, Integer> allLevel = itemManager.getLevel().get(player.getUniqueId());

        for (String string : configManager.getPlayer(player.getUniqueId()).getKeys(false)) {
            if (allXp.containsKey(string) && allLevel.containsKey(string)) continue;
            allXp.put(string, configManager.getPlayer(player.getUniqueId()).getInt(string + ".xp"));
            allLevel.put(string, configManager.getPlayer(player.getUniqueId()).getInt(string + ".level"));
        }
        List<String> listAll = new ArrayList<>(allXp.keySet());
        List<String> listPage = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            if ((i + 5 * (page - 1)) >= allXp.size()) continue;
            listPage.add(listAll.get(i + 5 * (page - 1)));
        }

        sender.sendMessage(messageManager.color(configManager.getMessages().getString("PlayerInfoHeader").replace("%player%", player.getName())));
        for (int i = 0; i < listPage.size(); i++) {
            String stat = listPage.get(i).substring(0, 1).toUpperCase() + listPage.get(i).substring(1).toLowerCase();
            String xp = String.valueOf(allXp.get(listPage.get(i)));
            String level = String.valueOf(allLevel.get(listPage.get(i)));
            String neededXP = String.valueOf(itemManager.nextLevel(player, listPage.get(i)));

            for (String string : configManager.getMessages().getStringList("PlayerInfo")) {
                sender.sendMessage(messageManager.color(string).replace("%xp%", xp).replace("%level%", level).replace("%stat%", stat).replace("%needed_xp%", neededXP));
            }
        }
        if (page + 1 <= allXp.size() / 5 + 1) {
            sender.sendMessage(messageManager.color(configManager.getMessages().getString("PlayerInfoFooter").replace("%page%", String.valueOf(page + 1)).replace("%player%", player.getName()).replace("%command%", label)));
        }
    }
}
