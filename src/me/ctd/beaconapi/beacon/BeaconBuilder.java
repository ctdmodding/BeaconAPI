package me.ctd.beaconapi.beacon;

import me.ctd.beaconapi.beacon.beam.BeaconBeam;
import me.ctd.beaconapi.beacon.beam.BeaconBeamColor;
import me.ctd.beaconapi.beacon.core.BeaconRaw;
import me.ctd.beaconapi.beacon.core.impl.BeaconBuilderType;
import me.ctd.beaconapi.beacon.core.impl.PacketBeacon;
import me.ctd.beaconapi.beacon.core.impl.WorldBeacon;
import me.ctd.beaconapi.beacon.pyramid.BeaconPyramid;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.logging.Level;

/**
 * Jon created on 7/16/2020
 */
public class BeaconBuilder {

    private BeaconBuilderType builderType;
    private BeaconRaw<?> beacon;

    public BeaconBuilder() {

    }

    public BeaconBuilder(BeaconBuilderType builderType, Location location) {
        this.builderType = builderType;
        beacon = builderType.newInstance(location);
    }

    public BeaconBuilder(BeaconBuilderType builderType, Location location, BeaconPyramid pyramid, BeaconBeam beam, BeaconBeamColor... beamColors) {
        this.builderType = builderType;
        beacon = builderType.newInstance(location);
        if (beacon != null) {
            beacon.setPyramid(pyramid);
            beacon.setBeaconBeam(beam);
            beacon.addBeamColors(beamColors);
        } else {
            Bukkit.getLogger().log(Level.SEVERE, "Beacon == NULL");
        }
    }

    public BeaconBuilder setBeaconBuilderType(BeaconBuilderType builderType) {
        this.builderType = builderType;
        return this;
    }

    public BeaconBuilder setBeacon(BeaconRaw<?> beacon) {
        this.beacon = beacon;
        return this;
    }

    public BeaconBuilder setPyramid(BeaconPyramid pyramid) {
        beacon.setPyramid(pyramid);
        return this;
    }

    public BeaconBuilder setBeaconBeam(BeaconBeam beaconBeam) {
        beacon.setBeaconBeam(beaconBeam);
        return this;
    }

    public BeaconBuilder addBeamColors(BeaconBeamColor... beamColors) {
        beacon.addBeamColors(beamColors);
        return this;
    }

    public BeaconRaw<?> getBeacon() {
        return beacon;
    }

    public BeaconBuilder build(Player player) {
        if (builderType == BeaconBuilderType.PACKET_BUILDER) {
            ((PacketBeacon)beacon).build(player);
        } else {
            ((WorldBeacon)beacon).build(beacon.getLocation().getWorld());
        }
        return this;
    }

    public BeaconBuilder buildTo(List<Player> players) {
       players.forEach(this::build);
       return this;
    }

    public BeaconBuilder buildToAll() {
        Bukkit.getOnlinePlayers().forEach(this::build);
        return this;
    }
}
