package me.tim.stratego.tasks;

import me.tim.stratego.Stratego;
import me.tim.stratego.manager.GameManager;
import me.tim.stratego.manager.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class FlagHoldTask extends BukkitRunnable {

    private final GameManager gameManager;

    public FlagHoldTask(Stratego plugin) {
        this.gameManager = plugin.getGameManager();
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getInventory().getItemInOffHand().getType().name().toLowerCase().contains("wool")) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 20, 0));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 0));
                if (gameManager.isPlayerInSpawn(player)) {
                    gameManager.setGameState(GameState.WON);
                    player.getInventory().setItem(EquipmentSlot.OFF_HAND, new ItemStack(Material.AIR));
                }
            }
        }
    }
}
