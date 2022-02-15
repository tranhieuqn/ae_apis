package com.ae.apis.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateTimeUtils {
    static {
        Locale.setDefault(new Locale("vn", "VN"));
    }

    private static final Logger log = LoggerFactory.getLogger(DateTimeUtils.class);

    private static DateTimeUtils instance;
    private Calendar calendar;

    private DateTimeUtils() {
        calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"), Locale.getDefault());
    }

    public static DateTimeUtils getInstance() {
        if (instance == null) {
            instance = new DateTimeUtils();
        }

        return instance;
    }

    public synchronized Date getNow() {
        return calendar.getTime();
    }


    public synchronized String getNowStr(String pattern) {
        if (pattern == null) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(getNow());
    }

    public synchronized String getDateStrFromPattern(Date date, String pattern) {
        if (pattern == null) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public synchronized Date getAddDate(Date date, int dayAdd) {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE, dayAdd);
            return c.getTime();
        } catch (Exception e) {
            log.error("Can't getAddDate with date={}, num={}", date, dayAdd, e);
            return null;
        }
    }

    public synchronized Date addHoursToDate(Date date, String hours) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        List<Integer> mTime = getTime(hours);
        if (!mTime.isEmpty()) {
            calendar.add(Calendar.HOUR_OF_DAY, mTime.get(0));
            calendar.add(Calendar.MINUTE, mTime.get(1));
            if (mTime.size() > 2)
                calendar.add(Calendar.SECOND, mTime.get(2));
        }

        return calendar.getTime();
    }

    public synchronized String getTimeFromDate(Date date, String timePattern) {
        if (null == date)
            return null;

        SimpleDateFormat dateFormat = new SimpleDateFormat(timePattern);
        return dateFormat.format(date);

    }

    public synchronized Date getDateFromStr(String dateStr, String pattern) throws ParseException {
        if (isEmpty(dateStr))
            return null;

        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.parse(dateStr);

    }

    private List<Integer> getTime(String hours) throws ParseException {
        if (hours == null || hours.length() == 0) {
            return new ArrayList<>();
        }
        if (isTimeFormat(hours)) {
            if (!hours.contains(":")) {
                String[] t = hours.split("(?<=\\G.{2})");
                return getIntList(t);
            }

            String[] t = hours.split(":");
            return getIntList(t);

        } else {
            throw new ParseException("Can't parse " + hours + " to time HH:MM or HH:MM:SS format, Please pass parameter like HH:MM or HH:MM:SS format", 0);
        }
    }

    private List<Integer> getIntList(String[] t) {
        List<Integer> time = new ArrayList<>();
        if (t.length > 0) {
            for (String s : t) {
                time.add(Integer.parseInt(s));
            }
        }
        return time;
    }

    private boolean isTimeFormat(final CharSequence cs) {
        if (cs == null) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(cs.charAt(i)) && cs.charAt(i) != ':') {
                return false;
            }
        }
        return true;
    }

    private boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }
}