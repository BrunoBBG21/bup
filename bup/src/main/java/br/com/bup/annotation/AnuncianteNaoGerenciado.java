package br.com.bup.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotation que indica quando um metodo de um controller só pode ser acessado por um Usuario não gerenciado.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AnuncianteNaoGerenciado {
}