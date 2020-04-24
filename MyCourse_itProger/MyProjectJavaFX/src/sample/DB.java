package sample;

import java.sql.*;

public class DB {

    private final String HOST = "localhost";
    private final String PORT = "3306";
    private final String DB_NAME = "user_reg";
    private final String LOGIN = "root";
    private final String PASS = "root";

    private Connection dbConn = null;

    public static void main (String[] args) throws SQLException, ClassNotFoundException {

        DB db = new DB();
        db.isConnected();
        db.createUsersTable();
        db.createArticlesTable();
        db.insertArticles("Google рулит", "Просто текст для анонса", "Просто текст для анонса...", 10);
        db.insertArticles("Apple рулит", "Просто текст про компанию Apple", "Просто текст про компанию Apple...", 16);

    }

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

    public boolean regUser(String login, String email, String password) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO `users` (`login`, `email`, `password`) VALUES (?, ?, ?);";

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM `users` WHERE `login` = '" + login + "' LIMIT 1");
        if (res.next()) {
            return false;
        }

        PreparedStatement ps = getDbConnection().prepareStatement(sql);
        ps.setString(1, login);
        ps.setString(2, email);
        ps.setString(3, password);
        ps.executeUpdate();
        return true;
    }

    public boolean authUser(String login, String password) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM `users` WHERE `login` = '" + login + "' AND `password` = '" + password + "' LIMIT 1;";

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);
        return res.next();
    }

    public String authUserEmail(String login) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM `users` WHERE `login` = '" + login + "' LIMIT 1;";

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        String email = "";
        while (res.next()) {
            email = res.getString("email");
        }

        return email;
    }

    public boolean isUser(String login) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM `users` WHERE `login` = '" + login + "' LIMIT 1;";

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);
        return res.next();
    }

    public void updateUser(String oldLogin, String newLogin, String email, String password) throws ClassNotFoundException, SQLException {
        String sql = "UPDATE `users` SET `login` = ?, `email` = ?, `password` = ? WHERE `login` = ?";

        PreparedStatement ps = getDbConnection().prepareStatement(sql);
        ps.setString(1, newLogin);
        ps.setString(2, email);
        ps.setString(3, password);
        ps.setString(4, oldLogin);
        ps.executeUpdate();
    }


    public void createUsersTable() throws ClassNotFoundException, SQLException  {
        String sql = "CREATE TABLE IF NOT EXISTS `users` (" +
                "`id` int(11) UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL, " +
                "`login` varchar(100) NOT NULL, " +
                "`email` varchar(100) NOT NULL, " +
                "`password` varchar(100) NOT NULL) " +
                "ENGINE=MyISAM DEFAULT CHARSET=utf8;";

        Statement statement = getDbConnection().createStatement();
        statement.executeUpdate(sql);
    }

    public void createArticlesTable() throws ClassNotFoundException, SQLException  {
        String sql = "CREATE TABLE IF NOT EXISTS `articles` (" +
                "`id` int(11) UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL, " +
                "`title` varchar(150) NOT NULL, " +
                "`intro` varchar(255) NOT NULL, " +
                "`text` text NOT NULL, " +
                "`views` int(3) UNSIGNED NOT NULL) " +
                "ENGINE=MyISAM DEFAULT CHARSET=utf8;";

        Statement statement = getDbConnection().createStatement();
        statement.executeUpdate(sql);
    }

    public void insertArticles(String title, String intro, String text, int views) throws ClassNotFoundException, SQLException {
        String sql = "INSERT INTO `articles` (`title`, `intro`, `text`, `views`) VALUES (?, ?, ?, ?);";

        PreparedStatement ps = getDbConnection().prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, intro);
        ps.setString(3, text);
        ps.setInt(4, views);
        ps.executeUpdate();
    }

    public void addArticle(String title, String intro, String text) throws ClassNotFoundException, SQLException {
        String sql = "INSERT INTO `articles` (`title`, `intro`, `text`, `views`) VALUES (?, ?, ?, ?);";

        PreparedStatement ps = getDbConnection().prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, intro);
        ps.setString(3, text);
        ps.setInt(4, 0);
        ps.executeUpdate();
    }

    public ResultSet getArticles() throws SQLException, ClassNotFoundException {
        String sql = "SELECT `id`, `title`, `intro`, `text` FROM `articles`";
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);
        return  res;
    }
}
