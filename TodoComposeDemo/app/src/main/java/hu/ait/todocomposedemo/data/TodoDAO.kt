package hu.ait.todocomposedemo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDAO {
    @Query("SELECT * FROM todotable")
    fun getAllTodos() : Flow<List<TodoItem>>

    @Query("SELECT * from todotable WHERE id = :id")
    fun getTodo(id: Int): Flow<TodoItem>

    @Query("SELECT COUNT(*) from todotable")
    suspend fun getTodosNum(): Int

    @Query("""SELECT COUNT(*) from todotable WHERE priority="HIGH"""")
    suspend fun getImportantTodosNum(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todo: TodoItem)

    @Update
    suspend fun update(todo: TodoItem)

    @Delete
    suspend fun delete(todo: TodoItem)

    @Query("DELETE from todotable")
    suspend fun deleteAllTodos()
}