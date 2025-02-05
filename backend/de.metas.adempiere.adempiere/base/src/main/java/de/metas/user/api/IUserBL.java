package de.metas.user.api;

import de.metas.email.EMailAddress;
import de.metas.email.mailboxes.UserEMailConfig;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.Language;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.ISingletonService;
import de.metas.util.hash.HashableString;
import lombok.NonNull;
import org.compiere.model.I_AD_User;

import javax.annotation.Nullable;

public interface IUserBL extends ISingletonService
{
	I_AD_User getById(@NonNull UserId userId);

	I_AD_User getByPasswordResetCode(@NonNull String passwordResetCode);

	String extractUserLogin(I_AD_User user);

	HashableString extractUserPassword(I_AD_User user);

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	boolean isPasswordMatching(I_AD_User user, HashableString password);

	void createResetPasswordByEMailRequest(String userId);

	I_AD_User resetPassword(String passwordResetCode, String newPassword);

	/**
	 * Change given user's password.
	 */
	void changePassword(ChangeUserPasswordRequest request);

	void changePasswordAndSave(I_AD_User user, String newPassword);

	static String buildContactName(@Nullable final String firstName, @Nullable final String lastName)
	{
		final StringBuilder contactName = new StringBuilder();
		if (lastName != null && !Check.isBlank(lastName))
		{
			contactName.append(lastName.trim());
		}

		if (firstName != null && !Check.isBlank(firstName))
		{
			if (contactName.length() > 0)
			{
				contactName.append(", ");
			}
			contactName.append(firstName.trim());
		}

		return contactName.toString();
	}

	/**
	 * Is the email valid
	 *
	 * @return return true if email is valid (artificial check)
	 */
	boolean isEMailValid(I_AD_User user);

	Language getUserLanguage(@NonNull UserId userId);

	/**
	 * @return the user's language or fallbacks; never returns {@code null}.
	 */
	Language getUserLanguage(I_AD_User userRecord);

	UserEMailConfig getEmailConfigById(UserId userId);

	ExplainedOptional<EMailAddress> getEMailAddressById(@NonNull UserId userId);

	void deleteUserDependency(I_AD_User userRecord);
}
