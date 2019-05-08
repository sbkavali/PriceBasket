package com.bjss.data;

import static com.bjss.helper.CurrencyUtil.formatMonetaryValue;

import java.math.BigDecimal;

/**
 * The Summary class for price basket, which contains all totals and methods to
 * return summary information
 * 
 * @author Suresh
 *
 */
public class PriceBasketSummary {

	public static final String EMPTY_BASKET = "(your basket is empty)";
	public static final String SUB_TOTAL = "Subtotal: %s";
	public static final String TOTAL = "Total: %s";
	public static final String NEW_LINE = System.getProperty("line.separator");

	/**
	 * The shopping cart contains all the chosen items.
	 */
	private ShoppingBasket shoppingBasket;

	/**
	 * The subtotal of all the items in shopping basket
	 */
	private BigDecimal subTotal;

	/**
	 * The element contains all the promotions applied on basket.
	 */
	private PromotionSummary promotionsSummary;

	/**
	 * Initialise the Price basket summary with shopping basket
	 * 
	 * @param shoppingBasket
	 */
	public PriceBasketSummary(ShoppingBasket shoppingBasket) {
		this.shoppingBasket = shoppingBasket;
		this.subTotal = BigDecimal.ZERO;
		promotionsSummary = new PromotionSummary();
		calculateSubTotal();
	}

	/**
	 * Calculates subtotal of shopping cart
	 */
	private void calculateSubTotal() {
		shoppingBasket.getCart()
				.forEach((key, value) -> subTotal = subTotal.add(key.getPrice().multiply(BigDecimal.valueOf(value))));
	}

	/**
	 * @return the Subtotal line to print
	 */
	public String getSubTotalString() {
		return String.format(SUB_TOTAL, formatMonetaryValue(subTotal));
	}

	/**
	 * The method calculates total of shopping cart by subtracting promotion amount
	 * (if any) from subtotal
	 * 
	 * @return the Price Basket total
	 */
	public BigDecimal getTotal() {
		return subTotal.subtract(promotionsSummary.getTotalDiscount());
	}

	/**
	 * @return the price basket total line to print
	 */
	public String getTotalString() {
		return String.format(TOTAL, formatMonetaryValue(getTotal()));
	}

	/**
	 * @return the price basket summary to print - Including Subtotal, promotions(if
	 *         any) and Total
	 */
	public String getBasketSummaryString() {
		return getSubTotalString() + NEW_LINE + promotionsSummary.getPromotionSummaryString() + getTotalString();
	}

	/**
	 * @return the shopping cart
	 */
	public ShoppingBasket getShoppingBasket() {
		return shoppingBasket;
	}

	/**
	 * @return the summary of promotions applied on the basket
	 */
	public PromotionSummary getPromotionsSummary() {
		return promotionsSummary;
	}

	/**
	 * Basic validation to check the total of basket should be a positive value
	 */
	public void validate() {
		if (getTotal().compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalStateException("The value of shopping basket can not be negative!!");
		}
	}
}
