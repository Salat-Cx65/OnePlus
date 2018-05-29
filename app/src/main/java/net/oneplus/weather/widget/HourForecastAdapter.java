package net.oneplus.weather.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import net.oneplus.weather.model.HourForecastsWeatherData;

public class HourForecastAdapter extends Adapter<ViewHolder> {
    private final Context mContext;
    private List<HourForecastsWeatherData> mDataset;

    public static class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        public ImageView mImageViewIcon;
        public TextView mTextViewTemperature;
        public TextView mTextViewTime;

        public ViewHolder(View v) {
            super(v);
            this.mTextViewTime = (TextView) v.findViewById(R.id.textViewTime);
            this.mImageViewIcon = (ImageView) v.findViewById(R.id.imageViewIcon);
            this.mTextViewTemperature = (TextView) v.findViewById(R.id.textViewTemperature);
        }
    }

    public HourForecastAdapter(Context context) {
        this.mContext = context;
    }

    public void bindForecastData(List<HourForecastsWeatherData> dataset) {
        this.mDataset = dataset;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.hour_forecast_item, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextViewTime.setText(((HourForecastsWeatherData) this.mDataset.get(position)).getHourText());
        holder.mImageViewIcon.setImageResource(((HourForecastsWeatherData) this.mDataset.get(position)).getWeatherIconId());
        holder.mTextViewTemperature.setText(((HourForecastsWeatherData) this.mDataset.get(position)).getTemperature());
    }

    public int getItemCount() {
        return this.mDataset == null ? 0 : this.mDataset.size();
    }
}
