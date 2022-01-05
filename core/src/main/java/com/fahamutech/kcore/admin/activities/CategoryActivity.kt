package com.fahamutech.kcore.admin.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.canhub.cropper.*
import com.fahamutech.kcore.R
import com.fahamutech.kcore.admin.database.DataBaseCallback
import com.fahamutech.kcore.admin.database.connector.CategoryDataSource
import com.fahamutech.kcore.admin.database.noSql.CategoryNoSqlDatabase
import com.fahamutech.kcore.admin.model.Category
import com.google.android.gms.tasks.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.io.File
import java.util.*

class CategoryActivity : AppCompatActivity() {
    private var toolbar: Toolbar? = null
    private var fab: FloatingActionButton? = null
    private var title: TextInputEditText? = null
    private var description: TextInputEditText? = null
    private var imageView: ImageView? = null
    private var uploadButton: Button? = null
    private var categoryDataSource: CategoryDataSource? = null
    private var imageUrl: CropImageView.CropResult? = null
    private var cropImage: ActivityResultLauncher<CropImageContractOptions>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        bindView()
        setSupportActionBar(toolbar)
        val supportActionBar = supportActionBar
        if (supportActionBar != null) {
            supportActionBar.title = "New Category"
            supportActionBar.setDisplayHomeAsUpEnabled(true)
        }
        categoryDataSource = CategoryNoSqlDatabase(this)
        fab!!.setOnClickListener { createCategory() }
        cropImage = registerForActivityResult(CropImageContract()) { result ->
            if (result.isSuccessful) {
                imageUrl = result
//                val image = result.getBitmap(this)
                val bitmap = result.getBitmap(this)
                Glide.with(this).load(bitmap).into(imageView!!)
//                imageUrl = image
//                // use the returned uri
//                val uriContent = result.uriContent
//                val uriFilePath = result.getUriFilePath(this) // optional usage
            } else {
                // an error occurred
                val exception = result.error
                Toast.makeText(this, exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {

//        }
//        super.onActivityResult(requestCode, resultCode, data)
//    }

    private fun uploadCategory(
        fileLocation: String,
        fileName: String,
        name: String,
        description: String
    ) {

        //progress dialog
//        val dialog = AlertDialog.Builder(this)
//            .setTitle("Upload Category")
//            .setView("Please wait...")
//            .setCancelable(false)
//            .progress(true, 1)
//            .positiveText("Close")
//            .onPositive { dialog1: MaterialDialog, which: DialogAction? -> dialog1.dismiss() }
//            .build()
//        dialog.show()
        Toast.makeText(this,"Upload category...", Toast.LENGTH_LONG).show()
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]

        //save to firebase
        val storage = FirebaseStorage.getInstance()
        //StorageReference reference = storage.getReference();
        val child = storage.reference
            .child("category/$year/$month/$fileName")
        child.putFile(Uri.fromFile(File(fileLocation)))
            .addOnProgressListener { taskSnapshot: UploadTask.TaskSnapshot ->
                Log.e("BYTE TRANS : ", taskSnapshot.bytesTransferred.toString())
            }
            .continueWithTask { task: Task<UploadTask.TaskSnapshot?> ->
                if (!task.isComplete) {
                    try {
                        Log.e("UPLOAD ERROR ", task.exception!!.message!!)
                    } catch (ignore: Throwable) {
                    }
                }
                child.downloadUrl
            }
            .addOnCompleteListener { task: Task<Uri> ->
                if (task.isSuccessful) {
                    val downloadUri = Objects.requireNonNull(task.result).toString()

                    //fill the url to the firestore
                    categoryDataSource!!.createCategory(
                        Category(name, description, downloadUri),
                        object : DataBaseCallback {
                            override fun then(`object`: Any?) {
                                //done
                                Log.e("upload category", "Done upload category")
                                Toast.makeText(
                                    this@CategoryActivity,
                                    "Done create category",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        },
                        object : DataBaseCallback {
                            override fun then(`object`: Any?) {
                                //fails
                                Log.e("upload category", "Fail to upload category")
                                Toast.makeText(
                                    this@CategoryActivity,
                                    "Fail to create category",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        })
//                    dialog.dismiss()
                } else {
                    // Handle failures
                    //fill the url to the firestore
                    categoryDataSource!!.createCategory(
                        Category(name, description, ""),
                        object : DataBaseCallback {
                            override fun then(`object`: Any?) {
                                //done
                                Log.e("upload category", "Done upload category")
                                Toast.makeText(
                                    this@CategoryActivity,
                                    "Done create category",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        },
                        object : DataBaseCallback {
                            override fun then(`object`: Any?) {
                                //fails
                                Log.e("upload category", "Fail to upload category")
                                Toast.makeText(
                                    this@CategoryActivity,
                                    "Fail to create category",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        })
                    Objects.requireNonNull(task.exception)?.message?.let {
                        Log.e(
                            "Image failed upload : ",
                            it
                        )
                    }
//                    dialog.dismiss()
                }
                startActivity(Intent(this, JifunzeAdminActivity::class.java))
                finish()
            }
    }

    private fun bindView() {
        toolbar = findViewById(R.id.toolbar)
        title = findViewById(R.id.cat_new_name)
        description = findViewById(R.id.cat_new_description)
        fab = findViewById(R.id.fab)
        imageView = findViewById(R.id.cat_new_image)
        uploadButton = findViewById(R.id.cat_new_upload)
        uploadButton?.setOnClickListener {
            cropImage?.launch(
                options {
                    setGuidelines(CropImageView.Guidelines.ON)
                }
            )
        }
    }

    private fun createCategory() {
        if (title!!.text.toString().isEmpty()) {
            title!!.requestFocus()
            snackMessage("Fill the title of the category")
        } else if (description!!.text.toString().isEmpty()) {
            description!!.requestFocus()
            snackMessage("Fill the description of the category")
        } else if (imageUrl == null) {
            uploadButton!!.requestFocus()
            snackMessage("Upload image for the category")
        } else {
            imageUrl!!.getUriFilePath(this)?.let {
                uploadCategory(
                    it, it, title!!.text.toString(),
                    description!!.text.toString()
                )
            }
            snackMessage("Creating category...")
        }
    }

    private fun snackMessage(message: String) {
        Snackbar.make(fab!!, message, Snackbar.LENGTH_SHORT).show()
    }
}