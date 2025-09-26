package com.dooray.util;

import java.util.Scanner;

public class InputValidator {

    /**
     * 사용자로부터 정수형 입력을 받고 유효성을 검증합니다.
     * 유효하지 않은 입력인 경우, 계속해서 재입력을 요청합니다.
     *
     * @param scanner 입력을 받을 Scanner 객체
     * @return 유효한 정수 값
     */
    public static int readInt(Scanner scanner) {
        while (true) {
            try {
                if (scanner.hasNextInt()) {
                    int value = scanner.nextInt();
                    if (value < 0) { // 음수 검증 추가
                        System.out.println("금액은 음수가 될 수 없습니다. 다시 입력해주세요.");
                        continue; // 음수일 경우 다시 루프 시작
                    }
                    scanner.nextLine();
                    return value;
                } else {
                    System.out.println("유효한 정수를 입력해주세요.");
                    scanner.next();
                }
            } catch (Exception e) {
                System.out.println("입력 처리 중 오류가 발생했습니다: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }
    /**
     * 사용자로부터 문자열 입력을 받고 유효성을 검증합니다.
     *
     * @param scanner 입력을 받을 Scanner 객체
     * @return 공백이 아닌 유효한 문자열
     */
    public static String readString(Scanner scanner) {
        String input = null;
        while (true) {
            try {
                input = scanner.nextLine();
                if (input != null && !input.trim().isEmpty()) {
                    return input;
                } else {
                    System.out.println("유효한 문자열을 입력해주세요.");
                }
            } catch (Exception e) {
                System.out.println("입력 처리 중 오류가 발생했습니다: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }
}