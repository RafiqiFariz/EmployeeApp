package com.example.employeeapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class yang merepresentasikan entitas karyawan dalam database.
 *
 * @param id ID unik untuk setiap karyawan. Nilai ini di-generate secara otomatis.
 * @param name Nama karyawan.
 * @param nip Nomor Induk Pegawai karyawan.
 * @param gender Jenis kelamin karyawan.
 * @param dateOfBirth Tanggal lahir karyawan.
 * @param placeOfBirth Tempat lahir karyawan.
 * @param address Alamat karyawan.
 * @param photo URL atau path ke foto karyawan.
 */
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
