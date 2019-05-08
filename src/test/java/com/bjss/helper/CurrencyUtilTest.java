package com.bjss.helper;

import static com.bjss.helper.CurrencyUtil.formatMonetaryValue;
import static com.bjss.helper.CurrencyUtil.formatMonetaryValueForDiscount;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.bjss.BaseTest;

/**
 * Test formatting of monetary values
 * 
 * @author Suresh
 *
 */
@RunWith(SpringRunner.class)
public class CurrencyUtilTest extends BaseTest {

	/**
	 * Test formatting sale value as currency
	 */
	@Test
	public void testFormatMonetaryValue() {

		String result1 = formatMonetaryValue(BigDecimal.ZERO);
		assertEquals("Unexpected formatted value for zero", "0p", result1);

		BigDecimal lessThanOne = BigDecimal.valueOf(0.20);
		String result2 = formatMonetaryValue(lessThanOne);
		assertEquals("Unexpected formatted value for zero", "20p", result2);

		String result3 = formatMonetaryValue(BigDecimal.ONE);
		assertEquals("Unexpected formatted value for zero", "£1.00", result3);

		String result4 = formatMonetaryValue(BigDecimal.valueOf(2.50));
		assertEquals("Unexpected formatted value for zero", "£2.50", result4);
	}

	/**
	 * Test formatting discount value as currency
	 */
	@Test
	public void testFormatMonetaryValueForDiscount() {

		String result1 = formatMonetaryValueForDiscount(BigDecimal.ZERO);
		assertEquals("Unexpected formatted value for zero", "0p", result1);

		BigDecimal lessThanOne = BigDecimal.valueOf(0.20);
		String result2 = formatMonetaryValueForDiscount(lessThanOne);
		assertEquals("Unexpected formatted value for zero", "-20p", result2);

		String result3 = formatMonetaryValueForDiscount(BigDecimal.ONE);
		assertEquals("Unexpected formatted value for zero", "-£1.00", result3);

		String result4 = formatMonetaryValueForDiscount(BigDecimal.valueOf(2.50));
		assertEquals("Unexpected formatted value for zero", "-£2.50", result4);
	}

}
