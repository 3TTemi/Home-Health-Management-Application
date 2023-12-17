// package HomeHealthManagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.io.*;
import java.sql.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class Payment extends JFrame implements ActionListener
{

    // declaring private variables
    private JPanel titlePanel;
    private JPanel buttonPanel;
    private JPanel mainPanel;
    private JButton closeButton;
    private JLabel frameTitle;
    private JComboBox nameSelect;
    private JLabel hoursWorked;
    private JLabel amountPay;
    private JPanel displayBox;
    private JPanel scrollBox;
    private JLabel scrollText;
    private JButton downloadButton;
    private JPanel displayOrder;
    private JPanel displayInfo;
    private JButton homePageButton;

    Connection myDbConn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    String dbName = "HomeHealthCare";
    String tableName = "WorkSchedule";
    String[] columnNames
            =
            {
                "Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
            };
    String[] columnNamesStaff
            =
            {
                "StaffID", "Name"
            };
    JavaDatabase dbObj = new JavaDatabase(dbName);

    public Payment()
    {
        // consturcting frame
        this.setBounds(100, 100, 600, 500);
        this.getContentPane().setBackground(HomePage.BAKGROUND_COLOR);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        myDbConn = dbObj.getDbConn();

        //creating and styling components
        titlePanel = new JPanel();
        buttonPanel = new JPanel();
        mainPanel = new JPanel();
        homePageButton = new JButton("Back To Home Page");
        homePageButton.addActionListener(this);
        closeButton = new JButton("Close the Program");
        closeButton.addActionListener(this);
        frameTitle = new JLabel("Payment");
        hoursWorked = new JLabel("Hours Worked:XXX");
        amountPay = new JLabel("Amount to pay: XXX");
        displayBox = new JPanel();
        scrollBox = new JPanel();
        scrollText = new JLabel("-Select-");
        downloadButton = new JButton("Donwload Spreadsheet");
        downloadButton.addActionListener(this);
        displayOrder = new JPanel();
        displayInfo = new JPanel();
        String[] nameList =
        {
            "Name1", "Name2", "Name3", "Name4"
        };

        nameSelect = new JComboBox(nameList);
        loadStaffNames();
        nameSelect.addActionListener(this);

        titlePanel.add(frameTitle);
        buttonPanel.add(closeButton);
        buttonPanel.add(Box.createHorizontalStrut(30));
        buttonPanel.add(homePageButton);

        titlePanel.setLayout(new GridBagLayout());
        buttonPanel.setLayout(new GridBagLayout());

        titlePanel.setPreferredSize(new Dimension(500, 50));
        buttonPanel.setPreferredSize(new Dimension(500, 50));
        titlePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        mainPanel.setBackground(HomePage.BAKGROUND_COLOR);

        displayBox.setBackground(HomePage.BAKGROUND_COLOR);
        displayBox.setLayout(new BoxLayout(displayBox, BoxLayout.PAGE_AXIS));
        displayBox.add(hoursWorked);
        displayBox.add(Box.createVerticalStrut(20));
        displayBox.add(amountPay);
        scrollBox.setLayout(new BoxLayout(scrollBox, BoxLayout.PAGE_AXIS));
        scrollBox.add(scrollText);
        scrollBox.add(Box.createVerticalStrut(10));
        scrollBox.add(nameSelect);
        scrollBox.setBorder(BorderFactory.createLineBorder(Color.black));
        scrollText.setAlignmentX(Component.CENTER_ALIGNMENT);

        displayInfo.setBackground(HomePage.BAKGROUND_COLOR);
        displayInfo.add(scrollBox);
        displayInfo.add(Box.createHorizontalStrut(50));
        displayInfo.add(displayBox);

        displayOrder.setBackground(HomePage.BAKGROUND_COLOR);
        displayOrder.setLayout(new BoxLayout(displayOrder, BoxLayout.PAGE_AXIS));
        displayOrder.add(displayInfo);
        displayOrder.add(Box.createVerticalStrut(20));
        displayOrder.add(downloadButton);
        downloadButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.setLayout(new GridBagLayout());
        mainPanel.add(displayOrder);

        PreparedStatement psAlter = null;
        ResultSet rsAlter = null;

        // adding variables to frame
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.add(mainPanel, BorderLayout.CENTER);

        this.setVisible(true);

    }

    // load Staff names into combo box
    public void loadStaffNames()
    {
        nameSelect = new JComboBox();

        String dbQuery = "SELECT * FROM Staff";
        try
        {
            ps = myDbConn.prepareStatement(dbQuery);
            rs = ps.executeQuery();
            nameSelect.addItem("Press Here");
            while (rs.next())
            {
                nameSelect.addItem(rs.getString("Name"));
            }

        } catch (Exception e)
        {
            new Warning("A function", "Recheck your inputted values");
//            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        // make object of class
        Payment paymentObj = new Payment();

    }

    public void export()
    {
        String jdbcURL = "jdbc:derby:HomeHealthCare";
        String username = "";
        String password = "";

        String excelFilePath = "Payment-export.xlsx";

        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password))
        {
            String sql = "SELECT * FROM Payment";

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Payment");

            writeHeaderLine(sheet);

            writeDataLines(result, workbook, sheet);

            FileOutputStream outputStream = new FileOutputStream(excelFilePath);
            workbook.write(outputStream);
            workbook.close();

            statement.close();

        } catch (SQLException e)
        {
            System.out.println("Datababse error:");
            e.printStackTrace();
        } catch (IOException e)
        {
            System.out.println("File IO error:");
            e.printStackTrace();
        }
    }

    private void writeHeaderLine(XSSFSheet sheet)
    {

        Row headerRow = sheet.createRow(0);

        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("Staff ID");

        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("Staff Name");

        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("Number of Hours Worked");

        headerCell = headerRow.createCell(3);
        headerCell.setCellValue("Amount to Pay");

    }

    private void writeDataLines(ResultSet result, XSSFWorkbook workbook,
            XSSFSheet sheet) throws SQLException
    {
        int rowCount = 1;

        while (result.next())
        {
            int staffID = result.getInt("StaffID");
            String name = result.getString("Name");
            int hoursWorked = result.getInt("HoursWorked");
            int amountToPay = result.getInt("AmountToPay");

            Row row = sheet.createRow(rowCount++);

            int columnCount = 0;
            Cell cell = row.createCell(columnCount++);
            cell.setCellValue(staffID);

            cell = row.createCell(columnCount++);
            cell.setCellValue(name);

            cell = row.createCell(columnCount++);
            cell.setCellValue(hoursWorked);

            cell = row.createCell(columnCount++);
            cell.setCellValue(amountToPay);

        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {

        PreparedStatement psCount = null;
        ResultSet rsCount = null;
        PreparedStatement psCountNames = null;
        ResultSet rsCountNames = null;
        PreparedStatement psSelectWage = null;
        ResultSet rsSelectWage = null;

        int numStaff = 0;
        String selectCountQuery = "";
        int fullStaffOccurences = 0;
        int numHoursWorked;
        int paymentAmount;
        int selectedWage = 0;

        String countQuery = "Select count(*) FROM Staff";
        try
        {
            psCount = myDbConn.prepareStatement(countQuery);
            rsCount = psCount.executeQuery();
            if (rsCount.next())
            {
                numStaff = rsCount.getInt(1);
                System.out.println(numStaff);
            }
        } catch (Exception err)
        {
            err.printStackTrace();
        }

        ArrayList<String> staffWages = new ArrayList<String>();

        // command to recieve input
        Object command = e.getSource();

        // if buttons pressed do the subsequent action
        if (command == closeButton)
        {
            this.dispose();
        }
        if (command == homePageButton)
        {
            this.dispose();
            new HomePage();
        }
        if (command == downloadButton)
        {
            export();
        }

        String nameSelected = nameSelect.getSelectedItem().toString();

        if (nameSelected.equals("Press Here"))
        {
            hoursWorked.setText("Hours Worked:XXX");
            amountPay.setText("Amount to pay: XXX");
        }
        if (!nameSelected.equals("Press Here"))
        {

            ArrayList<ArrayList<String>> scheduleData = new ArrayList<ArrayList<String>>();
            JavaDatabase dbObj = new JavaDatabase(dbName);
            scheduleData = dbObj.getData("WorkSchedule", columnNames);
            int counterFound = 0;

            for (int i = 0; i < scheduleData.size(); i++)
            {
                for (int j = 0; j < scheduleData.get(i).size(); j++)
                {
                    if (nameSelected.equals(scheduleData.get(i).get(j)))
                    {
                        counterFound++;
                    }
                }
            }
            System.out.println(counterFound);

            numHoursWorked = counterFound * 2;
            hoursWorked.setText("Hours Worked: " + numHoursWorked);

            String selectWage = "SELECT Wage FROM Staff WHERE Name = " + "'" + nameSelected + "'";
            try
            {
                psSelectWage = myDbConn.prepareStatement(selectWage);
                rsSelectWage = psSelectWage.executeQuery();

                if (rsSelectWage.next())
                {
                    selectedWage = Integer.parseInt(rsSelectWage.getString(1));
                }

            } catch (Exception error)
            {
                new Warning("A function", "Recheck your inputted values");
                error.printStackTrace();
            }

            paymentAmount = numHoursWorked * selectedWage;
            amountPay.setText("Amount to pay: " + paymentAmount);

        }

    }
}
