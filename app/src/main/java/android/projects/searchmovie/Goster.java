package android.projects.searchmovie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Goster extends AppCompatActivity {

    TextView tvAd, tvYil, tvTur, tvYonetmen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goster);

        String xml = getIntent().getExtras().getString("xml");

        try {
            Document doc = Jsoup.parse(xml);
            Element film = doc.select("movie").first();

            tvAd = (TextView) findViewById(R.id.ad);
            tvYil = (TextView) findViewById(R.id.yil);
            tvTur = (TextView) findViewById(R.id.tur);
            tvYonetmen = (TextView) findViewById(R.id.yonetmen);

            String ad = film.attr("title");
            String yil = film.attr("released");
            String tur = film.attr("genre");
            String yonetmen = film.attr("director");
            tvAd.setText(ad);
            tvYil.setText(yil);
            tvTur.setText(tur);
            tvYonetmen.setText(yonetmen);
            Log.e("x", "Xml : " + doc.html().toString());

        } catch (Exception e) {
        }
    }
}
