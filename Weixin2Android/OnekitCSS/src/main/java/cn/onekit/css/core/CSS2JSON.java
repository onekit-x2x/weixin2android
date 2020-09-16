package cn.onekit.css.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSS2JSON {

    String parseKeyframes(Map<String, Object> keyframes, String css2, int open) {
        int count = 0;
        int name0 = css2.indexOf(" ", open);
        int begin0 = css2.indexOf("{", open);
        if (begin0 < 0) {
            return css2;
        }
        String name = css2.substring(name0 + 1, begin0 - 1).trim();
        int begin, end;
        int temp = open;
        do {
            begin = css2.indexOf("{", temp);
            end = css2.indexOf("}", temp);
            if (begin < 0 && end < 0) {
                break;
            }
            if (begin >= 0 && (end < 0 || begin < end)) {
                count++;
                temp = begin + 1;
            } else if (end >= 0 && (begin < 0 || end < begin)) {
                count--;
                temp = end + 1;
            }
        } while (count > 0);
        Map<String, Object> keyframe = parse(css2.substring(begin0 + 1, end));
        keyframes.put(name, keyframe);
        css2 = css2.substring(0, open) + css2.substring(end + 1);
        return css2;
    }

    List _get(List json, String key) {
        for (Object temp : json) {
            Map<String, Object> item = (Map) temp;
            if (item.get("key").equals(key)) {
                return (List) item.get("value");
            }
        }
        return null;
    }

    void _set(List json, String key, Object value) {
        json.add(new HashMap<String, Object>() {
            {
                put("key", key);
                put("value", value);
            } });
    }

    // Remove all comments from the css-file
    Map<String, String> toObject(List<String> array) {
        Map<String, String> ret = new HashMap<>();
        for (String elm : array) {
            int index = elm.indexOf(":");
            String property = elm.substring(0, index).trim();

            ret.put(property, elm.substring(index + 1).trim());
        }
        return ret;
    }

    public Map<String, Object> parse(String css) {
        Map<String, Object> keyframes = new HashMap<>();

        int open, close;
        while ((open = css.indexOf("/*")) != -1 &&
                (close = css.indexOf("*/")) != -1) {
            css = css.substring(0, open) + css.substring(close + 2);
        }

        // Initialize the return value _json_.
        List<Map> json = new ArrayList<>();
        // @import
        while ((open = css.indexOf("@import")) != -1 &&
                (close = css.indexOf(";", open)) != -1) {
            String import_css = css.substring(open, close);
            String[] array = import_css.split(" ");
            String path = array[1].trim();
            path = path.substring(1, path.length() - 1);
            String finalPath = path;
            json.add(new HashMap<String, String>() {
                {
                    put("key", "import");
                    put("value", finalPath);
                } });
            css = css.substring(0, open) + css.substring(close + 1);
        }
        // @meadia
        while ((open = css.indexOf("@media")) != -1) {
            int count = 0;
            int begin0 = css.indexOf("{", open);
            if (begin0 < 0) {
                break;
            }
            int begin, end;
            int temp = open;
            do {
                begin = css.indexOf("{", temp);
                end = css.indexOf("}", temp);
                if (begin < 0 && end < 0) {
                    break;
                }
                if (begin >= 0 && (end < 0 || begin < end)) {
                    count++;
                    temp = begin + 1;
                } else if (end >= 0 && (begin < 0 || end < begin)) {
                    count--;
                    temp = end + 1;
                }
            } while (count > 0);
            css = css.substring(0, end) + css.substring(end + 1);
            css = css.substring(0, open) + css.substring(begin0 + 1);
        }
        //
        // @keyframes
        while ((open = css.indexOf("@keyframes")) != -1) {
            css = parseKeyframes(keyframes, css, open);
        }
        while ((open = css.indexOf("@-webkit-keyframes")) != -1) {
            css = parseKeyframes(keyframes, css, open);
        }
        //

        // Each rule gets parsed and then removed from _css_ until all rules have been
        // parsed.
        while (css.length() > 0) {
            // Save the index of the first left bracket and first right bracket.
            int lbracket = css.indexOf('{');
            int rbracket = css.indexOf('}');
            String[] array1 = css.substring(lbracket + 1, rbracket).split(";");

            List<String> declarations = new ArrayList<>();
            for (String item1 : array1) {
                String declaration = item1.trim();
                if (declaration.length() > 0) {
                    declarations.add(declaration);
                }
            }

            List temp = new ArrayList();
            for (int j = 0; j < declarations.size(); j++) {
                String declaration = declarations.get(j);
                int begin = declaration.lastIndexOf("(");
                int end = declaration.lastIndexOf(")");
                if (begin >= 0 && begin > end) {
                    String next = declarations.get(j + 1);
                    declaration += ";" + next;
                    j++;
                }
                temp.add(declaration);
            }
            declarations = temp;
            // Remove any empty ("") values from the array

            // _declaration_ is now an array reado to be transformed into an obj.
            Map<String, String> declarations_2 = toObject(declarations);

            // ## Part 2: The selectors
            //
            // Each selector in the selectors block will be associated with the
            // declarations defined above. For example, `h1, p#bar {color: red}`<br/>
            // result in the obj<br/>
            // {"h1": {color: red}, "p#bar": {color: red}}

            // Split the selectors block of the first rule into an array and remove
            // whitespace, e.g. `"h1, p#bar, span.foo"` get parsed to
            // `["h1", "p#bar", "span.foo"]`.
            String[] selectors = css.substring(0, lbracket).split(",");
            for (int i = 0; i < selectors.length; i++) {
                String selector = selectors[i];
                selectors[i] = selector.trim();
            }

            // Iterate through each selector from _selectors_.

            for (int i = 0; i < selectors.length; i++) {
                String selector = selectors[i];
                // Initialize the json-obj representing the declaration block of
                // _selector_.
                if (_get(json, selector)==null) {
                    _set(json, selector, new ArrayList<>());
                }
                // Save the declarations to the right selector
                for (Map.Entry<String, String> entry : declarations_2.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    String important = "!important";
                    String priority;
                    int p = value.indexOf(important);
                    if (p >= 0) {
                        value = value.substring(0, p).trim();
                        priority = "important";
                    } else {
                        priority = "";
                    }
                    String finalValue = value;
                    _get(json, selector).add(new HashMap() {
                        {
                            put("key", key);
                            put("value", finalValue);
                            put("priority", priority);
                        }
                    });
                }

            }

            // Continue to next instance
            css = css.substring(rbracket + 1).trim();
        }
        // return the json data
        //Log.e("======================", JSON.stringify(json));
        return new HashMap() {{
            put("css", json);
            put("keyframes", keyframes);
        }};
    }
}
