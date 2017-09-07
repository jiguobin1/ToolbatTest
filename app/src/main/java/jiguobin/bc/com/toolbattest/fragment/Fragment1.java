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
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

import jiguobin.bc.com.toolbattest.Bean.HeadlinesInfo;
import jiguobin.bc.com.toolbattest.MainActivity;
import jiguobin.bc.com.toolbattest.R;
import jiguobin.bc.com.toolbattest.myadapter.HeadlinesRecylerViewAdapter;
import jiguobin.bc.com.toolbattest.onclick.SetOnClickListen;
import jiguobin.bc.com.toolbattest.webview.Decs;

/**
 * Created by acer-PC on 2017/4/13.
 */

public class Fragment1 extends Fragment {

    private RecyclerView toutiao_recy;
    private Gson mGson=new Gson();
    private MaterialRefreshLayout refresh;
    private int START = 0;
    private int SUM = 20;
    private int STATE;
    private final int NORMAL=0;//正常状态
    private final int REFRESH=1;//下拉刷新状态
    private final int LOADMORE=2;//下拉加载状态
    private List<HeadlinesInfo.T1348647909107Bean> t1348647909107;
    private  HeadlinesInfo headlinesInfo;
    private HeadlinesRecylerViewAdapter adapter;
    private String url;
    private  ProgressDialog progress;
    private String image_icon;
    private String title;
    private TextView text;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate( R.layout.fragment1, null);
        toutiao_recy= (RecyclerView) inflate.findViewById(R.id.touxiao_recy);
        refresh= (MaterialRefreshLayout) inflate.findViewById(R.id.refresh);
        progress=new ProgressDialog(getActivity());
        text=new TextView(getActivity());
        progress.setTitle("消息提示：");
        progress.setMessage("正在加载中请稍后......");
        progress.show();
        shuaxin();
        noHttp();
        return inflate;
    }
    public void noHttp(){
        RequestQueue requestQueue = NoHttp.newRequestQueue();
        Request<String> request = NoHttp.createStringRequest("http://c.m.163.com/nc/article/headline/T1348647909107/" + START + "-" + SUM + ".html", RequestMethod.GET);
        requestQueue.add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                String json = response.get();//得到请求数据
                Log.e("TTT",json);
                headlinesInfo= mGson.fromJson(json, HeadlinesInfo.class);
                t1348647909107= headlinesInfo.getT1348647909107();
                if (t1348647909107.size()==0){
                    //请求到数据结束进度条显示
                    Toast.makeText(getActivity(),"已经没有数据了哦....",Toast.LENGTH_SHORT).show();
                    refresh.finishRefresh();
                    refresh.finishRefreshLoadMore();
                    return;
                }
                adapter=new HeadlinesRecylerViewAdapter(getActivity(),t1348647909107);
                toutiao_recy.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
                toutiao_recy.setAdapter(adapter);
                SetOnClickListen setOnClickListen = new SetOnClickListen() {
                    @Override
                    public void setOnclick(int position) {
                        if(t1348647909107.get(position).getUrl_3w()==null){
                            Toast.makeText(getActivity(),"没有详细内容",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Log.e("AAA",""+position);
                        url=t1348647909107.get(position).getUrl();
                        image_icon=t1348647909107.get(position).getImgsrc();
                        title=t1348647909107.get(position).getLtitle();
                        Log.e("jjj","URL:::"+url);
                       Intent intent=new Intent(getActivity(),Decs.class);
                        intent.putExtra("url",url);
                        intent.putExtra("image_icon",image_icon);
                        intent.putExtra("title",title);
                        startActivity(intent);
                    }
                };
                adapter.SetClickListen(setOnClickListen);

                refresh.finishRefresh();
                refresh.finishRefreshLoadMore();

            }

            @Override
            public void onFailed(int what, Response<String> response) {
                text.setText("网络异常，请检查网络连接情况");
            }

            @Override
            public void onFinish(int what) {
                progress.dismiss();
            }
        });
    }


    //框架的下拉和上拉
    public void shuaxin(){
        refresh.setLoadMore(true);//设置支持上拉加载更多
        refresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                START = START + 20;
                SUM = SUM + 20;
                noHttp();
                Log.d("sss", "initData::" + START + "SUM::" + SUM);

            }
            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                //load more refreshing...
                STATE=LOADMORE;
                SUM=SUM+20;
                noHttp();
            }
        });

    }
}

