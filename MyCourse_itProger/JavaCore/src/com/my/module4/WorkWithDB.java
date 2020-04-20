package com.my.module4;

import java.sql.*;

public class WorkWithDB {
    public static void main(String[] args) {
        DB db = new DB();
        try {
            /*
            db.createItemsTable();
            db.insertItems("Кружка Мужская", 300, "cups");
            db.insertItems("Кепка красная", 150, "hats");
            db.insertItems("Кепка синяя", 200, "hats");
            db.insertItems("Кружка Женская", 400, "cups");
            db.insertItems("Красная футблока", 550, "shirts");
            db.insertItems("Футболка \"Рик и Морти\"", 700, "shirts");
            */

            /*
            db.createUsersTable();
            db.insertUsers("john", "some_pass");
            db.insertUsers("alex", "some_pass");
             */

            /*
            db.createOrdersTable();
            db.insertOrders(1, 3);
            db.insertOrders(1, 2);
            db.insertOrders(2, 1);
            db.insertOrders(2, 6);
            db.insertOrders(2, 4);
            */

            db.getJonhUsers();
            db.getHatsItems();
            db.getOrders();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class DB {
    private final String HOST = "localhost";
    private final String PORT = "3306";
    private final String DB_NAME = "simple";
    private final String LOGIN = "root";
    private final String PASS = "root";

    private Connection dbConn = null;

    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME +
                "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConn = DriverManager.getConnection(connStr, LOGIN, PASS);
        return dbConn;
    }

    public void isConnected() throws ClassNotFoundException, SQLException {
        dbConn = getDbConnection();
        System.out.println(dbConn.isValid(1000));
    }

    public void createItemsTable() throws ClassNotFoundException, SQLException  {
        String sql = "CREATE TABLE IF NOT EXISTS `items` (" +
                "`id` int(11) UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL, " +
                "`title` varchar(100) NOT NULL, " +
                "`price` int(5) UNSIGNED NOT NULL, " +
                "`category` varchar(50) NOT NULL) " +
                "ENGINE=MyISAM DEFAULT CHARSET=utf8;";

        Statement statement = getDbConnection().createStatement();
        statement.executeUpdate(sql);
    }

    public void createOrdersTable() throws ClassNotFoundException, SQLException  {
        String sql = "CREATE TABLE IF NOT EXISTS `orders` (" +
                "`id` int(11) UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL, " +
                "`user_id` int(11) UNSIGNED NOT NULL, " +
                "`item_id` int(11) UNSIGNED NOT NULL) " +
                "ENGINE=MyISAM DEFAULT CHARSET=utf8;";

        Statement statement = getDbConnection().createStatement();
        statement.executeUpdate(sql);
    }

    public void createUsersTable() throws ClassNotFoundException, SQLException  {
        String sql = "CREATE TABLE IF NOT EXISTS `users` (" +
                "`id` int(11) UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL, " +
                "`login` varchar(150) NOT NULL, " +
                "`pass` varchar(32) NOT NULL) " +
                "ENGINE=MyISAM DEFAULT CHARSET=utf8;";

        Statement statement = getDbConnection().createStatement();
        statement.executeUpdate(sql);
    }

    public void insertItems(String title, int price, String category) throws ClassNotFoundException, SQLException {
        String sql = "INSERT INTO `items` (`title`, `price`, `category`) VALUES (?, ?, ?);";

        PreparedStatement ps = getDbConnection().prepareStatement(sql);
        ps.setString(1, title);
        ps.setInt(2, price);
        ps.setString(3, category);
        ps.executeUpdate();
    }

    public void insertUsers(String login, String pass) throws ClassNotFoundException, SQLException {
        String sql = "INSERT INTO `users` (`login`, `pass`) VALUES (?, ?);";

        PreparedStatement ps = getDbConnection().prepareStatement(sql);
        ps.setString(1, login);
        ps.setString(2, pass);
        ps.executeUpdate();
    }

    public void insertOrders(int userId, int itemId) throws ClassNotFoundException, SQLException {
        String sql = "INSERT INTO `orders` (`user_id`, `item_id`) VALUES (?, ?);";

        PreparedStatement ps = getDbConnection().prepareStatement(sql);
        ps.setInt(1, userId);
        ps.setInt(2, itemId);
        ps.executeUpdate();
    }

    public void getJonhUsers() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM `users` WHERE `login` = 'john';";

        Statement statement = getDbConnection().createStatement();
        ResultSet rs = statement.executeQuery(sql);

        System.out.println("Users -> john:");
        while (rs.next()) {
            System.out.println(rs.getString("id") + " "
                    + rs.getString("login") + " "
                    + rs.getString("pass"));
        }
        System.out.println("-----------------");
    }

    public void getHatsItems() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM `items` WHERE `category` = 'hats';";

        Statement statement = getDbConnection().createStatement();
        ResultSet rs = statement.executeQuery(sql);

        System.out.println("Items -> hats:");
        while (rs.next()) {
            System.out.println(rs.getString("id") + " "
                    + rs.getString("title") + " "
                    + rs.getString("price") + " "
                    + rs.getString("category"));
        }
        System.out.println("-----------------");
    }

    public void getOrders() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM `orders` " +
                "JOIN `users` ON `users`.`id` = `orders`.`user_id`" +
                "JOIN `items` ON `items`.`id` = `orders`.`item_id`;";

        Statement statement = getDbConnection().createStatement();
        ResultSet rs = statement.executeQuery(sql);

        System.out.println("Orders -> Все заказы: ");
        while (rs.next()) {
            System.out.println(rs.getString("login") + " - "
                    + rs.getString("title"));
        }
        System.out.println("-----------------");
    }
}