package enough.paul.espresso21;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    public static String URI = "http://appoll";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void launchShareAction(View view) {
     /*   Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra("cheie", "valoare");

        startActivity(Intent.createChooser(shareIntent, "Trimite aici"));*/

        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogLevelsStyle);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       // View view = inflater.inflate(R.layout.dialog_game_level, null);
        builder.setMessage("Yoo yooo");
        builder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (dialog != null) {
                            dialog.cancel();
                        }
                    }
                });
        dialog = builder.create();
        dialog.show();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setText("CEEEEEEEEE");
        positiveButton.setGravity(Gravity.CENTER_HORIZONTAL);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!hasFocus) {
            Intent closeDialogs = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialogs);
        }
    }

    public void launchSecondActivity(View v){
        Intent intent = new Intent(this, SecondActivity.class);
        Uri testUri = Uri.parse(URI);

        //intent.setData(testUri);
        //intent.setType("text/plain");
        // cand folosesc si setType si setData nu imi apare la recorded intents ca al meu ar avea dat = uri, apare doar typ =

        intent.putExtra("cheie", "valoare");
        startActivity(intent);
    }

    public void launchContactPicker(View v){
        Intent intent = new Intent (Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent, 14);
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("DEBUG onResume");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("DEBUG onActivityResult");

        TextView result = (TextView) findViewById(R.id.pickedContact);

        if (requestCode == 14 && resultCode == RESULT_OK){

            Uri contactUri = data.getData();
            System.out.println(" URI contact: " + contactUri);

            String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

            Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
            cursor.moveToFirst();

            int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String number = cursor.getString(column);

            result.setText(number);
        }

        else if (requestCode == 15 && resultCode == RESULT_OK){
            String correctUsername = data.getStringExtra("username");
            result.setText(correctUsername);
        }
    }

    public void launchLoginActivity(View view) {
        Intent loginIntent = new Intent ();
        loginIntent.setClass(this, LoginActivity.class);
        startActivityForResult(loginIntent, 15);
    }



   /* public static Uri getContactUriByName(String contactName){
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if (cursor.getCount() > 0){
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            if (name.equals(contactName)){
                return ContactsContract.Contacts.getLookupUri(Long.parseLong(id),null);
            }
        }
        return null;
    }*/

}
