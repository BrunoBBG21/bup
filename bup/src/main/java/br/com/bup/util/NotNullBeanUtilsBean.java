/**
 * 
 */
package br.com.bup.util;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;

/**
 * @author andreluisdionisio
 *
 */
public class NotNullBeanUtilsBean extends BeanUtilsBean {
	
	/**
	 * 
	 */
	public NotNullBeanUtilsBean() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param convertUtilsBean
	 */
	public NotNullBeanUtilsBean(ConvertUtilsBean convertUtilsBean) {
		super(convertUtilsBean);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param convertUtilsBean
	 * @param propertyUtilsBean
	 */
	public NotNullBeanUtilsBean(ConvertUtilsBean convertUtilsBean, PropertyUtilsBean propertyUtilsBean) {
		super(convertUtilsBean, propertyUtilsBean);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void copyProperty(Object dest, String name, Object value) throws IllegalAccessException, InvocationTargetException {
		if (value == null || (value instanceof List && ((List<?>) value).isEmpty()))
			return;
		super.copyProperty(dest, name, value);
	}
	
	/**
	 * Gets the instance which provides the functionality for {@link BeanUtils}. This is a pseudo-singleton - an single instance
	 * is provided per (thread) context classloader. This mechanism provides isolation for web apps deployed in the same
	 * container.
	 *
	 * @return The (pseudo-singleton) BeanUtils bean instance
	 */
	public static NotNullBeanUtilsBean getInstance() {
		return new NotNullBeanUtilsBean();
	}
}
