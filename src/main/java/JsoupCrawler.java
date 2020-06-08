import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class JsoupCrawler extends Thread {

    static Document loadDocumentFromURL(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        return doc;
    }
    static void getTitle(String url) throws IOException {
        Document document = loadDocumentFromURL(url);
        String title = document.title();
        System.out.println(title);
    }

    public void run () {
        try{
            String msg = Consumer.message;
            String[] message = msg.split(" ");
            String url = message[0];
            getTitle(url);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
