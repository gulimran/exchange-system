package imran.matcher;

import imran.model.Order;

import javax.inject.Named;

@Named
public class QuantityMatcher {

    public Boolean matches(Order newOrder, Order existingOrder) {
        return (existingOrder.getQuantity().equals(newOrder.getQuantity()));
    }
}
