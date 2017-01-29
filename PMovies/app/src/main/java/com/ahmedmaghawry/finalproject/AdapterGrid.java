package com.ahmedmaghawry.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ahmed Maghawry on 10/17/2016.
 */
public class AdapterGrid extends BaseAdapter {
    ArrayList<String> result;
    placeholderFragement context;
    ArrayList<String> image;
    private static LayoutInflater inflater=null;
    public AdapterGrid(placeholderFragement mainActivity, ArrayList<String> prgmNameList, ArrayList<String> prgmImages) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=mainActivity;
        image=prgmImages;
        inflater = ( LayoutInflater )context.getActivity().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.grid_item, null);
        holder.tv=(TextView) rowView.findViewById(R.id.title);
        holder.img=(ImageView) rowView.findViewById(R.id.image);
        holder.tv.setText(result.get(position));
        Picasso.with(context.getActivity()).load("https://image.tmdb.org/t/p/w500" + image.get(position)).placeholder(R.drawable.loading).error(R.drawable.imagenotfound).resize(400,600).centerCrop().into(holder.img);
        /*rowView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Toast.makeText(context , "You Clicked " + result.get(position), Toast.LENGTH_LONG).show();
                Log.i("El7amd","Yeah");
            }
        });*/

        return rowView;
    }
}
