package me.ctd.beaconapi.beacon;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

/**
 * Jon created on 7/23/2020
 */
public class BeaconAPI {

    private JavaPlugin plugin;
    private static BeaconAPI instance;
    private Random random;

    public BeaconAPI() {
        this.random = new Random();
    }
    public void init(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public Random getRandom() {
        return  random;
    }

    public static BeaconAPI getInstance() {
        if (instance == null) {
            instance = new BeaconAPI();
        }
        return instance;
    }


}
