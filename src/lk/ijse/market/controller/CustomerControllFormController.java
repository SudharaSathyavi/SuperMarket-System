package lk.ijse.market.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.market.model.Customer;
import lk.ijse.market.view.TM.CustomerTM;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerControllFormController {
    public  AnchorPane CustomerContext;
    public TableView <CustomerTM>tblCustomer;
    public TableColumn colID;
    public TableColumn colTitle;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colCity;
    public TableColumn colProvince;
    public TableColumn colPostCode;

    public void initialize() {
        try {

            colID.setCellValueFactory(new PropertyValueFactory<>("custID"));
            colTitle.setCellValueFactory(new PropertyValueFactory<>("custTitle"));
            colName.setCellValueFactory(new PropertyValueFactory<>("custName"));
            colAddress.setCellValueFactory(new PropertyValueFactory<>("custAddress"));
            colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
            colProvince.setCellValueFactory(new PropertyValueFactory<>("Province"));
            colPostCode.setCellValueFactory(new PropertyValueFactory<>("postCode"));

            setCustomersToTable(new CustomerController().getAllCustomers());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addCustomerOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AddNewCustomerForm.fxml");
        Parent load = FXMLLoader.load(resource);
        CustomerContext.getChildren().clear();
        CustomerContext.getChildren().add(load);
    }

    public void editCustomerOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/EditCustomerForm.fxml");
        Parent load = FXMLLoader.load(resource);
        CustomerContext.getChildren().clear();
        CustomerContext.getChildren().add(load);
    }

    public void searchCustomerOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/SearchCustomerForm.fxml");
        Parent load = FXMLLoader.load(resource);
        CustomerContext.getChildren().clear();
        CustomerContext.getChildren().add(load);
    }

    public void removeCustomerOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/RemoveCustomerForm.fxml");
        Parent load = FXMLLoader.load(resource);
        CustomerContext.getChildren().clear();
        CustomerContext.getChildren().add(load);
    }
    private void setCustomersToTable(ArrayList<Customer> customers) {
        ObservableList<CustomerTM> obList = FXCollections.observableArrayList();
        customers.forEach(e->{
            obList.add(
                    new CustomerTM(e.getCustID(),e.getCustTitle(),e.getCustName(),e.getCustAddress(),e.getCity(),
                            e.getProvince(),e.getPostCode()));
        });
        tblCustomer.setItems(obList);
    }
}
