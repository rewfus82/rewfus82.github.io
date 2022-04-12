package app.models;

public interface EnumBody {
    public enum CustomerField { CUSTOMERID, FIRSTNAME, LASTNAME, CITY, STATE, STREETADDRESS }
    public enum Tender { TOTAL, PAID, CHANGE }
    public enum TenderType { CASH, CREDIT, DEBIT, CHECK, GIFTCARD, BTC, ETH, DOGE }
    public enum Points { ADD, SUB, SET }
    public enum PointsGet { GET }
    public enum MenuItem { NAME, DESC }
}
