package net.maksim6opka.STVchat.handlers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;

import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class MessageHandler {

    private static final Component PLAYERINFO = Component.text("Натисни, щоб надіслати повідомлення")
                                                .append(Component.newline())
                                                .append(Component.newline())
                                                .append(Component.text("☠ ⏸ %statistic_DEATHS%"))
                                                .append(Component.newline())
                                                .append(Component.text("⌚ ⏸ %statistic_hours_played%г.")
    );


    public static Component time() {
        return Component.text(
                ZonedDateTime.now(ZoneId.of("Europe/Kyiv"))
                        .format(DateTimeFormatter.ofPattern("HH:mm:ss"))
        );
    }



    public static void sendGlobalMsg(Player player, String message) {

        Component finalMessage = Component.text("♦ ")
                .color(TextColor.fromHexString("#fe7eff"))
                .hoverEvent(HoverEvent.showText(time()))

                .append(Component.text(player.getName())
                        .color(TextColor.fromHexString("#aaaaaa"))
                        .clickEvent(ClickEvent.suggestCommand("/msg " + player.getName() + " "))
                        .hoverEvent(HoverEvent.showText(MessageHandler.PLAYERINFO))
                )

                .append(Component.text(" • ")
                        .color(TextColor.fromHexString("#aaaaaa"))
                )

                .append(Component.text(message)
                        .color(TextColor.fromHexString("#aaaaaa"))
                        .decorate(TextDecoration.ITALIC)
                        .clickEvent(ClickEvent.copyToClipboard(message))
                        .hoverEvent(HoverEvent.showText(Component.text("Натисніть, щоб скопіювати")))

                );
        String url = "https://discord.com/api/webhooks/1443851902470197430/GIT1Zy73GWzWDo9O_y4-vpDJuwCMes80uvVGaESJk7UmHsYuv4w2rEJ5QBdcwS8BiXE6";
        WebhookGlobalChat.send(url, message, PlayerAvatarUrl.getAvatarUrl(player), player.getName());
        Bukkit.broadcast(finalMessage);

    }
    public static void sendServerMsg(Player player, String message) {
        Bukkit.broadcast(
                Component.text(player.getName())
                        .append(Component.text(" • " + message))

        );
    }
    public static void sendWhisperMsg(Player player, String message) {

        Component finalMessage = Component.text("♦ ")
                .color(TextColor.fromHexString("#fe7eff"))
                .hoverEvent(HoverEvent.showText(time()))

                .append(Component.text(player.getName())
                        .color(TextColor.fromHexString("#aaaaaa"))
                        .clickEvent(ClickEvent.suggestCommand("/msg " + player.getName() + " "))
                        .hoverEvent(HoverEvent.showText(MessageHandler.PLAYERINFO))
                )

                .append(Component.text(" • ")
                        .color(TextColor.fromHexString("#aaaaaa"))
                )

                .append(Component.text(message)
                        .color(TextColor.fromHexString("#aaaaaa"))
                        .decorate(TextDecoration.ITALIC)
                        .clickEvent(ClickEvent.copyToClipboard(message))
                        .hoverEvent(HoverEvent.showText(Component.text("Натисніть, щоб скопіювати")))

                );

        for (var entity : player.getNearbyEntities(10, 5, 10)) {
            if (entity instanceof Player nearbyPlayer) {
                nearbyPlayer.sendMessage(finalMessage);
            }

        }
        player.sendMessage(finalMessage);
    }
    public static void sendLocalMsg(Player player, String message) {

        Component finalMessage = Component.text("♦ ")
                .color(TextColor.fromHexString("#fe7eff"))
                .hoverEvent(HoverEvent.showText(time()))

                .append(Component.text(player.getName())
                        .color(TextColor.fromHexString("#aaaaaa"))
                        .clickEvent(ClickEvent.suggestCommand("/msg " + player.getName() + " "))
                        .hoverEvent(HoverEvent.showText(MessageHandler.PLAYERINFO))
                )

                .append(Component.text(" • ")
                        .color(TextColor.fromHexString("#aaaaaa"))
                )

                .append(Component.text(message)
                        .color(TextColor.fromHexString("#aaaaaa"))
                        .clickEvent(ClickEvent.copyToClipboard(message))
                        .hoverEvent(HoverEvent.showText(Component.text("Натисніть, щоб скопіювати")))

                );

        for (var entity : player.getNearbyEntities(75, 30, 75)) {
            if (entity instanceof Player nearbyPlayer) {
                nearbyPlayer.sendMessage(finalMessage);
            }

        }
        player.sendMessage(finalMessage);
    }
}
