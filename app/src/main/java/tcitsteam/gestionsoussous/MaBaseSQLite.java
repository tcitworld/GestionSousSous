package tcitsteam.gestionsoussous;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by bechu on 18/05/16.
 */
public class MaBaseSQLite extends SQLiteOpenHelper{

    private static final String TABLE_PRODUITS = "table_produits";
    private static final String COL_ID_PRODUIT = "ID_produit";
    private static final String COL_NOM_PRODUIT = "Nom_produit";
    private static final String COL_PRIX_PRODUIT = "Prix_produit";
    private static final String COL_DETAIL_PRODUIT = "Detail_produit";
    private static final String COL_TYPE_PRODUIT = "Type_produit";
    private static final String COL_DATE_PRODUIT = "Dte_produit";

    private static final String TABLE_ACTIVITES = "table_activites";
    private static final String COL_ID_ACTIVITE = "ID_activite";
    private static final String COL_NOM_ACTIVITE = "Nom_activite";
    private static final String COL_PRIX_ACTIVITE = "Prix_activite";
    private static final String COL_DUREE_ACTIVITE = "Duree_activite";

    private static final String CREATE_BDD =
            "CREATE TABLE " + TABLE_PRODUITS + " ("
                    + COL_ID_PRODUIT + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COL_PRIX_PRODUIT + " REAL NOT NULL,"
                    + COL_NOM_PRODUIT + " TEXT NOT NULL,"
                    + COL_DETAIL_PRODUIT + " TEXT,"
                    + COL_TYPE_PRODUIT +" BOOLEAN,"
                    + COL_DATE_PRODUIT + " DATE DEFAULT CURRENT_TIMESTAMP); "+
                    "CREATE TABLE " + TABLE_ACTIVITES + " ("
                    + COL_ID_ACTIVITE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COL_NOM_ACTIVITE +  " TEXT NOT NULL,"
                    + COL_PRIX_ACTIVITE + " REAL NOT NULL,"
                    + COL_DUREE_ACTIVITE + "INTEGER NOT NULL );";


    private SQLiteDatabase db;

    public MaBaseSQLite(Context context){
        super(context,"GestionSousSousBD1",null,2);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("Creation", CREATE_BDD);
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    public long insertExpense(Operation ex) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NOM_PRODUIT, ex.getNom());
        contentValues.put(COL_PRIX_PRODUIT, ex.getMontant());
        contentValues.put(COL_DETAIL_PRODUIT, ex.getDetail());
        contentValues.put(COL_TYPE_PRODUIT, ex.getType());
        contentValues.put(COL_DATE_PRODUIT, ex.getDate().getTime() / 1000);
        return db.insert(TABLE_PRODUITS, null, contentValues);
    }

    public void editExpense(Operation ex) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NOM_PRODUIT, ex.getNom());
        contentValues.put(COL_PRIX_PRODUIT, ex.getMontant());
        contentValues.put(COL_DETAIL_PRODUIT, ex.getDetail());
        contentValues.put(COL_TYPE_PRODUIT, ex.getType());
        contentValues.put(COL_DATE_PRODUIT, ex.getDate().getTime() / 1000);
        db.update(TABLE_PRODUITS, contentValues, COL_ID_PRODUIT + "=" + ex.getId(), null);
    }

    public ArrayList<Operation> getAllValues() {
        ArrayList<Operation> alr = new ArrayList<>();
        String[] columns = {COL_ID_PRODUIT, COL_NOM_PRODUIT, COL_PRIX_PRODUIT, COL_DETAIL_PRODUIT, COL_TYPE_PRODUIT, COL_DATE_PRODUIT};
        Cursor res =  db.query(TABLE_PRODUITS, columns, null, null, null,null,null);
        res.moveToFirst();
        try {
            while (!res.isAfterLast()) {
                java.util.Date dt = new java.util.Date();

                alr.add(new Operation(res.getInt(0), res.getString(1), res.getString(3), res.getDouble(2), (res.getInt(4) != 0), new Date(res.getLong(5)*1000)));
                res.moveToNext();
            }
        } finally {
            res.close();
        }
        return alr;
    }

    public boolean deleteExpense(Operation ex) {
        return db.delete(TABLE_PRODUITS, COL_ID_PRODUIT + "=" + ex.getId(), null) > 0;
    }

    private String getDateTime(Operation e) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(e.getDate());
    }
}
