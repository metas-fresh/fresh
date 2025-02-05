/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.apps;

import de.metas.acct.api.IPostingRequestBuilder.PostImmediate;
import de.metas.acct.api.IPostingService;
import de.metas.adempiere.form.IClientUIInvoker.OnFail;
import de.metas.cache.CCache;
import de.metas.document.references.zoom_into.RecordWindowFinder;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.security.IUserRolePermissions;
import de.metas.security.permissions.Access;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.images.Images;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.apps.form.FormFrame;
import org.compiere.grid.ed.Calculator;
import org.compiere.grid.ed.Calendar;
import org.compiere.model.GridWindowVO;
import org.compiere.model.I_AD_Form;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.compiere.swing.CButton;
import org.compiere.swing.CFrame;
import org.compiere.swing.CMenuItem;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.SwingUtils;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.util.Set;

/**
 * Windows Application Environment and utilities
 *
 * @author Jorg Janke
 * @version $Id: AEnv.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 *
 * @author Colin Rooney (croo) & kstan_79 RFE#1670185
 * @author victor.perez@e-evolution.com
 * see FR [ 1966328 ] New Window Info to MRP and CRP into View http://sourceforge.net/tracker/index.php?func=detail&aid=1966328&group_id=176962&atid=879335
 *
 */
public final class AEnv
{
	/**
	 * Show window: de-iconify and bring it to front
	 *
	 * @author teo_sarca [ 1707221 ]
	 */
	public static void showWindow(final Window window)
	{
		if (isModal(window))
		{
			// We are dealing with a modal window that needs to be displayed now and wait for it's return
			showWindowNow(window);
		}
		else
		{
			// NOTE: if we are not invokingLater, then the window it's moved to front for ~1sec then it's moved back again
			EventQueue.invokeLater(() -> showWindowNow(window));
		}
	}

	/**
	 *
	 * @param window
	 * @return true if given window is modal
	 */
	private static boolean isModal(final Window window)
	{
		if (window instanceof java.awt.Dialog)
		{
			final java.awt.Dialog dialog = (Dialog)window;
			return dialog.isModal();
		}

		return false;
	}

	private static void showWindowNow(final Window window)
	{
		if (!window.isVisible())
		{
			window.setVisible(true);
		}

		if (window instanceof Frame)
		{
			final Frame f = (Frame)window;
			final int state = f.getExtendedState();
			if ((state & Frame.ICONIFIED) > 0)
			{
				f.setExtendedState(state & ~Frame.ICONIFIED);
			}
		}
		window.toFront();
	}

	/**
	 * Show in the center of the screen. (pack, set location and set visibility)
	 *
	 * @param window Window to position
	 */
	public static void showCenterScreen(final Window window)
	{
		positionCenterScreen(window);
		showWindow(window);
	}   // showCenterScreen

	/**
	 * Show Window as maximized.
	 *
	 * @param window
	 */
	public static void showMaximized(final Window window)
	{
		// NOTE: if we are not invokingLater, then the window it's moved to front for ~1sec then it's moved back again

		if (window instanceof Frame)
		{
			final Frame frame = (Frame)window;
			EventQueue.invokeLater(() -> {
				frame.pack();
				frame.setExtendedState(Frame.MAXIMIZED_BOTH);
				frame.setVisible(true);
				frame.toFront();
			});
		}
		else if (window instanceof Dialog)
		{
			final Dialog dialog = (Dialog)window;
			EventQueue.invokeLater(() -> {
				dialog.setLocation(0, 0);
				final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				dialog.setSize(screenSize.width, screenSize.height);
				dialog.setVisible(true);
				dialog.toFront();
			});
		}
	}

	/**
	 * Calls {@link #showCenterScreen(Window)} or {@link #showMaximized(Window)} beased on {@link Ini#P_OPEN_WINDOW_MAXIMIZED} flag.
	 *
	 * @param window
	 */
	public static void showCenterScreenOrMaximized(final Window window)
	{
		if (Ini.isPropertyBool(Ini.P_OPEN_WINDOW_MAXIMIZED))
		{
			showMaximized(window);
		}
		else
		{
			showCenterScreen(window);
		}
	}

