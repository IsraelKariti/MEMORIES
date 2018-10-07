package com.example.izi.memories.Utility;

import java.util.Calendar;

public class TotalDay {
    public static int getTotalDay(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day_of_month = calendar.get(Calendar.DAY_OF_MONTH);
        int totalDay = getTotalDayForSpecificDay(year, month, day_of_month);
        return totalDay;
    }

    // month is 0-based
    public static int getTotalDayForSpecificDay(int year, int month, int day_of_month){
        int daysInPastYears = daysInPastYears(year -1);
        int daysInPastMonths = daysInPastMonths(month);
        return daysInPastYears + daysInPastMonths + day_of_month;
    }

    public static int daysInPastYears(int years){
        int incubatedYRS4 = years / 4;
        int incubatedYRS100 = years / 100;
        int incubatedYRS400 = years / 400;
        int incubatedYRS = incubatedYRS4 - incubatedYRS100 + incubatedYRS400;
        int normalYRS = years - incubatedYRS;
        int res = normalYRS*365 + incubatedYRS*366;
        return res;
    }

    public static int daysInPastMonths(int month){
        switch(month){
            case 0: // user Picked January
                return 0;
            case 1: // user Picked February
                return 31;
            case 2: // user Picked March
                return 31+28;
            case 3: // user Picked April
                return 31+28+31;
            case 4: // user Picked May
                return 31+28+31+30;
            case 5: // user Picked june
                return 31+28+31+30+31;
            case 6: // user Picked July
                return 31+28+31+30+31+30;
            case 7: // user Picked August
                return 31+28+31+30+31+30+31;
            case 8: // user Picked September
                return 31+28+31+30+31+30+31+31;
            case 9: // user Picked October
                return 31+28+31+30+31+30+31+31+30;
            case 10: // user Picked November
                return 31+28+31+30+31+30+31+31+30+31;
            case 11: // user Picked December
                return 31+28+31+30+31+30+31+31+30+31+30;
        }
        return 0;
    }
}
