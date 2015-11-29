package daily.selfie.thiago.com.dailyselfieproject;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class DailySelfieLoader extends AsyncTask<String,Void,List<DailySelfie>> {

    private static DailySelfieListAdapter mDailySelfieListAdapter;


    public DailySelfieLoader(DailySelfieListAdapter dailySelfieListAdapter) {
        this.mDailySelfieListAdapter = dailySelfieListAdapter;
    }


    @Override
    protected List<DailySelfie> doInBackground(String... params) {
        String pathStorageDir = params[0];
        List<DailySelfie> dailySelfies = new ArrayList<DailySelfie>();

        if(Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            File storeDir = new File(pathStorageDir);
            if(storeDir.exists()) {
                File[] images = storeDir.listFiles();
                for(File image : images) {
                    DailySelfie newDailySelfie = new DailySelfie(
                            Utils.getBitmap(image.getAbsolutePath()),
                            image.getName().replace(".jpg",""),
                            image.getAbsolutePath()
                    );
                    dailySelfies.add(newDailySelfie);
                }
            }
        }

        return dailySelfies;
    }

    @Override
    protected void onPostExecute(List<DailySelfie> result) {
        mDailySelfieListAdapter.setDailySelfies(result);
    }


}
