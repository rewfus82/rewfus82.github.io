package app.models;

import app.models.EnumBody.Tender;

public class TenderModel {
    protected double total;
    protected double paid;
    protected double change;

    public double get(Tender tender) {
        switch (tender) {
            case TOTAL: {
                return this.total;
            }
            case PAID: {
                return this.paid;
            }
            case CHANGE: {
                return this.change;
            }
            default: {
                return -1;
            }
        }
    }

    public void set(Tender tender, double arg) {
        switch (tender) {
            case TOTAL: {
                this.total = arg;
            }
            case PAID: {
                this.paid = arg;
            }
            case CHANGE: {
                this.change = arg;
            }
        }
    }

    public String getString(){
        return ("Paid: " + this.paid + " - " + this.total + " = " + this.change);
    }
}
