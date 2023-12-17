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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class PatientEvaluation extends JFrame implements ActionListener
{

    // declaring private variables
    private JPanel titlePanel;
    private JPanel buttonPanel;
    private JPanel mainPanel;
    private JButton closeButton;
    private JButton homePageButton;
    private JLabel frameTitle;

    private JComboBox nameSelect;
    private JPanel scrollBox;
    private JLabel scrollText;

    private JLabel patientChange;
    private JLabel similarities;
    private JPanel displayPanel;

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

    public PatientEvaluation()
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
        frameTitle = new JLabel("Patient Evaluation Frame");
        String[] nameList =
        {
            "Name1", "Name2", "Name3", "Name4"
        };
        nameSelect = new JComboBox(nameList);
        loadPatientNames();
        nameSelect.addActionListener(this);

        scrollBox = new JPanel();
        scrollText = new JLabel("-Select-");
        patientChange = new JLabel("<html> Patient Changes: <ul>" + "<li> Since: </li>" + "<ul> <li>Change1</li> <li>Change2</li> </ul>" + "</ul><html>");
        similarities = new JLabel("<html> Examiantion: <ul>" + "<li> Patient Criteria: </li>" + "<ul> <li>XXX</li> </ul>" + "<ul> <li>XXX</li> </ul>" + "</ul><html>");
        displayPanel = new JPanel();
        homePageButton = new JButton("Back To Home Page");
        homePageButton.addActionListener(this);

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

        displayPanel.setBackground(HomePage.BAKGROUND_COLOR);
        displayPanel.add(similarities);

        scrollBox.setLayout(new BoxLayout(scrollBox, BoxLayout.PAGE_AXIS));
        scrollBox.add(scrollText);
        scrollBox.add(Box.createVerticalStrut(10));
        scrollBox.add(nameSelect);
        scrollBox.setBorder(BorderFactory.createLineBorder(Color.black));
        scrollText.setAlignmentX(Component.CENTER_ALIGNMENT);

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
            // make object of class
            new Warning("A function", "Recheck your inputted values");
        }
    }

    public static void main(String[] args)
    {
        PatientEvaluation evalObj = new PatientEvaluation();

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        // navigation buttons
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

        // Update / Calculate information in GUI 
        String nameSelected = nameSelect.getSelectedItem().toString();

        if (nameSelected.equals("Press Here"))
        {
            similarities.setText("<html> Examiantion: <ul>" + "<li> Patient Criteria: </li>" + "<ul> <li>XXX</li> </ul>" + "<ul> <li>XXX</li> </ul>" + "</ul><html>");
        }
        if (!nameSelected.equals("Press Here"))
        {

            ArrayList<Integer> patientCaloriesList = new ArrayList<Integer>();

            PreparedStatement psCalories = null;
            ResultSet rsCalories = null;
            double listSum = 0;
            int listAverage;

            String caloriesQuery = "SELECT * FROM Patients";
            try
            {
                psCalories = myDbConn.prepareStatement(caloriesQuery);
                rsCalories = psCalories.executeQuery();
                while (rsCalories.next())
                {
                    patientCaloriesList.add(Integer.parseInt(rsCalories.getString("Calories")));
                }

            } catch (Exception err)
            {
                // make object of class
                new Warning("A function", "Recheck your inputted values");
            }

            for (int i = 0; i < patientCaloriesList.size(); i++)
            {
                listSum = listSum + patientCaloriesList.get(i);
            }

            listAverage = (int) listSum / patientCaloriesList.size();  
            
            int currentCalorie = 0;
            String selectCalorieQuery = "SELECT Calories FROM Patients WHERE Name = " + "'" + nameSelected + "'";
            PreparedStatement psSelect = null;
            ResultSet rsSelect = null;
            try
            {
                psSelect = myDbConn.prepareStatement(selectCalorieQuery);
                rsSelect = psSelect.executeQuery();
                if (rsSelect.next())
                {
                    currentCalorie = Integer.parseInt(rsSelect.getString(1));
                }
            } catch (Exception err)
            {
                err.printStackTrace();
            }

            int daysAppointment = 0;
            String selectAppointmentQuery = "SELECT NextAppointment FROM Patients WHERE Name = " + "'" + nameSelected + "'";
            psSelect = null;
            rsSelect = null;

            try
            {
                psSelect = myDbConn.prepareStatement(selectAppointmentQuery);
                rsSelect = psSelect.executeQuery();

                if (rsSelect.next())
                {
                    daysAppointment = Integer.parseInt(rsSelect.getString(1));
                }
            } catch (Exception err)
            {
                err.printStackTrace();
            }

            if (currentCalorie > listAverage && daysAppointment < 0)
            {
                similarities.setText("<html> Examiantion: <ul>" + "<li> Patient Criteria: </li>"
                        + "<ul> <li>Patient has higher comparitive calories</li> </ul>" + "<ul> <li>Doctors Appointment Past Due</li> </ul>" + "</ul><html>");
            } else if (currentCalorie == listAverage && daysAppointment < 0)
            {
                similarities.setText("<html> Examiantion: <ul>" + "<li> Patient Criteria: </li>"
                        + "<ul> <li>Patient has average comparitive calories</li> </ul>" + "<ul> <li>Doctors Appointment Past Due</li> </ul>" + "</ul><html>");
            } else if (currentCalorie < listAverage && daysAppointment < 0)
            {
                similarities.setText("<html> Examiantion: <ul>" + "<li> Patient Criteria: </li>"
                        + "<ul> <li>Patient has lower comparitive calories</li> </ul>" + "<ul> <li>Doctors Appointment Past Due</li> </ul>" + "</ul><html>");
            } else if (currentCalorie > listAverage && daysAppointment > 0)
            {
                similarities.setText("<html> Examiantion: <ul>" + "<li> Patient Criteria: </li>"
                        + "<ul> <li>Patient has higher comparitive calories</li> </ul>" + "<ul> <li>Doctors Appointment is Coming up</li> </ul>" + "</ul><html>");

            } else if (currentCalorie == listAverage && daysAppointment > 0)
            {
                similarities.setText("<html> Examiantion: <ul>" + "<li> Patient Criteria: </li>"
                        + "<ul> <li>Patient has average comparitive calories</li> </ul>" + "<ul> <li>Doctors Appointment is Coming up</li> </ul>" + "</ul><html>");

            } else if (currentCalorie < listAverage && daysAppointment > 0)
            {
                similarities.setText("<html> Examiantion: <ul>" + "<li> Patient Criteria: </li>"
                        + "<ul> <li>Patient has lower comparitive calories</li> </ul>" + "<ul> <li>Doctors Appointment is Coming up</li> </ul>" + "</ul><html>");

            } else
            {
                similarities.setText("<html> Examiantion: <ul>" + "<li> Patient Criteria: </li>"
                        + "<ul> <li>No Calorie Information Available</li> </ul>" + "<ul> <li>XXX</li> </ul>" + "</ul><html>");

            }

        }

    }
}
