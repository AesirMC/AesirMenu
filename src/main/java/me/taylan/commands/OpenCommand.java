package me.taylan.commands;


import me.taylan.AesirMenu;
import me.taylan.utils.PlayerStats;
import me.taylan.utils.PlayerUtility;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OpenCommand implements CommandExecutor {

    private final AesirMenu aesirMenu;
    private final PlayerUtility playerUtility;

    public OpenCommand(AesirMenu aesirMenu) {
        this.aesirMenu = aesirMenu;
        this.playerUtility = aesirMenu.getPlayerUtility();
        aesirMenu.getCommand("menuac").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerStats stats = playerUtility.getPlayerStats(player);
            if (!stats.isMenuAktif()) {
                stats.setMenuAktifligi(true);
                player.sendMessage(MiniMessage.miniMessage().deserialize(aesirMenu.getConfig().getString("menuerisimmesaj")));
            } else {
                player.sendMessage(MiniMessage.miniMessage().deserialize("<gray>[<dark_aqua>Aesir<gray>] <green>Zaten menü erişiminiz var!"));

            }
        }
        return false;
    }

}
