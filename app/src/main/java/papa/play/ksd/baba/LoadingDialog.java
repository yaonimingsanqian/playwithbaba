package papa.play.ksd.baba;

/**
 * Created by test2 on 14-10-15.
 */
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoadingDialog extends Dialog {
    private TextView tv;
    public Button btnCamer;
    public Button btnAlbum;
    Context mcontext;
    public LoadingDialog(Context context) {
        super(context, R.style.loadingDialogStyle);
        mcontext = context;
    }

    private LoadingDialog(Context context, int theme) {
        super(context, theme);
        mcontext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
//        tv = (TextView)this.findViewById(R.id.tv);
//        tv.setText("   正在跳转...");
//        LinearLayout linearLayout = (LinearLayout)this.findViewById(R.id.LinearLayout);
//        if(linearLayout  != null){
//            linearLayout.getBackground().setAlpha(210);
//        }else{
//            Log.v("test","null");
//        }

        LinearLayout linearLayout = (LinearLayout)this.findViewById(R.id.linerlayout);
        btnAlbum = (Button)linearLayout.findViewById(R.id.btn1);
        btnAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("btnAlbum","btnAlbum");
                mcontext.sendBroadcast(new Intent("papa.play.ksd.baba.album"));
            }
        });
        btnCamer = (Button)linearLayout.findViewById(R.id.btn2);
        btnCamer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcontext.sendBroadcast(new Intent("papa.play.ksd.baba.camer"));
                Log.v("btnCamer","btnCamer");
            }
        });

    }
}