	/**
	 * Position window in center of the screen
	 *
	 * @param window Window to position
	 */
	public static void positionCenterScreen(final Window window)
	{
		positionScreen(window, SwingConstants.CENTER);
	}	// positionCenterScreen

	/**
	 * Show in the center of the screen. (pack, set location and set visibility)
	 *
	 * @param window Window to position
	 * @param position SwingConstants
	 */
	public static void showScreen(final Window window, final int position)
	{
		positionScreen(window, position);
		showWindow(window);
	}   // showScreen

	/**
	 * Position window in center of the screen
	 *
	 * @param window Window to position
	 * @param position SwingConstants
	 */
	public static void positionScreen(final Window window, final int position)
	{
		// guard against
		// java.lang.NullPointerException: Cannot invoke "sun.awt.X11.XBaseWindow.isVisible()" because "parent" is null
		// see https://stackoverflow.com/q/75209745/1012103
		try
		{
			window.pack();
		}
		catch(final Exception e)
		{
			log.info("Unable to pack the window (known issue for java17 andd x2go). Will continue without packing it. window={}",window);
		}
		
		final Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
		// take into account task bar and other adornments
		final GraphicsConfiguration config = window.getGraphicsConfiguration();
		final Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(config);
		sSize.width -= insets.left + insets.right;
		sSize.height -= insets.top + insets.bottom;

		final Dimension wSize = window.getSize();
		// fit on window
		if (wSize.height > sSize.height)
		{
			wSize.height = sSize.height;
		}
		if (wSize.width > sSize.width)
		{
			wSize.width = sSize.width;
		}
		window.setSize(wSize);
		// Center
		int x = (sSize.width - wSize.width) / 2;
		int y = (sSize.height - wSize.height) / 2;
		if (position == SwingConstants.CENTER)
		{
			
		}
		else if (position == SwingConstants.NORTH_WEST)
		{
			x = 0;
			y = 0;
		}
		else if (position == SwingConstants.NORTH)
		{
			y = 0;
		}
		else if (position == SwingConstants.NORTH_EAST)
		{
			x = sSize.width - wSize.width;
			y = 0;
		}
		else if (position == SwingConstants.WEST)
		{
			x = 0;
		}
		else if (position == SwingConstants.EAST)
		{
			x = sSize.width - wSize.width;
		}
		else if (position == SwingConstants.SOUTH)
		{
			y = sSize.height - wSize.height;
		}
		else if (position == SwingConstants.SOUTH_WEST)
		{
			x = 0;
			y = sSize.height - wSize.height;
		}
		else if (position == SwingConstants.SOUTH_EAST)
		{
			x = sSize.width - wSize.width;
			y = sSize.height - wSize.height;
		}
		//
		window.setLocation(x + insets.left, y + insets.top);
	}	// positionScreen

	public static void setMaximumSizeAsScreenSize(final Window window)
	{
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// take into account task bar and other adornments
		final GraphicsConfiguration config = window.getGraphicsConfiguration();
		final Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(config);
		final int width = screenSize.width - screenInsets.left - screenInsets.right;
		final int height = screenSize.height - screenInsets.top - screenInsets.bottom;

		window.setMaximumSize(new Dimension(width, height));
	}

	/**
	 * Position in center of the parent window. (pack, set location and set visibility)
	 *
	 * @param parent Parent Window or null. In case it's null the window will be centered on current screen.
	 * @param window Window to position
	 */
	public static void showCenterWindow(final Window parent, final Window window)
	{
		positionCenterWindow(parent, window);
		showWindow(window);
	}   // showCenterWindow

