package com.nda.Rent_Master.Houses.HouseDetail.Rooms.AddRoom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nda.Rent_Master.Model.Service;
import com.nda.Rent_Master.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterAddService extends RecyclerView.Adapter<AdapterAddService.HolderAddRoom> {
    AddRoom context;
    List<Service> serviceList;
    List<Service> checkedServiceList;

    public AdapterAddService(AddRoom context, List<Service> serviceList, List<Service> checkedServiceList) {
        this.context = context;
        this.serviceList = serviceList;
        this.checkedServiceList = checkedServiceList;
    }

    @NonNull
    @Override
    public AdapterAddService.HolderAddRoom onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_services_add_room, parent,false);
        return new AdapterAddService.HolderAddRoom(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAddService.HolderAddRoom holder, int position) {
        Service service = serviceList.get(position);

        holder.txtServicesName.setText(service.getName());

        /**
         * Format cost lấy về từ firebase
         * theo định dạng money
         * */
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatter.applyPattern("#,###,###,###");
        Double cost = Double.parseDouble(service.getPrice());
        holder.txtServicesCost.setText(formatter.format(cost) + " đ/" + service.getUnit());

        holder.checkBox_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.checkBox_service.isChecked())
                {
                    checkedServiceList.add(service);
                }
                else {
                    checkedServiceList.remove(service);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public class HolderAddRoom extends RecyclerView.ViewHolder {
        CheckBox checkBox_service;
        TextView txtServicesName, txtServicesCost;

        public HolderAddRoom(@NonNull View itemView) {
            super(itemView);
            checkBox_service = itemView.findViewById(R.id.checkBox_service);

            txtServicesName = itemView.findViewById(R.id.txtServicesName);
            txtServicesCost = itemView.findViewById(R.id.txtServicesCost);

        }
    }
}
