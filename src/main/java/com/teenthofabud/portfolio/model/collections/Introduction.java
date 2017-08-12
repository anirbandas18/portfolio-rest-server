package com.teenthofabud.portfolio.model.collections;

import com.teenthofabud.portfolio.model.embeddeddocuments.Employee;
import com.teenthofabud.portfolio.model.fields.Profile;

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
