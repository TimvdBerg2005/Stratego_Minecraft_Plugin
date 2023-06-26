package me.tim.stratego.flag;

import me.tim.stratego.Stratego;
import me.tim.stratego.teams.Team;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class Flag {
    private ArmorStand armorStand;
    private final Stratego plugin;
    private Location location;
    private final Team team;

    public Flag(Stratego plugin, Team team, Location location) {
        this.plugin = plugin;
        this.location = location;
        this.team = team;
        createFlag();
    }

    public void resetFlag() {
        armorStand.teleport(location);
        armorStand.setItem(EquipmentSlot.HEAD, new ItemStack(team.getWoolBlock()));
    }

    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
       this.location = location;
    }

    public void setArmorStandHead(ItemStack itemStack) {
        armorStand.setItem(EquipmentSlot.HEAD, itemStack);
    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }

    public void setArmorStand(ArmorStand armorStand) {
        this.armorStand = armorStand;
    }

    public void moveFlag(Location newLocation) {
        armorStand.teleport(newLocation);
    }

    public void createFlag() {
        World world = Bukkit.getWorld("world");
        if (world == null) {
            System.out.println("Error: Flags world = null");
            return;
        }

        armorStand = world.spawn(location, ArmorStand.class);
        armorStand.setCustomName(team.getName());
        armorStand.setCustomNameVisible(false);
        armorStand.setVisible(false);
        armorStand.setSmall(true);
        armorStand.setGravity(false);
        armorStand.setInvulnerable(true);
        armorStand.setItem(EquipmentSlot.HEAD, new ItemStack(team.getWoolBlock()));
    }

}
