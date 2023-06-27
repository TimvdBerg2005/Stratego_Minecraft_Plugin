package me.tim.stratego.commands;

import me.tim.stratego.manager.GameManager;
import me.tim.stratego.manager.GameState;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GameStateCommand implements CommandExecutor {
    private final GameManager gameManager;

    public GameStateCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                String gameStateString = args[0];
                switch (gameStateString) {
                    case "prelobby":
                        gameManager.setGameState(GameState.PRELOBBY);
                        break;
                    case "teamselect":
                        gameManager.setGameState(GameState.TEAM_SELECT);
                        break;
                    case "active":
                        gameManager.setGameState(GameState.ACTIVE);
                        break;
                    case "won":
                        gameManager.setGameState(GameState.WON);
                        break;
                    case "restarting":
                        gameManager.setGameState(GameState.RESTARTING);
                        break;
                    default:
                        player.sendMessage(ChatColor.RED + "Invalid GameState: prelobby, teamselect, active, won, restarting");

                }
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /gamestate <gamestate>");
            }
        } else {
            sender.sendMessage("Only players are allowed to execute this command");
        }
        return true;
    }
}
