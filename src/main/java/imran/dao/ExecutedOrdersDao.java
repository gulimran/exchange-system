package imran.dao;

import imran.model.ExecutedOrder;
import imran.model.ReutersInstrumentCode;
import imran.model.User;

import java.util.List;

public interface ExecutedOrdersDao {

    void save(ExecutedOrder order);

    List<ExecutedOrder> findBy(ReutersInstrumentCode ric);

    List<ExecutedOrder> findBy(ReutersInstrumentCode ric, User user);
}
