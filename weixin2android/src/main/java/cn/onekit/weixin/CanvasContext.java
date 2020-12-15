package cn.onekit.weixin;

import java.lang.reflect.Array;

import cn.onekit.js.JsArray;
import cn.onekit.js.JsBoolean;
import cn.onekit.js.JsObject;
import cn.onekit.js.Map;
import cn.onekit.js.core.function;

public class CanvasContext {
    private CanvasGradient fillStyle;
    private CanvasGradient strokeStyle;
    private JsObject shadowOffsetX;
    private JsObject shadowOffsetY;
    private JsObject shadowColor;
    private JsObject shadowBlur;
    private JsObject lineWidth;
    private JsObject lineCap;
    private JsObject lineJoin;
    private JsObject miterLimit;
    private JsObject lineDashOffset;
    private JsObject font;
    private JsObject globalAlpha;
    private JsObject globalCompositeOperation;

    public CanvasGradient getFillStyle() {
        return fillStyle;
    }

    public CanvasGradient getStrokeStyle() {
        return strokeStyle;
    }

    public JsObject getShadowOffsetX() {
        return shadowOffsetX;
    }

    public JsObject getShadowOffsetY() {
        return shadowOffsetY;
    }

    public JsObject getShadowColor() {
        return shadowColor;
    }

    public JsObject getShadowBlur() {
        return shadowBlur;
    }

    public JsObject getLineWidth() {
        return lineWidth;
    }

    public JsObject getLineCap() {
        return lineCap;
    }

    public JsObject getLineJoin() {
        return lineJoin;
    }

    public JsObject getMiterLimit() {
        return miterLimit;
    }

    public JsObject getLineDashOffset() {
        return lineDashOffset;
    }

    public JsObject getFont() {
        return font;
    }

    public JsObject getGlobalAlpha() {
        return globalAlpha;
    }

    public JsObject getGlobalCompositeOperation() {
        return globalCompositeOperation;
    }

    public class CanvasGradient {
        private JsObject stop;
        private JsObject color;

        public JsObject getStop() {
            return stop;
        }

        public JsObject getColor() {
            return color;
        }

        public void addColorStop(JsObject stop, JsObject color){

        }

    }
    public void arc(JsObject x, JsObject y, JsObject r, JsObject sAngle, JsObject eAngle, JsBoolean counterclockwise){

    }
    public void arcTo(JsObject x1, JsObject y1, JsObject x2, JsObject y2, JsObject radius){

    }
    public void beginPath(){

    }
    public void bezierCurveTo(JsObject cp1x, JsObject cp1y, JsObject cp2x, JsObject cp2y, JsObject x, JsObject y) {

    }
    public void clearRect(JsObject x, JsObject y, JsObject width, JsObject height) {

    }
    public void clip() {

    }
    public void closePath() {

    }
    public CanvasGradient createCircularGradient(JsObject x, JsObject y, JsObject r) {
         return null;
    }
    public CanvasGradient createLinearGradient(JsObject x0, JsObject y0, JsObject x1, JsObject y1) {
        return null;
    }
    public void createPattern(JsObject image, JsObject repetition) {

    }
    public void draw(JsBoolean reserve, function callback) {

    }
    public void drawImage(JsObject imageResource, JsObject sx, JsObject sy, JsObject sWidth, JsObject sHeight, JsObject dx, JsObject dy, JsObject dWidth, JsObject dHeight) {

    }
    public void fill() {

    }
    public void fillRect(JsObject x, JsObject y, JsObject width, JsObject height) {

    }
    public void fillText(JsObject text, JsObject x, JsObject y, JsObject maxWidth) {

    }
    public void lineTo(JsObject x, JsObject y) {

    }
    public Map measureText(JsObject text) {
       return null;
    }
    public void moveTo(JsObject x, JsObject y) {

    }
    public void quadraticCurveTo(JsObject cpx, JsObject cpy, JsObject x, JsObject y) {

    }
    public void restore() {

    }
    public void rotate(JsObject rotate) {

    }
    public void save() {

    }
    public void scale(JsObject scaleWidth, JsObject scaleHeight) {

    }
    public void setFillStyle(CanvasGradient color) {

    }
    public void setFontSize(JsObject fontSize) {

    }
    public void setGlobalAlpha(JsObject alpha) {

    }
    public void setLineCap(JsObject lineCap) {

    }
    public void setLineDash(JsArray pattern, JsObject offset) {

    }
    public void setLineJoin(JsObject lineJoin) {

    }
    public void setLineWidth(JsObject lineWidth) {

    }
    public void setMiterLimit(JsObject miterLimit) {

    }
    public void setShadow(JsObject offsetX, JsObject offsetY, JsObject blur, JsObject color) {

    }
    public void setStrokeStyle(CanvasGradient color) {

    }
    public void setTextAlign(JsObject align) {

    }
    public void setTextBaseline(JsObject textBaseline) {

    }
    public void setTransform(JsObject scaleX, JsObject skewX, JsObject skewY, JsObject scaleY, JsObject translateX, JsObject translateY) {

    }
    public void stroke() {

    }
    public void strokeRect(JsObject x, JsObject y, JsObject width, JsObject height) {

    }
    public void strokeText(JsObject text, JsObject x, JsObject y, JsObject maxWidth) {

    }
    public void transform(JsObject scaleX, JsObject skewX, JsObject skewY, JsObject scaleY, JsObject translateX, JsObject translateY) {

    }
    public void translate(JsObject x, JsObject y) {

    }
}
