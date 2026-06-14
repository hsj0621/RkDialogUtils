package me.rukon0621.dialog.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.rukon0621.dialog.api.util.AdventureUtil;
import me.rukon0621.dialog.api.util.DialogValidator;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 다이얼로그 하단에 표시되는 버튼 모델입니다.
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class DialogButton {

    /**
     * 버튼에 표시될 텍스트입니다.
     */
    private Component text = Component.empty();

    /**
     * 버튼에 마우스를 올렸을 때 표시될 설명입니다.
     */
    private Component tooltip;

    /**
     * 버튼 폭입니다. 렌더링 시 Paper 허용 범위로 보정됩니다.
     */
    private int width = 150;

    /**
     * 버튼 클릭 시 플레이어만 받아 실행할 동작입니다.
     */
    private Consumer<Player> action;

    /**
     * 버튼 클릭 시 플레이어와 입력 응답을 함께 받아 실행할 동작입니다.
     */
    private BiConsumer<Player, DialogResponse> responseAction;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final DialogButton button = new DialogButton();

        /**
         * MiniMessage 형식의 버튼 텍스트를 설정합니다.
         */
        public Builder text(String text) {
            button.setText(AdventureUtil.toComponent(text));
            return this;
        }

        public Builder text(Component text) {
            button.setText(text);
            return this;
        }

        /**
         * MiniMessage 형식의 툴팁을 설정합니다.
         */
        public Builder tooltip(String tooltip) {
            button.setTooltip(AdventureUtil.toComponent(tooltip));
            return this;
        }

        public Builder tooltip(Component tooltip) {
            button.setTooltip(tooltip);
            return this;
        }

        public Builder width(int width) {
            button.setWidth(width);
            return this;
        }

        /**
         * 버튼을 누른 플레이어만 받아 실행할 동작을 설정합니다.
         */
        public Builder action(Consumer<Player> action) {
            button.setAction(action);
            return this;
        }

        /**
         * 버튼을 누른 플레이어와 다이얼로그 입력값을 함께 받아 실행할 동작을 설정합니다.
         */
        public Builder action(BiConsumer<Player, DialogResponse> action) {
            button.setResponseAction(action);
            return this;
        }

        public DialogButton build() {
            DialogValidator.validate(button);
            return button;
        }
    }
}
