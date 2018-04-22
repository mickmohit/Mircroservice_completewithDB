package io.mohitspringboot.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.mohitspringboot.repo.Quote;
import io.mohitspringboot.repo.QuoteRepository;
import io.mohitspringboot.repo.Quotes;

@RestController
@RequestMapping("/rest/db")
public class DBServiceResource {

	private QuoteRepository quotesRepository;
	
	@GetMapping("/{username}")
	public List<String> getQuotes( @PathVariable("username") String username ){
	
		return getQuoteByUserName(username);
		
		//return null;
	}


	private List<String> getQuoteByUserName(String username) {
		return quotesRepository.findByUserName(username).stream().map(quote->{return quote.getQuote();})
		.collect(Collectors.toList());
	}
	
	
	@PostMapping("/add")
	//@RequestMapping(method=RequestMethod.POST, value="/add")
	public List<String> add(@RequestBody final Quotes quotes){
		//return null;
		quotes.getQuotes().stream().forEach(quote->
		{
			quotesRepository.save(new Quote(quotes.getUserName(),quote));
		});
		
		return getQuoteByUserName(quotes.getUserName());
	}


	public DBServiceResource(QuoteRepository quotesRepository) {
		super();
		this.quotesRepository = quotesRepository;
	}
	
	
	@PostMapping("/delete/{username}")
	public boolean delete (@PathVariable("username") String username)
	{
	
		List<Quote> quotes = quotesRepository.findByUserName(username);
		quotesRepository.deleteInBatch(quotes);
		return true;
		
	}
	
	
}
