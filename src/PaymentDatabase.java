
//package HomeHealthManagement;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

public class PaymentDatabase extends JFrame implements ActionListener
{

    // declaring private variables
    private JPanel titlePanel;
    private JPanel buttonPanel;
    private JButton closeButton;
    private JLabel frameTitle;

    private JButton homePageButton;

    private ArrayList<ArrayList<String>> dataList;
    private Object[][] data;
    private JTable paymentTable;
    private JScrollPane scrollTable;
    private JTableHeader header;
    private TableColumn column;
    private JButton deletePaymentButton;
    private JButton createPaymentButton;

    Connection myDbConn = null;

    String dbName = "HomeHealthCare";
    String[] columnNamesStaff
            =
            {
                "StaffID", "Name"
            };
    String tableName = "Payment";
    String[] columnNames
            =
            {
                "StaffID", "Name", "HoursWorked", "AmountToPay"
            };
    String[] columnNamesSchedule
            =
            {
                "Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
            };

    JavaDatabase dbObj = new JavaDatabase(dbName);

    public PaymentDatabase(String dbName, String tableName, String[] columnNames)
    {
        // consturcting frame
        this.setBounds(100, 100, 1500, 500);
        this.getContentPane().setBackground(HomePage.BAKGROUND_COLOR);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        myDbConn = dbObj.getDbConn();

        //creating and styling components
        titlePanel = new JPanel();
        buttonPanel = new JPanel();
        closeButton = new JButton("Close the Frame");
        closeButton.addActionListener(this);
        homePageButton = new JButton("Back To Home Page");
        homePageButton.addActionListener(this);
        frameTitle = new JLabel("Payment Datbase");
        deletePaymentButton = new JButton("Delete Payment Database");
        deletePaymentButton.addActionListener(this);
        createPaymentButton = new JButton("Create Payment Database");
        createPaymentButton.addActionListener(this);

        titlePanel.add(frameTitle);
        buttonPanel.add(closeButton);
        buttonPanel.add(Box.createHorizontalStrut(30));
        buttonPanel.add(deletePaymentButton);
        buttonPanel.add(Box.createHorizontalStrut(30));
        buttonPanel.add(createPaymentButton);
        buttonPanel.add(Box.createHorizontalStrut(30));
        buttonPanel.add(homePageButton);

        titlePanel.setLayout(new GridBagLayout());
        buttonPanel.setLayout(new GridBagLayout());

        titlePanel.setPreferredSize(new Dimension(500, 50));
        buttonPanel.setPreferredSize(new Dimension(500, 50));

        titlePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        JavaDatabase objDb = new JavaDatabase(dbName);
        dataList = objDb.getData(tableName, columnNames);
        objDb.getData(tableName, columnNames);

        data = objDb.to2ndArray(dataList);

        paymentTable = new JTable(data, columnNames);

        paymentTable.setGridColor(Color.GRAY);
        paymentTable.setBackground(HomePage.BAKGROUND_COLOR);
        paymentTable.setFont(new Font("Arial", Font.BOLD, 20));

        header = paymentTable.getTableHeader();
        header.setBackground(HomePage.BAKGROUND_COLOR);
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Arial", Font.BOLD, 15));

        paymentTable.setRowHeight(25);

        scrollTable = new JScrollPane();
        scrollTable.getViewport().add(paymentTable);
        paymentTable.setFillsViewportHeight(true);

        // adding variables to frame
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.add(scrollTable, BorderLayout.CENTER);

        this.setVisible(true);

    }

    public static void main(String[] args)
    {
        String dbName = "HomeHealthCare";
        String tableName = "Payment";
        String[] columnNames
                =
                {
                    "StaffID", "Name", "HoursWorked", "AmountToPay"
                };

        // make object of class
        new PaymentDatabase(dbName, tableName, columnNames);
    }

    // delete the datbase
    public void deletePaymentDB()
    {
        try
        {
            String deletePayment = "DELETE FROM Payment WHERE 1=1";

            PreparedStatement psDelete = myDbConn.prepareStatement(deletePayment);

            psDelete.executeUpdate();

            System.out.println("Data deleted successfully");
        } catch (Exception err)
        {
            err.printStackTrace();
        }
    }

