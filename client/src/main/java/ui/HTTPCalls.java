package ui;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPCalls {

    public static String executeHTTP(String requestMethod, String msg){
        try{
            var url = new URL("http://localhost:8080/echo/ + msg");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestMethod);
            connection.setDoOutput(true);
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
