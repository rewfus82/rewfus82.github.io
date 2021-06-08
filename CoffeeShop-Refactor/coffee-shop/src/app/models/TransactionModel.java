package app.models;

import java.sql.Date;
import app.models.EnumBody.*;
import app.services.*;

public class TransactionModel {
    final String ID = "T-" + new IDGenerator().generate();

    private Date date = new Date(System.currentTimeMillis());
    private TenderModel tender = new TenderModel();
    private TenderType tenderType;
    private BasketModel basket = new BasketModel();

// The record holds a date, tender, tenderType and basket
    private Object[] record = new Object[4];

//Constructors
    public TransactionModel() {
        build(this.date);
    }

    public TransactionModel(TenderModel tender) {
        this.tender = tender;
    }

    public TransactionModel(BasketModel basket) {
        this.basket = basket;
    }

    public TransactionModel(TenderModel tender, BasketModel basket) {
        this.tender = tender;
        this.basket = basket;
    }

// add
    public void add(MenuItemModel arg) {
        this.basket.add(arg);
        build(this.basket);
    }

    public void add(DrinkModel arg) {
        this.basket.add(arg);
        build(this.basket);
    }

    public void add(FoodModel arg) {
        this.basket.add(arg);
        build(this.basket);
    }

    public void add(TenderType arg) {
        this.tenderType = arg;
        build(this.tenderType);
    }

// get
    public Object[] getTransaction() {
        return this.record;
    }

// pay
    public void pay(double arg, TenderType tenderType) {
        add(tenderType);
        this.tender.set(Tender.TOTAL, this.basket.getTotal());
        this.tender.set(Tender.PAID, arg);
        this.tender.set(Tender.CHANGE, arg - this.basket.getTotal());
        build(this.tender);
    }

// Build record methods
    private void build(Date arg) {
        this.record[0] = arg;
    }

    private void build(TenderModel arg) {
        this.record[1] = arg;
    }

    private void build(TenderType arg) {
        this.record[2] = arg;
    }

    private void build(BasketModel arg) {
        this.record[3] = arg;
    }

}
