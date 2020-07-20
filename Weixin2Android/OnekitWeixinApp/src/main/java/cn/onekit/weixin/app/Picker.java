package cn.onekit.weixin.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


import cn.onekit.js.Array;
import cn.onekit.js.Dict;
import cn.onekit.js.core.JsNumber;
import cn.onekit.js.core.JsString;
import cn.onekit.js.core.JsObject;
import cn.onekit.w3c.*;
import cn.onekit.w3c.core.FormItem_;
import cn.onekit.weixin.app.core.WeixinElement;

public class Picker extends WeixinElement implements FormItem_ {

    public enum Mode {
        selector,
        multiSelector,
        time,
        date,
        region
    }

    public enum Fields {
        day,
        month,
        year
    }


    ///////////////////////////////////
    public Picker(Context context) {
        super(context);
        _init();
    }

    public Picker(Context context, AttributeSet attrs) {
        super(context, attrs);
        _init();
    }

    BottomSheetDialog bottomSheetDialog;

    private void _init() {
        bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.onekit_picker);
        ///////////////////////
        bottomSheetDialog.findViewById(R.id.cancelButton).setOnClickListener(v -> bottomSheetDialog.hide());
        bottomSheetDialog.findViewById(R.id.confirmButton).setOnClickListener(v -> {
                Event event = new Event("change",new Dict() {{
                    put("value", new JsString(getValue().toString()));
                }},this,this,0);
                dispatchEvent(event);

            bottomSheetDialog.hide();
        });
        ///////////////////////
        this.setOnClickListener(v -> {
            int viewID = getResources().getIdentifier(getMode().toString(), "id", getContext().getPackageName());
            bottomSheetDialog.findViewById(viewID).setVisibility(View.VISIBLE);
            switch (getMode()) {
                case selector:
                    selector();
                    break;
                case multiSelector:
                    multiSelector();
                    break;
                case time:
                    time();
                    break;
                case date:
                    date();
                    break;
                case region:
                    region();
                    break;
                default:
                    break;
            }
            bottomSheetDialog.show();
        });
    }

    private Mode _mode = Mode.selector;

    public void setMode(Mode mode) {
        _mode = mode;

    }

    public Mode getMode() {
        return _mode;
    }

    String _name;
    @Override
    public void setName(String name) {
        _name=name;
    }

    @Override
    public String getName() {
        return _name;
    }



    private JsObject _value = null;


    @Override
    public void setValue(JsObject value) {
        _value = value;
    }

    @Override
    public JsObject getValue() {
        return _value;
    }

    @Override
    public void reset() {

    }

    private void _picker(NumberPicker numberPicker, List  data) {
        final List<String> names=new ArrayList();
        if (getRangeKey() != null) {
            data.forEach(o -> names.add(((JsString) ((Dict)o).get(getRangeKey())).THIS));

        } else {
            data.forEach(o -> names.add((String) o));
        }
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(names.size() - 1);
        numberPicker.setDisplayedValues(names.toArray(new String[names.size()]));

    }

    ////////////////////
    private List _range;

    public void setRange(List  range) {
        _range = range;
    }

    public List  getRange() {
        return _range;
    }

    //
    private String _rangeKey;

    public void setRangeKey(String rangeKey) {
        _rangeKey = rangeKey;
    }

    public String getRangeKey() {
        return _rangeKey;
    }

    //
    private String _start;

    public void setStart(String start) {
        _start = start;
    }

    public String getStart() {
        return _start;
    }

    //
    private String _end;

    public void setEnd(String end) {
        _end = end;
    }

    public String getEnd() {
        return _end;
    }

    //
    private Fields _fields = Fields.day;

    public void setFields(Fields fields) {
        _fields = fields;
    }

    public Fields getFields() {
        return _fields;
    }

    //
    private String _customItem;

    public void setCustomItem(String customItem) {
        _customItem = customItem;
    }

    public String getCustomItem() {
        return _customItem;
    }

    //
    private int[] _regionIndexes = new int[]{0, 0, 0};
    ////////////

    private void selector() {
        NumberPicker selector = bottomSheetDialog.findViewById(R.id.selector);
        _picker(selector, getRange());
        if (getValue() != null) {
            selector.setValue(((JsNumber) getValue()).THIS.intValue());
        }
        selector.setOnValueChangedListener((picker, oldVal, newVal) -> {
            setValue(new JsNumber(newVal));
        });
    }

    private void multiSelector() {
        ViewGroup multiSelector = bottomSheetDialog.findViewById(R.id.multiSelector);
        for (int c = 0; c < getRange().size(); c++) {
            List data = (List ) getRange().get(c);
            //
            NumberPicker selector = new NumberPicker(getContext());
            selector.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            selector.setWrapSelectorWheel(false);
            selector.setTag(c);
            multiSelector.addView(selector);
            //
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) selector.getLayoutParams();
            lp.weight = 1;
            selector.setLayoutParams(lp);
            //
            _picker(selector, data);
            if (getValue() != null) {
                JsObject v= ((Array) getValue()).get(c);
                selector.setValue(((JsNumber)v).THIS.intValue());
            }
            selector.setOnValueChangedListener((picker, oldVal, newVal) -> {
                final int column = (int) picker.getTag();

                    Event event = new Event("columnChange",new Dict() {{

                            put("column",new JsNumber( column));
                            put("value", new JsString( getValue().toString()));

                    }},this,this,0);
                     dispatchEvent(event);

                ////////////
                Array value = (Array) getValue();
                if (value == null) {
                    value = new Array(new JsNumber(getRange().size()));
                }
                value.set(column,new JsNumber(newVal));
                setValue(value);
            });
        }

    }

    private void time_set(TimePicker time, String value) {
        String[] hour_minute = (((JsString) getValue()).THIS).split(":");
        time.setHour(Integer.valueOf(hour_minute[0]));
        time.setMinute(Integer.valueOf(hour_minute[1]));
    }

    private void date_set(DatePicker date, String value) {
        String[] year_month_day = (((JsString) getValue()).THIS).split("-");
        int year = Integer.valueOf(year_month_day[0]);
        int month = Integer.valueOf(year_month_day[1]);
        int day = Integer.valueOf(year_month_day[1]);
        date.init(year, month, day, null);
    }

    @SuppressLint("DefaultLocale")
    private void time() {
        TimePicker time = bottomSheetDialog.findViewById(R.id.time);
        assert time != null;
        time.setIs24HourView(true);
        if (getValue() != null) {
            time_set(time,((JsString) getValue()).THIS);
        }
        time.setOnTimeChangedListener((picker, hour, minute) -> {
            /*if(getStart()!=null){
                String[] start = getStart().split("\\:");
                int start_hour = Integer.valueOf(start[0]);
                int start_minute = Integer.valueOf(start[1]);
                if(hour<start_hour || minute<start_minute){
                    setValue(getStart());
                    time_set(time,  getStart());
                    return;
                }
            }
            if(getEnd()!=null){
                String[] end = getEnd().split("\\:");
                int end_hour = Integer.valueOf(end[0]);
                int end_minute = Integer.valueOf(end[1]);
                if(hour>end_hour || minute>end_minute){
                    setValue(getEnd());
                    time_set(time, getEnd());
                    return;
                }
            }*/
            setValue(new JsString(String.format("%d:%d", hour, minute)));
        });
    }

    @SuppressLint("SimpleDateFormat")
    private void date() {
        try {
            DatePicker date = bottomSheetDialog.findViewById(R.id.date);
            assert date != null;
            switch (getFields()) {
                case year: {
                    int monthSpinnerId = Resources.getSystem().getIdentifier("month", "id", "android");
                    if (monthSpinnerId != 0) {
                        View monthSpinner = date.findViewById(monthSpinnerId);
                        if (monthSpinner != null) {
                            monthSpinner.setVisibility(View.GONE);
                        }
                    }
                    int daySpinnerId = Resources.getSystem().getIdentifier("day", "id", "android");
                    if (daySpinnerId != 0) {
                        View daySpinner = date.findViewById(daySpinnerId);
                        if (daySpinner != null) {
                            daySpinner.setVisibility(View.GONE);
                        }
                    }
                }
                break;
                case month: {
                    int daySpinnerId = Resources.getSystem().getIdentifier("day", "id", "android");
                    if (daySpinnerId != 0) {
                        View daySpinner = date.findViewById(daySpinnerId);
                        if (daySpinner != null) {
                            daySpinner.setVisibility(View.GONE);
                        }
                    }
                }
                break;
                case day:
                    break;
                default:
                    break;
            }
            if (getStart() != null) {
                date.setMinDate(new SimpleDateFormat("yyyy-MM-dd").parse(getStart()).getTime());
            }
            if (getEnd() != null) {
                date.setMaxDate(new SimpleDateFormat("yyyy-MM-dd").parse(getEnd()).getTime());
            }
            if (getValue() != null) {
                date_set(date,  ((JsString) getValue()).THIS);
            }
            date.setOnDateChangedListener((view, year, month, day) -> setValue(new JsString(String.format("YYYY-MM-DD", year, month, day))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    JSONArray _provinces;

    private void region() {
        try {
            ViewGroup region = bottomSheetDialog.findViewById(R.id.region);
            try (InputStreamReader inputReader = new InputStreamReader(getResources().openRawResource(R.raw.region))) {
                try (BufferedReader bufReader = new BufferedReader(inputReader)) {
                    String line = "";
                    StringBuffer sb = new StringBuffer();
                    while ((line = bufReader.readLine()) != null) {
                        sb.append(line);
                    }
                    _provinces = new JSONArray(sb.toString());
                    //
                    Array value = (Array) getValue();
                    if (value == null) {
                        value = new Array();
                        JSONArray buffers = _provinces;
                        for (int i = 0; i < childNames.length + 1; i++) {
                            if (getCustomItem() != null) {
                                value.add(new JsString(getCustomItem()));
                            } else {
                                if (i < 2) {
                                    value.add(new JsString(buffers.getJSONObject(0).getString("name")));
                                } else {
                                    value.add(new JsString(buffers.getString(0)));
                                }
                                if (i < 2) {
                                    buffers = buffers.getJSONObject(0).getJSONArray(childNames[i]);
                                }
                            }
                        }
                        setValue(value);
                    }
                    //
                    _region(region, 0, _provinces, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final String[] childNames = new String[]{"city", "area"};
    private void _region_change(ViewGroup region,int column,int index) {
        try {
            _regionIndexes[column] = index;
            final Array value = (Array) getValue();
            if (getCustomItem() != null) {
                JSONArray buffers = _provinces;
                if(index==0){
                    value.set(column, new JsString(getCustomItem()));
                }else {
                    for (int c = 0; c < column; c++) {
                        buffers = buffers.getJSONObject(_regionIndexes[c]-1).getJSONArray(childNames[c]);
                    }
                    value.set(column, new JsString(column < childNames.length ? buffers.getJSONObject(index - 1).getString("name") : buffers.getString(index-1)));

                }
                for (int c = column + 1; c < childNames.length + 1; c++) {
                    value.set(c, new JsString(getCustomItem()));
                }
                setValue(value);
                if (column < childNames.length) {
                    JSONArray dat;
                    if (index == 0) {
                        dat = new JSONArray();
                    } else {
                        dat = buffers.getJSONObject(index-1).getJSONArray(childNames[column]);
                    }
                    _region(region, column + 1, dat, value);
                }
            }else {
                //////////////////////
                JSONArray buffers = _provinces;
                JSONArray data = null;
                for (int c = 0; c < childNames.length + 1; c++) {
                    if (c >= column) {
                        int index2 = (c == column ? index : 0);
                        value.set(c, new JsString(c < childNames.length ? buffers.getJSONObject(index2).getString("name") : buffers.getString(index2)));
                    }
                    if (c == column) {
                        data = buffers;
                    }
                    if (c < childNames.length) {
                        buffers = buffers.getJSONObject(_regionIndexes[c]).getJSONArray(childNames[c]);
                    }
                }
                setValue(value);
                //
                if (column < childNames.length) {
                    JSONArray dat = data.getJSONObject(index).getJSONArray(childNames[column]);
                    _region(region, column + 1, dat, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void _region(final ViewGroup region, final int column, final JSONArray data, final List value) throws JSONException {
        final NumberPicker numberPicker = (NumberPicker) region.getChildAt(column);
        numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            _region_change(region, Integer.parseInt((String) picker.getTag()),newVal);
        });
        numberPicker.setWrapSelectorWheel(false);
        //
        final List<String> names = new ArrayList();
        if (getCustomItem() != null) {
            names.add(getCustomItem());
        }
        for (int d = 0; d < data.length(); d++) {
            String name = column < childNames.length ? data.getJSONObject(d).getString("name") : data.getString(d);
            names.add(name);
        }
        int index = names.indexOf(value.get(column));
        _regionIndexes[column] = index;
        //
        numberPicker.setValue(0);
        numberPicker.setMaxValue(0);
        numberPicker.setDisplayedValues(names.toArray(new String[0]));
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(names.size() - 1);
        numberPicker.setValue(index);
        //
        if (column < childNames.length) {
            JSONArray dat;
            if (getCustomItem() != null) {
                if (index == 0) {
                    dat = new JSONArray();
                } else {
                    dat = data.getJSONObject(index-1).getJSONArray(childNames[column]);
                }
            } else {
                dat = data.getJSONObject(index).getJSONArray(childNames[column]);
            }
            _region(region, column + 1, dat, value);
        }
    }

}