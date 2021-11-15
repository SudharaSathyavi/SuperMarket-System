package lk.ijse.market.controller;



import lk.ijse.market.DB.DbConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetails {
    public OrderDetails() {
    }

    public List<String> getAllItemCode() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Order Details`").executeQuery();
        List <String> itemCodes=new ArrayList<>();
        while (resultSet.next()){
            itemCodes.add(resultSet.getString(2));
        }
        return itemCodes;
    }

}
