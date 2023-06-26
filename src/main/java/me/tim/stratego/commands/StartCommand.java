package me.tim.stratego.commands;

import me.tim.stratego.Stratego;
import me.tim.stratego.Utils;
import me.tim.stratego.card.StrategoCard;
import me.tim.stratego.teams.Team;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

public class StartCommand implements CommandExecutor {

    private final Stratego plugin;

    public StartCommand(Stratego plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.getInventory().clear();
            Team team = plugin.getPlayerTeam(player);
            if (team == null) {
                player.sendMessage(ChatColor.RED + "You must be in a team to execute this command");
                return true;
            }

            StrategoCard strategoCard = team.getRandomAvailableCard();
            plugin.setPlayerCard(player, strategoCard);

            player.getInventory().setItem(4, Utils.createItemStack(team.getBanner(), team.getColor() + "" + ChatColor.BOLD + team.getName() + " Flag"));
            player.getInventory().setItem(0, Utils.createItemStackWithLore(Material.STONE_SWORD, ChatColor.AQUA + "" + ChatColor.BOLD + "Attack" + ChatColor.GRAY + " (Left Click)",
                    Arrays.asList(ChatColor.GRAY + "Hit an enemy to engage in a fight",
                            "",
                            ChatColor.GRAY + "If your card is a " + ChatColor.GREEN + "" + ChatColor.BOLD + "HIGHER" + ChatColor.GRAY + " power than",
                            ChatColor.GRAY + "your oponents power, your opponent will die.",
                            ChatColor.GRAY + "If your card has " + ChatColor.RED + "" + ChatColor.BOLD + "LESS" + ChatColor.GRAY + " power than",
                            ChatColor.GRAY + "your opponent, you will die.")));
            player.getInventory().setItem(7, Utils.createItemStackWithLore(Material.ECHO_SHARD, ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Change Card" + ChatColor.GRAY + " (Right Click)", Arrays.asList(
                    ChatColor.GRAY + "Refreshes on kill",
                    ChatColor.GRAY + "Cycles after a kill: " + ChatColor.AQUA + "" + ChatColor.BOLD + "2",
                    "")));
           Utils.updateCard(player, strategoCard);


        } else {
            sender.sendMessage("Only players are allowed to execute this command");
        }
        return true;
    }
}
