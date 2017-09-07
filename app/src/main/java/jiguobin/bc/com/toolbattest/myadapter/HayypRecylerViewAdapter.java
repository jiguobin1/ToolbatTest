package jiguobin.bc.com.toolbattest.myadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import jiguobin.bc.com.toolbattest.Bean.Happyinfo;
import jiguobin.bc.com.toolbattest.R;
import jiguobin.bc.com.toolbattest.onclick.SetOnClickListen;

/**
 * Created by acer-PC on 2017/4/20.
 */

public class HayypRecylerViewAdapter extends RecyclerView.Adapter<HayypRecylerViewHolder> {
    private SetOnClickListen setOnClickListen;
    private Context mContext;
    private List<Happyinfo.T1350383429665Bean> data;
    public HayypRecylerViewAdapter(Context mContext,List<Happyinfo.T1350383429665Bean> data){
        this.mContext=mContext;
        this.data=data;
    }
    public void onClick(SetOnClickListen setOnClickListen){
        this.setOnClickListen=setOnClickListen;
    }
    @Override
    public HayypRecylerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.happy_item, parent, false);
        HayypRecylerViewHolder hayypRecylerViewHolder = new HayypRecylerViewHolder(inflate);
        return hayypRecylerViewHolder;
    }

    @Override
    public void onBindViewHolder(HayypRecylerViewHolder holder, final int position) {
        holder.hName.setText(data.get(position).getTitle());
        holder.hDecs.setText(data.get(position).getDigest());
        ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
        imageLoader.displayImage(data.get(position).getImgsrc(),holder.hIcon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOnClickListen.setOnclick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
class HayypRecylerViewHolder extends RecyclerView.ViewHolder{

    public ImageView hIcon;
    public TextView hName;
    public TextView hDecs;
    public HayypRecylerViewHolder(View itemView) {
        super(itemView);
        hIcon= (ImageView) itemView.findViewById(R.id.happy_image);
        hName= (TextView) itemView.findViewById(R.id.happy_name);
        hDecs= (TextView) itemView.findViewById(R.id.happy_decs);
    }
}
