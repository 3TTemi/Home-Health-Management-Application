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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class PatientFrame extends JFrame implements ActionListener
{
    // declaring private variables

    private JPanel addBox;
    private JPanel titlePanel;
    private JPanel exitFrame;
    private JPanel mainPanel;
    private JButton closeButton;
    private JLabel frameTitle;
    private JPanel navigatePatient;

    private JLabel nameLabel;
    private JLabel weightLabel;
    private JLabel ageLabel;
    private JLabel genderLabel;
    private JLabel heightLabel;
    private JLabel disabilityLabel;

    private JTextField nameField;
    private JTextField weightField;
    private JTextField ageField;
    private JTextField genderField;
    private JTextField heightField;
    private JTextField disabilityField;

    private JButton addButton;
    private JButton dietButton;
    private JButton appointmentButton;
    private JButton homeButton;
    private JButton treatmentButton;
    private JButton databaseButton;
    private JButton homePageButton;

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

    public PatientFrame()
    {
        // consturcting frame
        this.setBounds(100, 100, 650, 500);
        this.getContentPane().setBackground(HomePage.BAKGROUND_COLOR);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //creating and styling components
        titlePanel = new JPanel();
        exitFrame = new JPanel();
        mainPanel = new JPanel();

        addBox = new JPanel();
        closeButton = new JButton("Close the Frame");
        closeButton.addActionListener(this);
        homePageButton = new JButton("Back To Home Page");
        homePageButton.addActionListener(this);
        frameTitle = new JLabel("Patient");
        nameLabel = new JLabel("Name: ");
        weightLabel = new JLabel("Weight: ");
        ageLabel = new JLabel("Age: ");
        genderLabel = new JLabel("Gender: ");
        heightLabel = new JLabel("Height: ");
        disabilityLabel = new JLabel("Disabilities: ");
        addButton = new JButton("Add Patient");
        addButton.addActionListener(this);
        dietButton = new JButton("Diet");
        dietButton.addActionListener(this);
        appointmentButton = new JButton("Appointment");
        appointmentButton.addActionListener(this);
        homeButton = new JButton("Home Allocation");
        homeButton.addActionListener(this);
        treatmentButton = new JButton("Treatment");
        treatmentButton.addActionListener(this);
        databaseButton = new JButton("Database");
        databaseButton.addActionListener(this);

        deletePanel = new JPanel();
        deleteWhereText = new JLabel("Delete where PatientID = ");
        deleteWhereField = new JTextField(9);
        deleteWhereField.setBorder(BorderFactory.createLineBorder(Color.black));
        deleteButton = new JButton("Delete Patient");
        deleteButton.addActionListener(this);
        deleteBox = new JPanel();

        updatePanel = new JPanel();
        updateText = new JLabel("Update column");
        updateField = new JTextField(5);
        updateField.setBorder(BorderFactory.createLineBorder(Color.black));
        toText = new JLabel("to value");
        toField = new JTextField(5);
        toField.setBorder(BorderFactory.createLineBorder(Color.black));
        updateWhereText = new JLabel("where PatientID = ");
        updateWhereField = new JTextField(10);
        updateWhereField.setBorder(BorderFactory.createLineBorder(Color.black));
        updateButton = new JButton("Update Patient");
        updateButton.addActionListener(this);
        updateBox = new JPanel();

        navigatePatient = new JPanel();
        nameField = new JTextField(9);
        nameField.setBorder(BorderFactory.createLineBorder(Color.black));
        weightField = new JTextField(9);
        weightField.setBorder(BorderFactory.createLineBorder(Color.black));
        ageField = new JTextField(9);
        ageField.setBorder(BorderFactory.createLineBorder(Color.black));
        genderField = new JTextField(9);
        genderField.setBorder(BorderFactory.createLineBorder(Color.black));
        heightField = new JTextField(9);
        heightField.setBorder(BorderFactory.createLineBorder(Color.black));
        disabilityField = new JTextField(9);
        disabilityField.setBorder(BorderFactory.createLineBorder(Color.black));

        deletePanel.add(deleteWhereText);
        deletePanel.add(deleteWhereField);

        deleteBox.setLayout(new BoxLayout(deleteBox, BoxLayout.PAGE_AXIS));
        deleteBox.add(deletePanel);
        deleteBox.add(deleteButton);
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
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

        navigatePatient.add(dietButton);
        navigatePatient.add(appointmentButton);
        navigatePatient.add(homeButton);
        navigatePatient.add(treatmentButton);
        navigatePatient.add(databaseButton);
        navigatePatient.setBackground(HomePage.BAKGROUND_COLOR);
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
        GridLayout inputLayout = new GridLayout(2, 3);
        inputLayout.setHgap(20);
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
        addInputs.add(weightLabel);
        addInputs.add(weightField);
        addInputs.add(ageLabel);
        addInputs.add(ageField);
        addInputs.add(genderLabel);
        addInputs.add(genderField);
        addInputs.add(heightLabel);
        addInputs.add(heightField);
        addInputs.add(disabilityLabel);
        addInputs.add(disabilityField);

        mainPanel.add(addBox);
        mainPanel.add(deleteBox);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 100)));

        mainPanel.add(updateBox);
        mainPanel.add(Box.createVerticalStrut(70));
        mainPanel.add(navigatePatient);

        // adding variables to frame
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(exitFrame, BorderLayout.SOUTH);
        this.add(mainPanel, BorderLayout.CENTER);

        this.setVisible(true);

    }

    public static void main(String[] args)
    {
        // make object of class

        PatientFrame patientObj = new PatientFrame();

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {

        String dbName = "HomeHealthCare";
        String tableName = "Patients";
        String[] columnNames
                =
                {
                    "Name", "Gender", "Weight", "Height", "Age", "Disability",
                    "Calories", "NextAppointment", "Home", "Treatment"
                };

        JavaDatabase dbObj = new JavaDatabase(dbName);
        Connection myDbConn = dbObj.getDbConn();

        int patientID;
        String name;
        String gender;
        int weight;
        int height;
        int age;
        String disability;
        int calories;
        int daysTill;
        String home;
        String treatment;
        int patientCounter = 0;
        String column;
        String toValue;
        String columnBefore;
        PreparedStatement psSelect = null;
        ResultSet rsSelect = null;

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
        if (command == addButton)
        {
            try
            {

                String dbQuery = "INSERT INTO Patients (Name,Gender,Weight,Height,Age,Disability,Calories,NextAppointment,Home,Treatment) "
                        + "VALUES (?,?,?,?,?,?,?,?,?,?)";

                name = nameField.getText();
                gender = genderField.getText();
                weight = Integer.parseInt(weightField.getText());
                height = Integer.parseInt(heightField.getText());
                age = Integer.parseInt(ageField.getText());
                disability = disabilityField.getText();
                if (disability.equalsIgnoreCase("Language") || disability.equalsIgnoreCase("Mobility") || disability.equalsIgnoreCase("Learning") 
                        || disability.equalsIgnoreCase("Sensory") || disability.equalsIgnoreCase("Memory"))
                {

                    PreparedStatement ps = myDbConn.prepareStatement(dbQuery);

                    ps.setString(1, name);
                    ps.setString(2, gender);
                    ps.setInt(3, weight);
                    ps.setInt(4, height);
                    ps.setInt(5, age);
                    ps.setString(6, disability);
                    ps.setInt(7, 0);
                    ps.setInt(8, 0);
                    ps.setString(9, "-");
                    ps.setString(10, "-");

                    ps.executeUpdate();
                    System.out.println("Data inserted successfully");

                } else
                {
                    new Warning("Inserting Disability Information", "Inserting Disability as Language, Mobility, Leanring, Sensory, or Memory");
                }

            } catch (Exception err)
            {
                new Warning("Inserting Patient Information", "Checking values inputted into Patient Frame");
//        System.out.println("Bad things happened");
            }

            int patientAge;
            String patientGender;
            int calorieCount;
            String patientName = "";
            int daysAppointment;
            String patientDisability;

            Patient patientObj = new Patient();

            patientName = nameField.getText();
            patientAge = Integer.parseInt(ageField.getText());
            patientGender = genderField.getText();
            calorieCount = patientObj.calculateCalorie(patientAge, patientGender);

            try
            {
                String updateQuery = "UPDATE Patients SET Calories = ? WHERE Name = ?";

                PreparedStatement psUpdate = myDbConn.prepareStatement(updateQuery);

                psUpdate.setInt(1, calorieCount);
                psUpdate.setString(2, patientName);

                psUpdate.executeUpdate();
                System.out.println("Patient Updated successfully");
            } catch (Exception err)
            {
                err.printStackTrace();
            }

            patientDisability = disabilityField.getText();

            patientName = nameField.getText();
            String patientTreatment[] = new String[4];
            patientTreatment = patientObj.calculateTreatment(patientDisability);

            try
            {
                String updateQuery = "UPDATE Patients SET Treatment = ? WHERE Name = ?";

                PreparedStatement psUpdate = myDbConn.prepareStatement(updateQuery);

                psUpdate.setString(1, patientTreatment[3]);
                psUpdate.setString(2, patientName);

                psUpdate.executeUpdate();
                System.out.println("Patient Updated successfully");
            } catch (Exception err)
            {
                err.printStackTrace();
            }
        }

        if (command == deleteButton)
        {

            try
            {
                String dbQuery = "DELETE FROM Patients WHERE PatientID = ?";

                patientID = Integer.parseInt(deleteWhereField.getText());
                deleteWhereField.setText("");

                PreparedStatement ps = myDbConn.prepareStatement(dbQuery);

                ps.setInt(1, patientID);

                ps.executeUpdate();
                System.out.println("Data deleted successfully");

            } catch (Exception err)
            {
                new Warning("Deleting Patient Information", "Checking values inputted into Patient Frame");

            }

        }
        if (command == updateButton)
        {
            try
            {
                String dbQuery = "UPDATE Patients SET ";

                columnBefore = updateField.getText().toLowerCase();
                column = columnBefore.substring(0, 1).toUpperCase() + columnBefore.substring(1);
                toValue = toField.getText();
                patientID = Integer.parseInt(updateWhereField.getText());
                dbQuery = dbQuery + column + " = ? WHERE PatientID = ?";
                System.out.println(dbQuery);
                PreparedStatement ps = myDbConn.prepareStatement(dbQuery);
                System.out.println(column);
                if (column.equals("Name") || column.equals("Gender") || column.equals("Disability"))
                {
                    ps.setString(1, toValue);
                }
                if (column.equals("Weight") || column.equals("Age") || column.equals("Height"))
                {
                    ps.setInt(1, Integer.parseInt(toValue));
                }
                ps.setInt(2, patientID);

                ps.executeUpdate();
                System.out.println("Data Updated successfully");

            } catch (Exception err)
            {

                new Warning("Updating Patient Information", "Checking values inputted into Patient Frame");
                err.printStackTrace();
            }

            patientID = Integer.parseInt(updateWhereField.getText());
            int newAge = 0;

            String selectAgeQuery = "SELECT Age FROM Patients WHERE PatientID = " + patientID ;
            System.out.println(selectAgeQuery);

            try
            {

                psSelect = myDbConn.prepareStatement(selectAgeQuery);
                rsSelect = psSelect.executeQuery();

                if (rsSelect.next())
                {
                    newAge = Integer.parseInt(rsSelect.getString(1));
//                    purchasePrice = userDate.replace("-", "");

                    System.out.println("Here is " + newAge);

                }
            } catch (Exception err)
            {
                err.printStackTrace();
            }

            patientID = Integer.parseInt(updateWhereField.getText());
            String newGender = "";
            int currentCalorie;
            String selectGenderQuery = "SELECT Gender FROM Patients WHERE PatientID = " + patientID ;
            System.out.println(selectGenderQuery);

            try
            {

                psSelect = myDbConn.prepareStatement(selectGenderQuery);
                rsSelect = psSelect.executeQuery();

                if (rsSelect.next())
                {
                    newGender = rsSelect.getString(1);
//                    purchasePrice = userDate.replace("-", "");

                    System.out.println("Here is " + newGender);

                }
            } catch (Exception err)
            {
                err.printStackTrace();
            }

            Patient patientObj = new Patient();

            currentCalorie = patientObj.calculateCalorie(newAge, newGender);

            try
            {
                String updateQuery = "UPDATE Patients SET Calories = ? WHERE PatientID = ?";

                PreparedStatement psUpdate = myDbConn.prepareStatement(updateQuery);

                
                psUpdate.setInt(1, currentCalorie);
                psUpdate.setInt(2, patientID);

                psUpdate.executeUpdate();
                System.out.println("Home Updated successfully");
            } catch (Exception err)
            {
                err.printStackTrace();
            }

            patientID = Integer.parseInt(updateWhereField.getText());
            String patientDisability = "";
            String selectDisabilityQuery = "SELECT Disability FROM Patients WHERE PatientID = " + patientID;
            System.out.println(selectDisabilityQuery);

            try
            {

                psSelect = myDbConn.prepareStatement(selectDisabilityQuery);
                rsSelect = psSelect.executeQuery();

                if (rsSelect.next())
                {
                    patientDisability = rsSelect.getString(1);
//                    purchasePrice = userDate.replace("-", "");

                    System.out.println("Here is " + patientDisability);

                }
            } catch (Exception err)
            {
                err.printStackTrace();
            }

            patientID = Integer.parseInt(updateWhereField.getText());

            String patientTreatment[] = new String[4];

            patientTreatment = patientObj.calculateTreatment(patientDisability);

            try
            {
                String updateQuery = "UPDATE Patients SET Treatment = ? WHERE PatientID = " + patientID;

                PreparedStatement psUpdate = myDbConn.prepareStatement(updateQuery);

                psUpdate.setString(1, patientTreatment[3]);
//                psUpdate.setInt(2, patientID);

                psUpdate.executeUpdate();
                System.out.println("Patient Updated successfully");

            } catch (Exception err)
            {
                err.printStackTrace();
            }
        }

        if (command == dietButton)
        {
            this.dispose();
            new Diet();
        }
        if (command == appointmentButton)
        {
            this.dispose();
            new Appointment();
        }
        if (command == homeButton)
        {
            this.dispose();
            new HomeAllocation();
        }
        if (command == treatmentButton)
        {
            this.dispose();
            new Treatment();
        }
        if (command == databaseButton)
        {
            this.dispose();
            String[] dbcolumnNames
                    =
                    {
                        "PatientID", "Name", "Gender", "Weight", "Height", "Age", "Disability",
                        "Calories", "NextAppointment", "Home", "Treatment"
                    };
            System.setProperty("derby.language.sequence.preallocator", String.valueOf(1));
            new PatientDatabase(dbName, tableName, dbcolumnNames);

        }

    }

}
