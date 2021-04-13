import java.util.ArrayList;
import com.oracle.javafx.jmx.json.JSONException;
import org.json.JSONObject;

public class OrderBook {
    ArrayList<Order> _orders;
    private int ptr;
    public OrderBook() {
        _orders = new ArrayList<Order>();

    }

    public void updateBook(String data){
        //{"type":"ticker","sequence":21274507,"product_id":"ETH-BTC","price":"0.03571","open_24h":"0.03595","volume_24h":"728.50685154","low_24h":"0.03558","high_24h":"0.03617","volume_30d":"19166.78370592","best_bid":"0.03571","best_ask":"0.03573","side":"sell","time":"2021-04-12T11:29:03.643541Z","trade_id":2733143,"last_size":"0.01"}
        double bid = 0.0;
        double ask = 0.0;
        String time;
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(data);
            String msg_type = jsonObject.getString("type");
            if(!msg_type.equals("ticker")){
                System.out.println("Unhandled message type");
                return;
            }
            bid = Double.parseDouble(jsonObject.getString("best_bid"));
            ask = Double.parseDouble(jsonObject.getString("best_ask"));
            time = jsonObject.getString("time");
        }catch (JSONException err){
            System.out.println(err.toString());
            return;
        }
        if(_orders.size() < 10){
            //keep adding until we reach 10
            int prevIndex = _orders.size() - 1;
            Order ot = _orders.isEmpty() ? new Order(null) : new Order(_orders.get(prevIndex));
            _orders.add(ot);
        }
        for(int i = _orders.size() - 1; i >= 1; --i){
            _orders.get(i).updateOrder();
        }
        _orders.get(0).updateOrder(bid, ask, time);
        printOrderBook();
    }

    private void printOrderBook(){
        for(int i = 0; i < _orders.size(); ++i){
            _orders.get(i).printOrder();
        }
        System.out.println("-------------------------");
    }

}
