package me.tim.stratego.listeners;
import me.tim.stratego.manager.GameManager;
import me.tim.stratego.teams.Team;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static org.bukkit.Bukkit.getServer;

public class TeamSelectorListener implements Listener {

    private GameManager gameManager;

    public TeamSelectorListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item != null && item.getType() == Material.NETHER_STAR) {
            openWoolInventory(player);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;
        if (event.getView().getTitle().equals("Team Selector")) {
            event.setCancelled(true);

            Team red = gameManager.getTeams().get(0);
            Team blue = gameManager.getTeams().get(1);

            if (gameManager.getPlayerTeam(player) != null && (clickedItem.getType() == Material.AIR ||
                    clickedItem.getType() == Material.GREEN_WOOL || clickedItem.getType() == Material.RED_WOOL)) {
                Team currentTeam = gameManager.getPlayerTeam(player);
                currentTeam.getPlayers().remove(player);
            }

            if (clickedItem.getType() == Material.RED_WOOL) {
                red.addPlayer(player);
                player.sendMessage(ChatColor.RED + "Joined team Red!");


            } else if (clickedItem.getType() == Material.BLUE_WOOL) {
                blue.addPlayer(player);
                player.sendMessage(ChatColor.GREEN + "Joined team Blue!");
            }
        }
    }

    private void openWoolInventory(Player player) {
        Inventory inventory = getServer().createInventory(null, 9, "Team Selector");

        ItemStack redWool = new ItemStack(Material.RED_WOOL);
        ItemMeta redWoolMeta = redWool.getItemMeta();
        redWoolMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Red");
        redWool.setItemMeta(redWoolMeta);

        ItemStack blueWool = new ItemStack(Material.BLUE_WOOL);
        ItemMeta blueWoolItemMeta = blueWool.getItemMeta();
        blueWoolItemMeta.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Blue");
        blueWool.setItemMeta(blueWoolItemMeta);

        inventory.setItem(3, redWool);

        inventory.setItem(5, blueWool);

        player.openInventory(inventory);
    }
}


