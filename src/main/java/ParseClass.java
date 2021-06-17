import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ParseClass {

    Document document;
    Element element;
    String review;

    // To load all Topics and Link in Map
    // TopicLinkNope --> topic , link
    // Map --> index , topic , link
    public Map<Integer, TopicLinkNode> getTopicList(String page) {
        Map<Integer, TopicLinkNode> topics = new HashMap<Integer, TopicLinkNode>();
        document = Jsoup.parse(page);

        int i = 1;
        TopicLinkNode node;
        Elements elements = document.body().select("li.browse-by-list-item > a");
        for (Element element : elements) {
            node = new TopicLinkNode(element.select("button").text(),
                    element.select("a").attr("href").trim());
            topics.put(i, node);
            i++;
        }
        return topics;
    }

    // To load Reviews
    public void loadReviews(String html, String topic)  {

        document = Jsoup.parse(html);

        // To get all reviews
        Elements elements = document.body()
                .select(".search-results-item-body");
        for (Element each : elements) {
            element = each;

            review = getLink() + " | " + topic + " | " + getTitle() + " | " + getAuthor() + " | " + getDate();
            System.out.println(review);
        }
    }

    public Map<Integer,String> getLinks() {
        Map<Integer,String> links = new LinkedHashMap<Integer, String>();
        int i=1;
        Elements elements = document.body()
                .select("li.pagination-page-list-item > a");
        for (Element element : elements ) {
            links.put(i,element.attr("href"));
            i++;
        }
        return links;
    }

    // To get Author
    public String getAuthor() {
        return element.selectFirst("div.search-result-authors").text();
    }

    //To get Date
    public String getDate() {
        String date = element.select("div.search-result-date > div").text();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
            Date d = sdf.parse(date);
            sdf.applyPattern("yyyy-MM-dd");
            date = sdf.format(d);
        } catch (ParseException e) {
            System.out.println("Failed to get date...");
        }
        return date;
    }

    //To gate Link
    private String getLink() {
        return "https://www.cochranelibrary.com" + element.select("a").attr("href");
    }

    //To get Title
    public String getTitle() {
        return element.selectFirst("a").text();
    }




}