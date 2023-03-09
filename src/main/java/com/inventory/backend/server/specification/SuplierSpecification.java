package com.inventory.backend.server.specification;

import java.util.Arrays;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.inventory.backend.server.base.BaseSpecification;
import com.inventory.backend.server.model.Suplier;

@Component
public class SuplierSpecification extends BaseSpecification<Suplier> {
	@Override
	public Specification<Suplier> containsTextInOmni(String text) {
		return containsTextInAttributes(text,
				Arrays.asList("name", "address", "phone"));
	}
}
