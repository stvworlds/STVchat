package net.maksim6opka.STVchat.commands;

import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.maksim6opka.STVchat.Main;

public class PluginCommands {
    private final Main plugin;

    public PluginCommands(Main plugin) {
        this.plugin = plugin;
    }

    public void registerPluginCommands(){
        plugin.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final var registrar = event.registrar();

            registrar.register(
                    Commands.literal("stvchat")
                            .executes( ctx -> {

                                var sender = ctx.getSource().getSender();

                                sender.sendMessage("§a Private chat system for Stevious.zyx, created by Maksim6opka");
                                return 1;
                            } )
                            .then(Commands.literal("reload")
                                .requires(req -> req.getSender().hasPermission("stvchat.reload"))
                                .executes(ctx -> {

                                    var sender = ctx.getSource().getSender();

                                    plugin.reloadConfig();
                                    sender.sendMessage("§a STVchat reloaded");

                                    return 1;
                                })
                            )
                            .build()
            );
        });
    }
}
