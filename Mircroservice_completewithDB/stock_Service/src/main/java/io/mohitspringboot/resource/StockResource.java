package io.mohitspringboot.resource;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.quotes.stock.StockQuote;

@RestController
@RequestMapping("/rest/stock")
public class StockResource {

	@Autowired
	RestTemplate resttemp;
	
	
	
	@GetMapping("/{username}")
	public List<Quote> getStock(@PathVariable("username") String UserName)
	{
	
		System.out.println("UserNameUserNameUserNameUserName"+UserName);
				
		ResponseEntity<List<String>> quoteResponse= resttemp.exchange("http://db-service/rest/db/"+UserName
				, HttpMethod.GET,null, new ParameterizedTypeReference<List<String>>() {
				});
		
		System.out.println("quoteResponsequoteResponse"+quoteResponse);
		
			List<String> quotes=quoteResponse.getBody();
			
			System.out.println("quotesquotesquotes"+quotes);
			
			return quotes.stream()
					//.map(this::getStockPrice)
					.map(quote -> {
						Stock stock = getStockPrice(quote);
						System.out.println("quote________"+quote);
						System.out.println("aaaaaaaaaaaaaaaaaaaa"+stock.getQuote() + stock.getQuote());
					return new Quote(quote, stock.getQuote().getPrice());
					})
					.collect(Collectors.toList());
			
	
	}



	private Stock getStockPrice(String quote) {
		try {
			return	YahooFinance.get(quote);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Stock(quote);
		}
	}

	public class Quote
	{
		private String quote;
		private BigDecimal price = new BigDecimal("1212.3");
		private String stockExchange;
		
		
		public String getQuote() {
			return quote;
		}

		public void setQuote(String quote) {
			this.quote = quote;
		}

	

		public Quote(String quote, BigDecimal price) {
			super();
			this.quote = quote;
			this.price=price;
		}

		public BigDecimal getPrice() {
			return price;
		}

		public void setPrice(BigDecimal price) {
			this.price = price;
		}
		
		
	}
	
}
