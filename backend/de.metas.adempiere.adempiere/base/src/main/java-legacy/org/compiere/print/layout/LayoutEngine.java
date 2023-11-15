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
package org.compiere.print.layout;

import de.metas.i18n.Language;
import de.metas.i18n.Msg;
import de.metas.logging.LogManager;
import org.adempiere.archive.api.ArchiveInfo;
import org.compiere.Adempiere;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.compiere.model.MTable;
import org.compiere.print.ArchiveEngine;
import org.compiere.print.CPaper;
import org.compiere.print.DataEngine;
import org.compiere.print.MPrintColor;
import org.compiere.print.MPrintFont;
import org.compiere.print.MPrintFormat;
import org.compiere.print.MPrintFormatItem;
import org.compiere.print.MPrintPaper;
import org.compiere.print.MPrintTableFormat;
import org.compiere.print.PrintData;
import org.compiere.print.PrintDataElement;
import org.compiere.util.AmtInWords;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;
import org.compiere.util.ValueNamePair;
import org.slf4j.Logger;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.attribute.DocAttributeSet;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

/**
 * Adempiere Print Engine.
 * All coordinates are relative to the Page.
 * The Language setting is maintained in the format
 *
 * @author Jorg Janke
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL <li>BF [ 1673505 ] BarCode/Image problem when print format is not form <li>BF [ 1673542 ] Can't add static image in report table cell <li>BF [ 1673548 ]
 * Image is not scaled in a report table cell <li>BF [ 1807917 ] Layout positioning issue with m_maxHeightSinceNewLine <li>BF [ 1825876 ] Layout boxes with auto width not working <li>FR [
 * 1966406 ] Report Engine: AD_PInstance_Logs should be displayed <li>BF [ 2487307 ] LayoutEngine: NPE when Barcode field is null <li>BF [ 2828893 ] Problem with NextPage in Print Format
 * https://sourceforge.net/tracker/?func=detail&atid=879332&aid=2828893&group_id=176962
 * @author victor.perez@e-evolution.com, e-Evolution <li>BF [ 2011567 ] Implement Background Image for Document printed <li>
 * http://sourceforge.net/tracker/index.php?func=detail&aid=2011567&group_id=176962&atid=879335
 * @author Michael Judd (Akuna Ltd) <li>BF [ 2695078 ] Country is not translated on invoice
 * @version $Id: LayoutEngine.java,v 1.3 2006/07/30 00:53:02 jjanke Exp $
 */
@Deprecated  // TODO delete me
public class LayoutEngine implements Pageable, Printable, Doc
{
	/**
	 * Detail Constructor
	 *
	 * @param format Print Format
	 * @param data   Print Data
	 * @param query  query for parameter info
	 */
	public LayoutEngine(final MPrintFormat format, final PrintData data, final MQuery query, final ArchiveInfo info)
	{
		this(format, data, query, info, null);
	}    // LayoutEngine

	/**
	 * Detail Constructor
	 *
	 * @param format  Print Format
	 * @param data    Print Data
	 * @param query   query for parameter info
	 * @param trxName
	 */
	public LayoutEngine(final MPrintFormat format, final PrintData data, final MQuery query, final ArchiveInfo info, final String trxName)
	{
		m_TrxName = trxName;
		log.info(format + " - " + data + " - " + query);
		// s_FASTDRAW = MClient.get(format.getCtx()).isUseBetaFunctions();
		//
		setPrintFormat(format, false);
		setPrintData(data, query, false);
		setPrintInfo(info);
		layout();
	}    // LayoutEngine

	/*************************************************************************/

	/**
	 * Logger
	 */
	private static final Logger log = LogManager.getLogger(LayoutEngine.class);
	/**
	 * Existing Layout
	 */
	private boolean m_hasLayout = false;
	/**
	 * The Format
	 */
	private MPrintFormat m_format;
	/**
	 * Print Context
	 */
	private Properties m_printCtx;
	/**
	 * The Data
	 */
	private PrintData m_data;
	/**
	 * The Query (parameter
	 */
	private MQuery m_query;
	/**
	 * Default Color
	 */
	private MPrintColor m_printColor;
	/**
	 * Default Font
	 */
	private MPrintFont m_printFont;
	/**
	 * Printed Column Count
	 */
	private int m_columnCount = -1;
	/**
	 * Transaction name
	 */
	private String m_TrxName = null;
	/**
	 * PrintInfo
	 **/
	private ArchiveInfo m_PrintInfo = null;

	/**
	 * Paper - default: standard portrait
	 */
	private CPaper m_paper;
	private static final int HEADERHEIGHT_Default = 20;
	// task 09359: get rid of the logo
	// private static final int LOGO_WIDTH_MAX = 100; // logo's max width to be displayed in standard header/footer
	/**
	 * Header Area Height (1/4")
	 */
	private int m_headerHeight = HEADERHEIGHT_Default;
	/**
	 * Footer Area Height (1/4")
	 */
	private int m_footerHeight = 20;

	/**
	 * Current Page Number
	 */
	private int m_pageNo = 0;
	/**
	 * Current Page
	 */
	private Page m_currPage;
	/**
	 * Pages
	 */
	private ArrayList<Page> m_pages = new ArrayList<>();
	/**
	 * Header&Footer for all pages
	 */
	private HeaderFooter m_headerFooter;

	/**
	 * Header Coordinates
	 */
	private Rectangle m_header = new Rectangle();
	/**
	 * Content Coordinates
	 */
	private Rectangle m_content = new Rectangle();
	/**
	 * Footer Coordinates
	 */
	private Rectangle m_footer = new Rectangle();
	/**
	 * Temporary NL Position
	 */
	private int m_tempNLPositon = 0;

	/**
	 * Header Area
	 */
	public static final int AREA_HEADER = 0;
	/**
	 * Content Area
	 */
	public static final int AREA_CONTENT = 1;
	/**
	 * Footer Area
	 */
	public static final int AREA_FOOTER = 2;
	/**
	 * Area Pointer
	 */
	private int m_area = AREA_CONTENT;

	/**
	 * Current Position in 1/72 inch
	 */
	private Point2D.Double[] m_position = new Point2D.Double[]
			{ new Point2D.Double(0, 0), new Point2D.Double(0, 0), new Point2D.Double(0, 0) };
	/**
	 * Max Height Since New Line
	 */
	private float m_maxHeightSinceNewLine[] = new float[] { 0f, 0f, 0f };

	/**
	 * Primary Table Element for Page XY Info
	 */
	private TableElement m_tableElement = null;

	/**
	 * Last Height by area
	 */
	private float m_lastHeight[] = new float[] { 0f, 0f, 0f };
	/**
	 * Last Width by area
	 */
	private float m_lastWidth[] = new float[] { 0f, 0f, 0f };

	/**
	 * Draw using attributed String vs. Text Layout where possible
	 */
	// hengsin: [ 1564523 ] Max width of print element not always respected
	// tspc: [ 2084725 ] setting s_FASTDRAW to false is causing exported pdf to be generated as image
	public static boolean s_FASTDRAW = true;
	/**
	 * Print Copy (print interface)
	 */
	private boolean m_isCopy = false;

	/*************************************************************************/

	/**
	 * True Image
	 */
	public static Image IMAGE_TRUE = null;
	/**
	 * False Image
	 */
	public static Image IMAGE_FALSE = null;
	/**
	 * Image Size
	 */
	public static Dimension IMAGE_SIZE = new Dimension(10, 10);

