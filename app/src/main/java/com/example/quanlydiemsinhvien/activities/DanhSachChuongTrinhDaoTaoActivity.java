package com.example.quanlydiemsinhvien.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.util.Attributes;
import com.example.quanlydiemsinhvien.R;
import com.example.quanlydiemsinhvien.adapters.ChuongTrinhDaoTaoAdapter;
import com.example.quanlydiemsinhvien.adapters.KhoaHocAdapter;
import com.example.quanlydiemsinhvien.data_models.LopHocPhan;
import com.example.quanlydiemsinhvien.data_models.MonHoc;
import com.example.quanlydiemsinhvien.dialogs.DialogAddChuongTrinhDaoTao;
import com.example.quanlydiemsinhvien.dialogs.DialogAddOrEditKhoahoc;
import com.example.quanlydiemsinhvien.divider.DividerItemDecoration;
import com.example.quanlydiemsinhvien.interfaces.OnItemClickToAddListener;
import com.example.quanlydiemsinhvien.interfaces.OnItemClickToDeleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;

import static com.example.quanlydiemsinhvien.activities.DanhSachKhoaHocActivity.ADD_SUCCESS_NOTIFY;
import static com.example.quanlydiemsinhvien.activities.DanhSachLopHPTheoMHActivity.DB_LOPHOCPHAN_NAME;

public class DanhSachChuongTrinhDaoTaoActivity extends AppCompatActivity implements OnItemClickToAddListener, OnItemClickToDeleteListener {
    RecyclerView rvChuongTrinhDaoTaos;
    private ArrayList<MonHoc> dsMonHocs = new ArrayList<MonHoc>();
    private String maMH;
    TextView txtNganh;
    private ChuongTrinhDaoTaoAdapter chuongTrinhDaoTaoAdapter;

    private Intent intent;

    private String maKH;
    private String maNganh;
    private MonHoc monHoc;

    private Map<String, Object> map;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    DatabaseReference referenceCTDT;

    public static final String DB_CTDT_NAME = "ChuongTrinhDaoTao";
    public static final String TAG = "tag";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chuong_trinh_dao_tao_layout);

        rvChuongTrinhDaoTaos = findViewById(R.id.rvChuongtrinhdaotao);
        txtNganh = findViewById(R.id.txtNganh);
        dsMonHocs = new ArrayList<MonHoc>();
        intent = getIntent();
        maKH = intent.getStringExtra(KhoaHocAdapter.MAKH_STRING);
        maNganh = intent.getStringExtra(KhoaHocAdapter.MANGANH_STRING);
        txtNganh.setText(maNganh + " " + maKH);

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference(DB_CTDT_NAME).child(maNganh).child(maKH);
        referenceCTDT = rootNode.getReference("MonHoc");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dsMonHocs.clear();
                for (final DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    referenceCTDT.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                if (dataSnapshot.getKey().equals(dataSnapshot1.getKey())) {
                                    monHoc = new MonHoc();
                                    monHoc = dataSnapshot1.getValue(MonHoc.class);
                                    dsMonHocs.add(monHoc);
                                }
                            }
                            chuongTrinhDaoTaoAdapter = new ChuongTrinhDaoTaoAdapter(DanhSachChuongTrinhDaoTaoActivity.this, dsMonHocs);
                            rvChuongTrinhDaoTaos.setHasFixedSize(true);
                            rvChuongTrinhDaoTaos.setAdapter(chuongTrinhDaoTaoAdapter);
                            ((ChuongTrinhDaoTaoAdapter) chuongTrinhDaoTaoAdapter).setMode(Attributes.Mode.Single);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e(TAG, DialogAddOrEditKhoahoc.THOAT_STRING, error.toException());
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, DialogAddOrEditKhoahoc.THOAT_STRING, error.toException());
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvChuongTrinhDaoTaos.setLayoutManager(linearLayoutManager);
        // Item decoration
        rvChuongTrinhDaoTaos.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider, getTheme())));
        // Item Animators
        rvChuongTrinhDaoTaos.setItemAnimator(new FadeInLeftAnimator());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.them_item: {
                openAddDiaLog();
                return true;
            }
            case R.id.thoat_item: {
                // do your code
                AlertDialog.Builder builder = new AlertDialog.Builder(DanhSachChuongTrinhDaoTaoActivity.this);
                builder.setTitle("Thoát chương trình");
                builder.setMessage("Bạn có muốn thoát khỏi chương trình?");
                builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton(DialogAddOrEditKhoahoc.THOAT_STRING, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        intent.setClass(getApplicationContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
//                finishAffinity();
//                System.exit(0);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openAddDiaLog() {
        DialogAddChuongTrinhDaoTao addOrDeleteChuongTrinhDaoTao = new DialogAddChuongTrinhDaoTao();
        addOrDeleteChuongTrinhDaoTao.show(getSupportFragmentManager(), "Thêm môn học");
    }

    @Override
    public void applyObject(Object object) {
        maMH = (String) object;
        reference.child(maMH).setValue(true);
        chuongTrinhDaoTaoAdapter.notifyDataSetChanged();
        Toast.makeText(DanhSachChuongTrinhDaoTaoActivity.this, ADD_SUCCESS_NOTIFY, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteObject(Object object, int position) {
        dsMonHocs.remove(position);
        maMH = (String) object;
        reference.child(maMH).removeValue();
        
        DatabaseReference referenceLHP = rootNode.getReference(DB_LOPHOCPHAN_NAME);
        referenceLHP.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot != null){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        if(dataSnapshot.getValue(LopHocPhan.class).getMaMH().equals(maMH)){
                            dataSnapshot.getRef().removeValue();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, DialogAddOrEditKhoahoc.THOAT_STRING, error.toException());
            }
        });
        rvChuongTrinhDaoTaos.removeViewAt(position);
        chuongTrinhDaoTaoAdapter.notifyDataSetChanged();
        Toast.makeText(DanhSachChuongTrinhDaoTaoActivity.this, ADD_SUCCESS_NOTIFY, Toast.LENGTH_SHORT).show();
    }
}
