import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

public class Sample {
    public static void main(String[] args) throws IOException {
        StringBuilder urlBuilder =
                new StringBuilder("http://api.koreafilm.or.kr/openapi-data2/" +
                        "wisenut/search_api/search_json2.jsp?" +
                        "collection=kmdb_new2&nation=대한민국");
        urlBuilder.append("?" +
                URLEncoder.encode("ServiceKey", "UTF-8")+"=서비스키"); /*Service Key*/
        urlBuilder.append("&" +
                URLEncoder.encode("val001", "UTF-8")
                + "=" + URLEncoder.encode("2018", "UTF-8")); /*상영년도*/
        urlBuilder.append("&" +
                URLEncoder.encode("val002", "UTF-8")
                + "=" + URLEncoder.encode("01", "UTF-8")); /*상영 월*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
    }
}
