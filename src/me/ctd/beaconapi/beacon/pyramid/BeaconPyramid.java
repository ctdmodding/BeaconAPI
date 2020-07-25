package me.ctd.beaconapi.beacon.pyramid;

import me.ctd.beaconapi.beacon.core.impl.BeaconBuilderType;
import me.ctd.beaconapi.beacon.util.PacketUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;

public class BeaconPyramid {
    public enum BeaconPyramidType {

        FIVE_LAYER(5),
        FOUR_LAYER(4),
        THREE_LAYER(3),
        TWO_LAYER(2),
        ONE_LAYER(1);

        private int radiusSize;

        BeaconPyramidType(int radiusSize) {
            this.radiusSize = radiusSize;
        }

        public int getRadiusSize() {
            return radiusSize;
        }
    }

    private Location location;
    private BeaconBaseType baseType;
    private BeaconPyramidType pyramidType;
    private List<BeaconPyramidType> layers;
    private Map<Location, Material> blockCache;

    public BeaconPyramid(Location location, BeaconBaseType baseType, BeaconPyramidType pyramidType) {
        this.location = location;
        this.baseType = baseType;
        this.pyramidType = pyramidType;
        blockCache = new HashMap<>();
        setupLayers();
    }

    private void setupLayers() {
        layers = new LinkedList<>();
        switch (pyramidType) {
            case FIVE_LAYER:
                layers.add(BeaconPyramidType.FIVE_LAYER);
            case FOUR_LAYER:
                layers.add(BeaconPyramidType.FOUR_LAYER);
            case THREE_LAYER:
                layers.add(BeaconPyramidType.THREE_LAYER);
            case TWO_LAYER:
                layers.add(BeaconPyramidType.TWO_LAYER);
            case ONE_LAYER:
                layers.add(BeaconPyramidType.ONE_LAYER);
        }
        Collections.reverse(layers);
    }

    public void cacheBlockChange(Location location) {
        blockCache.put(location, location.getBlock().getType());
    }

    public Map<Location, Material> getBlockCache() {
        return blockCache;
    }

    public void build(Player player, BeaconBuilderType builderType) {
        int currentRadius;
        Location current;
        for (int i = 0; i < layers.size(); i++) {
            BeaconPyramidType layer = layers.get(i);
            currentRadius = layer.getRadiusSize();
            for (int x = -currentRadius; x <= currentRadius; x++) {
                for (int z = -currentRadius; z <= currentRadius; z++) {
                    current = location.clone().add(x, -(i + 1), z);
                    cacheBlockChange(current);
                    if (builderType == BeaconBuilderType.PACKET_BUILDER) {
                        PacketUtil.sendBlockChange(player, current, baseType.getBaseMaterial());
                    } else if (builderType == BeaconBuilderType.WORLD_BUILDER) {
                        current.getBlock().setType(baseType.getBaseMaterial());
                    }
                }
            }
        }
    }
}