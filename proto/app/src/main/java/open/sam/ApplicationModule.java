package open.sam;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    OnboardingService provideOnboardingService() {
        return new OnboardingService();
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    UserInfoHelper provideUserInfoHelper(Context context) {
        return new UserInfoHelper(context);
    }
}
