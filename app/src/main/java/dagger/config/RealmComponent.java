package dagger.config;

import com.smartheting.smartheating.MainActivity;
import com.smartheting.smartheating.StatisticsActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Wojtek on 2016-09-13.
 */

@Singleton
@Component(modules = {RealmModule.class, AppModule.class})
public interface RealmComponent {
    void inject(MainActivity mainActivity);
    void inject(StatisticsActivity statisticsActivity);
}
