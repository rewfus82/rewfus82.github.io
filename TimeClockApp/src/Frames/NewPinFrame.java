package Frames;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import org.json.simple.JSONArray;
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
import java.io.FileWriter;
import java.io.IOException;

public class NewPinFrame extends MainFrame {
    public NewPinFrame(EmployeeList employeeList, String id, String first, String last, boolean isAdmin) {
        Employee employee = new Employee(id, first, last);

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
        this.setTitle("New Pin for ID #" + id);
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
                        int pin = Integer.parseInt(pinField1.getText());
                        Employee newEmployee = new Employee(id, pin, first, last);
                        if (isAdmin) {
                            newEmployee.setAdmin(true);
                        }
                        employeeList.add(newEmployee);
                        addToRoster(employeeList);
                        employeeList.forEach((em) -> {
                            System.out.println(em.getId());
                        });
                        new LoginFrame(employeeList);
                        JFrame frame = new JFrame();
                        JOptionPane.showMessageDialog(frame, "Created new user " + newEmployee.getName() + " with ID " + newEmployee.getId());
                        dispose();

                    }
                } catch (Exception exception) {
                    pinField1.setText("");
                    pinField2.setText("");
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "PIN must be a number.");
                }
            }
        });

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pinField1.setText("");
                pinField2.setText("");
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
