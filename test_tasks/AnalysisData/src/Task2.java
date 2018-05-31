import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;

public class Task2 {
    public static void main(String[] args) throws Exception {
        Connection conn = null;
        Statement stmt = null;
        BufferedWriter output = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter out = null;
        String dte = "";
        int sum =0;
        String office = "";
        String offices = "", file_sum_by_dates = "", file_sum_by_offices = "";

        if (args.length > 0) {
            try {
                offices = args[0];
                file_sum_by_dates = args[1];
                file_sum_by_offices = args[2];
            } catch (NumberFormatException e) {
                System.err.println("Проверьте, все типы введеных аргументов коммандной строки");
                System.exit(1);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("Проверьте, все ли аргументы Вы вводите. Формат аргументов: <файл офисов> <выходной файл - сумма по датам> <выходной файл - сумма по офисам>");
                System.exit(1);
            }

        }

        Class.forName("org.h2.Driver");
        conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "qwe123");
        ResultSet rset = conn.getMetaData().getTables(null, null, "OPERATIONS", null);
        stmt = conn.createStatement();
        if (rset.next()) {
            System.out.println("table OPERATIONS is existed");
            String sql_drop = "DROP TABLE OPERATIONS";
            stmt.executeUpdate(sql_drop);
        }
        System.out.println("Create table OPERATIONS...");
        stmt = conn.createStatement();
        String sql = "CREATE TABLE OPERATIONS " +
                "(date_opr varchar(50)," +
                "office varchar(50)," +
                "ost integer)";
        stmt.executeUpdate(sql);
        System.out.println("Table OPERATIONS is created.");


        ArrayList<String> operData = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File(offices));
            while (sc.hasNext()) {
                String line = sc.nextLine();
                operData.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

        for (int i = 0; i < operData.size(); i++) {
            String[] itm = operData.get(i).split(",");
            System.out.println(itm[0]);
            String sql_ins = "INSERT INTO OPERATIONS (date_opr,office,ost) values('" + itm[0] + "','" + itm[1] + "'," + itm[2] + ")";
            stmt.executeUpdate(sql_ins);

        }

        String sql_select_dte = "select trim(substring(date_opr,1,10)) DTE, sum(ost) \"SUM\" from operations group by trim(substring(date_opr,1,10)) " +
                "order by 1";
        ResultSet rs = stmt.executeQuery(sql_select_dte);
        while (rs.next()) {
            dte = rs.getString("DTE");
            sum = rs.getInt("SUM");

            try {
                fw = new FileWriter(file_sum_by_dates, true);
                bw = new BufferedWriter(fw);
                out = new PrintWriter(bw);
                out.println(dte + ' ' + sum);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null)
                    out.close();
            }
        }
        rs.close();

        String sql_select_office = "SELECT office, SUM(ost) \"SUM\" FROM OPERATIONS GROUP BY office order by 1 ";
        ResultSet rs1 = stmt.executeQuery(sql_select_office);
        while (rs1.next()) {
            office = rs1.getString("office");
            sum = rs1.getInt("SUM");

            try {
                fw = new FileWriter(file_sum_by_offices, true);
                bw = new BufferedWriter(fw);
                out = new PrintWriter(bw);
                out.println(office + ' ' + sum);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null)
                    out.close();
            }
        }
        rs1.close();
        conn.close();
    }
}


