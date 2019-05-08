package com.bjss.data;

import static com.bjss.helper.CurrencyUtil.formatMonetaryValueForDiscount;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.bjss.promo.Promotion;

/**
 * The class with summary of promotions applied on shopping basket
 * 
 * @author Suresh
 *
 */
public class PromotionSummary {

	public static final String NO_PROMOTIONS_APPLIED = "(no offers available)";
	public static final String NEW_LINE = System.getProperty("line.separator");

	/**
	 * Promotions applied and their corresponding discount value.
	 */
	private Map<Promotion, BigDecimal> promotionsApplied = new HashMap<>();

	/**
	 * Total discount of promotions
	 */
	private BigDecimal totalDiscount;

	/**
	 * Instantiate promotions summary
	 */
	public PromotionSummary() {
		totalDiscount = BigDecimal.ZERO;
	}

	/**
	 * Method populates the promotions applied
	 * 
	 * @param promotion      the promotion applied
	 * @param promotionValue the discount value
	 */
	public void addAppliedPromotion(Promotion promotion, BigDecimal promotionValue) {
		promotionsApplied.put(promotion, promotionValue);
		totalDiscount = totalDiscount.add(promotionValue);
	}

	/**
	 * @return the aggregate of discounts applied
	 */
	public BigDecimal getTotalDiscount() {
		return totalDiscount;
	}

	/**
	 * @return the promotions summary to print in console
	 */
	public String getPromotionSummaryString() {
		if (promotionsApplied == null || promotionsApplied.isEmpty()) {
			return NO_PROMOTIONS_APPLIED + NEW_LINE;
		}

		StringBuffer promotionsSummary = new StringBuffer();

		promotionsApplied.forEach((key, value) -> promotionsSummary.append(key.getSummaryDescription())
				.append(formatMonetaryValueForDiscount(value)).append(NEW_LINE));

		return promotionsSummary.toString();
	}

}
