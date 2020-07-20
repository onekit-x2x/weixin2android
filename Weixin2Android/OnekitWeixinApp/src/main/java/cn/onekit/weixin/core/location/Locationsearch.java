package cn.onekit.weixin.core.location;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;

import cn.onekit.js.Array;
import cn.onekit.js.Dict;
import cn.onekit.js.JSON;
import cn.onekit.js.core.JsNumber;
import cn.onekit.js.core.JsString;
import cn.onekit.weixin.app.R;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by lenovo on 2017/9/5.
 */

public class Locationsearch extends Activity {
    private ImageView locationsearchimageview;
    private EditText Locationsearchedittext;
    private ListView location_search_listview;
    private String edittext;
    private Double latitude;
    private Double longitude;
    private Array locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onekit_activity_location_search);
        init();
        initview();
        OnClickListener();
    }

    private void init() {
        Bundle bundle = this.getIntent().getExtras();
        latitude = bundle.getDouble("latitude");
        longitude = bundle.getDouble("longitude");
    }

    private void initview() {
        locationsearchimageview = (ImageView) findViewById(R.id.locationsearchimageview);
        Locationsearchedittext = (EditText) findViewById(R.id.Locationsearchedittext);
        Locationsearchedittext.addTextChangedListener(textWatcher);
        location_search_listview = (ListView) findViewById(R.id.location_search_listview);
    }

    private void OnClickListener() {
        locationsearchimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void SearchGet() {
        String format = "http://apis.map.qq.com/ws/place/v1/suggestion/?orderby=distance(%s,%s)&region_fix=0&keyword=%s&key=%s";
        String url = String.format(format, latitude, longitude, edittext, "3EBBZ-EZYK6-ASKS2-EVXMH-OXEAS-U3FVZ");
        new LoadOkHttpClientTask().execute(url);
    }

    class LoadOkHttpClientTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(params[0])
                    .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            Dict json = (Dict) JSON.parse(s);
//            locations = json.getArray("data");
            locations = (Array) json.get("data");
            location_search_listview.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return locations.size();
                }

                @Override
                public Object getItem(int i) {
                    return null;
                }

                @Override
                public long getItemId(int i) {
                    return 0;
                }

                @Override
                public View getView(int i, View view, ViewGroup viewGroup) {
                    ViewHolder holder = null;
                    if (view == null) {
                        view = LayoutInflater.from(Locationsearch.this).inflate(
                                R.layout.onekit_chooselocation_list_item, null);
                        holder = new ViewHolder(view);
                        view.setTag(holder);
                    } else {
                        holder = (ViewHolder) view.getTag();
                    }
//                    Dict location = locations.getObject(i);
                    Dict location = (Dict) locations.get(i);
                    holder.text_name.setText(((JsString) location.get("title")).THIS);
                    holder.text_address.setText(((JsString) location.get("address")).THIS);
                    holder.image.setVisibility(View.GONE);
                    return view;
                }
            });

            location_search_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    Dict location = locations.getObject(i);
//                    Dict s = location.getObject("location");
                    Dict location = (Dict) locations.get(i);
                    Dict s = (Dict) location.get("location");
                    Intent intent = new Intent();
                    intent.putExtra("lat", ((JsNumber)s.get("lat")).THIS);
                    intent.putExtra("lng", ((JsNumber)s.get("lng")).THIS);
                    setResult(888888, intent);
                    finish();
                }
            });
        }
    }

    static class ViewHolder {
        private TextView text_name;
        private TextView text_address;
        private final ImageView image;

        public ViewHolder(View v) {
            text_name = (TextView) v.findViewById(R.id.text_name);
            text_address = (TextView) v.findViewById(R.id.text_address);
            image = (ImageView) v.findViewById(R.id.image);
        }
    }


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            edittext = Locationsearchedittext.getText().toString();
            SearchGet();
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };
}
