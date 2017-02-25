package imran.builder;

import imran.model.*;

public class OrderBuilder {

    public static OrderBuilder OrderBuilder = new OrderBuilder();

    private Direction direction;
    private ReutersInstrumentCode reutersInstrumentCode;
    private User user;
    private Price price;
    private Integer quantity;

    public OrderBuilder direction(String direction) {
        this.direction = Direction.valueOf(direction);
        return this;
    }

    public OrderBuilder reutersInstrumentCode(String reutersInstrumentCode) {
        this.reutersInstrumentCode = new ReutersInstrumentCode(reutersInstrumentCode);
        return this;
    }

    public OrderBuilder user(String user) {
        this.user = new User(user);
        return this;
    }

    public OrderBuilder price(Double price) {
        this.price = new Price(price);
        return this;
    }

    public OrderBuilder quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public Order build() {
        Order order = new Order();
        order.setDirection(direction);
        order.setReutersInstrumentCode(reutersInstrumentCode);
        order.setUser(user);
        order.setPrice(price);
        order.setQuantity(quantity);
        return order;
    }


    public static Order order(String direction, String ric, String user, Double price, Integer quantity) {
        return OrderBuilder.direction(direction).reutersInstrumentCode(ric).user(user).price(price).quantity(quantity).build();
    }
}
