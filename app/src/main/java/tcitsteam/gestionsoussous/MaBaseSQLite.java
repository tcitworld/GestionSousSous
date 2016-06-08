package tcitsteam.gestionsoussous;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by bechu on 18/05/16.
 */
public class MaBaseSQLite extends SQLiteOpenHelper{

    private static final String TABLE_PRODUITS = "table_produits";
    private static final String COL_ID_PRODUIT = "ID_produit";
    private static final String COL_NOM_PRODUIT = "Nom_produit";
    private static final String COL_PRIX_PRODUIT = "Prix_produit";
    private static final String COL_DETAIL_PRODUIT = "Detail_produit";
    private static final String COL_TYPE_PRODUIT = "Detail_produit";

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
                    + COL_TYPE_PRODUIT +" BOOLEAN); "+
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
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    public void insertExpense(Expense ex) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NOM_PRODUIT, ex.getNom());
        contentValues.put(COL_PRIX_PRODUIT, ex.getMontant());
        contentValues.put(COL_DETAIL_PRODUIT, ex.getDetail());
        contentValues.put(COL_TYPE_PRODUIT, ex.getType());
        db.insert(TABLE_PRODUITS, null, contentValues);
    }

    public ArrayList<Expense> getAllValues() {
        ArrayList<Expense> alr = new ArrayList<>();
        String[] columns = {COL_NOM_PRODUIT, COL_PRIX_PRODUIT, COL_DETAIL_PRODUIT, COL_TYPE_PRODUIT};
        Cursor res =  db.query(TABLE_PRODUITS, columns, null, null, null,null,null);
        res.moveToFirst();

        while(!res.isAfterLast()){
            alr.add(new Expense(res.getString(0), res.getString(2), res.getDouble(1), (res.getInt(3) != 0)));
            res.moveToNext();
        }
        res.close();
        return alr;
    }
}
