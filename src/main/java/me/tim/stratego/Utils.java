package me.tim.stratego;

import me.tim.stratego.card.StrategoCard;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class Utils {

    public static void updateCard(Player player, StrategoCard card) {
        String cardName = card.getName();
        int power = card.getPower();
        player.getInventory().setItem(8, Utils.createItemStackWithLore(Material.AMETHYST_SHARD, ChatColor.GRAY + "Selected Card: " + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + cardName, Arrays.asList(ChatColor.GRAY + "Power: " + ChatColor.AQUA + "" + ChatColor.BOLD + power,
                "",
                ChatColor.DARK_GRAY + "--------------",
                ChatColor.DARK_AQUA + "Stats: ",
                ChatColor.GRAY + "  Kills: " + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "5",
                ChatColor.GRAY + "  Deaths: " + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "0",
                ChatColor.DARK_GRAY + "--------------",
                "",
                ChatColor.GRAY + "Level: " + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "2",
                ChatColor.GRAY + "  Experience: " + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "125",
                ChatColor.GRAY + "  Needed EXP: " + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "25",
                "",
                ChatColor.AQUA + "--------------",
                ChatColor.GRAY + "Prestige: " + ChatColor.AQUA + "" + ChatColor.BOLD + "Diamond",
                "")));
    }
    public static ItemStack createItemStack(Material material, String name) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (material == Material.STONE_SWORD) {
            itemMeta.setUnbreakable(true);
        }
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack createItemStackWithLore(Material material, String name, List<String> lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
