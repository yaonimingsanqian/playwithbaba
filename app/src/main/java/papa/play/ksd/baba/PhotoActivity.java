package papa.play.ksd.baba;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewTreeObserver;
import android.widget.GridView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_album);
		mImageThumbSize = getResources().getDimensionPixelSize(
				R.dimen.image_thumbnail_size);
		mImageThumbSpacing = getResources().getDimensionPixelSize(
				R.dimen.image_thumbnail_spacing);
		mPhotoWall = (GridView) findViewById(R.id.photo_wall);
        mFileList=new ArrayList<String>();
        handler = new Handler();

        try {
            File f = new File(System.getenv("SECONDARY_STORAGE")+"/DCIM/Camera/");
            if(f != null){
                getFile(f);
            }
        }catch (Exception e){

        }


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