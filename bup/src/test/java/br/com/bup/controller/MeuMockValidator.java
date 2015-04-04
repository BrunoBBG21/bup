package br.com.bup.controller;

import javax.validation.Validation;

import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.caelum.vraptor.validator.Validator;

public class MeuMockValidator extends MockValidator {
    
	@Override
	public Validator validate(Object object, Class<?>... groups) {
		javax.validation.Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		this.addAll(validator.validate(object));
		return this;
	}
}
