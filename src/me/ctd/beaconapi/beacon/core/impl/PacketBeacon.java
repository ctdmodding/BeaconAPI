package me.ctd.beaconapi.beacon.core.impl;

import me.ctd.gameframework.util.PacketUtil;
import me.ctd.gameframework.util.beacon.core.BeaconRaw;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Jon created on 7/16/2020
 */
public class PacketBeacon extends BeaconRaw<Player> {

    public PacketBeacon(BeaconBuilderType builderType, Location location) {
        super(builderType, location);
    }

    @Override
    public void destroy(Player player) {
        getBlockCache().forEach((location, material) -> PacketUtil.sendBlockChange(player, location, material));
        getPyramid().getBlockCache().forEach((location, material) -> PacketUtil.sendBlockChange(player, location, material));
        getBeaconBeam().getBlockCache().forEach((location, material) -> PacketUtil.sendBlockChange(player, location, material));
    }

    @Override
    public void build(Player player) {
        PacketUtil.sendBlockChange(player, getLocation(), Material.BEACON);
        getPyramid().build(player, getBuilderType());
        getBeaconBeam().build(player);
    }
}
