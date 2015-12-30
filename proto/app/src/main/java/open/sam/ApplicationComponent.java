package open.sam;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import open.sam.network.IPManager;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    OnboardingService provideOnboardingService();

    Context provideApplicationContext();

    UserInfoHelper provideUserInfoHelper();

    IPManager provideIPManager();
}
