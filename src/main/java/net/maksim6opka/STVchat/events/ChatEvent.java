package net.maksim6opka.STVchat.events;


import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.maksim6opka.STVchat.Main;
import net.maksim6opka.STVchat.handlers.MessageHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;



public class ChatEvent implements Listener {

    @EventHandler
    public void onChat(AsyncChatEvent event) {

        Player player = event.getPlayer();
        String text = PlainTextComponentSerializer
                .plainText()
                .serialize(event.message())
                .stripLeading();

        if (text.isEmpty()) return;

        char firstChar = text.charAt(0);

        switch (firstChar) {
            case '~':
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> MessageHandler.sendWhisperMsg(player, text));
                break;
            //  Chat with 75 blocks radius
            default:
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> MessageHandler.sendLocalMsg(player, text));
        }

        event.setCancelled(true);
    }
}
