//Phần 1
//Giả sử có 1000 bản ghi:
//        | Hoạt động      | Số lần    |
//        | -------------- | ----------|
//        | Parse SQL      |  1000 lần |
//        | Execution Plan |  1000 lần |
//        | Tạo Statement  |  1000 lần |
//Trong khi:
//-Cấu trúc SQL giống hệt nhau
//-Chỉ khác giá trị data

//Phần 2
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

class TestResult {
    private String data;

    public TestResult(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}

public class Bai4 {
    static final String URL = "jdbc:mysql://localhost:3306/premium_leage";
    static final String USER = "root";
    static final String PASS = "123456";

    public static void main(String[] args) {
        List<TestResult> list = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            list.add(new TestResult("Result_" + i));
        }

        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASS);

            String sql = "INSERT INTO Results(data) VALUES(?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            long start = System.currentTimeMillis();

            for (TestResult tr : list) {
                ps.setString(1, tr.getData());
                ps.executeUpdate();
            }

            long end = System.currentTimeMillis();

            System.out.println("Insert thành công");
            System.out.println("Thời gian: " + (end - start) + " ms");

            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//Sự khác biệt về tốc độ:
//        | Tiêu chí       | Statement       | PreparedStatement|
//        | -------------- | --------------- | ---------------- |
//        | Parse SQL      | 1000 lần        | 1 lần            |
//        | Execution Plan | 1000 lần        | 1 lần            |
//        | Tốc độ         | Chậm            |  Nhanh hơn nhiều |
//        | Network        | Nhiều request   | Ít hơn (batch)   |
//        | Bảo mật        | Dễ SQL Injection| An toàn          |
