package org.interior_design.utils;

/**
 * Project: INTERIOR_DESIGN
 * Date: 2025/05/13
 * Time: 10:38 PM
 */

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.interior_design.dto.ProjectDTO;
import org.interior_design.dto.ProjectStatusDTO;
import org.interior_design.model.ImageAttribute;
import org.interior_design.model.Project;
import org.interior_design.model.ProjectStatus;
import org.interior_design.repository.ImageAttributeRepositoryJPA;
import org.interior_design.repository.ImageRepositoryJPA;
import org.interior_design.repository.ProjectRepositoryJPA;
import org.interior_design.repository.ProjectStatusRepositoryJPA;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public static boolean isNullOrEmpty(String value) {
        if (value == null) {
            return true;
        }
        return value.compareToIgnoreCase("") == 0;
    }
}
