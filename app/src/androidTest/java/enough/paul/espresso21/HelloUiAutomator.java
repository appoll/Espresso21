package enough.paul.espresso21;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.Until;
import android.test.InstrumentationTestCase;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by Paul on 30/04/2015.
 */

public class HelloUiAutomator extends InstrumentationTestCase {
    private UiDevice mDevice;

    @Override
    protected void setUp() throws Exception {
        mDevice = UiDevice.getInstance(getInstrumentation());

        mDevice.pressHome();

        final String launcherPackage = getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), 5000);

        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage("enough.paul.espresso21");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        mDevice.wait(Until.hasObject(By.pkg("enough.paul.espresso21")), 5000);
    }

    public String getLauncherPackageName(){
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        PackageManager packageManager = InstrumentationRegistry.getContext().getPackageManager();
        ResolveInfo resolveInfo = packageManager.resolveActivity(intent,PackageManager.MATCH_DEFAULT_ONLY);

        return resolveInfo.activityInfo.packageName;
    }

}
