package com.example.persiapanujikom.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employees")
data class Employee(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val nip: String,
    val gender: String,
    val dateOfBirth: String,
    val placeOfBirth: String,
    val address: String,
    val photo: String,
)
