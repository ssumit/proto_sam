package open.sam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import javax.inject.Inject;

public class BaseActivity extends ActionBarActivity {

    @Inject
    private OnboardingService _onboardingService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean onboardingComplete = _onboardingService.isOnboardingComplete();
        if (!onboardingComplete) {
            Intent intent = new Intent(this, OnboardingActivity.class);
            startActivity(intent);
            finish();
        }
    }
}