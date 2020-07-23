package me.ctd.beaconapi.beacon.beam;

import me.ctd.gameframework.GameFramework;
import me.ctd.gameframework.util.ChatUtil;
import me.ctd.gameframework.util.PacketUtil;
import me.ctd.gameframework.util.beacon.core.impl.BeaconBuilderType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * Jon created on 7/16/2020
 */
public class BeaconBeam {

    private BeaconBuilderType builderType;
    private Location location;
    private List<BeaconBeamColor> beamColors;
    private BeamColorType beamColorType;
    private boolean disable;
    private Map<Location, Material> blockCache;

    // Don't add a color to the beacon
    public BeaconBeam(BeaconBuilderType builderType, Location location, BeamColorType beamColorType) {
        this(builderType, location, beamColorType, false, null);
    }

    public BeaconBeam(BeaconBuilderType builderType, Location location, BeamColorType beamColorType, boolean disable, BeaconBeamColor... beamColors) {
        this.builderType = builderType;
        this.location = location;
        this.beamColorType = beamColorType;
        this.disable = disable;
        this.beamColors = new LinkedList<>();
        blockCache = new HashMap<>();
        addBeamColors(beamColors);
    }

    public void addBeamColor(BeaconBeamColor beamColor) {
        beamColors.add(beamColor);
    }

    public void addBeamColors(BeaconBeamColor... beamColors) {
        if (beamColors != null) {
            for (BeaconBeamColor beamColor : beamColors) {
                addBeamColor(beamColor);
            }
        }
    }

    public void sendBlockChange(Player player, Location location, Material material) {
        cacheBlockChange(location);
        if (builderType == BeaconBuilderType.PACKET_BUILDER) {
            PacketUtil.sendBlockChange(player, location, material);
        } else if (builderType == BeaconBuilderType.WORLD_BUILDER) {
            location.getBlock().setType(material);
        }
    }

    public void flashBeam(Player player) {
        Location top = location.add(0, beamColors.size(), 0);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendBlockChange(player, top, Material.AIR);
            }
        }.runTaskLater(GameFramework.getInstance().getPlugin(), 20L * 6);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendBlockChange(player, top, Material.EMERALD);
                ChatUtil.broadcastMessage("Flashing Beacon");
            }
        }.runTaskLater(GameFramework.getInstance().getPlugin(), 20L * 6 + 3);
    }

    public void updateBeamColor(Player player, boolean clear, BeaconBeamColor... beamColors) {
        if (clear) {
            for (int i = 0; i < this.beamColors.size(); i++) {
                if (builderType == BeaconBuilderType.PACKET_BUILDER) {
                    PacketUtil.sendBlockChange(player, location.clone().add(0, i + 1, 0), Material.AIR);
                } else if (builderType == BeaconBuilderType.WORLD_BUILDER) {
                    location.clone().add(0, i + 1, 0).getBlock().setType(Material.AIR);
                }
            }
            this.beamColors.clear();
        }
        Arrays.stream(beamColors).forEach(this::addBeamColor);
        build(player);
    }

    public void cacheBlockChange(Location location) {
        blockCache.put(location, location.getBlock().getType());
    }

    public Map<Location, Material> getBlockCache() {
        return blockCache;
    }

    public void build(Player player) {
        for (int i = 0; i < beamColors.size(); i++) {
            sendBlockChange(player, location.clone().add(0, i + 1, 0), beamColors.get(i).getBeamColor(beamColorType));
        }
        if (disable) {
            sendBlockChange(player, location.clone().add(0, beamColors.size(), 0), Material.EMERALD);
        }
    }
}
