package com.dooray;

import java.sql.*;

public class GamePeroidCheck {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/GameSystem";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public void RangeSearch(String started, String ended){
        String sql = """
                select 
                    p.nickname,
                    count(ph.play_history_id) as total_games,
                    min(ph.started_at) as first_play,
                    max(ph.ended_at) as last_play
                from PlayHistories ph
                join Players p on ph.player_id = p.player_id
                where ph.started_at between ? and ?
                group by p.player_id, p.nickname
                order by total_games desc;
                """;

        try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1,started);
            pstmt.setString(2,ended);


            ResultSet rs = pstmt.executeQuery();

            System.out.println("=== 특정 기간 활성 유저 조회 ===");
            System.out.println("----------------------------------------------------------------------------");
            System.out.printf("%-15s %-15s %-15s %-15s\n", "닉네임", "총 게임 수", "첫 플레이 일시", "마지막 플레이 일시");
            System.out.println("----------------------------------------------------------------------------");
            while(rs.next()){
                System.out.printf("%-15s %-15d %-15s %-15s\n",
                        rs.getString("nickname"),
                        rs.getInt("total_games"),
                        rs.getString("first_play"),
                        rs.getString("last_play")
                );
            }

        }catch (SQLException e){
            System.out.println("오류 발생: " + e.getMessage());
        }
    }
}
