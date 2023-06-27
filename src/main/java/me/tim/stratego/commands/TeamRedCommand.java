package me.tim.stratego.commands;

import me.tim.stratego.Stratego;
import me.tim.stratego.manager.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TeamRedCommand implements CommandExecutor {
    private final GameManager gameManager;

    public TeamRedCommand(Stratego plugin) {
        this.gameManager = plugin.getGameManager();
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                Player targetPlayer = Bukkit.getPlayer(args[0]);
                gameManager.getTeams().get(0).addPlayer(targetPlayer);
                if (targetPlayer == null) {
                    player.sendMessage(ChatColor.RED + "The player was not found");
                    return true;
                }
                player.sendMessage(ChatColor.GREEN + "Added the player " + targetPlayer.getName() + " to team red");
                targetPlayer.setCustomName(ChatColor.RED + targetPlayer.getName());
                targetPlayer.setCustomNameVisible(true);
                targetPlayer.setPlayerListName(ChatColor.RED + player.getName());
            } else {
                sender.sendMessage(ChatColor.RED + "Usage: /blue <Player>");
            }
        } else {
            sender.sendMessage("Only players are allowed to execute this command.");
        }
        return true;
    }
}
