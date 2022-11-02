package com.example.foodapp2.admin


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.foodapp2.R
import com.example.foodapp2.database.Database
import com.example.foodapp2.databinding.ActivityAdminAddBinding
import java.io.ByteArrayOutputStream

class AdminAddActivity : AppCompatActivity() {
    private val database by lazy { Database.getInstance(this) }
    private val tag = "PermissionDemo"
    private val cameraRequestCode = 101
    private val folderRequestCode = 202
    private lateinit var binding: ActivityAdminAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_admin_add)
        initListener()
    }

    private val takePhoto: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val bitmap: Bitmap = result.data?.extras?.get("data") as Bitmap
                binding.addImg.setImageBitmap(bitmap)
            }
        }
    private val folderPhoto =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val uri: Uri? = result.data?.data
                binding.addImg.setImageURI(uri)
            }
        }

    private fun initListener() {

        binding.addBtnCancle.setOnClickListener {
            Toast.makeText(this@AdminAddActivity, "Đã huỷ!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@AdminAddActivity, AdminActivity::class.java)
            startActivity(intent)
        }

        binding.addImgCamera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            setupPermissions()
            takePhoto.launch(intent)
        }

        binding.addImgFoder.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            folderPhoto.launch(intent)
        }
        binding.addBtnAdd.setOnClickListener {
            val name = binding.addEdtName.text.toString()
            val describe = binding.addEdtDescribe.text.toString()
            val price = binding.addEdtPrice.text.toString()
            val priceD: Double = price.toDouble()
            val bitmapDrawable: BitmapDrawable = binding.addImg.drawable as BitmapDrawable
            val bitmap: Bitmap = bitmapDrawable.bitmap
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val picture: ByteArray = byteArrayOutputStream.toByteArray()
            database.insert(name, describe, priceD, picture)
            Toast.makeText(this, "Đã thêm thành công!", Toast.LENGTH_SHORT)
                .show()
            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(tag, "Permission to record denied")
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            cameraRequestCode
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            cameraRequestCode -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    Log.i(tag, "Permission has been denied by user")
                } else {
                    Log.i(tag, "Permission has been granted by user")
                }
            }
        }

    }

}