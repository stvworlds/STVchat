package net.maksim6opka.STVchat.events;


import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;



public class ChatEvent implements Listener {

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        String text = PlainTextComponentSerializer
                .plainText()
                .serialize(event.message())
                .stripLeading();

        if (text.isEmpty()) return;

        char firstChar = text.charAt(0);

        switch (firstChar) {
            // Global chat
            case '!':
                event.getPlayer().sendMessage("Виконано команду з !");
                break;
            // One server chat
            case '#':
                event.getPlayer().sendMessage("Виконано команду з #");
                break;
            // Chat with 10 blocks radius
            case '~':
                event.getPlayer().sendMessage("Виконано команду з /");
                break;
            //  Chat with 75 blocks radius
            default:
                event.getPlayer().sendMessage("Звичайне повідомлення");
                break;
        }


    }
}
