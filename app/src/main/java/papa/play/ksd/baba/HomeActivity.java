package papa.play.ksd.baba;

import android.graphics.Color;
import android.os.Bundle;
import android.app.ListActivity;
import android.widget.SimpleAdapter;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import android.view.Window;
import android.view.WindowManager;
public class HomeActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.simple, new String[] { "title",  "img","publishtime","img1" ,"img2","img3","img4","img5","img6","img7","img8","img9"}, new int[] { R.id.title, R.id.img,R.id.publishtime
                ,R.id.img1,R.id.img2,R.id.img3,R.id.img4,R.id.img5,R.id.img6,R.id.img7,R.id.img8,R.id.img9});
        setListAdapter(adapter);


    }
    private List<Map<String, Object>> getData() {
        //map.put(参数名字,参数值)
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "摩托罗拉");
        map.put("publishtime", "昨天");
        map.put("img", R.drawable.ic_launcher);
        map.put("img1", R.drawable.ic_launcher);
        map.put("img2", R.drawable.ic_launcher);
        map.put("img3", R.drawable.ic_launcher);
        map.put("img4", R.drawable.ic_launcher);
        map.put("img5", R.drawable.ic_launcher);
        map.put("img6", R.drawable.ic_launcher);
        map.put("img7", R.drawable.ic_launcher);
        map.put("img8", R.drawable.ic_launcher);
        map.put("img9", R.drawable.ic_launcher);
        list.add(map);
        list.add(map);
        list.add(map);
        list.add(map);
        list.add(map);
        list.add(map);
        list.add(map);
        return list;
    }

    @Override
    protected void onResume(){
        super.onResume();

    }
}