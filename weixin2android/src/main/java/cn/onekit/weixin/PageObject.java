package cn.onekit.weixin;

public class PageObject {
    public String route;

    public PageObject(String route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return String.format("{\"route\":\"%s\"}",route);
    }
}
