package mobapde.mobapdemp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by amiel on 11/14/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String SCHEMA = "coach";
    public static final int VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, SCHEMA, null, VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + Account.TABLE_NAME + " ("
                + Account.COLUMN_EMAIL + " TEXT PRIMARY KEY, "
                + Account.COLUMN_PASSWORD + " TEXT, "
                + Account.COLUMN_NAME + " TEXT "
                +");";
        db.execSQL(sql);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + Account.TABLE_NAME + ";";
        db.execSQL(sql);

        onCreate(db);
    }


    ///-- CRUD METHODS --///

    ///-- ADD METHODS --///
    public long addAccount(Account a){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(Account.COLUMN_EMAIL, a.getEmail());
        cv.put(Account.COLUMN_PASSWORD, a.getPassword());
        cv.put(Account.COLUMN_NAME, a.getName());

        long id = db.insert(Account.TABLE_NAME, null, cv);
        db.close();
        return id;
    }

    public long addAccount(Account a, SQLiteDatabase db){
        ContentValues cv = new ContentValues();

        cv.put(Account.COLUMN_EMAIL, a.getEmail());
        cv.put(Account.COLUMN_PASSWORD, a.getPassword());
        cv.put(Account.COLUMN_NAME, a.getName());

        long id = db.insert(Account.TABLE_NAME, null, cv);
        db.close();
        return id;
    }
    ///-- END OF ADD METHODS --///

    ///-- READ METHODS --///

    public Account login(Account a){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query(Account.TABLE_NAME, null, Account.COLUMN_EMAIL + "=? AND " + Account.COLUMN_PASSWORD + "=?",
                new String[]{a.getEmail()+"",a.getPassword()+""},null,null,null);

        Account newA = null;

        if(c.moveToFirst()){
            newA = new Account();
            a.setEmail(c.getString(c.getColumnIndex(Account.COLUMN_EMAIL)));
            a.setPassword(c.getString(c.getColumnIndex(Account.COLUMN_PASSWORD)));
            a.setName(c.getString(c.getColumnIndex(Account.COLUMN_NAME)));
        }
        c.close();
        db.close();

        return newA;
    }

    ///-- END OF READ METHODS --///

    ///-- UPDATE METHODS --///
    public boolean editAccount(Account a){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Account.COLUMN_EMAIL, a.getEmail());
        cv.put(Account.COLUMN_PASSWORD, a.getPassword());
        cv.put(Account.COLUMN_NAME, a.getName());

        int rowsAffected = db.update(Account.TABLE_NAME, cv, Account.COLUMN_EMAIL + "=?", new String[]{a.getEmail()+""});
        db.close();
        return rowsAffected > 0;
    }


    ///-- END OF UPDATE METHODS --///

    ///-- DELETE METHODS --///
    public boolean deleteAccount(Account a){
        return deleteAccount(a.getEmail());
    }

    public boolean deleteAccount(String email){
        SQLiteDatabase db = getWritableDatabase();

        int rowsAffected = db.delete(Account.TABLE_NAME, Account.COLUMN_EMAIL + "=?", new String[]{email + ""});
        db.close();
        return rowsAffected > 0;
    }
    ///-- END OF DELETE METHODS --///
}
