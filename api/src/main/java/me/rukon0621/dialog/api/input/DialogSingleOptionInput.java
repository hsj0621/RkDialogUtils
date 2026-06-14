package me.rukon0621.dialog.api.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.rukon0621.dialog.api.util.AdventureUtil;
import me.rukon0621.dialog.api.util.DialogValidator;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 여러 선택지 중 하나를 고르는 입력입니다.
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class DialogSingleOptionInput implements DialogInputPart {

    /**
     * 응답 데이터에서 사용할 입력 key입니다.
     */
    private String key;

    /**
     * 선택지 묶음 위에 표시될 라벨입니다.
     */
    private Component label = Component.empty();

    /**
     * 플레이어가 고를 수 있는 선택지 목록입니다.
     */
    private List<DialogOptionEntry> options = new ArrayList<>();

    /**
     * 입력 폭입니다. 렌더링 시 Paper 허용 범위로 보정됩니다.
     */
    private int width = 200;

    /**
     * 라벨 표시 여부입니다.
     */
    private boolean labelVisible = true;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final DialogSingleOptionInput input = new DialogSingleOptionInput();

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

        public Builder option(String id, String display) {
            input.getOptions().add(DialogOptionEntry.of(id, display));
            return this;
        }

        /**
         * MiniMessage 형식의 표시 이름과 최초 선택 여부로 선택지를 추가합니다.
         */
        public Builder option(String id, String display, boolean initial) {
            input.getOptions().add(DialogOptionEntry.of(id, display, initial));
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

        /**
         * 현재 설정값으로 단일 선택 입력을 생성합니다.
         */
        public DialogSingleOptionInput build() {
            input.setOptions(new ArrayList<>(input.getOptions()));
            DialogValidator.validate(input);
            return input;
        }
    }
}
