package me.ctd.beaconapi.beacon.util;

import net.minecraft.server.v1_14_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Jon created on 7/23/2020
 */
public class PacketUtil {

    // Let's cache the classes we use
    public static Map<String, Class<?>> cacheNMS = new HashMap<>();
    public static Map<String, Class<?>> cacheCraftBukkit = new HashMap<>();

    /**
     * Send a Block Change to a player
     *
     * @param player the player to send to
     * @param location the location of the block
     * @param material the new material of the block
     */
    public static void sendBlockChange(Player player, Location location, Material material) {
        try {
            Object blockPosition = Objects.requireNonNull(getNMSClass("BlockPosition"))
                    .getDeclaredConstructor(int.class, int.class, int.class).newInstance(location.getBlockX(), location.getBlockY(), location.getBlockZ());
            Object packet = Objects.requireNonNull(getNMSClass("PacketPlayOutBlockChange"))
                    .getDeclaredConstructor(getNMSClass("IBlockAccess"), getNMSClass("BlockPosition"))
                    .newInstance(new BeaconBlockAccess((Block) Objects.requireNonNull(getCraftBukkitClass("util.CraftMagicNumbers")).getDeclaredMethod("getBlock", Material.class).invoke(null, material)), blockPosition);
            sendPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Send a Packet to a Player
     *
     * @param player the player to send to
     * @param packet the Packet Object to send
     */
    public static void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getDeclaredField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get an NMS Class by its name
     *
     * @param name the Class' name
     * @returns the Class or null if not found
     */
    private static Class<?> getNMSClass(String name) {
        if (cacheNMS.containsKey(name)) {
            return cacheNMS.get(name);
        }
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            Class<?> clazz = Class.forName("net.minecraft.server." + version + "." + name);
            cacheNMS.put(name, clazz);
            return clazz;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get a CraftBukkit Class by its name
     *
     * @param name the Class' name
     * @returns the Class or null if not found
     */
    private static Class<?> getCraftBukkitClass(String name) {
        if (cacheCraftBukkit.containsKey(name)) {
            return cacheCraftBukkit.get(name);
        }
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            Class<?> clazz = Class.forName("org.bukkit.craftbukkit." + version + "." + name);
            cacheCraftBukkit.put(name, clazz);
            return clazz;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Block access for the BlockChangePacket
     */
    public static class BeaconBlockAccess implements IBlockAccess {

        private Block block;

        public BeaconBlockAccess(Block block) {
            this.block = block;
        }

        @Nullable
        @Override
        public TileEntity getTileEntity(BlockPosition blockPosition) {
            return null;
        }

        @Override
        public IBlockData getType(BlockPosition blockPosition) {
            return this.block.getBlockData();
        }

        @Override
        public Fluid getFluid(BlockPosition blockPosition) {
            return null;
        }
    }
}
