package me.ctd.beaconapi.beacon.beam;

import org.bukkit.Material;

public enum BeaconBeamColor {
    WHITE,
    ORANGE,
    MAGENTA,
    LIGHT_BLUE,
    YELLOW,
    LIME,
    PINK,
    GRAY,
    LIGHT_GRAY,
    CYAN,
    PURPLE,
    BLUE,
    BROWN,
    GREEN,
    RED,
    BLACK
    ;
    
    public Material getBeamColor(BeamColorType beamColorType) {
        return Material.valueOf(name() + "_" + beamColorType.name());
    }
}
