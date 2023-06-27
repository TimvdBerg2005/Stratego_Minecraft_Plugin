package me.tim.stratego.manager;

import me.tim.stratego.Stratego;
import me.tim.stratego.Utils;
import me.tim.stratego.card.StrategoCard;
import me.tim.stratego.tasks.GameStartCountdownTask;
import me.tim.stratego.tasks.TeamSelectCountdownTask;
import me.tim.stratego.teams.Team;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class GameManager {

    public GameState gameState = GameState.PRELOBBY;
    private HashMap<UUID, StrategoCard> playerCards;
    private ArrayList<Player> playerAllowedToSwap;

    private final Stratego plugin;
    private final ArrayList<Team> teams;
    private GameStartCountdownTask gameStartCountdownTask;
    private TeamSelectCountdownTask teamSelectCountdownTask;

    public GameManager(Stratego plugin) {
        this.plugin = plugin;
        this.teams = new ArrayList<>();
        World world = Bukkit.getWorld("world");
        teams.add(new Team(plugin, ChatColor.RED, new Location(world, -0.5, 81, -1.5), new ItemStack(Material.RED_WOOL), "Red", Material.RED_BANNER));
        teams.add(new Team(plugin, ChatColor.BLUE, new Location(world, 19.5, 81, -1.5), new ItemStack(Material.BLUE_WOOL), "Blue", Material.BLUE_BANNER));
        playerCards = new HashMap<>();
        playerAllowedToSwap = new ArrayList<>();
    }

    public void setGameState(GameState gameState) {
        if (this.gameState == GameState.ACTIVE && gameState == GameState.STARTING) return;
        if (this.gameState == gameState) return;
        this.gameState = gameState;

        switch (gameState) {
            case PRELOBBY:
                Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Returning to the prelobby!");
                break;
            case TEAM_SELECT:
                World targetWorld = Bukkit.getWorld("world");

                if (targetWorld != null) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.teleport(targetWorld.getSpawnLocation());
                    }
                }
                Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Select a team!");
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.getInventory().addItem(new ItemStack(Material.NETHER_STAR));
                    player.getActivePotionEffects().clear();
                    player.setHealth(20);
                    player.setFoodLevel(20);
                }
                this.teamSelectCountdownTask = new TeamSelectCountdownTask(this);
                this.teamSelectCountdownTask.runTaskTimer(plugin, 0, 20);
                break;
            case STARTING:
                Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Starting!");
                if (this.teamSelectCountdownTask != null) this.teamSelectCountdownTask.cancel();
                this.gameStartCountdownTask = new GameStartCountdownTask(this);
                this.gameStartCountdownTask.runTaskTimer(plugin,0, 20);
                startGame();
                break;
            case ACTIVE:
                if (this.gameStartCountdownTask != null) this.gameStartCountdownTask.cancel();
                for (Team team : teams) {
                    for (Player player : team.getPlayers()) {
                        player.teleport(team.getDefaultSpawnLocation());
                    }
                }
                break;
            case WON:
                Bukkit.broadcastMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Game Over!");
                Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "GG");
                setGameState(GameState.RESTARTING);
                break;
            case RESTARTING:
                for (Team team : getTeams()) {
                    team.refillCards();
                    team.resetFlag();
                    team.clearPlayers();
                }
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.getInventory().clear();
                }
                clearPlayerCards();
                setGameState(GameState.TEAM_SELECT);
                break;
        }
    }

    public Team getPlayerTeam(Player player) {
        for (Team team : teams) {
            if (team.getPlayers().contains(player)) {
                return team;
            }
        }
        return null;
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

    public boolean isPlayerInSpawn(Player player) {
        Team team = getPlayerTeam(player);
        if (team == null) {
            return false;
        }
        int x = (int) Math.floor(player.getLocation().getX());
        int z = (int) Math.floor(player.getLocation().getZ());
        return player.getWorld().getBlockAt(x, 1, z).getType() == team.getWoolBlock().getType();
    }

    public void respawnPlayer(Player player) {
        Team team = getPlayerTeam(player);
        player.teleport(team.getDefaultSpawnLocation());
        setPlayerCard(player, team.getRandomAvailableCard());
    }

    public void changePlayerCard(Player player) {
        StrategoCard currentCard = getCurrentCard(player);
        StrategoCard newCard = getPlayerTeam(player).replaceRandomCard(currentCard);

        setPlayerCard(player, newCard);
        Utils.updateCard(player, newCard);

        String message = ChatColor.translateAlternateColorCodes('&', "&7&lNew Card: &3&l" + newCard.getName());
        player.sendActionBar(message);
    }


    public void disallowCardSwap(Player player) {
        playerAllowedToSwap.remove(player);
    }

    public boolean canSwapCard(Player player) {
        return playerAllowedToSwap.contains(player);
    }

    public void allowCardSwap(Player player) {
        playerAllowedToSwap.add(player);
    }

    public void startGame() {
        for (Team team : teams) {
            for (Player player : team.getPlayers()) {
                StrategoCard strategoCard = team.getRandomAvailableCard();
                setPlayerCard(player, strategoCard);

                player.getInventory().setItem(4, Utils.createItemStack(team.getBanner(), team.getColor() + "" + ChatColor.BOLD + team.getName() + " Flag"));
                player.getInventory().setItem(0, Utils.createItemStackWithLore(Material.STONE_SWORD, ChatColor.AQUA + "" + ChatColor.BOLD + "Attack" + ChatColor.GRAY + " (Left Click)",
                        Arrays.asList(ChatColor.GRAY + "Hit an enemy to engage in a fight",
                                "",
                                ChatColor.GRAY + "If your card is a " + ChatColor.GREEN + "" + ChatColor.BOLD + "HIGHER" + ChatColor.GRAY + " power than",
                                ChatColor.GRAY + "your oponents power, your opponent will die.",
                                ChatColor.GRAY + "If your card has " + ChatColor.RED + "" + ChatColor.BOLD + "LESS" + ChatColor.GRAY + " power than",
                                ChatColor.GRAY + "your opponent, you will die.")));
                player.getInventory().setItem(7, Utils.createItemStackWithLore(Material.ECHO_SHARD, ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Change Card" + ChatColor.GRAY + " (Right Click)", Arrays.asList(
                        ChatColor.GRAY + "Refreshes on kill",
                        ChatColor.GRAY + "Cycles after a kill: " + ChatColor.AQUA + "" + ChatColor.BOLD + "2",
                        "")));
                Utils.updateCard(player, strategoCard);
            }
        }
    }

}
