package com.bjss.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * The helper class to format currency for sale and discounts
 * 
 * @author Suresh
 *
 */
public class CurrencyUtil {

	private static final String MINOR_CURRENCY_TEXT = "%sp";

	/**
	 * Format the sale price in to currency. It is assumed the value will be a
	 * positive value.
	 * 
	 * @param value the sale price (subtotal/total)
	 * @return the currency formatted value (show value in minor currency if value
	 *         is less than £1 and format in to major currency if the value is more
	 *         than £1)
	 */
	public static String formatMonetaryValue(BigDecimal value) {
		if (BigDecimal.ONE.compareTo(value) > 0) {
			return String.format(MINOR_CURRENCY_TEXT,
					value.multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.HALF_UP));
		}

		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.UK);
		return currencyFormat.format(value);
	}

	/**
	 * Format the discount price in to currency. The value will be prefixed with
	 * negate symbol. It is assumed the value will be a positive value.
	 * 
	 * @param value the discount price
	 * @return the currency formatted value with negate symbol (show value in minor
	 *         currency if value is less than £1 and format in to major currency if
	 *         the value is more than £1)
	 */
	public static String formatMonetaryValueForDiscount(BigDecimal value) {
		
		if (BigDecimal.ONE.compareTo(value) > 0) {
			return String.format(MINOR_CURRENCY_TEXT,
					value.multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.HALF_UP).negate());
		}

		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.UK);
		return currencyFormat.format(value.negate());
	}
}
