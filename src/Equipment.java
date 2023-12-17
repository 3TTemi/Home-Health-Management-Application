// package HomeHealthManagement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Equipment
{

    // get private variables
    private int currentValue;
    private int daysTillRenewal;

    public Equipment()
    {
        currentValue = 0;
        daysTillRenewal = 0;

    }

    // calculate equipment value after time
    public int calculateCurrentValue(String dateBought, int purchasePrice)
    {
        double currentPriceValue = 0;
        double daysCount;
        double yearCount;
        double yearlyDepreciation;
        double totalDepreciation;
        int roundedPriceValue = 0;
        try
        {
            Calendar currentDateCalendar = new GregorianCalendar();
            Calendar dateBoughtCalendar = new GregorianCalendar();

            SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");

            String currentDate = getCurrentTime();

            Date date = sdf.parse(dateBought);
            dateBoughtCalendar.setTime(date);
            date = sdf.parse(currentDate);
            currentDateCalendar.setTime(date);

            daysCount = daysBetween(dateBoughtCalendar.getTime(), currentDateCalendar.getTime());
            yearCount = daysCount / 365;

            yearlyDepreciation = ((purchasePrice - 50) / 5);
            totalDepreciation = (yearlyDepreciation * yearCount);
            currentPriceValue = (purchasePrice - totalDepreciation);
            roundedPriceValue = (int) currentPriceValue;
            if (roundedPriceValue < 0)
            {
                roundedPriceValue = 0;
            }
        } catch (Exception e)
        {
            System.out.println("Something went bad!");
        }

        return roundedPriceValue;
    }

    // days between two dates
    public int daysBetween(Date date1, Date date2)
    {
        return (int) ((date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24));
    }

    
    
    // how many days until renewal calculation
    public int calculateRenewalTime(String dateBought)
    {
        int daysRenewal = 0;

        try
        {
            Calendar currentDateCalendar = new GregorianCalendar();
            Calendar dateBoughtCalendar = new GregorianCalendar();

            SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");

            String currentDate = getCurrentTime();

            Date date = sdf.parse(dateBought);
            dateBoughtCalendar.setTime(date);
            dateBoughtCalendar.add(Calendar.YEAR, 5);
            date = sdf.parse(currentDate);
            currentDateCalendar.setTime(date);

            daysRenewal = daysBetween(currentDateCalendar.getTime(), dateBoughtCalendar.getTime());
            if (daysRenewal < 0)
            {
                daysRenewal = 0;
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return daysRenewal;
    }

    // get current time on computer
    public String getCurrentTime()
    {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMdduuuu");
        LocalDate localDate = LocalDate.now();
        return (dtf.format(localDate));

    }

    public int getCurrentValue()
    {
        return currentValue;
    }

    public void setCurrentValue(int currentValue)
    {
        this.currentValue = currentValue;
    }

    public int getDaysTillRenewal()
    {
        return daysTillRenewal;
    }

    public void setDaysTillRenewal(int daysTillRenewal)
    {
        this.daysTillRenewal = daysTillRenewal;
    }

    public static void main(String[] args)
    {
        // make object of class
        Equipment equipmentObj = new Equipment();
        int days = equipmentObj.calculateCurrentValue("02042019", 5000);
        int test = equipmentObj.calculateRenewalTime("02042019");

    }

}
