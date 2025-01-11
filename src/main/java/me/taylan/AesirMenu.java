package me.taylan;

import me.taylan.commands.CloseCommand;
import me.taylan.commands.MenuItemCommand;
import me.taylan.commands.OpenCommand;
import me.taylan.commands.ReloadCommand;
import me.taylan.listeners.JoinListener;
import me.taylan.listeners.MenuItemClickListener;
import me.taylan.listeners.QuitListener;
import me.taylan.utils.PlayerUtility;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class RemielMenu extends JavaPlugin {
    private static RemielMenu instance;
    private PlayerUtility playerUtility;

    public PlayerUtility getPlayerUtility() {
        return playerUtility;
    }

    public RemielMenu() {
        instance = this;
    }

    public static RemielMenu getInstance() {
        return instance;
    }
    @Override
    public void onEnable() {
        File config = new File("plugins/RemielMenu", "config.yml");
        if (!config.exists()) {
            saveDefaultConfig();
        }
        File playerData = new File(getDataFolder(), "playerdata");
        if (!playerData.exists()) {
            playerData.mkdirs();
        }
        playerUtility = new PlayerUtility(this);
        new CloseCommand(this);
        new OpenCommand(this);
        new JoinListener(this);
        new QuitListener(this);
        new MenuItemClickListener(this);
        new MenuItemCommand(this);
        new ReloadCommand(this);
        getServer().getScheduler().runTaskTimerAsynchronously(instance, task2 -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                playerUtility.autoSave(player, playerUtility.getPlayerStats(player));
            }
            send(" ");
            send("<yellow> RemielMenu Dataları Kaydedildi!");
            send(" ");
        }, 0, 5000);
        send("-------------------------------");
        send("<gray> RemielMenu <green>Aktif!");
        send("<gray> Versiyon: <yellow>1.0.0");
        send("<gray> Yapımcı: <green>Taylan");
        send("-------------------------------");

    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            playerUtility.autoSaveNull(player, playerUtility.getPlayerStats(player));
        }
        send(" ");
        send("<yellow> RemielMenu Dataları Kaydedildi!");
        send(" ");
        Bukkit.getServer().getScheduler().cancelTasks(this);
        send("-------------------------------");
        send("<gray> RemielMenu <red>Deaktif!");
        send("<gray> Versiyon: <yellow>1.0.0");
        send("<gray> Yapımcı: <green>Taylan");
        send("-------------------------------");
    }

    public void send(String string) {
        getServer().getConsoleSender().sendMessage(MiniMessage.miniMessage().deserialize(string));
    }
}
