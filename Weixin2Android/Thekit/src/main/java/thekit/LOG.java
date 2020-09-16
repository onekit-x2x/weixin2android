package thekit;

import android.util.Log;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LOG {
    String tag;
    List<Date> times = new ArrayList();
    public LOG(String tag){
        this.tag = tag;
        times.add(new Date());
    }

    public void add(String label) {
        Date time = new Date();
        Log.e(tag+"==================" + label, String.valueOf(time.getTime() - times.get(times.size() - 1).getTime()));
        times.add(time);
    }
}