	/**
	 * Position in center of the parent window
	 *
	 * @param parent Parent Window
	 * @param window Window to position
	 */
	public static void positionCenterWindow(final Window parent, final Window window)
	{
		if (parent == null)
		{
			positionCenterScreen(window);
			return;
		}
		
		// guard against
		// java.lang.NullPointerException: Cannot invoke "sun.awt.X11.XBaseWindow.isVisible()" because "parent" is null
		// see https://stackoverflow.com/q/75209745/1012103
		try 
		{
			window.pack();
		} 
		catch (final Exception e)
		{
			positionCenterScreen(window);
			return;
		}
		
		//
		final Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
		// take into account task bar and other adornments
		final GraphicsConfiguration config = window.getGraphicsConfiguration();
		final Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(config);
		sSize.width -= insets.left + insets.right;
		sSize.height -= insets.top + insets.bottom;

		final Dimension wSize = window.getSize();
		// fit on window
		if (wSize.height > sSize.height)
		{
			wSize.height = sSize.height;
		}
		if (wSize.width > sSize.width)
		{
			wSize.width = sSize.width;
		}
		window.setSize(wSize);
		// center in parent
		final Rectangle pBounds = parent.getBounds();
		// Parent is in upper left corner
		if (pBounds.x == pBounds.y && pBounds.x == 0)
		{
			positionCenterScreen(window);
			return;
		}
		// Find middle
		int x = pBounds.x + (pBounds.width - wSize.width) / 2;
		if (x < 0)
		{
			x = 0;
		}
		int y = pBounds.y + (pBounds.height - wSize.height) / 2;
		if (y < 0)
		{
			y = 0;
		}

		// Is it on Screen?
		if (x + wSize.width > sSize.width)
		{
			x = sSize.width - wSize.width;
		}
		if (y + wSize.height > sSize.height)
		{
			y = sSize.height - wSize.height;
		}
		//
		// System.out.println("Position: x=" + x + " y=" + y + " w=" + wSize.getWidth() + " h=" + wSize.getHeight()
		// + " - Parent loc x=" + pLoc.x + " y=" + y + " w=" + pSize.getWidth() + " h=" + pSize.getHeight());
		window.setLocation(x + insets.left, y + insets.top);
	}	// positionCenterScreen

	/*************************************************************************
	 * Get Button
	 *
	 * @param iconName
	 * @return button
	 */
	public static CButton getButton(final String iconName)
	{
		final CButton button = new CButton(Images.getImageIcon2(iconName + "16"));
		button.setMargin(new Insets(0, 0, 0, 0));
		button.setToolTipText(Services.get(IMsgBL.class).getMsg(Env.getCtx(), iconName));
		button.setDefaultCapable(false);
		return button;
	}	// getButton

	/**
	 * Create Menu Title (translate it and set Mnemonics). Based on MS notation of &Help => H is Mnemonics
	 *
	 * @param AD_Message message
	 * @return JMenu
	 */
	public static JMenu getMenu(final String AD_Message)
	{
		final JMenu menu = new JMenu();
		String text = Services.get(IMsgBL.class).getMsg(Env.getCtx(), AD_Message);
		final int pos = text.indexOf('&');
		if (pos != -1 && text.length() > pos)   	// We have a nemonic
		{
			final char ch = text.toUpperCase().charAt(pos + 1);
			if (ch != ' ')
			{
				text = text.substring(0, pos) + text.substring(pos + 1);
				menu.setMnemonic(ch);
			}
		}
		menu.setText(text);
		return menu;
	}	// getMenu

	/**
	 * Create Menu Item.
	 *
	 * @param actionName action command
	 * @param defaultIconName optional icon name (without size, without file extension), to be used when no icon was found for <code>actionName</code>
	 * @param ks optional key stroke
	 * @param menu optional menu to add menu item to
	 * @param al action listener to register
	 * @return menu item
	 */
	public static JMenuItem addMenuItem(final String actionName, String defaultIconName, final KeyStroke ks, final JMenu menu, final ActionListener al)
	{
		// Get the icon
		ImageIcon icon = Images.getImageIcon2(actionName + "16");
		// Fallback to default icon name
		if (icon == null && !Check.isEmpty(defaultIconName, true))
		{
			icon = Images.getImageIcon2(defaultIconName + "16");
		}

		final String text = Services.get(IMsgBL.class).getMsg(Env.getCtx(), actionName);

		final CMenuItem mi = new CMenuItem(text, icon);
		mi.setActionCommand(actionName);
		if (ks != null)
		{
			mi.setAccelerator(ks);
		}
		if (menu != null)
		{
			menu.add(mi);
		}
		if (al != null)
		{
			mi.addActionListener(al);
		}
		return mi;
	}   // addMenuItem

