package papa.play.ksd.baba;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.File;

public class CameraActivity extends Activity {

    private ImageView imageView;
    private Button button;

    private static  String FILE_PATH ="/sdcard/ilock/syscamera.jpg";
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_camera);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.public_title);
        Intent in = getIntent();
        if (in != null) {

            imageView = (ImageView)findViewById(R.id.imageView);
            DisplayMetrics metric = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metric);
            int width = metric.widthPixels;  // 屏幕宽度（像素）
            int height = metric.heightPixels;  // 屏幕高度（像素）

            byte[] bis = in.getByteArrayExtra("bitmap");
            Bitmap bitmap = BitmapFactory.decodeByteArray(bis, 0, bis.length);
            imageView.setImageBitmap(bitmap);


        }
        button = (Button)findViewById(R.id.spinner);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.registertitle);
        Button reP = (Button)relativeLayout.findViewById(R.id.rep);
        reP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });


    }

    void showDialog(){
        new AlertDialog.Builder(CameraActivity.this) // build AlertDialog
                .setTitle("选择类别") // title
                .setItems(R.array.items, new DialogInterface.OnClickListener() { //content
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String[] aryShop = getResources().getStringArray(R.array.items); //array
                        button.setText(aryShop[which]);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); //关闭alertDialog
                    }
                })
                .show();
    }

    @Override
    protected void onActivityResult ( int requestCode , int resultCode , Intent data ) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = PictureUtil.getSmallBitmap(FILE_PATH);
        if(bitmap == null){
            if(data == null){
                return;
            }else{
                if(data.getExtras() != null){
                    bitmap = (Bitmap)data.getExtras().get("data");
                }else{
                    return;
                }

            }

        }
        imageView.setImageBitmap(bitmap);
    }


}
