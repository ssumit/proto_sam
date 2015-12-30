package open.sam;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserInfoHelper {
    private final Context _context;

    @Inject
    public UserInfoHelper(Context context) {
        _context = context;
    }

    public String getName() {
        String firstName = getFirstName();
        String lastName = getLastName();
        if (firstName == null && lastName == null) {
            return null;
        } else if (firstName == null) {
            return lastName;
        } else if (lastName == null) {
            return firstName;
        } else {
            return firstName + " " + lastName;
        }
    }

    public String getUserEmail() {
        String email;
        AccountManager accountManager = AccountManager.get(_context);
        Account[] accounts = accountManager.getAccountsByType("com.google");
        if (accounts.length > 0 && accounts[0].name != null) {
            email = accounts[0].name;
        } else {
            email = null;
        }
        return email;
    }

    public String getFirstName() {
        return getFromProfile(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);
    }


    public String getLastName() {
        return getFromProfile(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME);
    }

    private String getGoogleAccountName() {
        return getUserNameFromEmail(getUserEmail());
    }

    private String getUserNameFromEmail(String email) {
        return email.split("@")[0];
    }

    private String getFromProfile(String givenName) {
        final ContentResolver content = _context.getContentResolver();
        final Cursor cursor = content.query(
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), new String[]{
                        givenName,}, ContactsContract.Contacts.Data.MIMETYPE + "=?",
                new String[]{ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE}, null
        );

        String value = null;
        if (cursor != null) {
            if (cursor.moveToNext()) {
                int firstNameIndex = cursor.getColumnIndex(givenName);
                value = cursor.getString(firstNameIndex);
            }
            cursor.close();
        }
        return value;
    }
}