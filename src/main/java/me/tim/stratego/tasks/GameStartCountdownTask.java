package me.tim.stratego.tasks;

import me.tim.stratego.manager.GameManager;
import me.tim.stratego.manager.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class GameStartCountdownTask extends BukkitRunnable {

    private GameManager gameManager;

    public GameStartCountdownTask(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    private int timeLeft = 5;

    @Override
    public void run() {
        timeLeft--;
        if (timeLeft <= 0) {
            cancel();
            gameManager.setGameState(GameState.ACTIVE);
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.getInventory().clear();
            }
            return;
        }

        Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + timeLeft + ChatColor.GREEN + " seconds until game starts!");

    }
}

