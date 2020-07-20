package cn.onekit.js;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import cn.onekit.js.core.Iterator;
import cn.onekit.js.core.JsBoolean;
import cn.onekit.js.core.JsNumber;
import cn.onekit.js.core.JsObject;
import cn.onekit.js.core.JsString;
import cn.onekit.js.core.Onekit_JS;
import cn.onekit.js.core.function;

public class Array extends ArrayList<JsObject> implements JsObject {

    public Array(List<JsObject> subList) {
        super(subList);
    }

    /////////////////////////////////////////
    public int getLength() {
        return this.size();
    }

    private void _setLength(long length) {
        if (length < 0 || length >= 4294967296L) {
            throw new RangeError(new JsString("Invalid array length"));
        }
        if (length > this.size()) {
            return;
        }
        for (long i = length; i <= this.size(); i++) {
            this.remove(this.size() - 1);
        }
    }

    public void setLength(JsObject length) {
        int length_ = Onekit_JS.number(length, 0, 0).intValue();
        _setLength(length_);
    }

    int _hashCode = new Random().nextInt();

    @Override
    public int hashCode() {
        return _hashCode;
    }

    private static int _index(Array array, int index) {
        if (index >= 0) {
            return index;
        }
        return array.size() + index;
    }

    public JsString ToString() {

        StringBuilder result = new StringBuilder();
        result.append("[");
        for (int i = 0; i < size(); i++) {
            JsObject element = get(i);
            if (i > 0) {
                result.append(",");
            }
            result.append(element == null ? "empty" : Onekit_JS.toString(element));

        }
        result.append("]");
        return new JsString(result.toString());
    }

    @Override
    public String toLocaleString(JsString locales, JsObject options) {
        return null;
    }

    @Override
    public JsObject invoke(JsObject... params) {
        return null;
    }

    @Override
    public JsObject get(String key) {
        return null;
    }

    //////////////////////////////////////
    public static Array from(JsObject arrayLike, JsObject mapFn, JsObject thisArg) {
        function fn = (function) mapFn;
        if (arrayLike instanceof Array) {
            return from((Array) arrayLike, fn, thisArg);
        } else if (arrayLike instanceof JsString) {
            return from(((JsString) arrayLike).THIS, fn, thisArg);
        } else {
            return null;
        }
    }

    private static Array from(Array array, JsObject mapFn, JsObject thisArg) {
        Array result = new Array();
        for (JsObject element : array) {
            if (mapFn != null) {
                result.add(mapFn.invoke(element));
            } else {
                result.add(element);
            }
        }
        return result;
    }

    private static Array from(String arrayLike, JsObject mapFn, JsObject thisArg) {
        return from(Onekit_JS.string2Array(arrayLike), mapFn, thisArg);
    }


    //
    public static boolean isArray(JsObject obj) {
        return obj instanceof Array;
    }

    //
    public static Array of(JsObject... elements) {
        Array result = new Array();
        Collections.addAll(result, elements);
        return result;
    }

    /////////////////////////////////////////
    public Array() {
    }

    public Array(JsObject length) {
        if (length == null) {
            add(null);
            return;
        }
        if (Onekit_JS.isNumber(length)) {
            long length_ = ((JsNumber) length).THIS.longValue();
            if (length_ <= 0 || length_ >= 4294967296L) {
                throw new Error(new JsString("Invalid array length"));
            }
            for (int i = 0; i < length_; i++) {
                this.add(null);
            }
        } else {
            add(length);
        }
    }

    public Array(JsObject... args) {

        Collections.addAll(this, args);

    }
    /////////////////////////////////////////////////

    public Array concat(JsObject... values) {
        Array RESULT = (Array) this.clone();
        for (JsObject value : values) {
            if (value instanceof Array) {
                Array array = (Array) value;
                RESULT.addAll(array);
            } else {
                RESULT.add(value);
            }
        }
        return RESULT;
    }

