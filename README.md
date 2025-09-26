## 프로젝트 개요

### 주제: 게임 플레이 관리 시스템 구현

---

- 여러분이 게임 개발 회사에 입사했다고 상상해보세요!
- 첫 번쨰 미션은 플레이어들의 게임 기록과 랭킹을 관리하는 시스템을 만드는 것입니다. 이 시스템을 통해 플레이어들은 자신의 점수를 확인하고, 다른 플레이어들과 순위를 비교할 수 있어야 합니다

### 기술 스택

- Backend: Java + JDBC + MySQL

### 학습 목표

지난 3일간 배운 내용들을 모두 활용해보는 종합 실습입니다.

---

- 지난 3일간 배운 내용들

  1일차

    - MySQL Workbench 사용 방법
    - 데이터베이스 개요
    - 관계 모델과 join
    - DML 상세
    - 인덱스
    - 트랜잭션

  2일차

    - ERD 기초 개념
    - ERD 작성 실습 (MySQL Workbench)
    - 4개 테이블 ERD 설계 및 구현
    - 데이터베이스 정규화
    - 보안, 뷰, 저장프로시저

  3일차

    - JDBC 기초 개념
    - JDBC Type과 Architecture
    - Connection 관리
    - JDBC SQL Exception
    - Statement와 ResultSet
    - PreparedStatement와 SQL Injection
    - JDBC URL 파라미터
    - 트랜잭션과 JDBC
    - Batch Processing - 대용량 데이터 처리
    - 성능 측정하기

---

- SQL 설계: 테이블 관계와 제약조건 이해
- JDBC 프로그래밍: 데이터베이스 연동과 예외 처리
- 트랜잭션: 데이터 일관성 보장
- 보안: SQL Injection 방어
- 성능: 효율적인 쿼리 작성

## 데이터베이스 스키마

프로젝트에서 사용할 테이블 구조는 다음과 같습니다. 이미 설계되어 있으니 이 구조를 기반으로 기능을 구현하면 됩니다!

```sql
-- 플레이어 정보
Players(player_id, nickname, email, joined_at, country)

-- 게임 목록  
Games(game_id, title, genre, release_date)

-- 게임 플레이 기록
PlayHistories(play_history_id, player_id, game_id, started_at, ended_at)

-- 점수 및 결과
Scores(score_id, play_history_id, score, result)

-- 결제 내역
Payments(payment_id, player_id, amount, payment_method, paid_at, item_description)
```

→ 내가 작성한 SQL 쿼리문은 GameSystem/database/schema.sql ← 여기에 있다!

### 스키마 이해하기

- `Players` ↔ `PlayHistories`: 한 플레이어는 여러 경기 참여 (1:N)
- `Games` ↔ `PlayHistories`: 한 게임은 여러 경기로 플레이 (1:N)
- `PlayHistories` ↔ `Scores`: 한 경기는 하나의 점수 기록 (1:1)
- `Players` ↔ `Payments`: 한 플레이어는 여러 결제 내역 (1:N)

---

## 샘플 데이터

과제 실습을 위한 테스트 데이터 입니다. 아래 SQL을 실행하여 데이터를 준비하세요!

### 샘플 데이터 INSERT

