package me.ctd.beaconapi.beacon.pyramid;

import org.bukkit.Material;

public enum BeaconBaseType {
    EMERALD(Material.EMERALD_BLOCK),
    DIAMOND(Material.DIAMOND_BLOCK),
    IRON(Material.IRON_BLOCK);

    private Material baseType;
    private int baseSize;

    BeaconBaseType(Material baseType) {
        setBaseMaterial(baseType);
    }

    public Material getBaseMaterial() {
        return baseType;
    }

    public void setBaseMaterial(Material baseType) {
        this.baseType = baseType;
    }
}
