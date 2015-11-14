package itp341.compestine.vinson.playlisthero;
import com.microsoft.windowsazure.mobileservices.*;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.net.MalformedURLException;

public class MainActivity extends Activity {
    private MobileServiceClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{mClient = new MobileServiceClient(
                "https://playerhero.azure-mobile.net/",
                "BbtKkMKKubarKfNOkjAawsUKVaSgpc25",
                this
        );}
        catch(MalformedURLException me){
            me.printStackTrace();
        }

        Item item = new Item();
        item.Text = "Awesome item";
        mClient.getTable(Item.class).insert(item, new TableOperationCallback<Item>() {
            public void onCompleted(Item entity, Exception exception, ServiceFilterResponse response) {
                if (exception == null) {
                    // Insert succeeded
                } else {
                    // Insert failed
                }
            }
        });

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

    public class Item {
        public String Id;
        public String Text;
    }

}
