package daily.selfie.thiago.com.dailyselfieproject;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * It was use as reference https://github.com/aporter/coursera-android and
 * reference http://developer.android.com/training/camera/photobasics.html
 */
public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final long ALARM_IN_TWO_MINUTES = 120 * 1000L;

    private DailySelfieListAdapter mDailySelfieListAdapter;
    private Bitmap mPictureTakenAsBitmap;
    private File mCurrentPhotoPath = null;
    private DailySelfie dailySelfieToRemove;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = (ListView) findViewById(R.id.list_view_selfie);

        mDailySelfieListAdapter = new DailySelfieListAdapter(getApplicationContext());
        listView.setAdapter(mDailySelfieListAdapter);

        //load images
        DailySelfieLoader loader = new DailySelfieLoader(mDailySelfieListAdapter);
        try {
            loader.execute(Utils.makeGetStoreDir(getApplicationContext()).getCanonicalPath());
        } catch (IOException e) {
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DailySelfie dailySelfie = (DailySelfie) listView.getItemAtPosition(position);
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,ShowSelfieActivity.class);
                intent.putExtra("path_selfie_file", dailySelfie.getPathOfImage());
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.this.dailySelfieToRemove = (DailySelfie) listView.getItemAtPosition(position);
                AlertDialogFragment alertDialogFragment = AlertDialogFragment.newInstance();
                alertDialogFragment.show(getFragmentManager(), "Alert");
                return true;
            }
        });

        setAlarmNotification();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_camera) {
            startTakePicture();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     *
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            mPictureTakenAsBitmap = Utils.getBitmap(mCurrentPhotoPath.getAbsolutePath()); ;
            mDailySelfieListAdapter.add(new DailySelfie(mPictureTakenAsBitmap, mCurrentPhotoPath.getName().replace(".jpg", ""), mCurrentPhotoPath.getAbsolutePath()));
        }
    }

    /**
     *
     */
    private void startTakePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            mCurrentPhotoPath = createImageFile();

            if(mCurrentPhotoPath != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(mCurrentPhotoPath));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            String imageName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File imageFile = null;
            try {
                imageFile = new File(Utils.makeGetStoreDir(getApplicationContext()).getCanonicalPath() + File.separator + imageName + ".jpg");
                imageFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return imageFile;

        } else {
            return null;
        }
    }

    /**
     *
     */
    private void setAlarmNotification() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent notificationAlarmIntent = new Intent(
                MainActivity.this, DailySelfieNotificationReceiver.class);
        PendingIntent pendingIntentAlarm =  PendingIntent.getBroadcast(
                MainActivity.this, 0, notificationAlarmIntent, 0);

        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + ALARM_IN_TWO_MINUTES,
                ALARM_IN_TWO_MINUTES,
                pendingIntentAlarm);
    }

    public void removeDailySelfie() {

        File imageToRemove = new File(dailySelfieToRemove.getPathOfImage());
        if(imageToRemove.exists()) {
            imageToRemove.delete();
        }

        mDailySelfieListAdapter.remove(dailySelfieToRemove);

    }


    /**
     *
     */
    public static class AlertDialogFragment extends DialogFragment {

        public static AlertDialogFragment newInstance() {
            return new AlertDialogFragment();
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                    .setMessage(this.getResources().getString(R.string.delete_content))
                    .setCancelable(false)
                    .setNegativeButton(this.getResources().getString(R.string.no_action),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    dismiss();
                                }
                            })
                    .setPositiveButton(this.getResources().getString(R.string.yes_action),
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        final DialogInterface dialog, int id) {
                                    ((MainActivity) getActivity())
                                            .removeDailySelfie();
                                    dismiss();
                                }
                            }).create();
        }
    }
}
