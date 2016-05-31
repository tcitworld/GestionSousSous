package tcitsteam.gestionsoussous;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by bechu on 18/05/16.
 */
public class MaBaseSQLite extends SQLiteOpenHelper{

    private static final String TABLE_PRODUITS = "table_produits";
    private static final String COL_ID_PRODUIT = "ID_produit";
    private static final String COL_NOM_PRODUIT = "Nom_produit";
    private static final String COL_PRIX_PRODUIT = "Prix_produit";

    private static final String TABLE_ACTIVITES = "table_activites";
    private static final String COL_ID_ACTIVITE = "ID_activite";
    private static final String COL_NOM_ACTIVITE = "Nom_activite";
    private static final String COL_PRIX_ACTIVITE = "Prix_activite";
    private static final String COL_DUREE_ACTIVITE = "Duree_activite";

    private static final String CREATE_BDD =
            "CREATE TABLE " + TABLE_PRODUITS + " ("
                    + COL_ID_PRODUIT + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COL_PRIX_PRODUIT + " REAL NOT NULL,"
                    + COL_NOM_PRODUIT + " TEXT NOT NULL); "+
                    "CREATE TABLE " + TABLE_ACTIVITES + " ("
                    + COL_ID_ACTIVITE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COL_NOM_ACTIVITE +  " TEXT NOT NULL,"
                    + COL_PRIX_ACTIVITE + " REAL NOT NULL,"
                    + COL_DUREE_ACTIVITE + "INTEGER NOT NULL );";

    public MaBaseSQLite(Context context, String name, CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
