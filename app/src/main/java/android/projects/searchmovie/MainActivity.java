package android.projects.searchmovie;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.jsoup.Jsoup;

// Yazılan filmin bilgilerini internetten indirip db'ye atar bir sonraki aynı arama db'den yapılır.
public class MainActivity extends AppCompatActivity {

    EditText etAra;
    DB db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etAra = (EditText) findViewById(R.id.filmAdi);
        db = new DB(this);
    }

    public void ara(View v) {
        String ad = etAra.getText().toString();

        String res = db.getMovie(ad);

        if (res.equals("NAN")) {
            Toast.makeText(this, "Film Webten Aranıyor", Toast.LENGTH_SHORT).show();
            new ASYNC().execute(ad);
        } else {
            Intent i = new Intent(this, Goster.class);
            i.putExtra("xml", res);
            startActivity(i);
        }
    }


    class ASYNC extends AsyncTask<String, String, String> {
        String filmAdi = "";

        @Override
        protected String doInBackground(String... params) {
            try {
                // http://www.omdbapi.com/?t=bonanza&y=&plot=short&r=xml
                filmAdi = params[0];
                String xmlData = Jsoup.connect("http://www.omdbapi.com/")
                        .data("t", filmAdi, "y", "", "plot", "short", "r", "xml")
                        .get()
                        .html().trim();

                db.addMovie(filmAdi, xmlData);
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            String xml = db.getMovie(filmAdi);
            Intent i = new Intent(MainActivity.this, Goster.class);
            i.putExtra("xml", xml);
            startActivity(i);
        }
    }
}
