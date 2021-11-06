package ru.netology.data;

import lombok.SneakyThrows;
import java.sql.Connection;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;

public class DbHelper {
    public DbHelper() {
    }

    @SneakyThrows
    private static Connection getConnection() {
        var url = System.getProperty("datasource.url");
        return DriverManager.getConnection(url, "app", "pass");
    }

    @SneakyThrows
    public static String getStatusCredit() {
        var conn = getConnection();
        QueryRunner qr = new QueryRunner();
        var status = "select status from credit_request_entity where created = (select max(created) from credit_request_entity);";
        return qr.query(conn, status, new ScalarHandler<>());
    }

    @SneakyThrows
    public static String getStatusDebitCard() {
        var conn = getConnection();
        QueryRunner qr = new QueryRunner();
        var status = "select status from payment_entity where created = (select max(created) from payment_entity);";
        return qr.query(conn, status, new ScalarHandler<>());
    }
}
