package net.maksim6opka.STVchat.handlers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;

import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MessageHandler {

    private static final Component PLAYERINFO = Component.text("Натисни, щоб надіслати повідомлення")
                                                .append(Component.newline())
                                                .append(Component.newline())
                                                .append(Component.text("☠ ⏸ %statistic_DEATHS%"))
                                                .append(Component.newline())
                                                .append(Component.text("⌚ ⏸ %statistic_hours_played%г.")
    );

    private static final Component TIME = Component.text(LocalTime
                                                .now()
                                                .format(DateTimeFormatter
                                                        .ofPattern("HH:mm:ss")
                                                )
    );

    public static void sendGlobalMsg(Player player, String message) {
        Bukkit.broadcast(
                Component.text(player.getName())
                        .append(Component.text(" • " + message))
        );

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
                .hoverEvent(HoverEvent.showText(MessageHandler.TIME))

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

        for (var entity : player.getNearbyEntities(75, 30, 75)) {
            if (entity instanceof Player nearbyPlayer) {
                nearbyPlayer.sendMessage(player.getName() + " • " + message);
            }

        }
        player.sendMessage(player.getName() + " • " + message);
    }
}
