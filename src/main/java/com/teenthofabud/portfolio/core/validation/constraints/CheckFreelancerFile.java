package com.teenthofabud.portfolio.core.validation.constraints;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.teenthofabud.portfolio.core.FreelancerFileValidator;
import com.teenthofabud.portfolio.core.constants.FreelancerFile;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Constraint(validatedBy = FreelancerFileValidator.class)
@Documented
public @interface CheckFreelancerFile {
	
	String message() default "{com.teenthofabud.portfolio.core.validation.constraints.checkfiletype}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
    FreelancerFile type();

}
