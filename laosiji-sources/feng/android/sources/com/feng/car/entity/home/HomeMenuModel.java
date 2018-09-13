package com.feng.car.entity.home;

import android.content.Context;
import android.content.Intent;
import com.feng.car.activity.AllProgramActivity;
import com.feng.car.activity.CarModleComparisonActivity;
import com.feng.car.activity.OldDriverChooseCarActivity;
import com.feng.car.activity.PriceRankingNewActivity;
import com.feng.car.event.HomeSwitchEvent;
import org.greenrobot.eventbus.EventBus;

public class HomeMenuModel {
    public int index;
    public int resourcesID;

    public HomeMenuModel(int index, int resourcesID) {
        this.index = index;
        this.resourcesID = resourcesID;
    }

    public void handleItemClickListener(Context context) {
        switch (this.index) {
            case 0:
                EventBus.getDefault().post(new HomeSwitchEvent());
                return;
            case 1:
                context.startActivity(new Intent(context, PriceRankingNewActivity.class));
                return;
            case 2:
                context.startActivity(new Intent(context, CarModleComparisonActivity.class));
                return;
            case 3:
                context.startActivity(new Intent(context, OldDriverChooseCarActivity.class));
                return;
            case 4:
                context.startActivity(new Intent(context, AllProgramActivity.class));
                return;
            default:
                return;
        }
    }
}
