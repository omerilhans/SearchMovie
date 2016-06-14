package android.projects.searchmovie;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wissen on 12.5.2016.
 */
public class DB extends SQLiteOpenHelper
{
    SQLiteDatabase db;

    public DB(Context c)
    {
        super(c, c.getDatabasePath("Filmler.db").getAbsolutePath(), null, 3);
        db = getWritableDatabase();
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String q = "create table filmler (id integer primary key autoincrement, ad text, xml text)";
        sqLiteDatabase.execSQL(q);
    }

    public void addMovie(String ad, String xml)
    {
        db = getWritableDatabase();
        db.execSQL("insert into filmler (ad, xml) values ('"+ad+"', '"+xml+"')");
        db.close();
    }

    public String getMovie(String ad)
    {
        boolean res = false;
        String xml="NAN";
        db = getWritableDatabase();
        Cursor c = db.rawQuery("select xml from filmler where ad = '"+ad+"'", null);
        while (c.moveToNext())
        {
            res = true;
            xml = c.getString(0);
        }
        c.close();
        db.close();
        return xml;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
