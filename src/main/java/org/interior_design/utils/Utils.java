package org.interior_design.utils;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/13
 * Time: 10:38 PM
 */

import lombok.extern.log4j.Log4j2;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @ 2025. All rights reserved
 */

@Log4j2
public class Utils {
    public static String printStackTrace(Exception e) {
        String result = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            result = sw.toString();
            pw.close();
            sw.close();
        } catch (Exception ex) {
            log.error(ex.fillInStackTrace());
        }
        return result;
    }
}
