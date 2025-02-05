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

import de.metas.adempiere.form.swing.SwingAskDialogBuilder;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import org.compiere.util.Env;
import org.compiere.util.SwingUtils;
import org.compiere.util.Trace;
import org.slf4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;

/**
 *  Info Dialog Management
 *
 *  @author 	Jorg Janke
 *  @version 	$Id: ADialog.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */

public final class ADialog
{
	/** Show ADialogADialog - if false use JOptionPane  */
	private static final boolean showDialog = true;
	/**	Logger			*/
	private static final transient Logger log = LogManager.getLogger(ADialog.class);
	
	/**
	 *	Show plain message
	 *	@param	WindowNo	Number of Window
	 *  @param  c           Container (owner)
	 *	@param	clearHeading	Translated Title of window
	 *	@param	clearMessage	Translated message
	 *	@param	clearText		Additional message
	 */
	public static void info (final int WindowNo, final Container c, final String clearHeading, final String clearMessage, final String clearText)
	{
		log.info("{}: {} {}", clearHeading, clearMessage, clearText);
		
		String out = clearMessage;
		if(!Check.isEmpty(clearText, true))
		{
			out += "\n" + clearText;
		}
		
		//
		Window parent = SwingUtils.getParentWindow(c);
		if (parent == null)
		{
			parent = Env.getWindow(WindowNo);
		}
		//
		if (showDialog  && parent != null)
		{
			if (parent instanceof JFrame)
			{
				new ADialogDialog ((JFrame)parent,
					clearHeading,
					out,
					JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				new ADialogDialog ((JDialog)parent,
					clearHeading,
					out,
					JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(parent,
				out + "\n",						//	message
				clearHeading,					//	title
				JOptionPane.INFORMATION_MESSAGE);
		}
	}	//	info

	/**
	 *	Show message with info icon
	 *	@param	WindowNo	Number of Window
	 *  @param  c           Container (owner)
	 *	@param	AD_Message	Message to be translated
	 *	@param	msg			Additional message
	 */
	public static void info (final int WindowNo, final Container c, final String AD_Message, final String msg)
	{
		log.info("{} - {}", AD_Message, msg);
		
		final Properties ctx = Env.getCtx();
		final StringBuilder out = new StringBuilder();
		if(!Check.isEmpty(AD_Message, true))
		{
			out.append(Services.get(IMsgBL.class).getMsg(ctx, AD_Message));
		}
		if(!Check.isEmpty(msg, true))
		{
			out.append("\n").append(msg);
		}
		//
		Window parent = SwingUtils.getParentWindow(c);
		if (parent == null)
		{
			parent = Env.getWindow(WindowNo);
		}
		//
		if (showDialog && parent != null)
		{
			if (parent instanceof JFrame)
			{
				new ADialogDialog ((JFrame)parent,
					Env.getHeader(ctx, WindowNo),
					out.toString(),
					JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				new ADialogDialog ((JDialog)parent,
					Env.getHeader(ctx, WindowNo),
					out.toString(),
					JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(parent,
				out.toString() + "\n",			//	message
				Env.getHeader(ctx, WindowNo),	//	title
				JOptionPane.INFORMATION_MESSAGE);
		}
	}	//	info

	/**
	 *	Show message with info icon
	 *	@param	WindowNo	Number of Window
	 *  @param  c           Container (owner)
	 *	@param	AD_Message	Message to be translated
	 */
	public static void info (int WindowNo, Container c, String AD_Message)
	{
		final String msg = null;
		info (WindowNo, c, AD_Message, msg);
	}	//	info


	/**************************************************************************
	 *	Display warning with warning icon
	 *	@param	WindowNo	Number of Window
	 *  @param  c           Container (owner)
	 *	@param	AD_Message	Message to be translated
	 *	@param	msg			Additional message
	 */
	public static void warn (final int WindowNo, final Container c, final String AD_Message, final String msg)
	{
		log.info("{} - {}", AD_Message, msg);
		
		Properties ctx = Env.getCtx();
		StringBuilder out = new StringBuilder();
		if (AD_Message != null && !AD_Message.equals(""))
		{
			out.append(Services.get(IMsgBL.class).getMsg(ctx, AD_Message));
		}
		if (msg != null && msg.length() > 0)
		{
			out.append("\n").append(msg);
		}
		//
		Window parent = SwingUtils.getParentWindow(c);
		if (parent == null)
		{
			parent = Env.getWindow(WindowNo);
		}
		//
		if (showDialog  && parent != null)
		{
			if (parent instanceof JFrame)
			{
				new ADialogDialog ((JFrame)parent,
					Env.getHeader(ctx, WindowNo),
					out.toString(),
					JOptionPane.WARNING_MESSAGE);
			}
			else
			{
				new ADialogDialog ((JDialog)parent,
					Env.getHeader(ctx, WindowNo),
					out.toString(),
					JOptionPane.WARNING_MESSAGE);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(parent,
				out.toString() + "\n",  		//	message
				Env.getHeader(ctx, WindowNo),	//	title
				JOptionPane.WARNING_MESSAGE);
		}
	}	//	warn (int, String)

	/**
	 *	Display warning with warning icon
	 *	@param	WindowNo	Number of Window
	 *  @param  c           Container (owner)
	 *	@param	AD_Message	Message to be translated
	 */
	public static void warn (final int WindowNo, final Container c, final String AD_Message)
	{
		final String msg = null;
		warn (WindowNo, c, AD_Message, msg);
	}

	
	/**************************************************************************
	 *	Display error with error icon
	 *	@param	WindowNo	Number of Window
	 *  @param  c           Container (owner)
	 *	@param	AD_Message	Message to be translated
	 *	@param	msg			Additional message
	 */
	public static void error (final int WindowNo, final Container c, final String AD_Message, final String msg)
	{
		log.info("{} - {}", AD_Message, msg);
		if (LogManager.isLevelFinest())
		{
			Trace.printStack();
		}
		
		final Properties ctx = Env.getCtx();
		final StringBuilder out = new StringBuilder();
		if(!Check.isEmpty(AD_Message, true))
		{
			out.append(Services.get(IMsgBL.class).getMsg(ctx, AD_Message));
		}
		if(!Check.isEmpty(msg, true))
		{
			out.append("\n").append(msg);
		}
		//
		Window parent = SwingUtils.getParentWindow(c);
		if (parent == null)
		{
			parent = Env.getWindow(WindowNo);
		}
		//
		if (showDialog && parent != null)
		{
			if (parent instanceof JFrame)
			{
				new ADialogDialog ((JFrame)parent,
					Env.getHeader(ctx, WindowNo),
					out.toString(),
					JOptionPane.ERROR_MESSAGE);
			}
			else if (parent instanceof JDialog)
			{
				new ADialogDialog ((JDialog)parent,
					Env.getHeader(ctx, WindowNo),
					out.toString(),
					JOptionPane.ERROR_MESSAGE);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(Env.getWindow(WindowNo),
				out.toString() + "\n",			//	message
				Env.getHeader(ctx, WindowNo),	//	title
				JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 *	Display error with error icon
	 *	@param	WindowNo	Number of Window
	 *  @param  c           Container (owner)
	 *	@param	AD_Message	Message to be translated
	 */
	public static void error (final int WindowNo, final Container c, final String AD_Message)
	{
		final String msg = null;
		error(WindowNo, c, AD_Message, msg);
	}

	
	/**************************************************************************
	 *	Ask Question with question icon and (OK) (Cancel) buttons
	 *	@param	WindowNo	Number of Window
	 *  @param  c           Container (owner)
	 *	@param	AD_Message	Message to be translated
	 *	@param	msg			Additional clear text message
	 *	@return true, if OK
	 */
	public static boolean ask (final int WindowNo, final Container c, final String AD_Message, final String msg)
	{
		return new SwingAskDialogBuilder()
				.setParentWindowNo(WindowNo)
				.setParentComponent(c)
				.setAD_Message(AD_Message)
				.setAdditionalMessage(msg)
				.getAnswer();
	}	//	ask

	/**
	 *	Ask Question with question icon and (OK) (Cancel) buttons
	 *	@param	WindowNo	Number of Window
	 *  @param  c           Container (owner)
	 *	@param	AD_Message	Message to be translated
	 *	@return true, if OK
	 */
	public static boolean ask (int WindowNo, Container c, String AD_Message)
	{
		return ask (WindowNo, c, AD_Message, null);
	}	//	ask

	/**************************************************************************
	 *	Beep
	 */
	public static void beep()
	{
		Toolkit.getDefaultToolkit().beep();
	}	//	beep

// metas: begin
	public static void error (int WindowNo, Container c, Throwable ex)
	{
		String adMessage = "Error";
		String msg = ex.getLocalizedMessage();
		log.warn(msg, ex);
		error(WindowNo, c, adMessage, msg);
	}
// metas: end
}	//	Dialog
