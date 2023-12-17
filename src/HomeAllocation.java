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
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class HomeAllocation extends JFrame implements ActionListener
{

    // declaring private variables
    private JPanel titlePanel;
    private JPanel buttonPanel;
    private JPanel mainPanel;
    private JButton closeButton;
    private JLabel frameTitle;
    private JLabel numLabel;
    private JLabel occupancyLabel;
    private JTextField numTextField;
    private JTextField occupancyTextField;
    private JButton submitButton;
    private JPanel inputPanel;
    private JPanel displayBox;
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

    public HomeAllocation()
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
        closeButton = new JButton("Close the Frame");
        closeButton.addActionListener(this);
        homePageButton = new JButton("Back To Home Page");
        homePageButton.addActionListener(this);
        frameTitle = new JLabel("Home Allocation ");
        numLabel = new JLabel("Number of Houses: ");
        occupancyLabel = new JLabel("House Occupancy: ");
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        inputPanel = new JPanel();
        displayBox = new JPanel();
        numTextField = new JTextField(10);
        occupancyTextField = new JTextField(10);

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
        inputPanel.setBackground(HomePage.BAKGROUND_COLOR);

        inputPanel.add(numLabel);
        inputPanel.add(numTextField);
        inputPanel.add(Box.createHorizontalStrut(10));
        inputPanel.add(occupancyLabel);
        inputPanel.add(occupancyTextField);

        displayBox.setBackground(HomePage.BAKGROUND_COLOR);
        displayBox.setLayout(new BoxLayout(displayBox, BoxLayout.PAGE_AXIS));
        displayBox.add(inputPanel);
        displayBox.add(Box.createVerticalStrut(10));
        displayBox.add(submitButton);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.setLayout(new GridBagLayout());
        mainPanel.add(displayBox);

        // adding variables to frame
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.add(mainPanel, BorderLayout.CENTER);

        this.setVisible(true);

    }

    public static void main(String[] args)
    {
        // make object of class
        HomeAllocation homeObj = new HomeAllocation();

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        // Update / Calculate information in GUI 
        int numHouses = 0;
        int houseOccupancy = 0;
        int numPatients = 0;
        PreparedStatement psCount = null;
        ResultSet rsCount = null;
        PreparedStatement psNames = null;
        ResultSet rsNames = null;

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

        } catch (Exception error)
        {
            new Warning("A function", "Recheck your inputted values");
        }

        // command to recieve input
        Object command = e.getSource();

        // if buttons pressed do the subsequent action
        if (command == closeButton)
        {
            this.dispose();
        }
        if (command == homePageButton)
        {
            new HomePage();

            this.dispose();
        }
        if (command == submitButton)
        {
            numHouses = Integer.parseInt(numTextField.getText());
            houseOccupancy = Integer.parseInt(occupancyTextField.getText());
            Patient patientObj = new Patient();
            ArrayList<String> patientHouses = patientObj.distributeHouse(numHouses, houseOccupancy, numPatients);

            for (int i = 0; i < numPatients; i++)
            {
                System.out.println(patientNames[i]);

                try
                {
                    String updateQuery = "UPDATE Patients SET Home = ? WHERE Name = ?";

                    PreparedStatement psUpdate = myDbConn.prepareStatement(updateQuery);

                    psUpdate.setString(1, patientHouses.get(i));
                    psUpdate.setString(2, patientNames[i]);

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
