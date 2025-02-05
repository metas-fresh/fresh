/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.apps;

import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.images.Images;
import org.compiere.swing.CButton;
import org.compiere.swing.CDialog;
import org.compiere.swing.CLabel;
import org.compiere.swing.CMenuItem;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextPane;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

/**
 * Dialog Windows
 *
 * @author Jorg Janke
 * @version $Id: ADialogDialog.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public final class ADialogDialog extends CDialog implements ActionListener
{
	/**
	 *
	 */
	private static final long serialVersionUID = 5670261006862936363L;

	// Services
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	/**
	 * Create Dialog Window for Frame and show it.
	 *
	 * @param frame
	 * @param title
	 * @param message
	 * @param messageType
	 */
	public ADialogDialog(final Frame frame, final String title, final String message, final int messageType)
	{
		this(frame, title);

		setMessage(message, messageType);

		showCenterScreen();
	}	// ADialogDialog

	public ADialogDialog(final Frame frame, final String title)
	{
		super(frame, title,
				frame != null // modal
		);
		init();
	}

	/**
	 * Create Dialog Window for Dialog and show it.
	 *
	 * @param dialog
	 * @param title
	 * @param message
	 * @param messageType
	 */
	public ADialogDialog(Dialog dialog, String title, String message, int messageType)
	{
		this(dialog, title);

		setMessage(message, messageType);

		showCenterScreen();
	}	// ADialogDialog

	public ADialogDialog(Dialog dialog, String title)
	{
		super(dialog, title,
				dialog != null // modal
		);

		init();
	}

	public void showCenterScreen()
	{
		AEnv.showCenterWindow(getOwner(), this);
	}

	/**
	 * Common Init
	 */
	private void init()
	{
		assertUIOutOfTransaction();

		try
		{
			jbInit();

			setInitialAnswer(A_OK); // backward compatibility: OK is the default
		}
		catch (Exception ex)
		{
			log.error("Dialog init failed: " + ex.getMessage(), ex);
		}
	}	// common

	/**
	 * Asserts dialog is opened out of transaction.
	 */
	public static final void assertUIOutOfTransaction()
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final String trxName = trxManager.getThreadInheritedTrxName(OnTrxMissingPolicy.ReturnTrxNone);
		if (!trxManager.isNull(trxName))
		{
			final AdempiereException ex = new AdempiereException("Opening a dialog while running in a trasaction it's always a bad idea"
					+ " because the database will be kept locked until the user will answer.");

			// NOTE: this issue is so critical that it's better to throw exception instead to just advice
			if (Services.get(IDeveloperModeBL.class).isEnabled())
			{
				throw ex;
			}
			else
			{
				// In case we run in production, we won't fail because that would be a show stopper, but we will log the exception.
				log.error(ex.getLocalizedMessage(), ex);
			}
		}
	}

	/**
	 * Sets message and messageType to be displayed.
	 *
	 * @param message
	 * @param messageType
	 */
	public void setMessage(final String message, final int messageType)
	{
		try
		{
			setInfoMessage(message);
			setInfoIcon(messageType);
		}
		catch (Exception ex)
		{
			log.error("Error while setting the message=" + message + ", messageType=" + messageType, ex);
		}
	}

	/**
	 * Window Events - requestFocus
	 *
	 * @param e
	 */
	@Override
	protected void processWindowEvent(final WindowEvent e)
	{
		super.processWindowEvent(e);

		//
		// When window is opened, focus on initial answer button
		if (e.getID() == WindowEvent.WINDOW_OPENED)
		{
			focusInitialAnswerButton();
		}
	}   // processWindowEvent

	/** Answer OK (0) */
	public static int A_OK = 0;
	/** Answer Cancel (1) */
	public static int A_CANCEL = 1;
	/** Answer Close (-1) - Default */
	public static int A_CLOSE = -1;
	/** Answer */
	private int m_returnCode = A_CLOSE;
	/** Logger */
	private static final Logger log = LogManager.getLogger(ADialogDialog.class);

	private int _initialAnswer = -100; // will be initialized on construction time

	static Icon i_inform = Images.getImageIcon2("Inform32");
	static Icon i_warn = Images.getImageIcon2("Warn32");
	static Icon i_question = Images.getImageIcon2("Question32");
	static Icon i_error = Images.getImageIcon2("Error32");

	private JMenuBar menuBar = new JMenuBar();
	private JMenu mFile = AEnv.getMenu("File");
	private CMenuItem mEnd = new CMenuItem();
	private CMenuItem mPreference = new CMenuItem();
	private ConfirmPanel confirmPanel = ConfirmPanel.builder()
			.withCancelButton(true)
			.build();
	private CPanel westPanel = new CPanel();
	private CLabel iconLabel = new CLabel();
	private GridBagLayout westLayout = new GridBagLayout();
	private CTextPane info = new CTextPane();
	private GridBagLayout infoLayout = new GridBagLayout();
	private CPanel infoPanel = new CPanel();

	/**
	 * Static Constructor
	 *
	 * @throws Exception
	 */
	private void jbInit() throws Exception
	{
		this.setJMenuBar(menuBar);
		confirmPanel.setActionListener(this); // set it first just to know buttons are working in case something failed
		//
		mPreference.setIcon(Images.getImageIcon2("Preference16"));
		mPreference.setText(msgBL.getMsg(Env.getCtx(), "Preference"));
		mPreference.addActionListener(this);
		mEnd.setIcon(Images.getImageIcon2("End16"));
		mEnd.setText(msgBL.getMsg(Env.getCtx(), "End"));
		mEnd.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.ALT_MASK));
		mEnd.addActionListener(this);
		//
		westPanel.setLayout(westLayout);
		westPanel.setName("westPanel");
		westPanel.setRequestFocusEnabled(false);
		infoPanel.setLayout(infoLayout);
		infoPanel.setName("infoPanel");
		infoPanel.setRequestFocusEnabled(false);
		this.getContentPane().add(confirmPanel, BorderLayout.SOUTH);
		this.getContentPane().add(westPanel, BorderLayout.WEST);
		westPanel.add(iconLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
				, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
		this.getContentPane().add(infoPanel, BorderLayout.CENTER);
		infoPanel.add(info, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
				, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
		//
		menuBar.add(mFile);
		if (Env.getUserRolePermissions().isShowPreference())
			mFile.add(mPreference);
		mFile.addSeparator();
		mFile.add(mEnd);
	}	// jbinit

	/**
	 * Convert to HTML and Calculate Size
	 *
	 * @param message message
	 */
	private void setInfoMessage(final String message)
	{
		final StringBuilder sb = new StringBuilder(message.length() + 20);
		sb.append("<b>");
		String html = StringUtils.maskHTML(message);
		char[] chars = html.toCharArray();
		boolean first = true;
		int paras = 0;
		for (int i = 0; i < chars.length; i++)
		{
			char c = chars[i];
			if (c == '\n')
			{
				if (first)
				{
					sb.append("</b>");
					first = false;
				}
				if (paras > 1)
					sb.append("<br>");
				else
					sb.append("<p>");
				paras++;
			}
			else
				sb.append(c);
		}
		info.setText(sb.toString());
		Dimension size = info.getPreferredSize();
		size.width = 450;
		size.height = (Math.max(paras, message.length() / 60) + 1) * 30;
		size.height = Math.min(size.height, 600);
		info.setPreferredSize(size);

		info.setRequestFocusEnabled(false);
		info.setReadWrite(false);
		info.setOpaque(false);
		info.setBorder(null);
		//
		info.setCaretPosition(0);
	}	// calculateSize

	/**************************************************************************
	 * Set Info
	 *
	 * @param messageType
	 */
	private void setInfoIcon(int messageType)
	{
		final CButton cancelButton = confirmPanel.getCancelButton();
		cancelButton.setVisible(false);
		cancelButton.setEnabled(false);
		//
		switch (messageType)
		{
			case JOptionPane.ERROR_MESSAGE:
				iconLabel.setIcon(i_error);
				break;
			case JOptionPane.INFORMATION_MESSAGE:
				iconLabel.setIcon(i_inform);
				break;
			case JOptionPane.QUESTION_MESSAGE:
				cancelButton.setVisible(true);
				cancelButton.setEnabled(true);
				iconLabel.setIcon(i_question);
				break;
			case JOptionPane.WARNING_MESSAGE:
				iconLabel.setIcon(i_warn);
				break;

			case JOptionPane.PLAIN_MESSAGE:
			default:
				break;
		}	// switch
	}	// setInfo

	/**************************************************************************
	 * ActionListener
	 *
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// log.trace( "ADialogDialog.actionPerformed - " + e);
		if (e.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			m_returnCode = A_OK;
			dispose();
		}
		else if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL) || e.getSource() == mEnd)
		{
			m_returnCode = A_CANCEL;
			dispose();
		}
		else if (e.getSource() == mPreference)
		{
			if (Env.getUserRolePermissions().isShowPreference())
			{
				final Preference p = new Preference(null, 0);
				p.setVisible(true);
			}
		}
	}

	/**
	 * Get Return Code
	 *
	 * @return return code ({@link #A_OK}, {@link #A_CANCEL}, {@link #A_CLOSE})
	 */
	public int getReturnCode()
	{
		return m_returnCode;
	}	// getReturnCode

	/**
	 * Sets initial answer (i.e. button that will be preselected by default).
	 *
	 * Please note that this is not the default answer that will be returned by {@link #getReturnCode()} if user does nothing (i.e. closes the window).
	 * It is just the preselectated button.
	 *
	 * @param initialAnswer {@link #A_OK}, {@link #A_CANCEL}.
	 */
	public void setInitialAnswer(final int initialAnswer)
	{
		// If the inial answer did not actual changed, do nothing
		if (this._initialAnswer == initialAnswer)
		{
			return;
		}

		//
		// Configure buttons accelerator (KeyStroke) and RootPane's default button
		final JRootPane rootPane = getRootPane();
		final CButton okButton = confirmPanel.getOKButton();
		final AppsAction okAction = (AppsAction)okButton.getAction();
		final CButton cancelButton = confirmPanel.getCancelButton();
		 final AppsAction cancelAction = (AppsAction)cancelButton.getAction();
		if (initialAnswer == A_OK)
		{
			okAction.setDefaultAccelerator();
			cancelAction.setDefaultAccelerator();
			rootPane.setDefaultButton(okButton);
		}
		else if (initialAnswer == A_CANCEL)
		{
			// NOTE: we need to set the OK's Accelerator keystroke to null because in most of the cases it is "Enter"
			// and we want to prevent user for hiting ENTER by mistake
			okAction.setAccelerator(null);
			cancelAction.setDefaultAccelerator();
			rootPane.setDefaultButton(cancelButton);
		}
		else
		{
			throw new IllegalArgumentException("Unknown inital answer: " + initialAnswer);
		}

		//
		// Finally, set the new inial answer
		this._initialAnswer = initialAnswer;
	}

	public int getInitialAnswer()
	{
		return this._initialAnswer;
	}

	/**
	 * Request focus on inital answer.
	 */
	private void focusInitialAnswerButton()
	{
		final CButton defaultButton;
		if (_initialAnswer == A_OK)
		{
			defaultButton = confirmPanel.getOKButton();
		}
		else if (_initialAnswer == A_CANCEL)
		{
			defaultButton = confirmPanel.getCancelButton();
		}
		else
		{
			return;
		}

		defaultButton.requestFocusInWindow();
	}
}	// ADialogDialog
