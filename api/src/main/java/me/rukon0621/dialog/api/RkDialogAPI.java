package me.rukon0621.dialog.api;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * 다른 플러그인이 RkDialogUtil을 통해 다이얼로그를 여는 진입점입니다.
 */
public abstract class RkDialogAPI extends JavaPlugin {
    @Getter
    protected static RkDialogAPI inst;

    public RkDialogAPI() {

    }

    /**
     * 지정한 플레이어에게 다이얼로그를 표시합니다.
     *
     * @param player 다이얼로그를 볼 플레이어
     * @param dialog 표시할 API 다이얼로그 모델
     */
    public abstract void open(Player player, Dialog dialog);
}
