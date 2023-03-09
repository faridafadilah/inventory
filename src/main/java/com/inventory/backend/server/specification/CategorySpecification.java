package com.inventory.backend.server.specification;

import java.util.Arrays;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.inventory.backend.server.base.BaseSpecification;
import com.inventory.backend.server.model.Category;

@Component
public class CategorySpecification extends BaseSpecification<Category> {
	@Override
	public Specification<Category> containsTextInOmni(String text) {
		return containsTextInAttributes(text,
				Arrays.asList("category"));
	}
}