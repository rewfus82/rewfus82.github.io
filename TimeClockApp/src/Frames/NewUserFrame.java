package Frames;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import Objects.Button;
import Objects.EmployeeList;
import Objects.Panel;
import Objects.TextField;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.ActionEvent;

public class NewUserFrame extends MainFrame {
    private boolean adminOverride = false;
    public NewUserFrame(EmployeeList employeeList, boolean adminOverride) {
        this.adminOverride = adminOverride;
        this.setLayout(new GridLayout(2, 1, 0, 20));
        this.setSize(400, 460);
        this.setTitle("New User Form");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        /* Panels */
        Panel entryPanel = new Panel();
        Panel buttonPanel = new Panel();
        entryPanel.setLayout(null);
        buttonPanel.setLayout(new GridLayout(2, 1, 0, 10));

        /* Buttons */
        Button clear = new Button("Clear");
        Button submit = new Button("Submit");

        /* Checkboxes */
        JCheckBox adminCheckBox = new JCheckBox(" Admin?");
        adminCheckBox.setSize(150, 30);
        adminCheckBox.setLocation(150, 120);
        adminCheckBox.setForeground(Color.WHITE);
        adminCheckBox.setBackground(Color.DARK_GRAY);
        adminCheckBox.setFont(new Font("Arial", Font.PLAIN, 20));


        /* Textfields */
        TextField firstField = new TextField("");
        TextField lastField = new TextField("");
        TextField idField = new TextField("");
        // firstField.setPreferredSize(new Dimension(200,50));
        // lastField.setPreferredSize(new Dimension(200,50));
        // idField.setPreferredSize(new Dimension(200,50));
        // firstField.setColumns(20);
        // lastField.setColumns(20);
        // idField.setColumns(20);
        firstField.setFont(new Font("Arial", Font.PLAIN, 20));
        firstField.setSize(150, 30);
        firstField.setLocation(150, 0);
        lastField.setFont(new Font("Arial", Font.PLAIN, 20));
        lastField.setSize(150, 30);
        lastField.setLocation(150, 40);
        idField.setFont(new Font("Arial", Font.PLAIN, 20));
        idField.setSize(150, 30);
        idField.setLocation(150, 80);

        /* Labels */
        JLabel firstLabel = new JLabel();
        JLabel lastLabel = new JLabel();
        JLabel idLabel = new JLabel();
        firstLabel.setForeground(Color.WHITE);
        lastLabel.setForeground(Color.WHITE);
        idLabel.setForeground(Color.WHITE);
        firstLabel.setText("First Name");
        lastLabel.setText("Last Name");
        idLabel.setText("Employee ID");
        firstLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        firstLabel.setSize(100, 20);
        firstLabel.setLocation(0, 0);
        lastLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        lastLabel.setSize(100, 20);
        lastLabel.setLocation(0, 45);
        idLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        idLabel.setSize(150, 20);
        idLabel.setLocation(0, 90);

        /** BUILD FRAME */
        if(adminOverride){       
            System.out.println("Admin override."); 
            entryPanel.add(firstLabel);
            entryPanel.add(firstField);
            entryPanel.add(lastLabel);
            entryPanel.add(lastField);
            entryPanel.add(idLabel);
            entryPanel.add(idField);
            entryPanel.add(adminCheckBox);
            entryPanel.setVisible(true);
            buttonPanel.add(clear);
            buttonPanel.add(submit);
            buttonPanel.setVisible(true);
            this.add(entryPanel);
            this.add(buttonPanel);
            // this.pack();
            this.setLocationRelativeTo(null);
            this.setVisible(true);
        } else {
            System.out.println("NO Admin override."); 
            entryPanel.add(firstLabel);
            entryPanel.add(firstField);
            entryPanel.add(lastLabel);
            entryPanel.add(lastField);
            entryPanel.add(idLabel);
            entryPanel.add(idField);
            entryPanel.setVisible(true);
            buttonPanel.add(clear);
            buttonPanel.add(submit);
            buttonPanel.setVisible(true);
            this.add(entryPanel);
            this.add(buttonPanel);
            // this.pack();
            this.setLocationRelativeTo(null);
            this.setVisible(true);
        

        }


        this.add(entryPanel);
        this.add(buttonPanel);
        // this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        /* Listeners */
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        // limit idField to 6 characters
        idField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (idField.getText().length() >= 6)
                    e.consume();
            }
        });

        // limit lastField to 10 characters
        lastField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (lastField.getText().length() >= 15)
                    e.consume();
            }
        });

        // limit firstField to 10 characters
        firstField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (firstField.getText().length() >= 15)
                    e.consume();
            }
        });

        /** Button Actions */
        // Submit
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idFieldInt;
                if (firstField.getText().isEmpty() || lastField.getText().isEmpty()) {
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "You must enter a first and last name.");
                } else {
                    try {
                        idFieldInt = idField.getText();
                        System.out.println("Idfield is valid");
                        if (!employeeList.idExists(idFieldInt)) {
                            int i = JOptionPane.showConfirmDialog(null,
                                    "Create new user with ID " + idFieldInt + "?");
                            if (i == 0) {
                                String id = idField.getText();
                                String first = firstField.getText();
                                String last = lastField.getText();
                                new NewPinFrame(employeeList, id, first, last, adminOverride);
                                dispose();
                            } else {
                                idField.setText("");
                            }
                        } else {
                            idField.setText("");
                            JFrame frame = new JFrame();
                            JOptionPane.showMessageDialog(frame, "Employee ID already exists!");
                        }
                    } catch (Exception exception) {
                        idField.setText("");
                        JFrame frame = new JFrame();
                        JOptionPane.showMessageDialog(frame, "Employee ID must be a number.");
                    }
                }
            }
        });

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                firstField.setText("");
                lastField.setText("");
                idField.setText("");
            }
        });


    }


}
