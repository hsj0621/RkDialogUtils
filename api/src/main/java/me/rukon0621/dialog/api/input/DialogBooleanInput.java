package me.rukon0621.dialog.api.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.rukon0621.dialog.api.util.AdventureUtil;
import me.rukon0621.dialog.api.util.DialogValidator;
import net.kyori.adventure.text.Component;

/**
 * true/false 값을 받는 체크박스 입력입니다.
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class DialogBooleanInput implements DialogInputPart {

    /**
     * 응답 데이터에서 사용할 입력 key입니다.
     */
    private String key;

    /**
     * 체크박스 옆에 표시될 라벨입니다.
     */
    private Component label = Component.empty();

    /**
     * 최초 체크 상태입니다.
     */
    private boolean initial;

    /**
     * 체크된 상태일 때 전송될 문자열 값입니다.
     */
    private String onTrue = "true";

    /**
     * 체크 해제 상태일 때 전송될 문자열 값입니다.
     */
    private String onFalse = "false";

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final DialogBooleanInput input = new DialogBooleanInput();

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

        public Builder initial(boolean initial) {
            input.setInitial(initial);
            return this;
        }

        public Builder onTrue(String onTrue) {
            input.setOnTrue(onTrue);
            return this;
        }

        public Builder onFalse(String onFalse) {
            input.setOnFalse(onFalse);
            return this;
        }

        public DialogBooleanInput build() {
            DialogValidator.validate(input);
            return input;
        }
    }
}
