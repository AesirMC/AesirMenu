package me.taylan.commands;


import me.taylan.AesirMenu;
import me.taylan.utils.PlayerStats;
import me.taylan.utils.PlayerUtility;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class CloseCommand implements CommandExecutor {

    private final AesirMenu aesirMenu;
    private final PlayerUtility playerUtility;

    public CloseCommand(AesirMenu aesirMenu) {
        this.aesirMenu = aesirMenu;
        this.playerUtility = aesirMenu.getPlayerUtility();
        aesirMenu.getCommand("menukapat").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerStats stats = playerUtility.getPlayerStats(player);
            NamespacedKey star = new NamespacedKey(aesirMenu, "star");
            if (stats.isMenuAktif()) {
                for (ItemStack item : player.getInventory()) {
                    if (item != null && item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(star, PersistentDataType.STRING)) {
                        player.getInventory().removeItem(item);
                        stats.setMenuAktifligi(false);
                        player.sendMessage(MiniMessage.miniMessage().deserialize(aesirMenu.getConfig().getString("menukapanismesaj")));
                        return true;
                    }
                }
            } else {
                player.sendMessage(MiniMessage.miniMessage().deserialize("<gray>[<dark_aqua>Aesir<gray>] <red>Zaten menü erişiminiz yok!."));
            }
        }
        return false;
    }

}
