package plat.pinto_ramirez.lab7

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    lateinit var db: AppDatabase
        private set

    fun init(context: Context) {
        if (!::db.isInitialized) {
            db = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "rick_and_morty_db"
            ).build()
        }
    }
}