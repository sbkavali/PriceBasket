package com.bjss;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bjss.data.PriceBasketSummary;
import com.bjss.exception.InvalidPromotionException;
import com.bjss.exception.NoItemsInShoppingBasketException;
import com.bjss.exception.NoSuchItemException;
import com.bjss.service.PriceBasketService;

@SpringBootApplication
public class PriceBasketApplication implements CommandLineRunner {

	@Autowired
	public PriceBasketService priceBasketService;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(PriceBasketApplication.class);
		app.setBannerMode(Banner.Mode.OFF);

		app.run(args);

	}

	/**
	 * The command line runner
	 */
	@Override
	public void run(String... args) throws Exception {
		try {
			printPriceBasket(priceBasketService.processPriceBasket(args));
		} catch (NoItemsInShoppingBasketException | NoSuchItemException ex) {
			handleException(ex);
		} catch (InvalidPromotionException ipEx) {
			System.out.println("Invalid promotions found: " + ipEx.getMessage());
		}

	}

	/**
	 * Print the Price Basket Summary
	 * 
	 * @param priceBasketSummary the price basket summary after calculating Totals
	 *                           and applying promotions
	 */
	private void printPriceBasket(PriceBasketSummary priceBasketSummary) {
		System.out.println(priceBasketSummary.getBasketSummaryString());
	}

	/**
	 * Handle the error by printing the error message and program usage
	 * 
	 * @param ex the exception thrown by price basket processor
	 */
	private static void handleException(Exception ex) {
		System.out.println("Error while processing request: " + ex.getMessage());
		System.out.println("Usage: java -jar PriceBasket-0.0.1-SNAPSHOT.jar item1 item2 item3...");
		System.out.println("The allowed item names are Apples, Milk, Bread and Soup");
	}

}
