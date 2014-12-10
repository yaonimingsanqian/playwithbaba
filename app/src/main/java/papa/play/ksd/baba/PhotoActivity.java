package papa.play.ksd.baba;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 照片墙主活动，使用GridView展示照片墙。
 * 
 * @author guolin
 */
public class PhotoActivity extends Activity {

	/**
	 * 用于展示照片墙的GridView
	 */
	private GridView mPhotoWall;

	/**
	 * GridView的适配器
	 */
    List<String> mFileList;
	private PhotoWallAdapter mAdapter;

	private int mImageThumbSize;
	private int mImageThumbSpacing;
    int selectCount = 0;
    private Map<Integer, Boolean> mSelectMap = new HashMap<Integer, Boolean>();

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_album);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.photo_activity);
		mImageThumbSize = getResources().getDimensionPixelSize(
				R.dimen.image_thumbnail_size);
		mImageThumbSpacing = getResources().getDimensionPixelSize(
				R.dimen.image_thumbnail_spacing);
		mPhotoWall = (GridView) findViewById(R.id.photo_wall);
        mFileList=new ArrayList<String>();

        try {
            File f = new File(System.getenv("SECONDARY_STORAGE")+"/DCIM/Camera/");
            if(f != null){
                getFile(f);
            }
        }catch (Exception e){

        }

        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.phototitle);
        Button reP = (Button)relativeLayout.findViewById(R.id.rep);
        reP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button confirm = (Button)relativeLayout.findViewById(R.id.confirm);
        reP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectCount != 0 && selectCount != 9){

                }
            }
        });
//
        File  f1 = new File("/sdcard/DCIM/Camera/");
        if(f1 != null){
            getFile(f1);
        }
//
        String[] strArr = new String[mFileList.size()];
        mFileList.toArray(strArr);
        mAdapter = new PhotoWallAdapter(PhotoActivity.this, 0, strArr,
                mPhotoWall);
        mPhotoWall.setAdapter(mAdapter);
        mPhotoWall.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        final int numColumns = (int) Math.floor(mPhotoWall
                                .getWidth()
                                / (mImageThumbSize + mImageThumbSpacing));
                        if (numColumns > 0) {
                            int columnWidth = (mPhotoWall.getWidth() / numColumns)
                                    - mImageThumbSpacing;
                            mAdapter.setItemHeight(columnWidth);
                            mPhotoWall.getViewTreeObserver()
                                    .removeGlobalOnLayoutListener(this);
                        }
                    }
                });
        mPhotoWall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                ImageView imageView = (ImageView)view.findViewById(R.id.select);
                if(mSelectMap.get(i) != null && mSelectMap.get(i)){
                    Log.v("mPhotoWall","隐藏");
                    mSelectMap.put(i,false);
                    imageView.setVisibility(View.INVISIBLE);
                    selectCount--;
                }else{
                    if(selectCount == 9){
                        Toast.makeText(getApplicationContext(),
                                "最多选9张图片", Toast.LENGTH_LONG).show();
                        return;
                    }
                    mSelectMap.put(i,true);
                    imageView.setVisibility(View.VISIBLE);
                    Log.v("mPhotoWall","显示");
                    selectCount++;

                }



            }
        });



	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mAdapter.fluchCache();
	}
    @Override
	protected void onDestroy() {
		super.onDestroy();
		// 退出程序时结束所有的下载任务
		mAdapter.cancelAllTasks();
	}

    public List<String> getFile(File file){
        File[] fileArray =file.listFiles();
        for (File f : fileArray) {
            if(f.isFile()){
                mFileList.add(f.getPath());
            }else{
                getFile(f);
            }
        }
        return mFileList;
    }


}