package me.tim.stratego.commands;

import me.tim.stratego.Stratego;
import me.tim.stratego.teams.Team;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ShowTeamsCommand implements CommandExecutor {
    private final Stratego plugin;

    public ShowTeamsCommand(Stratego plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage(ChatColor.GREEN + "Teams: ");
            player.sendMessage(ChatColor.GRAY + "------------------");
            for (Team team : plugin.getTeams()) {
                player.sendMessage(team.getColor() + " " + team.getName());
                for (Player players : team.getPlayers()) {
                    player.sendMessage(ChatColor.GRAY + " - " + team.getColor() + players.getName());
                }
                player.sendMessage(ChatColor.GRAY + "------------------");
            }
         } else {
            sender.sendMessage("Only players are allowed to execute this command.");
        }
        return true;
    }
}
