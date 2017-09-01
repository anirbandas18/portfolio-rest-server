package com.teenthofabud.portfolio.configuration;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;


@Configuration
public class QueryByExampleConfiguration {
	
	@Bean
	public ExampleMatcher matcher() {
        return ExampleMatcher.matchingAny()
                .withStringMatcher(StringMatcher.CONTAINING)
                .withIgnoreCase()
                .withIgnoreNullValues();
    }
	
	@Bean
	public Sort asc() {
		return new Sort(Direction.ASC, Arrays.asList("id"));
	}

	@Bean
	public Sort desc() {
		return new Sort(Direction.DESC, Arrays.asList("id"));
	}
	
}
