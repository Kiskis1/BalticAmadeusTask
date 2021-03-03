package com.example.batask.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Post(
    @PrimaryKey var id: Long,
    var userId: Long,
    var title: String,
    var body: String,
) : Parcelable