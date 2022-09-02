package Frames;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.KeyAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Objects.Panel;
import Objects.Button;
import Objects.Employee;
import Objects.EmployeeList;
import Objects.TextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class AdminFrame extends MainFrame {

    public AdminFrame(EmployeeList employeeList, Employee employee) {
        this.setSize(600, 900);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        /* Panels */
        Panel infoPanel = new Panel();
        Panel a = new Panel();
        Panel b = new Panel();
        Panel timeCardPanel = new Panel();
        infoPanel.setBackground(Color.DARK_GRAY);
        infoPanel.setPreferredSize(new Dimension(600, 80));
        a.setBackground(Color.DARK_GRAY);
        a.setLayout(new GridLayout(1, 3, 10, 10));
        timeCardPanel.setBackground(Color.DARK_GRAY);
        timeCardPanel.setLayout(new GridLayout(1, 2, 0, 0));

        /* Buttons */
        Button submit = new Button("Submit");
        Button newUser = new Button("New User");

        /* Textfields */
        TextField idField = new TextField("Employee ID");

        /* Scroll Text Area */
        JTextArea timeCard = new JTextArea();
        JScrollPane scroll = new JScrollPane(timeCard, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        timeCard.setVisible(true);
        timeCardPanel.add(scroll);
        timeCardPanel.setVisible(true);
        timeCard.setFont(new Font("Courier", Font.BOLD, 15));
        timeCardPanel.setPreferredSize(new Dimension(400, 600));

        /* BUILD FRAME */
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setBackground(Color.DARK_GRAY);
        this.setTitle("Admin Frame");
        a.add(idField);
        a.add(submit);
        a.add(newUser);
        infoPanel.add(a);
        timeCardPanel.add(scroll);
        this.add(infoPanel);
        this.add(timeCardPanel);
        infoPanel.setVisible(true);
        timeCardPanel.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        /* Listeners */
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        // limit textfield to 6 characters
        idField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (idField.getText().length() >= 6)
                    e.consume();
            }
        });

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

        /* Button Actions */
        // Submit
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (new File("Timecards/" + idField.getText() + ".json").isFile()) {
                    JSONParser parser = new JSONParser();
                    try (FileReader reader = new FileReader("Timecards/" + idField.getText() + ".json")) {
                        Object obj = parser.parse(reader);
                        JSONArray jsonList = (JSONArray) obj;
                        timeCard.setText("");
                        timeCard.append(parseTimecardJSON(jsonList, 0));
                    } catch (IOException | ParseException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                } else {
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "Please enter a valid ID.");
                }
            }

        });

        // Newuser
        newUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = JOptionPane.showConfirmDialog(null, "Create new user?");
                if (i == 0) {
                    NewUserFrame newUserFram = new NewUserFrame(employeeList, true);

                }
            }
        });

    }

    private String parseTimecardJSON(JSONArray jsonTimeCard, int howMany) {
        String returnStr = "";
        for (int i = 0; i < jsonTimeCard.size() - howMany; i++) {
            JSONObject obj = new JSONObject();
            obj = (JSONObject) jsonTimeCard.get(i);
            String temp = (String) obj.get("stamp");
            returnStr += (temp + "\n");
        }
        System.out.println(returnStr);
        return returnStr;
    }
}
