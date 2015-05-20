package enough.paul.espresso21;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HelloEspresso21 {

@Rule
public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testHelloWorld() {
        onView(withText(R.string.hello_world))
                .check(matches(isDisplayed()));
    }

/*@Test
public void testHelloWorld(){
    onView(withText(R.string.hello_world))
            .check(matches(isDisplayed()));

    openDrawer(R.id.action_bar);    // extremely wrong and fake

    ActivityLifecycleMonitor activityLifecycleMonitor = ActivityLifecycleMonitorRegistry.getInstance();

}

*/
}
