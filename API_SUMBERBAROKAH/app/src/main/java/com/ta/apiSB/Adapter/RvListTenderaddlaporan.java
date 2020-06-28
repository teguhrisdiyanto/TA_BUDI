package com.ta.apiSB.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ta.apiSB.DataTransaksi.Kontrak.Activity_addlaporan;
import com.ta.apiSB.DataTransaksi.Kontrak.DetailKontrakActivity;
import com.ta.apiSB.LoginActivity;
import com.ta.apiSB.R;
import com.ta.apiSB.RESTAPI.Controller.EnkripsiController;
import com.ta.apiSB.RESTAPI.Controller.KontrakController;
import com.ta.apiSB.RESTAPI.Kontrak.ListDataItemTender;
import com.ta.apiSB.Session.SessionManager;
import com.ta.apiSB.Utils;

import java.text.SimpleDateFormat;
import java.util.List;

public class RvListTenderaddlaporan extends RecyclerView.Adapter<RvListTenderaddlaporan.LisHolder> {
    Context context;
    List<ListDataItemTender> istDataItemTender;
    SessionManager mSession;
    String URL;

    EnkripsiController controllerEnkripsi = new EnkripsiController();
    KontrakController controllerKontrak = new KontrakController();

    class LisHolder extends RecyclerView.ViewHolder {   //disini tempat untuk inisialisasi

        CardView cvListKontrak;
        TextView tvIdKontrak, tvNamaCustomer, tvAlamatCustomer, tvStatusTender, tvTanggalTender,tv_tendernote;


        public LisHolder(View itemView) {
            super(itemView);

            cvListKontrak = (CardView)itemView.findViewById(R.id.cv_listkontrakaddlaporan);
            tvIdKontrak = (TextView) itemView.findViewById(R.id.tv_idKontrak);
            tvNamaCustomer = (TextView) itemView.findViewById(R.id.tv_namaCustomer);

            tvStatusTender = (TextView) itemView.findViewById(R.id.tv_statusTender);
            tvTanggalTender = (TextView) itemView.findViewById(R.id.tv_tanggalTenderKontrak);
            tvAlamatCustomer = (TextView) itemView.findViewById(R.id.tv_alamatCustomer);
        }
    }

    public RvListTenderaddlaporan(Context context, List<ListDataItemTender> listData, String URL, SessionManager mSession){
        this.context = context;
        this.istDataItemTender = listData;
        this.URL = URL;
        this.mSession = mSession;
    }

    @NonNull
    @Override
    public RvListTenderaddlaporan.LisHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_addtenderlaporan, null, false);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        LisHolder lisHolder = new LisHolder(view);
        return lisHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RvListTenderaddlaporan.LisHolder lisHolder, int i) {
        String idKontrak, noKontrak, namaCustomer, alamatCustomer, statusTender ,tvtendernote;
        Long tanggalTender;

        idKontrak = istDataItemTender.get(i).getTender_id();
        noKontrak = istDataItemTender.get(i).getTender_nomer();
        namaCustomer = istDataItemTender.get(i).getDataPelangan().getPelanganNama();
        alamatCustomer = istDataItemTender.get(i).getDataPelangan().getPelanganAlamat();
        statusTender = istDataItemTender.get(i).getTenderStatus();
        tanggalTender = istDataItemTender.get(i).getTender_date();
        tvtendernote = istDataItemTender.get(i).getTender_note();
        SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM yyyy");
        String dateText3 = df2.format(tanggalTender);

        lisHolder.tvIdKontrak.setText(noKontrak);
        lisHolder.tvNamaCustomer.setText(namaCustomer);
        lisHolder.tvAlamatCustomer.setText(alamatCustomer);
        lisHolder.tvStatusTender.setText(statusTender);
        lisHolder.tvTanggalTender.setText(dateText3);


        if (statusTender.equalsIgnoreCase("open")) {
            lisHolder.tvStatusTender.setTextColor(Color.parseColor("#32cd32"));
        }
        else if (statusTender.equalsIgnoreCase("progres"))
        {
            lisHolder.tvStatusTender.setTextColor(Color.parseColor("#0000FF"));
        }
        else
        {
            lisHolder.tvStatusTender.setTextColor(Color.parseColor("#FF0000"));
        }

        final Bundle args = new Bundle();
        String KontraktJsonString = Utils.getGsonParser().toJson(istDataItemTender.get(i));
        args.putString("data", KontraktJsonString);


        lisHolder.cvListKontrak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, Activity_addlaporan.class);
                in.putExtras(args);
                context.startActivity(in);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (istDataItemTender == null) ? 0 : istDataItemTender.size();
        //ini digunakan untuk mengatasi jika belum menginput data
    }
}
