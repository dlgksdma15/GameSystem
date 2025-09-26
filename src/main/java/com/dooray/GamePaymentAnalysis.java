package com.dooray;

import java.sql.*;

public class GamePaymentAnalysis {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/GameSystem";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";


    public void paymentAnalysis(){
        String sql = """
                select 
                    payment_method,
                    sum(amount) as total_amount,
                    count(payment_id) as transcation_count,
                    avg(amount) as average_amount
                from Payments
                group by payment_method
                order by total_amount desc;
                """;

        try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            ResultSet rs = pstmt.executeQuery();

            System.out.println("=== 결제 수단별 분석 리포트 ===");
            while(rs.next()){
                System.out.printf("%s: %d원 (%d건, 평균 %d원)\n",
                        rs.getString("payment_method"),
                        rs.getInt("total_amount"),
                        rs.getInt("transcation_count"),
                        rs.getInt("average_amount")
                );
            }

        } catch (SQLException e){
            System.err.println("오류 발생: " + e.getMessage());
        }
    }
}
