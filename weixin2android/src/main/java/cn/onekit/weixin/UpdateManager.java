package cn.onekit.weixin;

import cn.onekit.js.core.function;

public  class UpdateManager {
    public static class CheckForUpdate {
        public boolean hasUpdate;
    }

     public void applyUpdate(){};

    public void onCheckForUpdate(function callback){};

    public void onUpdateFailed(function callback){};

    public void onUpdateReady(function callback){};
}
