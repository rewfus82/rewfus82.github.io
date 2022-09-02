package Frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import Objects.Button;
import Objects.Employee;
import Objects.EmployeeList;
import Objects.InputField;
import Objects.Panel;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import javax.swing.JFrame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.io.FileWriter;
import java.io.IOException;

public class LoginFrame extends MainFrame {

    public LoginFrame(EmployeeList employeeList) {

        /* Panels */
        ImageIcon clockImage = new ImageIcon("clocksmall.png");
        Panel idPanel = new Panel();
        Panel clockPanel = new Panel();
        idPanel.setLayout(new GridLayout(1, 4, 0, 0));
   
        /* Buttons */
        Button loginButton = new Button("Login");
        Button newButton = new Button("New User");

        /* Textfields */
        InputField idField = new InputField("Employee ID");
        JPasswordField pinField = new JPasswordField();
        pinField.setPreferredSize(new Dimension(50, 50));
        pinField.setText("****");

        /* Labels */
        JLabel clockLabel = new JLabel();
        clockLabel.setText("Employee Time Clock");
        clockLabel.setIcon(clockImage);
        clockLabel.setHorizontalTextPosition(JLabel.CENTER);
        clockLabel.setVerticalTextPosition(JLabel.TOP);
        clockLabel.setForeground(new Color(255, 255, 255));
        clockLabel.setFont(new Font("Lucida Bright", Font.BOLD, 40));

        /** BUILD FRAME */
        clockPanel.add(clockLabel);
        idPanel.add(newButton);
        idPanel.add(idField);
        idPanel.add(pinField);
        idPanel.add(loginButton);
        this.setSize(600, 600);
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.DARK_GRAY));
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Time Clock App");
        this.add(clockPanel);
        this.add(idPanel);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        /* Listeners */
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Employee employee = login(idField.getText(), Integer.parseInt(pinField.getText()),
                        employeeList);
                if (employee != null) {
                    System.out.println(employee);
                    try {
                        EmployeeFrame employeeFrame = new EmployeeFrame(employeeList, employee);
                        idField.setText("Employee ID");
                        pinField.setText("****");
                    } catch (IOException | ParseException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                } else {
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "Please enter a valid ID and PIN.");
                }
            }
        });

        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = JOptionPane.showConfirmDialog(null, "Create new user?");
                if (i == 0) {
                    NewUserFrame newUserFram = new NewUserFrame(employeeList, false);
                    dispose();
                }
            }
        });

        //Click field to clear
        idField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                idField.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                System.out.println(idField.getText());
                if (idField.getText().isEmpty()) {
                    idField.setText("Employee ID");
                }
            }
        });

        // limit textfield to 6 characters
        idField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (idField.getText().length() >= 6)
                    e.consume();
            }
        });

        // limit textfield to 4 characters
        pinField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (pinField.getText().length() >= 4)
                    e.consume();
            }
        });

        pinField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                pinField.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (pinField.getText().isEmpty()) {
                    pinField.setText("PIN");
                }
            }
        });

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int i = JOptionPane.showConfirmDialog(null, "Quit Time Card Application?");
                if (i == 0) {
                    try {
                        addToRoster(employeeList);
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    System.exit(0);
                }
            }
        });

    }

    public Employee login(String id, int pin, EmployeeList list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(id) && list.get(i).getPin() == pin) {
                return list.get(i);
            }
            ;
        }
        return null;
    }

    private void addToRoster(EmployeeList employeeList) throws IOException {
        FileWriter writer = new FileWriter("RosterList/roster.json");
        JSONArray jsonList = new JSONArray();
        employeeList.forEach((e) -> {
            jsonList.add(e.getJSON());
        });
        writer.write(jsonList.toJSONString());
        writer.flush();
        writer.close();

    }

}