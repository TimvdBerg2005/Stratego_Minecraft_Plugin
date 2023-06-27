package me.tim.stratego.listeners;

import me.tim.stratego.card.FightResult;
import me.tim.stratego.card.StrategoCard;
import me.tim.stratego.manager.GameManager;
import me.tim.stratego.manager.GameState;
import me.tim.stratego.teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamageListener implements Listener {

    private final GameManager gameManager;

    public PlayerDamageListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onPlayerDamageByPlayer(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            if (gameManager.gameState != GameState.ACTIVE) {
                return;
            }
            Player damager = (Player) event.getDamager();
            Player player = (Player) event.getEntity();
            Team damagerTeam = gameManager.getPlayerTeam(damager);
            Team playerTeam = gameManager.getPlayerTeam(player);

            if (damagerTeam == null || playerTeam == null) {
                return;
            }

            ChatColor damagerColor = damagerTeam.getColor();
            ChatColor playerColor = playerTeam.getColor();

            StrategoCard damagerCard = gameManager.getCurrentCard(damager);
            StrategoCard playerCard = gameManager.getCurrentCard(player);

            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                    damagerColor + damager.getName() + "  &7-- &b&l⚔ &7--  " +
                           playerColor + player.getName()));

            switch (damagerCard.canBeat(playerCard)) {
                case WON:
                    gameManager.respawnPlayer(player);
                    gameManager.allowCardSwap(damager);
                    Bukkit.broadcastMessage(damagerColor + damager.getName() + damagerColor + "'s " +
                            ChatColor.GRAY + "card had a higher power than " + playerColor + player.getName() + playerColor + "'s");
                    break;
                case DRAW:
                    gameManager.respawnPlayer(player);
                    gameManager.respawnPlayer(damager);
                    Bukkit.broadcastMessage(damagerColor + damager.getName() + damagerColor + "'s " +
                            ChatColor.GRAY + "card had the same power as " + playerColor + player.getName() + playerColor + "'s");
                    break;
                case LOST:
                    gameManager.respawnPlayer(damager);
                    gameManager.allowCardSwap(player);
                    Bukkit.broadcastMessage(damagerColor + damager.getName() + damagerColor + "'s " +
                            ChatColor.GRAY + "card had a lower power than " + playerColor + player.getName() + playerColor + "'s");
                    break;
            }
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setCancelled(true);
            Player player = (Player) event.getEntity();
            player.setFoodLevel(20);
            player.setSaturation(20);
        }
    }
}
