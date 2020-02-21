package me.snowman.itemmultiply.managers;

import com.google.common.base.Strings;
import me.snowman.itemmultiply.ItemMultiply;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class ItemManager {
    private final MessageManager messageManager = ItemMultiply.messageManager;
    private HashMap<UUID, HashMap<Material, Integer>> itemxp = new HashMap<>();
    private HashMap<UUID, HashMap<Material, Integer>> itemlevel = new HashMap<>();

    public Integer getXP(Player player, Material material) {
        if (itemxp.get(player.getUniqueId()) == null) {
            return null;
        }
        if (itemxp.get(player.getUniqueId()).containsKey(material)) {
            return itemxp.get(player.getUniqueId()).get(material);
        }
        return null;
    }

    public void addXP(Player player, Material material) {
        HashMap<Material, Integer> perItem = itemxp.get(player.getUniqueId());
        int materialXP = perItem.get(material);
        perItem.put(material, materialXP + 1);
        itemxp.put(player.getUniqueId(), perItem);
    }

    public void addXP(Player player, Material material, int xp) {
        HashMap<Material, Integer> perItem = itemxp.get(player.getUniqueId());
        int materialXP = perItem.get(material);
        perItem.put(material, materialXP + xp);
        itemxp.put(player.getUniqueId(), perItem);
    }

    public void setXP(Player player, Material material, int xp) {
        HashMap<Material, Integer> perItem;
        if (itemxp.get(player.getUniqueId()) != null) {
            perItem = itemxp.get(player.getUniqueId());
        } else {
            perItem = new HashMap<>();
        }
        perItem.put(material, xp);
        itemxp.put(player.getUniqueId(), perItem);
    }

    public Integer getLevel(Player player, Material material) {
        if (itemlevel.get(player.getUniqueId()) == null) {
            return null;
        }
        if (itemlevel.get(player.getUniqueId()).containsKey(material)) {
            return itemlevel.get(player.getUniqueId()).get(material);
        }
        return null;
    }

    public void addLevel(Player player, Material material) {
        HashMap<Material, Integer> perItem = itemlevel.get(player.getUniqueId());
        int materialLevel = perItem.get(material);
        perItem.put(material, materialLevel + 1);
        itemlevel.put(player.getUniqueId(), perItem);
    }

    public void setLevel(Player player, Material material, int level) {
        HashMap<Material, Integer> perItem;
        if (itemlevel.get(player.getUniqueId()) != null) {
            perItem = itemlevel.get(player.getUniqueId());
        } else {
            perItem = new HashMap<>();
        }
        perItem.put(material, level);
        itemlevel.put(player.getUniqueId(), perItem);
    }

    public HashMap<UUID, HashMap<Material, Integer>> getItemlevel() {
        return itemlevel;
    }

    public HashMap<UUID, HashMap<Material, Integer>> getItemxp() {
        return itemxp;
    }

    public double nextLevel(Player player, Material material) {
        double exponent = 1.7;
        double baseXP = 50;
        double level = getLevel(player, material) + 1;
        return Math.floor(baseXP * (Math.pow(level, exponent)));
    }

    //Thanks to https://www.spigotmc.org/threads/progress-bars-and-percentages.276020/
    public String getProgressBar(int current, int max, int totalBars, char symbol, String completedColor,
                                 String notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);
        if (progressBars == 0) {
            progressBars = 1;
        }

        return Strings.repeat(messageManager.color("" + completedColor + symbol), progressBars)
                + Strings.repeat(messageManager.color("" + notCompletedColor + symbol), totalBars - progressBars);
    }
}
