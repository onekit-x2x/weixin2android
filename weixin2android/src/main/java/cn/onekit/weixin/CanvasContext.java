package cn.onekit.weixin;

public class CanvasContext {
    private CanvasGradient fillStyle;
    private CanvasGradient strokeStyle;
    private int shadowOffsetX;
    private int shadowOffsetY;
    private int shadowColor;
    private int shadowBlur;
    private int lineWidth;
    private String lineCap;
    private String lineJoin;
    private int miterLimit;
    private int lineDashOffset;
    private String font;
    private int globalAlpha;
    private String globalCompositeOperation;

    public CanvasGradient getFillStyle() {
        return fillStyle;
    }

    public CanvasGradient getStrokeStyle() {
        return strokeStyle;
    }

    public int getShadowOffsetX() {
        return shadowOffsetX;
    }

    public int getShadowOffsetY() {
        return shadowOffsetY;
    }

    public int getShadowColor() {
        return shadowColor;
    }

    public int getShadowBlur() {
        return shadowBlur;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public String getLineCap() {
        return lineCap;
    }

    public String getLineJoin() {
        return lineJoin;
    }

    public int getMiterLimit() {
        return miterLimit;
    }

    public int getLineDashOffset() {
        return lineDashOffset;
    }

    public String getFont() {
        return font;
    }

    public int getGlobalAlpha() {
        return globalAlpha;
    }

    public String getGlobalCompositeOperation() {
        return globalCompositeOperation;
    }

    private class CanvasGradient {

    }
}
