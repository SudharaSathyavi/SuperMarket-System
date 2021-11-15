package lk.ijse.market.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.market.DB.DbConnection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RemoveOrderFormController {
    public TextField txtOrderId;
    public JFXTextField txtDate;
    public JFXTextField txtCustID;
    String ItemCode;
    int qty;

    public void searchOrder(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        PreparedStatement stm =
                DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Order` WHERE OrderID=?");
        stm.setObject(1, txtOrderId.getText());
        ResultSet rst = stm.executeQuery();
        if (rst.next()){
            txtDate.setText(rst.getString(2));
            txtCustID.setText(rst.getString(3));
        }else{
            new Alert(Alert.AlertType.WARNING, "Empty Set").show();
        }

    }


    public void removeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM `Order` WHERE OrderID='"+txtOrderId.getText()+"'").executeUpdate()>0){
            //updateQtyITEM(txtOrderId.getText());
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    public void updateQTY(int qty,String itemCode) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("Update Item SET " +
                "QtyOnHand=QtyOnHand+?"+"WHERE ItemCode=?");
        stm.setObject(1,qty);
        stm.setObject(2,itemCode);
    }
   /* public void updateQtyITEM(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM `Order Details` WHERE  OrderID=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
           ItemCode= rst.getString(2);
           qty=rst.getInt(3);
           updateQTY(qty,ItemCode);
        }
    }*/
}
