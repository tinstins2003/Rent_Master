package com.nda.Rent_Master.Houses.HouseDetail.Rooms.RoomDetail.HoaDon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nda.Rent_Master.Houses.HouseDetail.Rooms.RoomDetail.RoomDetailSystem;
import com.nda.Rent_Master.Model.HoaDon;
import com.nda.Rent_Master.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterHoaDon extends RecyclerView.Adapter<AdapterHoaDon.HolderHoaDon> {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef  = database.getReference();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();


    RoomDetailSystem context;
    List<HoaDon> hoaDonList;


    public AdapterHoaDon(RoomDetailSystem context, List<HoaDon> hoaDonList) {
        this.context = context;
        this.hoaDonList = hoaDonList;
    }

    @NonNull
    @Override
    public AdapterHoaDon.HolderHoaDon onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hoadon, parent,false);
        return new AdapterHoaDon.HolderHoaDon(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHoaDon.HolderHoaDon holder, int position) {
        HoaDon hoaDon = hoaDonList.get(position);

        holder.txt_showFullSelectedTime.setText("(" + hoaDon.getHoaDonThang() + ")");

        String[] splitHoaDonThang = hoaDon.getHoaDonThang().split("/");
        holder.txt_dateHoaDon.setText("#" + splitHoaDonThang[1] + "/" + splitHoaDonThang[2]);


        holder.txt_houseName.setText(hoaDon.getRentHouse());
        holder.txt_roomName.setText(hoaDon.getRentRoom());

        /**
         * Format cost lấy về từ firebase
         * theo định dạng money
         * */
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatter.applyPattern("#,###,###,###");
        Double cost = Double.parseDouble(hoaDon.getRoomFee());
        holder.txt_roomFee.setText(formatter.format(cost));

        Double cost2 = Double.parseDouble(hoaDon.getTotalServiceFee());
        holder.txt_serviceFee.setText(formatter.format(cost2));

        int sumFee = Integer.parseInt(hoaDon.getRoomFee()) + Integer.parseInt(hoaDon.getTotalServiceFee());
        Double costSumFee = Double.parseDouble(String.valueOf(sumFee));
        holder.txt_tongTien.setText(formatter.format(costSumFee));

        if (hoaDon.isDaThanhToan() == false)
        {
            holder.txt_chuaThanhToan.setVisibility(View.VISIBLE);
            holder.txt_daThanhToan.setVisibility(View.GONE);
        }
        else {
            holder.txt_daThanhToan.setVisibility(View.VISIBLE);
            holder.txt_chuaThanhToan.setVisibility(View.GONE);
        }
        holder.cv_hoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetHoaDonOption(hoaDon);
            }
        });
    }


    @Override
    public int getItemCount() {
        return hoaDonList.size();
    }

    public class HolderHoaDon extends RecyclerView.ViewHolder {
        TextView txt_dateHoaDon, txt_houseName, txt_roomName, txt_tongTien, txt_roomFee, txt_serviceFee, txt_showFullSelectedTime;

        TextView txt_chuaThanhToan, txt_daThanhToan;

        CardView cv_hoaDon;
        public HolderHoaDon(@NonNull View itemView) {
            super(itemView);

            txt_dateHoaDon = itemView.findViewById(R.id.txt_dateHoaDon);
            txt_houseName = itemView.findViewById(R.id.txt_houseName);
            txt_roomName = itemView.findViewById(R.id.txt_roomName);
            txt_tongTien = itemView.findViewById(R.id.txt_tongTien);
            txt_roomFee = itemView.findViewById(R.id.txt_roomFee);
            txt_serviceFee = itemView.findViewById(R.id.txt_serviceFee);
            txt_showFullSelectedTime = itemView.findViewById(R.id.txt_showFullSelectedTime);

            txt_chuaThanhToan = itemView.findViewById(R.id.txt_chuaThanhToan);
            txt_daThanhToan = itemView.findViewById(R.id.txt_daThanhToan);

            cv_hoaDon   = itemView.findViewById(R.id.cv_hoaDon);
        }
    }

    private void bottomSheetHoaDonOption(HoaDon hoaDon) {
        View view = context.getLayoutInflater().inflate(R.layout.bottomsheet_hoadon_option,null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);

        TextView txt_editHoaDon = view.findViewById(R.id.txt_editHoaDon);
        TextView txt_deleteHoaDon = view.findViewById(R.id.txt_deleteHoaDon);
        CardView cv_closeBottomSheet = view.findViewById(R.id.cv_closeBottomSheet);

        txt_editHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.editHoaDon(hoaDon, bottomSheetDialog);
            }
        });

        txt_deleteHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.dialogConfirmDeleteHoaDon(hoaDon, bottomSheetDialog);
            }
        });

        cv_closeBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();

    }


}
