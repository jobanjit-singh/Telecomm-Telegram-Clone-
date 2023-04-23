package com.example.telecomm;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;

public class CustomLoadingDialog {
    Dialog d;
    public CustomLoadingDialog(Context c){
        d = new Dialog(c);
    }
    public void showDialog(){
        d.setContentView(R.layout.custom_loading_dialog);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        d.setCancelable(false);
        d.show();
    }
    public void hideDialog(){
        d.dismiss();
    }
}
