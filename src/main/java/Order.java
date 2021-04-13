public class Order {
    private double _bid;
    private double _ask;
    private String _time;
    private Order _prevOrder;
    public Order(Order prevOrder) {
        System.out.println("Order created");
        _bid = 0.0;
        _ask = 0.0;
        _prevOrder = prevOrder;
    }

    public void printOrder(){
        System.out.println(String.format("Bid: %f, Ask: %f, Time: %s", _bid, _ask, _time));
    }

    public void updateOrder(double bid, double ask, String time){
        _bid = bid;
        _ask = ask;
        _time = time;
    }

    public void updateOrder(){
        if(_prevOrder != null){
            _bid = _prevOrder._bid;
            _ask = _prevOrder._ask;
            _time = _prevOrder._time;
        }
    }
}
