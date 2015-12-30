package open.sam;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class OnboardingService {
    private boolean onboardingComplete;

    public boolean isOnboardingComplete() {
        return onboardingComplete;
    }
}
