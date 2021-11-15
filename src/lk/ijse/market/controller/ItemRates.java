package lk.ijse.market.controller;



import lk.ijse.market.DB.DbConnection;
import lk.ijse.market.model.ItemQtyRates;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemRates {
    int qty;
    public List<ItemQtyRates> getItemCode() throws SQLException, ClassNotFoundException {
        List<String> itemIds=new ArrayList<>();
        itemIds=new ItemController().getAllItemIds();

        List<String>itemCodes=new ArrayList<>();
        itemCodes=new OrderDetails().getAllItemCode();
        List <ItemQtyRates> itemQtyRates=new ArrayList<>();
        for(int i=0;i<itemIds.size();i++){
            for (int j=0;j<itemCodes.size();j++){
                if(itemIds.get(i).equals(itemCodes.get(j))){
                    qty=0;
                    getItemqty(itemCodes.get(j));
                    itemQtyRates.add(new ItemQtyRates(itemCodes.get(j),qty));
                    break;
                }
            }
        }
        return itemQtyRates;
    }

    private int getItemqty(String s) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("SELECT  * FROM `Order Details` WHERE ItemCode='" +s+ "'").executeQuery();
        while (resultSet.next()){
            qty+=resultSet.getInt(3);
        }
        return qty;


    }

}
