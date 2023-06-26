package me.tim.stratego.commands;

import me.tim.stratego.Stratego;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AmIInSpawnCommand implements CommandExecutor {

    private final Stratego plugin;

    public AmIInSpawnCommand(Stratego plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (plugin.isPlayerInSpawn(player)) {
                player.sendMessage(ChatColor.GREEN + "You are in the spawn");
            } else {
                player.sendMessage(ChatColor.RED + "You are not in the spawn");
            }

        } else {
            sender.sendMessage("Only players are allowed to use this command");
        }
        return true;
    }
}
