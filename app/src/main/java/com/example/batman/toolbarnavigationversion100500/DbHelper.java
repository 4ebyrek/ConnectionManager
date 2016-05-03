package com.example.batman.toolbarnavigationversion100500;


import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {


    private static final String DataBase_Name = "myDataBase.db";
    private static final int DataBase_Version = 1;




    private static final String DataBase_Table_BETS = "Bets";

    public static final String Bets_match = "Match";
    public static final String Bets_P1 = "Win1";
    public static final String Bets_X = "X";
    public static final String Bets_P2 = "Win2";

    private static final String DataBase_CreateTable_Bets= "create table "
            + DataBase_Table_BETS + "( Bets_ID integer primary key autoincrement, " + Bets_match
            + " text, " + Bets_P1 + " text, " + Bets_X
            + " text, " + Bets_P2 + " text);";

    private static final String DataBase_Table_User = "User";

    public static final String User_Login = "Login";
    public static final String User_Password = "Password";
    public static final String User_Email = "Email";

    private static final String DataBase_CreateTable_User= "create table "
            + DataBase_Table_User + "( _id integer primary key autoincrement, " + User_Login
            + " text, " + User_Password + " text, " + User_Email
            + " text);";

    private static final String DataBase_Table_History = "History";

    public static final String History_Login = "Login";
    public static final String History_Match = "Match";
    public static final String History_Bet = "Bet";

    private static final String DataBase_CreateTable_History= "create table "
            + DataBase_Table_History + "( _id integer primary key autoincrement, " + History_Login
            + " text, " + History_Match + " text, " + History_Bet
            + " text);";



    public DbHelper(Context context){
        super(context, DataBase_Name, null, DataBase_Version);
    }
    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBase_CreateTable_Bets);
        db.execSQL(DataBase_CreateTable_User);
        db.execSQL(DataBase_CreateTable_History);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("SQLite", "Обновляемся с версии " + oldVersion + " на версию " + newVersion);
        db.execSQL("DROP TABLE IF IT EXISTS " + DataBase_Table_BETS);
        onCreate(db);
    }


}
