package com.hospitalle.util;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class StringFormatter {
    public static String formatTime(LocalDateTime timeObj) {
        if (timeObj == null) return "";
        return timeObj.format(DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' h:mm a"));
    }

    public static String formatMoney(Integer money) {
        if (money == null) return "Unset";
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
        return formatter.format(money) + " /=";
    }

}
