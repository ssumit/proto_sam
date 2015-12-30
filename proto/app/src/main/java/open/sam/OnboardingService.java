package open.sam;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import open.sam.store.KeyValueStore;

@Singleton
public class OnboardingService {
    public static final String FIRST_NAME = "FIRST_NAME";
    public static final String LAST_NAME = "LAST_NAME";
    public static final String EMAIL = "EMAIL";
    private boolean onboardingComplete;
    private final KeyValueStore kvStore;

    @Inject
    public OnboardingService(KeyValueStore keyValueStore) {
        this.kvStore = keyValueStore;
        onboardingComplete = keyValueStore.contains(EMAIL);
    }

    public boolean isOnboardingComplete() {
        return onboardingComplete;
    }

    public ListenableFuture<Void> createAccount(String firstName, String lastName, String emailId) {
        onboardingComplete = true;
        kvStore.putString(FIRST_NAME, firstName);
        kvStore.putString(LAST_NAME, lastName);
        kvStore.putString(EMAIL, emailId);
        return Futures.immediateFuture(null);
    }
}