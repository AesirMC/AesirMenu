package me.taylan.listeners;

import me.taylan.AesirMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class MenuItemClickListener implements Listener {

    private final AesirMenu aesirMenu;

    public MenuItemClickListener(AesirMenu aesirMenu) {
        this.aesirMenu = aesirMenu;
        Bukkit.getPluginManager().registerEvents(this, aesirMenu);
    }

    @EventHandler
    public void onClickKnowledge(PlayerSwapHandItemsEvent event) {
        NamespacedKey star = new NamespacedKey(aesirMenu, "star");
        Player player = event.getPlayer();
        if (event.getOffHandItem() != null && event.getOffHandItem().hasItemMeta() && event.getOffHandItem().getItemMeta().getPersistentDataContainer() != null && event.getOffHandItem().getItemMeta().getPersistentDataContainer().has(star, PersistentDataType.STRING)) {
            event.setCancelled(true);
            player.sendMessage("swap");
            player.performCommand(aesirMenu.getConfig().getString("Komut"));
            player.playSound(player, Sound.UI_LOOM_SELECT_PATTERN, 2.0F, 1.2F);
        }

    }

    @EventHandler
    public void onClickKnowledge(InventoryClickEvent event) {
        NamespacedKey star = new NamespacedKey(aesirMenu, "star");
        Player player = (Player) event.getWhoClicked();
        if (event.getWhoClicked().getItemOnCursor().getType() != Material.AIR && event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().getPersistentDataContainer().has(star, PersistentDataType.STRING)) {
            event.setCancelled(true);
            if (event.getWhoClicked().getItemOnCursor().hasItemMeta() && event.getWhoClicked().getItemOnCursor().getItemMeta().getPersistentDataContainer().has(star)) {
                player.setItemOnCursor(null);
                event.setCurrentItem(null);
                player.sendMessage("clickevent 1");
            }

            player.performCommand(aesirMenu.getConfig().getString("Komut"));
            player.playSound(player, Sound.UI_LOOM_SELECT_PATTERN, 2.0F, 1.2F);
        }
        if (event.getWhoClicked() instanceof Player && event.getClickedInventory() != null) {
            List<ItemStack> items = new ArrayList<>();
            items.add(event.getCurrentItem());
            items.add(event.getCursor());
            items.add((event.getClick() == org.bukkit.event.inventory.ClickType.NUMBER_KEY) ? event.getWhoClicked().getInventory().getItem(event.getHotbarButton()) : event.getCurrentItem());
            for (ItemStack item : items) {
                if (item != null && item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(star)) {
                    event.setCancelled(true);
                    player.sendMessage("clickevent 2");
                }
            }
        }
    }


    @EventHandler
    public void dropbook(PlayerDropItemEvent event) {
        NamespacedKey star = new NamespacedKey(aesirMenu, "star");
        Player player = event.getPlayer();
        ItemStack item = event.getItemDrop().getItemStack();
        if (item.hasItemMeta() && item.getType() != Material.AIR && item.getItemMeta().getPersistentDataContainer().has(star, PersistentDataType.STRING)) {
            event.setCancelled(true);
            player.sendMessage("dropevent");
            player.performCommand(aesirMenu.getConfig().getString("Komut"));
            player.playSound(player, Sound.UI_LOOM_SELECT_PATTERN, 2.0F, 1.2F);
        }

    }

    @EventHandler
    public void clickbook(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        NamespacedKey star = new NamespacedKey(aesirMenu, "star");
        if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) &&
                player.getInventory().getItemInMainHand().hasItemMeta() && player.getInventory().getItemInMainHand().getType() != Material.AIR && player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(star, PersistentDataType.STRING)) {
            event.setCancelled(true);
            player.performCommand(aesirMenu.getConfig().getString("Komut"));
            player.sendMessage("interact");
            player.playSound(player, Sound.UI_LOOM_SELECT_PATTERN, 2.0F, 1.2F);
        }

    }

}