	/**
	 * Perform action command for common menu items. Created in AMenu.createMenu(), APanel.createMenu(), FormFrame.createMenu()
	 *
	 * @param actionCommand known action command
	 * @param WindowNo window no
	 * @param c Container parent
	 * @return true if actionCommand was found and performed
	 */
	public static boolean actionPerformed(final String actionCommand, final int WindowNo, final Container c)
	{
		// File Menu ------------------------
		if (actionCommand.equals("Exit"))
		{
			if (ADialog.ask(WindowNo, c, "ExitApplication?"))
			{
				final AMenu aMenu = (AMenu)Env.getWindow(Env.WINDOW_MAIN);
				aMenu.dispose();
			}
		}

		// Go Menu ------------------------
		else if (actionCommand.equals("WorkFlow"))
		{
			startWorkflowProcess(0, 0);
		}
		else if (actionCommand.equals("Home"))
		{
			showWindow(Env.getWindow(Env.WINDOW_MAIN));
		}

		// Tools Menu ------------------------
		else if (actionCommand.equals("Calculator"))
		{
			final Calculator calc = new org.compiere.grid.ed.Calculator(SwingUtils.getFrame(c));
			calc.setDisposeOnEqual(false);
			showCenterScreen(calc);
		}
		else if (actionCommand.equals("Calendar"))
		{
			Calendar.builder().buildAndGet();
		}
		else if (actionCommand.equals("Editor"))
		{
			showCenterScreen(new org.compiere.grid.ed.Editor(SwingUtils.getFrame(c)));
		}
		else if (actionCommand.equals("Preference"))
		{
			final IUserRolePermissions role = Env.getUserRolePermissions();
			if (role.isShowPreference())
			{
				showCenterScreen(new Preference(SwingUtils.getFrame(c), WindowNo));
			}
		}

		// Help Menu ------------------------
		else if (actionCommand.equals("Online"))
		{
			OnlineHelp.openInDefaultBrowser();
		}
		else if (actionCommand.equals("About"))
		{
			showCenterScreen(new AboutBox(SwingUtils.getFrame(c)));
		}
		else
		{
			return false;
		}
		//
		return true;
	}   // actionPerformed

	/**
	 * Set Text and Mnemonic for Button. Create Mnemonics of text containing '&'. Based on MS notation of &Help => H is Mnemonics
	 *
	 * @param b The button
	 * @param text The text with optional Mnemonics
	 */
	public static void setTextMnemonic(final JButton b, final String text)
	{
		if (text == null || b == null)
		{
			return;
		}
		final int pos = text.indexOf('&');
		if (pos != -1)   					// We have a nemonic
		{
			final char ch = text.charAt(pos + 1);
			b.setMnemonic(ch);
			b.setText(text.substring(0, pos) + text.substring(pos + 1));
		}
		b.setText(text);
	}   // setTextMnemonic

	/**
	 * Get Mnemonic character from text.
	 *
	 * @param text text with '&'
	 * @return Mnemonic or 0
	 */
	public static char getMnemonic(final String text)
	{
		final int pos = text.indexOf('&');
		if (pos != -1)
		{
			return text.charAt(pos + 1);
		}
		return 0;
	}   // getMnemonic

	public static void zoom(final int AD_Table_ID, final int Record_ID)
	{
		if (AD_Table_ID <= 0)
		{
			return;
		}

		final String tableName = Services.get(IADTableDAO.class).retrieveTableName(AD_Table_ID);
		zoom(RecordWindowFinder.newInstance(tableName, Record_ID));
	}

	private static void zoom(final RecordWindowFinder recordWindowFinder)
	{
		final AdWindowId windowIdToUse = recordWindowFinder.findAdWindowId().orElse(null);
		if (windowIdToUse == null)
		{
			log.warn("No AD_Window_ID found to zoom for {}", recordWindowFinder);
			return;
		}

		final MQuery query = recordWindowFinder.createZoomQuery();

		// task #797 Make sure the window is displayed by the AWT event dispatching thread. The current thread might not be able to do it right.
		SwingUtilities.invokeLater(() -> {
			final AWindow frame = new AWindow();
			if (!frame.initWindow(windowIdToUse, query))
			{
				return;
			}
			addToWindowManager(frame);
			showCenterScreenOrMaximized(frame);
		});
	}

