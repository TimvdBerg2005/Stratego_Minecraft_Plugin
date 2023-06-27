package me.tim.stratego.listeners;

import me.tim.stratego.Stratego;
import me.tim.stratego.manager.GameManager;
import me.tim.stratego.teams.Team;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
    private final GameManager gameManager;
    public BlockPlaceListener(Stratego plugin) {
        this.gameManager = plugin.getGameManager();
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
            return;
        }
        event.setCancelled(true);
        Block placedBlock = event.getBlockPlaced();
        Material blockType = placedBlock.getType();
        Player player = event.getPlayer();

        if (blockType.name().toLowerCase().contains("banner")) {
            Team team = gameManager.getPlayerTeam(player);
            if (team == null) {
                player.sendMessage(ChatColor.RED + "You must be in a team to move the flag");
                return;
            }
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aSuccesfully moved the flag to: " + ChatColor.GRAY + placedBlock.getLocation().getX() + ", " + placedBlock.getLocation().getY() + "!"));
            team.moveFlag(event.getBlock().getLocation().toCenterLocation().add(0, -1, 0));
        }
    }
}

