package net.maksim6opka.STVchat;

import net.maksim6opka.STVchat.events.ChatEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new ChatEvent(), this);

        saveDefaultConfig();

        getLogger().info("Plugin enabled");
    }
    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled");
    }
}
