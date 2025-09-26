package com.dooray;

import java.sql.*;

// 기능 3. 게임별 통계 분석
public class GameAnalysis {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/GameSystem";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public void Analysis(){
        String sql = "select " +
                "g.title as game_title, " + // 콤마와 공백 추가
                "avg(s.score) as average_score, max(s.score) as max_score " + // 공백 추가
                "from Scores s " + // 공백 추가
                "join PlayHistories ph on s.play_history_id = ph.play_history_id " + // 공백 추가
                "join Games g on ph.game_id = g.game_id " + // 공백 추가
                "group by g.title " + // 공백 추가
                "order by average_score ASC";

        try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            ResultSet rs = pstmt.executeQuery();
            System.out.println("=== 게임별 통계 분석 ===");
            System.out.println("------------------------------------------------------------------");
            System.out.printf("%-15s %-15s %-15s\n", "game_title", "average_score", "max_score");
            System.out.println("------------------------------------------------------------------");

            while(rs.next()){
                System.out.printf("%-15s %-15.2f %d\n",
                        rs.getString("game_title"),
                        rs.getDouble("average_score"),
                        rs.getInt("max_score")
                );
            }
        } catch (SQLException e) {
            System.err.println("오류 발생: " + e.getMessage());
        }

    }
}
