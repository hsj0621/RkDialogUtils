package me.rukon0621.dialog.core.example;

import me.rukon0621.dialog.api.Dialog;
import me.rukon0621.dialog.api.DialogButton;
import me.rukon0621.dialog.api.util.AdventureUtil;

public class MerchantDialog extends Dialog {

    public MerchantDialog(String name) {
        super();
        setTitle(AdventureUtil.toComponent("<gold>" + name + "</gold>"));
        setText(AdventureUtil.toComponent("<gray>필요한 물건을 구매하시겠습니까?</gray>"));
        getButtons().add(DialogButton.builder()
                .text("<green>구매</green>")
                .action(player -> player.sendMessage(AdventureUtil.toComponent("<green>구매 메뉴를 엽니다.</green>")))
                .build());
        getButtons().add(DialogButton.builder()
                .text("<red>닫기</red>")
                .build());
    }
}
