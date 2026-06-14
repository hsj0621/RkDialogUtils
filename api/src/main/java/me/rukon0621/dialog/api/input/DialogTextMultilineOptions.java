package me.rukon0621.dialog.api.input;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 텍스트 입력을 여러 줄로 표시할 때 사용하는 옵션입니다.
 */
@Getter
@AllArgsConstructor(staticName = "of")
public class DialogTextMultilineOptions {

    /**
     * 입력 가능한 최대 줄 수입니다. null이면 클라이언트 기본값을 사용합니다.
     */
    private Integer maxLines;

    /**
     * 입력칸 높이입니다. null이면 클라이언트 기본값을 사용합니다.
     */
    private Integer height;
}
