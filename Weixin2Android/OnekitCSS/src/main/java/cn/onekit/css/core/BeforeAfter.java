package cn.onekit.css.core;

import android.content.Context;
import android.widget.TextView;
import cn.onekit.core.BeforeAfter_;

public   class BeforeAfter extends TextView implements BeforeAfter_ {
    public BeforeAfter(Context context) {
        super(context);
    }

    public void run() {
        CssLayoutParams layoutParams = (CssLayoutParams) this.getLayoutParams();
        String content = layoutParams.computedStyle.content;
        content = content.substring(1, content.length() - 1);
        this.setText(content);
    }
}