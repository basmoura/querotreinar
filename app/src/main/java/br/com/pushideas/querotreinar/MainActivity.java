package br.com.pushideas.querotreinar;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import java.util.List;

import br.com.pushideas.querotreinar.models.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;
import br.com.pushideas.querotreinar.models.Trainer;


public class MainActivity extends Activity {

    private List<Trainer> trainers;
    private QueroTreinarService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://192.168.25.21:3000").build();

            service = restAdapter.create(QueroTreinarService.class);
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();

            service.getTrainers(
                    new Callback<List<Trainer>>() {
                        @Override
                        public void success(List<Trainer> _trainers, Response response) {
                            trainers = _trainers;
                            Log.i("TAG", "lista de trainers");
                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });
        }
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    public interface QueroTreinarService {
        @Headers("Content-Type: application/json")
        @GET("/trainers")
        void getTrainers(Callback<List<Trainer>> callback);
    }
}