```sql
-- Players 데이터 (10명)
INSERT INTO Players (player_id, nickname, email, country) VALUES
(1, '게임마스터', 'master@game.com', 'Korea'),
(2, '프로게이머', 'pro@game.com', 'Korea'),
(3, '초보탈출', 'newbie@game.com', 'Japan'),
(4, '랭킹킹', 'ranking@game.com', 'USA'),
(5, '스피드런', 'speedrun@game.com', 'Korea'),
(6, '전략가', 'strategy@game.com', 'China'),
(7, '캐주얼', 'casual@game.com', 'Korea'),
(8, '하드코어', 'hardcore@game.com', 'Germany'),
(9, '럭키', 'lucky@game.com', 'Korea'),
(10, '챌린저', 'challenger@game.com', 'France');

-- Games 데이터 (5개)
INSERT INTO Games (game_id, title, genre, release_date) VALUES
(1, '배틀로얄 킹', 'Action', '2023-01-15'),
(2, '퍼즐 마스터', 'Puzzle', '2022-06-10'),
(3, '레이싱 챔피언', 'Racing', '2023-03-20'),
(4, 'RPG 어드벤처', 'RPG', '2021-11-05'),
(5, '스포츠 시뮬레이터', 'Sports', '2023-05-12');

-- PlayHistories 데이터 (20경기)
INSERT INTO PlayHistories (play_history_id, player_id, game_id, started_at, ended_at) VALUES
(1, 1, 1, '2024-01-10 14:30:00', '2024-01-10 15:15:00'),
(2, 2, 1, '2024-01-10 15:00:00', '2024-01-10 15:45:00'),
(3, 3, 2, '2024-01-11 10:20:00', '2024-01-11 11:10:00'),
(4, 4, 2, '2024-01-11 11:30:00', '2024-01-11 12:00:00'),
(5, 5, 3, '2024-01-12 16:45:00', '2024-01-12 17:30:00'),
(6, 1, 2, '2024-01-12 18:00:00', '2024-01-12 18:40:00'),
(7, 2, 3, '2024-01-13 13:15:00', '2024-01-13 14:00:00'),
(8, 6, 1, '2024-01-13 19:20:00', '2024-01-13 20:05:00'),
(9, 7, 4, '2024-01-14 12:30:00', '2024-01-14 13:45:00'),
(10, 8, 4, '2024-01-14 14:15:00', '2024-01-14 15:30:00'),
(11, 9, 5, '2024-01-15 11:00:00', '2024-01-15 12:15:00'),
(12, 10, 5, '2024-01-15 16:30:00', '2024-01-15 17:45:00'),
(13, 1, 3, '2024-01-16 20:00:00', '2024-01-16 20:45:00'),
(14, 3, 1, '2024-01-17 15:30:00', '2024-01-17 16:15:00'),
(15, 4, 3, '2024-01-18 14:00:00', '2024-01-18 14:50:00'),
(16, 5, 2, '2024-01-19 10:15:00', '2024-01-19 11:00:00'),
(17, 6, 4, '2024-01-20 17:45:00', '2024-01-20 19:00:00'),
(18, 7, 1, '2024-01-21 13:20:00', '2024-01-21 14:10:00'),
(19, 8, 2, '2024-01-22 11:40:00', '2024-01-22 12:30:00'),
(20, 9, 3, '2024-01-23 16:10:00', '2024-01-23 17:00:00');

-- Scores 데이터 (각 경기별 점수)
INSERT INTO Scores (score_id, play_history_id, score, result) VALUES
(1, 1, 8500, 'WIN'),
(2, 2, 7200, 'LOSE'),
(3, 3, 9500, 'WIN'),
(4, 4, 6800, 'LOSE'),
(5, 5, 12000, 'WIN'),
(6, 6, 8800, 'WIN'),
(7, 7, 11500, 'WIN'),
(8, 8, 7800, 'LOSE'),
(9, 9, 15200, 'WIN'),
(10, 10, 14800, 'WIN'),
(11, 11, 9200, 'WIN'),
(12, 12, 8900, 'LOSE'),
(13, 13, 13500, 'WIN'),
(14, 14, 6500, 'LOSE'),
(15, 15, 10200, 'WIN'),
(16, 16, 9800, 'WIN'),
(17, 17, 16500, 'WIN'),
(18, 18, 7500, 'LOSE'),
(19, 19, 8200, 'WIN'),
(20, 20, 11800, 'WIN');

-- Payments 데이터 (결제 내역)
INSERT INTO Payments (payment_id, player_id, amount, payment_method, item_description) VALUES
(1, 1, 50000, 'CARD', '프리미엄 캐릭터 스킨'),
(2, 2, 25000, 'BANK', '경험치 부스터 팩'),
(3, 3, 15000, 'CARD', '무기 강화 아이템'),
(4, 4, 80000, 'CARD', 'VIP 멤버십 1개월'),
(5, 5, 35000, 'CASH', '레어 장비 세트'),
(6, 1, 30000, 'CARD', '인벤토리 확장'),
(7, 6, 45000, 'BANK', '스페셜 마운트'),
(8, 7, 20000, 'STORE', '게임 패스'),
(9, 8, 60000, 'CARD', '프리미엄 배틀패스'),
(10, 2, 40000, 'BANK', '캐릭터 슬롯 확장'),
(11, 9, 25000, 'CARD', '코스튬 컬렉션'),
(12, 10, 55000, 'CARD', '길드 부스터'),
(13, 3, 15000, 'CASH', '펫 소환권'),
(14, 4, 70000, 'BANK', 'VIP 멤버십 연장'),
(15, 5, 30000, 'CARD', '스킬 리셋 아이템');
```

