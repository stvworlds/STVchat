package net.maksim6opka.STVchat.handlers;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;

import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.maksim6opka.STVchat.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
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
                .append(Component.text("☠ ⏸ " + PlaceholderAPI.setPlaceholders(player, "%statistic_DEATHS%")))
                .append(Component.newline())
                .append(Component.text("⌚ ⏸ " + PlaceholderAPI.setPlaceholders(player, "%statistic_hours_played%г.")))
                ;
    }


    private static Component time() {
        return Component.text(
                ZonedDateTime.now(ZoneId.of("Europe/Kyiv"))
                        .format(DateTimeFormatter.ofPattern("HH:mm:ss"))
        );
    }

    private static Component copyMsg(String color) {
        return Component.text("✎ ")
                .color(TextColor.fromHexString(color))

                .append(Component.text("Натисни, ")
                        .color(TextColor.fromHexString("#AAAAAA"))
                )

                .append(Component.text("щоб скопіювати.")
                        .color(TextColor.fromHexString(color))
                )
                ;
    }


    private static Component parseMsg(String message) {
        Component parsed = MiniMessage.miniMessage().deserialize(message);
        return parsed;
    }

    public static void sendGlobalMsg(Player player, String message) {

        Main plugin = Main.getPlugin(Main.class);
        FileConfiguration config = plugin.getConfig();


        String rawMessage = message.isEmpty() ? message : message.substring(1);


        Component finalMessage = Component.text("★ ")
                .color(TextColor.fromHexString("#fe7eff"))
                .hoverEvent(HoverEvent.showText(time()))

                .append(Component.text(player.getName())
                        .color(TextColor.fromHexString("#aaaaaa"))
                        .clickEvent(ClickEvent.suggestCommand("/msg " + player.getName() + " "))
                        .hoverEvent(HoverEvent.showText(playerInfo(player, "#fe7eff")))
                )

                .append(Component.text(" • ")
                        .color(TextColor.fromHexString("#fe7eff"))
                        .hoverEvent(HoverEvent.showText(Component.text("Глобальний чат")))
                )

                .append(parseMsg(rawMessage)
                        .color(TextColor.fromHexString("#aaaaaa"))
                        .clickEvent(ClickEvent.copyToClipboard(message))
                        .hoverEvent(HoverEvent.showText(copyMsg("#fe7eff")))

                );

        Bukkit.broadcast(finalMessage);
        WebhookGlobalChat.send(config.getString("webhook"), rawMessage, PlayerAvatarUrl.getAvatarUrl(player), player.getName());

    }
    public static void sendServerMsg(Player player, String message) {

        String rawMessage = message.isEmpty() ? message : message.substring(1);

        Component finalMessage = Component.text("☁ ")
                .color(TextColor.fromHexString("#FAEDCB"))
                .hoverEvent(HoverEvent.showText(time()))

                .append(Component.text(player.getName())
                        .color(TextColor.fromHexString("#C9E4DE"))
                        .clickEvent(ClickEvent.suggestCommand("/msg " + player.getName() + " "))
                        .hoverEvent(HoverEvent.showText(playerInfo(player, "#FAEDCB")))
                )

                .append(Component.text(" • ")
                        .color(TextColor.fromHexString("#FAEDCB"))
                        .hoverEvent(HoverEvent.showText(Component.text("Серверний чат")))
                )

                .append(parseMsg(rawMessage)
                        .color(TextColor.fromHexString("#C9E4DE"))
                        .clickEvent(ClickEvent.copyToClipboard(message))
                        .hoverEvent(HoverEvent.showText(copyMsg("#FAEDCB")))

                );

        Bukkit.broadcast(finalMessage);
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

                .append(parseMsg(rawMessage)
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

                .append(parseMsg(message)
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
