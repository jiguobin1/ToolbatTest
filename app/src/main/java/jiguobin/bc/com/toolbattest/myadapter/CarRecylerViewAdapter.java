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

import jiguobin.bc.com.toolbattest.Bean.CarInfo;
import jiguobin.bc.com.toolbattest.R;
import jiguobin.bc.com.toolbattest.onclick.SetOnClickListen;

/**
 * Created by acer-PC on 2017/4/22.
 */

public class CarRecylerViewAdapter extends RecyclerView.Adapter<CarRecylerViewHolder> {
    private SetOnClickListen setOnClickListen;
    private Context mContext;
    private List<CarInfo.T1348654060988Bean> list;
    public CarRecylerViewAdapter(Context mContext,List<CarInfo.T1348654060988Bean> list) {
        this.mContext=mContext;
        this.list=list;
    }
    public void SetClickListen(SetOnClickListen setOnClickListen){
        this.setOnClickListen=setOnClickListen;
    }

    @Override
    public CarRecylerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.car_item, parent, false);
        CarRecylerViewHolder carRecylerViewHolder = new CarRecylerViewHolder(inflate);
        return carRecylerViewHolder;
    }

    @Override
    public void onBindViewHolder(CarRecylerViewHolder holder, final int position) {
        holder.car_name.setText(list.get(position).getTitle());
        holder.car_decs.setText(list.get(position).getDigest());
        Glide.with(mContext).load(list.get(position).getImgsrc()).into(holder.car_icon);
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
class CarRecylerViewHolder extends RecyclerView.ViewHolder{

    public ImageView car_icon;
    public TextView car_name;
    public TextView car_decs;
    public CarRecylerViewHolder(View itemView) {
        super(itemView);
        car_icon= (ImageView) itemView.findViewById(R.id.car_image);
        car_name= (TextView) itemView.findViewById(R.id.car_name);
        car_decs= (TextView) itemView.findViewById(R.id.car_decs);
    }
}
