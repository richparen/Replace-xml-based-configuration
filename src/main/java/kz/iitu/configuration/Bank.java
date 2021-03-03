package kz.iitu.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component()
public class Bank implements BankService {
    @Autowired
    DBConnection dbConnection;

    public Bank() {}

    @Autowired
    public Bank(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public DBConnection getDbConnection() {
        return dbConnection;
    }

    @Autowired
    public void setDbConnection(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public boolean checkCard(String cardNumber, String pinCode) {
        String sql = "SELECT * FROM cards WHERE cards.cardNumber = \'" + cardNumber + "\' AND cards.pinCode = \'" + pinCode + "\'";

        ResultSet resultSet = dbConnection.getData(sql);

        try {
            while (resultSet.next()) {
                if (resultSet.getString("cardNumber").equals(cardNumber) && resultSet.getString("pinCode").equals(pinCode)) {
                    return true;
                }
            }
        } catch (SQLException sqlE) {
            System.out.println("ERROR!");
            System.out.println(sqlE);
        }
        return false;
    }

    @Override
    public double checkBalance(String cardNumber) {
        String sql = "SELECT * FROM accounts, cards WHERE accounts.id = cards.accountId AND cards.cardNumber = \'" + cardNumber + "\'";

        ResultSet resultSet = dbConnection.getData(sql);

        try {
            while (resultSet.next()) {
                return resultSet.getDouble("balance");
            }
        } catch (SQLException sqlE) {
            System.out.println("ERROR!");
            System.out.println(sqlE);
        }
        return 0;
    }

    @Override
    public boolean withdraw(double amount, String cardNumber) {
        String sql = "SELECT * FROM accounts, cards WHERE accounts.id = cards.accountId AND cards.cardNumber = \'" + cardNumber + "\'";

        ResultSet resultSet = dbConnection.getData(sql);

        try {
            while (resultSet.next()) {
                if (resultSet.getDouble("balance") >= amount) {
                    String sqlUpdate = "UPDATE accounts set balance = balance - " + amount + " WHERE accounts.id = " + resultSet.getInt("accountId");
                    dbConnection.updateData(sqlUpdate);
                    return true;
                }
            }
        } catch (SQLException sqlE) {
            System.out.println("ERROR!");
            System.out.println(sqlE);
        }

        return false;
    }

    @Override
    public boolean topUp(double amount, String cardNumber) {
        String sql = "SELECT * FROM accounts, cards WHERE accounts.id = cards.accountId AND cards.cardNumber = \'" + cardNumber + "\'";

        ResultSet resultSet = dbConnection.getData(sql);

        try {
            while (resultSet.next()) {
                if (resultSet.getString("cardNumber").equals(cardNumber)) {
                    String sqlUpdate = "UPDATE accounts set balance = balance + " + amount + " WHERE accounts.id = " + resultSet.getInt("accountId");
                    dbConnection.updateData(sqlUpdate);
                    return true;
                }
            }
        } catch (SQLException sqlE) {
            System.out.println("ERROR!");
            System.out.println(sqlE);
        }

        return false;
    }

    @Override
    public void changePinCode(String newPinCode, String cardNumber) {
        String sql = "SELECT * FROM cards WHERE cards.cardNumber = \'" + cardNumber + "\'";

        ResultSet resultSet = dbConnection.getData(sql);

        try {
            while (resultSet.next()) {
                if (resultSet.getString("cardNumber").equals(cardNumber)) {
                    System.out.println("update pincode");
                    String sqlUpdate = "UPDATE cards set pinCode = \'" + newPinCode +"\' WHERE cardNumber = \'" + cardNumber + "\'";
                    dbConnection.updateData(sqlUpdate);
                    return;
                }
            }
        } catch (SQLException sqlE) {
            System.out.println("ERROR!");
            System.out.println(sqlE);
        }
    }
}
