package imran.api;

import imran.model.*;

import java.util.Optional;

public interface OrderService {

    /**
     * Adds a new @{link Order} to the exchange system.  The order is executed if it matches with an
     * existing open order.
     *
     * @param order - the @{link Order} to be added.
     * @return - an optional @{ExecutedOrder}, if the new order matches with an open order.
     */
    Optional<ExecutedOrder> addOrder(Order order);

    /**
     * Gets @{OpenInterest} which is the total quantity of all open orders for the given RIC and
     * direction at each price point.
     *
     * @param reutersInstrumentCode - the @{ReutersInstrumentCode} or RIC
     * @param direction - the @{Direction} of the order, either Buy or Sell.
     * @return - an optional @{OpenInterest}, if open order is available for the given parameters.
     */
    Optional<OpenInterest> getOpenInterest(ReutersInstrumentCode reutersInstrumentCode, Direction direction);

    /**
     * Gets the @{AverageExecutionPrice}, which is the average price per unit of
     * all executions for the given RIC.
     *
     * @param reutersInstrumentCode - the @{ReutersInstrumentCode} or RIC
     * @return - an optional @{AverageExecutionPrice}, if there are any executed orders.
     */
    Optional<AverageExecutionPrice> getAverageExecutionPrice(ReutersInstrumentCode reutersInstrumentCode);

    /**
     * Gets the @{ExecutedQuantity}, which is the sum of quantities of executed orders for
     * the given RIC and user. The quantity of sell orders is negated.
     *
     * @param reutersInstrumentCode - the @{ReutersInstrumentCode} or RIC
     * @param user - the @{User} who placed the order.
     * @return the sum of quantities of executed orders.
     */
    Optional<ExecutedQuantity> getExecutedQuantity(ReutersInstrumentCode reutersInstrumentCode, User user);
}
