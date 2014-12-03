package papa.play.ksd.baba;

/**
 * Created by test2 on 14/12/3.
 */
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 文件操作类
 * @Description: 文件操作类

 * @FileName: FileUtil.java

 * @Package com.device.photo

 * @Author Hanyonglu

 * @Date 2012-5-10 下午01:37:49

 * @Version V1.0
 */
public class FileUtil {
    public FileUtil() {
        // TODO Auto-generated constructor stub
    }

    /**
     * InputStream to byte
     * @param inStream
     * @return
     * @throws Exception
     */
    public byte[] readInputStream(InputStream inStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }

        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();

        return data;
    }

    /**
     * Byte to bitmap
     * @param bytes
     * @param opts
     * @return
     */
    public Bitmap getBitmapFromBytes(byte[] bytes, BitmapFactory.Options opts) {
        if (bytes != null){
            if (opts != null){
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,opts);
            }
            else{
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }
        }

        return null;
    }
}