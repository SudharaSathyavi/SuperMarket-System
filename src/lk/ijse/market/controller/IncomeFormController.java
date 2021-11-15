package lk.ijse.market.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.util.Duration;
import lk.ijse.market.DB.DbConnection;


import java.lang.management.MemoryManagerMXBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class IncomeFormController {
    public Label lblDailyIncom;
    public Label lblMonthlyIncome;
    public Label lblAbualIncome;
    public Label lblDate;
    public Label lblTime;
    public LineChart<String,Number>lineChart;
    public Label lblDate1;
    @FXML
    private BarChart<?, ?> IncomeChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;


    double total;
    public void initialize(){

        loadDateAndTime();
        try {
            setIncome();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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


    public void setIncome() throws SQLException, ClassNotFoundException {
        PreparedStatement stm= DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Order " +
                "Details`");
        ResultSet rst = stm.executeQuery();
        while(rst.next()) {

            total+=Double.parseDouble(rst.getString(5));
        }
        lblDailyIncom.setText(String.valueOf(total));
       a();


    }

    public void a() {
        XYChart.Series series=new XYChart.Series();
        series.getData().add(new XYChart.Data("JAN",85000));
        series.getData().add(new XYChart.Data("FEB",95000));
        series.getData().add(new XYChart.Data("MAR",105000));
        series.getData().add(new XYChart.Data("APR",60000));
        series.getData().add(new XYChart.Data("MAY",95000));
        series.getData().add(new XYChart.Data("JUN",75000));
        series.getData().add(new XYChart.Data("JUL",15000));




        IncomeChart.getData().addAll(series);
    }


}
