import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import java.io.IOException;

public class Handler {

    CloseableHttpClient httpClient;
    HttpContext httpContext;
    String content;
    CloseableHttpResponse response;

    public String getHTML(String url){

        // Create Configurations
        RequestConfig config = RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD)
                        .setCircularRedirectsAllowed(true)
                        .build();

        // Create HttpClient
        httpClient = HttpClientBuilder.create()
                        .setDefaultRequestConfig(config)
                        .setMaxConnPerRoute(100)
                        .build();
        //For Cookie
        CookieStore cookieStore = new BasicCookieStore();
            httpContext = new BasicHttpContext();
            httpContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);


        // Create HttpGet
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent", "Whatever");


        try {
            response = httpClient.execute(httpGet,httpContext);
            System.out.println(response.getStatusLine());

            HttpEntity entity = response.getEntity();
            content = EntityUtils.toString(entity);


        } catch (IOException e) {
            e.printStackTrace();

        }finally {

            // Close the HTTP response
            if(response != null) {
                try {
                    response.close();
                    httpClient.close();
                } catch(IOException e) {

                }
            }
        }

        httpGet.releaseConnection();

        return content;
    }
}
