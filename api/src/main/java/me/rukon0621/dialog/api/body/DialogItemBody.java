package me.rukon0621.dialog.api.body;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.rukon0621.dialog.api.util.DialogValidator;
import org.bukkit.inventory.ItemStack;

/**
 * 다이얼로그 본문에 아이템을 표시하는 요소입니다.
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class DialogItemBody implements DialogBodyPart {

    /**
     * 표시할 아이템입니다.
     */
    private ItemStack item;

    /**
     * 아이템 아래에 표시할 설명 본문입니다.
     */
    private DialogPlainMessageBody description;

    /**
     * 아이템 슬롯 장식을 표시할지 여부입니다.
     */
    private boolean showDecorations = true;

    /**
     * 아이템 툴팁을 표시할지 여부입니다.
     */
    private boolean showTooltip = true;

    /**
     * 아이템 본문 폭입니다. 렌더링 시 Paper 허용 범위로 보정됩니다.
     */
    private int width = 16;

    /**
     * 아이템 본문 높이입니다. 렌더링 시 Paper 허용 범위로 보정됩니다.
     */
    private int height = 16;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final DialogItemBody body = new DialogItemBody();

        /**
         * 표시할 아이템을 설정합니다.
         */
        public Builder item(ItemStack item) {
            body.setItem(item);
            return this;
        }

        public Builder description(DialogPlainMessageBody description) {
            body.setDescription(description);
            return this;
        }

        public Builder showDecorations(boolean showDecorations) {
            body.setShowDecorations(showDecorations);
            return this;
        }

        public Builder showTooltip(boolean showTooltip) {
            body.setShowTooltip(showTooltip);
            return this;
        }

        public Builder width(int width) {
            body.setWidth(width);
            return this;
        }

        public Builder height(int height) {
            body.setHeight(height);
            return this;
        }

        public DialogItemBody build() {
            DialogValidator.validate(body);
            return body;
        }
    }
}
