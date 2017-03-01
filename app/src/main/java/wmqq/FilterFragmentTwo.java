package wmqq;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import baiduanimation.R;


/**
 * desc:
 * author:张肖换
 * date:${Date}
 */
public class FilterFragmentTwo extends Fragment {
    private ListView lv_department;
    private ImageView iv_back;
    private String departmentName = "";
    String[] list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_department_select, null);
        initView(view);
        initEvent();
        return view;
    }
    private void initView(View  view) {
        lv_department= (ListView) view.findViewById(R.id.lv_department);
        iv_back= (ImageView) view.findViewById(R.id.iv_back);
         list =new  String[10];
         for (int  i =0 ;i<10;i++){
         list[i]="部门"+i;
         }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,list);
        lv_department.setAdapter(arrayAdapter);

    }
    private void initEvent() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });
        lv_department.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 departmentName=list[position];
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });

    }

}
