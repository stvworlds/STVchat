package net.maksim6opka.STVchat.handlers;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;

import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class MessageHandler {

    private static Component playerInfo(Player player, String color) {
        return Component.text("Натисни, ")
                .color(TextColor.fromHexString("#AAAAAA"))
                .append(Component.text("щоб надіслати повідомлення.")
                        .color(TextColor.fromHexString(color))
                )
                .append(Component.newline())
                .append(Component.newline())
                .append(Component.text("⌚ ⏸ " + PlaceholderAPI.setPlaceholders(player, "%statistic_hours_played%г.")))
        ;
    }


    public static Component time() {
        return Component.text(
                ZonedDateTime.now(ZoneId.of("Europe/Kyiv"))
                        .format(DateTimeFormatter.ofPattern("HH:mm:ss"))
        );
    }

    public static Component copyMsg(String color) {
        return Component.text("✎")
                .color(TextColor.fromHexString(color))

                .append(Component.text("Натисни, ")
                        .color(TextColor.fromHexString("#AAAAAA"))
                )

                .append(Component.text("щоб скопіювати.")
                    .color(TextColor.fromHexString(color))
                )
        ;
    }


    public static void sendWhisperMsg(Player player, String message) {

        String rawMessage = message.isEmpty() ? message : message.substring(1);

        Component finalMessage = Component.text("✉ ")
                .color(TextColor.fromHexString("#9863E7"))
                .hoverEvent(HoverEvent.showText(time()))

                .append(Component.text(player.getName())
                        .color(TextColor.fromHexString("#C6DEF1"))
                        .clickEvent(ClickEvent.suggestCommand("/msg " + player.getName() + " "))
                        .hoverEvent(HoverEvent.showText(playerInfo(player, "#9863E7")))
                )

                .append(Component.text(" • ")
                        .color(TextColor.fromHexString("#9863E7"))
                        .hoverEvent(HoverEvent.showText(Component.text("Шепотіння")))
                )

                .append(Component.text(rawMessage)
                        .decorate(TextDecoration.ITALIC)
                        .color(TextColor.fromHexString("#C6DEF1"))
                        .clickEvent(ClickEvent.copyToClipboard(message))
                        .hoverEvent(HoverEvent.showText(copyMsg("#9863E7")))

                );

        for (var entity : player.getNearbyEntities(10, 5, 10)) {
            if (entity instanceof Player nearbyPlayer) {
                nearbyPlayer.sendMessage(finalMessage);
            }

        }
        player.sendMessage(finalMessage);
    }
    public static void sendLocalMsg(Player player, String message) {

        Component finalMessage = Component.text("⚐ ")
                .color(TextColor.fromHexString("#C8F1C6"))
                .hoverEvent(HoverEvent.showText(time()))

                .append(Component.text(player.getName())
                        .color(TextColor.fromHexString("#A7C1A5"))
                        .clickEvent(ClickEvent.suggestCommand("/msg " + player.getName() + " "))
                        .hoverEvent(HoverEvent.showText(playerInfo(player, "#C8F1C6")))
                )

                .append(Component.text(" • ")
                        .color(TextColor.fromHexString("#C8F1C6"))
                        .hoverEvent(HoverEvent.showText(Component.text("Локальний чат")))
                )

                .append(Component.text(message)
                        .color(TextColor.fromHexString("#A7C1A5"))
                        .clickEvent(ClickEvent.copyToClipboard(message))
                        .hoverEvent(HoverEvent.showText(copyMsg("#C8F1C6")))

                );

        for (var entity : player.getNearbyEntities(75, 30, 75)) {
            if (entity instanceof Player nearbyPlayer) {
                nearbyPlayer.sendMessage(finalMessage);
            }

        }
        player.sendMessage(finalMessage);
    }
}
