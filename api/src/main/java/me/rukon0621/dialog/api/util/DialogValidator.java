package me.rukon0621.dialog.api.util;

import me.rukon0621.dialog.api.Dialog;
import me.rukon0621.dialog.api.DialogButton;
import me.rukon0621.dialog.api.DialogTypeMode;
import me.rukon0621.dialog.api.body.DialogBodyPart;
import me.rukon0621.dialog.api.body.DialogItemBody;
import me.rukon0621.dialog.api.body.DialogPlainMessageBody;
import me.rukon0621.dialog.api.exception.DialogValidationException;
import me.rukon0621.dialog.api.input.DialogBooleanInput;
import me.rukon0621.dialog.api.input.DialogInputPart;
import me.rukon0621.dialog.api.input.DialogNumberRangeInput;
import me.rukon0621.dialog.api.input.DialogOptionEntry;
import me.rukon0621.dialog.api.input.DialogSingleOptionInput;
import me.rukon0621.dialog.api.input.DialogTextInput;
import me.rukon0621.dialog.api.input.DialogTextMultilineOptions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class DialogValidator {

    private DialogValidator() {
    }

    public static void validate(Dialog dialog) {
        if (dialog == null) {
            throw new DialogValidationException("Dialog가 누락되었습니다.");
        }
        if (dialog.getTitle() == null) {
            throw new DialogValidationException("Dialog title이 누락되었습니다.");
        }
        if (dialog.getText() == null) {
            throw new DialogValidationException("Dialog text가 누락되었습니다.");
        }
        validateBody(dialog.getBody());
        validateInputs(dialog.getInputs());
        validateButtons(dialog.getButtons());
        validateType(dialog);
    }

    public static void validate(DialogButton button) {
        if (button == null) {
            throw new DialogValidationException("DialogButton이 누락되었습니다.");
        }
        if (button.getText() == null) {
            throw new DialogValidationException("DialogButton text가 누락되었습니다.");
        }
        if (button.getWidth() < 1 || button.getWidth() > 1024) {
            throw new DialogValidationException("DialogButton width는 1~1024 사이여야 합니다. 입력값: " + button.getWidth());
        }
    }

    public static void validate(DialogPlainMessageBody body) {
        if (body == null) {
            throw new DialogValidationException("DialogPlainMessageBody가 누락되었습니다.");
        }
        if (body.getText() == null) {
            throw new DialogValidationException("DialogPlainMessageBody text가 누락되었습니다.");
        }
        if (body.getWidth() != null && (body.getWidth() < 1 || body.getWidth() > 1024)) {
            throw new DialogValidationException("DialogPlainMessageBody width는 1~1024 사이여야 합니다. 입력값: " + body.getWidth());
        }
    }

    public static void validate(DialogItemBody body) {
        if (body == null) {
            throw new DialogValidationException("DialogItemBody가 누락되었습니다.");
        }
        if (body.getItem() == null) {
            throw new DialogValidationException("DialogItemBody item이 누락되었습니다.");
        }
        if (body.getDescription() != null) {
            validate(body.getDescription());
        }
        if (body.getWidth() < 1 || body.getWidth() > 256) {
            throw new DialogValidationException("DialogItemBody width는 1~256 사이여야 합니다. 입력값: " + body.getWidth());
        }
        if (body.getHeight() < 1 || body.getHeight() > 256) {
            throw new DialogValidationException("DialogItemBody height는 1~256 사이여야 합니다. 입력값: " + body.getHeight());
        }
    }

    public static void validate(DialogBooleanInput input) {
        if (input == null) {
            throw new DialogValidationException("DialogBooleanInput이 누락되었습니다.");
        }
        validateKey("DialogBooleanInput", input.getKey());
        if (input.getLabel() == null) {
            throw new DialogValidationException("DialogBooleanInput label이 누락되었습니다.");
        }
        if (isBlank(input.getOnTrue())) {
            throw new DialogValidationException("DialogBooleanInput onTrue가 누락되었습니다.");
        }
        if (isBlank(input.getOnFalse())) {
            throw new DialogValidationException("DialogBooleanInput onFalse가 누락되었습니다.");
        }
    }

    public static void validate(DialogTextInput input) {
        if (input == null) {
            throw new DialogValidationException("DialogTextInput이 누락되었습니다.");
        }
        validateKey("DialogTextInput", input.getKey());
        if (input.getLabel() == null) {
            throw new DialogValidationException("DialogTextInput label이 누락되었습니다.");
        }
        if (input.getWidth() < 1 || input.getWidth() > 1024) {
            throw new DialogValidationException("DialogTextInput width는 1~1024 사이여야 합니다. 입력값: " + input.getWidth());
        }
        if (input.getInitial() == null) {
            throw new DialogValidationException("DialogTextInput initial이 누락되었습니다.");
        }
        if (input.getMaxLength() < 1) {
            throw new DialogValidationException("DialogTextInput maxLength는 1 이상이어야 합니다. 입력값: " + input.getMaxLength());
        }
        validate(input.getMultiline());
    }

    public static void validate(DialogTextMultilineOptions options) {
        if (options == null) {
            return;
        }
        if (options.getMaxLines() != null && options.getMaxLines() < 1) {
            throw new DialogValidationException("DialogTextMultilineOptions maxLines는 1 이상이어야 합니다. 입력값: " + options.getMaxLines());
        }
        if (options.getHeight() != null && (options.getHeight() < 1 || options.getHeight() > 512)) {
            throw new DialogValidationException("DialogTextMultilineOptions height는 1~512 사이여야 합니다. 입력값: " + options.getHeight());
        }
    }

    public static void validate(DialogSingleOptionInput input) {
        if (input == null) {
            throw new DialogValidationException("DialogSingleOptionInput이 누락되었습니다.");
        }
        validateKey("DialogSingleOptionInput", input.getKey());
        if (input.getLabel() == null) {
            throw new DialogValidationException("DialogSingleOptionInput label이 누락되었습니다.");
        }
        if (input.getWidth() < 1 || input.getWidth() > 1024) {
            throw new DialogValidationException("DialogSingleOptionInput width는 1~1024 사이여야 합니다. 입력값: " + input.getWidth());
        }
        if (input.getOptions() == null || input.getOptions().isEmpty()) {
            throw new DialogValidationException("DialogSingleOptionInput options가 누락되었습니다.");
        }
        validateOptions(input.getOptions());
    }

    public static void validate(DialogNumberRangeInput input) {
        if (input == null) {
            throw new DialogValidationException("DialogNumberRangeInput이 누락되었습니다.");
        }
        validateKey("DialogNumberRangeInput", input.getKey());
        if (input.getLabel() == null) {
            throw new DialogValidationException("DialogNumberRangeInput label이 누락되었습니다.");
        }
        if (input.getWidth() < 1 || input.getWidth() > 1024) {
            throw new DialogValidationException("DialogNumberRangeInput width는 1~1024 사이여야 합니다. 입력값: " + input.getWidth());
        }
        if (isBlank(input.getLabelFormat())) {
            throw new DialogValidationException("DialogNumberRangeInput labelFormat이 누락되었습니다.");
        }
        if (input.getStart() >= input.getEnd()) {
            throw new DialogValidationException("DialogNumberRangeInput range가 올바르지 않습니다. start는 end보다 작아야 합니다.");
        }
        if (input.getInitial() != null && (input.getInitial() < input.getStart() || input.getInitial() > input.getEnd())) {
            throw new DialogValidationException("DialogNumberRangeInput initial은 start~end 범위 안이어야 합니다. 입력값: " + input.getInitial());
        }
        if (input.getStep() != null && input.getStep() <= 0) {
            throw new DialogValidationException("DialogNumberRangeInput step은 0보다 커야 합니다. 입력값: " + input.getStep());
        }
    }

    private static void validateBody(List<DialogBodyPart> body) {
        if (body == null) {
            throw new DialogValidationException("Dialog body가 누락되었습니다.");
        }
        for (DialogBodyPart part : body) {
            if (part instanceof DialogPlainMessageBody plainMessage) {
                validate(plainMessage);
            } else if (part instanceof DialogItemBody itemBody) {
                validate(itemBody);
            } else if (part == null) {
                throw new DialogValidationException("Dialog body에 null 요소가 포함되어 있습니다.");
            } else {
                throw new DialogValidationException("지원하지 않는 Dialog body 타입입니다: " + part.getClass().getName());
            }
        }
    }

    private static void validateInputs(List<DialogInputPart> inputs) {
        if (inputs == null) {
            throw new DialogValidationException("Dialog inputs가 누락되었습니다.");
        }
        Set<String> keys = new HashSet<>();
        for (DialogInputPart input : inputs) {
            validateInput(input);
            if (!keys.add(input.getKey())) {
                throw new DialogValidationException("Dialog input key가 중복되었습니다: " + input.getKey());
            }
        }
    }

    private static void validateInput(DialogInputPart input) {
        if (input instanceof DialogBooleanInput booleanInput) {
            validate(booleanInput);
        } else if (input instanceof DialogTextInput textInput) {
            validate(textInput);
        } else if (input instanceof DialogSingleOptionInput optionInput) {
            validate(optionInput);
        } else if (input instanceof DialogNumberRangeInput rangeInput) {
            validate(rangeInput);
        } else if (input == null) {
            throw new DialogValidationException("Dialog input에 null 요소가 포함되어 있습니다.");
        } else {
            throw new DialogValidationException("지원하지 않는 Dialog input 타입입니다: " + input.getClass().getName());
        }
    }

    private static void validateButtons(List<DialogButton> buttons) {
        if (buttons == null) {
            throw new DialogValidationException("Dialog buttons가 누락되었습니다.");
        }
        for (DialogButton button : buttons) {
            validate(button);
        }
    }

    private static void validateType(Dialog dialog) {
        DialogTypeMode typeMode = dialog.getTypeMode() == null ? DialogTypeMode.AUTO : dialog.getTypeMode();
        int buttonSize = dialog.getButtons().size();
        if (typeMode == DialogTypeMode.NOTICE && buttonSize != 1) {
            throw new DialogValidationException("NOTICE 타입은 버튼이 정확히 1개 필요합니다. 현재: " + buttonSize);
        }
        if (typeMode == DialogTypeMode.CONFIRMATION && buttonSize != 2) {
            throw new DialogValidationException("CONFIRMATION 타입은 버튼이 정확히 2개 필요합니다. 현재: " + buttonSize);
        }
        if (typeMode == DialogTypeMode.MULTI_ACTION && buttonSize < 1) {
            throw new DialogValidationException("MULTI_ACTION 타입은 버튼이 1개 이상 필요합니다.");
        }
        if (dialog.getExitButton() != null) {
            validate(dialog.getExitButton());
        }
        if (dialog.getMultiActionColumns() < 1) {
            throw new DialogValidationException("Dialog multiActionColumns는 1 이상이어야 합니다. 입력값: " + dialog.getMultiActionColumns());
        }
    }

    private static void validateOptions(List<DialogOptionEntry> options) {
        Set<String> ids = new HashSet<>();
        int initialCount = 0;
        for (DialogOptionEntry option : options) {
            if (option == null) {
                throw new DialogValidationException("DialogSingleOptionInput option에 null 요소가 포함되어 있습니다.");
            }
            if (isBlank(option.getId())) {
                throw new DialogValidationException("DialogSingleOptionInput option id가 누락되었습니다.");
            }
            if (!ids.add(option.getId())) {
                throw new DialogValidationException("DialogSingleOptionInput option id가 중복되었습니다: " + option.getId());
            }
            if (option.getDisplay() == null) {
                throw new DialogValidationException("DialogSingleOptionInput option display가 누락되었습니다.");
            }
            if (option.isInitial()) {
                initialCount++;
            }
        }
        if (initialCount > 1) {
            throw new DialogValidationException("DialogSingleOptionInput initial option은 하나만 지정할 수 있습니다.");
        }
    }

    private static void validateKey(String owner, String key) {
        if (isBlank(key)) {
            throw new DialogValidationException(owner + " key가 누락되었습니다.");
        }
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
