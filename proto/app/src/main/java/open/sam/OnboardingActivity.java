package open.sam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;

import javax.inject.Inject;

import open.sam.network.IPManager;

public class OnboardingActivity extends BaseActivity {
    private TextView _fNameTextView;
    private TextView _lNameTextView;
    private TextView _emailTextView;
    private TextView _nextButton;
    @Inject
    UserInfoHelper _userInfoHelper;
    private OnboardingScreenErrorStateHelper _errorStateHelper;
    @Inject
    IPManager ipManager;
    private ProgressDialog _progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ipManager = SamApplication.getComponent().provideIPManager();
        _userInfoHelper = SamApplication.getComponent().provideUserInfoHelper();
        _errorStateHelper = new OnboardingScreenErrorStateHelper(OnboardingActivity.this,
                getSupportFragmentManager());
        setContentView(R.layout.onboarding_screen);
        _fNameTextView = (TextView) findViewById(R.id.first_name);
        _lNameTextView = (TextView) findViewById(R.id.last_name);
        _emailTextView = (TextView) findViewById(R.id.email);
        _nextButton = (TextView) findViewById(R.id.next_button);
        setupNameTextView();
        setupEMailTextView();
        setupNextButton();
        setButtonHandlerOnKeyboardPopUp();
    }

    private void setupNameTextView() {
        _fNameTextView.setText(_userInfoHelper.getFirstName());
        _lNameTextView.setText(_userInfoHelper.getLastName());
        _fNameTextView.addTextChangedListener(getTextChangedListener());
        _lNameTextView.addTextChangedListener(getTextChangedListener());
    }

    private void setupEMailTextView() {
        _emailTextView.addTextChangedListener(getTextChangedListener());
        insertAccountEMailIntoTextViewIfAvailable();
    }

    private void insertAccountEMailIntoTextViewIfAvailable() {
        String userEmail = _userInfoHelper.getUserEmail();
        if (userEmail != null) {
            _emailTextView.setText(userEmail);
        }
    }

    private TextWatcher getTextChangedListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int p, int p1, int p2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int p, int p1, int p2) {
                enableNextButtonIfTextFieldsAreNotEmpty();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
    }

    private void enableNextButtonIfTextFieldsAreNotEmpty() {
        boolean isEmailEmpty = TextUtils.isEmpty(_emailTextView.getText());
        boolean isFNameEmpty = TextUtils.isEmpty(_fNameTextView.getText());
        boolean isLNameEmpty = TextUtils.isEmpty(_lNameTextView.getText());
        boolean isEnabled = !isEmailEmpty && !isFNameEmpty && !isLNameEmpty;
        _nextButton.setEnabled(isEnabled);
    }

    private void setButtonHandlerOnKeyboardPopUp() {
        final View activityRootView = findViewById(R.id.onboarding_activity_root);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                activityRootView.getWindowVisibleDisplayFrame(r);

                int heightDiff = activityRootView.getRootView().getHeight() - (r.bottom - r.top);
                if (heightDiff > 100) {
                    activityRootView.scrollTo(0, _nextButton.getBottom());
                }
            }
        });
    }

    private void setupNextButton() {
        _nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ipManager.isNetworkAvailable()) {
                    _errorStateHelper.displayNoNetworkConnectionAvailableError();
                } else {
                    String emailId = _emailTextView.getText().toString().trim();
                    String firstName = _fNameTextView.getText().toString().trim();
                    String lastName = _lNameTextView.getText().toString().trim();

                    if (isValidEmail(emailId)) {
                        _progressDialog = new ProgressDialog(OnboardingActivity.this);
                        _progressDialog.setMessage(getString(R.string.please_wait));
                        _progressDialog.setCancelable(false);
                        _progressDialog.show();
                        Futures.addCallback(_onboardingService.createAccount(firstName, lastName, emailId),
                                new FutureCallback<Void>() {
                                    @Override
                                    public void onSuccess(Void result) {
                                        closeProgressDialog();
                                        Intent intent = new Intent(OnboardingActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(Throwable t) {
                                        closeProgressDialog();
                                        _errorStateHelper.displayUnknownError();
                                        _nextButton.setEnabled(true);
                                    }
                                });
                    } else {
                        _errorStateHelper.displayInvalidEmailAddressEntered();
                    }
                }
            }
        });
        enableNextButtonIfTextFieldsAreNotEmpty();
    }

    private void closeProgressDialog() {
        if (_progressDialog != null) {
            try {
                _progressDialog.dismiss();
            } catch (IllegalArgumentException ignored) {
            }
        }
    }

    private boolean isValidEmail(String emailId) {
        return !TextUtils.isEmpty(emailId) && Patterns.EMAIL_ADDRESS.matcher(emailId).matches();
    }
}