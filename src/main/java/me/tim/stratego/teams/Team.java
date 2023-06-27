package me.tim.stratego.teams;

import me.tim.stratego.Stratego;
import me.tim.stratego.card.Cards;
import me.tim.stratego.card.StrategoCard;
import me.tim.stratego.flag.Flag;
import me.tim.stratego.manager.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Team {
    private final GameManager gameManager;
    private final String name;
    private boolean flagCaptured = false;
    private Location defaultSpawnLocation;
    private ArrayList<Player> players;
    private final ChatColor color;
    private final ItemStack woolBlock;
    private final Flag flag;
    private final Stratego plugin;
    private final Material banner;
    private HashMap<StrategoCard, Integer> availableCards;

    public Team(Stratego plugin, ChatColor color, Location defaultSpawnLocation, ItemStack woolBlock, String name, Material banner) {
        this.players = new ArrayList<>();
        this.plugin = plugin;
        this.gameManager = plugin.getGameManager();
        this.color = color;
        this.woolBlock = woolBlock;
        this.defaultSpawnLocation = defaultSpawnLocation;
        this.name = name;
        this.banner = banner;
        this.flag = new Flag(plugin, this, defaultSpawnLocation);
        availableCards = new HashMap<>();
        refillCards();
    }

    public StrategoCard replaceRandomCard(StrategoCard card) {
        int count = availableCards.get(card);
        availableCards.put(card, count + 1);
        return getRandomAvailableCard();
    }

    public HashMap<StrategoCard, Integer> getAvailableCards() {
        return availableCards;
    }

    public StrategoCard getRandomAvailableCard() {
        List<StrategoCard> availableCardTypes = new ArrayList<>();
        for (Map.Entry<StrategoCard, Integer> entry : availableCards.entrySet()) {
            StrategoCard card = entry.getKey();
            int count = entry.getValue();
            for (int i = 0; i < count; i++) {
                availableCardTypes.add(card);
            }
        }

        Collections.shuffle(availableCardTypes);

        if (!availableCardTypes.isEmpty()) {
            StrategoCard strategoCard = availableCardTypes.get(0);

            int count = availableCards.get(strategoCard);
            if (count > 0) {
                availableCards.put(strategoCard, count - 1);
            }

            return strategoCard;
        } else {
            return null;
        }

    }

    public void resetFlag() {
        if (flag == null) return;
        flag.resetFlag();
    }

    public void clearPlayers() {
        players.clear();
    }

    public void refillCards() {
        availableCards.clear();
        availableCards.put(Cards.MARSHAL_CARD, 1);
        availableCards.put(Cards.GENERAL_CARD, 1);
        availableCards.put(Cards.COLONEL_CARD, 2);
        availableCards.put(Cards.MAJOR_CARD, 3);
        availableCards.put(Cards.CAPTAIN_CARD, 4);
        availableCards.put(Cards.LIEUTENANT_CARD, 4);
        availableCards.put(Cards.SERGEANT_CARD, 4);
        availableCards.put(Cards.MINER_CARD, 5);
        availableCards.put(Cards.SCOUT_CARD, 5);
        availableCards.put(Cards.SPY_CARD, 2);
        availableCards.put(Cards.BOMB_CARD, 5);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void addPlayerToTeam(Player player) {
        players.add(player);
    }

    public Material getBanner() {
        return banner;
    }

    public ChatColor getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public void moveFlag(Location newLocation) {
        flag.moveFlag(newLocation);
    }

    public void moveFlagToDefault() {
        flag.moveFlag(defaultSpawnLocation);
    }

    public void setFlagCaptured(boolean flagCaptured) {
        if (flagCaptured) {
            flag.setArmorStandHead(new ItemStack(Material.AIR));
        } else {
            flag.setArmorStandHead(woolBlock);
        }
        this.flagCaptured = flagCaptured;
    }

    public boolean isFlagCaptured() {
        return flagCaptured;
    }

    public ItemStack getWoolBlock() {
        return woolBlock;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public Location getDefaultSpawnLocation() {
        return defaultSpawnLocation;
    }
}