## 데이터 확인용 쿼리

```sql
-- 데이터 확인
-- === Players ===
SELECT * FROM Players LIMIT 5;

-- === Games ===
SELECT * FROM Games;

-- === Recent Play Histories ===
SELECT ph.play_history_id, p.nickname, g.title, ph.started_at 
FROM PlayHistories ph 
JOIN Players p ON ph.player_id = p.player_id 
JOIN Games g ON ph.game_id = g.game_id 
ORDER BY ph.started_at DESC LIMIT 5;

-- === Top Scores ===
SELECT p.nickname, g.title, s.score, s.result
FROM Scores s
JOIN PlayHistories ph ON s.play_history_id = ph.play_history_id
JOIN Players p ON ph.player_id = p.player_id  
JOIN Games g ON ph.game_id = g.game_id
ORDER BY s.score DESC LIMIT 5;

-- === Payment Summary ===
SELECT payment_method, COUNT(*) as count, SUM(amount) as total
FROM Payments 
GROUP BY payment_method;
```

## 프로젝트 구조

```
GameSystem/
├── src/main/java/com/dooray
│   ├── GameSystem.java (메인 프로그램)
│   ├── DatabaseConfig.java (DB 설정)
│   └── util/
│       └── InputValidator.java (입력 검증 유틸)
├── database/
│   └── schema.sql (테이블 생성 스크립트)
└── README.md
```

## 구현할 기능들

### 기능 1. 게임 플레이 기록 입력

상황: 플레이어가 방금 게임을 끝냈습니다! 이제 그 기록을 데이터베이스에 저장해야 해요.

왜 중요한가요?

게임 플레이 기록은 두 개의 테이블(`PlayHistories`, `Scores`)에 동시에 저장되어야 합니다. 만약 하나의 테이블에만 저장되고 다른 테이블에 저장이 실패한다면? 데이터 불일치가 발생하죠! 이런 상황을 방지하기 위해 트랜잭션을 사용합니다.

구현해야 할 기능:

- 사용자로부터 플레이어 ID, 게임 ID, 시작/종료 시간, 점수, 결과를 입력받기
- `PlayHistories` 테이블에 경기 정보 저장
- `Scores` 테이블에 점수 정보 저장
- 둘 다 성공하거나 둘 다 실패하도록 트랜잭션 처리

구현 팁:

```java
// 트랜잭션 시작
conn.setAutoCommit(false);
try {
    // 1. PlayHistories 테이블에 INSERT
    // 2. Scores 테이블에 INSERT
    conn.commit(); // 모두 성공시 커밋
} catch (SQLException e) {
    conn.rollback(); // 실패시 롤백
}
```

### 기능 2. 플레이어 랭킹 조회

상황: "내가 지금 몇 등이지?" 플레이어들이 가장 궁금해하는 것은 바로 자신의 순위입니다!

왜 중요한가요?

랭킹 시스템은 게임의 핵심 재미 요소입니다. 플레이어들은 자신의 실력을 다른 사람들과 비교하고 싶어하죠. 이 기능을 통해 집계 함수(MAX, AVG)와 정렬(ORDER BY)을 실습할 수 있습니다.

구현해야 할 기능:

- 각 플레이어의 최고 점수와 평균 점수 계산
- 평균 점수를 기준으로 내림차순 정렬하여 랭킹 표시
- 보기 좋게 포맷팅된 랭킹 리스트 출력

### **기능 3. 게임별 통계 분석**

상황: "어떤 게임이 가장 어렵지?" 게임 기획자가 게임별 난이도를 분석하고 싶어합니다!

왜 중요한가요?

게임별 통계를 통해 어떤 게임이 인기가 많은지, 어떤 게임이 어려운지 파악할 수 있습니다. 이는 게임 밸런싱과 신규 게임 기획에 중요한 데이터가 됩니다. JOIN과 집계 함수를 함께 사용하는 실습입니다.

구현해야 할 기능:

- 각 게임별 평균 점수와 최고 점수 계산
- 게임 제목과 함께 보기 좋게 출력
- 평균 점수가 낮은 게임 = 어려운 게임으로 분석 가능!

### **기능 4. 보안 실습 - SQL Injection 차단**

상황: 해커가 여러분의 게임 시스템을 공격하려고 합니다! 로그인 창에 이상한 코드를 입력하고 있어요.

왜 중요한가요?

