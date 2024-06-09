package ui;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;

public class HTTPCalls {

    public static String executeHTTP(String requestMethod, String urlPath, String body, String authToken){
        try{
            URI uri = new URI("http://localhost:8080" + urlPath);
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod(requestMethod);
            connection.setDoOutput(true);
            if(authToken != null) {
                connection.addRequestProperty("Authorization", authToken);
            }
            //write out body
            if(body != null){
                try (var outputStream = connection.getOutputStream()) {
                    outputStream.write(body.getBytes());
                }
            }
            connection.connect();

            try(InputStream respBody = connection.getInputStream()){
                byte[] bytes = new byte[respBody.available()];
                respBody.read(bytes);
                return new String(bytes);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
