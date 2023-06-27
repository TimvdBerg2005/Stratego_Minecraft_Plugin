package me.tim.stratego.listeners;

import me.tim.stratego.Stratego;
import me.tim.stratego.Utils;
import me.tim.stratego.card.StrategoCard;
import me.tim.stratego.manager.GameManager;
import me.tim.stratego.teams.Team;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ChangeCardClickListener implements Listener {

    private final Stratego plugin;
    private final GameManager gameManager;

    public ChangeCardClickListener(Stratego plugin) {
        this.plugin = plugin;
        this.gameManager = plugin.getGameManager();
    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Material clickedType = event.getMaterial();
            if (clickedType == Material.AIR) {
                return;
            }
            if (clickedType == Material.ECHO_SHARD) {
                if (gameManager.isPlayerInSpawn(player)) {
                    Team team = gameManager.getPlayerTeam(player);
                    if (team == null) {
                        player.sendMessage(ChatColor.RED + "You must be in a team to use this");
                        return;
                    }
                    if (gameManager.canSwapCard(player)) {
                        gameManager.changePlayerCard(player);
                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou can only swap cards after a kill"));
                    }

                } else {
                    player.sendMessage(ChatColor.RED + "You must be in spawn to change cards");
                }
            }
        }
    }
}
