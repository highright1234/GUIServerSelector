package com.github.highright1234.guiserverselector;

import io.github.leonardosnt.bungeechannelapi.BungeeChannelApi;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.projecttl.api.inventorygui.InventoryBuilder;
import org.projecttl.api.inventorygui.utils.InventoryType;

import java.util.Objects;

public class SelectorCommand implements CommandExecutor {

    private final GUIServerSelector plugin;

    public SelectorCommand(GUIServerSelector plugin) {
        this.plugin=plugin;
    }

    public void openInventory(Player player) {
        BungeeChannelApi api = new BungeeChannelApi(plugin);
        InventoryBuilder builder = new InventoryBuilder(Component.text("Selector"), InventoryType.CHEST_9);
        api.getServers().whenComplete((strings, throwable) -> {
            for (String serverName : strings) {
                ItemStack itemStack = new ItemStack(Material.DIAMOND, 0);
                ItemMeta itemMeta = itemStack.getItemMeta();
                Objects.requireNonNull(itemMeta).setDisplayName(serverName);
                itemStack.setItemMeta(itemMeta);
                builder.setItem(strings.indexOf(serverName), itemStack);
            }
            builder.registerListener(0, event -> {
                event.setCancelled(true);
                api.connect(player, Objects.requireNonNull(
                        Objects.requireNonNull(
                                event.getCurrentItem()).getItemMeta()).getDisplayName());
            });
            Inventory inventory = builder.build();
            player.openInventory(inventory);
        });
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            openInventory(((Player) sender));
            return true;
        }
        return false;
    }
}
