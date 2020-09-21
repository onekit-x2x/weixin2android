package cn.onekit.weixin.core.wx;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import thekit.android.Android;
import cn.onekit.js.JsObject;
import cn.onekit.js.core.function;
import cn.onekit.weixin.app.R;
import cn.onekit.weixin.core.page.PullToRefreshLayout;
import cn.onekit.weixin.core.res.wx_fail;

public class WxPullDownRefresh extends WxPhoneContact {
    private AbsListView alv;
    private PullToRefreshLayout refreshLayout;

    public void init() {
       /* alv = (AbsListView) Android.context.findViewById(R.id.content_view);
        refreshLayout = (PullToRefreshLayout) Android.context.findViewById(R.id.refresh_view);
        refreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                Toast.makeText(Android.context, "测试", Toast.LENGTH_SHORT).show();

                // 下拉刷新操作
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {

                    }
                }.sendEmptyMessageDelayed(0, 5000);
            }
        });

        initGridView();*/
    }

    /**
     * GridView初始化方法
     */
    private void initGridView() {
        List items = new ArrayList();
        for (int i = 0; i < 30; i++) {
            items.add("这里是item " + i);
        }
        alv.setAdapter(new MyAdapter(Android.context, items));
        alv.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return true;
            }
        });
        alv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }

    public void startPullDownRefresh(Map OBJECT) {
        try {
            refreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
                @SuppressLint("HandlerLeak")
                @Override
                public void onRefresh() {
                    // 下拉刷新操作
                    new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            // 千万别忘了告诉控件刷新完毕了哦！
                            Toast.makeText(Android.context, "RefreshTest", Toast.LENGTH_SHORT).show();
                        }
                    }.sendEmptyMessageDelayed(0, 5000);
                }
            });

            if (OBJECT.containsKey("success")) {
                function success = (function) OBJECT.get("success");
                JsObject res = new JsObject();
//                res.errMsg = Android.context.getResources().getString(R.string.wx_startPullDownRefresh_success);
                success.invoke(res);
            }
            if (OBJECT.containsKey("complete")) {
                function complete = (function) OBJECT.get("complete");
                JsObject res = new JsObject();
//                res.errMsg = Android.context.getResources().getString(R.string.wx_startPullDownRefresh_success);
                complete.invoke(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_startPullDownRefresh_fail));
//            res.errMsg = Android.context.getResources().getString(R.string.wx_startPullDownRefresh_fail);
            if (OBJECT.containsKey("fail")) {
                function fail = (function) OBJECT.get("fail");
                fail.invoke(res);
            }
            if (OBJECT.containsKey("complete")) {
                function complete = (function) OBJECT.get("complete");
                complete.invoke(res);

            }
        }
    }

    public void stopPullDownRefresh(Map OBJECT) {
        function success = OBJECT.get("success") != null ? (function) OBJECT.get("success") : null;
        function complete = OBJECT.get("complete") != null ? (function) OBJECT.get("complete") : null;
        function fail = OBJECT.get("fail") != null ? (function) OBJECT.get("fail") : null;
        try {
            refreshLayout.refreshFinish(PullToRefreshLayout.REFRESH_SUCCEED);
            JsObject res = new JsObject();
//            res.errMsg = Android.context.getResources().getString(R.string.wx_stopPullDownRefresh_success);
            if (success != null) {
                success.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            wx_fail res = new wx_fail(Android.context.getResources().getString(R.string.wx_stopPullDownRefresh_fail));
//            res.errMsg = Android.context.getResources().getString(R.string.wx_stopPullDownRefresh_fail);
            if (fail != null) {
                fail.invoke(res);
            }
            if (complete != null) {
                complete.invoke(res);
            }
        }
    }

    class MyAdapter extends BaseAdapter {
        List items;
        Context context;

        public MyAdapter(Context context, List items) {
            this.context = context;
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(R.layout.onekit_list_item, null);
            TextView tv = (TextView) view.findViewById(R.id.name_tv);
            tv.setText(items.get(position).toString());
            return view;
        }
    }
}


