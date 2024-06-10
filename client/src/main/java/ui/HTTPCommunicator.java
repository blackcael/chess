package ui;

import com.google.gson.Gson;
import intermediary.ResponseCodeAndObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;

public class HTTPCommunicator {
    private final int port;
    public HTTPCommunicator(int port){
        this.port = port;
    }

    public ResponseCodeAndObject executeHTTP(String requestMethod, String urlPath, String body, String authToken, Class clazz){
        try{
            //configure path
            URI uri = new URI("http://localhost:" + port + urlPath);
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod(requestMethod);
            connection.setDoOutput(true);

            //set authToken
            if(authToken != null) {
                connection.addRequestProperty("Authorization", authToken);
            }

            //write out body
            if(body != null){
                try (var outputStream = connection.getOutputStream()) {
                    outputStream.write(body.getBytes());
                }
            }

            //execute connection
            connection.connect();

            //receive response
            int responseCode = connection.getResponseCode();
            return switch(responseCode){
                case 200 -> new ResponseCodeAndObject(responseCode, getRespBody(connection, clazz));
                case 400 -> new ResponseCodeAndObject(responseCode, "Error: bad request");
                case 401 -> new ResponseCodeAndObject(responseCode, "Error: unauthorized");
                case 403 -> new ResponseCodeAndObject(responseCode, "Error: already taken");
                case 500 -> new ResponseCodeAndObject(responseCode, "Error: otherError");
                default -> new ResponseCodeAndObject(501, "Error: otherError");
            };

        } catch (Exception e) {
            throw new RuntimeException("Execute HTTPCalls error: " + e.getMessage());
        }


    }
    private Object getRespBody(HttpURLConnection connection, Class clazz) throws Exception{
        if(clazz == null) return null;
        return new Gson().fromJson(readRespBody(connection), clazz);
    }

    private String readRespBody(HttpURLConnection connection) throws Exception{
        try(InputStream respBody = connection.getInputStream()){
            byte[] bytes = new byte[respBody.available()];
            respBody.read(bytes);
            return new String(bytes);
        }
    }
}
