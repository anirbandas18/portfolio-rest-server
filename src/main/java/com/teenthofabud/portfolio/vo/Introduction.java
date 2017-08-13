package com.teenthofabud.portfolio.vo;

import com.teenthofabud.portfolio.model.collections.Employee;
import com.teenthofabud.portfolio.model.collections.Profile;

public class Introduction {
	
	private Profile to;
	private Employee of;
	public Profile getTo() {
		return to;
	}
	public void setTo(Profile to) {
		this.to = to;
	}
	public Employee getOf() {
		return of;
	}
	public void setOf(Employee of) {
		this.of = of;
	}

}
