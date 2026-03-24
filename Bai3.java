//Phần 1
//JDBC bắt buộc phải gọi registerOutParameter() trước khi thực thi vì
//-JDBC cần biết trước kiểu dữ liệu OUT
// Nếu tham số đầu ra là kiểu DECIMAL trong SQL thì trong Java phải đăng ký bằng hằng số DECIMAL trong lớp Types

//Phần 2
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class Bai3 {
    static final String URL = "jdbc:mysql://localhost:3306/premium_leage";
    static final String USER = "root";
    static final String PASS = "123456";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(URL, USER, PASS);

            System.out.print("Nhập surgery_id: ");
            int surgeryId = sc.nextInt();

            String sql = "{call GET_SURGERY_FEE(?, ?)}";
            CallableStatement cstmt = conn.prepareCall(sql);

            cstmt.setInt(1, surgeryId);

            cstmt.registerOutParameter(2, java.sql.Types.DECIMAL);

            cstmt.execute();

            double cost = cstmt.getDouble(2);

            System.out.println("Chi phí phẫu thuật: " + cost);

            cstmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}