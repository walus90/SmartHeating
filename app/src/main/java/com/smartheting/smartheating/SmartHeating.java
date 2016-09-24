package com.smartheting.smartheating;

import android.app.Application;

import dagger.config.AppModule;
import dagger.config.DaggerRealmComponent;
import dagger.config.RealmComponent;
import io.realm.Realm;

/**
 * Created by Wojtek on 2016-09-13.
 */
public class SmartHeating extends Application {

    private RealmComponent realmComponent;


    @Override
    public void onCreate(){
        super.onCreate();
        realmComponent = DaggerRealmComponent.builder().appModule(new AppModule(this)).build();
    }

    RealmComponent getRealmComponent(){
        return realmComponent;
    }

}
