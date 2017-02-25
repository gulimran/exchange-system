package imran.dao.impl;

import imran.model.ExecutedOrder;
import imran.model.Order;

import java.util.HashSet;
import java.util.Set;

/**
 * In-Memory Database.  Remove this class when RDBMS is used.
 */
public class DataSource {

    public static Set<Order> OPEN_ORDERS = new HashSet<>();

    public static Set<ExecutedOrder> EXECUTED_ORDERS = new HashSet<>();
}
