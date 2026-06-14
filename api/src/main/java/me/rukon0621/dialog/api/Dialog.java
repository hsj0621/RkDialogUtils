package me.rukon0621.dialog.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.rukon0621.dialog.api.body.DialogBodyPart;
import me.rukon0621.dialog.api.body.DialogPlainMessageBody;
import me.rukon0621.dialog.api.input.DialogInputPart;
import me.rukon0621.dialog.api.util.AdventureUtil;
import me.rukon0621.dialog.api.util.DialogValidator;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Paper Dialog를 쉽게 구성하기 위한 최상위 API 모델입니다.
 * 문자열 인자는 모두 MiniMessage 형식만 사용합니다.
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Dialog {

    /**
     * 다이얼로그 상단에 표시되는 제목입니다.
     */
    private Component title = Component.empty();

    /**
     * 다른 다이얼로그나 버튼에서 이 다이얼로그를 참조할 때 보이는 외부 제목입니다.
     */
    private Component externalTitle;

    /**
     * 단순 다이얼로그용 기본 본문입니다. body가 비어 있을 때만 사용됩니다.
     */
    private Component text = Component.empty();

    /**
     * 본문 요소 목록입니다. 텍스트, 아이템 등 여러 요소를 순서대로 추가할 수 있습니다.
     */
    private List<DialogBodyPart> body = new ArrayList<>();

    /**
     * 플레이어 입력 요소 목록입니다. 각 입력은 key로 응답 값을 식별합니다.
     */
    private List<DialogInputPart> inputs = new ArrayList<>();

    /**
     * 하단 액션 버튼 목록입니다.
     */
    private List<DialogButton> buttons = new ArrayList<>();

    /**
     * ESC 키로 닫을 수 있는지 여부입니다.
     */
    private boolean canCloseWithEscape = true;

    /**
     * 싱글 플레이 환경에서 화면 뒤 게임을 일시정지할지 여부입니다.
     */
    private boolean pause = false;

    /**
     * 버튼 클릭 후 클라이언트가 다이얼로그를 어떻게 처리할지 결정합니다.
     */
    private DialogAfterAction afterAction = DialogAfterAction.CLOSE;

    /**
     * 버튼 수에 따른 자동 타입 선택 대신 특정 DialogType을 강제할 수 있습니다.
     */
    private DialogTypeMode typeMode = DialogTypeMode.AUTO;

    /**
     * multiAction 타입에서 별도로 표시할 종료 버튼입니다.
     */
    private DialogButton exitButton;

    /**
     * multiAction 타입에서 버튼을 배치할 열 수입니다.
     */
    private int multiActionColumns = 1;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Dialog dialog = new Dialog();

        /**
         * MiniMessage 형식의 제목을 설정합니다.
         */
        public Builder title(String title) {
            dialog.setTitle(AdventureUtil.toComponent(title));
            return this;
        }

        public Builder title(Component title) {
            dialog.setTitle(title);
            return this;
        }

        /**
         * MiniMessage 형식의 외부 제목을 설정합니다.
         */
        public Builder externalTitle(String externalTitle) {
            dialog.setExternalTitle(AdventureUtil.toComponent(externalTitle));
            return this;
        }

        public Builder externalTitle(Component externalTitle) {
            dialog.setExternalTitle(externalTitle);
            return this;
        }

        /**
         * body를 따로 추가하지 않았을 때 사용할 기본 본문을 설정합니다.
         */
        public Builder text(String text) {
            dialog.setText(AdventureUtil.toComponent(text));
            return this;
        }

        public Builder text(Component text) {
            dialog.setText(text);
            return this;
        }

        /**
         * 텍스트, 아이템 같은 본문 요소를 순서대로 추가합니다.
         */
        public Builder body(DialogBodyPart body) {
            dialog.getBody().add(body);
            return this;
        }

        /**
         * MiniMessage 텍스트 본문을 빠르게 추가합니다.
         */
        public Builder plainBody(String text) {
            dialog.getBody().add(DialogPlainMessageBody.builder().text(text).build());
            return this;
        }

        /**
         * 체크박스, 선택지, 텍스트, 슬라이더 같은 입력 요소를 추가합니다.
         */
        public Builder input(DialogInputPart input) {
            dialog.getInputs().add(input);
            return this;
        }

        /**
         * 하단 액션 버튼을 추가합니다.
         */
        public Builder button(DialogButton button) {
            dialog.getButtons().add(button);
            return this;
        }

        public Builder buttons(List<DialogButton> buttons) {
            dialog.setButtons(new ArrayList<>(buttons));
            return this;
        }

        public Builder canCloseWithEscape(boolean canCloseWithEscape) {
            dialog.setCanCloseWithEscape(canCloseWithEscape);
            return this;
        }

        public Builder pause(boolean pause) {
            dialog.setPause(pause);
            return this;
        }

        public Builder afterAction(DialogAfterAction afterAction) {
            dialog.setAfterAction(afterAction);
            return this;
        }

        /**
         * 버튼 개수 기반 자동 선택 대신 특정 타입을 강제합니다.
         */
        public Builder typeMode(DialogTypeMode typeMode) {
            dialog.setTypeMode(typeMode);
            return this;
        }

        public Builder exitButton(DialogButton exitButton) {
            dialog.setExitButton(exitButton);
            return this;
        }

        public Builder multiActionColumns(int multiActionColumns) {
            dialog.setMultiActionColumns(multiActionColumns);
            return this;
        }

        /**
         * 현재 설정값으로 다이얼로그 모델을 생성합니다.
         */
        public Dialog build() {
            dialog.setBody(new ArrayList<>(dialog.getBody()));
            dialog.setInputs(new ArrayList<>(dialog.getInputs()));
            dialog.setButtons(new ArrayList<>(dialog.getButtons()));
            DialogValidator.validate(dialog);
            return dialog;
        }
    }
}
