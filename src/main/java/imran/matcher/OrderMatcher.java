package imran.matcher;

import imran.model.Direction;
import imran.model.Order;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Named
public class OrderMatcher {

    @Inject
    private DirectionMatcher directionMatcher;

    @Inject
    private ReutersInstrumentCodeMatcher reutersInstrumentCodeMatcher;

    @Inject
    private QuantityMatcher quantityMatcher;

    @Inject
    private PriceMatcher priceMatcher;

    public Optional<Order> matchOrder(Order newOrder, List<Order> openOrders) {
        List<Order> matchedOrders = openOrders.stream()
                .filter(openOrder -> (directionMatcher.matches(openOrder, newOrder)
                        && (reutersInstrumentCodeMatcher.matches(openOrder, newOrder))
                        && quantityMatcher.matches(openOrder, newOrder))
                        && priceMatcher.matches(openOrder, newOrder))
                .collect(toList());

        if (matchedOrders.isEmpty()) {
            return Optional.empty();
        }

        if (matchedOrders.size() == 1) {
            return Optional.of(matchedOrders.get(0));
        }

        if (newOrder.getDirection().equals(Direction.SELL)) {
            return matchedOrders.stream().max(new OrderComparator());
        } else {
            return matchedOrders.stream().min(new OrderComparator());
        }
    }

    class OrderComparator implements Comparator<Order> {
        @Override
        public int compare(Order o1, Order o2) {
            if (o1.getPrice().getAmount() > o2.getPrice().getAmount()) {
                return 1;
            } else if (o1.getPrice().getAmount() < o2.getPrice().getAmount()) {
                return -1;
            } else {
                return 0;
            }
        }
    }

}
