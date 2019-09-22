package com.nurram.project.bola.Db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class SqlHelper(context: Context) :
    ManagedSQLiteOpenHelper(context, "FavouriteTeam.db", null) {

    companion object {
        private var instance: SqlHelper? = null

        @Synchronized
        fun getInstance(context: Context): SqlHelper {
            if (instance == null) {
                instance = SqlHelper(context)
            }

            return instance as SqlHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
            DbContract.TABLE_FAVOURITE,
            true,
            DbContract.HOME_ID to TEXT + PRIMARY_KEY,
            DbContract.HOME_NAME to TEXT,
            DbContract.HOME_SCORE to TEXT,
            DbContract.HOME_SCORER to TEXT,
            DbContract.HOME_CARD to TEXT,
            DbContract.HOME_GOALKEEPER to TEXT,
            DbContract.HOME_FWD to TEXT,
            DbContract.HOME_DEFENSE to TEXT,
            DbContract.HOME_MIDDLE to TEXT,
            DbContract.HOME_SUBSTITUTE to TEXT,
            DbContract.AWAY_ID to TEXT,
            DbContract.AWAY_NAME to TEXT,
            DbContract.AWAY_SCORE to TEXT,
            DbContract.AWAY_SCORER to TEXT,
            DbContract.AWAY_CARD to TEXT,
            DbContract.AWAY_GOALKEEPER to TEXT,
            DbContract.AWAY_FWD to TEXT,
            DbContract.AWAY_DEFENSE to TEXT,
            DbContract.AWAY_MIDDLE to TEXT,
            DbContract.AWAY_SUBSTITUTE to TEXT,
            DbContract.MATCH_DATE to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVer: Int, newVer: Int) {
        db.dropTable(DbContract.TABLE_FAVOURITE, true)
    }
}

val Context.matchDatabase: SqlHelper
    get() = SqlHelper.getInstance(applicationContext)