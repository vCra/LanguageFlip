package io.vcra.apps.languageflip.data.settings

import android.arch.persistence.room.*

@Dao
interface SettingDAO {
    @Insert
    fun insert(setting: Setting)

    @Update
    fun update(vararg settings: Setting)

    @Delete
    fun delete(vararg settings: Setting)

    @Query("SELECT COUNT(*) FROM settings")
    fun getSettingsCount(): Int

    @Query("DELETE FROM settings")
    fun deleteAll()
}
