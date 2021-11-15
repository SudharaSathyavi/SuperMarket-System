package lk.ijse.market.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.market.DB.DbConnection;
import lk.ijse.market.view.TM.OrderDetailsTM;
import lk.ijse.market.view.TM.OrderTM;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderAndOrderDetailsTableFormController {
    public TableView tblOrder;
    public TableColumn colOrderDate;
    public TableColumn colCustId;
    public ComboBox cmbSelectTable;
    public TableView tblOrderDetails;
    public TableColumn colOrderID2;
    public TableColumn colItemCode2;
    public TableColumn colQTY;
    public TableColumn colDisCount;
    public TableColumn colPrice;
    public TableColumn colOrderCode;
    public TableColumn colOrderTemp;

    public void initialize(){
        cmbSelectTable.getItems().addAll("Order","Order Details");
        cmbSelectTable.setValue("Order");


        try {
            colOrderTemp.setCellValueFactory(new PropertyValueFactory<>("orderID"));
            colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
            colCustId.setCellValueFactory(new PropertyValueFactory<>("custID"));

            colOrderID2.setCellValueFactory(new PropertyValueFactory<>("orderid"));
            colItemCode2.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
            colQTY.setCellValueFactory(new PropertyValueFactory<>("orderQTY"));
            colDisCount.setCellValueFactory(new PropertyValueFactory<>("discont"));
            colPrice.setCellValueFactory(new PropertyValueFactory<>("total"));
            loadAllOrderDetails();
            loadAllOrder();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void selectTabelOnAction(ActionEvent actionEvent) {
        if(cmbSelectTable.getValue().equals("Order Details")){
            tblOrder.setVisible(false);
            tblOrderDetails.setVisible(true);
        }else{
            tblOrder.setVisible(true);
            tblOrderDetails.setVisible(false);
        }
    }
    private void loadAllOrder() throws ClassNotFoundException, SQLException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Order`");
        ResultSet rst = stm.executeQuery();
        ObservableList<OrderTM> obList = FXCollections.observableArrayList();
        while (rst.next()) {
            obList.add(new OrderTM(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            ));
        }
        tblOrder.setItems(obList);
    }
    private void loadAllOrderDetails() throws ClassNotFoundException, SQLException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Order " +
                "Details`");
        ResultSet rst = stm.executeQuery();
        ObservableList<OrderDetailsTM> obList = FXCollections.observableArrayList();
        while (rst.next()) {
            obList.add(new OrderDetailsTM(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4),
                    rst.getDouble(5)
            ));
        }
        tblOrderDetails.setItems(obList);
    }
}
