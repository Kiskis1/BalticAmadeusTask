package com.example.batask.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val id: Long,
    val name: String,
    val username: String,
    val email: String,
    @Embedded val address: Address?,
    val phone: String,
    val website: String,
    @Embedded val company: Company?,
)

data class Address(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    @Embedded val geo: Geo,
)

data class Geo(
    val lat: String,
    val lng: String,
)

data class Company(
    @ColumnInfo(name = "company_name")val name: String,
    val catchPhrase: String,
    val bs: String,
)
