package com.bdx.rainbow.mapp.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.bdx.rainbow.mapp.model.bean.TYdzfInspectCaseEvidenceSampleMB;
import com.bdx.rainbow.mapp.model.req.YDZF0033Request;
import com.bdx.rainbow.mapp.model.req.YDZF0035Request;

public class Test {
	
	public static void main(String[] args) {
		YDZF0033Request d = new YDZF0033Request();
		 ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
	        Validator validator = vf.getValidator();
	        Set<ConstraintViolation<YDZF0033Request>> set = validator.validate(d);
	        for (ConstraintViolation<YDZF0033Request> constraintViolation : set) {
	            System.out.println(constraintViolation.getMessage());
	        }
	}

}
