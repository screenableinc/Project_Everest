package reply_cirlce.screenable.project_everest;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Retention;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class AccessApi {
    private HttpURLConnection conn;
    private Globals globals = new Globals();

    public String sendPost(String url, String parameters) throws Exception {

        URL obj = new URL(url);
        conn = (HttpURLConnection) obj.openConnection();

        // Acts like a browser
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        byte[] out = parameters .getBytes(StandardCharsets.UTF_8);
        int length = out.length;
        conn.setRequestProperty("User-Agent", "Android app");
        conn.setRequestProperty("Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        conn.setConnectTimeout(5000);
//        for (String cookie : this.cookies) {
//            conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
//        }
        conn.setRequestProperty("Connection", "keep-alive");
        conn.setRequestProperty("Referer", url);
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setFixedLengthStreamingMode(length);

        conn.setDoOutput(true);
        conn.setDoInput(true);

        // Send post request
       OutputStream wr = conn.getOutputStream();
        wr.write(out);
        wr.flush();
        wr.close();

        int responseCode = conn.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + parameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in =
                new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        // System.out.println(response.toString());

        return response.toString();

    }

    public String sendGET(String url, String[] parameters, String[] values) throws Exception {

        String queryString = GenQueryString(parameters, values);
        URL obj = new URL(url+"?"+queryString);

        conn = (HttpURLConnection) obj.openConnection();

        // Acts like a browser
        conn.setUseCaches(false);
        conn.setRequestMethod("GET");

        conn.setRequestProperty("User-Agent", "Android app");
        conn.setRequestProperty("Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        conn.setConnectTimeout(5000);
//        for (String cookie : this.cookies) {
//            conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
//        }
        conn.setRequestProperty("Connection", "keep-alive");
        conn.setRequestProperty("Referer", url);
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");


        conn.setDoOutput(true);
        conn.setDoInput(true);


        int responseCode = conn.getResponseCode();


        BufferedReader in =
                new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        // System.out.println(response.toString());

        return response.toString();

    }

    public String GenQueryString(String[] params, String[] values)
            throws UnsupportedEncodingException {





        // build parameters list
        StringBuilder result = new StringBuilder();
        for (int i = 0; i<params.length;i++) {
            if (result.length() == 0) {
                result.append(params[i] + "=" +URLEncoder.encode(values[i],"UTF-8") );
            } else {
                result.append("&" + params[i]+"="+URLEncoder.encode(values[i],"UTF-8") );
            }
        }
        Log.w("WWWWWWWW", result.toString());
        return result.toString();
    }
}
