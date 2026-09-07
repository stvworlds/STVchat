package net.maksim6opka.STVchat.handlers;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.maksim6opka.STVchat.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jspecify.annotations.NonNull;

import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class GlobalMessage implements PluginMessageListener {

    private static final String PACKET_CHAT = "CHAT";
    private static final GsonComponentSerializer SERIALIZER = GsonComponentSerializer.gson();

    public static void sendGlobalMsg(Player player, String message) {
        Main plugin = Main.getInstance();
        FileConfiguration config = plugin.getConfig();

        String rawMessage = message.isEmpty() ? message : message.substring(1);

        Component finalMessage = Component.text("★ ")
                .color(TextColor.fromHexString("#fe7eff"))
                .hoverEvent(HoverEvent.showText(MessageHandler.time()))

                .append(Component.text(player.getName())
                        .color(TextColor.fromHexString("#aaaaaa"))
                        .clickEvent(ClickEvent.suggestCommand("/msg " + player.getName() + " "))
                        .hoverEvent(HoverEvent.showText(MessageHandler.playerInfo(player, "#fe7eff")))
                )

                .append(Component.text(" • ")
                        .color(TextColor.fromHexString("#fe7eff"))
                        .hoverEvent(HoverEvent.showText(Component.text("Глобальний чат")))
                )

                .append(MessageHandler.parseMsg(rawMessage)
                        .color(TextColor.fromHexString("#aaaaaa"))
                        .clickEvent(ClickEvent.copyToClipboard(message))
                        .hoverEvent(HoverEvent.showText(MessageHandler.copyMsg("#fe7eff")))
                );

        // Local delivery + Velocity handoff must run on the main thread.
        Bukkit.getScheduler().runTask(plugin, () -> sendGlobalMsg(finalMessage, player));
        WebhookGlobalChat.send(
                config.getString("webhook"),
                rawMessage,
                PlayerAvatarUrl.getAvatarUrl(player),
                player.getName()
        );
    }

    private static void sendGlobalMsg(Component message, Player player) {
        Bukkit.broadcast(message);

        if (!player.isOnline()) {
            return;
        }

        try {
            byte[] componentBytes = SERIALIZER.serialize(message).getBytes(StandardCharsets.UTF_8);

            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF(PACKET_CHAT);
            out.writeInt(componentBytes.length);
            out.write(componentBytes);

            player.sendPluginMessage(Main.getInstance(), Main.CHANNEL, out.toByteArray());
        } catch (Exception e) {
            Main.getInstance().getLogger().log(Level.WARNING, "Failed to send global chat plugin message", e);
        }
    }

    @Override
    public void onPluginMessageReceived(@NonNull String channel, @NonNull Player player, byte @NonNull [] message) {
        if (!Main.CHANNEL.equals(channel)) {
            return;
        }

        try {
            ByteArrayDataInput in = ByteStreams.newDataInput(message);
            String packetType = in.readUTF();
            if (!PACKET_CHAT.equals(packetType)) {
                return;
            }

            int length = in.readInt();
            if (length <= 0 || length > 1_048_576) {
                Main.getInstance().getLogger().warning("Ignored global chat packet with invalid payload length: " + length);
                return;
            }

            byte[] payload = new byte[length];
            in.readFully(payload);

            Component component = SERIALIZER.deserialize(new String(payload, StandardCharsets.UTF_8));

            // Display only — never forward incoming Velocity messages back out.
            Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                for (Player online : Bukkit.getOnlinePlayers()) {
                    online.sendMessage(component);
                }
            });
        } catch (Exception e) {
            Main.getInstance().getLogger().log(Level.WARNING, "Failed to handle incoming global chat packet", e);
        }
    }
}