    // recreate the database
    public void createPaymentDB()
    {
        PreparedStatement psCount = null;
        ResultSet rsCount = null;
        int numStaff = 0;

        String countQuery = "Select count(*) FROM Staff";
        try
        {
            psCount = myDbConn.prepareStatement(countQuery);
            rsCount = psCount.executeQuery();
            if (rsCount.next())
            {
                numStaff = rsCount.getInt(1);
            }
        } catch (Exception err)
        {
            err.printStackTrace();
        }

        ArrayList<ArrayList<String>> staffDataList = new ArrayList<ArrayList<String>>();
        staffDataList = dbObj.getData("Staff", columnNamesStaff);

        String insertStaffData = "INSERT INTO Payment (StaffID,Name) VALUES (?,?)";
        try
        {
            PreparedStatement psInsert = myDbConn.prepareStatement(insertStaffData);

            for (int i = 0; i < numStaff; i++)
            {
                psInsert.setString(1, staffDataList.get(i).get(0));
                psInsert.setString(2, staffDataList.get(i).get(1));

                psInsert.executeUpdate();
                System.out.println("Data inserted into Payment");

            }
        } catch (Exception err)
        {
            err.printStackTrace();
        }

        // Update / Calculate information in GUI 
        PreparedStatement psNames = null;
        ResultSet rsNames = null;
        String[] staffNames = new String[numStaff];

        String selectStaffNames = "SELECT * FROM Staff";
        try
        {
            psNames = myDbConn.prepareStatement(selectStaffNames);
            rsNames = psNames.executeQuery();

            for (int i = 0; i < numStaff; i++)
            {
                if (rsNames.next())
                {
                    staffNames[i] = rsNames.getString("Name");

                }
            }
        } catch (Exception error)
        {
            new Warning("A function", "Recheck your inputted values");
        }

        ArrayList<ArrayList<String>> scheduleData = new ArrayList<ArrayList<String>>();
        JavaDatabase dbObj = new JavaDatabase(dbName);
        scheduleData = dbObj.getData("WorkSchedule", columnNamesSchedule);
        int[] counterFound = new int[numStaff];
        Arrays.fill(counterFound, 0);
        int[] numHoursWorked = new int[numStaff];

        for (int i = 0; i < scheduleData.size(); i++)
        {
            for (int j = 0; j < scheduleData.get(i).size(); j++)
            {

                for (int h = 0; h < numStaff; h++)
                {

                    if (staffNames[h].equals(scheduleData.get(i).get(j)))
                    {

                        counterFound[h]++;
                    }
                }
            }
        }

        Staff staffObj = new Staff(numStaff);
        numHoursWorked = staffObj.calculateNumHoursWorked(counterFound, numStaff);

        for (int k = 0; k < numStaff; k++)
        {

            try
            {
                String updateQuery = "UPDATE Payment SET HoursWorked = ? WHERE Name = ?";

                PreparedStatement psUpdate = myDbConn.prepareStatement(updateQuery);

                psUpdate.setInt(1, numHoursWorked[k]);
                psUpdate.setString(2, staffNames[k]);

                psUpdate.executeUpdate();
                System.out.println("Hours Worked Updated successfully");
            } catch (Exception e)
            {
                e.printStackTrace();

            }
        }

        PreparedStatement psSelectWage = null;
        ResultSet rsSelectWage = null;
        int selectedWage[] = new int[numStaff];
        int paymentAmount[] = new int[numStaff];

        for (int m = 0; m < numStaff; m++)
        {
            String selectWage = "SELECT Wage FROM Staff WHERE Name = " + "'" + staffNames[m] + "'";
            try
            {
                psSelectWage = myDbConn.prepareStatement(selectWage);
                rsSelectWage = psSelectWage.executeQuery();

                if (rsSelectWage.next())
                {
                    selectedWage[m] = Integer.parseInt(rsSelectWage.getString(1));
                }

            } catch (Exception error)
            {
                new Warning("A function", "Recheck your inputted values");
                error.printStackTrace();
            }
        }

        paymentAmount = staffObj.calculatePaymentAmount(numHoursWorked, selectedWage, numStaff);

        for (int n = 0; n < numStaff; n++)
        {

            try
            {
                String updateQuery = "UPDATE Payment SET AmountToPay = ? WHERE Name = ?";

                PreparedStatement psUpdate = myDbConn.prepareStatement(updateQuery);

                psUpdate.setInt(1, paymentAmount[n]);
                psUpdate.setString(2, staffNames[n]);

                psUpdate.executeUpdate();
                System.out.println("Amount to Pay Updated successfully");
            } catch (Exception e)
            {
                e.printStackTrace();

            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        // command to recieve input
        Object command = e.getSource();

        if (command == closeButton)
        {
            this.dispose();
        }
        if (command == homePageButton)
        {
            this.dispose();
            new HomePage();
        }
        if (command == deletePaymentButton)
        {
            deletePaymentDB();
            this.dispose();
            new PaymentDatabase(dbName, tableName, columnNames);
        }
        if (command == createPaymentButton)
        {
            createPaymentDB();
            this.dispose();
            new PaymentDatabase(dbName, tableName, columnNames);
        }

    }

}
