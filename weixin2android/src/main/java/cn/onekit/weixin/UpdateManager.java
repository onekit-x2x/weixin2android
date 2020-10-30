package cn.onekit.weixin;

import cn.onekit.js.core.function;

public abstract class UpdateManager {
    public static class CheckForUpdate {
        public boolean hasUpdate;
    }

    abstract void applyUpdate();

    abstract void onCheckForUpdate(function callback);

    abstract void onUpdateFailed(function callback);

    abstract void onUpdateReady(function callback);
}
