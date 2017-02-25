package imran.dao;

import imran.model.Direction;
import imran.model.Order;
import imran.model.ReutersInstrumentCode;

import java.util.List;

public interface OpenOrdersDao {

    void save(Order order);

    void remove(Order order);

    List<Order> findBy(ReutersInstrumentCode ric);

    List<Order> findBy(ReutersInstrumentCode ric, Direction direction);
}
