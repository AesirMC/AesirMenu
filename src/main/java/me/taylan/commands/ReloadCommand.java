package me.taylan.commands;


import me.taylan.AesirMenu;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {

    private final AesirMenu aesirMenu;

    public ReloadCommand(AesirMenu aesirMenu) {
        this.aesirMenu = aesirMenu;
        aesirMenu.getCommand("menureload").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.isOp()) {
                if (args.length == 0) {
                    aesirMenu.reloadConfig();
                    sender.sendMessage(MiniMessage.miniMessage().deserialize("             <aqua><bold><<<  Aesir MENU 1.0.0  >>>"));
                    sender.sendMessage(MiniMessage.miniMessage().deserialize("<green><i:false>✔ <gray>config.yml dosyası tekrardan yüklendi. "));
                }
            }
        }
        return false;
    }

}
