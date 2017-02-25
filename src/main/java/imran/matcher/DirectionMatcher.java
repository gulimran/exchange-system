package imran.matcher;

import imran.model.Order;

import javax.inject.Named;

import static imran.model.Direction.BUY;
import static imran.model.Direction.SELL;

@Named
public class DirectionMatcher {

    public Boolean matches(Order newOrder, Order existingOrder) {
        return (existingBuyAndNewSell(newOrder, existingOrder)) || (existingSellAndNewBuy(newOrder, existingOrder));
    }

    private Boolean existingBuyAndNewSell(Order newOrder, Order existingOrder) {
        return existingOrder.getDirection() == BUY && newOrder.getDirection() == SELL;
    }

    private Boolean existingSellAndNewBuy(Order newOrder, Order existingOrder) {
        return existingOrder.getDirection() == SELL && newOrder.getDirection() == BUY;
    }
}
