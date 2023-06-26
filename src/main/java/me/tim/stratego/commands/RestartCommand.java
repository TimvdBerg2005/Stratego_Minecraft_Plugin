package me.tim.stratego.commands;

import org.bukkit.command.CommandExecutor;
import me.tim.stratego.Stratego;
import me.tim.stratego.teams.Team;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RestartCommand implements CommandExecutor {
    private final Stratego plugin;

    public RestartCommand(Stratego plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.isOp()) {
                for (Team team : plugin.getTeams()) {
                    team.refillCards();
                    team.resetFlag();
                    team.clearPlayers();
                }
                plugin.clearPlayerCards();
            } else {
                player.sendMessage("You are not allowed to execute this command");
            }
        } else {
            sender.sendMessage("Only players are allowed to execute this command");
        }
        return true;
    }
}

