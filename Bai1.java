//Phần 1
//PreparedStatement lại được coi là "tấm khiên" chống lại SQL Injection vì:
//-Không code không nối chuỗi SQL trực tiếp
//-SQL được compile trước
//-Input không thể chèn vào cấu trúc câu lệnh

//Phần 2
import java.sql.PreparedStatement;
import java.sql.ResultSet;
String sql = "SELECT * FROM Doctors WHERE code = ? AND pass = ?";

PreparedStatement ps = conn.prepareStatement(sql);


ps.setString(1, code);
ps.setString(2, pass);


ResultSet rs = ps.executeQuery();

if (rs.next()) {
        System.out.println("Đăng nhập thành công");
} else {
        System.out.println("Sai mã hoặc mật khẩu");
}