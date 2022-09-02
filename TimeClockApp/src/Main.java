import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import Frames.*;
import Objects.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/* Timeclock App by Ryan McLaughlin - August, 2022 */

public class Main {
    public static EmployeeList employeeList = new EmployeeList();

    public static void main(String[] args) throws IOException, ParseException {

        initialize();
        LoginFrame loginFrame = new LoginFrame(employeeList);
    }

    private static Employee parseEmployeeJSONObject(JSONObject employeeJSONObject) {
        String id = (String) employeeJSONObject.get("id");
        long pinLong = (long) employeeJSONObject.get("pin");
        int pin = (int) pinLong;
        String first = (String) employeeJSONObject.get("first");
        String last = (String) employeeJSONObject.get("last");
        boolean isAdmin = (boolean) employeeJSONObject.get("isAdmin");
        boolean onBreak = (boolean) employeeJSONObject.get("onBreak");
        boolean clockedIn = (boolean) employeeJSONObject.get("clockedIn");
        boolean onLunch = (boolean) employeeJSONObject.get("onLunch");

        return (new Employee(id, pin, first, last, isAdmin, onBreak, clockedIn, onLunch));

    }

    private static void initialize() throws IOException, ParseException {
        if (employeeList.isEmpty()) {
            System.out.println("Employee list is empty.");
            
            // if roster.json exists, read it
            if (new File("RosterList/roster.json").isFile()) {

                JSONParser parser = new JSONParser();
                FileReader reader = new FileReader("RosterList/roster.json");
                Object obj = parser.parse(reader);
                JSONArray jsonList = (JSONArray) obj;

                for (int i = 0; i < jsonList.size(); i++) {

                    JSONObject jsonObject = (JSONObject) jsonList.get(i);
                    employeeList.add(parseEmployeeJSONObject(jsonObject));

                }
                employeeList.forEach((e) -> System.out.println(e.getJSON()));
                reader.close();

                // if roster.json doesn't exist, create it
            } else {
                FileWriter writer = new FileWriter("RosterList/roster.json");
                JSONArray jsonList = new JSONArray();

                // ... and populate it with mock data
                /* Employee List Mock Data */
                employeeList.add(new Employee("123456", 1234, "Joe", "Schmoe", false));
                employeeList.add(new Employee("999999", 9999, "Head", "Boss", true));
                employeeList.add(new Employee("888888", 8888, "Assistant", "Boss", true));
                employeeList.add(new Employee("121212", 1212, "Jane", "Doe", false));
                employeeList.add(new Employee("333333", 3333, "Harrison", "Ford", false));
                employeeList.forEach((e) -> {
                jsonList.add(e.getJSON());
                });

                writer.write(jsonList.toJSONString());
                writer.flush();
                writer.close();
            }
        }
    }

}