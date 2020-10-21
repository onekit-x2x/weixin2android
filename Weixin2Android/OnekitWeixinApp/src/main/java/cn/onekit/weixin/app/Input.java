package cn.onekit.weixin.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import cn.onekit.js.JsObject;
import cn.onekit.js.JsNumber;
import cn.onekit.js.JsString;
import cn.onekit.w3c.Event;
import cn.onekit.w3c.core.FormItem_;
import cn.onekit.weixin.app.core.WeixinElement;

import static cn.onekit.thekit.Android.px2dp;


@SuppressLint("AppCompatCustomView")
public class Input extends WeixinElement implements FormItem_<JsString> {

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
                Event event = new Event("keyboardHeightChange",new JsObject() {{

                        put("height",  new JsNumber(_height));
                        put("duration", new JsNumber(0));

                }},this,this,0);
                dispatchEvent(event);
            }
            isVisiableForLast = visible;
        });
    }
    ///////////////////////
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
    //////////////
    public Input(Context context) {
        super(context);
        _init();
    }
    public Input(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private EditText _input(){
        return findViewById(R.id.input);
    }
    private void _init() {
        inflate(getContext(), R.layout.onekit_input, this);
        _input().setOnFocusChangeListener((v, hasFocus) -> {

            if(hasFocus){
                if (_adjustPosition){
                    ((Activity) getContext()).getWindow().setSoftInputMode(-WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }else {

                    ((Activity) getContext()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

                }
                if(_focusListener!=null){
                    Event event = new Event("change",new JsObject() {{

                            put("value", new JsString(_input().getText().toString()));
                            put("height",  new JsNumber(_height));

                    }},this,this,0);
                    _focusListener.onFocus(event);

                }
            }else {
                if (_blurListener!=null){
                    Event event = new Event("change",new JsObject() {{

                            put("value",  new JsString(_input().getText().toString()));

                    }},this,this,0);
                    _blurListener.onBlur(event);

                }
            }
        });
///////////////////
        _input().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, final int start, int before, final int count) {

                if(_inputListener!=null){

                    if(_keyCode==null) {
                        _keyCode = (int)s.subSequence(start, start + 1).charAt(0);
                    }

                    Event event = new Event("change",new JsObject() {{

                            put("value",  new JsString((String) s));
                            put("cursor", new JsNumber( start + 1));
                            put("keyCode",  new JsNumber(_keyCode));

                    }},Input.this,Input.this,0);
                    _inputListener.onInput(event);

                }
                _keyCode=null;
            }

            @Override
            public void afterTextChanged(final Editable s) {

            }
        });
        _input().setOnKeyListener((v, keyCode, event) -> {
            if(event.getAction()==KeyEvent.ACTION_DOWN) {
                int chr = event.getUnicodeChar();
                if (chr == 0) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                        case KeyEvent.KEYCODE_DEL:
                            _keyCode = 8;
                            break;
                        default:
                            Log.e("=========", "" + keyCode);
                            break;
                    }

                }
            }

            return false;
        });


    }
    Integer _keyCode;
    ////////////////////////////

    private String _value="";

    public void setValue(String value){
        _value=value;
        _input().setText(_value);
        //_input().setHint(_value);
    }

    ////////////////////////
    private String _type="text";
    public void setType(String type){
        _type=type;

        switch (_type){
            case "text":
                _input().setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case "number":
                _input().setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case "idcard":
                //_input().setInputType(InputType.TYPE_CLASS_TEXT);
                _input().setKeyListener(DigitsKeyListener.getInstance("1234567890X"));
                break;
            case "digit":
                _input().setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                break;
            default:
                break;
        }
    }
    public String getType(){
        return _type;
    }
    /////////////////////////////
    private boolean _password=false;
    public void setPassword(boolean password){
        _password=password;
        if (_password){
            // _input().setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            _input().setTransformationMethod(new PasswordTransformationMethod());
            Log.d("Password", _password+"");
        }

    }
    public boolean getPassword(){
        return _password;
    }
    /////////////////////////////
    private boolean _disabled=false;
    public void setDisabled(boolean disabled){
        _disabled=disabled;
        _input().setEnabled(!_disabled);
    }
    public boolean getDisabled(){
        return _disabled;
    }
    /////////////////////////////
    private int _maxlength=140;
    public void setMaxlenth(int maxlenth){
        _maxlength=maxlenth;
        if(_maxlength==-1){
            _input().setFilters(new InputFilter[]{new InputFilter.LengthFilter(1000000000)});
        }else {
            _input().setFilters(new InputFilter[]{new InputFilter.LengthFilter(_maxlength)});
        }
    }
    public int getMaxlenth(){
        return _maxlength;
    }
    /////////////////////////
    private String _placeholder;
    public void setPlaceholder(String placeholder){
        _placeholder=placeholder;
        _input().setHint(_placeholder);
    }
    public String getPlaceholder(){
        return _placeholder;
    }

    ///////////////////
