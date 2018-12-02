package io.vcra.apps.languageflip.data.settings

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "settings")
class Setting(@field:PrimaryKey
              @field:ColumnInfo(name = "settingKey")
              var settingKey: String, @field:ColumnInfo(name = "settingValue")
              var settingValue: String)
