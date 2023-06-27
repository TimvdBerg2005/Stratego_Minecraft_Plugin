package me.tim.stratego;

import me.tim.stratego.card.StrategoCard;
import me.tim.stratego.commands.*;
import me.tim.stratego.listeners.*;
import me.tim.stratego.manager.GameManager;
import me.tim.stratego.tasks.FlagHoldTask;
import me.tim.stratego.teams.Team;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public final class Stratego extends JavaPlugin {

    private GameManager gameManager;

    @Override
    public void onEnable() {
        this.gameManager = new GameManager(this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(this), this);
        getServer().getPluginManager().registerEvents(new ArmorStandClickListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
        getServer().getPluginManager().registerEvents(new ChangeCardClickListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDamageListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new TeamSelectorListener(gameManager), this);
        getServer().getPluginManager().registerEvents(new PlayerLoseFoodListener(), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getCommand("blue").setExecutor(new TeamBlueCommand(this));
        getCommand("red").setExecutor(new TeamRedCommand(this));
        getCommand("showteams").setExecutor(new ShowTeamsCommand(this));
        getCommand("availablecards").setExecutor(new AvailableCardsCommand(this));
        getCommand("gamestate").setExecutor(new GameStateCommand(gameManager));
        FlagHoldTask task = new FlagHoldTask(this);
        task.runTaskTimer(this, 0, 10);
    }

    public GameManager getGameManager() {
        return gameManager;
    }
    @Override
    public void onDisable() {
        for (World world : Bukkit.getWorlds()) {
            for (org.bukkit.entity.Entity entity : world.getEntities()) {
                if (entity instanceof ArmorStand) {
                    entity.remove();
                }
            }
        }
    }
}
