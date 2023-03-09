package com.inventory.backend.server.specification;

import java.util.Arrays;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.inventory.backend.server.base.BaseSpecification;
import com.inventory.backend.server.model.Logging;

@Component
public class LoggerSpecification extends BaseSpecification<Logging> {
	@Override
	public Specification<Logging> containsTextInOmni(String text) {
		return containsTextInAttributes(text,
				Arrays.asList("method", "date", "url", "error", "username"));
	}
}

