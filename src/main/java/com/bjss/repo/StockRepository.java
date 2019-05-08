package com.bjss.repo;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bjss.data.Item;
import com.bjss.exception.NoSuchItemException;

/**
 * A virtual repository class for Stock.
 * 
 * @author Suresh
 *
 */
public class StockRepository {

	private Logger logger = LoggerFactory.getLogger(StockRepository.class);

	/**
	 * The items available in stock
	 */
	private Map<String, Item> stockItems;

	/**
	 * Populate items into repository
	 */
	@PostConstruct
	public void init() {
		// Tree map has been taken with a case insensitive order so the items can be
		// added and retrieved with case safety
		Map<String, Item> tempStock = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		tempStock.put("Apples", new Item("Apples", BigDecimal.ONE));
		tempStock.put("Bread", new Item("Bread", BigDecimal.valueOf(0.80)));
		tempStock.put("Milk", new Item("Milk", BigDecimal.valueOf(1.30)));
		tempStock.put("Soup", new Item("Soup", BigDecimal.valueOf(0.65)));

		stockItems = Collections.unmodifiableMap(tempStock);
	}

	/**
	 * Get item by name
	 * 
	 * @param itemName the item name
	 * @return the item by it's name. Throws exception when item doesn't exist
	 */
	public Item getByName(String itemName) {
		Item item = stockItems.get(itemName);

		if (item == null) {
			logger.error("The item - {} does not exist in our Stock!", itemName);
			throw new NoSuchItemException("No such item with the name - " + itemName);
		}

		return item;

	}
}
