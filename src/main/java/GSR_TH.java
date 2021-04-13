import java.net.URI;
import java.net.URISyntaxException;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import sun.misc.Signal;
import sun.misc.SignalHandler;

public class GSR_TH extends WebSocketClient {
    private OrderBook _orderBook;
    private String _ticker;
    public GSR_TH(String ticker, URI serverURI) {
        super(serverURI);
        _orderBook = new OrderBook();
        _ticker = ticker;
    }
    @Override
    public void onOpen(ServerHandshake handshakedata) {
        String str = String.format("{\n" +
                "    \"type\": \"subscribe\",\n" +
                "    \"channels\": [\n" +
                "        \"level2\",\n" +
                "        \"heartbeat\",\n" +
                "        {\n" +
                "            \"name\": \"ticker\",\n" +
                "            \"product_ids\": [\n" +
                "                \"%s\"\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}", _ticker);
        send(str);
        System.out.println("opened connection");
    }

    @Override
    public void onMessage(String message) {
        _orderBook.updateBook(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println(
                "Connection closed by " + (remote ? "remote peer" : "us") + " Code: " + code + " Reason: "
                        + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    public static void main(String[] args) throws URISyntaxException  {

        String ticker;
        if(args.length < 1) {
            System.out.println("Cannot proceed, Ticker Not provided");
            return;
        }
        ticker = args[0];
        String sand_box_url = "wss://ws-feed-public.sandbox.pro.coinbase.com";
        String prod_url = "wss://ws-feed.pro.coinbase.com";
        GSR_TH c = new GSR_TH(ticker, new URI(prod_url));
        c.connect();
    }

}