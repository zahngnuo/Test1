package random;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import baiduanimation.R;


/**
 * desc:
 * author:张肖换
 * date:${Date}
 */
public class RandomActivity extends Activity {
    private RandomTextView textView;
    private int[] pianyiliang = new int[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_random_activity);
        textView = (RandomTextView) findViewById(R.id.rtv);
        pianyiliang[0] = 10;
        pianyiliang[1] = 9;
        pianyiliang[2] = 8;
        pianyiliang[3] = 7;
        pianyiliang[4] = 6;
        pianyiliang[5] = 5;
        textView.setPianyilian(pianyiliang);
        textView.start();
    }

    public void start(View v) {
        textView.setText("876543");
        textView.setPianyilian(RandomTextView.ALL);
        textView.start();
    }
    public void start2(View v){
        textView.setText("909878");
        pianyiliang[0]=7;
        pianyiliang[1]=6;
        pianyiliang[2]=12;
        pianyiliang[3]=8;
        pianyiliang[4]=18;
        pianyiliang[5]=10;
        textView.setMaxLine(20);
        textView.setPianyilian(pianyiliang);
        textView.start();
    }
    public void start3(View view){
        textView.setText("9078111123");
        textView.setPianyilian(RandomTextView.FIRSTF_LAST);
        textView.start();

    }
    public void start4(View v) {
        textView.setText("12313288");
        textView.setPianyilian(RandomTextView.FIRSTF_FIRST);
        textView.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        textView.destroy();
    }
}
