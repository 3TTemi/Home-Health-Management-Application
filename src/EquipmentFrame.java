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

public class EquipmentFrame extends JFrame implements ActionListener
{
    // declaring private variables

    private JPanel addBox;
    private JPanel titlePanel;
    private JPanel exitFrame;
    private JPanel mainPanel;
    private JButton closeButton;
    private JLabel frameTitle;
    private JPanel navigationEquipment;

    private JLabel serialLabel;
    private JLabel dateLabel;
    private JLabel priceLabel;
    private JLabel restockerLabel;
    private JLabel quantityLabel;
    private JLabel homeLabel;

    private JTextField serialField;
    private JTextField dateField;
    private JTextField priceField;
    private JTextField restockerField;
    private JTextField quantityField;
    private JTextField homeField;

    private JButton addButton;
    private JButton valueButton;
    private JButton recommendationButton;
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

    public EquipmentFrame()
    {
        // consturcting frame
        this.setBounds(100, 100, 850, 500);
        this.getContentPane().setBackground(HomePage.BAKGROUND_COLOR);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //creating and styling components
        titlePanel = new JPanel();
        exitFrame = new JPanel();
        mainPanel = new JPanel();

        addBox = new JPanel();
        closeButton = new JButton("Close the Program");
        closeButton.addActionListener(this);
        homePageButton = new JButton("Back To Home Page");
        homePageButton.addActionListener(this);
        frameTitle = new JLabel("Medical Equipment");
        serialLabel = new JLabel("Serial Number: ");
        dateLabel = new JLabel("<html>Date Bought: <br> (mm-dd-yyyy) <html>  ");
        priceLabel = new JLabel("Price Bought: ");
        restockerLabel = new JLabel("Retail Restocker: ");
        quantityLabel = new JLabel("Quantity Available: ");
        homeLabel = new JLabel("Group Home: ");
        addButton = new JButton("Add Equipment");
        addButton.addActionListener(this);
        valueButton = new JButton("Equipment Value");
        valueButton.addActionListener(this);
        recommendationButton = new JButton("Equipment Recommendation");
        recommendationButton.addActionListener(this);
        databaseButton = new JButton("Database");
        databaseButton.addActionListener(this);
        navigationEquipment = new JPanel();
        serialField = new JTextField(9);
        serialField.setBorder(BorderFactory.createLineBorder(Color.black));
        dateField = new JTextField(9);
        dateField.setBorder(BorderFactory.createLineBorder(Color.black));
        priceField = new JTextField(9);
        priceField.setBorder(BorderFactory.createLineBorder(Color.black));
        restockerField = new JTextField(9);
        restockerField.setBorder(BorderFactory.createLineBorder(Color.black));
        quantityField = new JTextField(9);
        quantityField.setBorder(BorderFactory.createLineBorder(Color.black));
        homeField = new JTextField(9);
        homeField.setBorder(BorderFactory.createLineBorder(Color.black));

        deletePanel = new JPanel();
        deleteWhereText = new JLabel("Delete where Serial Number = ");
        deleteWhereField = new JTextField(9);
        deleteWhereField.setBorder(BorderFactory.createLineBorder(Color.black));
        deleteButton = new JButton("Delete Equipment");
        deleteButton.addActionListener(this);
        deleteBox = new JPanel();

        updatePanel = new JPanel();
        updateText = new JLabel("Update column");
        updateField = new JTextField(5);
        updateField.setBorder(BorderFactory.createLineBorder(Color.black));
        toText = new JLabel("to calue");
        toField = new JTextField(5);
        toField.setBorder(BorderFactory.createLineBorder(Color.black));
        updateWhereText = new JLabel("where Serial Number = ");
        updateWhereField = new JTextField(10);
        updateWhereField.setBorder(BorderFactory.createLineBorder(Color.black));
        updateButton = new JButton("Update Equipment");
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

        navigationEquipment.add(valueButton);
        navigationEquipment.add(recommendationButton);
        navigationEquipment.add(databaseButton);
        navigationEquipment.setBackground(HomePage.BAKGROUND_COLOR);
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

        addInputs.setBackground(HomePage.BAKGROUND_COLOR);

        mainPanel.setBackground(HomePage.BAKGROUND_COLOR);

        addBox.setLayout(new BoxLayout(addBox, BoxLayout.PAGE_AXIS));

        addBox.add(addInputs);
        addBox.add(addButton);
        addBox.setBackground(HomePage.BAKGROUND_COLOR);
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        addInputs.add(serialLabel);

        addInputs.add(serialField);
        addInputs.add(dateLabel);
        addInputs.add(dateField);
        addInputs.add(priceLabel);
        addInputs.add(priceField);
        addInputs.add(restockerLabel);
        addInputs.add(restockerField);
        addInputs.add(quantityLabel);
        addInputs.add(quantityField);
        addInputs.add(homeLabel);
        addInputs.add(homeField);

        mainPanel.add(Box.createVerticalStrut(120));
        mainPanel.add(addBox);
        mainPanel.add(Box.createRigidArea(new Dimension(100, 0)));

        mainPanel.add(deleteBox);

        mainPanel.add(Box.createRigidArea(new Dimension(100, 100)));

        mainPanel.add(updateBox);
        mainPanel.add(Box.createVerticalStrut(70));
        mainPanel.add(navigationEquipment);

        // adding variables to frame
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(exitFrame, BorderLayout.SOUTH);
        this.add(mainPanel, BorderLayout.CENTER);

        this.setVisible(true);

    }

