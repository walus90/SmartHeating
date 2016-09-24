package dagger.config;

import android.app.Application;

import org.androidannotations.annotations.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Wojtek on 2016-09-14.
 */
@Module
public class AppModule {
    Application application;

    public AppModule(Application a){
        application = a;
    }

    @Provides
    @Singleton
    public Application providesApplication(){
        return application;
    }

}
