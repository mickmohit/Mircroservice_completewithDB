package io.mohitspringboot.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Quote,Integer> {

	List<Quote> findByUserName(String userName);

}
