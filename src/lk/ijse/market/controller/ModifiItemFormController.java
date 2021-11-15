package lk.ijse.market.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.market.model.Item;


import java.sql.SQLException;
import java.util.List;

public class ModifiItemFormController {
    public TextField txtDiscription;
    public TextField txtPackSize;
    public TextField txtUnitPrice;
    public TextField txtQTYOnhand;
    public JFXComboBox cmdItcomCode;

    public void initialize(){
        try {
            loadItemIds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cmdItcomCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setItemData((String) newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public void comfirmOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Item i1= new Item((String) cmdItcomCode.getValue(),txtDiscription.getText(),txtPackSize.getText(),
                Double.parseDouble(txtUnitPrice.getText()),Integer.parseInt(txtQTYOnhand.getText())
        );


        if (new ItemController().updateItem(i1))
            new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
        else
            new Alert(Alert.AlertType.WARNING,"Try Again").show();

    }

    private void loadItemIds() throws SQLException, ClassNotFoundException {
        List<String> itemIds = new ItemController().getAllItemIds();
        cmdItcomCode.getItems().addAll(itemIds);
    }
    private void setItemData(String itemCode) throws SQLException, ClassNotFoundException {
        Item i1 = new ItemController().getItem(itemCode);
        if (i1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
           txtDiscription.setText(i1.getItenDescription());
           txtPackSize.setText(i1.getPackSize());
           txtUnitPrice.setText(String.valueOf(i1.getUnitPrice()));
           txtQTYOnhand.setText(String.valueOf(i1.getQtyOnHand()));

        }
    }

}
