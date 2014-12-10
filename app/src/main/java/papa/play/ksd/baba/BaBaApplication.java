package papa.play.ksd.baba;

import android.app.Application;
import cn.smssdk.SMSSDK;

/**
 * Created by test2 on 14/12/5.
 */

/**
 * Created by kevin on 2014/10/8.
 */
public class BaBaApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SMSSDK.initSDK(this,"483c1aa4bd8a","6fc2872c43609f627a4aaaabd7345def");
    }

}