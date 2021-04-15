package com.example.earthquake;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class EarthQuakeAdapter extends RecyclerView.Adapter<EarthQuakeAdapter.EarthquakeViewHolder>
{

    private static final String LOCATION_SEPERATOR = "of";

    ArrayList<Earthquake> earthquakes;
    EarthQuakeItemListener earthQuakeItemListener;
    EarthQuakeAdapter(ArrayList<Earthquake> earthquakes,EarthQuakeItemListener earthQuakeItemListener){
            this.earthquakes = earthquakes;
            this.earthQuakeItemListener = earthQuakeItemListener;
    }

    public void addAll(List<Earthquake> data){
        earthquakes.addAll(data);
        notifyDataSetChanged();
    }
    public void clear(){
        earthquakes.clear();
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public EarthquakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View listItemContainer = LayoutInflater.from(parent.getContext()).inflate(R.layout.earthquak_list_item,parent,false);
        return new EarthquakeViewHolder(listItemContainer);
    }

    @Override
    public void onBindViewHolder(@NonNull EarthquakeViewHolder holder, int position) {
            Earthquake earthquake = earthquakes.get(position);

            long date = earthquake.getDate();
            String dateToDisplay = QueryUtils.dateToDisplay(date);
            holder.getDateView().setText(dateToDisplay);

            String timeToDisplay = QueryUtils.timeToDisplay(date);
            holder.getTimeView().setText(timeToDisplay);

            String location = earthquake.getLocation();
            String primaryLocation;
            String locationOffset;
            if (location.contains(LOCATION_SEPERATOR)){
                String[] parts = location.split(LOCATION_SEPERATOR);
                locationOffset = parts[0] + LOCATION_SEPERATOR;
                primaryLocation = parts[1];
            }
            else {
                locationOffset = holder.getLocationView().getContext().getString(R.string.near_the);
                primaryLocation = location;
            }
            holder.getLocationOffsetView().setText(locationOffset);
            holder.getLocationView().setText(primaryLocation);

            double magnitude = earthquake.getMagnitude();
            int magnitudeColor = getMagnitudeColor(magnitude,holder.getMagnitudeView().getContext());
        GradientDrawable  gradientDrawable = (GradientDrawable) holder.getMagnitudeView().getBackground();
        gradientDrawable.setColor(magnitudeColor);

            holder.getMagnitudeView().setText(formatDecimal(earthquake.getMagnitude()));

            holder.itemView.setOnClickListener(view -> earthQuakeItemListener.itemClicked(earthquake));


    }

    private String formatDecimal(double magnitude){
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(magnitude);
    }


    private int getMagnitudeColor(double magnitude,Context context){
        int magnitudeResColorId;
        int magnitudeFloor = (int) Math.floor(magnitude);

        switch (magnitudeFloor){
            case 0:
            case 1:
                magnitudeResColorId = R.color.magnitude1;
                break;
            case 2:
                magnitudeResColorId = R.color.magnitude2;
                break;
            case 3:
                magnitudeResColorId = R.color.magnitude3;
                break;
            case 4:
                magnitudeResColorId = R.color.magnitude4;
                break;
            case 5:
                magnitudeResColorId = R.color.magnitude5;
                break;
            case 6:
                magnitudeResColorId = R.color.magnitude6;
                break;
            case 7:
                magnitudeResColorId = R.color.magnitude7;
                break;
            case 8:
                magnitudeResColorId = R.color.magnitude8;
                break;
            case 9:
                magnitudeResColorId = R.color.magnitude9;
                break;
            default:
                magnitudeResColorId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(context,magnitudeResColorId);

    }

    @Override
    public int getItemCount() {
        return earthquakes.size();
    }


    static class EarthquakeViewHolder extends RecyclerView.ViewHolder{

        private final TextView magnitudeView;
        private final TextView locationView;
        private final TextView dateView;
        private final TextView timeView;
        private final TextView locationOffsetView;

        public EarthquakeViewHolder(@NonNull View itemView) {
            super(itemView);

            magnitudeView = itemView.findViewById(R.id.magnitude_tv);
            locationView = itemView.findViewById(R.id.location_tv);
            dateView = itemView.findViewById(R.id.date_tv);
            timeView = itemView.findViewById(R.id.time_tv);
            locationOffsetView = itemView.findViewById(R.id.location_offset);

        }


        public TextView getLocationView() {
            return locationView;
        }

        public TextView getLocationOffsetView() {
            return locationOffsetView;
        }

        public TextView getMagnitudeView() {
            return magnitudeView;
        }

        public TextView getDateView() {
            return dateView;
        }

        public TextView getTimeView() {
            return timeView;
        }
    }
}
