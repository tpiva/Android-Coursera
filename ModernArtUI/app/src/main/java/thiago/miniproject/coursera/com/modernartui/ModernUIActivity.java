package thiago.miniproject.coursera.com.modernartui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ModernUIActivity extends AppCompatActivity {

    private TextView mTextViewBlue = null;
    private TextView mTextViewRed = null;
    private TextView mTextViewPink = null;
    private TextView mTextViewGreen = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modern_ui);

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mTextViewBlue.setBackgroundColor(interpolateColor(Color.parseColor("#191970"), Color.parseColor("#00ffff"), progress / 100f));
                mTextViewRed.setBackgroundColor(interpolateColor(Color.parseColor("#cd5c5c"), Color.parseColor("#a52a2a"), progress / 100f));
                mTextViewPink.setBackgroundColor(interpolateColor(Color.parseColor("#ff69b4"), Color.parseColor("#d8bfd8"), progress / 100f));
                mTextViewGreen.setBackgroundColor(interpolateColor(Color.parseColor("#66cdaa"), Color.parseColor("#f0e68c"), progress / 100f));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        setDefaultColorRects(tableLayout);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_modern_ui, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.information) {
            AlertDialogFragment dialog = AlertDialogFragment.newInstance();
            dialog.show(getFragmentManager(),"");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Primary colors came from http://www.tayloredmktg.com/rgb/
     * @param tableLayout
     */
    private void setDefaultColorRects(TableLayout tableLayout) {
        for(int row = 0; row < tableLayout.getChildCount(); row++) {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(row);
            for(int elementRow = 0; elementRow < tableRow.getChildCount(); elementRow++) {
                // get each textView inside current row (tableRow)
                TextView currentTextView = (TextView) tableRow.getChildAt(elementRow);
                switch (currentTextView.getId()) {
                    case R.id.textViewBlue:
                        currentTextView.setBackgroundColor(Color.parseColor("#191970"));
                        mTextViewBlue = currentTextView;
                        break;
                    case R.id.textViewGray:
                        currentTextView.setBackgroundColor(Color.parseColor("#BDBDBD"));
                        break;
                    case R.id.textViewPink:
                        currentTextView.setBackgroundColor(Color.parseColor("#ff69b4"));
                        mTextViewPink = currentTextView;
                        break;
                    case R.id.textViewGreen:
                        currentTextView.setBackgroundColor(Color.parseColor("#66cdaa"));
                        mTextViewGreen = currentTextView;
                        break;
                    case R.id.textViewRed:
                        currentTextView.setBackgroundColor(Color.parseColor("#bc8f8f"));
                        mTextViewRed = currentTextView;
                        break;
                }
            }
        }

    }

    /**
     * Those code below are from Mark Renouf on comments in Stackoverflow at link:
     * http://stackoverflow.com/questions/4414673/android-color-between-two-colors-based-on-percentage
     * that are decompose color on three basic colors red, blue and green. Moreover those sites below
     * are used to undestand it: Eingestellt von Han.Solo - http://harmoniccode.blogspot.com.br/2011/04/bilinear-color-interpolation.html
     *
     * @param a
     * @param b
     * @param proportion
     * @return
     */
    private int interpolateColor(final int a, final int b,
                                 final float proportion) {
        final float[] hsva = new float[3];
        final float[] hsvb = new float[3];
        Color.colorToHSV(a, hsva);
        Color.colorToHSV(b, hsvb);
        for (int i = 0; i < 3; i++) {
            hsvb[i] = interpolate(hsva[i], hsvb[i], proportion);
        }
        return Color.HSVToColor(hsvb);
    }
    private float interpolate(final float a, final float b,
                              final float proportion) {
        return (a + ((b - a) * proportion));
    }

}



