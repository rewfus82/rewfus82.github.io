package Objects;

import org.json.simple.JSONObject;

public class Employee {

    private String id = "";
    private int pin = 0;
    private String first = "";
    private String last = "";
    private boolean isAdmin = false;
    private boolean onBreak = false;
    private boolean isClockedIn = false;
    private boolean isOnLunch = false;

    /* Constructors */
    public Employee() {
    }

    public Employee(String id, String first, String last) {
        this.id = id;
        this.first = first;
        this.last = last;
    }

    public Employee(String id, int pin, String first, String last) {
        this.id = id;
        this.pin = pin;
        this.first = first;
        this.last = last;
    }

    public Employee(String id, int pin, String first, String last, boolean isAdmin) {
        this.id = id;
        this.pin = pin;
        this.first = first;
        this.last = last;
        this.isAdmin = isAdmin;
    }

    public Employee(String id, int pin, String first, String last, boolean isAdmin, boolean onBreak) {
        this.id = id;
        this.pin = pin;
        this.first = first;
        this.last = last;
        this.isAdmin = isAdmin;
        this.onBreak = onBreak;
    }

    public Employee(String id, int pin, String first, String last, boolean isAdmin, boolean onBreak, boolean isClockedIn) {
        this.id = id;
        this.pin = pin;
        this.first = first;
        this.last = last;
        this.isAdmin = isAdmin;
        this.onBreak = onBreak;
        this.isClockedIn = isClockedIn;
    }

    public Employee(String id, int pin, String first, String last, boolean isAdmin, boolean onBreak, boolean isClockedIn,
            boolean isOnLunch) {
        this.id = id;
        this.pin = pin;
        this.first = first;
        this.last = last;
        this.isAdmin = isAdmin;
        this.onBreak = onBreak;
        this.isClockedIn = isClockedIn;
        this.isOnLunch = isOnLunch;
    }

    /* Getters */

    public boolean isAdmin() {
        return this.isAdmin;
    }

    public boolean isOnBreak() {
        return this.onBreak;
    }

    public boolean isClockedIn() {
        return this.isClockedIn;
    }

    public boolean isOnLunch() {
        return this.isOnLunch;
    }

    public String getId() {
        return this.id;
    }

    public int getPin() {
        return this.pin;
    }

    public String getFirstName() {
        return this.first;
    }

    public String getLastName() {
        return this.last;
    }

    public String getName() {
        return getFirstName() + " " + getLastName();
    }

    /* Setters */

    public void setAll(String id, int pin, String first, String last, boolean isAdmin) {
        this.id = id;
        this.pin = pin;
        this.first = first;
        this.last = last;
        this.isAdmin = isAdmin;
    }

    public void setBreak(boolean b) {
        this.onBreak = b;
    }

    public void setClockedIn(boolean b) {
        this.isClockedIn = b;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public void setLunch(boolean b) {
        this.isOnLunch = b;
    }

    public void setName(String first, String last) {
        this.first = first;
        this.last = last;
    }

    public void setAdmin(boolean b) {
        this.isAdmin = b;
    }

    /* Parse object to JSON */
    public JSONObject getJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", this.id);
        jsonObject.put("pin", this.pin);
        jsonObject.put("first", this.first);
        jsonObject.put("last", this.last);
        jsonObject.put("isAdmin", this.isAdmin);
        jsonObject.put("onBreak", this.onBreak);
        jsonObject.put("clockedIn", this.isClockedIn);
        jsonObject.put("onLunch", this.isOnLunch);

        return jsonObject;
    }


}
