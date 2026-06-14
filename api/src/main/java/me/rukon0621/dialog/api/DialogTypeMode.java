package me.rukon0621.dialog.api;

/**
 * Paper Dialog 하단 영역 타입 선택 방식입니다.
 */
public enum DialogTypeMode {
    /**
     * 버튼 개수에 따라 notice, confirmation, multiAction을 자동 선택합니다.
     */
    AUTO,

    /**
     * 버튼 하나짜리 notice 타입으로 렌더링합니다.
     */
    NOTICE,

    /**
     * 버튼 두 개짜리 confirmation 타입으로 렌더링합니다.
     */
    CONFIRMATION,

    /**
     * 여러 버튼을 배치하는 multiAction 타입으로 렌더링합니다.
     */
    MULTI_ACTION
}
