package com.dooray;

import com.dooray.util.InputValidator;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Scanner;

// 메인 프로그램
public class GameSystem {
    public static void main(String[] args) throws SQLException {
//        // 기능 1. 게임 플레이 기록 입력
//        GameRecordService gameRecordService = new GameRecordService();
//        Scanner sc = new Scanner(System.in);
//
//        System.out.print("플레이어 ID:");
//        int playerId = InputValidator.readInt(sc);
//
//        System.out.print("게임 ID:");
//        int gameId = InputValidator.readInt(sc);
//
//        LocalDateTime startTime = LocalDateTime.now(); // 시작 시간
//
//        LocalDateTime endTime = LocalDateTime.now(); // 종료 시간
//
//        System.out.print("점수:");
//        int score = InputValidator.readInt(sc);
//
//        System.out.print("결과:");
//        String result = InputValidator.readString(sc);
//
//        gameRecordService.saveGameRecord(playerId,gameId,startTime,endTime,score,result);

//        // 기능 2. 플레이어 랭킹 조회
//        GameRankingSearchService gameRankingSearchService = new GameRankingSearchService();
//        gameRankingSearchService.RankingSearch();

//        // 기능 3. 게임별 통계 분석
//        GameAnalysis gameAnalysis = new GameAnalysis();
//        gameAnalysis.Analysis();

//        // 기능 5. 결제 내역 관리 시스템
//        GamePaymentHistory gamePaymentHistory = new GamePaymentHistory();
//        Scanner sc = new Scanner(System.in);
//
//        System.out.print("플레이어 ID: ");
//        int playerId = InputValidator.readInt(sc);
//
//        System.out.print("결제 금액: ");
//        int amount = InputValidator.readInt(sc);
//
//        System.out.print("결제 방법: ");
//        String payment_method = InputValidator.readString(sc);
//
//        LocalDateTime paid_at = LocalDateTime.now(); // 결제 시간
//
//        System.out.print("구매 아이템 정보: ");
//        String item_description = InputValidator.readString(sc);
//
//        gamePaymentHistory.register(playerId,amount,payment_method,paid_at,item_description);
//
//        gamePaymentHistory.vipSearch();

//        // 기능 6. 플레이어 조회
//        GameUserProfile gameUserProfile = new GameUserProfile();
//        gameUserProfile.basicInquiry("게임마스터");
//        gameUserProfile.history("게임마스터");

//        // 기능 7. 특정 기간 활성 유저 조회
//        GamePeroidCheck gamePeroidCheck = new GamePeroidCheck();
//        gamePeroidCheck.RangeSearch("2024-01-01 00:00:00","2024-02-01 00:00:00");

        // 기능 8. 결제 수단 분석 대시보드
        GamePaymentAnalysis gamePaymentAnalysis = new GamePaymentAnalysis();
        gamePaymentAnalysis.paymentAnalysis();



    }
}
