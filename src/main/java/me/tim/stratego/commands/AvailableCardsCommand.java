package me.tim.stratego.commands;

import me.tim.stratego.card.StrategoCard;
import me.tim.stratego.manager.GameManager;
import org.bukkit.command.CommandExecutor;
import me.tim.stratego.Stratego;
import me.tim.stratego.teams.Team;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class AvailableCardsCommand implements CommandExecutor {
    private final GameManager gameManager;

    public AvailableCardsCommand(Stratego plugin) {
        this.gameManager = plugin.getGameManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Team team = gameManager.getPlayerTeam(player);
            assert team != null;
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Cards left for team " + team.getColor() + team.getName()));
            for (Map.Entry<StrategoCard, Integer> entry : team.getAvailableCards().entrySet()) {
                StrategoCard card = entry.getKey();
                int availableCount = entry.getValue();
                String cardName = card.getName();

                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&3&l" + cardName + "&7: &b" + availableCount));
            }
        } else {
            sender.sendMessage("Only players are allowed to execute this command.");
        }
        return true;
    }
}
