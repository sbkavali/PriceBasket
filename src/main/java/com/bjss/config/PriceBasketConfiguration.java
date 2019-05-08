package com.bjss.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.bjss.repo.PromotionsRepository;
import com.bjss.repo.StockRepository;
import com.bjss.service.PriceBasketService;
import com.bjss.service.impl.PriceBasketServiceImpl;

/**
 * The Price Basket app configuration with virtual repository classes for Stock
 * and Promotions
 * 
 * @author Suresh
 *
 */
@Configuration
public class PriceBasketConfiguration {

	@Bean
	public PriceBasketService priceBasketService() {
		return new PriceBasketServiceImpl();
	}

	@Bean("stockRepository")
	public StockRepository stockRepository() {
		return new StockRepository();
	}

	@DependsOn("stockRepository")
	@Bean("promotionsRepository")
	public PromotionsRepository promotionsRepository() {
		return new PromotionsRepository();
	}
}