    //
    private Array _copyWithin(int target, int start, int end) {
        target = _index(this, target);
        start = _index(this, start);
        end = _index(this, end);
        Array result = new Array();
        result.addAll(this);
        for (int i = start, j = 0; i < end && i < result.size() && target + j < result.size(); i++, j++) {
            result.set(target + j, this.get(i));
        }
        return result;
    }

    public Array copyWithin(int target, int start, int end) {
        target = _index(this, target);
        start = _index(this, start);
        end = _index(this, end);
        Array result = new Array();
        result.addAll(this);
        for (int i = start, j = 0; i < end && i < result.size() && target + j < result.size(); i++, j++) {
            result.set(target + j, this.get(i));
        }
        return result;
    }

    public Array copyWithin(int target, int start) {
        return copyWithin(target, start, this.size() - 1);
    }

    public Array copyWithin(int target) {
        return copyWithin(target, 0);
    }
    //

    public Iterator entries() {
        return new Iterator(this.iterator()) {


            @Override
            public JsObject getValue(Object value) {
                return new Array() {{
                    add(new JsNumber(index++));
                    add((JsObject) value);
                }};
            }

            int index = 0;

        };
    }

    //
    public boolean every(function callback, JsObject thisArg) {
        callback.thisArg = thisArg;
        for (int i = 0; i < size(); i++) {
            JsObject element = get(i);
            if (element == null) {
                continue;
            }

            if (!Onekit_JS.is(callback.invoke(element, new JsNumber(i), this))) {
                return false;
            }
        }
        return true;
    }

    public boolean every(function callback) {
        return every(callback, null);
    }


    //
    private Array _fill(JsObject value, int start, int end) {
        start = _index(this, start);
        end = _index(this, end);
        for (int i = start; i >= 0 && i < end && i < size(); i++) {
            set(i, value);
        }
        return this;
    }

    public Array fill(JsObject value, JsObject start, JsObject end) {
        int start_ = Onekit_JS.number(start, 0, 0).intValue();
        int end_ = Onekit_JS.number(end, 0, 0).intValue();
        return _fill(value, start_, end_);
    }

    public Array fill(JsObject value, JsObject start) {
        return fill(value, start, new JsNumber(this.size()));
    }

    public Array fill(JsObject value) {
        return fill(value, new JsNumber(0));
    }

    //
    public Array filter(function callback, JsObject thisArg) {
        callback.thisArg = thisArg;
        Array result = new Array();
        for (int i = 0; i < size(); i++) {
            JsObject element = this.get(i);
            if (element == null) {
                continue;
            }
            if (Onekit_JS.is(callback.invoke(element))) {
                result.add(element);
            }
        }
        return result;
    }

    public Array filter(function callback) {
        return filter(callback, null);
    }

    //
    public JsObject find(function callback, JsObject thisArg) {
        callback.thisArg = thisArg;
        for (int i = 0; i < size(); i++) {
            JsObject element = this.get(i);
            if (Onekit_JS.is(callback.invoke(element, new JsNumber(i), this))) {
                return element;
            }
        }
        return null;
    }

    public JsObject find(function callback) {
        return find(callback, null);
    }

    //
    public Integer findIndex(function callback, JsObject thisArg) {
        callback.thisArg = thisArg;
        for (int i = 0; i < size(); i++) {
            JsObject element = this.get(i);
            if (element == null) {
                continue;
            }
            if (Onekit_JS.is(callback.invoke(element, new JsNumber(i), this))) {
                return i;
            }
        }
        return -1;
    }

    public Integer findIndex(function callback) {
        return findIndex(callback, null);
    }

    //
    private Array _flat(int depth, int current) {
        Array result = new Array();
        for (JsObject element : this) {
            if (element instanceof Array && current < depth) {
                Array array = (Array) element;
                result = result.concat(array._flat(depth, current + 1));
            } else {
                result.add(element);
            }
        }
        return result;
    }

    public Array flat(int depth) {
        return _flat(depth, 0);
    }