	/**
	 * Zoom
	 *
	 * @param query query
	 */
	public static void zoom(final MQuery query)
	{
		if (query == null || query.getTableName() == null || query.getTableName().length() == 0)
		{
			return;
		}

		zoom(RecordWindowFinder.newInstance(query));
	}

	/**
	 * Zoom into a given window based on a query
	 *
	 * @param query
	 * @param adWindowId
	 */
	public static void zoom(final MQuery query, final int adWindowId)
	{
		if (query == null || query.getTableName() == null || query.getTableName().length() == 0)
		{
			return;
		}

		if (adWindowId <= 0)
		{
			log.warn("No AD_Window_ID found for ID {}", adWindowId);
			return;
		}

		zoom(RecordWindowFinder.newInstance(query, AdWindowId.ofRepoIdOrNull(adWindowId)));
	}

	/**
	 * Track open frame in window manager
	 *
	 * @param frame
	 */
	public static void addToWindowManager(final Window frame)
	{
		final AMenu mainWindow = AEnv.getAMenu();
		if (mainWindow != null)
		{
			mainWindow.getWindowManager().add(frame);
		}
	}

	/**
	 *
	 * @param adWindowId
	 * @return the found window or <code>null</code>
	 */
	// task 05796
	public static AWindow findInWindowManager(final AdWindowId adWindowId)
	{
		final AMenu mainWindow = AEnv.getAMenu();
		if (mainWindow != null)
		{
			return mainWindow.getWindowManager().find(adWindowId);
		}
		return null;
	}

	/**
	 * FR [ 1966328 ] get AMenu
	 */
	public static AMenu getAMenu()
	{
		final JFrame top = Env.getWindow(Env.WINDOW_MAIN);
		if (top instanceof AMenu)
		{
			return (AMenu)top;
		}
		return null;
	}

	/**
	 * Exit System
	 *
	 * @param status System exit status (usually 0 for no error)
	 */
	public static void exit(final int status)
	{
		Env.exitEnv(status);
	}	// exit

	/**
	 * Is Workflow Process view enabled.
	 *
	 * @return true if enabled
	 */
	public static boolean isWorkflowProcess()
	{
		if (s_workflow == null)
		{
			s_workflow = Boolean.FALSE;
			int AD_Table_ID = 645;	// AD_WF_Process
			if (Env.getUserRolePermissions().isTableAccess(AD_Table_ID, Access.READ))
			{
				s_workflow = Boolean.TRUE;
			}
			else
			{
				AD_Table_ID = 644;	// AD_WF_Activity
				if (Env.getUserRolePermissions().isTableAccess(AD_Table_ID, Access.READ))
				{
					s_workflow = Boolean.TRUE;
				}
			}
			// Get Window
			if (s_workflow.booleanValue())
			{
				s_workflow_Window_ID = AdWindowId.ofRepoIdOrNull(DB.getSQLValue(
						ITrx.TRXNAME_None,
						"SELECT AD_Window_ID FROM AD_Table WHERE AD_Table_ID=?", AD_Table_ID));
				if (s_workflow_Window_ID == null)
				{
					s_workflow_Window_ID = AdWindowId.ofRepoId(297);	// fallback HARDCODED
				}
			}
		}
		return s_workflow.booleanValue();
	}	// isWorkflowProcess

	/**
	 * Start Workflow Process Window
	 *
	 * @param AD_Table_ID optional table
	 * @param Record_ID optional record
	 */
	public static void startWorkflowProcess(final int AD_Table_ID, final int Record_ID)
	{
		if (s_workflow_Window_ID == null)
		{
			return;
		}
		//
		MQuery query = null;
		if (AD_Table_ID != 0 && Record_ID != 0)
		{
			query = new MQuery("AD_WF_Process");
			query.addRestriction("AD_Table_ID", Operator.EQUAL, AD_Table_ID);
			query.addRestriction("Record_ID", Operator.EQUAL, Record_ID);
		}
		//
		AWindow frame = new AWindow();
		if (!frame.initWindow(s_workflow_Window_ID, query))
		{
			return;
		}
		addToWindowManager(frame);
		showCenterScreenOrMaximized(frame);
		frame = null;
	}	// startWorkflowProcess

	/*************************************************************************/

	/** Workflow Menu */
	private static Boolean s_workflow = null;
	/** Workflow Menu */
	private static AdWindowId s_workflow_Window_ID = null;