	static
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
		URL url = LayoutEngine.class.getResource("true10.gif");
		if (url != null)
			IMAGE_TRUE = tk.getImage(url);
		url = LayoutEngine.class.getResource("false10.gif");
		/** @todo load images via medialoader */
		if (url != null)
			IMAGE_FALSE = tk.getImage(url);
	}    // static init

	/**************************************************************************
	 * Set Print Format
	 * Optionally re-calculate layout
	 *
	 * @param doLayout if layout exists, redo it
	 * @param format print Format
	 */
	public void setPrintFormat(final MPrintFormat format, final boolean doLayout)
	{
		m_format = format;
		// Initial & Default Settings
		m_printCtx = Env.deriveCtx(format.getCtx());

		// Set Paper
		boolean tempHasLayout = m_hasLayout;
		m_hasLayout = false;    // do not start re-calculation
		MPrintPaper mPaper = MPrintPaper.get(format.getAD_PrintPaper_ID());
		if (m_format.isStandardHeaderFooter())
			setPaper(mPaper.getCPaper());
		else
			setPaper(mPaper.getCPaper(),
					m_format.getHeaderMargin(), m_format.getFooterMargin());
		m_hasLayout = tempHasLayout;
		//
		m_printColor = MPrintColor.get(getCtx(), format.getAD_PrintColor_ID());
		m_printFont = MPrintFont.get(format.getAD_PrintFont_ID());

		// Print Context
		Env.setContext(m_printCtx, Page.CONTEXT_REPORTNAME, m_format.getName());
		Env.setContext(m_printCtx, Page.CONTEXT_HEADER, Env.getHeader(m_printCtx, 0));
		Env.setContext(m_printCtx, Env.CTXNAME_AD_Language, m_format.getLanguage().getAD_Language());

		if (m_hasLayout && doLayout)
			layout();            // re-calculate
	}    // setPrintFormat

	/**
	 * Set PrintData.
	 * Optionally re-calculate layout
	 *
	 * @param data     data
	 * @param doLayout if layout exists, redo it
	 * @param query    query for parameter
	 */
	public void setPrintData(final PrintData data, final MQuery query, final boolean doLayout)
	{
		m_data = data;
		m_query = query;
		if (m_hasLayout && doLayout)
			layout();            // re-calculate
	}    // setPrintData

	public void setPrintData(final PrintData data, final MQuery query, final boolean doLayout, final String trxName)
	{
		m_data = data;
		m_query = query;
		m_TrxName = trxName;
		if (m_hasLayout && doLayout)
			layout();            // re-calculate
	}    // setPrintData

	/**************************************************************************
	 * Set Paper
	 *
	 * @param paper Paper
	 */
	public void setPaper(final CPaper paper)
	{
		setPaper(paper, m_headerHeight, m_footerHeight);
	}    // setPaper

	/**
	 * Set Paper
	 * Optionally re-calculate layout
	 *
	 * @param paper        Paper
	 * @param headerHeight header height
	 * @param footerHeight footer height
	 */
	public void setPaper(final CPaper paper, final int headerHeight, final int footerHeight)
	{
		if (paper == null)
			return;
		//
		boolean paperChange = headerHeight != m_headerHeight || footerHeight != m_footerHeight;
		if (!paperChange)
			paperChange = !paper.equals(m_paper);
		//
		log.debug(paper + " - Header=" + headerHeight + ", Footer=" + footerHeight);
		m_paper = paper;
		m_headerHeight = headerHeight;
		m_footerHeight = footerHeight;
		calculatePageSize();
		//
		if (m_hasLayout && paperChange)
			layout();            // re-calculate
	}    // setPaper

	/**
	 * Show Dialog and Set Paper
	 * Optionally re-calculate layout
	 *
	 * @param job printer job
	 */
	public void pageSetupDialog(final PrinterJob job)
	{
		log.info("");
		if (m_paper.pageSetupDialog(job))
		{
			setPaper(m_paper);
			layout();
		}
	}    // pageSetupDialog

	/**
	 * Set Paper from Page Format.
	 * PageFormat is derived from CPaper
	 *
	 * @param pf Optional PageFormat - if null standard paper Portrait
	 */
	protected void setPageFormat(final PageFormat pf)
	{
		if (pf != null)
			setPaper(new CPaper(pf));
		else
			setPaper(null);
	}    // setPageFormat

	/**
	 * Get Page Format
	 *
	 * @return page format
	 */
	public PageFormat getPageFormat()
	{
		return m_paper.getPageFormat();
	}    // getPageFormat

	/**
	 * Calculate Page size based on Paper and header/footerHeight.
	 *
	 * <pre>
	 *  Paper: 8.5x11.0" Portrait x=32.0,y=32.0 w=548.0,h=728.0
	 *  +------------------------ Paper   612x792
	 *  |    non-imageable space          32x32
	 *  |  +--------------------- Header = printable area start
	 *  |  | headerHeight=32      =>  [x=32,y=32,width=548,height=32]
	 *  |  +--------------------- Content
	 *  |  |                      =>  [x=32,y=64,width=548,height=664]
	 *  |  |
	 *  |  |
	 *  |  |
	 *  |  +--------------------- Footer
	 *  |  | footerHeight=32      =>  [x=32,y=728,width=548,height=32]
	 *  |  +--------------------- Footer end = printable area end
	 *  |   non-imageable space
	 *  +------------------------
	 * </pre>
	 */
	private void calculatePageSize()
	{
		int x = (int)m_paper.getImageableX(true);
		int w = (int)m_paper.getImageableWidth(true);
		//
		int y = (int)m_paper.getImageableY(true);
		int h = (int)m_paper.getImageableHeight(true);

		int height = m_headerHeight;
		m_header.setBounds(x, y, w, height);
		//
		y += height;
		height = h - m_headerHeight - m_footerHeight;
		m_content.setBounds(x, y, w, height);
		//
		y += height;
		height = m_footerHeight;
		m_footer.setBounds(x, y, w, height);

		log.debug("Paper=" + m_paper + ",HeaderHeight=" + m_headerHeight + ",FooterHeight=" + m_footerHeight
				+ " => Header=" + m_header + ",Contents=" + m_content + ",Footer=" + m_footer);
	}    // calculatePageSize

	/**
	 * Set Paper
	 *
	 * @return Paper
	 */
	public CPaper getPaper()
	{
		return m_paper;
	}    // getPaper

	/**************************************************************************
	 * Create Layout
	 */
	private void layout()
	{
		// Header/Footer
		m_headerFooter = new HeaderFooter(m_printCtx);
		if (!m_format.isForm() && m_format.isStandardHeaderFooter())
			createStandardHeaderFooter();
		//
		m_pageNo = 0;
		m_pages.clear();
		m_tableElement = null;
		newPage(true, false);    // initialize
		//
		if (m_format.isForm())
			layoutForm();
		else
		{
			// Parameter
			PrintElement element = layoutParameter();
			if (element != null)
			{
				m_currPage.addElement(element);
				element.setLocation(m_position[AREA_CONTENT]);
				m_position[AREA_CONTENT].y += element.getHeight() + 5;    // GAP
			}
			// Process Instance Log (if any):
			element = layoutPInstanceLogs();
			if (element != null)
			{
				m_currPage.addElement(element);
				element.setLocation(m_position[AREA_CONTENT]);
				m_position[AREA_CONTENT].y += element.getHeight() + 5;    // GAP
			}
			// Table
			if (m_data != null)
			{
				element = layoutTable(m_format, m_data, 0);
				element.setLocation(m_content.getLocation());
				for (int p = 1; p <= element.getPageCount(); p++)
				{
					if (p != 1)
						newPage(true, false);
					m_currPage.addElement(element);
				}
			}
		}

		// task 09359
		// Total number of pages, Y dimension (the number of pages reports would fit in if they all had 1 page horizontally)
		if (m_tableElement != null)
		{
			final String pageInfo = String.valueOf(m_tableElement.getPageYCount());
			Env.setContext(m_printCtx, Page.CONTEXT_PAGECOUNT, pageInfo);
		}
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Env.setContext(m_printCtx, Page.CONTEXT_DATE,
				DisplayType.getDateFormat(DisplayType.Date, m_format.getLanguage()).format(now));
		Env.setContext(m_printCtx, Page.CONTEXT_TIME,
				DisplayType.getDateFormat(DisplayType.DateTime, m_format.getLanguage()).format(now));

		//
		// Page Background Image
		Image image = null;
		MPrintTableFormat tf = m_format.getTableFormat();
		MTable table = MTable.get(getCtx(), getPrintInfo().getRecordRef().getAD_Table_ID());
		if (table.getColumn("IsPrinted") != null && !table.isView())
		{
			String tableName = table.getTableName();
			final String sql = "SELECT IsPrinted FROM " + tableName + " WHERE " + tableName + "_ID=?";
			boolean isPrinted = "Y".equals(DB.getSQLValueStringEx(m_TrxName, sql, getPrintInfo().getRecordRef().getRecord_ID()));
			if (isPrinted)
			{
				image = tf.getImageWaterMark();
			}
		}
		else
		{
			image = tf.getImage();
		}

		// Update Page Info
		int pages = m_pages.size();
		for (int i = 0; i < pages; i++)
		{
			Page page = m_pages.get(i);

			final String pageInfo = updatePageInfo(page.getPageNo());

			page.setPageInfo(pageInfo);
			page.setPageCount(pages);
			page.setBackgroundImage(image);
		}

		m_hasLayout = true;
	}    // layout

	/**
	 * Build the string that tells that the current page is the x'th page of the set of n pages
	 *
	 * @param pageNo
	 * @return
	 */
	public String updatePageInfo(final int pageNo)
	{
		// form
		if (m_tableElement == null)
		{
			return "";
		}
		// table
		else
		{
			final int pi = m_tableElement.getPageIndex(pageNo);
			return String.valueOf(m_tableElement.getPageYIndex(pi) + 1) + getPageInfo(pageNo);
		}
	}

	/***************************************************************************
	 * Get PrintLayout (Report) Context
	 *
	 * @return context
	 */
	public Properties getCtx()
	{
		return m_printCtx;
	}    // getCtx

	/**
	 * Get the number of printed Columns
	 *
	 * @return no of printed columns
	 */
	public int getColumnCount()
	{
		return m_columnCount;
	}    // getColumnCount

	/**
	 * Set the current Print Area
	 *
	 * @param area see HEADER_.. constants
	 */
	protected void setArea(final int area)
	{
		if (m_area == area)
			return;
		if (area < 0 || area > 2)
			throw new ArrayIndexOutOfBoundsException(area);
		m_area = area;
	}    // setArea

	/**
	 * Get the current Print Area
	 *
	 * @return area see HEADER_.. constants
	 */
	public int getArea()
	{
		return m_area;
	}    // getArea

	/**
	 * Return bounds of current Area
	 *
	 * @return rectangle with bounds
	 */
	public Rectangle getAreaBounds()
	{
		Rectangle part = m_content;
		if (m_area == AREA_HEADER)
			part = m_header;
		else if (m_area == AREA_FOOTER)
			part = m_footer;
		//
		return part;
	}    // getAreaBounds

	/**************************************************************************
	 * Create New Page, set position to top content
	 *
	 * @param force if false will check if nothing printed so far
	 * @param preserveXPos preserve X Position of content area
	 * @return new page no
	 */
	protected int newPage(final boolean force, final boolean preserveXPos)
	{
		// We are on a new page
		if (!force
				&& m_position[AREA_CONTENT].getX() == m_content.x
				&& m_position[AREA_CONTENT].getY() == m_content.y)
		{
			log.debug("skipped");
			return m_pageNo;
		}

		m_pageNo++;
		m_currPage = new Page(m_printCtx, m_pageNo);
		m_pages.add(m_currPage);
		//
		m_position[AREA_HEADER].setLocation(m_header.x, m_header.y);
		if (preserveXPos)
			m_position[AREA_CONTENT].setLocation(m_position[AREA_CONTENT].x, m_content.y);
		else
			m_position[AREA_CONTENT].setLocation(m_content.x, m_content.y);
		m_position[AREA_FOOTER].setLocation(m_footer.x, m_footer.y);
		m_maxHeightSinceNewLine = new float[] { 0f, 0f, 0f };
		log.trace("Page=" + m_pageNo);
		return m_pageNo;
	}    // newPage

	/**
	 * Move to New Line (may cause new page)
	 */
	protected void newLine()
	{
		Rectangle part = m_content;
		if (m_area == AREA_HEADER)
			part = m_header;
		else if (m_area == AREA_FOOTER)
			part = m_footer;

		// Temporary NL Position
		int xPos = part.x;
		if (m_tempNLPositon != 0)
			xPos = m_tempNLPositon;

		if (isYspaceFor(m_maxHeightSinceNewLine[m_area]))
		{
			m_position[m_area].setLocation(xPos, m_position[m_area].y + m_maxHeightSinceNewLine[m_area]);
			log.trace("Page=" + m_pageNo + " [" + m_area + "] " + m_position[m_area].x + "/" + m_position[m_area].y);
		}
		else if (m_area == AREA_CONTENT)
		{
			log.trace("Not enough Y space "
					+ m_lastHeight[m_area] + " - remaining " + getYspace() + " - Area=" + m_area);
			newPage(true, false);
			log.trace("Page=" + m_pageNo + " [" + m_area + "] " + m_position[m_area].x + "/" + m_position[m_area].y);
		}
		else
		// footer/header
		{
			m_position[m_area].setLocation(part.x, m_position[m_area].y + m_maxHeightSinceNewLine[m_area]);
			log.error("Outside of Area(" + m_area + "): " + m_position[m_area]);
		}
		m_maxHeightSinceNewLine[m_area] = 0f;
	}    // newLine

	/**
	 * Get current Page Number (not zero based)
	 *
	 * @return Page No
	 */
	public int getPageNo()
	{
		return m_pageNo;
	}    // getPageNo

	/**
	 * Get Page No
	 *
	 * @param pageNo page number (NOT zero based)
	 * @return Page
	 */
	public Page getPage(final int pageNo)
	{
		if (pageNo <= 0 || pageNo > m_pages.size())
		{
			log.error("No page #" + pageNo);
			return null;
		}
		Page retValue = m_pages.get(pageNo - 1);
		return retValue;
	}    // getPage

	/**
	 * Get Pages
	 *
	 * @return Pages in ArrayList
	 */
	public ArrayList<Page> getPages()
	{
		return m_pages;
	}    // getPages

	/**
	 * Get Header & Footer info
	 *
	 * @return Header&Footer
	 */
	public HeaderFooter getHeaderFooter()
	{
		return m_headerFooter;
	}    // getPages

	/**
	 * Set Current page to Page No
	 *
	 * @param pageNo page number (NOT zero based)
	 */
	protected void setPage(final int pageNo)
	{
		if (pageNo <= 0 || pageNo > m_pages.size())
		{
			log.error("No page #" + pageNo);
			return;
		}
		Page retValue = m_pages.get(pageNo - 1);
		m_currPage = retValue;
	}    // setPage

	/**
	 * Get Page Info for Multi-Page tables
	 *
	 * @param pageNo page
	 * @return info e.g. (1,1) if there is more than 1 page vertically. 1 otherwise
	 */
	public String getPageInfo(final int pageNo)
	{
		if (m_tableElement == null || m_tableElement.getPageXCount() == 1)
			return "";
		int pi = m_tableElement.getPageIndex(pageNo);

		StringBuilder sb = new StringBuilder("(")
				.append(m_tableElement.getPageXIndex(pi) + 1)
				.append(")");

		return sb.toString();
	}    // getPageInfo

	/**
	 * Get Max Page Info ( Y dimension)
	 *
	 * @return info e.g. 1 ( if one page), 2 ( if 2pages) etc
	 */
	public String getPageInfoMax()
	{
		// form
		if (m_tableElement == null)
		{
			return "";
		}
		// table
		else
		{
			return String.valueOf(m_tableElement.getPageYCount());
		}
	}    // getPageInfoMax

	/**
	 * Get Format Model
	 *
	 * @return model
	 */
	public MPrintFormat getFormat()
	{
		return m_format;
	}    // getFormat

	/**
	 * Get Print Interface (Pageable, Printable, Doc)
	 *
	 * @param isCopy true if it is a document copy
	 * @return this if nothing to print
	 */
	public LayoutEngine getPageable(final boolean isCopy)
	{
		setCopy(isCopy);
		if (getNumberOfPages() == 0
				|| !ArchiveEngine.isValid(this))
		{
			log.warn("Nothing to print - " + toString());
			return null;
		}
		return this;
	}    // getPageable

	/**************************************************************************
	 * Set Position on current page (no check)
	 *
	 * @param p point relative in area
	 */
	protected void setRelativePosition(final Point2D p)
	{
		if (p == null)
			return;
		Rectangle part = m_content;
		if (m_area == AREA_HEADER)
			part = m_header;
		else if (m_area == AREA_FOOTER)
			part = m_footer;
		m_position[m_area].setLocation(part.x + p.getX(), part.y + p.getY());
		log.trace("Page=" + m_pageNo + " [" + m_area + "] " + m_position[m_area].x + "/" + m_position[m_area].y);
	}    // setPosition

	/**
	 * Set Position on current page (no check)
	 *
	 * @param x x position in 1/72 inch
	 * @param y y position in 1/72 inch
	 */
	protected void setRelativePosition(final float x, final float y)
	{
		setRelativePosition(new Point2D.Float(x, y));
	}    // setPosition

	/**
	 * Get the current position on current page
	 *
	 * @return current position
	 */
	public Point2D getPosition()
	{
		return m_position[m_area];
	}    // getPosition

	/**
	 * Set X Position on current page
	 *
	 * @param x x position in 1/72 inch
	 */
	protected void setX(final float x)
	{
		m_position[m_area].x = x;
		log.trace("Page=" + m_pageNo + " [" + m_area + "] " + m_position[m_area].x + "/" + m_position[m_area].y);
	}    // setX

	/**
	 * Add to X Position on current page
	 *
	 * @param xOffset add offset to x position in 1/72 inch
	 */
	protected void addX(final float xOffset)
	{
		if (xOffset == 0f)
			return;
		m_position[m_area].x += xOffset;
		log.trace("Page=" + m_pageNo + " [" + m_area + "] " + m_position[m_area].x + "/" + m_position[m_area].y);
	}    // addX

	/**
	 * Get X Position on current page
	 *
	 * @return x position in 1/72 inch
	 */
	public float getX()
	{
		return (float)m_position[m_area].x;
	}    // getX

	/**
	 * Set Y Position on current page
	 *
	 * @param y y position in 1/72 inch
	 */
	protected void setY(final int y)
	{
		m_position[m_area].y = y;
		log.trace("Page=" + m_pageNo + " [" + m_area + "] " + m_position[m_area].x + "/" + m_position[m_area].y);
	}    // setY

	/**
	 * Add to Y Position - may cause New Page
	 *
	 * @param yOffset add offset to y position in 1/72 inch
	 */
	protected void addY(final int yOffset)
	{
		if (yOffset == 0f)
			return;
		if (isYspaceFor(yOffset))
		{
			m_position[m_area].y += yOffset;
			log.trace("Page=" + m_pageNo + " [" + m_area + "] " + m_position[m_area].x + "/" + m_position[m_area].y);
		}
		else if (m_area == AREA_CONTENT)
		{
			log.trace("Not enough Y space "
					+ m_lastHeight[m_area] + " - remaining " + getYspace() + " - Area=" + m_area);
			newPage(true, true);
			log.trace("Page=" + m_pageNo + " [" + m_area + "] " + m_position[m_area].x + "/" + m_position[m_area].y);
		}
		else
		{
			m_position[m_area].y += yOffset;
			log.error("Outside of Area: " + m_position);
		}
	}    // addY

	/**
	 * Get Y Position on current page
	 *
	 * @return y position in 1/72 inch
	 */
	public float getY()
	{
		return (float)m_position[m_area].y;
	}    // getY

	/**************************************************************************
	 * Return remaining X dimension space _ on current page in Area
	 *
	 * @return space in 1/72 inch remaining in line
	 */
	public float getXspace()
	{
		Rectangle part = m_content;
		if (m_area == AREA_HEADER)
			part = m_header;
		else if (m_area == AREA_FOOTER)
			part = m_footer;
		//
		return (float)(part.x + part.width - m_position[m_area].x);
	}    // getXspace

	/**
	 * Remaining Space is OK for Width in Area
	 *
	 * @param width width
	 * @return true if width fits in area
	 */
	public boolean isXspaceFor(final float width)
	{
		return (getXspace() - width) >= 0f;
	}    // isXspaceFor

	/**
	 * Return remaining Y dimension space | on current page in Area
	 *
	 * @return space in 1/72 inch remaining on page
	 */
	public float getYspace()
	{
		Rectangle part = m_content;
		if (m_area == AREA_HEADER)
			part = m_header;
		else if (m_area == AREA_FOOTER)
			part = m_footer;
		//
		return (float)(part.y + part.height - m_position[m_area].y);
	}    // getYspace

	/**
	 * Remaining Space is OK for Height in Area
	 *
	 * @param height height
	 * @return true if height fits in area
	 */
	public boolean isYspaceFor(final float height)
	{
		return (getYspace() - height) >= 0f;
	}    // isYspaceFor

	/**************************************************************************
	 * Create Standard Header/Footer
	 *
	 * <pre>
	 *  title           C        Page x of x
	 *  Copyright      who         date&time
	 * </pre>
	 */
	private void createStandardHeaderFooter()
	{
		final MPrintTableFormat tf = m_format.getTableFormat();

		// task 09359 get rid of logo. Commenting out so we have it in case we need it again

		// Header: Logo
		// {
		// final I_AD_ClientInfo ci = Services.get(IClientDAO.class).retrieveClientInfo(Env.getCtx());
		// final PrintElement logoElement;
		// if (ci.getLogoReport_ID() > 0)
		// {
		// logoElement = new ImageElement(ci.getLogoReport_ID(), false);
		// }
		// else
		// {
		// logoElement = new ImageElement(org.compiere.Adempiere.getProductLogoLarge()); // 48x15
		// }
		// logoElement.layout(LOGO_WIDTH_MAX, m_headerHeight, false, MPrintFormatItem.FIELDALIGNMENTTYPE_LeadingLeft);
		// logoElement.setLocation(m_header.getLocation());
		// //m_headerFooter.addElement(logoElement);
		// }

		// Header - Report name, Page count
		{
			final Font font = tf.getPageHeader_Font();
			final Color color = tf.getPageHeaderFG_Color();
			//
			PrintElement element = new StringElement("@*ReportName@", font, color, null, true);
			element.layout(m_header.width, 0, true, MPrintFormatItem.FIELDALIGNMENTTYPE_Center);
			element.setLocation(m_header.getLocation());
			m_headerFooter.addElement(element);
			//
			//
			element = new StringElement("@Page@ @*Page@ @slash@ @*PageCount@", font, color, null, true);
			element.layout(m_header.width, 0, true, MPrintFormatItem.FIELDALIGNMENTTYPE_TrailingRight);
			element.setLocation(m_header.getLocation());
			m_headerFooter.addElement(element);
		}

		// Footer
		{
			final Font font = tf.getPageFooter_Font();
			final Color color = tf.getPageFooterFG_Color();
			//
			PrintElement element = new StringElement(Adempiere.getBrandCopyright(), font, color, null, true);
			element.layout(m_footer.width, 0, true, MPrintFormatItem.FIELDALIGNMENTTYPE_LeadingLeft);
			Point ft = m_footer.getLocation();
			ft.y += m_footer.height - element.getHeight() - 2;    // 2pt above min
			element.setLocation(ft);
			m_headerFooter.addElement(element);
			//
			element = new StringElement("@*Header@", font, color, null, true);
			element.layout(m_footer.width, 0, true, MPrintFormatItem.FIELDALIGNMENTTYPE_Center);
			element.setLocation(ft);
			m_headerFooter.addElement(element);
			//
			element = new StringElement("@*CurrentDateTime@", font, color, null, true);
			element.layout(m_footer.width, 0, true, MPrintFormatItem.FIELDALIGNMENTTYPE_TrailingRight);
			element.setLocation(ft);
			m_headerFooter.addElement(element);
		}
	}    // createStandardHeaderFooter

	/**************************************************************************
	 * Layout Form.
	 * For every Row, loop through the Format
	 * and calculate element size and position.
	 */
	private void layoutForm()
	{
		// log.info("layoutForm");
		m_columnCount = 0;
		if (m_data == null)
			return;
		// for every row
		for (int row = 0; row < m_data.getRowCount(); row++)
		{
			log.info("Row=" + row);
			m_data.setRowIndex(row);
			boolean somethingPrinted = true;    // prevent NL of nothing printed and supress null
			// for every item
			for (int i = 0; i < m_format.getItemCount(); i++)
			{
				MPrintFormatItem item = m_format.getItem(i);
				// log.debug("layoutForm - Row=" + row + " - #" + i + " - " + item);
				if (!item.isPrinted())
					continue;
				// log.debug("layoutForm - Row=" + row + " - #" + i + " - " + item);
				m_columnCount++;
				// Read Header/Footer just once
				if (row > 0 && (item.isHeader() || item.isFooter()))
					continue;
				// Position
				if (item.isHeader())            // Area
					setArea(AREA_HEADER);
				else if (item.isFooter())
					setArea(AREA_FOOTER);
				else
					setArea(AREA_CONTENT);
				//
				if (item.isSetNLPosition() && item.isRelativePosition())
					m_tempNLPositon = 0;
				// New Page/Line
				if (item.isNextLine() && somethingPrinted)        // new line
				{
					newLine();
					somethingPrinted = false;
				}
				else
				{
					addX(m_lastWidth[m_area]);
				}
				if (item.isNextPage())            // item.isPageBreak() // new page
				{
					newPage(false, false);
				}
				// Relative Position space
				if (item.isRelativePosition())
				{
					addX(item.getXSpace());
					addY(item.getYSpace());
				}
				else
					// Absolute relative position
					setRelativePosition(item.getXPosition(), item.getYPosition());
				// Temporary NL Position when absolute positioned
				if (item.isSetNLPosition() && !item.isRelativePosition())
					m_tempNLPositon = (int)getPosition().getX();

				// line alignment
				String alignment = item.getFieldAlignmentType();
				int maxWidth = item.getMaxWidth();
				boolean lineAligned = false;
				if (item.isRelativePosition())
				{
					if (item.isLineAlignLeading())
					{
						alignment = MPrintFormatItem.FIELDALIGNMENTTYPE_LeadingLeft;
						maxWidth = getAreaBounds().width;
						lineAligned = true;
					}
					else if (item.isLineAlignCenter())
					{
						alignment = MPrintFormatItem.FIELDALIGNMENTTYPE_Center;
						maxWidth = getAreaBounds().width;
						lineAligned = true;
					}
					else if (item.isLineAlignTrailing())
					{
						alignment = MPrintFormatItem.FIELDALIGNMENTTYPE_TrailingRight;
						maxWidth = getAreaBounds().width;
						lineAligned = true;
					}
				}

				// Type
				PrintElement element = null;
				if (item.isTypePrintFormat())        // ** included PrintFormat
				{
					element = includeFormat(item, m_data);
				}
				else if (item.isBarcode())
				{
					element = createBarcodeElement(item);
					if (element != null)
					{
						element.layout(maxWidth, item.getMaxHeight(), false, alignment);
					}
				}
				else if (item.isTypeImage())        // ** Image
				{
					if (item.isImageField())
						element = createImageElement(item);
					else if (item.isImageIsAttached())
						element = ImageElement.get(item.get_ID());
					else
						element = ImageElement.get(item.getImageURL());
					if (element != null)
						element.layout(maxWidth, item.getMaxHeight(), false, alignment);
				}
				else if (item.isTypeField())        // ** Field
				{
					if (maxWidth == 0 && item.isFieldAlignBlock())
						maxWidth = getAreaBounds().width;
					element = createFieldElement(item, maxWidth, alignment, m_format.isForm());
				}
				else if (item.isTypeBox())            // ** Line/Box
				{
					if (m_format.isForm())
						element = createBoxElement(item);
					// Auto detect width - teo_sarca, BF [ 1825876 ]
					if (element != null && maxWidth == 0)
					{
						maxWidth = getAreaBounds().width;
						element.setMaxWidth(maxWidth);
					}
				}
				else
				// (item.isTypeText()) //** Text
				{
					if (maxWidth == 0 && item.isFieldAlignBlock())
						maxWidth = getAreaBounds().width;
					element = createStringElement(item.getPrintName(m_format.getLanguage()),
							item.getAD_PrintColor_ID(), item.getAD_PrintFont_ID(),
							maxWidth, item.getMaxHeight(), item.isHeightOneLine(), alignment, true);
				}

				// Printed - set last width/height
				if (element != null)
				{
					somethingPrinted = true;
					if (!lineAligned)
						m_lastWidth[m_area] = element.getWidth();
					m_lastHeight[m_area] = element.getHeight();
				}
				else
				{
					somethingPrinted = false;
					m_lastWidth[m_area] = 0f;
					m_lastHeight[m_area] = 0f;
					// Carlos Ruiz - globalqss - 20060826
					// Fix problem when the element is not printed but X Space was previously added
					if (item.isRelativePosition())
					{
						addX(-item.getXSpace());
						addY(-item.getYSpace());
					}
					// end globalqss
				}

				// Does it fit?
				if (item.isRelativePosition() && !lineAligned)
				{
					if (!isXspaceFor(m_lastWidth[m_area]))
					{
						log.trace("Not enough X space for "
								+ m_lastWidth[m_area] + " - remaining " + getXspace() + " - Area=" + m_area);
						newLine();
					}
					if (m_area == AREA_CONTENT && !isYspaceFor(m_lastHeight[m_area]))
					{
						log.trace("Not enough Y space "
								+ m_lastHeight[m_area] + " - remaining " + getYspace() + " - Area=" + m_area);
						newPage(true, true);
					}
				}
				// We know Position and Size
				// log.debug( "LayoutEngine.layoutForm",
				// "Page=" + m_pageNo + " [" + m_area + "] " + m_position[m_area].x + "/" + m_position[m_area].y
				// + " w=" + lastWidth[m_area] + ",h=" + lastHeight[m_area] + " " + item);
				if (element != null)
					element.setLocation(m_position[m_area]);
				// Add to Area
				if (m_area == AREA_CONTENT)
					m_currPage.addElement(element);
				else
					m_headerFooter.addElement(element);
				//
				if (m_lastHeight[m_area] > m_maxHeightSinceNewLine[m_area])
					m_maxHeightSinceNewLine[m_area] = m_lastHeight[m_area];
				// Reset maxHeightSinceNewLine if we have an absolute position - teo_sarca BF [ 1807917 ]
				if (!item.isRelativePosition())
					m_maxHeightSinceNewLine[m_area] = m_lastHeight[m_area];

			}    // for every item
		}    // for every row
	}    // layoutForm

	/**
	 * Include Table Format
	 *
	 * @param item print format item
	 * @param data print data
	 * @return Print Element
	 */
	private PrintElement includeFormat(final MPrintFormatItem item, final PrintData data)
	{
		newLine();
		PrintElement element = null;
		//
		MPrintFormat format = MPrintFormat.get(getCtx(), item.getAD_PrintFormatChild_ID(), false);
		format.setLanguage(m_format.getLanguage());
		if (m_format.isTranslationView())
			format.setTranslationLanguage(m_format.getLanguage());
		int AD_Column_ID = item.getAD_Column_ID();
		log.info(format + " - Item=" + item.getName() + " (" + AD_Column_ID + ")");
		//
		Object obj = data.getNode(new Integer(AD_Column_ID));
		// Object obj = data.getNode(item.getColumnName()); // slower
		if (obj == null)
		{
			data.dumpHeader();
			data.dumpCurrentRow();
			log.error("No Node - AD_Column_ID="
					+ AD_Column_ID + " - " + item + " - " + data);
			return null;
		}
		PrintDataElement dataElement = (PrintDataElement)obj;
		String recordString = dataElement.getValueKey();
		if (recordString == null || recordString.length() == 0)
		{
			data.dumpHeader();
			data.dumpCurrentRow();
			log.error("No Record Key - " + dataElement
					+ " - AD_Column_ID=" + AD_Column_ID + " - " + item);
			return null;
		}
		int Record_ID = 0;
		try
		{
			Record_ID = Integer.parseInt(recordString);
		}
		catch (Exception e)
		{
			data.dumpCurrentRow();
			log.error("Invalid Record Key - " + recordString
					+ " (" + e.getMessage()
					+ ") - AD_Column_ID=" + AD_Column_ID + " - " + item);
			return null;
		}
		MQuery query = new MQuery(format.getAD_Table_ID());
		query.addRestriction(item.getColumnName(), Operator.EQUAL, new Integer(Record_ID));
		format.setTranslationViewQuery(query);
		log.debug(query.toString());
		//
		DataEngine de = new DataEngine(format.getLanguage(), m_TrxName);
		PrintData includedData = de.getPrintData(data.getCtx(), format, query);
		if (includedData == null)
			return null;
		log.debug(includedData.toString());
		//
		element = layoutTable(format, includedData, item.getXSpace());
		// handle multi page tables
		if (element.getPageCount() > 1)
		{
			Point2D.Double loc = m_position[m_area];
			element.setLocation(loc);
			for (int p = 1; p < element.getPageCount(); p++)    // don't add last one
			{
				m_currPage.addElement(element);
				newPage(true, false);
			}
			m_position[m_area] = loc;
			((TableElement)element).setHeightToLastPage();
		}

		m_lastWidth[m_area] = element.getWidth();
		m_lastHeight[m_area] = element.getHeight();

		if (!isXspaceFor(m_lastWidth[m_area]))
		{
			log.trace("Not enough X space for "
					+ m_lastWidth[m_area] + " - remaining " + getXspace() + " - Area=" + m_area);
			newLine();
		}
		if (m_area == AREA_CONTENT && !isYspaceFor(m_lastHeight[m_area]))
		{
			log.trace("Not enough Y space "
					+ m_lastHeight[m_area] + " - remaining " + getYspace() + " - Area=" + m_area);
			newPage(true, false);
		}
		//
		return element;
	}    // includeFormat

	/**
	 * Create String Element
	 *
	 * @param content            string to be printed
	 * @param AD_PrintColor_ID   color
	 * @param AD_PrintFont_ID    font
	 * @param maxWidth           max width
	 * @param maxHeight          max height
	 * @param isHeightOneLine    onle line only
	 * @param FieldAlignmentType alignment type (MPrintFormatItem.FIELD_ALIGN_*)
	 * @param isTranslated       if true and content contaiins @variable@, it is dynamically translated during print
	 * @return Print Element
	 */
	private PrintElement createStringElement(final String content, final int AD_PrintColor_ID, final int AD_PrintFont_ID,
											 final int maxWidth, final int maxHeight, final boolean isHeightOneLine, final String FieldAlignmentType, final boolean isTranslated)
	{
		if (content == null || content.length() == 0)
			return null;
		// Color / Font
		Color color = getColor();    // default
		if (AD_PrintColor_ID != 0 && m_printColor.get_ID() != AD_PrintColor_ID)
		{
			MPrintColor c = MPrintColor.get(getCtx(), AD_PrintColor_ID);
			if (c.getColor() != null)
				color = c.getColor();
		}
		Font font = m_printFont.getFont();        // default
		if (AD_PrintFont_ID != 0 && m_printFont.get_ID() != AD_PrintFont_ID)
		{
			MPrintFont f = MPrintFont.get(AD_PrintFont_ID);
			if (f.getFont() != null)
				font = f.getFont();
		}
		PrintElement e = new StringElement(content, font, color, null, isTranslated);
		e.layout(maxWidth, maxHeight, isHeightOneLine, FieldAlignmentType);
		return e;
	}    // createStringElement

	/**
	 * Create Field Element
	 *
	 * @param item               Format Item
	 * @param maxWidth           max width
	 * @param FieldAlignmentType alignment type (MPrintFormatItem.FIELD_ALIGN_*)
	 * @param isForm             true if document
	 * @return Print Element or null if nothing to print
	 */
	private PrintElement createFieldElement(final MPrintFormatItem item, final int maxWidth,
											String FieldAlignmentType, final boolean isForm)
	{
		// Get Data
		Object obj = m_data.getNode(new Integer(item.getAD_Column_ID()));
		if (obj == null)
			return null;
		else if (obj instanceof PrintDataElement)
			;
		else
		{
			log.error("Element not PrintDataElement " + obj.getClass());
			return null;
		}

		// Convert DataElement to String
		PrintDataElement data = (PrintDataElement)obj;
		if (data.isNull() && item.isSuppressNull())
			return null;
		String stringContent = data.getValueDisplay(m_format.getLanguage());
		if ((stringContent == null || stringContent.length() == 0) && item.isSuppressNull())
			return null;
		// non-string
		Object content = stringContent;
		if (data.getValue() instanceof Boolean)
			content = data.getValue();

		// Convert AmtInWords Content to alpha
		if (item.getColumnName().equals("AmtInWords"))
		{
			log.debug("AmtInWords: " + stringContent);
			stringContent = getAmtInWords(m_format.getLanguage(), stringContent);
			content = stringContent;
		}
		// Label
		String label = item.getPrintName(m_format.getLanguage());
		String labelSuffix = item.getPrintNameSuffix(m_format.getLanguage());

		// ID Type
		NamePair ID = null;
		if (data.isID())
		{    // Record_ID/ColumnName
			Object value = data.getValue();
			if (value instanceof KeyNamePair)
				ID = KeyNamePair.of(((KeyNamePair)value).getKey(), item.getColumnName());
			else if (value instanceof ValueNamePair)
				ID = ValueNamePair.of(((ValueNamePair)value).getValue(), item.getColumnName());
		}
		else if (MPrintFormatItem.FIELDALIGNMENTTYPE_Default.equals(FieldAlignmentType))
		{
			if (data.isNumeric())
				FieldAlignmentType = MPrintFormatItem.FIELDALIGNMENTTYPE_TrailingRight;
			else
				FieldAlignmentType = MPrintFormatItem.FIELDALIGNMENTTYPE_LeadingLeft;
		}

		// Get Color/ Font
		Color color = getColor();    // default
		if (ID != null && !isForm)
			;                                    // link color/underline handeled in PrintElement classes
		else if (item.getAD_PrintColor_ID() != 0 && m_printColor.get_ID() != item.getAD_PrintColor_ID())
		{
			MPrintColor c = MPrintColor.get(getCtx(), item.getAD_PrintColor_ID());
			if (c.getColor() != null)
				color = c.getColor();
		}

		Font font = m_printFont.getFont();        // default
		if (item.getAD_PrintFont_ID() != 0 && m_printFont.get_ID() != item.getAD_PrintFont_ID())
		{
			MPrintFont f = MPrintFont.get(item.getAD_PrintFont_ID());
			if (f.getFont() != null)
				font = f.getFont();
		}

		// Create String, HTML or Location
		PrintElement e = null;
		if (data.getDisplayType() == DisplayType.Location)
		{
			e = new LocationElement(m_printCtx, ((KeyNamePair)ID).getKey(), font, color,
					item.isHeightOneLine(), label, labelSuffix, m_format.getLanguage().getAD_Language());
			e.layout(maxWidth, item.getMaxHeight(), item.isHeightOneLine(), FieldAlignmentType);
		}
		else
		{
			if (HTMLElement.isHTML(stringContent))
				e = new HTMLElement(stringContent);
			else
				e = new StringElement(content, font, color, isForm ? null : ID, label, labelSuffix);
			e.layout(maxWidth, item.getMaxHeight(), item.isHeightOneLine(), FieldAlignmentType);
		}
		return e;
	}    // createFieldElement

	/**************************************************************************
	 * Get Amount in Words
	 *
	 * @param language language
	 * @param amount numeric amount (352.80)
	 * @return amount in words (three*five*two 80/100)
	 */
	private static String getAmtInWords(final Language language, final String amount)
	{
		if (amount == null || language == null)
		{
			return amount;
		}
		// Try to find Class
		String className = "org.compiere.util.AmtInWords_";
		try
		{
			className += language.getLanguageCode().toUpperCase();
			final Class<?> clazz = Class.forName(className);
			final AmtInWords aiw = (AmtInWords)clazz.newInstance();
			return aiw.getAmtInWords(amount);
		}
		catch (final ClassNotFoundException e)
		{
			log.trace("Class not found: {}", className);
		}
		catch (final Exception e)
		{
			log.error(className, e);
		}

		// Fallback
		final StringBuilder sb = new StringBuilder();
		int pos = amount.lastIndexOf('.');
		final int pos2 = amount.lastIndexOf(',');
		if (pos2 > pos)
		{
			pos = pos2;
		}
		for (int i = 0; i < amount.length(); i++)
		{
			if (pos == i)    // we are done
			{
				final String cents = amount.substring(i + 1);
				sb.append(' ').append(cents).append("/100");
				break;
			}
			else
			{
				final char c = amount.charAt(i);
				if (c == ',' || c == '.')
				{
					continue;
				}
				if (!sb.isEmpty())
				{
					sb.append("*");
				}
				sb.append(Msg.getMsg(language, String.valueOf(c)));
			}
		}
		return sb.toString();
	}    // getAmtInWords

	/**
	 * Create Box/Line Element
	 *
	 * @param item item
	 * @return box element
	 */
	private PrintElement createBoxElement(final MPrintFormatItem item)
	{
		Color color = getColor();    // default
		if (item.getAD_PrintColor_ID() != 0
				&& m_printColor.get_ID() != item.getAD_PrintColor_ID())
		{
			MPrintColor c = MPrintColor.get(getCtx(), item.getAD_PrintColor_ID());
			if (c.getColor() != null)
				color = c.getColor();
		}
		return new BoxElement(item, color);
	}    // createBoxElement

	/**
	 * Create Image Element from item
	 *
	 * @param item item
	 * @return image element
	 */
	private PrintElement createImageElement(final MPrintFormatItem item)
	{
		Object obj = m_data.getNode(new Integer(item.getAD_Column_ID()));
		if (obj == null)
			return null;
		else if (obj instanceof PrintDataElement)
			;
		else
		{
			log.error("Element not PrintDataElement " + obj.getClass());
			return null;
		}

		PrintDataElement data = (PrintDataElement)obj;
		if (data.isNull() && item.isSuppressNull())
			return null;
		String url = data.getValueDisplay(m_format.getLanguage());
		if ((url == null || url.length() == 0))
		{
			if (item.isSuppressNull())
				return null;
			else
				// should create an empty area
				return null;
		}
		ImageElement element = null;
		if (data.getDisplayType() == DisplayType.Image)
		{
			element = ImageElement.get(data, url);
		}
		else
		{
			element = ImageElement.get(url);
		}
		return element;
	}    // createImageElement

	/**
	 * Create Barcode Element
	 *
	 * @param item item
	 * @return barcode element
	 */
	private PrintElement createBarcodeElement(final MPrintFormatItem item)
	{
		// Get Data
		Object obj = m_data.getNode(new Integer(item.getAD_Column_ID()));
		if (obj == null)
			return null;
		else if (obj instanceof PrintDataElement)
			;
		else
		{
			log.error("Element not PrintDataElement " + obj.getClass());
			return null;
		}

		// Convert DataElement to String
		PrintDataElement data = (PrintDataElement)obj;
		if (data.isNull() && item.isSuppressNull())
			return null;
		String stringContent = data.getValueDisplay(m_format.getLanguage());
		if ((stringContent == null || stringContent.length() == 0) && item.isSuppressNull())
			return null;

		BarcodeElement element = new BarcodeElement(stringContent, item);
		if (element.isValid())
			return element;
		return null;
	}    // createBarcodeElement

	/**
	 * Get default Color
	 *
	 * @return color
	 */
	public Color getColor()
	{
		if (m_printColor == null)
			return Color.BLACK;
		return m_printColor.getColor();
	}    // getColor

	/**************************************************************************
	 * Layout Table.
	 * Convert PrintData into TableElement
	 *
	 * @param format format to use
	 * @param printData data to use
	 * @param xOffset X Axis - offset (start of table) i.e. indentation
	 * @return TableElement
	 */
	private PrintElement layoutTable(final MPrintFormat format, final PrintData printData, final int xOffset)
	{
		log.info(format.getName() + " - " + printData.getName());
		MPrintTableFormat tf = format.getTableFormat();
		// Initial Values
		HashMap<Point, Font> rowColFont = new HashMap<>();
		MPrintFont printFont = MPrintFont.get(format.getAD_PrintFont_ID());
		rowColFont.put(new Point(TableElement.ALL, TableElement.ALL), printFont.getFont());
		tf.setStandard_Font(printFont.getFont());
		rowColFont.put(new Point(TableElement.HEADER_ROW, TableElement.ALL), tf.getHeader_Font());
		//
		HashMap<Point, Color> rowColColor = new HashMap<>();
		MPrintColor printColor = MPrintColor.get(getCtx(), format.getAD_PrintColor_ID());
		rowColColor.put(new Point(TableElement.ALL, TableElement.ALL), printColor.getColor());
		rowColColor.put(new Point(TableElement.HEADER_ROW, TableElement.ALL), tf.getHeaderFG_Color());
		//
		HashMap<Point, Color> rowColBackground = new HashMap<>();
		rowColBackground.put(new Point(TableElement.HEADER_ROW, TableElement.ALL), tf.getHeaderBG_Color());
		// Sizes
		boolean multiLineHeader = tf.isMultiLineHeader();
		int pageNoStart = m_pageNo;
		int repeatedColumns = 1;
		Rectangle firstPage = new Rectangle(m_content);
		firstPage.x += xOffset;
		firstPage.width -= xOffset;
		int yOffset = (int)m_position[AREA_CONTENT].y - m_content.y;
		firstPage.y += yOffset;
		firstPage.height -= yOffset;
		Rectangle nextPages = new Rectangle(m_content);
		nextPages.x += xOffset;
		nextPages.width -= xOffset;
		// Column count
		int columnCount = 0;
		for (int c = 0; c < format.getItemCount(); c++)
		{
			if (format.getItem(c).isPrinted())
				columnCount++;
		}
		// System.out.println("Cols=" + cols);

		// Header & Column Setup
		ValueNamePair[] columnHeader = new ValueNamePair[columnCount];
		int[] columnMaxWidth = new int[columnCount];
		int[] columnMaxHeight = new int[columnCount];
		boolean[] fixedWidth = new boolean[columnCount];
		boolean[] colSuppressRepeats = new boolean[columnCount];
		String[] columnJustification = new String[columnCount];
		HashMap<Integer, Integer> additionalLines = new HashMap<>();

		int col = 0;
		for (int c = 0; c < format.getItemCount(); c++)
		{
			MPrintFormatItem item = format.getItem(c);
			if (item.isPrinted())
			{
				if (item.isNextLine() && item.getBelowColumn() != 0)
				{
					additionalLines.put(new Integer(col), new Integer(item.getBelowColumn() - 1));
					if (!item.isSuppressNull())
					{
						item.setIsSuppressNull(true);    // display size will be set to 0 in TableElement
						item.save();
					}
				}
				columnHeader[col] = ValueNamePair.of(item.getColumnName(), item.getPrintName(format.getLanguage()));
				columnMaxWidth[col] = item.getMaxWidth();
				fixedWidth[col] = (columnMaxWidth[col] != 0 && item.isFixedWidth());
				colSuppressRepeats[col] = item.isSuppressRepeats();
				if (item.isSuppressNull())
				{
					if (columnMaxWidth[col] == 0)
						columnMaxWidth[col] = -1;        // indication suppress if Null
					else
						columnMaxWidth[col] *= -1;
				}
				columnMaxHeight[col] = item.getMaxHeight();
				if (item.isHeightOneLine())
					columnMaxHeight[col] = -1;
				columnJustification[col] = item.getFieldAlignmentType();
				if (columnJustification[col] == null || columnJustification[col].equals(MPrintFormatItem.FIELDALIGNMENTTYPE_Default))
					columnJustification[col] = MPrintFormatItem.FIELDALIGNMENTTYPE_LeadingLeft;    // when generated sets correct alignment
				// Column Fonts
				if (item.getAD_PrintFont_ID() != 0 && item.getAD_PrintFont_ID() != format.getAD_PrintFont_ID())
				{
					MPrintFont font = MPrintFont.get(item.getAD_PrintFont_ID());
					rowColFont.put(new Point(TableElement.ALL, col), font.getFont());
				}
				if (item.getAD_PrintColor_ID() != 0 && item.getAD_PrintColor_ID() != format.getAD_PrintColor_ID())
				{
					MPrintColor color = MPrintColor.get(getCtx(), item.getAD_PrintColor_ID());
					rowColColor.put(new Point(TableElement.ALL, col), color.getColor());
				}
				//
				col++;
			}
		}

		// The Data
		int rows = printData.getRowCount();
		// System.out.println("Rows=" + rows);
		Object[][] data = new Object[rows][columnCount];
		KeyNamePair[] pk = new KeyNamePair[rows];
		String pkColumnName = null;
		ArrayList<Integer> functionRows = new ArrayList<>();
		ArrayList<Integer> pageBreak = new ArrayList<>();

		// for all rows
		for (int row = 0; row < rows; row++)
		{
			// System.out.println("row=" + row);
			printData.setRowIndex(row);
			if (printData.isFunctionRow())
			{
				functionRows.add(new Integer(row));
				rowColFont.put(new Point(row, TableElement.ALL), tf.getFunct_Font());
				rowColColor.put(new Point(row, TableElement.ALL), tf.getFunctFG_Color());
				rowColBackground.put(new Point(row, TableElement.ALL), tf.getFunctBG_Color());
				if (printData.isPageBreak())
				{
					pageBreak.add(new Integer(row));
					log.trace("PageBreak row=" + row);
				}
			}
			// Summary/Line Levels for Finanial Reports
			else
			{
				int levelNo = printData.getLineLevelNo();
				if (levelNo != 0)
				{
					if (levelNo < 0)
						levelNo = -levelNo;
					Font base = printFont.getFont();
					if (levelNo == 1)
						rowColFont.put(new Point(row, TableElement.ALL), new Font(base.getName(),
								Font.ITALIC, base.getSize() - levelNo));
					else if (levelNo == 2)
						rowColFont.put(new Point(row, TableElement.ALL), new Font(base.getName(),
								Font.PLAIN, base.getSize() - levelNo));
				}
			}
			// for all columns
			col = 0;
			for (int c = 0; c < format.getItemCount(); c++)
			{
				MPrintFormatItem item = format.getItem(c);
				Object dataElement = null;
				if (item.isPrinted())    // Text Columns
				{
					if (item.isTypeImage())
					{
						if (item.isImageField())
						{
							Object obj = null;
							if (item.getAD_Column_ID() > 0) // teo_sarca, [ 1673542 ]
								obj = printData.getNode(new Integer(item.getAD_Column_ID()));
							if (obj == null)
								;
							else if (obj instanceof PrintDataElement)
							{
								PrintDataElement pde = (PrintDataElement)obj;
								// Get the PrintDataElement string value - teo_sarca [ 1673505 ]
								Object o = pde.getValue();
								String value = null;
								if (o == null)
									value = "";
								else if (o instanceof KeyNamePair)
									value = ((KeyNamePair)o).getName();
								else
									value = o.toString();

								data[row][col] = ImageElement.get(value);
							}
						}
						else if (item.isImageIsAttached())
							data[row][col] = ImageElement.get(item.get_ID());
						else
							data[row][col] = ImageElement.get(item.getImageURL());
						// Image layout - teo_sarca, [ 1673548 ]
						if (data[row][col] != null)
							((ImageElement)data[row][col]).layout(item.getMaxWidth(), item.getMaxHeight(), false, item.getFieldAlignmentType());
					}
					else if (item.isBarcode())
					{
						Object obj = null;
						if (item.getAD_Column_ID() > 0) // teo_sarca, [ 1673542 ]
							obj = printData.getNode(new Integer(item.getAD_Column_ID()));
						if (obj == null)
							;
						else if (obj instanceof PrintDataElement)
						{
							PrintDataElement pde = (PrintDataElement)obj;
							// Get the PrintDataElement string value - teo_sarca [ 1673505 ]
							String value = null;
							Object o = pde.getValue();
							if (o == null)
								value = "";
							if (o instanceof KeyNamePair)
								value = ((KeyNamePair)o).getID();
							else
								value = o.toString();
							BarcodeElement element = new BarcodeElement(value, item);

							if (element.isValid())
								data[row][col] = element;
						}

						if (data[row][col] != null)
							((BarcodeElement)data[row][col]).layout(item.getMaxWidth(), item.getMaxHeight(), false, item.getFieldAlignmentType());

					}
					else if (item.isTypeText())
					{
						data[row][col] = item.getPrintName(format.getLanguage());
					}
					else if (item.isTypeField())
					{
						Object obj = null;
						if (item.getAD_Column_ID() > 0) // teo_sarca, [ 1673542 ]
							obj = printData.getNode(new Integer(item.getAD_Column_ID()));
						if (obj == null)
							;
						else if (obj instanceof PrintDataElement)
						{
							PrintDataElement pde = (PrintDataElement)obj;
							if (pde.isID() || pde.isYesNo())
								dataElement = pde.getValue();
							else
								dataElement = pde.getValueDisplay(format.getLanguage());
						}
						else
							log.error("Element not PrintDataElement " + obj.getClass());
						// System.out.println("  row=" + row + ",col=" + col + " - " + item.getAD_Column_ID() + " => " + dataElement);
						data[row][col] = dataElement;
					}
					else
					// item.isTypeBox() or isTypePrintFormat()
					{
						log.warn("Unsupported: " + (item.isTypeBox() ? "Box" : "PrintFormat") + " in Table: " + item);
					}
					col++;
				}    // printed
			}    // for all columns

			PrintDataElement pde = printData.getPKey();
			if (pde != null)    // for FunctionRows
			{
				pk[row] = (KeyNamePair)pde.getValue();
				if (pkColumnName == null)
					pkColumnName = pde.getColumnName();
			}
			// else
			// System.out.println("No PK " + printData);
		}    // for all rows

		//
		TableElement table = new TableElement(columnHeader,
				columnMaxWidth, columnMaxHeight, columnJustification,
				fixedWidth, functionRows, multiLineHeader,
				data, pk, pkColumnName,
				pageNoStart, firstPage, nextPages, repeatedColumns, additionalLines,
				rowColFont, rowColColor, rowColBackground,
				tf, pageBreak, colSuppressRepeats);
		table.layout(0, 0, false, MPrintFormatItem.FIELDALIGNMENTTYPE_LeadingLeft);
		if (m_tableElement == null)
			m_tableElement = table;
		return table;
	}    // layoutTable

	/**
	 * Layout Parameter based on MQuery
	 *
	 * @return PrintElement
	 */
	private PrintElement layoutParameter()
	{
		if (m_query == null || !m_query.isActive())
			return null;
		//
		ParameterElement pe = new ParameterElement(m_query, m_printCtx, m_format.getTableFormat());
		pe.layout(0, 0, false, null);
		return pe;
	}    // layoutParameter

	/**
	 * Layout Process Instance Logs (if any)
	 *
	 * @return PrintElement
	 */
	private PrintElement layoutPInstanceLogs()
	{
		if (m_query == null || !m_query.isActive() || m_query.getPinstanceId() == null)
		{
			return null;
		}

		//
		PInstanceLogElement e = new PInstanceLogElement(m_printCtx, m_query, m_format.getTableFormat());
		if (e.getEffectiveRowCount() <= 0)
		{
			return null;
		}
		e.layout(0, 0, false, null);
		return e;
	}

	/**************************************************************************
	 * Get number of pages (Pageable Interface)
	 *
	 * @return number of pages
	 */
	@Override
	public int getNumberOfPages()
	{
		return m_pages.size();
	}    // getNumberOfPages

	/**
	 * Get Page Format (Pageable Interface)
	 *
	 * @param pageIndex page index
	 * @return Page Format
	 * @throws IndexOutOfBoundsException
	 */
	@Override
	public PageFormat getPageFormat(final int pageIndex) throws IndexOutOfBoundsException
	{
		if (!havePage(pageIndex))
			throw new IndexOutOfBoundsException("No page index=" + pageIndex);
		return getPageFormat();
	}    // getPageFormat

	/**
	 * Get Printable (PageableInterface)
	 *
	 * @param pageIndex page index
	 * @return this
	 * @throws IndexOutOfBoundsException
	 */
	@Override
	public Printable getPrintable(final int pageIndex) throws IndexOutOfBoundsException
	{
		if (!havePage(pageIndex))
			throw new IndexOutOfBoundsException("No page index=" + pageIndex);
		return this;
	}    // getPrintable

	/**
	 * Print Page (Printable Interface)
	 *
	 * @param graphics   graphics
	 * @param pageFormat page format (ignored)
	 * @param pageIndex  page index
	 * @return PageExists/NoSuchPage
	 * @throws PrinterException
	 */
	@Override
	public int print(final Graphics graphics, final PageFormat pageFormat, final int pageIndex)
			throws PrinterException
	{
		if (!havePage(pageIndex))
			return Printable.NO_SUCH_PAGE;
		//
		Rectangle r = new Rectangle(0, 0, (int)getPaper().getWidth(true), (int)getPaper().getHeight(true));
		Page page = getPage(pageIndex + 1);
		//
		// log.debug("#" + m_id, "PageIndex=" + pageIndex + ", Copy=" + m_isCopy);
		page.paint((Graphics2D)graphics, r, false, m_isCopy);    // sets context
		getHeaderFooter().paint((Graphics2D)graphics, r, false);
		//
		return Printable.PAGE_EXISTS;
	}    // print

	/**
	 * Do we have the page
	 *
	 * @param pageIndex page index
	 * @return true if page exists
	 */
	private boolean havePage(final int pageIndex)
	{
		if (pageIndex < 0 || pageIndex >= getNumberOfPages())
			return false;
		return true;
	}    // havePage

	/**
	 * Print Copy
	 *
	 * @return true if copy
	 */
	public boolean isCopy()
	{
		return m_isCopy;
	}    // isCopy

	/**
	 * Set Copy
	 *
	 * @param isCopy if true document is a copy
	 */
	public void setCopy(final boolean isCopy)
	{
		m_isCopy = isCopy;
	}    // setCopy

	/**************************************************************************
	 * Get the doc flavor (Doc Interface)
	 *
	 * @return SERVICE_FORMATTED.PAGEABLE
	 */
	@Override
	public DocFlavor getDocFlavor()
	{
		return DocFlavor.SERVICE_FORMATTED.PAGEABLE;
	}    // getDocFlavor

	/**
	 * Get Print Data (Doc Interface)
	 *
	 * @return this
	 * @throws IOException
	 */
	@Override
	public Object getPrintData() throws IOException
	{
		return this;
	}    // getPrintData

	/**
	 * Get Document Attributes (Doc Interface)
	 *
	 * @return null to obtain all attribute values from the
	 * job's attribute set.
	 */
	@Override
	public DocAttributeSet getAttributes()
	{
		return null;
	}    // getAttributes

	/**
	 * Obtains a reader for extracting character print data from this doc.
	 * (Doc Interface)
	 *
	 * @return null
	 * @throws IOException
	 */
	@Override
	public Reader getReaderForText() throws IOException
	{
		return null;
	}    // getReaderForText

	/**
	 * Obtains an input stream for extracting byte print data from this doc.
	 * (Doc Interface)
	 *
	 * @return null
	 * @throws IOException
	 */
	@Override
	public InputStream getStreamForBytes() throws IOException
	{
		return null;
	}    // getStreamForBytes

	public void setPrintInfo(final ArchiveInfo info)
	{
		m_PrintInfo = info;
	}

	/**
	 * @return PrintInfo
	 */
	public ArchiveInfo getPrintInfo()
	{
		return m_PrintInfo;
	}

}    // LayoutEngine
