import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    private static final String EXCHANGE_NAME = "linklogs";
    private static final String QUEUE_NAME = "link";
    public static String message = "";


    public static void main (String[] argv) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("rmq");
        factory.setPassword("rmq");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            message = new String(delivery.getBody(), "UTF-8");

            if(message.endsWith("jsoup")){
                JsoupCrawler jsoupCrawler = new JsoupCrawler();
                jsoupCrawler.start();
            }else if (message.endsWith("phantomjs")) {
                PhantomJSCrawler phantomJSCrawler = new PhantomJSCrawler();
                phantomJSCrawler.start();
            }//System.out.println(" [x] Received '" + message + "'");

        };
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });


    }
}


