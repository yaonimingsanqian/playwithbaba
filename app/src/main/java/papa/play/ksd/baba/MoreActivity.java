package papa.play.ksd.baba;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import cn.smssdk.SMSSDK;

/**
 * @author Adil Soomro
 *
 */
public class MoreActivity extends Activity {

    Button register;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.more_title);
        register = (Button)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MoreActivity.this,RegisterActivity.class));
            }
        });



    }
    public void doSearch(View s)
    {
        Toast.makeText(this, "I lied, I love KUNG FUuuuuuuuuUUuuu...!!", Toast.LENGTH_LONG).show();
    }
}
