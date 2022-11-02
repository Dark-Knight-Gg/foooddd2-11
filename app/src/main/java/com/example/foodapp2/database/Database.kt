package com.example.foodapp2.database

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteStatement
import com.example.foodapp2.model.Food
import com.example.foodapp2.model.FoodOder
import com.example.foodapp2.model.History
import com.example.foodapp2.model.User
import java.text.SimpleDateFormat
import java.util.*

class Database(context: Context) : SQLiteOpenHelper(context, "Data.sql", null, 1) {
    companion object {
        private const val SQL_DELETE_USERS = "DROP TABLE IF EXISTS Users"
        private const val SQL_DELETE_FOOD = "DROP TABLE IF EXISTS Food"
        private const val SQL_DELETE_HISTORY = "DROP TABLE IF EXISTS History"

        private var instance: Database? = null
        fun getInstance(context: Context): Database {
            if (instance == null) {
                instance = Database(context)
            }
            return instance!!
        }
    }


    override fun onCreate(db: SQLiteDatabase?) {
        val sqlCreateUsers =
            "CREATE TABLE IF NOT EXISTS Users(Id INTEGER PRIMARY KEY AUTOINCREMENT, Username NVARCHAR(200), Password NVARCHAR(200))"
        val sqlCreateFood =
            "CREATE TABLE IF NOT EXISTS Food(Id INTEGER PRIMARY KEY AUTOINCREMENT, Name NVARCHAR(200), Describe NVARCHAR(400), Price DOUBLE, Picture BLOB)"
        val sqlCreateHistory =
            "CREATE TABLE IF NOT EXISTS History(Id INTEGER PRIMARY KEY AUTOINCREMENT, Day NVARCHAR(50), Name NVARCHAR(200), Price DOUBLE, Count INTEGER, Money DOUBLE)"
        db?.execSQL(sqlCreateUsers)
        db?.execSQL(sqlCreateFood)
        db?.execSQL(sqlCreateHistory)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_USERS)
        db?.execSQL(SQL_DELETE_FOOD)
        db?.execSQL(SQL_DELETE_HISTORY)
        onCreate(db)
    }

    fun queryData(sql: String) {
        val data: SQLiteDatabase = writableDatabase
        data.execSQL(sql)
    }

    fun insert(name: String, describe: String, price: Double, picture: ByteArray) {
        val data = writableDatabase
        val sql = "INSERT INTO Food VALUES(null,?,?,?,?)"
        val statement: SQLiteStatement = data.compileStatement(sql)
        statement.clearBindings()
        statement.bindString(1, name)
        statement.bindString(2, describe)
        statement.bindDouble(3, price)
        statement.bindBlob(4, picture)
        statement.executeInsert()
    }

    fun isUsernameExists(username: String): Boolean {
        val db: SQLiteDatabase = readableDatabase
        val cursor = db.query(
            "Users",
            arrayOf("Username", "Password"),
            "Username" + "=?",
            arrayOf(username),
            null,
            null,
            null
        )
        if (cursor != null && cursor.moveToFirst()) {
            cursor.close()
            return true
        }
        return false
    }

    fun isLogin(user: User): Boolean {
        val db: SQLiteDatabase = readableDatabase
        val cursor = db.query(
            "Users",
            arrayOf("Username", "Password"),
            "Username" + "=?",
            arrayOf(user.username),
            null,
            null,
            null
        )
        if (cursor != null && cursor.moveToFirst()) {
            if (user.password ==cursor.getString(1)) {
                return true
            }
            cursor.close()
        }
        return false
    }

    private fun getData(sql: String): Cursor? {
        val data: SQLiteDatabase = readableDatabase
        return data.rawQuery(sql, null)
    }

    fun getFoodList(): List<Food> {
        val result = mutableListOf<Food>()
        val cursor: Cursor? = getData("SELECT * FROM Food")
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val describe = cursor.getString(2)
                val price = cursor.getDouble(3)
                val picture = cursor.getBlob(4)
                result.add(Food(id, name, describe, price, picture))
            }
        }
        return result
    }

    fun getHistory(): List<History> {
        val result = mutableListOf<History>()
        val cursor: Cursor? = getData("SELECT * FROM History")
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val id = cursor.getInt(0)
                val date = cursor.getString(1)
                val name = cursor.getString(2)
                val price = cursor.getDouble(3)
                val count = cursor.getInt(4)
                val money = cursor.getDouble(5)
                result.add(History(id, date, name, price, count, money))
            }
        }
        return result
    }

    fun getFood(): List<Food> {
        val result = mutableListOf<Food>()
        val cursor: Cursor? = getData("SELECT * FROM Food")
        while (cursor != null && cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val name = cursor.getString(1)
            val describe = cursor.getString(2)
            val price = cursor.getDouble(3)
            val picture = cursor.getBlob(4)
            result.add(Food(id, name, describe, price, picture))
        }
        return result
    }

    fun insertIntoUsers(username: String, password: String) {
        queryData("INSERT INTO Users VALUES(null,'$username','$password')")
    }

    @SuppressLint("SimpleDateFormat")
    fun getYourHistory(listFoodOder: ArrayList<FoodOder>) {
        val fo = SimpleDateFormat("yyyy-MM-dd")
        val dateTime = fo.format(Date()).toString()
        val n = listFoodOder.size
        for (i in 0 until n) {
            queryData("INSERT INTO History VALUES(null,'$dateTime','${listFoodOder[i].name}','${listFoodOder[i].price}','${listFoodOder[i].count}','${listFoodOder[i].price * listFoodOder[i].count}')")
        }
    }

    fun deleteById(position: Int) {
        queryData("DELETE FROM Food WHERE Id= '$position'")
    }
}