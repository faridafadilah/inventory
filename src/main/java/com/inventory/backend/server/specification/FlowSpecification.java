package com.inventory.backend.server.specification;

import java.util.Arrays;

import javax.persistence.criteria.Join;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.inventory.backend.server.base.BaseSpecification;
import com.inventory.backend.server.model.Flow;
import com.inventory.backend.server.model.Flow_;
import com.inventory.backend.server.model.ListBarang;
import com.inventory.backend.server.model.ListBarang_;

@Component
public class FlowSpecification extends BaseSpecification<Flow> {
	@Override
	public Specification<Flow> containsTextInOmni(String text) {
		return containsTextInAttributes(text,
				Arrays.asList("nameRecipients", "status", "date"));
	}

	public Specification<Flow> barangEqual(long id) {
		return (Specification<Flow>) (root, cq, cb) -> {
				if (id == 0) {
						return null;
				} else {
						Join<Flow, ListBarang> barangJoin = root.join(Flow_.LISTBARANG);
						return cb.equal(barangJoin.get(ListBarang_.ID), id);
				}
		};
}
}