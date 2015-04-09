package br.com.bup.validator;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.bup.annotation.EmailDisponivel;
import br.com.bup.dao.UsuarioDAO;

public class EmailDisponivelValidator implements ConstraintValidator<EmailDisponivel, String> {

    @Inject 
    private UsuarioDAO usuarioDAO;

    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !usuarioDAO.existeComEmail(email);
    }

	public void initialize(EmailDisponivel constraintAnnotation) {
	}
}