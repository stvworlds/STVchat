package net.maksim6opka.STVchat.handlers;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class WebhookGlobalChat {
    public static void send(String webhookUrl, String content, String avatarUrl, String nickname) {
        try {
            URL url = new URL(webhookUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String json = "{"
                    + "\"content\":\"" + content.replace("\"", "\\\"") + "\","
                    + "\"username\":\"" + nickname.replace("\"", "\\\"") + "\","
                    + "\"avatar_url\":\"" + avatarUrl + "\""
                    + "}";


            try (OutputStream os = connection.getOutputStream()) {
                os.write(json.getBytes(StandardCharsets.UTF_8));
            }

            connection.getInputStream().close(); // виконує запит
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
