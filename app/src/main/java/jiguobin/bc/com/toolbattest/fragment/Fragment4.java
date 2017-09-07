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

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import jiguobin.bc.com.toolbattest.Bean.Happyinfo;
import jiguobin.bc.com.toolbattest.R;
import jiguobin.bc.com.toolbattest.myadapter.HayypRecylerViewAdapter;
import jiguobin.bc.com.toolbattest.myadapter.HeadlinesRecylerViewAdapter;
import jiguobin.bc.com.toolbattest.myadapter.MyRecyclerViewAdapter;
import jiguobin.bc.com.toolbattest.onclick.SetOnClickListen;
import jiguobin.bc.com.toolbattest.webview.Decs;

/**
 * Created by acer-PC on 2017/4/13.
 */

public class Fragment4 extends Fragment {
    private RecyclerView recyclerView;
    private Gson mGson=new Gson();
    private MaterialRefreshLayout refresh;
    private int START = 0;
    private int SUM = 20;
    private int STATE;
    private final int NORMAL=0;//正常状态
    private final int REFRESH=1;//下拉刷新状态
    private final int LOADMORE=2;//下拉加载状态
    private List<Happyinfo.T1350383429665Bean> t1350383429665;
    private HayypRecylerViewAdapter adapter;
    private String url;
    private String image_icon;
    private String title;
    private ProgressDialog progress;
    private TextView text;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate( R.layout.fragment4, null);
        recyclerView = (RecyclerView) inflate.findViewById(R.id.happy_recy);
        refresh= (MaterialRefreshLayout) inflate.findViewById(R.id.refresh4);
        text=new TextView(getActivity());
        progress=new ProgressDialog(getActivity());
        progress.setTitle("消息提示：");
        progress.setMessage("正在加载中请稍后......");
        progress.show();
        shuaxin();
        utilsHttp();
        return inflate;
    }
    public void utilsHttp(){
        RequestParams requestParams=new RequestParams("http://c.m.163.com/nc/article/list/T1350383429665/"+START+"-"+SUM+".html");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("sss","onSuccess::"+result);
                Happyinfo happyinfo = mGson.fromJson(result, Happyinfo.class);
                t1350383429665 = happyinfo.getT1350383429665();
                if (t1350383429665.size()==0){
                    //请求到数据结束进度条显示
                    Toast.makeText(getActivity(),"已经没有数据了哦....",Toast.LENGTH_SHORT).show();
                    refresh.finishRefresh();
                    refresh.finishRefreshLoadMore();
                    return;
                }
                adapter=new HayypRecylerViewAdapter(getActivity(),t1350383429665);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(adapter);
                SetOnClickListen setOnClickListen=new SetOnClickListen() {
                    @Override
                    public void setOnclick(int position) {
                        if(t1350383429665.get(position).getUrl_3w()==null){
                            Toast.makeText(getActivity(),"没有详细内容",Toast.LENGTH_SHORT).show();
                        }
                        url=t1350383429665.get(position).getUrl();
                        image_icon=t1350383429665.get(position).getImgsrc();
                        title=t1350383429665.get(position).getLtitle();
                        Intent intent=new Intent(getActivity(), Decs.class);
                        intent.putExtra("url",url);
                        intent.putExtra("image_icon",image_icon);
                        intent.putExtra("title",title);
                        startActivity(intent);
                    }
                };
                adapter.onClick(setOnClickListen);
                refresh.finishRefresh();
                refresh.finishRefreshLoadMore();

            }


            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                text.setText("网络异常，请检查网络连接情况");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.d("sss","onCancelled::"+cex.getMessage());
            }

            @Override
            public void onFinished() {
                Log.d("sss","onFinished::");
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
                utilsHttp();
                Log.d("sss", "initData::" + START + "SUM::" + SUM);

            }
            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                //load more refreshing...
                STATE=LOADMORE;
                SUM=SUM+20;
                utilsHttp();
            }
        });

    }
}
