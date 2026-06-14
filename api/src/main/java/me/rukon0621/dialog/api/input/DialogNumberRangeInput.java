package me.rukon0621.dialog.api.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.rukon0621.dialog.api.util.AdventureUtil;
import me.rukon0621.dialog.api.util.DialogValidator;
import net.kyori.adventure.text.Component;

/**
 * 숫자 범위를 슬라이더로 입력받는 요소입니다.
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class DialogNumberRangeInput implements DialogInputPart {

    /**
     * 응답 데이터에서 사용할 입력 key입니다.
     */
    private String key;

    /**
     * 슬라이더 위에 표시될 라벨입니다.
     */
    private Component label = Component.empty();

    /**
     * 슬라이더 폭입니다. 렌더링 시 Paper 허용 범위로 보정됩니다.
     */
    private int width = 200;

    /**
     * 값 표시 형식입니다. 기본값은 바닐라 옵션 값 형식입니다.
     */
    private String labelFormat = "options.generic_value";

    /**
     * 슬라이더 최소값입니다.
     */
    private float start;

    /**
     * 슬라이더 최대값입니다.
     */
    private float end = 1;

    /**
     * 초기값입니다. null이면 클라이언트 기본값을 사용합니다.
     */
    private Float initial;

    /**
     * 증감 단위입니다. null이면 클라이언트 기본값을 사용합니다.
     */
    private Float step;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final DialogNumberRangeInput input = new DialogNumberRangeInput();

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

        public Builder labelFormat(String labelFormat) {
            input.setLabelFormat(labelFormat);
            return this;
        }

        /**
         * 슬라이더 최소값과 최대값을 설정합니다.
         */
        public Builder range(float start, float end) {
            input.setStart(start);
            input.setEnd(end);
            return this;
        }

        public Builder initial(Float initial) {
            input.setInitial(initial);
            return this;
        }

        public Builder step(Float step) {
            input.setStep(step);
            return this;
        }

        /**
         * 현재 설정값으로 숫자 범위 입력을 생성합니다.
         */
        public DialogNumberRangeInput build() {
            DialogValidator.validate(input);
            return input;
        }
    }
}
