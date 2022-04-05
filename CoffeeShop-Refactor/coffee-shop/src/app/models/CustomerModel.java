package app.models;
import app.models.EnumBody.*;
import app.services.*;
public class CustomerModel {
    private String customerId = new IDGenerator().generate();
    private String firstName;
    private String lastName;
    private String city;
    private String state;
    private String streetAddress;
    private PointsModel points = new PointsModel();
    private HistoryModel history = new HistoryModel();
    public CustomerModel(){}
    public CustomerModel(String lastName, String firstName) {this.lastName = lastName; this.firstName = firstName;}
//set
    public void set(CustomerField customerField, String arg) {
        switch (customerField) {
            case CUSTOMERID:
                this.customerId = arg;
                break;
            case FIRSTNAME:
                this.firstName = arg;
                break;
            case LASTNAME:
                this.lastName = arg;
                break;
            case CITY:
                this.city = arg;
                break;
            case STATE:
                this.state = arg;
                break;
            case STREETADDRESS:
                this.streetAddress = arg;
                break;
        }
    }

    public void setPoints(PointsModel points) {this.points = points;}
    public void setHistory(HistoryModel history) {this.history = history;}
//get
    public String get(CustomerField customer) {
        switch (customer) {
            case CUSTOMERID:
                return this.customerId;
            case FIRSTNAME:
                return this.firstName;
            case LASTNAME:
                return this.lastName;
            case CITY:
                return this.city;
            case STATE:
                return this.state;
            case STREETADDRESS:
                return this.streetAddress;
            default:
                return null;
        }
    }
    public PointsModel getPoints() {return this.points;}
    public HistoryModel getHistory() {return this.history;}









}
