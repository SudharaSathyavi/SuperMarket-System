package lk.ijse.market.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.market.model.Customer;
import lk.ijse.market.view.TM.CustomerTM;


import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerTableForCashierFormController {
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
