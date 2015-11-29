package daily.selfie.thiago.com.dailyselfieproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

/**
 *
 */
public class ShowSelfieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_selfie);

        ImageView imageView = (ImageView) findViewById(R.id.picture);

        String path = getIntent().getStringExtra("path_selfie_file");
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        imageView.setImageBitmap(bitmap);
    }
}
