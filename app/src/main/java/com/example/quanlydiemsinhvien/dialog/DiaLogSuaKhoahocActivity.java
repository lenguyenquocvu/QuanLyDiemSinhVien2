package com.example.quanlydiemsinhvien.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.quanlydiemsinhvien.R;

public class DiaLogSuaKhoahocActivity extends AppCompatDialogFragment {
    private EditText edtBatdau;
    private EditText edtKetthuc;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sua_khoahoc_layout, null);
        edtBatdau = view.findViewById(R.id.edtKhBatDau);
        edtKetthuc = view.findViewById(R.id.edtKhKetThuc);
        builder.setView(view)
                .setTitle("Cập nhật lại khóa học")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openDialog();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }

    public void openDialog() {
        DiaLogSuaKhoahocActivity dialog = new DiaLogSuaKhoahocActivity();
        dialog.show(getFragmentManager(), "Cập nhật lại khóa học");
    }
}
