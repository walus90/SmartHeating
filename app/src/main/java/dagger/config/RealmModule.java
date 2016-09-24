package dagger.config;

import android.app.Application;
import android.content.Context;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Wojtek on 2016-09-13.
 */
@EBean
@Module
public class RealmModule {

    public RealmModule(){
    }


    @Provides static Realm providesRealm(Application application){
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(application.getBaseContext()).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfig);
        // Open the Realm for the UI thread.
        //realm = Realm.getInstance(realmConfig);
        Realm realm = Realm.getDefaultInstance();
        return realm;
    }
}
