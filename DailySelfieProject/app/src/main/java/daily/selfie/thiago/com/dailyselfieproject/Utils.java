package daily.selfie.thiago.com.dailyselfieproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 *
 */
public class Utils {

    private static final String DIRECTORY_DAILY_SELFIE = "DailySelfie";
    public static final int WIDTH = 100;
    public static final int HEIGH = 100;

    public static File makeGetStoreDir(Context context) {
        File storePath = null;
        try {
            String basePath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getCanonicalPath();
            if(basePath != null) {
                storePath = new File(basePath + DIRECTORY_DAILY_SELFIE);
                storePath.mkdirs();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return storePath;
    }

    public static Bitmap getBitmap(String pathImage) {
        int targetW = WIDTH;
        int targetH = HEIGH;

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathImage, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(pathImage, bmOptions);
        return bitmap;
    }

}
