package kz.iitu.configuration;

public class Card {
    private Integer id;
    private String cardNumber;
    private String pinCode;
    private Integer accountId;

    public Card() {}

    public Card(Integer id, String cardNumber, String pinCode, Integer accountId) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.pinCode = pinCode;
        this.accountId = accountId;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPinCode() {
        return pinCode;
    }

    public Integer getAccountId() {
        return accountId;
    }
}
