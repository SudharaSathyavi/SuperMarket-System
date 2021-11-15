package lk.ijse.market.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.market.DB.DbConnection;
import lk.ijse.market.model.OrderDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModifiOrderFormController{
    public AnchorPane modifiOrderContext;
    public TextField txtOrderID;
    public JFXTextField txtItemCode;
    public JFXTextField txtOrderQty;
    public JFXTextField txtDiscount;
    double newTotal;
    Double fullTotal;

    public void searchOrderDetaisOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String OrderID= txtOrderID.getText();

        OrderDetails o= new OrderController().getOrder(OrderID);
        if (o==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(o);

        }

    }
    public void comfirmOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {


     updateTotal(txtItemCode.getText());

     OrderDetails i1= new OrderDetails(txtItemCode.getText(),Integer.parseInt(txtOrderQty.getText()),
                Double.parseDouble(txtDiscount.getText()),newTotal);


        if (new OrderController().updateOrder(i1,txtOrderID.getText()))
            new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
        else
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
    }

    void setData(OrderDetails o) {
        txtItemCode.setText(o.getItemCode());
        txtOrderQty.setText(String.valueOf(o.getOrderQTY()));
        txtDiscount.setText(String.valueOf(o.getDiscont()));

        fullTotal=(o.getTotal()*100)/(100-Double.parseDouble(txtDiscount.getText()));
    }

   public void updateTotal(String id) throws SQLException, ClassNotFoundException {
       PreparedStatement stm = DbConnection.getInstance()
               .getConnection().prepareStatement("SELECT * FROM Item WHERE ItemCode=?");
       stm.setObject(1, id);

       ResultSet rst = stm.executeQuery();
       if (rst.next()) {
           double z=(rst.getDouble(4)*Double.parseDouble(txtOrderQty.getText()));
           newTotal=z-((z/100)*Double.parseDouble(txtDiscount.getText()));
       }
   }

}
