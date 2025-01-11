package me.taylan.commands;


import me.taylan.AesirMenu;
import me.taylan.utils.PlayerHeads;
import me.taylan.utils.PlayerStats;
import me.taylan.utils.PlayerUtility;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class MenuItemCommand implements CommandExecutor {

    private final AesirMenu aesirMenu;
    private final PlayerUtility playerUtility;

    public MenuItemCommand(AesirMenu aesirIstila) {
        this.aesirMenu = aesirIstila;
        this.playerUtility = aesirIstila.getPlayerUtility();
        aesirIstila.getCommand("menuitem").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerStats stats = playerUtility.getPlayerStats(player);
            if (stats.isMenuAktif()) {
                if (player.isOp()) {
                    NamespacedKey key = new NamespacedKey(aesirMenu, "star");
                    if (args.length == 0) {
                        ItemStack itemStack;
                        if (aesirMenu.getConfig().getString("Material").equalsIgnoreCase("PLAYER_HEAD")) {
                            itemStack = PlayerHeads.getSkull(aesirMenu.getConfig().getString("HeadUrl"));
                        } else {
                            itemStack = new ItemStack(Material.valueOf(aesirMenu.getConfig().getString("Material")));
                        }
                        if (itemStack == null) {
                            sender.sendMessage(MiniMessage.miniMessage().deserialize("<gray><i:false>[<aqua>Aesir<gray>] <red>Bir hata oluştu."));
                            return false;
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
                    } else if (args.length == 2) {
                        if (args[0].equalsIgnoreCase("ver")) {
                            Player player1 = Bukkit.getPlayerExact(args[1]);
                            ItemStack itemStack;
                            if (aesirMenu.getConfig().getString("Material").equalsIgnoreCase("PLAYER_HEAD")) {
                                itemStack = PlayerHeads.getSkull(aesirMenu.getConfig().getString("HeadUrl"));
                            } else {
                                itemStack = new ItemStack(Material.valueOf(aesirMenu.getConfig().getString("Material")));
                            }
                            if (itemStack == null) {
                                sender.sendMessage(MiniMessage.miniMessage().deserialize("<gray><i:false>[<aqua>Aesir<gray>] <red>Bir hata oluştu."));
                                return false;
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
                            player1.getInventory().setItem(8, itemStack);
                        }
                    } else {
                        sender.sendMessage(MiniMessage.miniMessage().deserialize("<gray><i:false>[<aqua>Aesir<gray>] <red>Yanlış kullanım."));
                    }
                } else {
                    NamespacedKey key = new NamespacedKey(aesirMenu, "star");
                    ItemStack itemStack;
                    if (aesirMenu.getConfig().getString("Material").equalsIgnoreCase("PLAYER_HEAD")) {
                        itemStack = PlayerHeads.getSkull(aesirMenu.getConfig().getString("HeadUrl"));
                    } else {
                        itemStack = new ItemStack(Material.valueOf(aesirMenu.getConfig().getString("Material")));
                    }
                    if (itemStack == null) {
                        sender.sendMessage(MiniMessage.miniMessage().deserialize("<gray><i:false>[<aqua>Aesir<gray>] <red>Bir hata oluştu."));
                        return false;
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
            } else {
                sender.sendMessage(MiniMessage.miniMessage().deserialize("<gray><i:false>[<aqua>Aesir<gray>] <red>Menüye erişiminiz kapalı! Menüye erişimi açmak için /menuac komudunu kullanın."));

            }
        }
        return false;
    }

}
