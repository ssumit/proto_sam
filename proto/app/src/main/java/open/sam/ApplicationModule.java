package open.sam;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    @Provides
    @Singleton
    OnboardingService provideOnboardingService() {
        return new OnboardingService();
    }

}
