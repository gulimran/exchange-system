package imran.dao.impl;

import imran.dao.OpenOrdersDao;
import imran.model.Direction;
import imran.model.Order;
import imran.model.ReutersInstrumentCode;

import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

import static imran.dao.impl.DataSource.OPEN_ORDERS;

@Named
public class OpenOrderDaoInMemoryImpl implements OpenOrdersDao {

    @Override
    public void save(Order order) {
        OPEN_ORDERS.add(order);
    }

    @Override
    public List<Order> findBy(ReutersInstrumentCode ric) {
        return OPEN_ORDERS.stream().filter(p ->
                p.getReutersInstrumentCode().equals(ric))
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findBy(ReutersInstrumentCode ric, Direction direction) {
        return OPEN_ORDERS.stream().filter(p -> (
                p.getReutersInstrumentCode().equals(ric)) && p.getDirection().equals(direction))
                .collect(Collectors.toList());
    }

    @Override
    public void remove(Order order) {
        OPEN_ORDERS.remove(order);
    }
}
