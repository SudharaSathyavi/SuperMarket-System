package lk.ijse.market.controller;

import com.jfoenix.controls.JFXTreeTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.market.model.Item;
import lk.ijse.market.view.TM.ItemTM;


import java.sql.SQLException;
import java.util.ArrayList;

public class ItemsTableFormController {
    public TableView<ItemTM> tblItem;
    public TableColumn colCode;
    public TableColumn colDiscription;
    public TableColumn colPackSize;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;



    public void initialize() {
        try {

            colCode.setCellValueFactory(new PropertyValueFactory<>("itemID"));
            colDiscription.setCellValueFactory(new PropertyValueFactory<>("itenDescription"));
            colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
            colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
            colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));


            setCustomersToTable(new ItemController().getAllItem());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setCustomersToTable(ArrayList<Item> item) {
       ObservableList<ItemTM> obList = FXCollections.observableArrayList();

        item.forEach(e->{
            obList.add(
                    new ItemTM(e.getItemID(),e.getItenDescription(),e.getPackSize(),e.getUnitPrice(),e.getQtyOnHand()));
        });
        tblItem.setItems(obList);
    }
}
