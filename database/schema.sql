
-- 1.ERD 직접 그려보기
-- -----------------------------------------------------
-- Table `Players`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Players` (
    `player_id` INT NOT NULL AUTO_INCREMENT,
    `nickname` VARCHAR(30) NULL,
    `email` VARCHAR(100) NULL,
    `joined_at` DATETIME NULL,
    `country` VARCHAR(50) NULL,
    PRIMARY KEY (`player_id`))
    ENGINE = InnoDB; -- 테이블에 사용할 스토리지 엔진을 지정하는 명령어이다.
show tables;
# InnoDB의 주요 특징:
# 트랜잭션(Transaction) 지원: ACID 속성(원자성, 일관성, 고립성, 지속성)을 준수하여 데이터의 신뢰성을 보장합니다. 여러 작업이 하나의 단위로 묶여 모두 성공하거나 모두 실패하도록 합니다.
# 외래 키(Foreign Key) 지원: ON DELETE와 ON UPDATE 같은 참조 무결성 제약 조건을 지원하여 데이터의 일관성을 유지합니다.
# 행 단위 잠금(Row-level Locking): 데이터 수정 시, 테이블 전체가 아닌 해당 행만 잠궈서 여러 사용자가 동시에 데이터를 효율적으로 수정할 수 있게 합니다.


-- -----------------------------------------------------
-- Table `Payments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Payments` (
    `payment_id` INT NOT NULL AUTO_INCREMENT,
    `player_id` INT NOT NULL,
    `amount` INT NULL,
    `payment_method` ENUM('CARD', 'BANK', 'CASH', 'STORE') NULL,
    `paid_at` DATETIME NULL,
    `item_description` VARCHAR(100) NULL,
    PRIMARY KEY (`payment_id`),
    CONSTRAINT `fk_Payments_Players1`
    FOREIGN KEY (`player_id`)
    REFERENCES `Players` (`player_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;
show tables;
desc Payments;
CREATE INDEX `fk_Payments_Players1_idx` ON `Payments` (`player_id` ASC) VISIBLE;

-- ON DELETE NO ACTION
-- 부모 테이블의 레코드를 삭제하려는 시도가 있을 때, 자식 테이블에 해당 부모를 참조하는
-- 레코드가 존재하면 삭제를 허용하지 않습니다. (참조 무결성 유지 하기 위해 씀.)

