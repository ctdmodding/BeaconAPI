package me.ctd.beaconapi.beacon.core;

import me.ctd.beaconapi.beacon.beam.BeaconBeam;
import me.ctd.beaconapi.beacon.beam.BeaconBeamColor;
import me.ctd.beaconapi.beacon.core.impl.BeaconBuilderType;
import me.ctd.beaconapi.beacon.pyramid.BeaconBaseType;
import me.ctd.beaconapi.beacon.pyramid.BeaconPyramid;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

/**
 * Jon created on 7/16/2020
 */
public abstract class BeaconRaw<T> {

    private BeaconBuilderType builderType;
    private Location location;
    private BeaconBaseType baseType;
    private BeaconPyramid pyramid;
    private BeaconBeam beaconBeam;
    private Map<Location, Material> blockCache;

    public BeaconRaw(BeaconBuilderType builderType, Location location) {
        this.builderType = builderType;
        this.location = location;
        blockCache = new HashMap<>();
    }

    public void setPyramid(BeaconPyramid pyramid) {
        this.pyramid = pyramid;
    }

    public void setBeaconBeam(BeaconBeam beaconBeam) {
        this.beaconBeam = beaconBeam;
    }

    public BeaconPyramid getPyramid() {
        return pyramid;
    }

    public BeaconBeam getBeaconBeam() {
        return beaconBeam;
    }

    public BeaconBuilderType getBuilderType() {
        return builderType;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setBaseType(BeaconBaseType baseType) {
        this.baseType = baseType;
    }

    public Location getLocation() {
        return location;
    }

    public BeaconBaseType getBaseType() {
        return baseType;
    }

    public void addBeamColor(BeaconBeamColor beamColor) {
        beaconBeam.addBeamColor(beamColor);
    }

    public void addBeamColors(BeaconBeamColor... beamColors) {
        beaconBeam.addBeamColors(beamColors);
    }

    public void cacheBlockChange(Location location) {
        blockCache.put(location, location.getBlock().getType());
    }

    public Map<Location, Material> getBlockCache() {
        return blockCache;
    }

    public abstract void destroy(T buildMethod);
    public abstract void build(T buildMethod);
}
