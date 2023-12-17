// package HomeHealthManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;

public class Staff
{

    // declaring private variebels
    private int[] HoursWorked;
    private int[] paymentPrices;

    public Staff(int numStaff)
    {
        // setting private variables
        HoursWorked = new int[numStaff];
        paymentPrices = new int[numStaff];

    }
// calculating how many hours staff wroked

    public int[] calculateNumHoursWorked(int[] foundCounter, int numStaff)
    {
        int[] numHoursWorked = new int[numStaff];

        for (int i = 0; i < numStaff; i++)
        {
            numHoursWorked[i] = foundCounter[i] * 2;

        }
        return numHoursWorked;
    }
// calculating how much to pay staff

    public int[] calculatePaymentAmount(int[] numHoursWorked, int[] selectedWage, int numStaff)
    {
        int paymentAmount[] = new int[numStaff];

        for (int i = 0; i < numStaff; i++)
        {
            paymentAmount[i] = numHoursWorked[i] * selectedWage[i];

        }
        return paymentAmount;
    }

    public static void main(String[] args)
    {
        int numStaff = 0;
        // create object of class
        Staff staffObj = new Staff(numStaff);
    }

    // getters and setters
    public int[] getHoursWorked()
    {
        return HoursWorked;
    }

    public void setHoursWorked(int[] HoursWorked)
    {
        this.HoursWorked = HoursWorked;
    }

    public int[] getPaymentPrices()
    {
        return paymentPrices;
    }

    public void setPaymentPrices(int[] paymentPrices)
    {
        this.paymentPrices = paymentPrices;
    }

}
