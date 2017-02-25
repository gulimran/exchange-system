package imran.acceptance.data;


import com.google.common.collect.Lists;
import imran.model.Order;

import java.util.List;

import static imran.builder.OrderBuilder.order;

public class TestData {

    public static List<Order> ORDERS() {
        Order order1 = order("SELL", "VOD.L", "User1", 100.2, 1000);
        Order order2 = order("BUY", "VOD.L", "User2", 100.2, 1000);
        Order order3 = order("BUY", "VOD.L", "User1", 99.0, 1000);
        Order order4 = order("BUY", "VOD.L", "User1", 101.0, 1000);
        Order order5 = order("SELL", "VOD.L", "User2", 102.0, 500);
        Order order6 = order("BUY", "VOD.L", "User1", 103.0, 500);
        Order order7 = order("SELL", "VOD.L", "User2", 98.0, 1000);

        return Lists.newArrayList(order1, order2, order3, order4, order5, order6, order7);
    }
}
