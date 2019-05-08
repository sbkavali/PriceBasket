# PriceBasket
## Problem Summary
**PriceBaket** is an application with associated tests that can price a basket of goods, accounting for special offers.

The goods that can be purchased, which are all priced in GBP, are:
- Soup – 65p per tin
- Bread – 80p per loaf
- Milk – £1.30 per bottle
- Apples – £1.00 per bag

Current special offers are:
- Apples have 10% off their normal price this week
- Buy 2 tins of soup and get a loaf of bread for half price


# Solution

**PriceBaket** is an application with implementation logic for pricing the basket and applying promotional offers. The solution also contains associated tests for PriceBasket scenarios.

The solution is a Spring Boot application backed by CommandLine Runner, so the application can be run from console.

### Usage: java -jar PriceBasket-0.0.1-SNAPSHOT.jar item1 item2 item3...
**Note: The allowed item names are Apples, Milk, Bread and Soup (case-insensitive)**

For example: java -jar PriceBasket-0.0.1-SNAPSHOT.jar Apples Milk Bread

**Output should be to the console, for example:**

Subtotal: £3.10
Apples 10% off: -10p
Total: £3.00

**If no special offers are applicable, the code should output:**

Subtotal: £1.30
(no offers available)
Total: £1.30


### Class Summary:

***PriceBasketApplication*** - Entry point to the PriceBucket application.
***PriceBucketService/Impl*** - The service class which queries dummy Stock and Promotions repositories and calculates price bucket costs.
***StockRepository / PromotionsRepository*** - The virtual repositories with predefined stock items and promotions.

### Alternative approach

***PriceBasketApplication*** has been implemented using CommandLineRunner, alternativey the application can be implemented as a Shell component.