	/** Server Re-tries */
	private static int s_serverTries = 0;

	/** Logger */
	private static final transient Logger log = LogManager.getLogger(AEnv.class);

	/** Window Cache */
	private static final CCache<AdWindowId, GridWindowVO> s_windows = new CCache<>("AD_Window", 10);

	/**
	 * Get Window Model
	 *
	 * @param WindowNo Window No
	 * @param adWindowId window
	 * @param AD_Menu_ID menu
	 * @return Model Window Value Object; never returns <code>null</code>
	 */
	public static GridWindowVO getMWindowVO(final int WindowNo, final AdWindowId adWindowId, final int AD_Menu_ID)
	{
		//
		// Check cache (if any)
		GridWindowVO mWindowVO = null;
		if (adWindowId != null && Ini.isCacheWindow())   	// try cache
		{
			mWindowVO = s_windows.get(adWindowId);
			if (mWindowVO != null)
			{
				mWindowVO = mWindowVO.clone(WindowNo);
			}
		}

		//
		// Create Window Model on Client
		if (mWindowVO == null)
		{
			mWindowVO = GridWindowVO.builder()
					.ctx(Env.getCtx())
					.windowNo(WindowNo)
					.adWindowId(adWindowId)
					.adMenuId(AD_Menu_ID)
					.loadAllLanguages(false)
					.applyRolePermissions(true)
					.build();
			Check.assumeNotNull(mWindowVO, "mWindowVO not null"); // shall never happen because GridWindowVO.create throws exception if no window found
			s_windows.put(adWindowId, mWindowVO);
		}   	// from Client

		// Check (remote) context
		if (!mWindowVO.getCtx().equals(Env.getCtx()))
		{
			// Remote Context is called by value, not reference
			// Add Window properties to context
			final Enumeration<?> keyEnum = mWindowVO.getCtx().keys();
			while (keyEnum.hasMoreElements())
			{
				final String key = (String)keyEnum.nextElement();
				if (key.startsWith(WindowNo + "|"))
				{
					final String value = mWindowVO.getCtx().getProperty(key);
					Env.setContext(Env.getCtx(), key, value);
				}
			}
			// Sync Context
			mWindowVO.setCtx(Env.getCtx());
		}
		return mWindowVO;
	}   // getWindow

	/**
	 * Post Immediate. This method is usually triggered from the UI.
	 *
	 * If there is any error, an error dialog will be displayed to user.
	 *
	 * Always use this method when you want to post a document from UI and you want to give instant feedback to user.
	 *
	 * @param WindowNo window
	 * @param AD_Client_ID Client ID of Document
	 * @param AD_Table_ID Table ID of Document
	 * @param Record_ID Record ID of this document
	 * @param force force posting
	 */
	public static void postImmediate(final int WindowNo, final int AD_Client_ID,
			final int AD_Table_ID, final int Record_ID, final boolean force)
	{
		Services.get(IPostingService.class)
				.newPostingRequest()
				.setClientId(ClientId.ofRepoId(AD_Client_ID))
				.setDocumentRef(TableRecordReference.of(AD_Table_ID, Record_ID))
				.setForce(force)
				.setPostImmediate(PostImmediate.Yes)
				.setFailOnError(true) // yes, because we will display a pop-up to user in this case (see below)
				//
				// Run it on UI
				.postItOnUI()
				.setParentComponentByWindowNo(WindowNo)
				.setOnFail(OnFail.ShowErrorPopup)
				.setLongOperation(true)
				.invoke();
	}   // postImmediate

	/**
	 * Update all windows after look and feel changes.
	 *
	 * @since 2006-11-27
	 */
	public static void updateUI()
	{
		final Set<Window> updated = Env.updateUI();
		final AMenu mainWindow = AEnv.getAMenu();
		if (mainWindow != null)
		{
			final Window[] frames = mainWindow.getWindowManager().getWindows();
			for (final Window f : frames)
			{
				if (updated.contains(f))
				{
					continue;
				}
				SwingUtilities.updateComponentTreeUI(f);
				f.validate();
				final RepaintManager mgr = RepaintManager.currentManager(f);
				final Component childs[] = f.getComponents();
				for (final Component c : childs)
				{
					if (c instanceof JComponent)
					{
						mgr.markCompletelyDirty((JComponent)c);
					}
				}
				f.repaint();
				updated.add(f);
			}
		}
	}

