// package HomeHealthManagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import javax.swing.UIManager;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Diet extends JFrame implements ActionListener
{

    // declaring private variables
    private JPanel titlePanel;
    private JPanel buttonPanel;
    private JPanel mainPanel;
    private JButton closeButton;
    private JLabel frameTitle;
    private JComboBox nameSelect;
    private JLabel calorieIntake;
    private JLabel sampleDiet;
    private JPanel displayBox;
    private JPanel scrollBox;
    private JLabel scrollText;
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

    public Diet()
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
        closeButton = new JButton("Close the Frame");
        closeButton.addActionListener(this);
        frameTitle = new JLabel("Diet Frame");
        calorieIntake = new JLabel("Suggested Calorie Intake:XXX");
        sampleDiet = new JLabel("<html> Sample Diet: <ul>" + "<li> XXX </li>" + "<li> XXX </li>" + "<li> XXX </li>" + "<li> XXX </li>" + "</ul><html>");
        displayBox = new JPanel();
        scrollBox = new JPanel();
        scrollText = new JLabel("-Select-");

        String[] nameList =
        {
            "Name1", "Name2", "Name3", "Name4"
        };

        nameSelect = new JComboBox(nameList);
        loadPatientNames();
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
        displayBox.add(calorieIntake);
        displayBox.add(Box.createVerticalStrut(20));
        displayBox.add(sampleDiet);

        scrollBox.setLayout(new BoxLayout(scrollBox, BoxLayout.PAGE_AXIS));
        scrollBox.add(scrollText);
        scrollBox.add(Box.createVerticalStrut(10));
        scrollBox.add(nameSelect);
        scrollBox.setBorder(BorderFactory.createLineBorder(Color.black));

        scrollText.setAlignmentX(Component.CENTER_ALIGNMENT);

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
        Diet dietObj = new Diet();

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {

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

        // Update / Calculate information in GUI 
        String nameSelected = nameSelect.getSelectedItem().toString();

        int selectedAge = 0;
        String selectedGender = null;
        PreparedStatement psAge = null;
        ResultSet rsAge = null;
        PreparedStatement psGender = null;
        ResultSet rsGender = null;
        int numCalories;
        String[] diet = new String[3];

        if (nameSelected.equals("Press Here"))
        {
            calorieIntake.setText("Suggested Calorie Intake:XXX");
            sampleDiet.setText("<html> Sample Diet: <ul>" + "<li> XXX </li>" + "<li> XXX </li>" + "<li> XXX </li>" + "<li> XXX </li>" + "</ul><html>");
        }
        if (!nameSelected.equals("Press Here"))
        {
            String ageQuery = "SELECT Age FROM Patients WHERE Name = " + "'" + nameSelected + "'";
            String genderQuery = "SELECT Gender FROM Patients WHERE Name = " + "'" + nameSelected + "'";

            try
            {
                psAge = myDbConn.prepareStatement(ageQuery);
                rsAge = psAge.executeQuery();
                psGender = myDbConn.prepareStatement(genderQuery);
                rsGender = psGender.executeQuery();

                if (rsAge.next())
                {
                    selectedAge = Integer.parseInt(rsAge.getString(1));
                }
                if (rsGender.next())
                {
                    selectedGender = rsGender.getString(1);
                }
            } catch (Exception err)
            {
                err.printStackTrace();
            }

            Patient patientObj = new Patient();
            numCalories = patientObj.calculateCalorie(selectedAge, selectedGender);
            diet = patientObj.calculateDiet(numCalories);

            calorieIntake.setText("Suggested Calorie Intake: " + numCalories);
            sampleDiet.setText("<html> Sample Diet: <ul>" + "<li>" + diet[0] + "</li>" + "<li>" + diet[1] + "</li>" + "<li>" + diet[2] + "</li>" + "</ul><html>");

        }

    }
}
