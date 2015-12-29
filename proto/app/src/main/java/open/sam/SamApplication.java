package open.sam;

import android.app.Application;

public class SamApplication extends Application {
    private static OnboardingService onboardingService;

    public static OnboardingService getOnboardingService() {
        return onboardingService;
    }
}
