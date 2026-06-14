package me.rukon0621.dialog.api.exception;

/**
 * 다이얼로그 API 모델이 Paper Dialog로 변환되기 전에 유효하지 않을 때 발생합니다.
 */
public class DialogValidationException extends IllegalArgumentException {

    public DialogValidationException(String message) {
        super(message);
    }
}
