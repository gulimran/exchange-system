Feature: exchange system
  As an exchange system operator
  I want to add orders on stocks
  So that I can provide interest, price and quantity

  Scenario: Add an order consists of direction (buy/sell), RIC (Reuters Instrument Code), quantity, price and user
    Given an order consists of direction, RIC, quantity, price and user
    When the order is added into empty database
    Then I should get no executed order

  Scenario: Add multiple orders consists of direction (buy/sell), RIC (Reuters Instrument Code), quantity, price and user
    Given multiple orders consists of direction, RIC, quantity, price and user
    When the orders are added into empty database
    Then I should get executed orders with expected execution price
    And verify open interest after each order
    And verify average execution price after each order
    And verify executed quantity for each user after each order