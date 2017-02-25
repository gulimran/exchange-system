# Exchange System

Implement an exchange system which matches orders on stocks. The functionality implemented is:

##  Add an order
- An order consists of direction (buy/sell), RIC (Reuters Instrument Code), quantity, price and user
- When an order is added, it is compared against existing open orders to see whether it can be matched. Two orders match if they have opposing directions, matching RICs and quantities, and if the sell price is less than or equal to the buy price
- When two orders are matched they are said to be ‘executed’, and the price at which they are executed (the execution price) is the price of the newly added order
- If there are multiple matching orders at the same price for a new order, it should be matched against the earliest matching existing orders
- If there are multiple matching orders at different prices for a new sell order, it should be matched against the order with the highest price
- If there are multiple matching orders at different prices for a new buy order, it should be matched against the order with the lowest price
- Executed orders are removed from the set of open orders against which new orders can be matched

## Provide open interest for a given RIC and direction
- Open interest is the total quantity of all open orders for the given RIC and direction at each price point

## Provide the average execution price for a given RIC
- The average execution price is the average price per unit of all executions for the given RIC

## Provide executed quantity for a given RIC and user
- Executed quantity is the sum of quantities of executed orders for the given RIC and user. The quantity of sell orders should be negated

# Implementation

Implementing an exchange system which matches orders on stocks:

## API Interface

    imran.api.OrderService

## Packaging

    Maven jar, build creates "exchange-system.jar"

## Testing

    Cucumber acceptance test feature file is "imran.integration.features.exchange_system.feature".

# Design

-    Dependency injection - JSR-330 annotations powered by Spring 4
-    Data persistence - In-memory collection
-    Domain model - defined in package "imran.model"
-    Data access objects - to persist Open Orders and Executed Orders.
-    Service - implements the API "OrderService" interface, defined in package "imran.service.impl"
-    Matcher - filtering used for matching orders that can potentially be executed, defined in package "imran.matcher"
-    Unit testing - using JUnit and Mockito

# Assumptions

    The following aspects are not considered:
-        Validation
-        Logging
-        Error scenarios

    Criteria for determining the earliest matching order is not defined.  Since there is no example of this in
    the use cases provided, this has not been implemented.  This functionality can be added in the OrderMatcher.

    The average execution price is calculated by adding executed orders and diving by the number of executed orders.
    For example, average execution price = (100.2 + 103) / 2 = 101.6
                 average execution price = (100.2 + 103 + 98) / 3 = 100.3999
    This is different from the examples provided.  Please consider that I do not have any financial background.

    The case of Open Interest where there are multiple buy or sell orders at the same price point is not implemented.
    Therefore, in such case, the last value provided will overwrite the existing one.

    Unit testing of all classes of interest.  A hundred percent code coverage is not considered.
