package lk.ijse.market.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.market.DB.DbConnection;
import lk.ijse.market.model.Customer;
import lk.ijse.market.view.TM.CustomerOrderDetails;


import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CashierDashboardFormController{
    public AnchorPane CashierDashboardContext;
    public AnchorPane CashierContext;
    public Label lblDate;
    public Label lblTime;
    public JFXComboBox cmbCustomerID;
    public JFXTextField txtCustName;
    public JFXTextField txtCustAddress;
    public JFXTextField TxtDate;
    public JFXTextField txtOrderID;
    public TableView tblItemDetails;
    public TableColumn colItems;
    public TableColumn colQTY;
    public Label lblTotalprice;
    public TableColumn colPrice;


    public void initialize(){
        colItems.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));


        loadDateAndTime();



        try {
            loadCustomerIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbCustomerID.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    try {
                        setCustomerData((String) newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });

    }

    public void loadorderAndItems() throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement("Select * from `Order` WHERE CustID=?");
        statement.setObject(1,cmbCustomerID.getValue());

        ResultSet rst=statement.executeQuery();

        if (rst.next()){
            txtOrderID.setText(rst.getString(1));
            TxtDate.setText(rst.getString(2));
        }else {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        }
    }


    private void loadCustomerIds() throws SQLException, ClassNotFoundException {

        List<String> customerIds = new CustomerController()
                .getCustomerIds();
        cmbCustomerID.getItems().addAll(customerIds);

    }
    private void setItems() throws SQLException, ClassNotFoundException {
        double b=0;
        ObservableList<CustomerOrderDetails> obList = FXCollections.observableArrayList();
        PreparedStatement statement= DbConnection.getInstance().getConnection().prepareStatement("SELECT * From " +
                "`Order Details` WHERE OrderID=?");
        statement.setObject(1,txtOrderID.getText());
        ResultSet rst=statement.executeQuery();
       while (rst.next()) {
           obList.add(
                   new CustomerOrderDetails(rst.getString(2),rst.getInt(3)));
           double a=(rst.getDouble(5));
           b=b+a;
       }
       lblTotalprice.setText(Double.toString(b));
        tblItemDetails.setItems(obList);
    }

    private void setCustomerData(String customerId) throws SQLException, ClassNotFoundException {
        Customer c1 = new CustomerController().getCustomer(customerId);
        if (c1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtCustName.setText(c1.getCustName());
            txtCustAddress.setText(c1.getCustAddress());
        }
        loadorderAndItems();
        setItems();



    }

    private void loadDateAndTime() {
        // load Date
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        // load Time
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() +
                            " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }


    public void BackOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/DashboardForm.fxml");
        Parent load = FXMLLoader.load(resource);
        CashierDashboardContext.getChildren().clear();
        CashierDashboardContext.getChildren().add(load);
    }

    public void modifiOrderOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ModifiOrderForm.fxml");
        Parent load = FXMLLoader.load(resource);
        CashierContext.getChildren().clear();
        CashierContext.getChildren().add(load);
    }

    public void removeOrderOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/RemoveOrderForm.fxml");
        Parent load = FXMLLoader.load(resource);
        CashierContext.getChildren().clear();
        CashierContext.getChildren().add(load);
    }

    public void CustomerOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/CustomerTableForCashierForm.fxml");
        Parent load = FXMLLoader.load(resource);
        CashierContext.getChildren().clear();
        CashierContext.getChildren().add(load);


    }

    public void addCustomerOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AddNewCustomerForm.fxml");
        Parent load = FXMLLoader.load(resource);
        CashierContext.getChildren().clear();
        CashierContext.getChildren().add(load);
    }

    public void makeOrderOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/MakeOrderForm.fxml");
        Parent load = FXMLLoader.load(resource);
        CashierContext.getChildren().clear();
        CashierContext.getChildren().add(load);
    }

    public void showOrderTabels(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/OrderAndOrderDetailsTableForm.fxml");
        Parent load = FXMLLoader.load(resource);
        CashierContext.getChildren().clear();
        CashierContext.getChildren().add(load);
    }

    public void orderTabels(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/OrderAndOrderDetailsTableForm.fxml");
        Parent load = FXMLLoader.load(resource);
        CashierContext.getChildren().clear();
        CashierContext.getChildren().add(load);
    }

    public void MakeOrder(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/MakeOrderForm.fxml");
        Parent load = FXMLLoader.load(resource);
        CashierContext.getChildren().clear();
        CashierContext.getChildren().add(load);
    }

    public void homeOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/CashierDashboardForm.fxml");
        Parent load = FXMLLoader.load(resource);
        CashierDashboardContext.getChildren().clear();
        CashierDashboardContext.getChildren().add(load);

    }
}
