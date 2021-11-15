package lk.ijse.market.controller;

import lk.ijse.market.DB.DbConnection;
import lk.ijse.market.model.Order;
import lk.ijse.market.model.OrderDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderController {

    public String getOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT orderId FROM `Order` ORDER BY OrderID DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()){

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<=9){
                return "O-00"+tempId;
            }else if(tempId<99){

                return "O-0"+tempId;

            }else{
                return "O-"+tempId;
            }

        }else{
            return "O-001";
        }
    }

    public boolean placeOrder(Order order){
        Connection con=null;
        try {
            con=DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm=con.prepareStatement("INSERT INTO `Order` VALUES (?,?,?)");

            stm.setObject(1,order.getOrderID());
            stm.setObject(2,order.getOrderDate());
            stm.setObject(3,order.getCustID());

            if(stm.executeUpdate()>0){
                if(saveOrderDetail(order.getOrderID(),order.getItems())){
                    con.commit();
                    return true;
                }else{
                    con.rollback();
                    return false;
                }
            }else{
                con.rollback();
                return false;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }
    private boolean saveOrderDetail(String orderId, ArrayList<OrderDetails> items) throws SQLException,
            ClassNotFoundException {
        for (OrderDetails temp : items
        ) {
            PreparedStatement stm = DbConnection.getInstance().
                    getConnection().
                    prepareStatement("INSERT INTO `Order Details` VALUES(?,?,?,?,?)");
            stm.setObject(1, orderId);
            stm.setObject(2, temp.getItemCode());
            stm.setObject(3, temp.getOrderQTY());
            stm.setObject(4, temp.getDiscont());
            stm.setObject(5, temp.getTotal());
            if (stm.executeUpdate() > 0) {

            } else {
                return false;
            }
        }
        return true;
    }


    public boolean updateOrder(OrderDetails o,String orderID) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE `Order Details` " +
                "SET  " +"Orderqty=?, Discount=?,Price=? WHERE OrderID=?");
        stm.setObject(1,o.getOrderQTY());
        stm.setObject(2,o.getDiscont());
        stm.setObject(3,o.getTotal());
        stm.setObject(4,orderID);

        return stm.executeUpdate()>0;
    }
    public OrderDetails getOrder(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance()
                .getConnection().prepareStatement("SELECT * FROM `Order Details` WHERE OrderID=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new OrderDetails(
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4),
                    rst.getDouble(5)
            );

        } else {
            return null;
        }
    }
    public void mangeQTY(String id,int qty) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection()
                .prepareStatement
                        ("UPDATE Item SET QtyOnHand=(qtyOnHand-"+qty+ ") WHERE ItemCode='" + id + "'");

    }

}
