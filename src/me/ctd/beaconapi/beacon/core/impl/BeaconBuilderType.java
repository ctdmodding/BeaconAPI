package me.ctd.beaconapi.beacon.core.impl;

import me.ctd.beaconapi.beacon.core.BeaconRaw;
import org.bukkit.Location;

public enum BeaconBuilderType {
    WORLD_BUILDER(WorldBeacon.class),
    PACKET_BUILDER(PacketBeacon.class)
    ;

    private Class<? extends BeaconRaw<?>> beaconClass;

    BeaconBuilderType(Class<? extends BeaconRaw<?>> beaconClass) {
        this.beaconClass = beaconClass;
    }

    public BeaconRaw<?> newInstance(Location location) {
        try {
            return beaconClass.getDeclaredConstructor(BeaconBuilderType.class, Location.class).newInstance(this, location);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public Class<? extends BeaconRaw<?>> getBeaconClass() {
        return beaconClass;
    }
}
