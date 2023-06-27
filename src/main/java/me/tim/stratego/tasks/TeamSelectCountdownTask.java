package me.tim.stratego.tasks;

import me.tim.stratego.manager.GameManager;
import me.tim.stratego.manager.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class TeamSelectCountdownTask extends BukkitRunnable {

    private GameManager gameManager;

    public TeamSelectCountdownTask(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    private int timeLeft = 15;

    @Override
    public void run() {
        timeLeft--;
        if (timeLeft <= 0) {
            if (gameManager.getTeams().get(0).getPlayers().isEmpty() || gameManager.getTeams().get(1).getPlayers().isEmpty()) {
                Bukkit.broadcastMessage(ChatColor.RED + "Not enough players to start");
                timeLeft = 15;
                return;
            } else {
                gameManager.setGameState(GameState.ACTIVE);
                this.cancel();
            }
        }

        Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + timeLeft + ChatColor.DARK_AQUA + " more seconds to select a team!");

    }
}
