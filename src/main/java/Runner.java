import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class Runner {

    public static void main(String[] args) {

        Handler handler = new Handler();
        ParseClass parseClass = new ParseClass();

        String url = "https://www.cochranelibrary.com/cdsr/reviews/topics";

        try {
            // To load topic html list
            String topicsPage = handler.getHTML(url);

            // To get all topics
            Map<Integer, TopicLinkNode> topics = parseClass.getTopicList(topicsPage);

            // To ask to choose one of the topics
            System.out.println("Browse by Topic");
            for (int index : topics.keySet()) {
                System.out.println("# " + index + " - " + topics.get(index).getTopic());
            }
            System.out.println("****************************************");

            System.out.print("Enter topic number to get reviews:\n");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String input = reader.readLine();

            System.out.println("****************************************");

            // To check valid input and exit if invalid
            //isNumeric() method is to check if the entered value is a digit
            if (isNumeric(input)) {

                int index = Integer.parseInt(input);

                if (index > 0 && index <= topics.size()) {

                    String topic = topics.get(index).getTopic();
                    String link = topics.get(index).getLink();

                    System.out.println("Your choice: " + topic);

                    // To load reviews of the first page
                    parseClass.loadReviews(handler.getHTML(link), topic);

                    // To load reviews of the other pages
                    Map<Integer,String> otherPage = parseClass.getLinks();

                    // First page loaded so index = 2;
                    for (int i =2;i<=otherPage.size(); i++) {

                        String html =handler.getHTML(otherPage.get(i));

                        System.out.println(otherPage.get(i));
                        parseClass.loadReviews(html,topic);
                    }
                }
            }
            System.out.println("********************** End of list **********************");

        } catch (IOException e) {
            System.out.println("There is a problem...");
        }
    }

    // To check if the entered value is a digit
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

}
