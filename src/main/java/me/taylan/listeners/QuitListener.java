//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.taylan.listeners;


import me.taylan.AesirMenu;
import me.taylan.utils.PlayerStats;
import me.taylan.utils.PlayerUtility;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {
    private final AesirMenu plugin;

    private final PlayerUtility playerUtility;

    public QuitListener(AesirMenu plugin) {
        this.plugin = plugin;
        this.playerUtility = plugin.getPlayerUtility();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onplayerquitstat(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerStats playerStats = playerUtility.getPlayerStats(player);
        playerUtility.autoSaveNull(player, playerStats);

    }


}
