package me.snowman.itemmultiply.managers;

import com.google.common.base.Strings;
import me.snowman.itemmultiply.ItemMultiply;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class ItemManager {
    private final MessageManager messageManager = ItemMultiply.messageManager;
    private HashMap<UUID, HashMap<String, Integer>> xp = new HashMap<>();
    private HashMap<UUID, HashMap<String, Integer>> level = new HashMap<>();

    public Integer getXP(Player player, String string) {
        if (xp.get(player.getUniqueId()) == null) {
            return null;
        }
        if (xp.get(player.getUniqueId()).containsKey(string)) {
            return xp.get(player.getUniqueId()).get(string);
        }
        return null;
    }

    public void addXP(Player player, String string) {
        HashMap<String, Integer> perItem = xp.get(player.getUniqueId());
        int XP = perItem.get(string);
        perItem.put(string, XP + 1);
        xp.put(player.getUniqueId(), perItem);
    }

    public void addXP(Player player, String string, int xp) {
        HashMap<String, Integer> perItem = this.xp.get(player.getUniqueId());
        int XP = perItem.get(string);
        perItem.put(string, XP + xp);
        this.xp.put(player.getUniqueId(), perItem);
    }

    public void setXP(Player player, String string, int xp) {
        HashMap<String, Integer> perItem;
        if (this.xp.get(player.getUniqueId()) != null) {
            perItem = this.xp.get(player.getUniqueId());
        } else {
            perItem = new HashMap<>();
        }
        perItem.put(string, xp);
        this.xp.put(player.getUniqueId(), perItem);
    }

    public Integer getLevel(Player player, String string) {
        if (level.get(player.getUniqueId()) == null) {
            return null;
        }
        if (level.get(player.getUniqueId()).containsKey(string)) {
            return level.get(player.getUniqueId()).get(string);
        }
        return null;
    }

    public void addLevel(Player player, String string) {
        HashMap<String, Integer> perItem = level.get(player.getUniqueId());
        int level = perItem.get(string);
        perItem.put(string, level + 1);
        this.level.put(player.getUniqueId(), perItem);
    }

    public void setLevel(Player player, String string, int level) {
        HashMap<String, Integer> perItem;
        if (this.level.get(player.getUniqueId()) != null) {
            perItem = this.level.get(player.getUniqueId());
        } else {
            perItem = new HashMap<>();
        }
        perItem.put(string, level);
        this.level.put(player.getUniqueId(), perItem);
    }

    public HashMap<UUID, HashMap<String, Integer>> getLevel() {
        return level;
    }

    public HashMap<UUID, HashMap<String, Integer>> getXp() {
        return xp;
    }

    public double nextLevel(Player player, String string) {
        double exponent = 2.5;
        double baseXP = 50;
        double level = getLevel(player, string) + 1;
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
