package enough.paul.espresso21;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasType;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)

public class ChooserIntent {

    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<>(MainActivity.class);


    @Test
    public void validateIntentSentToPackage() {
        onView(withId(R.id.shareIntentLauncher))
                .check(matches(isDisplayed()))
                .perform(click());

        intended(hasType("text/plain"));
        intended(hasAction(Intent.ACTION_CHOOSER));

        //intended(hasAction(Intent.ACTION_SEND)); // is not found. why not?
    }

    @Test
    public void testSecondActivityIntent() {
        onView(withId(R.id.secondActivityLauncher))
                .check(matches(isDisplayed()))
                .perform(click());

        intended(toPackage("enough.paul.espresso21"));
        intended(hasComponent(hasClassName(SecondActivity.class.getName())));

        intended(hasExtra("cheie", "valoare"));

//        intended(hasData(Uri.parse(MainActivity.URI)));
        //  intended(hasType("What"));
    }

    @Test
    public void testContactPickerResult() {
        Intent resultData = new Intent();

        resultData.setData(getContactUriByName("Test"));

        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        intending(toPackage("com.google.android.contacts")).respondWith(result);

        onView(withId(R.id.contactPickerLauncher))
                .check(matches(isDisplayed()))
                .perform(click());

       /* intended(hasAction(Intent.ACTION_PICK));
        intended(hasData(ContactsContract.Contacts.CONTENT_URI));
        intended(toPackage("com.google.android.contacts"));
        intended(hasType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE));*/

        onView(withId(R.id.pickedContact))
                .check(matches(withText(getContactNumberByName("Test"))));
    }
    public Uri getContactUriByName(String contactName) {
        Cursor cursor = mActivityRule.getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                if (name.equals(contactName)) {
                    return Uri.withAppendedPath(ContactsContract.Data.CONTENT_URI, id);
                }
            }
        }
        return null;
    }

    public String getContactNumberByName(String contactName){
        Cursor cursor = mActivityRule.getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                if (name.equals(contactName)) {
                    return number;
                }
            }
        }
        return null;
    }

    @Test
    public void testLoginWithWrongCredentials(){
        onView(withText(R.string.hello_world))
                .check(matches(isDisplayed()));

        onView(withId(R.id.loginActivityLauncher))
                .perform(click());

        intended(toPackage("enough.paul.espresso21"));
        intended(hasComponent(hasClassName(LoginActivity.class.getName())));

        onView(withText(R.string.hello_login))
                .check(matches(isDisplayed()));

        onView(withId(R.id.username))
                .check(matches(isDisplayed()))
                .perform(typeText("nu"), closeSoftKeyboard());

        onView(withId(R.id.login))
                .perform(click());

        onView(withText(R.string.hello_login))
                .check(matches(isDisplayed()));
    }



    @Test
    public void testLoginWithCorrectCredentials(){
        onView(withText(R.string.hello_world))
                .check(matches(isDisplayed()));

        onView(withId(R.id.loginActivityLauncher))
                .perform(click());

        intended(toPackage("enough.paul.espresso21"));
        intended(hasComponent(hasClassName(LoginActivity.class.getName())));

        Intent resultIntent = new Intent ();
        resultIntent.putExtra("username", "ce faci?");
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultIntent);

        //intending(hasComponent(hasClassName(LoginActivity.class.getName()))).respondWith(result);


        onView(withText(R.string.hello_login))
                .check(matches(isDisplayed()));

        onView(withId(R.id.username))
                .check(matches(isDisplayed()))
                .perform(typeText("da"), closeSoftKeyboard());

        onView(withId(R.id.login))
                .perform(click());

        //intended(hasComponent(hasClassName(MainActivity.class.getName())));


        onView(withText(R.string.hello_world))
                .check(matches(isDisplayed()));
    }
}
