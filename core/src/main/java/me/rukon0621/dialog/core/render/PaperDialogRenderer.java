package me.rukon0621.dialog.core.render;

import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.action.DialogAction;
import io.papermc.paper.registry.data.dialog.body.DialogBody;
import io.papermc.paper.registry.data.dialog.input.DialogInput;
import io.papermc.paper.registry.data.dialog.input.SingleOptionDialogInput;
import io.papermc.paper.registry.data.dialog.input.TextDialogInput;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import me.rukon0621.dialog.api.Dialog;
import me.rukon0621.dialog.api.DialogAfterAction;
import me.rukon0621.dialog.api.DialogButton;
import me.rukon0621.dialog.api.DialogResponse;
import me.rukon0621.dialog.api.DialogTypeMode;
import me.rukon0621.dialog.api.body.DialogBodyPart;
import me.rukon0621.dialog.api.body.DialogItemBody;
import me.rukon0621.dialog.api.body.DialogPlainMessageBody;
import me.rukon0621.dialog.api.input.DialogBooleanInput;
import me.rukon0621.dialog.api.input.DialogInputPart;
import me.rukon0621.dialog.api.input.DialogNumberRangeInput;
import me.rukon0621.dialog.api.input.DialogOptionEntry;
import me.rukon0621.dialog.api.input.DialogSingleOptionInput;
import me.rukon0621.dialog.api.input.DialogTextInput;
import me.rukon0621.dialog.api.util.DialogValidator;
import net.kyori.adventure.text.event.ClickCallback;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public final class PaperDialogRenderer {

    public io.papermc.paper.dialog.Dialog render(Dialog dialog) {
        DialogValidator.validate(dialog);

        // API 모델의 공통 속성은 Paper DialogBase로 먼저 고정한다.
        DialogBase base = DialogBase.builder(dialog.getTitle())
                .externalTitle(dialog.getExternalTitle())
                .body(createBody(dialog))
                .inputs(createInputs(dialog.getInputs()))
                .canCloseWithEscape(dialog.isCanCloseWithEscape())
                .pause(dialog.isPause())
                .afterAction(createAfterAction(dialog.getAfterAction()))
                .build();

        DialogType type = createType(dialog);

        return io.papermc.paper.dialog.Dialog.create(builder -> builder.empty()
                .base(base)
                .type(type)
        );
    }

    private List<DialogBody> createBody(Dialog dialog) {
        List<DialogBody> body = new ArrayList<>();

        for (DialogBodyPart part : dialog.getBody()) {
            body.add(createBodyPart(part));
        }

        if (body.isEmpty()) {
            body.add(DialogBody.plainMessage(dialog.getText()));
        }
        return body;
    }

    private DialogBody createBodyPart(DialogBodyPart part) {
        if (part instanceof DialogPlainMessageBody plainMessage) {
            return createPlainMessage(plainMessage);
        }

        if (part instanceof DialogItemBody itemBody) {
            return DialogBody.item(
                    itemBody.getItem(),
                    itemBody.getDescription() == null ? null : createPlainMessage(itemBody.getDescription()),
                    itemBody.isShowDecorations(),
                    itemBody.isShowTooltip(),
                    clamp(itemBody.getWidth(), 1, 256),
                    clamp(itemBody.getHeight(), 1, 256)
            );
        }

        throw new IllegalArgumentException("Unsupported dialog body part: " + part.getClass().getName());
    }

    private io.papermc.paper.registry.data.dialog.body.PlainMessageDialogBody createPlainMessage(DialogPlainMessageBody body) {
        if (body.getWidth() == null) {
            return DialogBody.plainMessage(body.getText());
        }
        return DialogBody.plainMessage(body.getText(), clamp(body.getWidth(), 1, 1024));
    }

    private List<DialogInput> createInputs(List<DialogInputPart> inputs) {
        List<DialogInput> result = new ArrayList<>(inputs.size());
        for (DialogInputPart input : inputs) {
            result.add(createInput(input));
        }
        return result;
    }

    private DialogInput createInput(DialogInputPart input) {
        if (input instanceof DialogBooleanInput booleanInput) {
            return DialogInput.bool(
                    booleanInput.getKey(),
                    booleanInput.getLabel(),
                    booleanInput.isInitial(),
                    booleanInput.getOnTrue(),
                    booleanInput.getOnFalse()
            );
        }

        if (input instanceof DialogTextInput textInput) {
            return DialogInput.text(
                    textInput.getKey(),
                    clamp(textInput.getWidth(), 1, 1024),
                    textInput.getLabel(),
                    textInput.isLabelVisible(),
                    textInput.getInitial(),
                    Math.max(1, textInput.getMaxLength()),
                    createMultiline(textInput)
            );
        }

        if (input instanceof DialogSingleOptionInput optionInput) {
            return DialogInput.singleOption(
                    optionInput.getKey(),
                    clamp(optionInput.getWidth(), 1, 1024),
                    createOptionEntries(optionInput.getOptions()),
                    optionInput.getLabel(),
                    optionInput.isLabelVisible()
            );
        }

        if (input instanceof DialogNumberRangeInput rangeInput) {
            return DialogInput.numberRange(
                    rangeInput.getKey(),
                    clamp(rangeInput.getWidth(), 1, 1024),
                    rangeInput.getLabel(),
                    rangeInput.getLabelFormat(),
                    rangeInput.getStart(),
                    rangeInput.getEnd(),
                    rangeInput.getInitial(),
                    rangeInput.getStep()
            );
        }

        throw new IllegalArgumentException("Unsupported dialog input: " + input.getClass().getName());
    }

    private TextDialogInput.MultilineOptions createMultiline(DialogTextInput textInput) {
        if (textInput.getMultiline() == null) {
            return null;
        }

        Integer height = textInput.getMultiline().getHeight();
        if (height != null) {
            height = clamp(height, 1, 512);
        }
        return TextDialogInput.MultilineOptions.create(textInput.getMultiline().getMaxLines(), height);
    }

    private List<SingleOptionDialogInput.OptionEntry> createOptionEntries(List<DialogOptionEntry> options) {
        List<SingleOptionDialogInput.OptionEntry> entries = new ArrayList<>(options.size());
        for (DialogOptionEntry option : options) {
            entries.add(SingleOptionDialogInput.OptionEntry.create(option.getId(), option.getDisplay(), option.isInitial()));
        }
        return entries;
    }

    private DialogBase.DialogAfterAction createAfterAction(DialogAfterAction afterAction) {
        return switch (afterAction == null ? DialogAfterAction.CLOSE : afterAction) {
            case CLOSE -> DialogBase.DialogAfterAction.CLOSE;
            case NONE -> DialogBase.DialogAfterAction.NONE;
            case WAIT_FOR_RESPONSE -> DialogBase.DialogAfterAction.WAIT_FOR_RESPONSE;
        };
    }

    private DialogType createType(Dialog dialog) {
        List<DialogButton> buttons = dialog.getButtons();
        // 버튼이 없으면 Paper 기본 확인 버튼을 쓰는 notice dialog로 처리한다.
        if (buttons == null || buttons.isEmpty()) {
            return DialogType.notice();
        }

        List<ActionButton> actionButtons = new ArrayList<>(buttons.size());
        for (DialogButton button : buttons) {
            actionButtons.add(createButton(button));
        }

        DialogTypeMode typeMode = dialog.getTypeMode() == null ? DialogTypeMode.AUTO : dialog.getTypeMode();
        if (typeMode == DialogTypeMode.NOTICE || typeMode == DialogTypeMode.AUTO && actionButtons.size() == 1) {
            return DialogType.notice(actionButtons.get(0));
        }

        // 버튼 2개는 클라이언트의 확인/취소 레이아웃에 맞는 confirmation 타입이 가장 자연스럽다.
        if (typeMode == DialogTypeMode.CONFIRMATION || typeMode == DialogTypeMode.AUTO && actionButtons.size() == 2) {
            return DialogType.confirmation(actionButtons.get(0), actionButtons.get(1));
        }

        // 3개 이상부터는 여러 액션을 세로로 나열하는 multiAction 타입을 사용한다.
        return DialogType.multiAction(actionButtons, createNullableButton(dialog.getExitButton()), Math.max(1, dialog.getMultiActionColumns()));
    }

    private ActionButton createButton(DialogButton button) {
        return ActionButton.create(
                button.getText(),
                button.getTooltip(),
                clampWidth(button.getWidth()),
                createAction(button)
        );
    }

    private ActionButton createNullableButton(DialogButton button) {
        return button == null ? null : createButton(button);
    }

    private DialogAction createAction(DialogButton button) {
        if (button.getAction() == null && button.getResponseAction() == null) {
            return null;
        }

        // Options는 Paper 1.21.10에서 null을 허용하지 않으므로 명시적으로 지정한다.
        return DialogAction.customClick((view, audience) -> {
            if (audience instanceof Player player) {
                if (button.getAction() != null) {
                    button.getAction().accept(player);
                }
                if (button.getResponseAction() != null) {
                    button.getResponseAction().accept(player, createResponse(view));
                }
            }
        }, ClickCallback.Options.builder()
                .uses(1)
                .lifetime(Duration.ofMinutes(5))
                .build());
    }

    private DialogResponse createResponse(io.papermc.paper.dialog.DialogResponseView view) {
        return new DialogResponse() {
            @Override
            public String getText(String key) {
                return view.getText(key);
            }

            @Override
            public Boolean getBoolean(String key) {
                return view.getBoolean(key);
            }

            @Override
            public Float getFloat(String key) {
                return view.getFloat(key);
            }
        };
    }

    private int clampWidth(int width) {
        // Paper ActionButton 폭 제한은 1~1024이므로 잘못된 입력은 렌더링 직전에 보정한다.
        return clamp(width, 1, 1024);
    }

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}
