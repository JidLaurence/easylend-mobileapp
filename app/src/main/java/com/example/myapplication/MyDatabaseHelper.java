package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;

    //Current User Table
    private static final String USER_TABLE_NAME = "user_table_name";
    private static final String USER_ID = "user_id";
    private static final String USER_EMAIL = "user_email";
    private static final String USER_PASSWORD = "user_password";
    private static final String USER_STATUS = "user_status";

    //Data
    private static final String DATA_TABLE = "data_table";
    private static final String ID = "id";
    private static final String CUSTOMER_ID = "customer_id";
    private static final String COMPANY_ID = "company_id";
    private static final String BRANCH_ID = "branch_id";
    private static final String STAFF_ID = "staff_id";
    private static final String COLLECTOR_ID = "collector_id";
    private static final String COLLECTOR_DATES_ID = "collector_dates_id";
    private static final String EMAIL = "email";
    private static final String FIRSTNAME = "firstname";
    private static final String LASTNAME = "lastname";
    private static final String MIDDLENAME = "middlename";
    private static final String PHONE_NUMBER = "phone_number";
    private static final String ADDRESS = "address";
    private static final String BARANGAY = "barangay";
    private static final String CITY = "city";
    private static final String PROVINCE = "province";
    private static final String REGION = "region";
    private static final String AMOUNT = "amount";
    private static final String CAPITAL = "capital";
    private static final String MONTHS = "months";
    private static final String TYPE = "type";
    private static final String INTEREST = "interest";
    private static final String TOTAL_PAID = "total_paid";
    private static final String BALANCE = "balance";
    private static final String COLLECT = "collect";
    private static final String CAPITAL_TOTAL = "capital_total";
    private static final String CAPITAL_INTEREST = "capital_interest";
    private static final String MONTH = "month";
    private static final String DAY = "day";
    private static final String YEAR = "year";
    private static final String ISPAID = "isPaid";
    private static final String LAPSES = "lapses";




    public MyDatabaseHelper(@Nullable Context context) {
        super(context, "easylend.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String current_user_table_query =
                "CREATE TABLE " + USER_TABLE_NAME +
                        " (" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        USER_EMAIL + " TEXT, " +
                        USER_PASSWORD + " TEXT, " +
                        USER_STATUS + " TEXT);";

        String data_table =
                "CREATE TABLE " + DATA_TABLE +
                        " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        CUSTOMER_ID + " TEXT, " +
                        COMPANY_ID + " TEXT, " +
                        BRANCH_ID + " TEXT, " +
                        STAFF_ID + " TEXT, " +
                        COLLECTOR_ID + " TEXT, " +
                        COLLECTOR_DATES_ID + " TEXT, " +
                        EMAIL + " TEXT, " +
                        FIRSTNAME + " TEXT, " +
                        LASTNAME + " TEXT, " +
                        MIDDLENAME + " TEXT, " +
                        PHONE_NUMBER + " TEXT, " +
                        ADDRESS + " TEXT, " +
                        BARANGAY + " TEXT, " +
                        CITY + " TEXT, " +
                        PROVINCE + " TEXT, " +
                        REGION + " TEXT, " +
                        AMOUNT + " TEXT, " +
                        CAPITAL + " TEXT, " +
                        MONTHS + " TEXT, " +
                        TYPE + " TEXT, " +
                        INTEREST + " TEXT, " +
                        TOTAL_PAID + " TEXT, " +
                        BALANCE + " TEXT, " +
                        COLLECT + " TEXT, " +
                        CAPITAL_TOTAL + " TEXT, " +
                        CAPITAL_INTEREST + " TEXT, " +
                        MONTH + " TEXT, " +
                        DAY + " TEXT, " +
                        YEAR + " TEXT, " +
                        ISPAID + " TEXT, " +
                        LAPSES + " TEXT);";

        db.execSQL(current_user_table_query);
        db.execSQL(data_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
    }

    public boolean AddUserForSignup(String email, String password, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(USER_EMAIL, email);
        cv.put(USER_PASSWORD, password);
        cv.put(USER_STATUS, status);

        long result = db.insert(USER_TABLE_NAME, null, cv);
        if (result == -1){
            Toast.makeText(context, "Create Account Failed", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            Toast.makeText(context, "Create Account Completed", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public boolean userCheck(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {USER_EMAIL};
        String selection = USER_EMAIL + "=?" + " and " + USER_PASSWORD + "=?";
        String [] selectionargs = {email, password};
        Cursor cursor = db.query(USER_TABLE_NAME, columns, selection, selectionargs, null, null,  null);
        int count =cursor.getCount();
        if (count > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public void signedInUpdate(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(USER_STATUS, "SignedIn");

        long count = db.update(USER_TABLE_NAME,  cv, "user_email=?", new String[]{email});

        if (count == -1){
            Toast.makeText(context, "Signing In Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Signed In", Toast.LENGTH_SHORT).show();
        }
    }


    Cursor readAllDataFromDataTable(){
        String query = "SELECT * FROM " + DATA_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        return  cursor;
    }

    public StringBuilder getDataFirstName(String id){

        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT firstname FROM data_table WHERE id = '" + id + "'", null);

        StringBuilder stringBuilder = new StringBuilder();
        while(cursor.moveToNext()){
            int nameField = cursor.getColumnIndex("firstname");
            stringBuilder.append(cursor.getString(nameField));
        }
        return stringBuilder;
    }

    public StringBuilder getDataLastName(String id){

        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT lastname FROM data_table WHERE id = '" + id + "'", null);

        StringBuilder stringBuilder = new StringBuilder();
        while(cursor.moveToNext()){
            int nameField = cursor.getColumnIndex("lastname");
            stringBuilder.append(cursor.getString(nameField));
        }
        return stringBuilder;
    }

    public StringBuilder getDataMiddleName(String id){

        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT middlename FROM data_table WHERE id = '" + id + "'", null);

        StringBuilder stringBuilder = new StringBuilder();
        while(cursor.moveToNext()){
            int nameField = cursor.getColumnIndex("middlename");
            stringBuilder.append(cursor.getString(nameField));
        }
        return stringBuilder;
    }

    public StringBuilder getDataCapital(String id){

        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT capital FROM data_table WHERE id = '" + id + "'", null);

        StringBuilder stringBuilder = new StringBuilder();
        while(cursor.moveToNext()){
            int nameField = cursor.getColumnIndex("capital");
            stringBuilder.append(cursor.getString(nameField));
        }
        return stringBuilder;
    }

    public StringBuilder getDataCollected(String id){

        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT collect FROM data_table WHERE id = '" + id + "'", null);

        StringBuilder stringBuilder = new StringBuilder();
        while(cursor.moveToNext()){
            int nameField = cursor.getColumnIndex("collect");
            stringBuilder.append(cursor.getString(nameField));
        }
        return stringBuilder;
    }

    public StringBuilder getDataBalance(String id){

        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT balance FROM data_table WHERE id = '" + id + "'", null);

        StringBuilder stringBuilder = new StringBuilder();
        while(cursor.moveToNext()){
            int nameField = cursor.getColumnIndex("balance");
            stringBuilder.append(cursor.getString(nameField));
        }
        return stringBuilder;
    }

    public StringBuilder getDataDay(String id){

        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT day FROM data_table WHERE id = '" + id + "'", null);

        StringBuilder stringBuilder = new StringBuilder();
        while(cursor.moveToNext()){
            int nameField = cursor.getColumnIndex("day");
            stringBuilder.append(cursor.getString(nameField));
        }
        return stringBuilder;
    }

    public StringBuilder getDataMonth(String id){

        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT month FROM data_table WHERE id = '" + id + "'", null);

        StringBuilder stringBuilder = new StringBuilder();
        while(cursor.moveToNext()){
            int nameField = cursor.getColumnIndex("month");
            stringBuilder.append(cursor.getString(nameField));
        }
        return stringBuilder;
    }

    public StringBuilder getDataYear(String id){

        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT year FROM data_table WHERE id = '" + id + "'", null);

        StringBuilder stringBuilder = new StringBuilder();
        while(cursor.moveToNext()){
            int nameField = cursor.getColumnIndex("year");
            stringBuilder.append(cursor.getString(nameField));
        }
        return stringBuilder;
    }

    public StringBuilder getDataAmount(String id){

        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT amount FROM data_table WHERE id = '" + id + "'", null);

        StringBuilder stringBuilder = new StringBuilder();
        while(cursor.moveToNext()){
            int nameField = cursor.getColumnIndex("amount");
            stringBuilder.append(cursor.getString(nameField));
        }
        return stringBuilder;
    }


    public void upDateAmount(String id, String amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(AMOUNT, amount);

        long count = db.update(DATA_TABLE,  cv, "id=?", new String[]{id});

        if (count == -1){
            Toast.makeText(context, "Failed to add amount", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully amount added", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM data_table WHERE id = '" + id + "'", null);
        if (cursor.getCount() > 0){
            long result = db.delete(DATA_TABLE, "id=?", new String[]{id});
            if (result == -1){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }

    public boolean addData(String company_id, String branch_id, String staff_id, String collector_id, String customer_db_id, String firstname,
                           String lastname, String middlename, String phone_number, String purok, String barangay, String city,
                           String province, String region, Integer balance, Integer collect, Integer capital, Integer month, Integer day,
                           Integer year, Integer amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COMPANY_ID, company_id);
        cv.put(BRANCH_ID, branch_id);
        cv.put(STAFF_ID, staff_id);
        cv.put(COLLECTOR_ID, collector_id);
        cv.put(CUSTOMER_ID, customer_db_id);
        cv.put(FIRSTNAME, firstname);
        cv.put(LASTNAME, lastname);
        cv.put(MIDDLENAME, middlename);
        cv.put(PHONE_NUMBER, phone_number);
        cv.put(ADDRESS, purok);
        cv.put(BARANGAY, barangay);
        cv.put(CITY, city);
        cv.put(PROVINCE, province);
        cv.put(REGION, region);
        cv.put(BALANCE, balance);
        cv.put(COLLECT, collect);
        cv.put(CAPITAL, capital);
        cv.put(MONTH, month);
        cv.put(DAY, day);
        cv.put(YEAR, year);
        cv.put(AMOUNT, amount);

        long result = db.insert(DATA_TABLE, null, cv);
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            //Toast.makeText(context, "Create Account Completed", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public StringBuilder getDataPhone(String id){

        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT phone_number FROM data_table WHERE id = '" + id + "'", null);

        StringBuilder stringBuilder = new StringBuilder();
        while(cursor.moveToNext()){
            int nameField = cursor.getColumnIndex("phone_number");
            stringBuilder.append(cursor.getString(nameField));
        }
        return stringBuilder;
    }



}
