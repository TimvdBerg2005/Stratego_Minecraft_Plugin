package me.tim.stratego.listeners;

import me.tim.stratego.Stratego;
import me.tim.stratego.teams.Team;
import org.bukkit.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ArmorStandClickListener implements Listener {

    private final Stratego plugin;

    public ArmorStandClickListener(Stratego plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerArmorStandManipulateEvent(PlayerArmorStandManipulateEvent event) {
        Player player = event.getPlayer();
        ArmorStand armorStand = event.getRightClicked();
        event.setCancelled(true);
        Team targetTeam = null;
        for (Team team : plugin.getTeams()) {
            if (team.getName().equals(armorStand.getName())) {
                targetTeam = team;
            }
        }

        if (targetTeam == null) {
            player.sendMessage(ChatColor.RED + "The target team was not found");
        } else {
            if (plugin.getPlayerTeam(player) == targetTeam) {
                player.sendMessage(ChatColor.RED + "You cannot capture your own flag!");
                return;
            }
            targetTeam.moveFlagToDefault();
            player.sendMessage(ChatColor.GREEN + "You have captured the " + targetTeam.getColor() + targetTeam.getName() + ChatColor.GREEN + " flag!");
            player.getInventory().setItem(EquipmentSlot.OFF_HAND, targetTeam.getWoolBlock());
            targetTeam.setFlagCaptured(true);
        }
    }
}
