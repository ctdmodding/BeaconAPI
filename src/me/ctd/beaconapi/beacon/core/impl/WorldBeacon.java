package me.ctd.beaconapi.beacon.core.impl;

import me.ctd.beaconapi.beacon.core.BeaconRaw;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

/**
 * Jon created on 7/16/2020
 */
public class WorldBeacon extends BeaconRaw<World> {

    public WorldBeacon(BeaconBuilderType builderType, Location location) {
        super(builderType, location);
    }

    @Override
    public void destroy(World buildMethod) {
        getBlockCache().forEach((location, material) -> location.getBlock().setType(material));
        getPyramid().getBlockCache().forEach((location, material) -> location.getBlock().setType(material));
        getBeaconBeam().getBlockCache().forEach((location, material) -> location.getBlock().setType(material));
    }

    @Override
    public void build(World buildMethod) {
        cacheBlockChange(getLocation());
        buildMethod.getBlockAt(getLocation()).setType(Material.BEACON);
        getPyramid().build(null, getBuilderType());
        getBeaconBeam().build(null);
    }
}
