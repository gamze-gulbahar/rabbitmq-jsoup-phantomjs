
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class Producer {

    private static final String EXCHANGE_NAME = "linklogs";
    private static final String QUEUE_NAME = "link";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("rmq");
        factory.setPassword("rmq");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            channel.queueDeclare(QUEUE_NAME,true,false,false,null);

            String url = "https://stackoverflow.com/questions/13445589/jsoup-thread-safety";
            String message = String.join(" ", url, argv[0]);

            for(int i=0; i<10; i ++){
                channel.basicPublish(EXCHANGE_NAME,QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
                System.out.println(" [x] Sent '" + message + "'");
            }
        }
    }
}
