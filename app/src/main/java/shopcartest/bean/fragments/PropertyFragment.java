package shopcartest.bean.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import baiduanimation.BaiDuLoadingActivtiy;
import baiduanimation.R;
import hungry.HungryMainActivity;
import random.RandomActivity;
import redpacket.AirActivity;
import shopcartest.bean.property.AliPayAnimActivity;
import shopcartest.bean.property.LayoutAnimationsActivity;
import shopcartest.bean.property.PayPasswordActivity;
import shopcartest.bean.property.PropertyAnimationActivity;
import shopcartest.bean.property.RevealAnimatorActivity;
import shopcartest.bean.property.ShopCarAddAnimActivity;
import shopcartest.bean.property.ValueAnimationActivity;
import testheart.HeartActivity;
import zoomHeader.HungryActivity;

/**
 * desc:
 * author:张肖换
 * date:${Date}
 */
public class PropertyFragment extends Fragment implements View.OnClickListener {
    //     private Button  property,value,demo,shopcar_add_anim;
    private Context mContext;
    private View viewRoot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewRoot = inflater.inflate(R.layout.frament_property, null);
        initView();
        return viewRoot;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    private void initView() {
        viewRoot.findViewById(R.id.property).setOnClickListener(this);
        viewRoot.findViewById(R.id.value).setOnClickListener(this);
        viewRoot.findViewById(R.id.demo).setOnClickListener(this);
        viewRoot.findViewById(R.id.shopcar_add_anim).setOnClickListener(this);
        viewRoot.findViewById(R.id.LayoutAnimations).setOnClickListener(this);
        viewRoot.findViewById(R.id.RevealAnimator).setOnClickListener(this);
        viewRoot.findViewById(R.id.CustomView).setOnClickListener(this);
        viewRoot.findViewById(R.id.payPassword).setOnClickListener(this);
        viewRoot.findViewById(R.id.testherat).setOnClickListener(this);
        viewRoot.findViewById(R.id.randomText).setOnClickListener(this);
        viewRoot.findViewById(R.id.hungryActivity).setOnClickListener(this);
        viewRoot.findViewById(R.id.airActivity).setOnClickListener(this);
        viewRoot.findViewById(R.id.baiduaniset).setOnClickListener(this);
        viewRoot.findViewById(R.id.hungry_elm).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.property:
                intent = new Intent(mContext, PropertyAnimationActivity.class);
                break;
            case R.id.value:
                intent = new Intent(mContext, ValueAnimationActivity.class);
                break;
            case R.id.demo:
                intent = new Intent(mContext, AliPayAnimActivity.class);
                break;
            case R.id.shopcar_add_anim:
                intent = new Intent(mContext, ShopCarAddAnimActivity.class);
                break;
            case R.id.LayoutAnimations:
                intent = new Intent(mContext, LayoutAnimationsActivity.class);
                break;
            case R.id.RevealAnimator:
                intent = new Intent(mContext, RevealAnimatorActivity.class);
                break;
            case R.id.payPassword:
                intent = new Intent(mContext, PayPasswordActivity.class);
                break;
            case R.id.testherat:
                intent = new Intent(mContext, HeartActivity.class);
                break;
            case R.id.randomText:
                intent = new Intent(mContext, RandomActivity.class);
                break;
            case R.id.hungryActivity:
                intent = new Intent(mContext, HungryActivity.class);
                break;
            case R.id.airActivity:
                intent = new Intent(mContext, AirActivity.class);
                break;
            case R.id.baiduaniset:
                intent =new Intent(mContext, BaiDuLoadingActivtiy.class);
                break;
            case  R.id.hungry_elm:
                intent =new Intent(mContext, HungryMainActivity.class);
                break;
            default:
                break;
        }
        startActivity(intent);
    }

}
