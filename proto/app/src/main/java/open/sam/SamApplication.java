package open.sam;

import android.app.Application;

public class SamApplication extends Application {

    private static ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
//        component = DaggerApplicationComponent.builder().provideOnboardingService().build();
    }

    public static ApplicationComponent getComponent() {
        return component;
    }
}