//    private String _placeholderStyle;
//    public void setPlaceholderStyle(String placeholderStyle){
//        SpannableString ss = new SpannableString(_placeholder);
//       // ForegroundColorSpan fcs = new ForegroundColorSpan(color);
//       // ss.setSpan(fcs, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//       // _input().setHint(new SpannedString(ss));
//    }
//    public String getPlaceholderStyle(){
//        return _placeholderStyle;
//    }
    /////////////////////
    private String _confirmType=null;
    public void setConfirmType(String confirmType){
        _confirmType=confirmType;
        if(_type.equals("text")) {
            _input().setSingleLine(true);
            switch (_confirmType) {
                case "send":
                    _input().setImeOptions(EditorInfo.IME_ACTION_SEND);
                    break;
                case "search":
                    _input().setImeOptions(EditorInfo.IME_ACTION_SEARCH);
                    break;
                case "next":
                    _input().setImeOptions(EditorInfo.IME_ACTION_NEXT);
                    break;
                case "go":
                    _input().setImeOptions(EditorInfo.IME_ACTION_GO);
                    break;
                case "done":
                    _input().setImeOptions(EditorInfo.IME_ACTION_DONE);
                    break;
                default:
                    break;
            }
        }
    }
    public String getConfirmType(){
        return _confirmType;
    }
    /////////////////////
    private boolean _confirmHold=false;
    public void setConfirmHold(boolean confirmHold){
        _confirmHold=confirmHold;
        //if (_confirmHold){
//            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.Input_METHOD_SERVICE);
//            imm.showSoftInput( this, InputMethodManager.SHOW_FORCED);
//            _input().setOnKeyListener(new OnKeyListener() {
//                @Override
//                public boolean onKey(View v, int keyCode, KeyEvent event) {
//                    if(keyCode== KeyEvent.KEYCODE_ENTER) {
//                        return true;
//                    }
//                    return false;
//                }
//            });
        _input().setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Toast.makeText(getContext(),"提示111",Toast.LENGTH_SHORT).show();

                return true;
            }

            return false;
        });
    }
    public boolean getConfirmHold(){
        return _confirmHold;
    }

    ////////////////////
    private boolean _focus=false;
    public void setFocus(boolean focus){
        _focus=focus;
        if (_focus) {
            _input().requestFocus();
            ((Activity) getContext()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            int Maxlength = _input().getText().length();
            if (_cursor >= 0 && _cursor < Maxlength) {
                _input().setSelection(_cursor);
            } else if (_cursor > Maxlength) {
                _input().setSelection(_value.length());
            } else {
                _input().requestFocus();
                _input().setSelection(_cursor);
            }
        }else {
            _input().clearFocus();
        }
    }
    public boolean getFocus(){
        return _focus;
    }
    ////////////////////////
    private int _cursor=0;
    public void setCursor(int cursor){
        _cursor=cursor;
    }
    ////////////////////////
    private boolean _adjustPosition=true;
    public void setAdjustPosition(boolean adjustPosition){
        _adjustPosition=adjustPosition;

    }
    public boolean getAdjustPosition(){
        return _adjustPosition;
    }
    public int getCursor(){
        return _cursor;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setValue(JsString value) {

    }

    @Override
    public JsString getValue() {
        return null;
    }

    @Override
    public void reset() {

    }


}
