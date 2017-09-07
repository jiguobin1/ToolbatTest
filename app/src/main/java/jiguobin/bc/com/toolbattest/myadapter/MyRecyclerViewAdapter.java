package jiguobin.bc.com.toolbattest.myadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.util.List;

import jiguobin.bc.com.toolbattest.Bean.NABinfo;
import jiguobin.bc.com.toolbattest.R;
import jiguobin.bc.com.toolbattest.onclick.SetOnClickListen;
import okhttp3.Call;

/**
 * Created by acer-PC on 2017/4/19.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewViewHolder>{
    //声明点击事件接口
    private SetOnClickListen setOnClickListen;
    private Context mContext;
    private List<NABinfo.T1348649145984Bean> data;
    public MyRecyclerViewAdapter(Context mContext,List<NABinfo.T1348649145984Bean> data) {
        this.mContext=mContext;
        this.data=data;
    }
    public void SetClickListen(SetOnClickListen setOnClickListen){
        this.setOnClickListen=setOnClickListen;
    }

    @Override
    public MyRecyclerViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.nba_item,parent,false);
        MyRecyclerViewViewHolder viewHolder = new MyRecyclerViewViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyRecyclerViewViewHolder holder, final int position) {
        holder.name.setText(data.get(position).getTitle());
        holder.decs.setText(data.get(position).getDigest());
        OkHttpUtils.get()
                .url(data.get(position).getImgsrc())
                .build()
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(Bitmap response, int id) {
                        holder.icon.setImageBitmap(response);
                    }
                });
        //条目点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(setOnClickListen!=null){
                    setOnClickListen.setOnclick(position);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
class MyRecyclerViewViewHolder extends RecyclerView.ViewHolder {
    public ImageView icon;
    public TextView name;
    public TextView decs;
    public MyRecyclerViewViewHolder(View itemView) {
        super(itemView);
        icon= (ImageView) itemView.findViewById(R.id.main_image);
        name= (TextView) itemView.findViewById(R.id.name);
        decs= (TextView) itemView.findViewById(R.id.decs);
    }
}
