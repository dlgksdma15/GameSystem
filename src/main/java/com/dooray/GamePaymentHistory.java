package com.dooray;

import java.sql.*;
import java.time.LocalDateTime;

// 기능 5. 결제 내역 관리 시스템
public class GamePaymentHistory {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/GameSystem";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";
    // 결제 내역 등록

//    결제 내역 등록: 플레이어 ID, 금액, 결제방법, 일시, 구매 아이템 정보 저장
//    VIP 고객 찾기: 누적 결제 금액이 높은 순으로 정렬
//    입력 값 검증: 음수 금액, 빈 값 등 잘못된 데이터 방지


    public void register(int playerId, int amount, String payment_method, LocalDateTime paid_at,String item_description){

        String sql = "insert into Payments (player_id, amount, payment_method, paid_at, item_description) " +
                "values (?,?,?,?,?)";

        try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1,playerId);
            pstmt.setInt(2,amount);
            pstmt.setString(3,payment_method);
            pstmt.setObject(4,paid_at);
            pstmt.setString(5,item_description);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("연결 중 오류 발생: " + e.getMessage());
        }
    }

    // VIP 고객 찾기
    public void vipSearch(){
        String sql = "select p.nickname,sum(pm.amount) as total_payment " +
                "from Payments pm " +
                "join Players p on pm.player_id = p.player_id " +
                "group by p.player_id, p.nickname " +
                "order by total_payment desc";

        try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            ResultSet rs = pstmt.executeQuery();
            System.out.println("=== VIP 고객 찾기 ===");
            System.out.printf("%-15s %-15s\n","nickname","total_payment");
            System.out.println("--------------------------------------------");
            while(rs.next()){
                System.out.printf("%-15s %-15d\n",
                        rs.getString("nickname"),
                        rs.getInt("total_payment")
                        );
            }

        } catch (SQLException e){
            System.err.println("오류 발생: " + e.getMessage());
        }
    }
}
