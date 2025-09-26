package com.dooray;

import java.sql.*;

// 6. 플레이어 프로필 조회
// 기본 정보 조회: 닉네임, 국가, 가입일 표시
// 최근 경기 이력: 가장 최근 3번의 게임 플레이 기록
// 게임 정보 포함: 게임 제목, 플레이 시간, 점수 등
// 한 화면에 모든 정보를 예쁘게 출력
public class GameUserProfile {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/GameSystem";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    // 기본 정보 조회
    public void basicInquiry(String value){
        String sql = "select nickname, country, joined_at from Players " +
                "where nickname = ?";


        try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1,value);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("=== 기본 정보 조회 ===");

            while(rs.next()){
                String nickname = rs.getString("nickname");
                String country = rs.getString("country");
                String joinedAt = rs.getString("joined_at");

                System.out.println("닉네임: " + nickname);
                System.out.println("국가: " + country);
                System.out.println("가입일: " + joinedAt);
                System.out.println();

            }

        }catch (SQLException e){
            System.err.println("연결 중 오류 발생: " + e.getMessage());
        }
    }

    // 최근 경기 이력: 가장 최근 3번의 게임 플레이 기록
    public void history(String value){
        String sql = "select p.nickname, g.title, s.score, s.result, TIMEDIFF(ph.ended_at,ph.started_at) as play_time\n" +
                "from PlayHistories ph\n" +
                "join Players p on ph.player_id = p.player_id\n" +
                "join Games g on ph.game_id = g.game_id\n" +
                "join Scores s on ph.play_history_id = s.play_history_id\n" +
                "where p.nickname = ?\n" +
                "order by ph.started_at desc\n" +
                "limit 3";

        try(Connection conn = DriverManager.getConnection(DB_URL,USER,PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1,value);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("최근 경기 이력: 가장 최근 3번의 게임 플레이 기록 ");
            System.out.println("----------------------------------------------------------------------------");
            System.out.printf("%-15s %-15s %-15s %-15s %s\n", "닉네임", "제목", "점수", "결과", "플레이 시간");
            System.out.println("----------------------------------------------------------------------------");
            while(rs.next()){
                System.out.printf("%-15s %-15s %-15d %-15s %-15s\n",
                        rs.getString("nickname"),
                        rs.getString("title"),
                        rs.getInt("score"),
                        rs.getString("result"),
                        rs.getString("play_time")
                        );
            }
        } catch (SQLException e){
            System.err.println("오류 발생: " + e.getMessage());
        }
    }
}
