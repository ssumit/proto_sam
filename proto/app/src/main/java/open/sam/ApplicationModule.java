package open.sam;

import android.app.Application;
import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import open.sam.network.IPManager;

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

    @Provides
    @Singleton
    IPManager provideIPManager(Context context, @Named("app_executor") ExecutorService executorService) {
        return new IPManager(context, executorService);
    }

    @Provides
    @Singleton
    @Named("app_executor")
    ExecutorService provideExecutorService() {
        return Executors.newSingleThreadExecutor();
    }
}