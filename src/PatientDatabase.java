//package HomeHealthManagement;

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

public class PatientDatabase extends JFrame implements ActionListener
{

    // declaring private variables
    private JPanel titlePanel;
    private JPanel buttonPanel;
    private JButton closeButton;
    private JLabel frameTitle;

    private JButton homePageButton;

    private ArrayList<ArrayList<String>> dataList;
    private Object[][] data;
    private JTable patientTable;
    private JScrollPane scrollTable;
    private JTableHeader header;
    private TableColumn column;

    public PatientDatabase(String dbName, String tableName, String[] columnNames)
    {
        // consturcting frame
        this.setBounds(100, 100, 1500, 500);
        this.getContentPane().setBackground(HomePage.BAKGROUND_COLOR);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        titlePanel = new JPanel();
        buttonPanel = new JPanel();
        closeButton = new JButton("Close the Frame");
        closeButton.addActionListener(this);
        homePageButton = new JButton("Back To Home Page");
        homePageButton.addActionListener(this);
        frameTitle = new JLabel("Patient Datbase");

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

        JavaDatabase objDb = new JavaDatabase(dbName);
        dataList = objDb.getData(tableName, columnNames);
        objDb.getData(tableName, columnNames);

        data = objDb.to2ndArray(dataList);

        patientTable = new JTable(data, columnNames);
        patientTable.setGridColor(Color.GRAY);
        patientTable.setBackground(HomePage.BAKGROUND_COLOR);
        patientTable.setFont(new Font("Arial", Font.BOLD, 20));

        header = patientTable.getTableHeader();
        header.setBackground(HomePage.BAKGROUND_COLOR);
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Arial", Font.BOLD, 15));

        patientTable.setRowHeight(25);

        scrollTable = new JScrollPane();
        scrollTable.getViewport().add(patientTable);
        patientTable.setFillsViewportHeight(true);

        // adding variables to frame
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.add(scrollTable, BorderLayout.CENTER);

        this.setVisible(true);

    }

    public static void main(String[] args)
    {
        System.setProperty("derby.language.sequence.preallocator", String.valueOf(1));
        String dbName = "HomeHealthCare";
        String tableName = "Patients";
        String[] columnNames
                =
                {
                    "PatientID", "Name", "Gender", "Weight", "Height", "Age", "Disability",
                    "Calories", "NextAppointment", "Home", "Treatment"
                };
        // make object of class
        new PatientDatabase(dbName, tableName, columnNames);

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

    }

}
