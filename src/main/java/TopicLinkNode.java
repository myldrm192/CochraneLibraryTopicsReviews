import org.jsoup.select.Elements;

public class TopicLinkNode{

        String topic;
        String link;

        public TopicLinkNode(String topic, String link) {
            this.topic = topic;
            this.link = link;
        }

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

}
