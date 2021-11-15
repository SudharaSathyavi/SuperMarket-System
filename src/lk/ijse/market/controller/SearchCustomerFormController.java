package lk.ijse.market.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import lk.ijse.market.model.Customer;


import java.sql.SQLException;

public class SearchCustomerFormController {
    public JFXTextField txtID;
    public JFXTextField txtTitle;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtCity;
    public JFXTextField txtProvince;
    public JFXTextField txtPostCode;

    public void searchCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String customerId = txtID.getText();

        Customer c1= new CustomerController().getCustomer(customerId);
        if (c1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            txtTitle.setVisible(true);
            txtName.setVisible(true);
            txtAddress.setVisible(true);
            txtPostCode.setVisible(true);
            txtProvince.setVisible(true);
            txtCity.setVisible(true);
            setData(c1);
        }

    }
    void setData(Customer c){
        txtID.setText(c.getCustID());
        txtTitle.setText(c.getCustTitle());
        txtName.setText(c.getCustName());
        txtAddress.setText(c.getCustAddress());
        txtCity.setText(c.getCity());
        txtProvince.setText(c.getProvince());
        txtPostCode.setText(c.getPostCode());

    }
}
