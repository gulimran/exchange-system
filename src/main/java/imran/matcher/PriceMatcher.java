package imran.matcher;

import imran.model.Order;

import javax.inject.Named;

import static imran.model.Direction.BUY;
import static imran.model.Direction.SELL;

@Named
public class PriceMatcher {

    public Boolean matches(Order newOrder, Order existingOrder) {
        return (newOrderSellPriceLessThanOrEqualToBuy(newOrder, existingOrder))
                || (existingOrderSellPriceLessThanOrEqualToBuy(newOrder, existingOrder));
    }

    private Boolean newOrderSellPriceLessThanOrEqualToBuy(Order newOrder, Order existingOrder) {
        return (existingOrder.getDirection() == BUY && newOrder.getDirection() == SELL)
                && (existingOrder.getPrice().getAmount() >= newOrder.getPrice().getAmount());
    }

    private Boolean existingOrderSellPriceLessThanOrEqualToBuy(Order newOrder, Order existingOrder) {
        return (existingOrder.getDirection() == SELL && newOrder.getDirection() == BUY)
                && (existingOrder.getPrice().getAmount() <= newOrder.getPrice().getAmount());
    }
}
