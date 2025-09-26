package com.dooray;

import java.sql.*;


public class GameRankingSearchService {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/GameSystem";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public void RankingSearch(){

        String sql = "SELECT p.nickname, g.title, MAX(s.score) AS max_score, AVG(s.score) AS avg_score " +
                "FROM Scores s " +
                "JOIN PlayHistories ph ON s.play_history_id = ph.play_history_id " +
                "JOIN Players p ON ph.player_id = p.player_id " +
                "JOIN Games g ON ph.game_id = g.game_id " +
                "GROUP BY p.nickname, g.title " +
                "ORDER BY avg_score DESC";
        try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            System.out.println("=== 플레이어 랭킹 조회 ===");
            System.out.println("------------------------------------------------------------------");
            System.out.printf("%-15s %-15s %-15s %-15s\n", "닉네임", "게임", "최고점수", "평균점수");
            System.out.println("------------------------------------------------------------------");
            while(rs.next()){
                // 데이터 타입을 double로 수정하고, 보기 좋게 포맷팅
                System.out.printf("%-15s %-15s %-15d %.2f\n",
                        rs.getString("nickname"),
                        rs.getString("title"),
                        rs.getInt("max_score"),
                        rs.getDouble("avg_score")
                );
            }
        } catch (SQLException e) {
            System.err.println("오류 발생: " + e.getMessage());
        }
    }
}
