package app;

import app.models.*;
import app.models.EnumBody.*;




public class debug {
    public static void main(String[] args) {
        DrinkModel drink = new DrinkModel("Gargleblaster", "Pan-galactic gargleblaster", 1.99);
        FoodModel food = new FoodModel("Toast", "Burnt toast", 0.99);
        FoodModel food2 = new FoodModel("Pig's feet", "jar of pickled pig's feet", 6.99);         
        drink.setPrice(1.89);

        TransactionModel transaction = new TransactionModel();

        transaction.add(drink);
        transaction.add(food);
        transaction.add(food2);
        transaction.pay(20.00, TenderType.CASH);
        Object[] record = transaction.getTransaction();
        printTrans(record);
        printTrans(record);
   
    }


    public static void printTrans(Object[] record){
        System.out.println(record[0]);
        System.out.println(((TenderModel) record[1]).getString());
        System.out.println(record[2]);
        System.out.println(String.format(((BasketModel) record[3]).summary()));      
    }
}


