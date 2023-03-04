package com.mbs.workoutplanner.dataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = DatabaseConstants.UserInfoDatabaseName)
data class UserInfoEntity (
    @PrimaryKey
    val id: Int
        )
@Entity(tableName = DatabaseConstants.UserPhotoDataBaseName)
data class UserPhotoEntity (
    @PrimaryKey
    val uuid: Int = 1,

    val photo: ByteArray
        ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserPhotoEntity

        if (!photo.contentEquals(other.photo)) return false

        return true
    }

    override fun hashCode(): Int {
        return photo.contentHashCode()
    }
}