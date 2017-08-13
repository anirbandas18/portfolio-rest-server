package com.teenthofabud.portfolio.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.teenthofabud.portfolio.model.collections.Freelancer;
import com.teenthofabud.portfolio.model.collections.Profile;

@JsonRootName(value = "introduction")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class IntroductionVO {
	
	private Profile to;
	private Freelancer of;
	public Profile getTo() {
		return to;
	}
	public void setTo(Profile to) {
		this.to = to;
	}
	public Freelancer getOf() {
		return of;
	}
	public void setOf(Freelancer of) {
		this.of = of;
	}

}
