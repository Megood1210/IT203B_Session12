import java.sql.*;
import java.util.Scanner;

public class Bai5 {
    static final String URL = "jdbc:mysql://localhost:3306/premium_leage";
    static final String USER = "root";
    static final String PASS = "123456";

    static void listPatients(Connection conn) throws Exception {
        String sql = "SELECT id, name, age, department FROM Patients";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        System.out.println("\n--- DANH SÁCH ---");
        while (rs.next()) {
            System.out.println(rs.getInt("id") + " | " + rs.getString("name") + " | " + rs.getInt("age") + " | " + rs.getString("department"));
        }
    }

    static void addPatient(Connection conn, Scanner sc) throws Exception {
        String sql = "INSERT INTO Patients(name, age, department, disease, admission_date) VALUES(?, ?, ?, ?, NOW())";
        PreparedStatement ps = conn.prepareStatement(sql);

        System.out.print("Tên: ");
        String name = sc.nextLine();

        System.out.print("Tuổi: ");
        int age = sc.nextInt();
        sc.nextLine();

        System.out.print("Khoa: ");
        String dept = sc.nextLine();

        System.out.print("Bệnh lý: ");
        String disease = sc.nextLine();

        ps.setString(1, name);
        ps.setInt(2, age);
        ps.setString(3, dept);
        ps.setString(4, disease);

        ps.executeUpdate();
        System.out.println("Thêm thành công");
    }

    static void updateDisease(Connection conn, Scanner sc) throws Exception {
        String sql = "UPDATE Patients SET disease = ? WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);

        System.out.print("Nhập ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Bệnh lý mới: ");
        String disease = sc.nextLine();

        ps.setString(1, disease);
        ps.setInt(2, id);

        int rows = ps.executeUpdate();

        if (rows > 0)
            System.out.println("Cập nhật thành công");
        else
            System.out.println("Không tìm thấy bệnh nhân");
    }

    static void dischargePatient(Connection conn, Scanner sc) throws Exception {
        String sql = "{call CALCULATE_DISCHARGE_FEE(?, ?)}";
        CallableStatement cs = conn.prepareCall(sql);

        System.out.print("Nhập ID bệnh nhân: ");
        int id = sc.nextInt();

        cs.setInt(1, id);
        cs.registerOutParameter(2, java.sql.Types.DECIMAL);

        cs.execute();

        double fee = cs.getDouble(2);
        System.out.println("Tổng viện phí: " + fee);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {

            while (true) {
                System.out.println("\n===== MENU =====");
                System.out.println("1. Danh sách bệnh nhân");
                System.out.println("2. Tiếp nhận bệnh nhân");
                System.out.println("3. Cập nhật bệnh án");
                System.out.println("4. Xuất viện & Tính phí");
                System.out.println("5. Thoát");
                System.out.print("Chọn: ");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        listPatients(conn);
                        break;
                    case 2:
                        addPatient(conn, sc);
                        break;
                    case 3:
                        updateDisease(conn, sc);
                        break;
                    case 4:
                        dischargePatient(conn, sc);
                        break;
                    case 5:
                        System.out.println("Thoát chương trình");
                        return;
                    default:
                        System.out.println("Lựa chọn không hợp lệ");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}