package imran.matcher;

import imran.model.Order;

import javax.inject.Named;

@Named
public class ReutersInstrumentCodeMatcher {

    public Boolean matches(Order newOrder, Order existingOrder) {
        return (existingOrder.getReutersInstrumentCode().equals(newOrder.getReutersInstrumentCode()));
    }
}
