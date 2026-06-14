package me.rukon0621.dialog.api.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.rukon0621.dialog.api.util.AdventureUtil;
import net.kyori.adventure.text.Component;

/**
 * 단일 선택 입력에 들어가는 선택지 하나입니다.
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class DialogOptionEntry {

    /**
     * 응답 데이터로 전송될 선택지 id입니다.
     */
    private String id;

    /**
     * 플레이어에게 보이는 선택지 이름입니다.
     */
    private Component display;

    /**
     * 최초 선택 상태입니다. 한 입력 안에서는 하나만 true로 두는 것이 안전합니다.
     */
    private boolean initial;

    /**
     * MiniMessage 형식의 표시 이름으로 선택지를 생성합니다.
     */
    public static DialogOptionEntry of(String id, String display) {
        return new DialogOptionEntry()
                .setId(id)
                .setDisplay(AdventureUtil.toComponent(display));
    }

    /**
     * MiniMessage 형식의 표시 이름과 최초 선택 여부로 선택지를 생성합니다.
     */
    public static DialogOptionEntry of(String id, String display, boolean initial) {
        return of(id, display).setInitial(initial);
    }
}
