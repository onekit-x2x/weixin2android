package demo.weixin.app;


import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

public class MapDemo extends Activity {
    ViewGroup root(){
        return this.findViewById(R.id.root);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.demo_map);
//        Map map=new Map(this);
//        root().addView(map);

       // d.put("iconPath","resources/test.jpg");
//        List<Map> p=new ArrayList();
//        p.add(new Dict(){{put("latitude",23.10229);put("longitude",113.3245211);}});
//        p.add(new Dict(){{put("latitude",23.21229);put("longitude",113.324520);}});
//        p.add(new Dict(){{put("latitude",23.22229);put("longitude",113.334520);}});
//        HashMap d=new HashMap();
//        d.put("latitude",23.10229);
//        d.put("longitude",113.3245211);
//        d.put("color","#0000FF");
//        d.put("strokeWidth",10);
//        d.put("fillColor","#00FF00");
//        d.put("radius",50);
//        Array t=new Array(d);
//        map.setPolygon(t);
//        Dict d1=new Dict(){{
//            put("title","腾讯");
//            put("latitude",23.099994);
//            put("longitude",113.324520);
//        }};
//        map.setOnPoitapListener(new Map.OnPoitapListener() {
//            @Override
//            public void onPoitap(Event event) {
//                Log.d("logd", "111111111");
//                Log.d("name", event.detail.toString());
//                Log.d("logd", "111111111");
//            }
//        });
//        List<Map> t=new ArrayList(){{add(d);}};
        //map.setCircles(t);
    }
}
