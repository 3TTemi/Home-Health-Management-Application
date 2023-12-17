// package HomeHealthManagement;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class InstallDb
{

    public static void main(String[] args)
    {
        // create database "HomeHealthCare"
        String dbName = "HomeHealthCare";
        JavaDatabase dbObject = new JavaDatabase();
        dbObject.createDB(dbName);

        // create table "newTable" with columns
        String newTable = "CREATE TABLE Patients (PatientID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                + "Name varchar(50), Gender varchar(50), Weight int, Height int, Age int,"
                + "Disability varchar(50), Calories int, NextAppointment int,"
                + "Home varchar(50), Treatment varchar(100))";

        dbObject.createTable(newTable, dbName);

        newTable = "CREATE Table Staff (StaffID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), Name varchar(50),"
                + "Specialty varchar(50), Wage int, HoursAWeek int)";

        dbObject.createTable(newTable, dbName);

        newTable = "CREATE Table Equipment (SerialNumber varchar(50) PRIMARY KEY, "
                + "RetailRestocker varchar(50), GroupHome varchar(50), "
                + "CurrentValue int, Quantity int, OriginalPrice int, DateBought varchar(50), DaysUntilRenewal int )";

        dbObject.createTable(newTable, dbName);

        newTable = "CREATE TABLE WorkSchedule (Time varchar(50), Monday varchar(50),"
                + "Tuesday varchar(50), Wednesday varchar(50), Thursday varchar(50),"
                + "Friday varchar(50), Saturday varchar(50), Sunday varchar(50))";
        dbObject.createTable(newTable, dbName);

        newTable = "CREATE TABLE Payment(StaffID int, Name varchar(50), HoursWorked int, AmountToPay int)";

        dbObject.createTable(newTable, dbName);

    }

}