-- ON UPDATE NO ACTION
-- 자식 테이블의 레코드가 참조하는 **부모 테이블의 기본 키(Primary Key)**가 업데이트될 때의 동작을 지정합니다. (참조 무결성 유지)
-- -----------------------------------------------------
-- Table `Games`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Games` (
    `game_id` INT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(100) NULL,
    `genre` VARCHAR(50) NULL,
    `release_date` DATE NULL,
    PRIMARY KEY (`game_id`))
    ENGINE = InnoDB;
#
#
-- -----------------------------------------------------
-- Table `PlayHistories`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PlayHistories` (
    `play_history_id` INT NOT NULL AUTO_INCREMENT,
    `player_id` INT NOT NULL,
    `game_id` INT NOT NULL,
    `started_at` DATETIME NULL,
    `ended_at` DATETIME NULL,
    PRIMARY KEY (`play_history_id`),
    CONSTRAINT `fk_PlayHistories_Players1`
    FOREIGN KEY (`player_id`)
    REFERENCES `Players` (`player_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_PlayHistories_Games1`
    FOREIGN KEY (`game_id`)
    REFERENCES `Games` (`game_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;
#
CREATE INDEX `fk_PlayHistories_Players1_idx` ON `PlayHistories` (`player_id` ASC) VISIBLE;
#
CREATE INDEX `fk_PlayHistories_Games1_idx` ON `PlayHistories` (`game_id` ASC) VISIBLE;
#
-- -----------------------------------------------------
-- Table `Scores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Scores` (
    `score_id` INT NOT NULL AUTO_INCREMENT,
    `play_history_id` INT NOT NULL,
    `score` INT NULL,
    `result` ENUM('WIN', 'LOSE', 'DRAW') NULL,
    PRIMARY KEY (`score_id`),
    CONSTRAINT `fk_Scores_PlayHistories1`
    FOREIGN KEY (`play_history_id`)
    REFERENCES `PlayHistories` (`play_history_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;

CREATE INDEX `fk_Scores_PlayHistories1_idx` ON `Scores` (`play_history_id` ASC) VISIBLE;

show tables;

SHOW INDEX FROM Players;
SHOW INDEX FROM Payments;

# SET SQL_MODE=@OLD_SQL_MODE;
# SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
# SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


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

select * from Players;

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

select * from PlayHistories;
select * from Payments;
select * from Players;
select * from Games;
select * from Scores;

-- 기능 2. 플레이어 랭킹 조회
SELECT
    p.nickname,  -- 플레이어 닉네임
    g.title,     -- 게임 제목
    MAX(s.score) AS max_score,  -- 최고 점수
    AVG(s.score) AS avg_score   -- 평균 점수
FROM Scores s
         JOIN PlayHistories ph ON s.play_history_id = ph.play_history_id
         JOIN Players p ON ph.player_id = p.player_id
         JOIN Games g ON ph.game_id = g.game_id
GROUP BY p.nickname, g.title
ORDER BY avg_score DESC;

-- 기능 3. 게임별 통계 분석
SELECT
    g.title AS game_title,
    AVG(s.score) AS average_score,
    MAX(s.score) AS max_score
FROM Scores s
         JOIN PlayHistories ph ON s.play_history_id = ph.play_history_id
         JOIN Games g ON ph.game_id = g.game_id
GROUP BY g.title
ORDER BY average_score ASC;

-- 기능 5. 누적금액 높은순 찾기
select
    p.nickname,
    sum(pm.amount) as total_payment
from Payments pm
join Players p on pm.player_id = p.player_id
group by p.player_id, p.nickname
order by total_payment desc;

-- 기능 6. 플레이어 프로필 조회

select nickname,country,joined_at from Players
where nickname = '게임마스터';


-- 회원 정보에 날짜 업데이트
UPDATE Players
SET joined_at = '2024-01-01 10:00:00'
WHERE player_id = 1;

UPDATE Players
SET joined_at = '2024-01-02 10:00:00'
WHERE player_id = 2;

UPDATE Players
SET joined_at = '2024-01-03 10:00:00'
WHERE player_id = 3;

UPDATE Players
SET joined_at = '2024-01-04 10:00:00'
WHERE player_id = 4;

UPDATE Players
SET joined_at = '2024-01-05 10:00:00'
WHERE player_id = 5;

UPDATE Players
SET joined_at = '2024-01-06 10:00:00'
WHERE player_id = 6;

UPDATE Players
SET joined_at = '2024-01-07 10:00:00'
WHERE player_id = 7;

UPDATE Players
SET joined_at = '2024-01-08 10:00:00'
WHERE player_id = 8;


UPDATE Players
SET joined_at = '2024-01-09 10:00:00'
WHERE player_id = 9;

UPDATE Players
SET joined_at = '2024-01-10 10:00:00'
WHERE player_id = 10;

-- 최근 경기 이력: 가장 최근 3번의 게임 플레이 기록
select p.nickname, g.title, s.score, s.result, TIMEDIFF(ph.ended_at,ph.started_at) as play_time
from PlayHistories ph
join Players p on ph.player_id = p.player_id
join Games g on ph.game_id = g.game_id
join Scores s on ph.play_history_id = s.play_history_id
where p.nickname = '게임마스터'
order by ph.started_at desc
limit 3;

-- 기능7. 특정 기간 활성 유저 조회
select
    p.nickname,
    count(ph.play_history_id) as total_games,
    min(ph.started_at) as first_play,
    max(ph.ended_at) as last_play
from PlayHistories ph
join Players p on ph.player_id = p.player_id
where ph.started_at between '2024-01-01 00:00:00' AND '2024-02-01 00:00:00'
group by p.player_id, p.nickname
order by total_games desc;


-- 기능8. 결제 수단 분석 대시보드
select
    payment_method,
    sum(amount) as total_amount,
    count(payment_id) as transcation_count,
    avg(amount) as average_amount
from Payments
group by payment_method
order by total_amount desc;

