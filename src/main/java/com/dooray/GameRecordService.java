package com.dooray;

import java.sql.*;
import java.time.LocalDateTime;

// 기능 1.게임 플레이 기록 입력
public class GameRecordService {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/GameSystem";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public void saveGameRecord(int playerId, int gameId, LocalDateTime startTime, LocalDateTime endTime, int score, String result) throws SQLException {
        String insertPlayHistorySQL = "insert into PlayHistories (player_id, game_id, started_at, ended_at) values (?, ?, ?, ?)";
        String insertScoreSQL = "insert into Scores (play_history_id, score, result) values (?,?,?)";

        Connection conn = null;
        try{
            conn = DriverManager.getConnection(DB_URL,USER,PASSWORD);
            // 트랜잭션 시작
            conn.setAutoCommit(false);
            // 1. PlayHistories 테이블에 INSERT
            try(PreparedStatement playStmt = conn.prepareStatement(insertPlayHistorySQL,PreparedStatement.RETURN_GENERATED_KEYS)){
                // RETURN_GENERATED_KEYS를 사용하는 이유
                // Scores에도 동시에 player_id(Foreign Key)필드를 넣어줘야 하므로 PlayHistories의 player_id를 가져오기 위해서이다.
                playStmt.setInt(1,playerId);
                playStmt.setInt(2,gameId);
                playStmt.setObject(3,startTime);
                playStmt.setObject(4,endTime);
                playStmt.executeUpdate();

                int playHistoryId = -1;
                try(ResultSet rs = playStmt.getGeneratedKeys()){
                    if(rs.next()){
                        playHistoryId = rs.getInt(1);
                    }
                }

                // 2. Scores 테이블에 INSERT
                try(PreparedStatement scoreStmt = conn.prepareStatement(insertScoreSQL)){
                    scoreStmt.setInt(1,playHistoryId);
                    scoreStmt.setInt(2,score);
                    scoreStmt.setString(3,result);
                    scoreStmt.executeUpdate();
                }

            }
            conn.commit();
            System.out.println("게임 기록이 성공적으로 저장되었습니다.");
        } catch (SQLException e){
            if(conn != null){
                try{
                    System.out.println("데이터베이스 오류 발생, 롤백합니다.");
                    conn.rollback(); // 실패시 롤백
                } catch (SQLException ex){
                    System.err.println("롤백 중 오류 발생" + ex.getMessage());
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // 원래의 상태로 되돌림
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("연결 종료 중 오류 발생: " + e.getMessage());
                }
            }
        }

    }
}
