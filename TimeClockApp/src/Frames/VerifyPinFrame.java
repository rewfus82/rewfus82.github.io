package Frames;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Objects.Button;
import Objects.Employee;
import Objects.EmployeeList;
import Objects.Panel;

import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class VerifyPinFrame extends MainFrame {
    public VerifyPinFrame(EmployeeList employeeList, Employee employee) {
    

        /* Panels */
        Panel entryPanel = new Panel();
        Panel buttonPanel = new Panel();
        entryPanel.setLayout(null);
        buttonPanel.setLayout(new GridLayout(2, 1, 0, 10));

        /* Buttons */
        Button clear = new Button("Clear");
        Button submit = new Button("Submit");

        /* Textfields */
        JPasswordField pinField1 = new JPasswordField("");
        JPasswordField pinField2 = new JPasswordField("");
        pinField1.setFont(new Font("Arial", Font.PLAIN, 20));
        pinField1.setSize(150, 30);
        pinField1.setLocation(150, 10);
        pinField2.setFont(new Font("Arial", Font.PLAIN, 20));
        pinField2.setSize(150, 30);
        pinField2.setLocation(150, 50);

        /* Labels */
        JLabel pinLabel1 = new JLabel();
        JLabel pinLabel2 = new JLabel();

        pinLabel1.setForeground(Color.WHITE);
        pinLabel2.setForeground(Color.WHITE);
        pinLabel1.setText("Enter PIN");
        pinLabel2.setText("Re-Enter PIN");
        pinLabel1.setFont(new Font("Arial", Font.PLAIN, 20));
        pinLabel1.setSize(100, 20);
        pinLabel1.setLocation(10, 15);
        pinLabel2.setFont(new Font("Arial", Font.PLAIN, 17));
        pinLabel2.setSize(100, 20);
        pinLabel2.setLocation(10, 55);

        /** BUILD FRAME */
        this.setLayout(new GridLayout(2, 1, 0, 20));
        this.setSize(400, 400);
        this.setTitle("Verify PIN");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        entryPanel.add(pinLabel1);
        entryPanel.add(pinField1);
        entryPanel.add(pinLabel2);
        entryPanel.add(pinField2);
        entryPanel.setVisible(true);
        buttonPanel.add(clear);
        buttonPanel.add(submit);
        buttonPanel.setVisible(true);
        this.add(entryPanel);
        this.add(buttonPanel);
        // this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        // limit pinField1 to 4 characters
        pinField1.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (pinField1.getText().length() >= 4)
                    e.consume();
            }
        });

        // limit pinField2 to 4 characters
        pinField2.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (pinField2.getText().length() >= 4)
                    e.consume();
            }
        });

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int p1 = Integer.parseInt(pinField1.getText());
                    int p2 = Integer.parseInt(pinField2.getText());
                    if (p1 != p2) {
                        pinField1.setText("");
                        JFrame frame = new JFrame();
                        JOptionPane.showMessageDialog(frame, "PIN numbers do not match.");
                        pinField1.setText("");
                        pinField2.setText("");

                    } else {
                        if(p1 == employee.getPin()){
                            new AdminFrame(employeeList, employee);
                            dispose();
                        } else {
                            pinField1.setText("");
                            pinField2.setText("");
                            JFrame frame = new JFrame();
                            JOptionPane.showMessageDialog(frame, "Invalid PIN.");
                        }

                    }
                } catch (Exception exception) {
                    pinField1.setText("");
                    pinField2.setText("");
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "PIN must be a number.");
                }

                // int idFieldInt = 0;
                // try {
                // idFieldInt = Integer.valueOf(idField.getText());
                // if(employeeList.idExists(idFieldInt)){
                // int i = JOptionPane.showConfirmDialog(null,
                // "Create new user with ID " + Integer.toString(idFieldInt) + "?");
                // if (i == 0) {

                // } else {
                // idField.setText("");
                // }
                // }
                // } catch (Exception exception) {
                // idField.setText("");
                // JFrame frame = new JFrame();
                // JOptionPane.showMessageDialog(frame, "Employee ID must be a number.");
                // }
            }
        });

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
