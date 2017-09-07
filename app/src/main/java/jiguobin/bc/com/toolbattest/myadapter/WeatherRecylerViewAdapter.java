package jiguobin.bc.com.toolbattest.myadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import jiguobin.bc.com.toolbattest.Bean.WeatherInfo;
import jiguobin.bc.com.toolbattest.R;

/**
 * Created by acer-PC on 2017/5/1.
 */

public class WeatherRecylerViewAdapter extends RecyclerView.Adapter<WeatherRecylerViewHolder> {
    private Context mContext;
    private List<WeatherInfo.ResultBean.FutureBean> list;

    public WeatherRecylerViewAdapter(Context mContext,List<WeatherInfo.ResultBean.FutureBean> list) {
        this.mContext=mContext;
        this.list=list;
    }

    @Override
    public WeatherRecylerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.weather_item, parent, false);
        WeatherRecylerViewHolder weatherRecylerViewHolder = new WeatherRecylerViewHolder(inflate);
        return weatherRecylerViewHolder;
    }

    @Override
    public void onBindViewHolder(WeatherRecylerViewHolder holder, int position) {
        holder.tv_week.setText(list.get(position).getWeek());
        holder.tv_humidity.setText(list.get(position).getDate());
        holder.tv_temperature.setText(list.get(position).getTemperature());
        holder.tv_wind.setText(list.get(position).getWind());
        holder.tv_weather.setText(list.get(position).getDayTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class WeatherRecylerViewHolder extends RecyclerView.ViewHolder{

    public TextView tv_week,tv_humidity,tv_temperature,tv_wind,tv_weather;
    public WeatherRecylerViewHolder(View itemView) {
        super(itemView);
        tv_week= (TextView) itemView.findViewById(R.id.tv_week);
        tv_humidity= (TextView) itemView.findViewById(R.id.tv_humidity);
        tv_temperature= (TextView) itemView.findViewById(R.id.tv_temperature);
        tv_wind= (TextView) itemView.findViewById(R.id.tv_wind);
        tv_weather= (TextView) itemView.findViewById(R.id.tv_weather);
    }
}
