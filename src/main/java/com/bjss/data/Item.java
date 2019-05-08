package com.bjss.data;

import java.math.BigDecimal;

/**
 * The class to represent items in stock
 * 
 * @author Suresh
 *
 */
public class Item {
	
	private String name;
	
	private BigDecimal price;

	public Item(String name, BigDecimal price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}


	public BigDecimal getPrice() {
		return price;
	}

}
