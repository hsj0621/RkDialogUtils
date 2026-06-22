package me.rukon0621.dialog.core.command;

import me.rukon0621.dialog.RkDialogPlugin;
import me.rukon0621.dialog.api.Dialog;
import me.rukon0621.dialog.api.DialogAfterAction;
import me.rukon0621.dialog.api.DialogButton;
import me.rukon0621.dialog.api.DialogTypeMode;
import me.rukon0621.dialog.api.input.DialogBooleanInput;
import me.rukon0621.dialog.api.input.DialogNumberRangeInput;
import me.rukon0621.dialog.api.input.DialogSingleOptionInput;
import me.rukon0621.dialog.api.input.DialogTextInput;
import me.rukon0621.dialog.api.util.AdventureUtil;
import me.rukon0621.dialog.core.example.MerchantDialog;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class ExampleDialogCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(AdventureUtil.toComponent("<red>플레이어만 사용할 수 있습니다.</red>"));
            return true;
        }

        RkDialogPlugin.getInst().open(player, Dialog.builder()

                        .input(DialogNumberRangeInput.builder()
                                .label("<yellow>숫자 입력</yellow>")
                                .key("number")
                                .range(0, 100)
                                .initial(50F)
                                .step(1F)
                                .build())
                        .button(DialogButton.builder()
                                .text("확인")
                                .action((p, response) -> p.sendMessage(AdventureUtil.toComponent(
                                        "<green>입력한 숫자:</green> <gray>" + response.getFloat("number") + "</gray>"
                                )))
                                .build())
                .build());
        return true;
    }

    private Dialog createDialog(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("merchant")) {
            return new MerchantDialog("상인");
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("advanced")) {
            return createAdvancedDialog();
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("settings")) {
            return createSettingsDialog();
        }

        return createBasicDialog();
    }

    private Dialog createBasicDialog() {
        return Dialog.builder()
                .title("<yellow>[시스템]</yellow>")
                .text("<gray>도시로 이동하시겠습니까?</gray>")
                .button(DialogButton.builder()
                        .text("<green>확인</green>")
                        .tooltip("<gray>도시로 이동합니다.</gray>")
                        .action(player -> {
                            player.sendMessage(AdventureUtil.toComponent("<green>이동을 시작합니다.</green>"));
                            player.performCommand("spawn");
                        })
                        .build())
                .button(DialogButton.builder()
                        .text("<red>취소</red>")
                        .action(player -> player.sendMessage(AdventureUtil.toComponent("<red>이동을 취소했습니다.</red>")))
                        .build())
                .build();
    }

    private Dialog createSettingsDialog() {
        return Dialog.builder()
                .title("<yellow>개인 설정</yellow>")
                .externalTitle("<gold>설정</gold>")
                .plainBody("<gray>자주 쓰는 옵션을 간단히 조정합니다.</gray>")
                .input(DialogBooleanInput.builder()
                        .key("party_invite")
                        .label("<green>파티 초대 허용</green>")
                        .initial(true)
                        .build())
                .input(DialogSingleOptionInput.builder()
                        .key("chat_channel")
                        .label("<yellow>기본 채팅 채널</yellow>")
                        .option("local", "<gray>일반</gray>", true)
                        .option("party", "<aqua>파티</aqua>")
                        .option("guild", "<green>길드</green>")
                        .build())
                .input(DialogNumberRangeInput.builder()
                        .key("volume")
                        .label("<aqua>효과음 볼륨</aqua>")
                        .range(0, 100)
                        .initial(70F)
                        .step(5F)
                        .build())
                .typeMode(DialogTypeMode.CONFIRMATION)
                .afterAction(DialogAfterAction.CLOSE)
                .button(DialogButton.builder()
                        .text("<green>저장</green>")
                        .action((player, response) -> player.sendMessage(AdventureUtil.toComponent(
                                "<green>설정 저장:</green> <gray>파티초대=" + response.getBoolean("party_invite")
                                        + ", 채널=" + response.getText("chat_channel")
                                        + ", 볼륨=" + response.getFloat("volume")
                                        + "</gray>"
                        )))
                        .build())
                .button(DialogButton.builder()
                        .text("<red>취소</red>")
                        .build())
                .build();
    }

    private Dialog createAdvancedDialog() {
        return Dialog.builder()
                .title("<yellow>다이얼로그 설정</yellow>")
                .externalTitle("<gold>설정</gold>")
                .plainBody("<gray>텍스트 입력까지 포함한 예시입니다.</gray>")
                .input(DialogTextInput.builder()
                        .key("memo")
                        .label("<gray>메모</gray>")
                        .maxLength(120)
                        .build())
                .typeMode(DialogTypeMode.CONFIRMATION)
                .afterAction(DialogAfterAction.CLOSE)
                .button(DialogButton.builder()
                        .text("<green>저장</green>")
                        .action((player, response) -> player.sendMessage(AdventureUtil.toComponent(
                                "<green>메모:</green> <gray>" + response.getText("memo") + "</gray>"
                        )))
                        .build())
                .button(DialogButton.builder()
                        .text("<red>취소</red>")
                        .build())
                .build();
    }
}
