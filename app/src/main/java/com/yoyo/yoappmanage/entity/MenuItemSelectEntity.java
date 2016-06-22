package com.yoyo.yoappmanage.entity;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

/**
 * Created by nidey on 2016/6/22.
 */
public class MenuItemSelectEntity {
    View menuItemView;
    Drawable menuItemViewDrawable;

    public MenuItemSelectEntity(View menuItemView){
        this.menuItemView=menuItemView;
        this.menuItemViewDrawable=menuItemView.getBackground();
    }

    public void setBackground(){
        setBackground(menuItemViewDrawable);
    }
    public void setBackground(Drawable drawable){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            menuItemView.setBackground(drawable);
        } else {
            menuItemView.setBackgroundDrawable(drawable);
        }
    }

    public void setBackground(int rDrawableId){
        menuItemView.setBackgroundResource(rDrawableId);
    }
}
