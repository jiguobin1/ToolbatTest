package jiguobin.bc.com.toolbattest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import jiguobin.bc.com.toolbattest.Bean.WeatherInfo;
import jiguobin.bc.com.toolbattest.R;
import jiguobin.bc.com.toolbattest.myadapter.WeatherRecylerViewAdapter;
import okhttp3.Call;

/**
 * Created by acer-PC on 2017/5/1.
 */

public class WeatherFragemnt extends Fragment {
    private TextView tianqi;
    private Gson mGson=new Gson();
    private RecyclerView recy;
    private WeatherRecylerViewAdapter adapter;
    private EditText province,city;
    private Button next;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.weather_fragment, null);
        recy= (RecyclerView) inflate.findViewById(R.id.weather_recy);
        province= (EditText) inflate.findViewById(R.id.province);
        city= (EditText) inflate.findViewById(R.id.city);
        next= (Button) inflate.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edit1 = province.getText().toString().trim();
                String edit2 = city.getText().toString().trim();
                OkHttpUtils.post()
                        .url("http://apicloud.mob.com/v1/weather/query")
                        .addParams("city",edit2)
                        .addParams("province",edit1)
                        .addParams("key", "1d120467e646b")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("TAG",response);
                                WeatherInfo weatherInfo = mGson.fromJson(response, WeatherInfo.class);
                                List<WeatherInfo.ResultBean> result = weatherInfo.getResult();
                                List<WeatherInfo.ResultBean.FutureBean> list=  result.get(0).getFuture();
                                adapter=new WeatherRecylerViewAdapter(getActivity(),list);
                                recy.setAdapter(adapter);
                            }
                        });
            }
        });
        //横向滑动的gridView
        recy.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        recy.setItemAnimator(new DefaultItemAnimator());

        return inflate;
    }


}
