package me.rukon0621.dialog.api;

import org.bukkit.entity.Player;

/**
 * 다른 플러그인이 RkDialogUtil을 통해 다이얼로그를 여는 진입점입니다.
 */
public interface RkDialogAPI {

    /**
     * 지정한 플레이어에게 다이얼로그를 표시합니다.
     *
     * @param player 다이얼로그를 볼 플레이어
     * @param dialog 표시할 API 다이얼로그 모델
     */
    void open(Player player, Dialog dialog);
}
