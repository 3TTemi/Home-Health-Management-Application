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
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
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

public class EquipmentRecommendation extends JFrame implements ActionListener
{

    // declaring private variables
    private JPanel titlePanel;
    private JPanel buttonPanel;
    private JPanel mainPanel;
    private JButton closeButton;
    private JLabel frameTitle;

    private JLabel shouldBuyLabel;
    private JLabel restockersLabel;
    private JPanel displayPanel;
    private JButton homePageButton;

    public EquipmentRecommendation()
    {
        // consturcting frame
        this.setBounds(100, 100, 600, 500);
        this.getContentPane().setBackground(HomePage.BAKGROUND_COLOR);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //creating and styling components
        titlePanel = new JPanel();
        buttonPanel = new JPanel();
        mainPanel = new JPanel();
        closeButton = new JButton("Close the Program");
        closeButton.addActionListener(this);
        homePageButton = new JButton("Back To Home Page");
        homePageButton.addActionListener(this);
        frameTitle = new JLabel("Equipment Recommendation");
        String[] nameList
                =
                {
                    "Name1", "Name2", "Name3", "Name4"
                };
        shouldBuyLabel = new JLabel("<html> Equipment that should be bought:  <ul>" + "<li> XXX </li>" + "<li> XXX </li>" + "<li> XXX </li>" + "</ul><html>");
        restockersLabel = new JLabel("<html> Available Restockers: <ul>" + "<li> XXX </li>" + "<li> XXX </li>" + "<li> XXX </li>" + "</ul><html>");
        displayPanel = new JPanel();
        titlePanel.add(frameTitle);
        buttonPanel.add(closeButton);

        titlePanel.setLayout(new GridBagLayout());
        buttonPanel.setLayout(new GridBagLayout());

        titlePanel.setPreferredSize(new Dimension(500, 50));
        buttonPanel.setPreferredSize(new Dimension(500, 50));
        titlePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        mainPanel.setBackground(HomePage.BAKGROUND_COLOR);

        displayPanel.setBackground(HomePage.BAKGROUND_COLOR);
        displayPanel.add(shouldBuyLabel);
        displayPanel.add(Box.createHorizontalStrut(50));
        buttonPanel.add(Box.createHorizontalStrut(30));
        buttonPanel.add(homePageButton);

        displayPanel.add(restockersLabel);

        mainPanel.setLayout(new GridBagLayout());
        mainPanel.add(displayPanel);

        String dbName = "HomeHealthCare";
        String tableName = "Patients";
        String[] columnNames
                =
                {
                    "SerialNumber", "RetailRestocker", "GroupHome",
                    "CurrentValue", "Quantity", "OriginalPrice", "DateBought", "DaysUntilRenewal"
                };

        JavaDatabase dbObj = new JavaDatabase(dbName);
        Connection myDbConn = dbObj.getDbConn();
        PreparedStatement psCount = null;
        ResultSet rsCount = null;
        PreparedStatement psPrices = null;
        ResultSet rsPrices = null;
        PreparedStatement psNumbers = null;
        ResultSet rsNumbers = null;
        PreparedStatement psStockers = null;
        ResultSet rsStockers = null;
        int numEquipment = 0;

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
        ArrayList<String> recommendedSerials = new ArrayList<String>();
        ArrayList<String> recommendedStockers = new ArrayList<String>();

        int[] currentPrices = new int[numEquipment];
        String[] serialNumbers = new String[numEquipment];
        String[] restockers = new String[numEquipment];
        // recieving values from the database 
        String selectEquipmentPrices = "SELECT * FROM Equipment";
        try
        {
            psPrices = myDbConn.prepareStatement(selectEquipmentPrices);
            rsPrices = psPrices.executeQuery();

            for (int i = 0; i < numEquipment; i++)
            {
                if (rsPrices.next())
                {
                    currentPrices[i] = Integer.parseInt(rsPrices.getString("CurrentValue"));

                }
            }
        } catch (Exception error)
        {
            new Warning("A function", "Recheck your inputted values");
        }

        String selectEquipmentNumbers = "SELECT * FROM Equipment";
        try
        {
            psNumbers = myDbConn.prepareStatement(selectEquipmentNumbers);
            rsNumbers = psNumbers.executeQuery();

            for (int i = 0; i < numEquipment; i++)
            {
                if (rsNumbers.next())
                {
                    serialNumbers[i] = rsNumbers.getString("SerialNumber");

                }
            }
        } catch (Exception error)
        {
            new Warning("A function", "Recheck your inputted values");
//            error.printStackTrace();
        }

        String selectEquipmentStockers = "SELECT * FROM Equipment";
        try
        {
            psStockers = myDbConn.prepareStatement(selectEquipmentStockers);
            rsStockers = psStockers.executeQuery();

            for (int i = 0; i < numEquipment; i++)
            {
                if (rsStockers.next())
                {
                    restockers[i] = rsStockers.getString("RetailRestocker");

                }
            }
        } catch (Exception error)
        {
            new Warning("A function", "Recheck your inputted values");
//            error.printStackTrace();
        }

        for (int i = 0; i < numEquipment; i++)
        {
            if (currentPrices[i] <= 0)
            {
                recommendedSerials.add(serialNumbers[i]);
                recommendedStockers.add(restockers[i]);
            }
        }
        // if statments change text on frame
        if (recommendedSerials.size() == 0)
        {
            shouldBuyLabel.setText("<html> Equipment that should be bought:  <ul>" + "<li> All equipment are in working condition </li>" + "</ul><html>");
            restockersLabel.setText("<html> Available Restockers: <ul>" + "<li> All equipment are in working condition </li>" + "</ul><html>");

        }
        if (recommendedSerials.size() == 1)
        {
            shouldBuyLabel.setText("<html> Equipment that should be bought:  <ul>" + "<li> Serial Number: "
                    + recommendedSerials.get(0) + "</li>" + "<li> N/A </li>" + "<li> N/A </li>" + "</ul><html>");
            restockersLabel.setText("<html> Available Restockers: <ul>" + "<li> Manufacturer Name: "
                    + recommendedStockers.get(0) + "</li>" + "<li> N/A </li>" + "<li> N/A </li>" + "</ul><html>");

        }
        if (recommendedSerials.size() == 2)
        {
            shouldBuyLabel.setText("<html> Equipment that should be bought:  <ul>" + "<li> Serial Number: "
                    + recommendedSerials.get(0) + "</li>" + "<li> Serial Number: " + recommendedSerials.get(1) + "</li>" + "<li> N/A </li>" + "</ul><html>");
            restockersLabel.setText("<html> Available Restockers:  <ul>" + "<li> Manufacturer Name: "
                    + recommendedStockers.get(0) + "</li>" + "<li> Manufacturer Name:: " + recommendedStockers.get(1) + "</li>" + "<li> N/A </li>" + "</ul><html>");
        }
        if (recommendedSerials.size() >= 3)
        {
            shouldBuyLabel.setText("<html> Equipment that should be bought:  <ul>" + "<li> Serial Number: "
                    + recommendedSerials.get(0) + "</li>" + "<li> Serial Number: " + recommendedSerials.get(1) 
                    + "</li>" + "<li> Serial Number: " + recommendedSerials.get(2) + "</li>" + "</ul><html>");
            restockersLabel.setText("<html> Available Restockers:  <ul>" + "<li> Manufacturer Name: "
                    + recommendedStockers.get(0) + "</li>" + "<li> Manufacturer Name:: " + recommendedStockers.get(1) 
                    + "</li>" + "<li> Manufacturer Name: " + recommendedStockers.get(2) + "</li>" + "</ul><html>");
        }

        // adding variables to frame
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.add(mainPanel, BorderLayout.CENTER);

        this.setVisible(true);

    }

    public static void main(String[] args)
    {
        // make object of class
        EquipmentRecommendation recommendationObj = new EquipmentRecommendation();

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
    }

}