    public Array flat() {
        return flat(1);
    }

    //
    public Array flatMap(function callback, JsObject thisArg) {
        callback.thisArg = thisArg;
        Array result = new Array();
        for (int i = 0; i < size(); i++) {
            JsObject element = this.get(i);
            if (element == null) {
                continue;
            }
            result.add(callback.invoke(element, new JsNumber(i), this).get(new JsNumber(0)));
        }
        return result;
    }

    public Array flatMap(function callback) {
        return flatMap(callback, null);
    }

    //
    public void forEach(function callback, JsObject thisArg) {
        callback.thisArg = thisArg;
        for (int i = 0; i < size(); i++) {
            JsObject element = this.get(i);
            if (element == null) {
                continue;
            }
            callback.invoke(element, new JsNumber(i), this);
        }

    }

    public void forEach(function callback) {
        forEach(callback, null);
    }

    //
    public JsBoolean includes(JsObject valueToFind, JsObject fromIndex) {
        int index = Onekit_JS.number(fromIndex, 0, 0).intValue();

        for (int i = index; i < size(); i++) {
            if (i < 0) {
                continue;
            }
            JsObject element = this.get(i);
            if (element.equals(valueToFind)) {
                return new JsBoolean(true);
            }
        }
        return new JsBoolean(false);
    }

    //
    public JsNumber indexOf(JsObject searchElement, JsObject fromIndex) {
        int f = Onekit_JS.number(fromIndex, 0, 0).intValue();
        f = _index(this, f);
        for (int i = f; i < size(); i++) {
            JsObject temp = get(i);
            if (searchElement.equals(temp)) {
                return new JsNumber(i);
            }
        }
        return new JsNumber(-1);
    }

    public JsNumber indexOf(JsObject searchElement) {
        return indexOf(searchElement, new JsNumber(0));
    }

    //
    private String _join(String separator) {
        return StringUtils.join(this, separator);
    }

    public JsString join(JsObject separator) {
        return new JsString(_join(separator.toString()));
    }

    public JsString join() {
        return join(new JsString(","));
    }

    public Iterator keys() {
        return new Iterator(this.iterator()) {

            @Override
            public JsObject getValue(Object value) {
                return new JsNumber(index++);
            }

            int index = 0;
        };
    }

    public int lastIndexOf(JsObject searchElement, int fromIndex) {
        fromIndex = _index(this, fromIndex);
        for (int i = fromIndex; i >= 0; i--) {
            JsObject temp = get(i);
            if (searchElement.equals(temp)) {
                return i;
            }
        }
        return -1;
    }

    public int lastIndexOf(JsObject searchElement) {
        return lastIndexOf(searchElement, size() - 1);
    }

    //
    public Array map(JsObject callback, JsObject thisArg) {
        ((function)callback).thisArg = thisArg;
        Array result = new Array();
        for (int i = 0; i < size(); i++) {
            JsObject element = this.get(i);
            if (element == null) {
                continue;
            }
            result.add(callback.invoke(element));
        }
        return result;
    }

    public Array map(JsObject callback) {
        return map(callback, null);
    }
    //

    public JsObject pop() {
        if (this.size() == 0) {
            return null;
        }
        return remove(this.size() - 1);
    }

    public JsNumber push(JsObject... elements) {
        if (elements == null) {
            add(null);
        } else {
            this.addAll(Arrays.asList(elements));
        }
        return new JsNumber(this.size());
    }

    public JsObject reduce(function callback, JsObject initialValue) {
        boolean flag = (initialValue == null);
        if (flag) {
            initialValue = get(0);
        }
        for (int i = (flag ? 1 : 0); i < size(); i++) {
            JsObject element = this.get(i);
            if (element == null) {
                continue;
            }
            initialValue = callback.invoke(initialValue, element, new JsNumber(i), this);
        }
        return initialValue;
    }

    public JsObject reduce(function callback) {
        return reduce(callback, null);
    }

