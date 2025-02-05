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
package org.compiere.apps.form;

import de.metas.adempiere.form.IClientUI;
import de.metas.logging.LogManager;
import de.metas.process.ProcessInfo;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.images.Images;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.plaf.MetasfreshGlassPane;
import org.compiere.apps.AEnv;
import org.compiere.apps.AMenu;
import org.compiere.apps.Help;
import org.compiere.apps.WindowMenu;
import org.compiere.apps.search.InfoWindowMenuBuilder;
import org.compiere.model.I_AD_Form;
import org.compiere.model.MTreeNode;
import org.compiere.swing.CFrame;
import org.compiere.util.Env;
import org.compiere.util.SwingUtils;
import org.compiere.util.Util;
import org.slf4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Properties;


/**
 *	Form Framework
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: FormFrame.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 *
 *  Colin Rooney 2007/03/20 RFE#1670185 & BUG#1684142
 *                           Extend security to Info Queries
 */
public class FormFrame extends CFrame
	implements ActionListener
{
	/**
	 *
	 */
	private static final long serialVersionUID = 2559005548469735515L;

	/**
	 *	Create Form.
	 *  Need to call openForm
	 */
	public FormFrame ()
	{
		super();
		addWindowListener(new java.awt.event.WindowAdapter()
		{
			@Override
			public void windowOpened(java.awt.event.WindowEvent evt)
			{
				formWindowOpened(evt);
			}
		});

		m_WindowNo = Env.createWindowNo (this);
		m_glassPane = MetasfreshGlassPane.install(this);
		try
		{
			jbInit();
			createMenu();
		}
		catch(Exception e)
		{
			log.error("Failed to initialize the form frame", e);

			// Dispose this FormFrame (to make sure the status is cleared)
			dispose();
		}
	}	//	FormFrame

	private ProcessInfo  m_pi;

	/**	WindowNo					*/
	private final int	m_WindowNo;
	/** The GlassPane           	*/
	private final MetasfreshGlassPane m_glassPane;
	/**	Description					*/
	private String		m_Description = null;
	/**	Help						*/
	private String		m_Help = null;
	/**	Menu Bar					*/
	private final JMenuBar 	menuBar = new JMenuBar();
	/**	The Panel to be displayed	*/
	private FormPanel 	m_panel = null;
	/** Maximize Window				*/
	public boolean 		m_maximize = false;
	/**	Logger			*/
	private static final Logger log = LogManager.getLogger(FormFrame.class);

	/** Form ID			*/
	private int		p_AD_Form_ID = -1;

	/**
	 * 	Static Init
	 * 	@throws Exception
	 */
	private void jbInit() throws Exception
	{
		final String iconName = MTreeNode.getIconName(MTreeNode.TYPE_WINDOW);
		final Image icon = Images.getImage2(iconName);
		if (icon != null)
		{
			this.setIconImage(icon);
		}

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setJMenuBar(menuBar);
	}	//	jbInit

	/**
	 *  Create Menu
	 */
	private void createMenu()
	{
		//      File
		JMenu mFile = AEnv.getMenu("File");
		menuBar.add(mFile);
		AEnv.addMenuItem("Report", null, KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.ALT_MASK), mFile, this);
		mFile.addSeparator();
		AEnv.addMenuItem("End", null, KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.ALT_MASK), mFile, this);
		AEnv.addMenuItem("Exit", null, KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.SHIFT_MASK+Event.ALT_MASK), mFile, this);

		//      View
		JMenu mView = AEnv.getMenu("View");
		menuBar.add(mView);
		InfoWindowMenuBuilder.newBuilder()
				.setCtx(Env.getCtx())
				// Provide a supplier for WindowNo because the windowNo is not available at this moment
				.setParentWindowNo(() -> getWindowNo())
				.setMenu(mView)
				.build();

		//      Tools
		JMenu mTools = AEnv.getMenu("Tools");
		menuBar.add(mTools);
		if (Env.getUserRolePermissions().isShowPreference())
		{
			if (mTools.getComponentCount() > 0)
				mTools.addSeparator();
			AEnv.addMenuItem("Preference", null, null, mTools, this);
		}

		//		Window
		AMenu aMenu = (AMenu)Env.getWindow(Env.WINDOW_MAIN);
		JMenu mWindow = new WindowMenu(aMenu.getWindowManager(), this);
		menuBar.add(mWindow);

		//      Help
		JMenu mHelp = AEnv.getMenu("Help");
		menuBar.add(mHelp);
		AEnv.addMenuItem("Help", null, KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0),	mHelp, this);
		AEnv.addMenuItem("Online", null, null, mHelp, this);
		AEnv.addMenuItem("About", null, null, mHelp, this);
	}   //  createMenu

	/**
	 *  Dispose
	 */
	@Override
	public final void dispose()
	{
		log.info("");

		//
		// Don't call dispose() again if the form was already disposed or is currently disposing
		// Else, more than one WINDOW_CLOSED event will be triggered, so listeners will be called more than once
		if (_disposing || _disposed)
		{
			return;
		}

		_disposing = true;
		try
		{
			// recursive calls
			if (m_panel != null) // [x] close window pressed
			{
				// 05749: removed ( Trace.isCalledFrom("JFrame") && ) from dispose logic
				m_panel.dispose();
			}
		}
		finally
		{
			m_panel = null;
			super.dispose();
			Env.clearWinContext(m_WindowNo);

			_disposing = false;
			_disposed = true;
		}
	}	// dispose

	/**
	 * true when frame is currently disposing
	 */
	private boolean _disposing = false;

	/**
	 * true when frame was already disposed
	 */
	private boolean _disposed = false;

	/**
	 * 	Opens the Form or displays a warning using {@link IClientUI#warn(int, Throwable)}.
	 *
	 * 	@param AD_Form_ID form
	 *  @return true if form opened
	 */
	public boolean openForm (final int AD_Form_ID)
	{
		boolean opened = false;
		try
		{
			final Properties ctx = Env.getCtx();
			final I_AD_Form form = InterfaceWrapperHelper.create(ctx, AD_Form_ID, I_AD_Form.class, ITrx.TRXNAME_None);
			if (form == null || form.getAD_Form_ID() <= 0 || form.getAD_Form_ID() != AD_Form_ID)
			{
				throw new AdempiereException("@NotFound@ @AD_Form_ID@ (ID="+AD_Form_ID+")");
			}

			opened = openForm0(form);
		}
		catch (Exception e)
		{
			opened = false;
			dispose();
			Services.get(IClientUI.class).warn(m_WindowNo, e);
		}

		return opened;
	}	//	openForm

	/**
	 * Opens the Form or displays a warning using {@link IClientUI#warn(int, Throwable)}.
	 *
	 * @param form
	 * @return true if form was opened
	 */
	// metas
	public boolean openForm(final I_AD_Form form)
	{
		boolean opened = false;
		try
		{
			opened = openForm0(form);
		}
		catch (Exception e)
		{
			opened = false;
			dispose();
			Services.get(IClientUI.class).warn(m_WindowNo, e);
		}

		return opened;
	}

	private boolean openForm0(final I_AD_Form form) throws Exception
	{
		Check.assumeNotNull(form, "form not null", form);

		final String className = form.getClassname();
		if (Check.isEmpty(className, true))
		{
			log.info("No className found for {}", form);
			return false;
		}
		log.info("AD_Form_ID=" + form + " - Class=" + className);

		final I_AD_Form formTrl = InterfaceWrapperHelper.translate(form, I_AD_Form.class);
		p_AD_Form_ID = form.getAD_Form_ID(); // metas: moved this line before init because we need to provide actual form ID - BF3212894
		m_Description = formTrl.getDescription();
		m_Help = formTrl.getHelp();

		final Properties ctx = InterfaceWrapperHelper.getCtx(form);
		Env.setContext(ctx, m_WindowNo, Env.CTXNAME_WindowName, formTrl.getName());
		Env.setContext(ctx, m_WindowNo, "AD_Form_ID", form.getAD_Form_ID());
		setTitle(Env.getHeader(ctx, m_WindowNo));

		// Create instance w/o parameters
		m_panel = Util.getInstance(FormPanel.class, className);
		m_panel.init(m_WindowNo, this);
		final boolean ok = (m_panel != null) && !_disposed; // metas: return true if not disposed

		// Make sure the form frame is added to window manager
		// (to be able to select it from windows list and to be able to close it automatically on user logout)
		if (ok)
		{
			AEnv.addToWindowManager(this);
		}

		return ok;
	}	// openForm

	/**
	 * 	Get Form Panel
	 *	@return form panel
	 */
	public FormPanel getFormPanel()
	{
		return m_panel;
	}	//	getFormPanel

	/**
	 * @param formPanelClass
	 * @return panel converted to given type
	 */
	public <T extends FormPanel> T getFormPanel(final Class<T> formPanelClass)
	{
		Check.assumeNotNull(formPanelClass, "Given class not null when attempting to convert {}", m_panel);
		Check.assumeInstanceOf(m_panel, formPanelClass, "panel");
		final T panelConv = formPanelClass.cast(m_panel);
		return panelConv;
	}

	/**
	 * 	Action Listener
	 * 	@param e event
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();
		if (cmd.equals("End"))
			dispose();
		else if (cmd.equals("Help"))
			actionHelp();
		else if (!AEnv.actionPerformed(cmd, m_WindowNo, this))
			log.error("Not handeled=" + cmd);
	}   //  actionPerformed

	/**
	 *	Show Help
	 */
	private void actionHelp()
	{
		StringBuffer sb = new StringBuffer();
		if (m_Description != null && m_Description.length() > 0)
			sb.append("<h2>").append(m_Description).append("</h2>");
		if (m_Help != null && m_Help.length() > 0)
			sb.append("<p>").append(m_Help);
		Help hlp = new Help (SwingUtils.getFrame(this), this.getTitle(), sb.toString());
		hlp.setVisible(true);
	}	//	actionHelp


	/*************************************************************************
	 *  Set Window Busy
	 *  @param busy busy
	 */
	public void setBusy (boolean busy)
	{
		if (busy == m_glassPane.isVisible())
			return;
		log.info("Busy=" + busy);
		if (busy)
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		else
			setCursor(Cursor.getDefaultCursor());
		m_glassPane.setMessage(null);
		m_glassPane.setVisible(busy);
		m_glassPane.requestFocus();
	}   //  setBusy

	/**
	 * Set Busy Message.
	 *
	 * If you want to directly set a message, without any transaction, use {@link #setMessagePlain(String)}.
	 *
	 * @param AD_Message to be translated - null resets to default message.
	 */
	public final void setBusyMessage (final String AD_Message)
	{
		m_glassPane.setMessage(AD_Message);
	}

	/**
	 * Sets given busy message directly. No transaction or any other processing will be performed.
	 *
	 * @param message plain message to be set directly.
	 */
	public final void setBusyMessagePlain(final String message)
	{
		m_glassPane.setMessagePlain(message);
	}

	/**
	 *  Set and start Busy Counter
	 *  @param time in seconds
	 */
	public void setBusyTimer (int time)
	{
		m_glassPane.setBusyTimer (time);
	}   //  setBusyTimer


	/**
	 * 	Set Maximize Window
	 *	@param max maximize
	 */
	public void setMaximize (boolean max)
	{
		m_maximize = max;
	}	//	setMaximize


	/**
	 * 	Form Window Opened.
	 * 	Maximize window if required
	 *	@param evt event
	 */
	private void formWindowOpened(java.awt.event.WindowEvent evt)
	{
		if (m_maximize == true)
		{
			super.setVisible(true);
			super.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
   }	//	formWindowOpened

// Add window and tab no called from

	public void setProcessInfo(ProcessInfo pi)
	{
		m_pi = pi;

	}

	public ProcessInfo getProcessInfo()
	{
		return m_pi;
	}

	// End

	/**
	 * 	Start Batch
	 *	@param process
	 *	@return running thread
	 */
	public Thread startBatch (final Runnable process)
	{
		Thread worker = new Thread()
		{
			@Override
			public void run()
			{
				setBusy(true);
				process.run();
				setBusy(false);
			}
		};
		worker.start();
		return worker;
	}	//	startBatch

	/**
	 * @return Returns the AD_Form_ID.
	 */
	public int getAD_Form_ID ()
	{
		return p_AD_Form_ID;
	}	//	getAD_Window_ID

	public int getWindowNo()
	{
		return m_WindowNo;
	}

	/**
	 * @return Returns the  manuBar
	 */
	public JMenuBar getMenu()
	{
		return menuBar;
	}

	public void showFormWindow()
	{
		if (m_panel instanceof FormPanel2)
		{
			((FormPanel2)m_panel).showFormWindow(m_WindowNo, this);
		}
		else
		{
			AEnv.showCenterScreenOrMaximized(this);
		}
	}
}	//	FormFrame