SQL Injection은 웹 애플리케이션에서 가장 흔한 보안 취약점 중 하나입니다. 실제로 많은 회사들이 이 공격으로 인해 개인정보가 유출되었죠. 개발자라면 반드시 알아야 할 필수 지식입니다!

구현해야 할 기능:

- 취약한 버전: `Statement`를 사용한 로그인 기능
- 안전한 버전: `PreparedStatement`를 사용한 로그인 기능
- 공격 테스트: `admin' OR 1=1 OR nickname='abc` 같은 입력으로 실제 공격 시도
- 두 방식의 결과 비교 및 분석

구현 팁:

```
// 위험한 방법 (절대 사용 금지!)
String sql = "SELECT * FROM Players WHERE nickname='" + userInput + "'";

// 안전한 방법 (권장)
String sql = "SELECT * FROM Players WHERE nickname=?";
```

### **기능 4. 보안 실습 - SQL Injection 차단**

상황: 플레이어가 게임 아이템을 구매했습니다! 이 결제 내역을 기록하고 VIP 고객을 찾아야 합니다.

왜 중요한가요?

결제 내역은 게임 비즈니스의 핵심입니다. 누가 많이 결제하는지 알아야 VIP 혜택을 제공하고, 마케팅 전략을 세울 수 있죠. 데이터 입력 검증과 집계 쿼리 실습입니다.

구현해야 할 기능:

- 결제 내역 등록: 플레이어 ID, 금액, 결제방법, 일시, 구매 아이템 정보 저장
- VIP 고객 찾기: 누적 결제 금액이 높은 순으로 정렬
- 입력 값 검증: 음수 금액, 빈 값 등 잘못된 데이터 방지

### **기능 4. 보안 실습 - SQL Injection 차단**

상황: "이 플레이어가 어떤 사람이지 알고 싶어!" 고객 지원팀에서 플레이어 정보를 요청했습니다.

왜 중요한가요?

게임 운영에서는 플레이어의 활동 패턴을 파악하는 것이 중요합니다. 어떤 게임을 주로 하는지, 언제 마지막으로 접속했는지 등을 통해 개인화된 서비스를 제공할 수 있죠. 복잡한 JOIN과 LIMIT 실습입니다.

구현해야 할 기능:

- 기본 정보 조회: 닉네임, 국가, 가입일 표시
- 최근 경기 이력: 가장 최근 3번의 게임 플레이 기록
- 게임 정보 포함: 게임 제목, 플레이 시간, 점수 등
- 한 화면에 모든 정보를 예쁘게 출력

### **기능 7. 특정 기간 활성 유저 조회**

상황: 마케팅팀에서 특정 기간 동안 활성화된 유저를 분석하고 싶어합니다.

왜 중요한가요?

특정 기간 동안의 활성 유저 분석은 이벤트 효과 측정, 시즌별 유저 행동 분석에 중요합니다. 이를 통해 마케팅 전략을 세우고, 활성 유저들에게 맞춤형 혜택을 제공할 수 있죠.

구현해야 할 기능:

- 2024년 1월 31일 기준으로 최근 2주간(1월 18일~31일) 플레이한 유저 조회
- 각 유저의 해당 기간 총 게임 수, 첫 플레이 일시, 마지막 플레이 일시 출력
- 게임 수가 많은 순으로 정렬

### **기능 8. 결제 수단 분석 대시보드**

상황: CFO가 "어떤 결제 방식이 가장 인기있나요? 월별 매출 보고서에 넣을 데이터가 필요해요!" 라고 요청했습니다.

왜 중요한가요?

결제 수단별 분석은 비즈니스 전략 수립에 핵심적입니다. 어떤 결제 방식이 인기있는지 알면 그 방식에 대한 할인 혜택을 제공하거나, 불편한 결제 방식을 개선할 수 있죠. 그룹별 집계와 보고서 작성 실습입니다.

구현해야 할 기능:

- 결제 수단별 통계: CARD, BANK, CASH, STORE 각각의 사용 현황
- 매출 분석: 각 결제 수단별 총 금액과 거래 건수
- 점유율 계산: 어떤 결제 방식이 가장 인기있는지 분석
- 보기 좋은 보고서 형태로 출력

출력 예시:

```
=== 결제 수단별 분석 리포트 ===
CARD    : 1,250,000원 (45건, 평균 27,778원)
BANK    : 890,000원  (32건, 평균 27,813원)
CASH    : 450,000원  (18건, 평균 25,000원)
STORE   : 320,000원  (12건, 평균 26,667원)
```

---