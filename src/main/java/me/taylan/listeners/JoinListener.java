package me.taylan.listeners;

import me.taylan.AesirMenu;
import me.taylan.utils.PlayerHeads;
import me.taylan.utils.PlayerStats;
import me.taylan.utils.PlayerUtility;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JoinListener implements Listener {

    private final AesirMenu aesirMenu;
    private final PlayerUtility playerUtility;

    public JoinListener(AesirMenu aesirMenu) {
        this.aesirMenu = aesirMenu;
        this.playerUtility = aesirMenu.getPlayerUtility();
        Bukkit.getPluginManager().registerEvents(this, aesirMenu);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event) {
        NamespacedKey star = new NamespacedKey(aesirMenu, "star");
        for (Iterator<ItemStack> iterator = event.getDrops().iterator(); iterator.hasNext(); ) {
            ItemStack item = iterator.next();
            if (item.hasItemMeta() && item != null && item.getItemMeta().getPersistentDataContainer().has(star, PersistentDataType.STRING)) {
                iterator.remove();
                event.getItemsToKeep().add(item);
            }

        }
    }

    @EventHandler
    public void on(PlayerInteractEntityEvent e) {
        NamespacedKey star = new NamespacedKey(aesirMenu, "star");
        if (e.getRightClicked() instanceof ItemFrame) {
            ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
            if (item.hasItemMeta() && item != null && item.getItemMeta().getPersistentDataContainer().has(star, PersistentDataType.STRING)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onClickKnowledge(PlayerJoinEvent event) {
        NamespacedKey key = new NamespacedKey(aesirMenu, "star");
        Player player = event.getPlayer();
        PlayerStats stats = new PlayerStats();
        File file = new File("plugins/AesirMenu/playerdata", player.getUniqueId() + ".yml");
        if (file.exists()) {
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            stats.setMenuAktifligi(cfg.getBoolean("Stats.Menu"));
        } else {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "is admin disband " + player.getName());
            aesirMenu.getServer().getScheduler().runTaskLater(aesirMenu, () -> {
                if (player != null) {
                    player.performCommand("is");
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent addtemp vipplus 3d");
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mi give material MADENCISEKERI " + player.getName() + " 32");
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mi give CONSUMABLE PARA_2500 " + player.getName());
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "mi give CONSUMABLE XP20 " + player.getName() + " 2");
                } else {
                    Bukkit.getConsoleSender().sendMessage("player null");
                }
            }, 60);

        }
        playerUtility.setPlayerStats(player, stats);
        if (stats.isMenuAktif()) {
            if (player.getInventory().getItem(8) == null) {
                ItemStack itemStack;
                if (aesirMenu.getConfig().getString("Material").equalsIgnoreCase("PLAYER_HEAD")) {
                    itemStack = PlayerHeads.getSkull(aesirMenu.getConfig().getString("HeadUrl"));
                } else {
                    itemStack = new ItemStack(Material.valueOf(aesirMenu.getConfig().getString("Material")));
                }
                if (itemStack == null) {
                    aesirMenu.getServer().getConsoleSender().sendMessage(MiniMessage.miniMessage().deserialize("<gray><i:false>[<aqua>AesirMenu<gray>] <red>Bir hata oluştu."));
                    return;
                }
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(aesirMenu.getConfig().getInt("CustomModelData"));
                itemMeta.displayName(MiniMessage.miniMessage().deserialize(aesirMenu.getConfig().getString("name")));
                List<String> loreList = aesirMenu.getConfig().getStringList("lore");
                List<Component> loreRealList = new ArrayList<>();
                for (String string : loreList) {
                    loreRealList.add(MiniMessage.miniMessage().deserialize(string));
                }
                itemMeta.lore(loreRealList);
                PersistentDataContainer container = itemMeta.getPersistentDataContainer();
                container.set(key, PersistentDataType.STRING, "star");
                if (aesirMenu.getConfig().getBoolean("Ench")) {
                    itemMeta.addEnchant(Enchantment.DEPTH_STRIDER, 1, true);
                }
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                itemStack.setItemMeta(itemMeta);
                player.getInventory().setItem(8, itemStack);
            } else if (player.getInventory().getItem(8).hasItemMeta()) {
                ItemStack itemStack;
                if (aesirMenu.getConfig().getString("Material").equalsIgnoreCase("PLAYER_HEAD")) {
                    itemStack = PlayerHeads.getSkull(aesirMenu.getConfig().getString("HeadUrl"));
                } else {
                    itemStack = new ItemStack(Material.valueOf(aesirMenu.getConfig().getString("Material")));
                }
                if (itemStack == null) {
                    aesirMenu.getServer().getConsoleSender().sendMessage(MiniMessage.miniMessage().deserialize("<gray><i:false>[<aqua>AesirMenu<gray>] <red>Bir hata oluştu."));
                    return;
                }
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(aesirMenu.getConfig().getInt("CustomModelData"));
                itemMeta.displayName(MiniMessage.miniMessage().deserialize(aesirMenu.getConfig().getString("name")));
                List<String> loreList = aesirMenu.getConfig().getStringList("lore");
                List<Component> loreRealList = new ArrayList<>();
                for (String string : loreList) {
                    loreRealList.add(MiniMessage.miniMessage().deserialize(string));
                }
                itemMeta.lore(loreRealList);
                PersistentDataContainer container = itemMeta.getPersistentDataContainer();
                container.set(key, PersistentDataType.STRING, "star");
                if (aesirMenu.getConfig().getBoolean("Ench")) {
                    itemMeta.addEnchant(Enchantment.DEPTH_STRIDER, 1, true);
                }
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                itemStack.setItemMeta(itemMeta);
                player.getInventory().setItem(8, itemStack);
            } else {
                ItemStack itemStack;
                if (aesirMenu.getConfig().getString("Material").equalsIgnoreCase("PLAYER_HEAD")) {
                    itemStack = PlayerHeads.getSkull(aesirMenu.getConfig().getString("HeadUrl"));
                } else {
                    itemStack = new ItemStack(Material.valueOf(aesirMenu.getConfig().getString("Material")));
                }
                if (itemStack == null) {
                    aesirMenu.getServer().getConsoleSender().sendMessage(MiniMessage.miniMessage().deserialize("<gray><i:false>[<aqua>AesirMenu<gray>] <red>Bir hata oluştu."));
                    return;
                }
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(aesirMenu.getConfig().getInt("CustomModelData"));
                itemMeta.displayName(MiniMessage.miniMessage().deserialize(aesirMenu.getConfig().getString("name")));
                List<String> loreList = aesirMenu.getConfig().getStringList("lore");
                List<Component> loreRealList = new ArrayList<>();
                for (String string : loreList) {
                    loreRealList.add(MiniMessage.miniMessage().deserialize(string));
                }
                itemMeta.lore(loreRealList);
                PersistentDataContainer container = itemMeta.getPersistentDataContainer();
                container.set(key, PersistentDataType.STRING, "star");
                if (aesirMenu.getConfig().getBoolean("Ench")) {
                    itemMeta.addEnchant(Enchantment.DEPTH_STRIDER, 1, true);
                }
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                itemStack.setItemMeta(itemMeta);
                player.getInventory().setItem(8, itemStack);
            }
        }
    }

}




