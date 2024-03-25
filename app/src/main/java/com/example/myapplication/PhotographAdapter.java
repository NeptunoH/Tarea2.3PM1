package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class PhotographAdapter extends ArrayAdapter<Photograph> {
    private LayoutInflater inflater;

    public PhotographAdapter(Context context, List<Photograph> photographs) {
        super(context, 0, photographs);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_layout, parent, false);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.imageView);
            holder.descriptionTextView = convertView.findViewById(R.id.descriptionTextView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Photograph photograph = getItem(position);

        // Asignar datos a las vistas
        holder.imageView.setImageBitmap(photograph.getImage());
        holder.descriptionTextView.setText(photograph.getDescription());

        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView descriptionTextView;
    }
}
