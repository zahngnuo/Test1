package wmqq;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import baiduanimation.R;


/**
 * desc:
 * author:张肖换
 * date:${Date}
 */
public class DrawerLayoutAct extends AppCompatActivity{
     private TextView  tv_filter;
     private DrawerLayout mDrawerLayout;
     private FrameLayout mDrawerContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_drawerlayout_act);
        initView();
        initEvent();
    }

    private void initView() {
        tv_filter= (TextView) findViewById(R.id.tv_filter);
        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawerlayout_layout);
        mDrawerContent= (FrameLayout) findViewById(R.id.drawer_content);
        Fragment fragment =new FilterFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putString("departmentName","");
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.drawer_content, fragment).commit();
    }
    private  void initEvent(){
        tv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(mDrawerContent);
            }
        });
    }
}
