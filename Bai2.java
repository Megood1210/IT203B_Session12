// Phần 1
//Các phương thức setDouble(), setInt() của PreparedStatement lại giúp lập trình viên không cần lo lắng về định dạng
//dấu chấm hay dấu phẩy của hệ điều hành vì:
//-ps.setDouble(1, temp); Không phụ thuộc vào định dạng chuỗi
//-setDouble() đảm bảo dữ liệu là double hợp lệ, setInt() đảm bảo dữ liệu là int hợp lệ

//Phần 2
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Bai2 {
    static final String URL = "jdbc:mysql://localhost:3306/premium_leage";
    static final String USER = "root";
    static final String PASS = "123456";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASS);

            System.out.print("Nhập ID bệnh nhân: ");
            int patientId = sc.nextInt();

            System.out.print("Nhập nhiệt độ: ");
            double temp = sc.nextDouble();

            System.out.print("Nhập nhịp tim: ");
            int heartRate = sc.nextInt();

            String sql = "UPDATE Vitals SET temperature = ?, heart_rate = ? WHERE p_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setDouble(1, temp);
            ps.setInt(2, heartRate);
            ps.setInt(3, patientId);

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Cập nhật thành công");
            } else {
                System.out.println("Không tìm thấy bệnh nhân");
            }

            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}