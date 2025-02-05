/**
 *
 */
package org.adempiere.util.email;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;

public class EmailValidator
{

	/**
	 * Validate email using apache commons validator
	 *
	 * @param email email addresse for validation
	 */
	public static boolean validate(@Nullable final String email)
	{
		return validate(email, null);
	}

	/**
	 * @param clazz optional, may be {@code null}. If a class is given and the given {@code email} is not valid,
	 *              then this method instantiates and throws an exception with message {@code "@EmailNotValid@"}.
	 */
	public static boolean validate(@Nullable final String email, @Nullable final Class<? extends RuntimeException> clazz)
	{
		final boolean emailValid = isValid(email);
		if (!emailValid && clazz != null)
		{
			// initiate and throw our exception
			try
			{
				throw clazz.getConstructor(String.class).newInstance("@EmailNotValid@");
			}
			catch (final InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e)
			{
				throw new RuntimeException("Unable to instantiate a " + clazz + " exception", e);
			}
		}
		return emailValid;
	}

	public static boolean isValid(@Nullable final String email)
	{
		return org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(email);
	}
}
