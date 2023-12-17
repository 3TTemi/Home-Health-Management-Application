// package HomeHealthManagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StaffFrame extends JFrame implements ActionListener
{
    // declaring private variables

    private JPanel addBox;
    private JPanel titlePanel;
    private JPanel exitFrame;
    private JPanel mainPanel;
    private JButton closeButton;
    private JLabel frameTitle;
    private JPanel navigateStaff;

    private JLabel nameLabel;
    private JLabel specialtyLabel;
    private JLabel hoursLabel;
    private JLabel wageLabel;

    private JTextField nameField;
    private JTextField specialtyField;
    private JTextField hoursField;
    private JTextField wageField;

    private JButton addButton;
    private JButton workScheduleButton;
    private JButton paymentButton;
    private JButton homePageButton;
    private JButton databaseButton;

    private JPanel addInputs;

    private JPanel deletePanel;
    private JLabel deleteWhereText;
    private JTextField deleteWhereField;
    private JButton deleteButton;
    private JPanel deleteBox;

    private JPanel updatePanel;
    private JLabel updateText;
    private JTextField updateField;
    private JLabel toText;
    private JTextField toField;
    private JLabel updateWhereText;
    private JTextField updateWhereField;
    private JButton updateButton;
    private JPanel updateBox;

    Connection myDbConn = null;

    String dbName = "HomeHealthCare";

    JavaDatabase dbObj = new JavaDatabase(dbName);

    public StaffFrame()
    {
        // consturcting frame

        this.setBounds(100, 100, 650, 500);
        this.getContentPane().setBackground(HomePage.BAKGROUND_COLOR);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        myDbConn = dbObj.getDbConn();

        //creating and styling components
        titlePanel = new JPanel();
        exitFrame = new JPanel();
        mainPanel = new JPanel();

        addBox = new JPanel();
        closeButton = new JButton("Close the Frame");
        closeButton.addActionListener(this);
        homePageButton = new JButton("Back To Home Page");
        homePageButton.addActionListener(this);
        frameTitle = new JLabel("Staff");
        nameLabel = new JLabel("Name: ");
        specialtyLabel = new JLabel("Specialty: ");
        hoursLabel = new JLabel("Hours a week: ");
        wageLabel = new JLabel("Wage: ");
        addButton = new JButton("Add Staff");
        addButton.addActionListener(this);
        workScheduleButton = new JButton("Work Schedule");
        workScheduleButton.addActionListener(this);
        paymentButton = new JButton("Payments");
        paymentButton.addActionListener(this);
        databaseButton = new JButton("Database");
        databaseButton.addActionListener(this);
        navigateStaff = new JPanel();
        nameField = new JTextField(9);
        nameField.setBorder(BorderFactory.createLineBorder(Color.black));
        specialtyField = new JTextField(9);
        specialtyField.setBorder(BorderFactory.createLineBorder(Color.black));
        hoursField = new JTextField(9);
        hoursField.setBorder(BorderFactory.createLineBorder(Color.black));
        wageField = new JTextField(9);
        wageField.setBorder(BorderFactory.createLineBorder(Color.black));

        deletePanel = new JPanel();
        deleteWhereText = new JLabel("Delete where StaffID = ");
        deleteWhereField = new JTextField(9);
        deleteWhereField.setBorder(BorderFactory.createLineBorder(Color.black));
        deleteButton = new JButton("Delete Staff");
        deleteButton.addActionListener(this);
        deleteBox = new JPanel();

        updatePanel = new JPanel();
        updateText = new JLabel("Update column");
        updateField = new JTextField(5);
        updateField.setBorder(BorderFactory.createLineBorder(Color.black));
        toText = new JLabel("to value");
        toField = new JTextField(5);
        toField.setBorder(BorderFactory.createLineBorder(Color.black));
        updateWhereText = new JLabel("where StaffID = ");
        updateWhereField = new JTextField(10);
        updateWhereField.setBorder(BorderFactory.createLineBorder(Color.black));
        updateButton = new JButton("Update Staff");
        updateButton.addActionListener(this);
        updateBox = new JPanel();

        deletePanel.add(deleteWhereText);
        deletePanel.add(deleteWhereField);

        deleteBox.setLayout(new BoxLayout(deleteBox, BoxLayout.PAGE_AXIS));
        deleteBox.add(deletePanel);
        deleteBox.add(deleteButton);
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteButton.addActionListener(this);
        deleteBox.setBackground(HomePage.BAKGROUND_COLOR);
        deletePanel.setBackground(HomePage.BAKGROUND_COLOR);

        updateBox.setLayout(new BoxLayout(updateBox, BoxLayout.PAGE_AXIS));
        updatePanel.add(updateText);
        updatePanel.add(updateField);
        updatePanel.add(toText);
        updatePanel.add(toField);
        updatePanel.add(updateWhereText);
        updatePanel.add(updateWhereField);
        updateBox.add(updatePanel);
        updateBox.add(updateButton);
        updateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        updatePanel.setBackground(HomePage.BAKGROUND_COLOR);
        updateBox.setBackground(HomePage.BAKGROUND_COLOR);

        navigateStaff.add(workScheduleButton);
        navigateStaff.add(paymentButton);
        navigateStaff.add(databaseButton);
        navigateStaff.setBackground(HomePage.BAKGROUND_COLOR);
        titlePanel.add(frameTitle);
        exitFrame.add(closeButton);
        exitFrame.add(Box.createHorizontalStrut(30));
        exitFrame.add(homePageButton);

        titlePanel.setLayout(new GridBagLayout());
        exitFrame.setLayout(new GridBagLayout());

        titlePanel.setPreferredSize(new Dimension(500, 50));
        exitFrame.setPreferredSize(new Dimension(500, 50));
        titlePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        exitFrame.setBorder(BorderFactory.createLineBorder(Color.black));

        addInputs = new JPanel();
        GridLayout inputLayout = new GridLayout(2, 2);
        inputLayout.setHgap(5);
        inputLayout.setVgap(40);
        addInputs.setLayout(inputLayout);

        addInputs.setPreferredSize(new Dimension(600, 100));
        addInputs.setBackground(HomePage.BAKGROUND_COLOR);

        mainPanel.setBackground(HomePage.BAKGROUND_COLOR);

        addBox.setLayout(new BoxLayout(addBox, BoxLayout.PAGE_AXIS));

        addBox.add(addInputs);
        addBox.add(Box.createVerticalStrut(10));
        addBox.add(addButton);
        addBox.setBackground(HomePage.BAKGROUND_COLOR);
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        addInputs.add(nameLabel);
        addInputs.add(nameField);
        addInputs.add(specialtyLabel);
        addInputs.add(specialtyField);
        addInputs.add(hoursLabel);
        addInputs.add(hoursField);
        addInputs.add(wageLabel);
        addInputs.add(wageField);

        mainPanel.add(addBox);
        mainPanel.add(deleteBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 100)));

        mainPanel.add(updateBox);
        mainPanel.add(Box.createVerticalStrut(70));
        mainPanel.add(navigateStaff);

        PreparedStatement psAlter = null;
        ResultSet rsAlter = null;

        // adding variables to frame
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(exitFrame, BorderLayout.SOUTH);
        this.add(mainPanel, BorderLayout.CENTER);

        this.setVisible(true);

    }

    public static void main(String[] args)
    {
        // make object of class

        StaffFrame staffObj = new StaffFrame();

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {

        String dbName = "HomeHealthCare";
        String tableName = "Staff";
        String[] columnNames
                =
                {
                    "StaffID", "Name", "Specialty", "Wage", "HoursAWeek"
                };

        JavaDatabase dbObj = new JavaDatabase(dbName);
        Connection myDbConn = dbObj.getDbConn();

        int staffID;
        String name;
        String specialty;
        int wage;
        int hoursAWeek;
        int numPayments;
        int staffCounter = 0;
        String columnBefore;
        String column;
        String toValue;

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
        // if added button pressed
        if (command == addButton)
        {
            // add user inputted values and update the rest of columns
            try
            {
                String dbQuery = "INSERT INTO Staff (Name,Specialty,Wage,HoursAWeek) VALUES (?,?,?,?)";

                name = nameField.getText();
                specialty = specialtyField.getText();
                wage = Integer.parseInt(wageField.getText());
                hoursAWeek = Integer.parseInt(hoursField.getText());
                nameField.setText("");
                specialtyField.setText("");
                wageField.setText("");
                hoursField.setText("");

                PreparedStatement ps = myDbConn.prepareStatement(dbQuery);

                ps.setString(1, name);
                ps.setString(2, specialty);
                ps.setInt(3, wage);
                ps.setInt(4, hoursAWeek);

                ps.executeUpdate();
                System.out.println("Data inserted successfully");

            } catch (Exception err)
            {
                new Warning("A function", "Recheck your inputted values");
            }
        }
        // if delete key pressed
        if (command == deleteButton)
        {
            try
            {
                String dbQuery = "DELETE FROM Staff WHERE StaffID = ?";

                staffID = Integer.parseInt(deleteWhereField.getText());

                PreparedStatement ps = myDbConn.prepareStatement(dbQuery);

                ps.setInt(1, staffID);

                ps.executeUpdate();
                System.out.println("Data deleted successfully");

            } catch (Exception err)
            {
                new Warning("A function", "Recheck your inputted values");

            }

        }
        // if update button pressed
        if (command == updateButton)
        {
            // update user inputted values and update the rest of columns
            try
            {
                String dbQuery = "UPDATE Staff SET ";

                column = updateField.getText();
                toValue = toField.getText();
                staffID = Integer.parseInt(updateWhereField.getText());
                dbQuery = dbQuery + column + " = ? WHERE StaffID = ?";
                System.out.println(dbQuery);
                PreparedStatement ps = myDbConn.prepareStatement(dbQuery);
                if (column.equals("Specialty") || column.equals("Name"))
                {
                    ps.setString(1, toValue);
                }
                if (column.equals("Wage") || column.equals("HoursAWeek"))
                {
                    ps.setInt(1, Integer.parseInt(toValue));
                }
                ps.setInt(2, staffID);

                ps.executeUpdate();
                System.out.println("Data Updated successfully");

            } catch (Exception err)
            {

                new Warning("A function", "Recheck your inputted values");

            }
        }

        if (command == workScheduleButton)
        {
            String dbNameSchedule = "HomeHealthCare";
            String tableNameSchedule = "WorkSchedule";
            String[] columnNamesShedule
                    =
                    {
                        "Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
                    };
            this.dispose();
            new WorkSchedule(dbNameSchedule, tableNameSchedule, columnNamesShedule);
        }
        if (command == paymentButton)
        {
            this.dispose();
            new Payment();
        }
        if (command == databaseButton)
        {
            this.dispose();
            new StaffDatabase(dbName, tableName, columnNames);
        }
    }

}
