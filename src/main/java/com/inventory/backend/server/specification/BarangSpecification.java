package com.inventory.backend.server.specification;

import java.util.Arrays;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.inventory.backend.server.base.BaseSpecification;
import com.inventory.backend.server.model.ListBarang;

@Component
public class BarangSpecification extends BaseSpecification<ListBarang> {
	@Override
	public Specification<ListBarang> containsTextInOmni(String text) {
		return containsTextInAttributes(text,
				Arrays.asList("description", "name"));
	}
}