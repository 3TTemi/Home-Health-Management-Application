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
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class HomePage extends JFrame implements ActionListener
{

    // declaring private variables
    private JPanel titlePanel;
    private JPanel buttonPanel;
    private JPanel navigationPanel;
    private JButton staffButton;
    private JButton patientButton;
    private JButton equipmentButton;
    private JButton closeButton;
    private JLabel frameTitle;
    public static final Color BAKGROUND_COLOR = new Color(255, 242, 204);

    public HomePage()
    {
        // consturcting frame
        this.setBounds(100, 100, 600, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //creating and styling components
        titlePanel = new JPanel();
        buttonPanel = new JPanel();
        navigationPanel = new JPanel();
        staffButton = new JButton("Staff");
        staffButton.addActionListener(this);
        patientButton = new JButton("Patient");
        patientButton.addActionListener(this);
        equipmentButton = new JButton("Medical Equipment");
        equipmentButton.addActionListener(this);
        closeButton = new JButton("Close the Program");
        closeButton.addActionListener(this);
        frameTitle = new JLabel("Home Page");

        titlePanel.add(frameTitle);
        buttonPanel.add(closeButton);
        navigationPanel.add(staffButton);
        navigationPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        navigationPanel.add(patientButton);
        navigationPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        navigationPanel.add(equipmentButton);

        staffButton.setPreferredSize(new Dimension(160, 130));
        staffButton.setBackground(Color.green);

        staffButton.setOpaque(true);
        staffButton.setBorder(BorderFactory.createLineBorder(Color.black));

        patientButton.setPreferredSize(new Dimension(160, 130));
        patientButton.setBackground(Color.green);
        patientButton.setOpaque(true);
        patientButton.setBorder(BorderFactory.createLineBorder(Color.black));

        equipmentButton.setPreferredSize(new Dimension(160, 130));
        equipmentButton.setBackground(Color.green);
        equipmentButton.setOpaque(true);
        equipmentButton.setBorder(BorderFactory.createLineBorder(Color.black));

        navigationPanel.setBackground(BAKGROUND_COLOR);
        navigationPanel.setLayout(new GridBagLayout());

        titlePanel.setLayout(new GridBagLayout());
        buttonPanel.setLayout(new GridBagLayout());

        titlePanel.setPreferredSize(new Dimension(500, 50));
        buttonPanel.setPreferredSize(new Dimension(500, 50));
        titlePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        // adding variables to frame
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.add(navigationPanel, BorderLayout.CENTER);

        this.setVisible(true);

    }

    public static void main(String[] args)
    {
        // make object of class
        HomePage homeObj = new HomePage();

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        // command to recieve input
        Object command = e.getSource();

        // if buttons pressed do the subsequent action
        if (command == staffButton)
        {
            this.dispose();
            new StaffFrame();
        }
        if (command == patientButton)
        {
            this.dispose();
            new PatientFrame();
        }
        if (command == equipmentButton)
        {
            this.dispose();
            new EquipmentFrame();
        }
        if (command == closeButton)
        {
            System.exit(0);
        }
    }

}
