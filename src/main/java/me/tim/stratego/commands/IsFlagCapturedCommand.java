package me.tim.stratego.commands;

import org.bukkit.command.CommandExecutor;
import me.tim.stratego.Stratego;
import me.tim.stratego.teams.Team;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class IsFlagCapturedCommand implements CommandExecutor {
    private final Stratego plugin;

    public IsFlagCapturedCommand(Stratego plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Team team = plugin.getPlayerTeam(player);
            assert team != null;
            player.sendMessage(ChatColor.GREEN + "Flag captured: " + team.isFlagCaptured());
        } else {
            sender.sendMessage("Only players are allowed to execute this command.");
        }
        return true;
    }
}
