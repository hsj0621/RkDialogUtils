package me.rukon0621.dialog.api.input;

/**
 * 플레이어 입력 요소의 공통 타입입니다.
 */
public interface DialogInputPart {

    /**
     * 클라이언트 응답 데이터에서 이 입력값을 식별할 key입니다.
     */
    String getKey();
}
