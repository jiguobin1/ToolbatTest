package jiguobin.bc.com.toolbattest.fragment;

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
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.List;

import jiguobin.bc.com.toolbattest.Bean.ImageInfo;
import jiguobin.bc.com.toolbattest.R;
import jiguobin.bc.com.toolbattest.myadapter.ImageRecylerViewAdapter;
import jiguobin.bc.com.toolbattest.onclick.SetOnClickListen;
import jiguobin.bc.com.toolbattest.webview.Decs;
import okhttp3.Call;

import static jiguobin.bc.com.toolbattest.R.id.refresh;

/**
 * Created by acer-PC on 2017/4/24.
 */

public class ImageFragment extends Fragment {
    private RecyclerView recy;
    private Gson mGson=new Gson();
    private ImageRecylerViewAdapter adapter;
    private String url;
    private List<ImageInfo> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate( R.layout.imagefragment, null);
        recy = (RecyclerView) inflate.findViewById(R.id.image_recy);
        recy.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        http();
        return inflate;
    }
    public void http(){
        OkHttpUtils.get()
                .url("http://api.laifudao.com/open/tupian.json")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG","response:::"+response);
                        Type type = new TypeToken<List<ImageInfo>>() {}.getType();
                        list = mGson.fromJson(response, type);
                        adapter=new ImageRecylerViewAdapter(getActivity(),list);
                        recy.setAdapter(adapter);
                        SetOnClickListen setOnClickListen=new SetOnClickListen() {
                            @Override
                            public void setOnclick(int position) {
                                if(list.get(position).getUrl()==null){
                                    Toast.makeText(getActivity(),"没有详细内容",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                url=list.get(position).getUrl();
                                Intent intent=new Intent(getActivity(), Decs.class);
                                intent.putExtra("url",url);
                                startActivity(intent);

                            }
                        };
                        adapter.onClick(setOnClickListen);
                    }
                });
    }
}
