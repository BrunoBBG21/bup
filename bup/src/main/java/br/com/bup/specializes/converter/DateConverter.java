package br.com.bup.specializes.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.enterprise.inject.Specializes;
import javax.inject.Inject;

import br.com.caelum.vraptor.Convert;

import com.google.common.base.Strings;

@Convert(Date.class)
@Specializes
public class DateConverter extends br.com.caelum.vraptor.converter.DateConverter {
	private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy hh:mm aa", Locale.getDefault());
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected DateConverter() {
		this(null);
	}
	
	@Inject
	public DateConverter(Locale locale) {
		super(locale);
	}
	
	@Override
	public Date convert(String value, Class<? extends Date> type) {
		if (Strings.isNullOrEmpty(value)) {
			return null;
		}
		
		try {
			return SDF.parse(value);
			
		} catch (ParseException pe) {
			return super.convert(value, type);
		}
	}
}