	/**
	 * Helper method which gets the {@link Window} of given {@link Component}
	 *
	 * @param comp
	 * @return {@link Window} or null
	 */
	public static Window getWindow(final Component comp)
	{
		return getParentComponent(comp, Window.class);
	}

	/**
	 * Searches for nearest parent of <code>comp</code> which implements given <code>parentType</code>.
	 *
	 * @param comp component or null
	 * @param parentType
	 * @return parent component which implements given type or <code>null</code>
	 */
	public static <T> T getParentComponent(@Nullable final Component comp, final Class<T> parentType)
	{
		if (comp == null)
		{
			return null;
		}

		Component c = comp;
		while (c != null)
		{
			if (parentType.isAssignableFrom(c.getClass()))
			{
				@SuppressWarnings("unchecked")
				final T retValue = (T)c;
				return retValue;
			}
			c = c.getParent();
		}

		return null;
	}

	/**
	 * Helper method which gets the {@link Dialog} of given {@link Component}
	 *
	 * @param comp
	 * @return {@link Dialog} or null
	 */
	public static Dialog getDialog(final Component comp)
	{
		Component c = comp;
		while (c != null)
		{
			if (c instanceof Dialog)
			{
				return (Dialog)c;
			}
			c = c.getParent();
		}
		return null;
	}

	/**
	 * Make a {@link JFrame} behave like a modal {@link JDialog}.
	 *
	 * @param modalFrame
	 * @param parentFrame
	 * @param onCloseCallback
	 */
	public static void showCenterWindowModal(final JFrame modalFrame, final JFrame parentFrame, final Runnable onCloseCallback)
	{
		Check.assumeNotNull(modalFrame, "modalFrame not null");
		Check.assumeNotNull(parentFrame, "parentFrame not null");

		modalFrame.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowOpened(final WindowEvent e)
			{
				//
				// Disable frame (make new frame behave like a modal frame)
				parentFrame.setFocusable(false);
				parentFrame.setEnabled(false);
			}

			@Override
			public void windowActivated(final WindowEvent e)
			{
				windowGainedFocus(e);
			}

			@Override
			public void windowDeactivated(final WindowEvent e)
			{
				// nothing
			}

			@Override
			public void windowGainedFocus(final WindowEvent e)
			{
				// nothing
			}

			@Override
			public void windowLostFocus(final WindowEvent e)
			{
				// nothing
			}

			@Override
			public void windowClosed(final WindowEvent e)
			{
				//
				// Re-enable frame
				parentFrame.setFocusable(true);
				parentFrame.setEnabled(true);

				modalFrame.removeWindowListener(this);

				if (onCloseCallback != null)
				{
					onCloseCallback.run();
				}

				parentFrame.toFront();
			}
		});

		parentFrame.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowActivated(final WindowEvent e)
			{
				windowGainedFocus(e);
			}

			@Override
			public void windowDeactivated(final WindowEvent e)
			{
				// nothing
			}

			@Override
			public void windowGainedFocus(final WindowEvent e)
			{
				EventQueue.invokeLater(() -> {
					if (modalFrame == null || !modalFrame.isVisible() || !modalFrame.isShowing())
					{
						return;
					}
					modalFrame.toFront();
					modalFrame.repaint();
				});
			}

			@Override
			public void windowLostFocus(final WindowEvent e)
			{
				// nothing
			}
		});

		if (modalFrame instanceof CFrame)
		{
			addToWindowManager(modalFrame);
		}
		showCenterWindow(parentFrame, modalFrame);
	}

	/**
	 * Create a {@link FormFrame} for the given {@link I_AD_Form}.
	 *
	 * @param form
	 * @return formFrame which was created or null
	 */
	public static FormFrame createForm(final I_AD_Form form)
	{
		final FormFrame formFrame = new FormFrame();
		if (formFrame.openForm(form))
		{
			formFrame.pack();
			return formFrame;
		}
		else
		{
			formFrame.dispose();
			return null;
		}
	}

}	// AEnv
