package papa.play.ksd.baba;

import java.io.ByteArrayOutputStream;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.os.Environment;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
@SuppressWarnings("deprecation")
public class AwesomeActivity extends TabActivity {
    TabHost tabHost;
    /** Called when the activity is first created. */
    private boolean isShow = false;
    LoadingDialog dialogLoading;
    BroadcastReceiver alnumPress = null;
    BroadcastReceiver camerPress = null;
    CustomDialog.Builder dialog = null;
    private static  String FILE_PATH ="/sdcard/ilock/syscamera.jpg";

    boolean isFolderExists(String strFolder)
    {
        File file = new File(strFolder);

        if (!file.exists())
        {
            if (file.mkdir())
            {
                return true;
            }
            else
                return false;
        }
        return true;
    }
    @Override
    public void finishActivity(int requestCode) {
        super.finishActivity(requestCode);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
        tabHost = getTabHost();
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                if(s.equals("Search")){
                    startActivity(new Intent(AwesomeActivity.this,MoreActivity.class));
                }
            }
        });
        isFolderExists("/sdcard/ilock/");
        setTabs();
        //FILE_PATH = Environment.getExternalStorageState()+"/ilock/syscamera.jpg";
        alnumPress = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent1) {
                isShow = false;
                dialogLoading.dismiss();
                Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);

                getImage.addCategory(Intent.CATEGORY_OPENABLE);

                getImage.setType("image/jpeg");

                startActivityForResult(getImage, 0);
            }
        };
       // FILE_PATH = getFilesDir().getAbsolutePath()+"syscamera.jpg";
        registerReceiver(alnumPress,new IntentFilter("papa.play.ksd.baba.album"));

        camerPress = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                isShow = false;
                dialogLoading.dismiss();
                Intent camera = new Intent();
                camera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                camera.addCategory(Intent.CATEGORY_DEFAULT);
                File file = new File(FILE_PATH);
                if(file.exists()){
                    file.delete();
                }
                Uri uri = Uri.fromFile(file);
                camera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(camera, 0);
                //startActivity(new Intent(AwesomeActivity.this,CameraActivity.class));

            }
        };
        registerReceiver(camerPress,new IntentFilter("papa.play.ksd.baba.camer"));
    }
    @Override
    protected void onActivityResult ( int requestCode , int resultCode , Intent data ) {
        if(requestCode == 1){

        }else{
            super.onActivityResult(requestCode, resultCode, data);
            Bitmap bitmap = PictureUtil.getSmallBitmap(FILE_PATH);
            if(bitmap == null){
                if(data == null){
                    return;
                }else{
                    bitmap = (Bitmap)data.getExtras().get("data");
                }

            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] bitmapByte = baos.toByteArray();
            Intent in = new Intent(this,CameraActivity.class);

            in.putExtra("bitmap", bitmapByte);
            startActivity(in);
        }


    }

    private void setTabs()
    {
        addTab("Home", R.drawable.tab_home, HomeActivity.class);
        addTab("Search", R.drawable.tab_search, MoreActivity.class);
    }
    private void addTab(String labelId, int drawableId, Class<?> c)
    {
        Intent intent = new Intent(this, c);

        TabHost.TabSpec spec = tabHost.newTabSpec("tab" + labelId);

        View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
        TextView title = (TextView) tabIndicator.findViewById(R.id.title);
        title.setText(labelId);
        ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
        icon.setImageResource(drawableId);
        spec.setIndicator(tabIndicator);
        spec.setContent(intent);
        tabHost.addTab(spec);
    }
    public void openCameraActivity(View b)
    {
//        Intent intent = new Intent(this, CameraActivity.class);
//        startActivity(intent);

        if(isShow == false){
            if (dialogLoading == null) {
                dialogLoading = new LoadingDialog(this);
                //dialogLoading.setCanceledOnTouchOutside(false);
                dialogLoading.setCanceledOnTouchOutside(true);
//                dialogLoading.btnCamer.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialogLoading.dismiss();
//                        isShow = false;
//                    }
//                });
//
//                dialogLoading.btnAlbum.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                       dialogLoading.dismiss();
//                        isShow = false;
//                    }
//                });
                dialogLoading.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        isShow = false;
                    }
                });

            }

            dialogLoading.show();
            isShow = true;
//            Button btn1 = (Button)findViewById(R.id.btn1);
//            btn1.setVisibility(View.VISIBLE);
//            Button btn2 = (Button)findViewById(R.id.btn2);
//            btn2.setVisibility(View.VISIBLE);

        }else{
            dialogLoading.dismiss();
//            Button btn1 = (Button)findViewById(R.id.btn1);
//            btn1.setVisibility(View.INVISIBLE);
//            Button btn2 = (Button)findViewById(R.id.btn2);
//            btn2.setVisibility(View.INVISIBLE);
            isShow = false;



        }

    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(alnumPress);
        unregisterReceiver(camerPress);
    }
}