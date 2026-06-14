package me.rukon0621.dialog.api;

/**
 * 버튼 액션 처리 후 클라이언트가 다이얼로그를 어떻게 처리할지 지정합니다.
 */
public enum DialogAfterAction {
    /**
     * 액션 후 다이얼로그를 닫습니다.
     */
    CLOSE,

    /**
     * 액션 후 별도 처리를 하지 않습니다.
     */
    NONE,

    /**
     * 응답 처리를 기다리는 상태로 둡니다.
     */
    WAIT_FOR_RESPONSE
}
