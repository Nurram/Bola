package com.nurram.project.bola.Db.TeamDb

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class TeamSqlHelper(context: Context) :
    ManagedSQLiteOpenHelper(context, "Favourite.db", null) {

    companion object {
        private var instance: TeamSqlHelper? = null

        @Synchronized
        fun getInstance(context: Context): TeamSqlHelper {
            if (instance == null) {
                instance =
                        TeamSqlHelper(context)
            }

            return instance as TeamSqlHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
            TeamDbContract.TABLE_FAVOURITE,
            true,
            TeamDbContract.TEAM_ID to TEXT + PRIMARY_KEY,
            TeamDbContract.TEAM_NAME to TEXT,
            TeamDbContract.TEAM_FORMED_YEAR to TEXT,
            TeamDbContract.TEAM_WEBSITE to TEXT,
            TeamDbContract.TEAM_BADGE to TEXT,
            TeamDbContract.TEAM_YOUTUBE to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVer: Int, newVer: Int) {
        db.dropTable(TeamDbContract.TABLE_FAVOURITE, true)
    }
}

val Context.database: TeamSqlHelper
    get() = TeamSqlHelper.getInstance(applicationContext)