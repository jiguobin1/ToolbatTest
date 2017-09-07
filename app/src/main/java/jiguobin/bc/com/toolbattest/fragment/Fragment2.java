package jiguobin.bc.com.toolbattest.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import jiguobin.bc.com.toolbattest.Bean.NABinfo;

import jiguobin.bc.com.toolbattest.R;
import jiguobin.bc.com.toolbattest.myadapter.MyRecyclerViewAdapter;
import jiguobin.bc.com.toolbattest.onclick.SetOnClickListen;
import jiguobin.bc.com.toolbattest.webview.Decs;
import okhttp3.Call;

/**
 * Created by acer-PC on 2017/4/13.
 */

public class Fragment2 extends Fragment {
    private RecyclerView recyclerView;
    private Gson mGson = new Gson();
    private List<NABinfo.T1348649145984Bean> t1348649145984;
    private String url;
    private MyRecyclerViewAdapter adapter;
    private ProgressDialog progress;
    private String image_icon;
    private String title;
    private TextView text;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate( R.layout.fragment2, null);
        recyclerView = (RecyclerView) inflate.findViewById(R.id.NBA_recy);
        progress=new ProgressDialog(getActivity());
        text=new TextView(getActivity());
        progress.setTitle("消息提示：");
        progress.setMessage("正在加载中请稍后......");
        progress.show();
        http();
        return inflate;
    }

    //获取NBAjson数据
    public void http(){
        OkHttpUtils.get()
                .url("http://c.m.163.com/nc/article/list/T1348649145984/0-20.html")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        text.setText("网络异常，请检查网络连接情况");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        progress.dismiss();
                        Log.e("ABC",response);
                        NABinfo naBinfo = mGson.fromJson(response, NABinfo.class);
                        t1348649145984 = naBinfo.getT1348649145984();
                        Log.e("TAGrr",t1348649145984.toString());
                        adapter=new MyRecyclerViewAdapter(getActivity(),t1348649145984);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        recyclerView.setAdapter(adapter);
                        SetOnClickListen setOnClickListen=new SetOnClickListen() {
                            @Override
                            public void setOnclick(int position) {
                                if(t1348649145984.get(position).getUrl()==null){
                                    Toast.makeText(getActivity(),"没有详细内容",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                url=t1348649145984.get(position).getUrl();
                                image_icon=t1348649145984.get(position).getImgsrc();
                                title=t1348649145984.get(position).getLtitle();
                                Intent intent=new Intent(getActivity(),Decs.class);
                                intent.putExtra("url",url);
                                intent.putExtra("image_icon",image_icon);
                                intent.putExtra("title",title);
                                startActivity(intent);
                            }
                        };
                        adapter.SetClickListen(setOnClickListen);
                    }
                });
    }
}
