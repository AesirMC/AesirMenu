package me.taylan.utils;


import me.taylan.AesirMenu;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerUtility {
    private final AesirMenu plugin;

    private final Map<UUID, PlayerStats> playerMemory = new HashMap<>();

    public PlayerUtility(AesirMenu plugin) {
        this.plugin = plugin;
    }

    public PlayerStats getPlayerStats(OfflinePlayer p) {
        if (!playerMemory.containsKey(p.getUniqueId())) {
            PlayerStats ps = new PlayerStats();
            playerMemory.put(p.getUniqueId(), ps);
            return ps;
        }
        return playerMemory.get(p.getUniqueId());
    }

    public PlayerStats getPlayerStats(Player p) {
        if (!playerMemory.containsKey(p.getUniqueId())) {
            PlayerStats ps = new PlayerStats();
            playerMemory.put(p.getUniqueId(), ps);
            return ps;
        }
        return playerMemory.get(p.getUniqueId());
    }

    public void setPlayerStats(OfflinePlayer p, PlayerStats ps) {
        if (ps == null) playerMemory.remove(p.getUniqueId());
        else playerMemory.put(p.getUniqueId(), ps);
    }

    public void setPlayerStats(Player p, PlayerStats ps) {
        if (ps == null) playerMemory.remove(p.getUniqueId());
        else playerMemory.put(p.getUniqueId(), ps);
    }


    public void autoSaveNull(Player player, PlayerStats playerStats) {
        File file = new File("plugins/AesirMenu/playerdata", player.getUniqueId() + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        cfg.set("Stats.Menu", playerStats.isMenuAktif());
        try {
            cfg.save(file);
        } catch (IOException var11) {
            var11.printStackTrace();

        }

        setPlayerStats(player, null);

    }

    public void autoSave(Player player, PlayerStats playerStats) {
        File file = new File("plugins/AesirMenu/playerdata", player.getUniqueId() + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        cfg.set("Stats.Menu", playerStats.isMenuAktif());
        try {
            cfg.save(file);
        } catch (IOException var11) {
            var11.printStackTrace();

        }

    }


}
