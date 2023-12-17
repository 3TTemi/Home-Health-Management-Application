// package HomeHealthManagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class WorkSchedule extends JFrame implements ActionListener
{

    // declaring private variables
    String dbName = "HomeHealthCare";
    String tableName = "WorkSchedule";
    String[] columnNames
            =
            {
                "Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
            };
    JavaDatabase dbObj = new JavaDatabase(dbName);
    Connection myDbConn = dbObj.getDbConn();

    private JPanel titlePanel;
    private JPanel buttonPanel;
    private JPanel mainPanel;
    private JButton closeButton;
    private JLabel frameTitle;
    private JButton homePageButton;
    private JButton createSchedule;
    private JButton deleteSchedule;
    private JButton fillSchedule;

    private ArrayList<ArrayList<String>> dataList;
    private Object[][] data;
    private JTable workTable;
    private JScrollPane scrollTable;
    private JTableHeader header;
    private TableColumn column;

    PreparedStatement psCount = null;
    ResultSet rsCount = null;
    PreparedStatement psNames = null;
    ResultSet rsNames = null;
    PreparedStatement psHours = null;
    ResultSet rsHours = null;
    int numStaff = 0;

    public WorkSchedule(String dbName, String tableName, String[] columnNames)
    {
        // consturcting frame
        this.setBounds(100, 100, 950, 500);
        this.getContentPane().setBackground(HomePage.BAKGROUND_COLOR);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //creating and styling components
        titlePanel = new JPanel();
        buttonPanel = new JPanel();
        mainPanel = new JPanel();
        closeButton = new JButton("Close the Frame");
        closeButton.addActionListener(this);
        homePageButton = new JButton("Back To Home Page");
        homePageButton.addActionListener(this);
        createSchedule = new JButton("Create Schedule");
        createSchedule.addActionListener(this);
        deleteSchedule = new JButton("Delete Schedule");
        deleteSchedule.addActionListener(this);
        fillSchedule = new JButton("Fill Schedule");
        fillSchedule.addActionListener(this);

        frameTitle = new JLabel("Work Schedule");

        titlePanel.add(frameTitle);
        buttonPanel.add(closeButton);
        buttonPanel.add(Box.createHorizontalStrut(30));
        buttonPanel.add(createSchedule);
        buttonPanel.add(Box.createHorizontalStrut(30));
        buttonPanel.add(deleteSchedule);
        buttonPanel.add(Box.createHorizontalStrut(30));
        buttonPanel.add(fillSchedule);
        buttonPanel.add(Box.createHorizontalStrut(30));
        buttonPanel.add(homePageButton);

        titlePanel.setLayout(new GridBagLayout());
        buttonPanel.setLayout(new GridBagLayout());

        titlePanel.setPreferredSize(new Dimension(500, 50));
        buttonPanel.setPreferredSize(new Dimension(500, 50));
        titlePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        mainPanel.setBackground(HomePage.BAKGROUND_COLOR);

        JavaDatabase objDb = new JavaDatabase(dbName);
        dataList = objDb.getData(tableName, columnNames);
        objDb.getData(tableName, columnNames);

        data = objDb.to2ndArray(dataList);

        workTable = new JTable(data, columnNames);
        workTable.setGridColor(Color.GRAY);
        workTable.setBackground(HomePage.BAKGROUND_COLOR);
        workTable.setFont(new Font("Arial", Font.BOLD, 20));

        header = workTable.getTableHeader();
        header.setBackground(HomePage.BAKGROUND_COLOR);
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Arial", Font.BOLD, 15));

        workTable.setRowHeight(25);

        scrollTable = new JScrollPane();
        scrollTable.getViewport().add(workTable);
        workTable.setFillsViewportHeight(true);

        mainPanel.setLayout(new GridBagLayout());

        // adding variables to frame
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.add(scrollTable, BorderLayout.CENTER);

        this.setVisible(true);

    }

    public static void main(String[] args)
    {

        String dbName = "HomeHealthCare";
        String tableName = "WorkSchedule";
        String[] columnNames
                =
                {
                    "Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
                };
        // make object of class
        WorkSchedule workObj = new WorkSchedule(dbName, tableName, columnNames);

    }

    // create blank work schedule
    public void createBlankSchedule(String[] hourTimes)
    {
        for (int rowCount = 0; rowCount < 6; rowCount++)
        {
            try
            {
                String insertSchedule = "INSERT INTO WorkSchedule VALUES (?,?,?,?,?,?,?,?)";

                PreparedStatement psInsert = myDbConn.prepareStatement(insertSchedule);

                psInsert.setString(1, hourTimes[rowCount]);
                psInsert.setString(2, "-");
                psInsert.setString(3, "-");
                psInsert.setString(4, "-");
                psInsert.setString(5, "-");
                psInsert.setString(6, "-");
                psInsert.setString(7, "-");
                psInsert.setString(8, "-");

                psInsert.executeUpdate();

                System.out.println("Data inserted successfully");
            } catch (Exception err)
            {
                err.printStackTrace();
            }
        }
    }
// delete all data in work schedule

    public void deleteScheduleInfo()
    {
        try
        {
            String deleteSchedule = "DELETE FROM WorkSchedule WHERE 1=1";

            PreparedStatement psDelete = myDbConn.prepareStatement(deleteSchedule);

            psDelete.executeUpdate();

            System.out.println("Data deleted successfully");
        } catch (Exception err)
        {
            err.printStackTrace();
        }
    }
// fill work schedule with staff according to their hours 

    public void fillWorkSchedule(int staffHours[], String staffNames[])
    {
        String[] daysList =
        {
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
        };
        String[] timeList =
        {
            "8am-10am", "10am-12pm", "12pm-2pm", "2pm-4pm", "4pm-6pm", "6pm-8pm"
        };

        PreparedStatement psSelect = null;
        ResultSet rsSelect = null;
        PreparedStatement psFill = null;
        ResultSet rsFill = null;
        String selectQuery = "Select * FROM WorkSchedule";
        String fillQuery = "";

        try
        {
            psSelect = myDbConn.prepareStatement(selectQuery);
            rsSelect = psSelect.executeQuery();

            int staffIndex = 0;
            for (int column = 0; column < 7; column++)
            {

                for (int row = 0; row < 6; row++)
                {

                    if (staffHours[staffIndex] >= 2)
                    {
                        fillQuery = "UPDATE WorkSchedule SET " + daysList[column] + " = ? WHERE Time = ?";
                        psFill = myDbConn.prepareStatement(fillQuery);
                        psFill.setString(1, staffNames[staffIndex]);
                        psFill.setString(2, timeList[row]);
                        psFill.executeUpdate();
                        System.out.println("Work Scheduled Updated Correctly");
                        staffHours[staffIndex] = staffHours[staffIndex] - 2;

                    } else
                    {
                        boolean noStaffHours = false;
                        int count = 0;

                        for (int i = 0; i < numStaff; i++)
                        {
                            if (staffHours[i] < 2)
                            {
                                count++;
                            }
                        }
                        if (count == numStaff)
                        {
                            noStaffHours = true;
                        }
                        if (!noStaffHours)
                        {
                            // stays in the same row until it finds staff who can fill 
                            row--;
                        }
                    }

                    staffIndex++;
                    if (staffIndex == numStaff)
                    {
                        // loops back to first staff member after goes through all 10 
                        staffIndex = 0;
                    }

                }

            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {

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

        String staffNames[] = new String[numStaff];

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

        int staffHours[] = new int[numStaff];

        String selectStaffHours = "SELECT * FROM Staff";
        try
        {
            psHours = myDbConn.prepareStatement(selectStaffHours);
            rsHours = psHours.executeQuery();

            for (int i = 0; i < numStaff; i++)
            {
                if (rsHours.next())
                {
                    staffHours[i] = Integer.parseInt(rsHours.getString("HoursAWeek"));

                }
            }
        } catch (Exception error)
        {
            new Warning("A function", "Recheck your inputted values");
        }

//        command to recieve input
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
        if (command == createSchedule)
        {
            String[] timeIntervals = new String[6];

            timeIntervals[0] = "8am-10am";
            timeIntervals[1] = "10am-12pm";
            timeIntervals[2] = "12pm-2pm";
            timeIntervals[3] = "2pm-4pm";
            timeIntervals[4] = "4pm-6pm";
            timeIntervals[5] = "6pm-8pm";
            createBlankSchedule(timeIntervals);

            this.dispose();
            new WorkSchedule(dbName, tableName, columnNames);

        }
        if (command == deleteSchedule)
        {
            deleteScheduleInfo();
            this.dispose();
            new WorkSchedule(dbName, tableName, columnNames);

        }
        if (command == fillSchedule)
        {

            fillWorkSchedule(staffHours, staffNames);
            this.dispose();
            new WorkSchedule(dbName, tableName, columnNames);

        }
    }

}
