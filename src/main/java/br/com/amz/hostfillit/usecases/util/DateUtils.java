package br.com.amz.hostfillit.usecases.util;

import java.time.LocalDate;

public class DateUtils {

    /**
     * Check if two given date ranges overlap.
     * Two date ranges overlap if one range starts before the other one ends, and it ends after the other one starts.
     *
     * @param start1 Start date of the first range
     * @param end1   End date of the first range
     * @param start2 Start date of the second range
     * @param end2   End date of the second range
     * @return True if there is an overlap, false otherwise
     */
    public static boolean hasDateOverlap(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return !start1.isAfter(end2) && !end1.isBefore(start2);
    }
}
