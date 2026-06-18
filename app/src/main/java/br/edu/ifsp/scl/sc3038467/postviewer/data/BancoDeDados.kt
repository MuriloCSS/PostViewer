package br.edu.ifsp.scl.sc3038467.postviewer.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase


@Entity(tableName = "tabela_comentarios_locais")
data class ComentarioLocal(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val postId: Int,
    val texto: String
)

@Dao
interface ComentarioDao {
    @Insert
    suspend fun salvarComentario(comentario: ComentarioLocal)

    @Query("SELECT * FROM tabela_comentarios_locais WHERE postId = :postId")
    suspend fun buscarComentariosDoPost(postId: Int): List<ComentarioLocal>
}

@Database(entities = [ComentarioLocal::class], version = 1, exportSchema = false)
abstract class AppBancoDeDados : RoomDatabase() {
    abstract fun comentarioDao(): ComentarioDao

    companion object {
        @Volatile
        private var INSTANCIA: AppBancoDeDados? = null

        fun obterInstancia(context: Context): AppBancoDeDados {
            return INSTANCIA ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    AppBancoDeDados::class.java,
                    "postviewer_banco_dados"
                ).build()
                INSTANCIA = instancia
                instancia
            }
        }
    }
}