package Frames;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import Objects.Button;
import Objects.Employee;
import Objects.EmployeeList;
import Objects.Panel;
import Objects.TextField;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class EmployeeFrame extends MainFrame {
    private boolean adminOverride = false;
    public EmployeeFrame(EmployeeList employeeList, Employee employee) throws IOException, ParseException {
        if(employee.isAdmin()){
            adminOverride = true;
        }
        this.setSize(600, 900);

        /* Panels */
        Panel infoPanel = new Panel();
        Panel timeClockPanel = new Panel();
        Panel a = new Panel();
        Panel b = new Panel();
        Panel timeCardPanel = new Panel();
        infoPanel.setBackground(Color.DARK_GRAY);
        infoPanel.setPreferredSize(new Dimension(600, 80));
        infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        timeClockPanel.setBackground(Color.DARK_GRAY);
        timeClockPanel.setLayout(new GridLayout(1, 2, 0, 0));
        a.setBackground(Color.DARK_GRAY);
        a.setPreferredSize(new Dimension(600, 480));
        a.setLayout(new GridLayout(3, 1, 0, 0));
        b.setBackground(Color.DARK_GRAY);
        b.setPreferredSize(new Dimension(600, 480));
        b.setLayout(new GridLayout(1, 1, 0, 0));
        timeCardPanel.setBackground(Color.DARK_GRAY);
        timeCardPanel.setLayout(new GridLayout(1, 2, 0, 0));

        /* Buttons */
        JToggleButton clock = new JToggleButton("CLOCK", employee.isClockedIn());
        JToggleButton breakTime = new JToggleButton("BREAK", employee.isOnBreak());
        JToggleButton lunch = new JToggleButton("LUNCH", employee.isOnLunch());
        Button logout = new Button("Logout");
        Button admin = new Button("Admin");
        clock.setFont(new Font("Impact", Font.PLAIN, 50));
        breakTime.setFont(new Font("Impact", Font.PLAIN, 50));
        lunch.setFont(new Font("Impact", Font.PLAIN, 50));

        /* Textfields */
        TextField id = new TextField(employee.getId());
        TextField name = new TextField(employee.getName());
        id.setEditable(false);
        name.setEditable(false);
        id.setPreferredSize(new Dimension(100, 50));
        name.setPreferredSize(new Dimension(100, 50));
        id.setBackground(Color.DARK_GRAY);
        name.setBackground(Color.DARK_GRAY);
        id.setForeground(Color.WHITE);
        name.setForeground(Color.WHITE);
        id.setColumns(7);
        name.setColumns(25);
        id.setHorizontalAlignment(JTextField.CENTER);
        name.setHorizontalAlignment(JTextField.CENTER);

        /* Scroll Text Area */
        JTextArea timeCard = new JTextArea();
        JScrollPane scroll = new JScrollPane(timeCard, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        timeCard.setVisible(true);
        timeCardPanel.add(scroll);
        timeCardPanel.setVisible(true);
        timeCard.setFont(new Font("Courier", Font.BOLD, 15));
        timeCard.setBackground(Color.DARK_GRAY);
        timeCard.setForeground(Color.WHITE);

        /* Check for JSON timecard */
        if (new File("Timecards/" + employee.getId() + ".json").isFile()) {
            JSONParser parser = new JSONParser();
            FileReader reader = new FileReader("Timecards/" + employee.getId() + ".json");
            Object obj = parser.parse(reader);
            JSONArray jsonList = (JSONArray) obj;
            timeCard.append(parseTimecardJSON(jsonList));
            employeeList.forEach((e) -> System.out.println(e.getJSON()));
            reader.close();
        }

        /* BUILD FRAME */
        this.setLayout(new GridLayout(2, 1, 0, 50));
        this.setBackground(Color.DARK_GRAY);
        this.setTitle("Time Card for " + employee.getName());
        infoPanel.add(id);
        infoPanel.add(name);
        infoPanel.add(logout);
        if (adminOverride) {
            infoPanel.add(admin);
        }
        timeClockPanel.add(lunch);
        timeClockPanel.add(breakTime);
        timeCardPanel.add(scroll);
        a.add(infoPanel);
        a.add(timeClockPanel);
        b.add(clock);
        a.add(b);
        infoPanel.setVisible(true);
        timeClockPanel.setVisible(true);
        timeCardPanel.setVisible(true);
        this.add(a);
        this.add(timeCardPanel);
        // this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        if (new File("Timecards/" + employee.getId() + ".json").isFile()) {
        } else {
            timeCard.append("New Time Card for " + "(" + employee.getId() + ") " + employee.getName() + "\n");
        }

        /* Button Initilaizers */
        if(employee.isClockedIn()){
            toggleButton(clock, true);
            System.out.println("Employee is clocked in.");
        } else {
            System.out.println("Employee is not clocked in.");
            toggleButton(clock, false);
        }

        if(employee.isOnBreak()){
            toggleButton(breakTime, true);
        } else {
            toggleButton(breakTime, false);
        }

        if(employee.isOnLunch()){
            toggleButton(lunch, true);
        } else {
            toggleButton(lunch, false);
        }


        /* Button Color Initialize */
        if (clock.isSelected()) {
            clock.setForeground(Color.GREEN);
        } else {
            clock.setForeground(Color.RED);
        }

        if (breakTime.isSelected()) {
            breakTime.setForeground(Color.GREEN);
        } else {
            breakTime.setForeground(Color.RED);
        }

        if (lunch.isSelected()) {
            lunch.setForeground(Color.GREEN);
        } else {
            lunch.setForeground(Color.RED);
        }

        /* Listeners */
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int i = JOptionPane.showConfirmDialog(null, "Close Time Card?");
                System.out.println(i);
                if (i == 0) {
                    try {
                        writeTimeCard(employee, timeCard);
                        dispose();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        });

        /** Button Actions **/

        // Lunch
        lunch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employee.setLunch(((JToggleButton) e.getSource()).isSelected());
                if (employee.isClockedIn() || adminOverride) {
                    if (employee.isOnBreak()&& !adminOverride) {
                        JFrame frame = new JFrame();
                        JOptionPane.showMessageDialog(frame, "Must clock in from break before taking a lunch.");
                        toggleButton(lunch);
                        lunch.setForeground(Color.RED);
                    } else if (((JToggleButton) e.getSource()).isSelected()) {
                        timeCard.append(timeStamp() + " LUNCH START\n");
                        lunch.setForeground(Color.GREEN);
                    } else {
                        timeCard.append(timeStamp() + " LUNCH END\n");
                        lunch.setForeground(Color.RED);
                    }
                } else {
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "Employee must be clocked in to take a lunch break!");
                    toggleButton(lunch);
                    lunch.setForeground(Color.RED);
                }
                employee.setLunch(((JToggleButton) e.getSource()).isSelected());
            }
        });

        // Breaktime
        breakTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employee.setBreak(((JToggleButton) e.getSource()).isSelected());
                if (employee.isClockedIn() || adminOverride) {
                    if (employee.isOnLunch() && !adminOverride) {
                        JFrame frame = new JFrame();
                        JOptionPane.showMessageDialog(frame, "Must clock in from lunch before taking a break.");
                        toggleButton(breakTime);
                        breakTime.setForeground(Color.RED);
                    } else if (((JToggleButton) e.getSource()).isSelected()) {
                        timeCard.append(timeStamp() + " BREAK START\n");
                        breakTime.setForeground(Color.GREEN);
                    } else {
                        timeCard.append(timeStamp() + " BREAK END\n");
                        breakTime.setForeground(Color.RED);
                    }
                } else {
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "Employee must be clocked in to take a break!");
                    toggleButton(breakTime);
                    breakTime.setForeground(Color.RED);
                }
                employee.setBreak(((JToggleButton) e.getSource()).isSelected());
            }
        });

        // Clock
        clock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employee.setClockedIn(((JToggleButton) e.getSource()).isSelected());
                if (((JToggleButton) e.getSource()).isSelected()) {
                    timeCard.append(timeStamp() + " CLOCK IN\n");
                    clock.setForeground(Color.GREEN);
                } else {
                    if (employee.isOnBreak()) {
                        timeCard.append(timeStamp() + " BREAK END\n");
                        toggleButton(breakTime);
                        employee.setBreak(false);
                        breakTime.setForeground(Color.RED);
                    }
                    if (employee.isOnLunch()) {
                        timeCard.append(timeStamp() + " LUNCH END\n");
                        toggleButton(lunch);
                        employee.setLunch(false);
                        lunch.setForeground(Color.RED);
                    }
                    timeCard.append(timeStamp() + " CLOCK OUT\n");
                    clock.setForeground(Color.RED);
                }
            }
        });

        // Logout
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = JOptionPane.showConfirmDialog(null, "Logout of Time Clock?");
                if (i == 0) {
                    try {
                        writeTimeCard(employee, timeCard);
                        dispose();
                        // LoginFrame loginFrame = new LoginFrame(employeeList);
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        });

        //Admin
        admin.addActionListener(new ActionListener() {
            @Override
            
            public void actionPerformed(ActionEvent e) {
                new VerifyPinFrame(employeeList, employee);
            }
        });

    }

    public EmployeeFrame(EmployeeList employeeList, Employee employee, boolean adminOverride){
        this.adminOverride = adminOverride;
    }

    private String parseTimecardJSON(JSONArray jsonTimeCard) {
        String returnStr = "";
        for (int i = 0; i < jsonTimeCard.size(); i++) {
            JSONObject obj = new JSONObject();
            obj = (JSONObject) jsonTimeCard.get(i);
            String temp = (String) obj.get("stamp");
            returnStr += (temp + "\n");
        }
        System.out.println(returnStr);
        return returnStr;
    }

    private void writeTimeCard(Employee employee, JTextArea timeCard) throws IOException {
        FileWriter writer = new FileWriter(("Timecards/" + employee.getId() + ".json"));
        JSONArray jsonList = new JSONArray();
        String s[] = timeCard.getText().split("\\r?\\n");
        ArrayList<String> arrList = new ArrayList<>(Arrays.asList(s));

        arrList.forEach((e) -> {
            jsonList.add(parseToJSON(e));
        });

        writer.write(jsonList.toJSONString());
        writer.flush();
        writer.close();
    }

    private JSONObject parseToJSON(String str) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("stamp", str);
        return jsonObject;
    }

    private void toggleButton(JToggleButton button) {
        if (button.getForeground() == Color.GREEN) {
            button.setForeground(Color.RED);
        } else {
            button.setForeground(Color.GREEN);

        }
        button.setSelected(false);
        button.setEnabled(true);
    }

    private void toggleButton(JToggleButton button, boolean b) {
        if (button.getForeground() == Color.GREEN) {
            button.setForeground(Color.RED);
        } else {
            button.setForeground(Color.GREEN);

        }
        button.setSelected(b);
        button.setEnabled(true);
    }

    private String timeStamp() {
        return new SimpleDateFormat("[yyyy/MM/dd] - [HH:mm]").format(new java.util.Date());
    }

}
