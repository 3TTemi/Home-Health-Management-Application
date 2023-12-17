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
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Warning extends JFrame implements ActionListener
{

  // declaring private variables
  private JPanel titlePanel;
  private JPanel buttonPanel;
  private JPanel mainPanel;
  private JButton closeButton;
  private JLabel frameTitle;
  private JLabel wentWrongTitle;
  private JLabel fixProblemTitle;
  private JLabel fixProblemText;
  private JPanel textPanel;
  private JButton homePageButton;

  public Warning(String wrongInput, String fixInput)
  {
    // consturcting frame
    this.setBounds(100, 100, 600, 500);
    this.getContentPane().setBackground(HomePage.BAKGROUND_COLOR);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    //creating and styling components
    titlePanel = new JPanel();
    buttonPanel = new JPanel();
    mainPanel = new JPanel();
    closeButton = new JButton("Close the Frame");
    closeButton.addActionListener(this);
    homePageButton = new JButton("Back To Home Page");
    homePageButton.addActionListener(this);
    frameTitle = new JLabel("Warning");
    wentWrongTitle = new JLabel(wrongInput + " went wrong in the program");
    fixProblemTitle = new JLabel("You can fix the problem by:");
    fixProblemText = new JLabel(fixInput);

    textPanel = new JPanel();
    textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.PAGE_AXIS));
    textPanel.add(wentWrongTitle);
    textPanel.add(Box.createVerticalStrut(20));
    textPanel.add(fixProblemTitle);
    textPanel.add(fixProblemText);
    textPanel.setBackground(HomePage.BAKGROUND_COLOR);

    wentWrongTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
    fixProblemTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

    fixProblemText.setAlignmentX(Component.CENTER_ALIGNMENT);

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
    mainPanel.add(textPanel);
    mainPanel.setLayout(new GridBagLayout());

    // adding variables to frame
    this.add(titlePanel, BorderLayout.NORTH);
    this.add(buttonPanel, BorderLayout.SOUTH);
    this.add(mainPanel, BorderLayout.CENTER);

    this.setVisible(true);

  }

  public static void main(String[] args)
  {
    // make object of class
    Warning warningObj = new Warning("Test Error", "Test Fix");

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
