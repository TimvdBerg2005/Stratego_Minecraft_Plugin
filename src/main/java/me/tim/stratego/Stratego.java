package me.tim.stratego;

import me.tim.stratego.card.StrategoCard;
import me.tim.stratego.commands.*;
import me.tim.stratego.listeners.ArmorStandClickListener;
import me.tim.stratego.listeners.BlockPlaceListener;
import me.tim.stratego.listeners.ChangeCardClickListener;
import me.tim.stratego.listeners.InventoryClickListener;
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

    private ArrayList<Team> teams;
    private HashMap<UUID, StrategoCard> playerCards;

    @Override
    public void onEnable() {
        World world = Bukkit.getWorld("world");
        this.teams = new ArrayList<>();
        teams.add(new Team(this, ChatColor.RED, new Location(world, -0.5, 81, -1.5), new ItemStack(Material.RED_WOOL), "Red", Material.RED_BANNER));
        teams.add(new Team(this, ChatColor.BLUE, new Location(world, 1.5, 81, -1.5), new ItemStack(Material.BLUE_WOOL), "Blue", Material.BLUE_BANNER));
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(this), this);
        getServer().getPluginManager().registerEvents(new ArmorStandClickListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
        getServer().getPluginManager().registerEvents(new ChangeCardClickListener(this), this);
        playerCards = new HashMap<>();
        getCommand("blue").setExecutor(new TeamBlueCommand(this));
        getCommand("red").setExecutor(new TeamRedCommand(this));
        getCommand("showteams").setExecutor(new ShowTeamsCommand(this));
        getCommand("isflagcaptured").setExecutor(new IsFlagCapturedCommand(this));
        getCommand("start").setExecutor(new StartCommand(this));
        getCommand("amiinspawn").setExecutor(new AmIInSpawnCommand(this));
        getCommand("restartgame").setExecutor(new RestartCommand(this));
        FlagHoldTask task = new FlagHoldTask(this);
        task.runTaskTimer(this, 0, 10);
    }

    public void setPlayerCard(Player player, StrategoCard card) {
        UUID uuid = player.getUniqueId();
        playerCards.put(uuid, card);
    }

    public void clearPlayerCards() {
        playerCards.clear();
    }

    public StrategoCard getCurrentCard(Player player) {
        UUID uuid = player.getUniqueId();
        return playerCards.get(uuid);
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public Team getPlayerTeam(Player player) {
        for (Team team : teams) {
            if (team.getPlayers().contains(player)) {
                return team;
            }
        }
        return null;
    }

    public boolean isPlayerInSpawn(Player player) {
        Team team = getPlayerTeam(player);
        if (team == null) {
            return false;
        }
        int x = (int) Math.floor(player.getLocation().getX());
        int z = (int) Math.floor(player.getLocation().getZ());
        return player.getWorld().getBlockAt(x, 1, z).getType() == team.getWoolBlock().getType();
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
