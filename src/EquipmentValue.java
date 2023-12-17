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
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class EquipmentValue extends JFrame implements ActionListener
{

    // declaring private variables
    private JPanel titlePanel;
    private JPanel buttonPanel;
    private JPanel mainPanel;
    private JButton closeButton;
    private JLabel frameTitle;

    private JButton homePageButton;

    private ArrayList<ArrayList<String>> dataList;
    private Object[][] data;
    private JTable equipmentTable;
    private JScrollPane scrollTable;
    private JTableHeader header;
    private TableColumn column;

    public EquipmentValue(String dbName, String tableName, String[] columnNames)
    {
        // consturcting frame
        this.setBounds(100, 100, 800, 500);
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
        frameTitle = new JLabel("Equipment Value");

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

        JavaDatabase objDb = new JavaDatabase(dbName);
        dataList = objDb.getData(tableName, columnNames);
        objDb.getData(tableName, columnNames);

        data = objDb.to2ndArray(dataList);

        equipmentTable = new JTable(data, columnNames);
        // put stylign here
        equipmentTable.setGridColor(Color.GRAY);
        equipmentTable.setBackground(HomePage.BAKGROUND_COLOR);
        equipmentTable.setFont(new Font("Arial", Font.BOLD, 20));

        header = equipmentTable.getTableHeader();
        // put styling here
        header.setBackground(HomePage.BAKGROUND_COLOR);
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Arial", Font.BOLD, 15));

        equipmentTable.setRowHeight(25);

        scrollTable = new JScrollPane();
        scrollTable.getViewport().add(equipmentTable);
        equipmentTable.setFillsViewportHeight(true);

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
        String tableName = "Equipment";
        String[] columnNames
                =
                {
                    "SerialNumber", "OriginalPrice", "CurrentValue"
                };
        // make object of class
        EquipmentValue equipmentObj = new EquipmentValue(dbName, tableName, columnNames);

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
