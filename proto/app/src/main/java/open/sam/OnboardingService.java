package open.sam;

import com.google.common.util.concurrent.ListenableFuture;

import javax.inject.Singleton;

@Singleton
public class OnboardingService {
    private boolean onboardingComplete;

    public boolean isOnboardingComplete() {
        return onboardingComplete;
    }

    public ListenableFuture<Void> createAccount(String firstName, String lastName, String emailId) {
        return null;
    }
}
