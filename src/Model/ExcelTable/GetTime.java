package Model.ExcelTable;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class GetTime {
    private LocalDate currentDate = LocalDate.now();
    private YearMonth ym;
    private LocalDate firstOfMonth;
    private TemporalAdjuster ta;
    private LocalDate weekStart;
    private LocalDate endOfMonth;

    private String dayOfMonth[] = {"MON", "TUE", "WED", "THURS", "FRID", "SAT", "SUN"};


    public GetTime(){
        int lastDayOfMonth[] = {31};

        for(int day : lastDayOfMonth){
            if(currentDate.getDayOfMonth() == day){
                this.currentDate = currentDate.with(TemporalAdjusters.firstDayOfNextMonth());
                break;
            }
        }

        ym = YearMonth.of(currentDate.getYear(), currentDate.getMonth());
        firstOfMonth = ym.atDay(1);
        ta = TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY);
        weekStart = firstOfMonth.with(ta);
        endOfMonth = ym.atEndOfMonth();
    }

    public String getMonth(){
        return currentDate.getMonth().toString();
    }

    public int getYear(){
        return currentDate.getYear();
    }

    public Object[] getDate(){
        Object[] obj = new Object[7];

        for(int i = 0; i < obj.length; i++){
            obj[i] = dayOfMonth[i] + " " + String.format("%02d" ,weekStart.getDayOfMonth()) + "/" + String.format("%02d",weekStart.getMonthValue());
            weekStart = weekStart.plusDays(1);
        }

        return obj;
    }

    public int totalWeek(){
        LocalDate countToEndWeek = firstOfMonth.with(ta);
        int count = 0;

        do {
            LocalDate weekStop = countToEndWeek.plusDays( 6 );
            countToEndWeek = countToEndWeek.plusWeeks( 1 ) ;
            count++;
        } while ( ! countToEndWeek.isAfter(endOfMonth) );
        return count;
    }

    public String endOfMonth(){
        return Integer.toString(endOfMonth.getDayOfMonth());
    }

    public String getMonthNum(String month){
        String monthInt = "0";
        switch (month) {
            case "JANUARY":     monthInt = "01";       break;
            case "FEBRUARY":    monthInt = "02";      break;
            case "MARCH":       monthInt = "03";         break;
            case "APRIL":       monthInt = "04";         break;
            case "MAY":         monthInt = "05";           break;
            case "JUNE":        monthInt = "06";          break;
            case "JULY":        monthInt = "07";          break;
            case "AUGUST":      monthInt = "08";        break;
            case "SEPTEMBER":   monthInt = "09";     break;
            case "OCTOBER":     monthInt = "10";       break;
            case "NOVEMBER":    monthInt = "11";      break;
            case "DECEMBER":    monthInt = "12";      break;
            default: monthInt = "32"; break;
        }

        return monthInt;
    }
}
