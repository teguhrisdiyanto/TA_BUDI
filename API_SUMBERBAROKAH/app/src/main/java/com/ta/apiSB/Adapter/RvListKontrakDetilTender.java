package com.ta.apiSB.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ta.apiSB.R;
import com.ta.apiSB.RESTAPI.Kontrak.dataLaporan;
import com.ta.apiSB.Session.SessionManager;

import java.text.SimpleDateFormat;
import java.util.List;

public class RvListKontrakDetilTender extends RecyclerView.Adapter<RvListKontrakDetilTender.LisHolder> {
    Context context;
    List<dataLaporan> listDataTenderDetail;
    SessionManager mSession;
    String URL;

    class LisHolder extends RecyclerView.ViewHolder {   //disini tempat untuk inisialisasi

        CardView cvListProduk;
        TextView tvtanggalLaporan, tvlaporanmatrial, tvlaporanorang, tvlaporankegiatan;


        public LisHolder(View itemView) {
            super(itemView);

            cvListProduk = (CardView)itemView.findViewById(R.id.cv_detailTender);
            tvtanggalLaporan= (TextView) itemView.findViewById(R.id.tv_laporantanggal);
            tvlaporanmatrial = (TextView) itemView.findViewById(R.id.tv_laporanMatrial);
            tvlaporanorang= (TextView) itemView.findViewById(R.id.tv_laporanOrang);
            tvlaporankegiatan =(TextView)itemView.findViewById(R.id.tv_laporanKegiatan);
        }
    }
    public RvListKontrakDetilTender(Context context, List<dataLaporan> listData, String URL, SessionManager mSession){
        this.context = context;
        this.listDataTenderDetail = listData;
        this.URL = URL;
        this.mSession = mSession;
    }
    @NonNull
    @Override
    public RvListKontrakDetilTender.LisHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_tenderdetail, null, false);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        LisHolder lisHolder = new LisHolder(view);
        return lisHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RvListKontrakDetilTender.LisHolder lisHolder, int i) {
        String laporanmatrial,laporankegiatan, laporanorang;
        Long tgllaporan;
        tgllaporan = listDataTenderDetail.get(i).getLaporan_date();
        SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM yyyy");
        String dateText3 = df2.format(tgllaporan);
        laporanmatrial = listDataTenderDetail.get(i).getLaporan_matrial();
        laporanorang = listDataTenderDetail.get(i).getLaporan_orang();
        laporankegiatan = listDataTenderDetail.get(i).getLaporan_kegiatan();
//
        lisHolder.tvtanggalLaporan.setText(dateText3);
        lisHolder.tvlaporanmatrial.setText( laporanmatrial);
        lisHolder.tvlaporanorang.setText(laporanorang);
        lisHolder.tvlaporankegiatan.setText(laporankegiatan);

    }

    @Override
    public int getItemCount() {
        return (listDataTenderDetail == null) ? 0 : listDataTenderDetail.size();
        //ini digunakan untuk mengatasi jika belum menginput data
    }
}
