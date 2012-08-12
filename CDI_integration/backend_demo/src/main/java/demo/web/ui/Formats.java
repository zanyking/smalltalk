/* Formats.java

	Purpose:
		
	Description:
		
	History:
		Aug 1, 2012, Created by Ian Tsai(Zanyking)

Copyright (C) 2010 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under ZOL in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
*/
package demo.web.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Ian Y.T Tsai(zanyking)
 *
 */
public class Formats {

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
        final SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return df.format(date);
    }
}