    public static void main(String[] args)
    {
        // make object of class
        EquipmentFrame eqipmentObj = new EquipmentFrame();

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        // Update / Calculate information in GUI 
        String dbName = "HomeHealthCare";
        String tableName = "Equipment";
        String[] columnNames
                =
                {
                    "SerialNumber", "RetailRestocker", "GroupHome",
                    "CurrentValue", "Quantity", "OriginalPrice", "DateBought", "DaysUntilRenewal"
                };

        JavaDatabase dbObj = new JavaDatabase(dbName);
        Connection myDbConn = dbObj.getDbConn();

        String serialNumber;
        String retailRestocker;
        String groupHome;
        int currentPrice;
        int quantity;
        int originalPrice;
        String timeUntilRenewal;
        int equipmentCounter = 0;
        String userDateBought;
        String columnBefore;
        String column;
        String toValue;
        PreparedStatement psCount = null;
        ResultSet rsCount = null;
        PreparedStatement psNames = null;
        ResultSet rsNames = null;
        int numEquipment = 0;
        PreparedStatement psSelect = null;
        ResultSet rsSelect = null;
        String selectedDate;

        String valueQuery = "Select count(*) FROM Equipment";
        try
        {
            psCount = myDbConn.prepareStatement(valueQuery);
            rsCount = psCount.executeQuery();
            if (rsCount.next())
            {
                numEquipment = rsCount.getInt(1);
            }
        } catch (Exception err)
        {
            err.printStackTrace();
        }

        String[] serialNumbersData = new String[numEquipment];

        String selectEquipmentName = "SELECT * FROM Equipment";
        try
        {
            psNames = myDbConn.prepareStatement(selectEquipmentName);
            rsNames = psNames.executeQuery();

            for (int i = 0; i < numEquipment; i++)
            {
                if (rsNames.next())
                {
                    serialNumbersData[i] = rsNames.getString("SerialNumber");

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
            this.dispose();
            new HomePage();
        }
        if (command == valueButton)
        {
            String[] columnValues
                    =
                    {
                        "SerialNumber", "OriginalPrice", "CurrentValue"
                    };
            this.dispose();
            new EquipmentValue(dbName, tableName, columnValues);
        }
        if (command == recommendationButton)
        {
            this.dispose();
            new EquipmentRecommendation();
        }
        if (command == databaseButton)
        {

            this.dispose();
            new EquipmentDatabase(dbName, tableName, columnNames);
        }
        if (command == addButton)
        {

            try
            {

                String dbQuery = "INSERT INTO Equipment VALUES(?,?,?,?,?,?,?,?)";

                serialNumber = serialField.getText();
                retailRestocker = restockerField.getText();
                groupHome = homeField.getText();
                quantity = Integer.parseInt(quantityField.getText());
                originalPrice = Integer.parseInt(priceField.getText());
                userDateBought = dateField.getText();

                PreparedStatement ps = myDbConn.prepareStatement(dbQuery);
                equipmentCounter++;

                ps.setString(1, serialNumber);
                ps.setString(2, retailRestocker);
                ps.setString(3, groupHome);
                ps.setInt(4, 0);
                ps.setInt(5, quantity);
                ps.setInt(6, originalPrice);
                ps.setString(7, userDateBought);
                ps.setInt(8, 0);

                ps.executeUpdate();
                System.out.println("Data inserted successfully");

            } catch (Exception err)
            {
            new Warning("A function", "Recheck your inputted values");
            }

            Equipment equipmentObj = new Equipment();

            String filteredDate = "";
            int purchasePrice = 0;
            int currentPriceValue = 0;
            String userDate = "";
            String serialInput = "";

            userDate = dateField.getText();
            filteredDate = userDate.replace("-", "");
            purchasePrice = Integer.parseInt(priceField.getText());
            currentPriceValue = equipmentObj.calculateCurrentValue(filteredDate, purchasePrice);
            serialInput = serialField.getText();

            dateField.setText("");
            restockerField.setText("");
            homeField.setText("");
            quantityField.setText("");
            priceField.setText("");

            try
            {
                String updateQuery = "UPDATE Equipment SET CurrentValue = ? WHERE SerialNumber = ?";

                PreparedStatement psUpdate = myDbConn.prepareStatement(updateQuery);

                psUpdate.setInt(1, currentPriceValue);
                psUpdate.setString(2, serialInput);

                psUpdate.executeUpdate();
                System.out.println("Current Value Updated successfully");

            } catch (Exception err)
            {
                err.printStackTrace();
            }

            String userDateTime = "";
            int renewalTime = 0;
            String serialInputTime = "";

            serialInputTime = serialField.getText();

            String selectQuery = "SELECT DateBought FROM Equipment WHERE SerialNumber = " + "'" + serialInputTime + "'";

            try
            {

                psSelect = myDbConn.prepareStatement(selectQuery);
                rsSelect = psSelect.executeQuery();

                if (rsSelect.next())
                {
                    userDateTime = rsSelect.getString(1);
                    userDateTime = userDateTime.replace("-", "");

                }
            } catch (Exception err)
            {
                err.printStackTrace();
            }

            renewalTime = equipmentObj.calculateRenewalTime(userDateTime);

            dateField.setText("");
            serialField.setText("");
            restockerField.setText("");
            homeField.setText("");
            quantityField.setText("");
            priceField.setText("");

            try
            {
                String updateQuery = "UPDATE Equipment SET DaysUntilRenewal = ? WHERE SerialNumber = ?";

                PreparedStatement psUpdate = myDbConn.prepareStatement(updateQuery);

                psUpdate.setInt(1, renewalTime);
                psUpdate.setString(2, serialInputTime);

                psUpdate.executeUpdate();
                System.out.println("DaysUntilRenewal Updated successfully");

            } catch (Exception err)
            {
                err.printStackTrace();
            }

        }
        if (command == deleteButton)
        {
            try
            {

                String dbQuery = "DELETE FROM Equipment WHERE SerialNumber = ?";

                serialNumber = deleteWhereField.getText();

                PreparedStatement ps = myDbConn.prepareStatement(dbQuery);

                ps.setString(1, serialNumber);

                ps.executeUpdate();
                System.out.println("Data deleted successfully");

            } catch (Exception err)
            {

            new Warning("A function", "Recheck your inputted values");
            }
        }
        if (command == updateButton)
        {
            try
            {
                String dbQuery = "UPDATE Equipment SET ";

                column = updateField.getText();
                toValue = toField.getText();
                serialNumber = updateWhereField.getText();
                dbQuery = dbQuery + column + " = ? WHERE SerialNumber = ?";
                PreparedStatement ps = myDbConn.prepareStatement(dbQuery);
                if (column.equals("SerialNumber") || column.equals("RetailRestocker") || column.equals("GroupHome") || column.equals("DateBought"))
                {
                    ps.setString(1, toValue);
                }
                if (column.equals("CurrentValue") || column.equals("OriginalPrice"))
                {
                    ps.setInt(1, Integer.parseInt(toValue));
                }
                ps.setString(2, serialNumber);

                ps.executeUpdate();
                System.out.println("Data Updated successfully");

            } catch (Exception err)
            {
            new Warning("A function", "Recheck your inputted values");

            }

            String userDate = "";
            int renewalTime = 0;
            String serialInput = "";

            Equipment equipmentObj = new Equipment();

            serialInput = updateWhereField.getText();

            String selectQuery = "SELECT DateBought FROM Equipment WHERE SerialNumber = " + "'" + serialInput + "'";

            try
            {

                psSelect = myDbConn.prepareStatement(selectQuery);
                rsSelect = psSelect.executeQuery();

                if (rsSelect.next())
                {
                    userDate = rsSelect.getString(1);
                    userDate = userDate.replace("-", "");


                }
            } catch (Exception err)
            {
                err.printStackTrace();
            }

            renewalTime = equipmentObj.calculateRenewalTime(userDate);

            try
            {
                String updateQuery = "UPDATE Equipment SET DaysUntilRenewal = ? WHERE SerialNumber = ?";

                PreparedStatement psUpdate = myDbConn.prepareStatement(updateQuery);

                psUpdate.setInt(1, renewalTime);
                psUpdate.setString(2, serialInput);

                psUpdate.executeUpdate();
                System.out.println("DaysUntilRenewal Updated successfully");

            } catch (Exception err)
            {
                err.printStackTrace();
            }

            String filteredDate = "";
            int purchasePrice = 0;
            int currentPriceValue = 0;
            String userDatePrice = "";
            String serialInputPrice = "";
            String serialInputDate;
            String newDate = "";

            serialInputDate = updateWhereField.getText();

            String selectDateQuery = "SELECT DateBought FROM Equipment WHERE SerialNumber = " + "'" + serialInputDate + "'";

            try
            {
                psSelect = myDbConn.prepareStatement(selectDateQuery);
                rsSelect = psSelect.executeQuery();
                if (rsSelect.next())
                {
                    newDate = rsSelect.getString(1);
                }
            } catch (Exception err)
            {
                err.printStackTrace();
            }

            filteredDate = newDate.replace("-", "");

            String selectPriceQuery = "SELECT OriginalPrice FROM Equipment WHERE SerialNumber = " + "'" + serialInput + "'";

            try
            {

                psSelect = myDbConn.prepareStatement(selectPriceQuery);
                rsSelect = psSelect.executeQuery();

                if (rsSelect.next())
                {
                    purchasePrice = Integer.parseInt(rsSelect.getString(1));


                }
            } catch (Exception err)
            {
                err.printStackTrace();
            }

            currentPriceValue = equipmentObj.calculateCurrentValue(filteredDate, purchasePrice);

            dateField.setText("");
            restockerField.setText("");
            homeField.setText("");
            quantityField.setText("");
            priceField.setText("");

            serialInputPrice = updateWhereField.getText();

            try
            {
                String updateQuery = "UPDATE Equipment SET CurrentValue = ? WHERE SerialNumber = ?";

                PreparedStatement psUpdate = myDbConn.prepareStatement(updateQuery);

                psUpdate.setInt(1, currentPriceValue);
                psUpdate.setString(2, serialInputPrice);

                psUpdate.executeUpdate();
                System.out.println("Current Value Updated successfully");

            } catch (Exception err)
            {
                err.printStackTrace();
            }

        }

    }
}