    public JsObject reduceRight(function callback, JsObject initialValue) {
        boolean flag = initialValue == null;
        if (flag) {
            initialValue = get(size() - 1);
        }
        for (int i = size() - (flag ? 2 : 1); i >= 0; i--) {
            JsObject element = this.get(i);
            if (element == null) {
                continue;
            }
            initialValue = callback.invoke(initialValue, element, new JsNumber(i), this);
        }
        return initialValue;
    }

    public JsObject reduceRight(function callback) {
        return reduceRight(callback, null);
    }

    public Array reverse() {
        Array temp = new Array();
        for (int i = size() - 1; i >= 0; i--) {
            temp.add(get(i));
        }
        clear();
        addAll(temp);
        return this;
    }

    public JsObject shift() {
        return this.remove(0);
    }

    private Array _slice(int start, int end) {
        return new Array(this.subList(start, end));
    }

    public Array slice(JsObject start, JsObject end) {
        int start_ = Onekit_JS.number(start, 0, 0).intValue();
        int end_ = Onekit_JS.number(end, 0, 0).intValue();
        return _slice(start_, end_);
    }

    public Array slice(JsObject start) {
        return slice(start, new JsNumber(this.size() - 1));
    }

    public Array slice() {
        return slice(new JsNumber(0));
    }

    public boolean some(function callback, JsObject thisArg) {
        for (int i = 0; i < size(); i++) {
            JsObject element = this.get(i);
            if (element == null) {
                continue;
            }
            if (Onekit_JS.is(callback.invoke(element, new JsNumber(i), this))) {
                return true;
            }
        }
        return false;
    }

    public boolean some(function callback) {
        return some(callback, null);
    }

    public Array sort(function compareFunction) {
        Collections.sort(this, (o1, o2) -> ((JsNumber) compareFunction.invoke(o1, o2)).THIS.intValue());
        return this;
    }

    public Array sort() {

        Collections.sort(this, (o1, o2) -> {
            String str1 = o1.toString();
            String str2 = o2.toString();
            str1 = str1.length() > 0 ? str1.substring(0, 1) : "";
            str2 = str2.length() > 0 ? str2.substring(0, 1) : "";
            return str1.compareTo(str2);
        });
        return this;
    }

    public Array splice(int start, int deleteCount, JsObject... items) {
        Array result = new Array(subList(start, start + deleteCount));
        this.removeRange(start, start + deleteCount);
        for (JsObject element : items) {
            this.add(start++, element);
        }
        return result;
    }

    public Array splice(int start) {
        return splice(start, size() - start);
    }

    public String toLocaleString(JsObject locales, Dict options) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size(); i++) {
            JsObject element = this.get(i);
            if (i > 0) {
                result.append(",");
            }
            if (element == null) {
                result.append("null");
                continue;
            }
            String str;
            if (element instanceof Dict) {
                str = ((Dict) element).toLocaleString(locales, options).THIS;
            } else if (Onekit_JS.isNumber(element)) {
                str = ((JsNumber) element).toLocaleString(locales, options).THIS;
            } else if (element instanceof Date) {
                str = ((Date) element).toLocaleString(locales, options).THIS;
            } else {
                str = element.toString();
            }
            result.append(str);
        }
        return result.toString();
    }


    //
    public int unshift(JsObject... elements) {
        int i = 0;
        for (JsObject element : elements) {
            this.add(i++, element);
        }
        return this.size();
    }

    public Iterator values() {
        return new Iterator(this.iterator()) {

            @Override
            public JsObject getValue(Object value) {
                return (JsObject) value;
            }

        };
    }

    @Override
    public JsObject get(JsObject key) {
        int index = Onekit_JS.number(key, 0, 0).intValue();
        return super.get(index);
    }

    @Override
    public void set(String key, JsObject value) {
    }

    @Override
    public void set(JsObject key, JsObject value) {
        int index = Onekit_JS.number(key, 0, 0).intValue();
        set(index, value);
    }
}