package me.rukon0621.dialog.api;

/**
 * 버튼 클릭 시 함께 전송된 다이얼로그 입력값을 읽는 API입니다.
 */
public interface DialogResponse {

    /**
     * 텍스트 입력 또는 단일 선택 입력 값을 읽습니다.
     *
     * @param key 입력 요소에 지정한 key
     * @return 입력값이 없으면 null
     */
    String getText(String key);

    /**
     * 체크박스 입력 값을 읽습니다.
     *
     * @param key 입력 요소에 지정한 key
     * @return 입력값이 없으면 null
     */
    Boolean getBoolean(String key);

    /**
     * 숫자 범위 입력 값을 읽습니다.
     *
     * @param key 입력 요소에 지정한 key
     * @return 입력값이 없으면 null
     */
    Float getFloat(String key);
}
