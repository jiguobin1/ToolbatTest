package jiguobin.bc.com.toolbattest.myadapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import jiguobin.bc.com.toolbattest.Bean.HeadlinesInfo;
import jiguobin.bc.com.toolbattest.R;
import jiguobin.bc.com.toolbattest.onclick.SetOnClickListen;

/**
 * Created by acer-PC on 2017/4/22.
 */

public class HeadlinesRecylerViewAdapter extends RecyclerView.Adapter<HeadlinesRecylerViewHolder> {
    //声明点击事件接口
    private SetOnClickListen setOnClickListen;
    private Context mContext;
    private List<HeadlinesInfo.T1348647909107Bean> list;
    public HeadlinesRecylerViewAdapter(Context mContext,List<HeadlinesInfo.T1348647909107Bean> list) {
        this.mContext=mContext;
        this.list=list;
    }
    public void SetClickListen(SetOnClickListen setOnClickListen){
        this.setOnClickListen=setOnClickListen;
    }

    @Override
    public HeadlinesRecylerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.headlines_item, parent, false);
        HeadlinesRecylerViewHolder headlinesRecylerViewHolder = new HeadlinesRecylerViewHolder(inflate);
        return headlinesRecylerViewHolder;
    }

    @Override
    public void onBindViewHolder(HeadlinesRecylerViewHolder holder, final int position) {
        holder.headlines_name.setText(list.get(position).getTitle());
        holder.headlines_desc.setText(list.get(position).getDigest());
        Glide.with(mContext).load(list.get(position).getImgsrc()).into(holder.headlines_icon);
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
        return list.size();
    }
}
class HeadlinesRecylerViewHolder extends RecyclerView.ViewHolder{

    public ImageView headlines_icon;
    public TextView headlines_name;
    public TextView headlines_desc;
    public HeadlinesRecylerViewHolder(View itemView) {
        super(itemView);
        headlines_icon= (ImageView) itemView.findViewById(R.id.headlines_icon);
        headlines_name= (TextView) itemView.findViewById(R.id.headlines_name);
        headlines_desc= (TextView) itemView.findViewById(R.id.headlines_decs);
    }
}
