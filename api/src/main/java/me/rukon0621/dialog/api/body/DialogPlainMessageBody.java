package me.rukon0621.dialog.api.body;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.rukon0621.dialog.api.util.AdventureUtil;
import me.rukon0621.dialog.api.util.DialogValidator;
import net.kyori.adventure.text.Component;

/**
 * 다이얼로그 본문에 표시할 일반 텍스트 요소입니다.
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class DialogPlainMessageBody implements DialogBodyPart {

    /**
     * 표시할 텍스트입니다.
     */
    private Component text = Component.empty();

    /**
     * 본문 폭입니다. null이면 Paper 기본값을 사용합니다.
     */
    private Integer width;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final DialogPlainMessageBody body = new DialogPlainMessageBody();

        /**
         * MiniMessage 형식의 본문 텍스트를 설정합니다.
         */
        public Builder text(String text) {
            body.setText(AdventureUtil.toComponent(text));
            return this;
        }

        public Builder text(Component text) {
            body.setText(text);
            return this;
        }

        public Builder width(Integer width) {
            body.setWidth(width);
            return this;
        }

        /**
         * 현재 설정값으로 텍스트 본문 요소를 생성합니다.
         */
        public DialogPlainMessageBody build() {
            DialogValidator.validate(body);
            return body;
        }
    }
}
