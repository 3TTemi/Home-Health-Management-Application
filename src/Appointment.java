// package HomeHealthManagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Appointment extends JFrame implements ActionListener
{

    // declaring private variables
    private JPanel titlePanel;
    private JPanel buttonPanel;
    private JPanel mainPanel;
    private JButton closeButton;
    private JLabel frameTitle;

    private JComboBox nameSelect;
    private JPanel scrollBox;
    private JLabel scrollText;

    private JPanel displayBox;
    private JLabel visitQuestion;
    private JTextField visitInput;
    private JPanel userInput;
    private JButton submitButton;
    private JLabel daysTillLabel;
    private JButton homePageButton;

    Connection myDbConn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    String dbName = "HomeHealthCare";
    String tableName = "Patients";
    String[] columnNames
            =
            {
                "Name", "Gender", "Weight", "Height", "Age", "Disability",
                "Calories", "NextAppointment", "Home", "Treatment"
            };

    JavaDatabase dbObj = new JavaDatabase(dbName);

    public Appointment()
    {
        // consturcting frame
        this.setBounds(100, 10, 600, 500);
        this.getContentPane().setBackground(HomePage.BAKGROUND_COLOR);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        myDbConn = dbObj.getDbConn();

        //creating and styling components
        titlePanel = new JPanel();
        buttonPanel = new JPanel();
        mainPanel = new JPanel();
        homePageButton = new JButton("Back To Home Page");
        homePageButton.addActionListener(this);
        closeButton = new JButton("Close the Frame");
        closeButton.addActionListener(this);

        frameTitle = new JLabel("Appointment Frame");
        String[] nameList
                =
                {
                    "Name1", "Name2", "Name3", "Name4"
                };
        nameSelect = new JComboBox(nameList);
        loadPatientNames();
        nameSelect.addActionListener(this);

        scrollBox = new JPanel();
        scrollText = new JLabel("-Select-");
        displayBox = new JPanel();
        visitQuestion = new JLabel("Days Since Last Doctor Visit:");
        visitInput = new JTextField(10);
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        daysTillLabel = new JLabel("Days till next Doctor Visit: XXX");
        userInput = new JPanel();

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

        scrollBox.setLayout(new BoxLayout(scrollBox, BoxLayout.PAGE_AXIS));
        scrollBox.add(scrollText);
        scrollBox.add(Box.createVerticalStrut(10));
        scrollBox.add(nameSelect);
        scrollBox.setBorder(BorderFactory.createLineBorder(Color.black));
        scrollText.setAlignmentX(Component.CENTER_ALIGNMENT);

        userInput.setLayout(new BoxLayout(userInput, BoxLayout.LINE_AXIS));
        userInput.setBackground(HomePage.BAKGROUND_COLOR);
        userInput.add(visitQuestion);
        userInput.add(visitInput);

        displayBox.setBackground(HomePage.BAKGROUND_COLOR);
        displayBox.setLayout(new BoxLayout(displayBox, BoxLayout.PAGE_AXIS));
        displayBox.add(Box.createVerticalStrut(0));
        displayBox.add(userInput);
        displayBox.add(Box.createVerticalStrut(10));
        displayBox.add(submitButton);
        displayBox.add(Box.createVerticalStrut(30));
        displayBox.add(daysTillLabel);

        daysTillLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.setLayout(new GridBagLayout());
        mainPanel.add(scrollBox);
        mainPanel.add(Box.createHorizontalStrut(50));
        mainPanel.add(displayBox);

        // adding variables to frame
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.add(mainPanel, BorderLayout.CENTER);

        this.setVisible(true);

    }

    // load patient names into combo box
    public void loadPatientNames()
    {
        nameSelect = new JComboBox();

        String dbQuery = "SELECT * FROM Patients";
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
        }
    }

    public static void main(String[] args)
    {
        // make object of class
        Appointment appointmentObj = new Appointment();

    }

    public void actionPerformed(ActionEvent e)
    {
        // navigation buttons
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

        // Update / Calculate information in GUI 
        int daysSince;
        String patientDisability = "";
        PreparedStatement psDisability = null;
        ResultSet rsDisability = null;
        int daysTill;
        int daysOriginal;
        PreparedStatement psCount = null;
        ResultSet rsCount = null;
        PreparedStatement psNames = null;
        ResultSet rsNames = null;
        int numPatients = 0;

        String nameSelected = nameSelect.getSelectedItem().toString();

        if (nameSelected.equals("Press Here"))
        {
            daysTillLabel.setText("Days till next Doctor Visit: XXX");
        }
        if (!nameSelected.equals("Press Here"))
        {
            String disablityQuery = "SELECT Disability FROM Patients WHERE Name = " + "'" + nameSelected + "'";
            try
            {
                psDisability = myDbConn.prepareStatement(disablityQuery);
                rsDisability = psDisability.executeQuery();
                if (rsDisability.next())
                {
                    patientDisability = rsDisability.getString(1);
                    System.out.println(patientDisability);
                }
            } catch (Exception err)
            {
                err.printStackTrace();
            }

            String houseQuery = "Select count(*) FROM Patients";
            try
            {
                psCount = myDbConn.prepareStatement(houseQuery);
                rsCount = psCount.executeQuery();
                if (rsCount.next())
                {
                    numPatients = rsCount.getInt(1);
                }
            } catch (Exception err)
            {
                err.printStackTrace();
            }

            String[] patientNames = new String[numPatients];

            String dbQuery = "SELECT * FROM Patients";
            try
            {
                psNames = myDbConn.prepareStatement(dbQuery);
                rsNames = psNames.executeQuery();

                for (int i = 0; i < numPatients; i++)
                {
                    if (rsNames.next())
                    {
                        patientNames[i] = rsNames.getString("Name");
                        System.out.println(patientNames[i]);

                    }
                }
            } catch (Exception err)
            {
                err.printStackTrace();
            }

            // command to recieve input
            if (command == submitButton)
            {
                daysSince = Integer.parseInt(visitInput.getText());
                Patient patientObj = new Patient();
                System.out.println(patientDisability);
                System.out.println(daysSince);
                daysTill = patientObj.calculateDays(patientDisability, daysSince);
                daysOriginal = daysTill;
                System.out.println(daysTill);
                if (daysTill < 0)
                {
                    daysTill = Math.abs(daysTill);
                    daysTillLabel.setText("Days Past Recommended Doctor Visit: " + daysTill);
                } else if (daysTill >= 0)
                {
                    daysTillLabel.setText("Days till next Doctor Visit: " + daysTill);
                }

                try
                {
                    String updateQuery = "UPDATE Patients SET NextAppointment = ? WHERE Name = ?";

                    PreparedStatement psUpdate = myDbConn.prepareStatement(updateQuery);

                    psUpdate.setInt(1, daysOriginal);
                    psUpdate.setString(2, nameSelected);

                    psUpdate.executeUpdate();
                    System.out.println("Home Updated successfully");
                } catch (Exception err)
                {
                    err.printStackTrace();
                }

            }

        }
    }

}
