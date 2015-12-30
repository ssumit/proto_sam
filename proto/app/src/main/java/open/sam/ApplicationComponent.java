package open.sam;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    OnboardingService provideOnboardingService();

    Context provideApplicationContext();

    UserInfoHelper provideUserInfoHelper();
}
