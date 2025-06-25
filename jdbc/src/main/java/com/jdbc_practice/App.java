package com.jdbc_practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.jdbc_practice.classes.PropertiesAccess;

public class App {
    private static String configsPath = "config.properties";

    public static void main( String[] args ) throws SQLException {
        try {
        PropertiesAccess propsAccess = new PropertiesAccess(configsPath);

        String jdbcURL = propsAccess.getJdbcURL();
        String username = propsAccess.getUsername();
        String password = propsAccess.getPassword();

        System.out.println("Идёт соединение к " + jdbcURL);
        Connection conn = DriverManager.getConnection(jdbcURL, username, password);
        System.out.println("Соединение прошло успешно.");

        Statement stmt = conn.createStatement();

        boolean isAppWorking = true;
        Scanner scanner = new Scanner(System.in);

        final String quit = "QUIT";
        final String select = "SELECT";

        while(isAppWorking) {            
            System.out.println("Введите команду.");
            System.out.print("> ");
            String expr = scanner.nextLine();

            if (expr.isEmpty()) {
                System.out.println("Введите команду.");
                System.out.println("> ");
                continue;
            }

            System.out.println("Вы ввели команду: " + expr + ".");            

            if(expr.equals(quit)) {
                break;
            }

            if(expr.startsWith(select)) {
                ResultSet rs = stmt.executeQuery(expr);

                int maxStrings = 10;

                int count = 0;
                while(rs.next()) {
                    if (count >= maxStrings - 1) {
                        System.out.println(rs.getString("ID") + " " + rs.getString("NAME"));
                        System.out.println("В базе данных есть ещё записи.");
                        break;
                    }
                    System.out.println(rs.getString("ID") + " " + rs.getString("NAME"));
                    
                    count++;
                }               
                rs.close();
            }

            try {
                stmt.execute(expr);
                System.out.println("Команда " + expr + " была успешно выполнена.");
            } catch (SQLException e) {
                System.err.println("Ошибка выполнения SQL: " + e.getMessage());
            }
        }        

        scanner.close();
        System.out.println("Отключение соединения.");
        conn.close();                
        System.out.println("Соединение отключено.");

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    } 
}
