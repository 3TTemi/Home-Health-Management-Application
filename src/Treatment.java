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

public class Treatment extends JFrame implements ActionListener
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

    private JPanel displayPanel;
    private JLabel medication;
    private JLabel shots;
    private JLabel equipment;
    private JLabel treatment;
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

    public Treatment()
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
        frameTitle = new JLabel("Treatment");

        String[] nameList =
        {
            "Name1", "Name2", "Name3", "Name4"
        };
        nameSelect = new JComboBox(nameList);
        loadPatientNames();
        nameSelect.addActionListener(this);

        scrollBox = new JPanel();
        scrollText = new JLabel("-Select-");
        medication = new JLabel("Patient Medication: XXX");
        shots = new JLabel("Patient Shots: XXX");
        equipment = new JLabel("Patient Equipment: XXX");
        treatment = new JLabel("Treatment: XXX");
        displayPanel = new JPanel();

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

        GridLayout displayLayout = new GridLayout(2, 2);
        displayLayout.setHgap(60);
        displayLayout.setVgap(30);

        displayPanel.setBackground(HomePage.BAKGROUND_COLOR);
        displayPanel.setLayout(displayLayout);
        displayPanel.add(medication);
        displayPanel.add(shots);

        displayPanel.add(equipment);
        displayPanel.add(treatment);

        mainPanel.setLayout(new GridBagLayout());
        mainPanel.add(scrollBox);
        mainPanel.add(Box.createHorizontalStrut(50));
        mainPanel.add(displayPanel);

        // adding variables to frame
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.add(mainPanel, BorderLayout.CENTER);

        this.setVisible(true);

    }
//  load patient names into combo box

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
        Treatment treatmentObj = new Treatment();

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {

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

        String patientDisability = "";
        PreparedStatement psDisability = null;
        ResultSet rsDisability = null;
        String[] treatmentData = new String[4];

        // Update / Calculate information in GUI 
        String nameSelected = nameSelect.getSelectedItem().toString();

        if (nameSelected.equals("Press Here"))
        {
//                daysTillLabel.setText("Days till next Doctor Visit: XXX");
            medication.setText("Patient Medication: XXX");
            shots.setText("Patient Shots: XXX");
            equipment.setText("Patient Equipment: XXX");
            treatment.setText("Treatment: XXX");

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
                }
            } catch (Exception err)
            {
                err.printStackTrace();
            }

            Patient patientObj = new Patient();
            treatmentData = patientObj.calculateTreatment(patientDisability);

            medication.setText("<html>Patient Medication: <br> " + treatmentData[0] + "</html");
            shots.setText("<html>Patient Shots: <br>" + treatmentData[1] + "</html");
            equipment.setText("<html>Patient Equipment: <br> " + treatmentData[2] + "</html");
            treatment.setText("<html>Treatment: <br> " + treatmentData[3] + "</html");

        }

    }
}
