package com.nda.Rent_Master.Houses.AddHouse;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nda.Rent_Master.R;

import java.util.List;

public class AdapterSelectCityState extends RecyclerView.Adapter<AdapterSelectCityState.HolderAddHouse> {
    Context context;
    List<String> stringList;
    Dialog dialog;
    TextView txt_selectThanhPho, txt_selectQuanHuyen;

    public AdapterSelectCityState(Context context, List<String> stringList, Dialog dialog, TextView txt_selectThanhPho,
                                  TextView txt_selectQuanHuyen) {
        this.context = context;
        this.stringList = stringList;
        this.dialog = dialog;
        this.txt_selectThanhPho = txt_selectThanhPho;
        this.txt_selectQuanHuyen = txt_selectQuanHuyen;
    }

    @NonNull
    @Override
    public HolderAddHouse onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_simple_text, parent, false);
        return new HolderAddHouse(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderAddHouse holder, int position) {
        String str = stringList.get(position);

        holder.txt_simpleStringItem.setText(str);
        holder.txt_simpleStringItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_selectThanhPho.setText(str);
                txt_selectQuanHuyen.setText("");
                dialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public class HolderAddHouse extends RecyclerView.ViewHolder {
        TextView txt_simpleStringItem;

        public HolderAddHouse(@NonNull View itemView) {
            super(itemView);

            txt_simpleStringItem = itemView.findViewById(R.id.txt_simpleStringItem);
        }
    }
}
