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

import jiguobin.bc.com.toolbattest.Bean.ImageInfo;
import jiguobin.bc.com.toolbattest.R;
import jiguobin.bc.com.toolbattest.onclick.SetOnClickListen;

/**
 * Created by acer-PC on 2017/4/24.
 */

public class ImageRecylerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private SetOnClickListen setOnClickListen;
    private Context mContext;
    private List<ImageInfo> list;
    public ImageRecylerViewAdapter(Context mContext,List<ImageInfo> list) {
        this.mContext=mContext;
        this.list=list;
    }
    public void onClick(SetOnClickListen setOnClickListen){
        this.setOnClickListen=setOnClickListen;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_title.setText(list.get(position).getTitle());
        Glide.with(mContext).load(list.get(position).getThumburl()).into(holder.iv_icon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOnClickListen.setOnclick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class MyViewHolder extends RecyclerView.ViewHolder{
    public ImageView iv_icon;
    public TextView tv_title;
    public MyViewHolder(View itemView) {
        super(itemView);
        iv_icon= (ImageView) itemView.findViewById(R.id.iv_icon);
        tv_title= (TextView) itemView.findViewById(R.id.tv_title);
    }
}
