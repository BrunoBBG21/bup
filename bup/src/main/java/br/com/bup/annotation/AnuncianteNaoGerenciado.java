package br.com.bup.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotation que indica quando um metodo de um controller s� pode ser acessado por um Usuario n�o gerenciado.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AnuncianteNaoGerenciado {
}