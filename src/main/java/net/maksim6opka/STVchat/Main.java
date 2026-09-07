package net.maksim6opka.STVchat;

import net.maksim6opka.STVchat.commands.PluginCommands;
import net.maksim6opka.STVchat.events.ChatEvent;
import net.maksim6opka.STVchat.handlers.GlobalMessage;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;

    public static final String CHANNEL = "stvchat:main";

    private GlobalMessage globalMessageListener;

    @Override
    public void onEnable() {
        instance = this;
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new ChatEvent(), this);

        saveDefaultConfig();

        PluginCommands reloadCommand = new PluginCommands(this);
        reloadCommand.registerPluginCommands();

        globalMessageListener = new GlobalMessage();
        getServer().getMessenger().registerOutgoingPluginChannel(this, CHANNEL);
        getServer().getMessenger().registerIncomingPluginChannel(this, CHANNEL, globalMessageListener);

        getLogger().info("Plugin enabled");
    }

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        getServer().getMessenger().unregisterOutgoingPluginChannel(this, CHANNEL);
        if (globalMessageListener != null) {
            getServer().getMessenger().unregisterIncomingPluginChannel(this, CHANNEL, globalMessageListener);
        }
        getLogger().info("Plugin disabled");
    }
}
