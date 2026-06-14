package me.rukon0621.dialog.api.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.rukon0621.dialog.api.util.AdventureUtil;
import me.rukon0621.dialog.api.util.DialogValidator;
import net.kyori.adventure.text.Component;

/**
 * 한 줄 또는 여러 줄 문자열을 받는 텍스트 입력입니다.
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class DialogTextInput implements DialogInputPart {

    /**
     * 응답 데이터에서 사용할 입력 key입니다.
     */
    private String key;

    /**
     * 입력칸 위에 표시될 라벨입니다.
     */
    private Component label = Component.empty();

    /**
     * 입력칸 폭입니다. 렌더링 시 Paper 허용 범위로 보정됩니다.
     */
    private int width = 200;

    /**
     * 라벨 표시 여부입니다.
     */
    private boolean labelVisible = true;

    /**
     * 입력칸의 초기 문자열입니다.
     */
    private String initial = "";

    /**
     * 입력 가능한 최대 문자 수입니다.
     */
    private int maxLength = 32;

    /**
     * null이면 한 줄 입력, 값이 있으면 여러 줄 입력으로 표시됩니다.
     */
    private DialogTextMultilineOptions multiline;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final DialogTextInput input = new DialogTextInput();

        /**
         * 응답 식별용 key를 설정합니다.
         */
        public Builder key(String key) {
            input.setKey(key);
            return this;
        }

        /**
         * MiniMessage 형식의 라벨을 설정합니다.
         */
        public Builder label(String label) {
            input.setLabel(AdventureUtil.toComponent(label));
            return this;
        }

        public Builder label(Component label) {
            input.setLabel(label);
            return this;
        }

        public Builder width(int width) {
            input.setWidth(width);
            return this;
        }

        public Builder labelVisible(boolean labelVisible) {
            input.setLabelVisible(labelVisible);
            return this;
        }

        public Builder initial(String initial) {
            input.setInitial(initial);
            return this;
        }

        public Builder maxLength(int maxLength) {
            input.setMaxLength(maxLength);
            return this;
        }

        public Builder multiline(DialogTextMultilineOptions multiline) {
            input.setMultiline(multiline);
            return this;
        }

        /**
         * 현재 설정값으로 텍스트 입력을 생성합니다.
         */
        public DialogTextInput build() {
            DialogValidator.validate(input);
            return input;
        }
    }
}
