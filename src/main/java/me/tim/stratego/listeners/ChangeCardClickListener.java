package me.tim.stratego.listeners;

import me.tim.stratego.Stratego;
import me.tim.stratego.Utils;
import me.tim.stratego.card.StrategoCard;
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

    public ChangeCardClickListener(Stratego plugin) {
        this.plugin = plugin;
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
                if (plugin.isPlayerInSpawn(player)) {
                    Team team = plugin.getPlayerTeam(player);
                    if (team == null) {
                        player.sendMessage(ChatColor.RED + "You must be in a team to use this");
                        return;
                    }
                    StrategoCard currentCard = plugin.getCurrentCard(player);
                    StrategoCard strategoCard = team.replaceRandomCard(currentCard);
                    plugin.setPlayerCard(player, strategoCard);
                    Utils.updateCard(player, strategoCard);

                    String message = ChatColor.DARK_AQUA + "" + ChatColor.BOLD + strategoCard.getName();
                    player.sendActionBar(message);

                } else {
                    player.sendMessage(ChatColor.RED + "You must be in spawn to change cards");
                }
            }
        }
    }
}
