package cn.onekit.weixin.app;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cn.onekit.js.Dict;
import cn.onekit.js.core.JsNumber;
import cn.onekit.w3c.Event;
import cn.onekit.weixin.app.core.WeixinElement;

import static thekit.android.Android.px2dp;

public class TextArea extends WeixinElement {
    private float _height;
    boolean isVisiableForLast = false;
    public void setOnKeyboardHeightChangeListener() {
        final View decorView = ((Activity)getContext()).getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect rect = new Rect();
            decorView.getWindowVisibleDisplayFrame(rect);
            //计算出可见屏幕的高度
            int displayHight = rect.bottom - rect.top;
            //获得屏幕整体的高度
            int hight = decorView.getHeight();
            //获取状态栏的高度
            Resources resources = getContext().getResources();
            int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
            int ztheight = resources.getDimensionPixelSize(resourceId);
            //获得键盘高度
            boolean visible = (double) displayHight / hight < 0.8;
            if(visible != isVisiableForLast){
                //_height = Util.px2dp(hight-displayHight-ztheight);
                _height=px2dp((hight-displayHight-ztheight));
                Event event = new Event("keyboardHeightChange",new Dict() {{

                    put("height", new JsNumber(_height));
                    put("duration",new JsNumber(0));

                }},this,this,0);
                dispatchEvent(event);
            }
            isVisiableForLast = visible;
        });
    }
    public interface OnInputListener {
        void onInput(Event event);
    }
    public OnInputListener _inputListener;
    public void setOnInputListener(OnInputListener listener){
        _inputListener=listener;
    }
    //////////////
    public interface OnFocusListener {
        void onFocus(Event event);
    }
    public OnFocusListener _focusListener;
    public void setOnFocusListener(OnFocusListener listener){
        _focusListener=listener;
    }
    //////////////
    public interface OnBlurListener {
        void onBlur(Event event);
    }
    public OnBlurListener _blurListener;
    public void setOnBlurListener(OnBlurListener listener){
        _blurListener=listener;
    }


    public TextArea(@NonNull Context context) {
        super(context);
        _init();
    }
    public TextArea(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        _init();
    }
    private EditText _textarea(){
        return findViewById(R.id.textarea);
    }
    private void _init(){
        inflate(getContext(), R.layout.onekit_textarea, this);

    }
    private String _value="";

    public void setValue(String value){
        _value=value;
        _textarea().setText(_value);
        //_input().setHint(_value);
    }
    private boolean _disabled=false;
    public void setDisabled(boolean disabled){
        _disabled=disabled;
        _textarea().setEnabled(!_disabled);
    }
    public boolean getDisabled(){
        return _disabled;
    }
    /////////////////////////////
    private int _maxlength=140;
    public void setMaxlenth(int maxlenth){
        _maxlength=maxlenth;
        if(_maxlength==-1){
            _textarea().setFilters(new InputFilter[]{new InputFilter.LengthFilter(1000000000)});

        }else {
            _textarea().setFilters(new InputFilter[]{new InputFilter.LengthFilter(_maxlength)});
        }
    }
    public int getMaxlenth(){
        return _maxlength;
    }
    /////////////////////////
    private String _placeholder;
    public void setPlaceholder(String placeholder){
        _placeholder=placeholder;
        _textarea().setHint(_placeholder);
    }
    public String getPlaceholder(){
        return _placeholder;
    }
    private boolean _focus=false;
    public void setFocus(boolean focus){
        _focus=focus;
        if (_focus) {
            _textarea().requestFocus();
            ((Activity) getContext()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            int Maxlength = _textarea().getText().length();
            if (_cursor >= 0 && _cursor < Maxlength) {
                _textarea().setSelection(_cursor);
            } else if (_cursor > Maxlength) {
                _textarea().setSelection(_value.length());
            } else {
                _textarea().requestFocus();
                _textarea().setSelection(_cursor);
            }
        }else {
            _textarea().clearFocus();
        }
    }
    public boolean getFocus(){
        return _focus;
    }
    private int _cursor=0;
    public void setCursor(int cursor){
        _cursor=cursor;
    }
    public int getCursor(){
        return _cursor;
    }
    ////////////////////////
    private boolean _adjustPosition=true;
    public void setAdjustPosition(boolean adjustPosition){
        _adjustPosition=adjustPosition;

    }
    public boolean getAdjustPosition(){
        return _adjustPosition;
    }
    //////////
    private boolean _autoHeight=false;
    public void setAutoHeight(boolean autoHeight){
        _autoHeight=autoHeight;
        if (_autoHeight) {
            _textarea().setMinLines(1);
        }else {

        }
    }
    public boolean getAutoHeight(){
        return _autoHeight;

    }
    //////////////////////////
    private boolean _holdKeyboard=false;
    public void setHoldKeyboard(boolean holdKeyboard){
        _holdKeyboard=holdKeyboard;

    }
    public boolean getHoldKeyboard(){
        return _holdKeyboard;
    }
    ////////////////////

    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        if (me.getAction() == MotionEvent.ACTION_DOWN) {  //把操作放在用户点击的时候
            //得到当前页面的焦点,ps:有输入框的页面焦点一般会被输入框占据
            View v = ((Activity) getContext()).getCurrentFocus();
            if (isShouldHideKeyboard(v, me)) { //判断用户点击的是否是输入框以外的区域

                Toast.makeText(getContext(),"点击输入框",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getContext(),"点击外部",Toast.LENGTH_SHORT).show();
//                InputMethodManager manager = ((InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
//                manager.hideSoftInputFromWindow(this.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);  //收起键盘
            }
//            if (v instanceof EditText){
//                Toast.makeText(getContext(),"点击输入框",Toast.LENGTH_SHORT).show();
//            }else {
//                Toast.makeText(getContext(),"点击外部",Toast.LENGTH_SHORT).show();
//            }
        }
        return super.dispatchTouchEvent(me);

    }
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {  //判断得到的焦点控件是否包含EditText
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],    //得到输入框在屏幕中上下左右的位置
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {                // 点击位置如果是EditText的区域，忽略它，不收起键盘。
                return false;
            } else {
                return true;
            }
        }        // 如果焦点不是EditText则忽略
        return false;
    }

}
