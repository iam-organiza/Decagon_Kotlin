package com.example.implementationtwo

//import com.afollestad.materialdialogs.MaterialDialog
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.implementationtwo.networking.ApiConfig
import com.example.implementationtwo.networking.AppConfig
import com.example.implementationtwo.networking.ServerRes
import com.example.implementationtwo.networking.ServerResponse
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.Logger

class ImageActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var imageView: ImageView
    private lateinit var pickImage: Button
    private lateinit var upload: Button
    private val mMediaUri: Uri? = null

    private var fileUri: Uri? = null

    private var mediaPath: String? = null

    private val btnCapturePicture: Button? = null

    private var mImageFileLocation = ""
    private lateinit var pDialog: ProgressDialog
    private var postPath: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        imageView = findViewById<ImageView>(R.id.preview)
        pickImage = findViewById<Button>(R.id.pickImage)
        upload = findViewById<Button>(R.id.upload)

        pickImage.setOnClickListener(this)
        upload.setOnClickListener(this)
        initDialog()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.pickImage -> {
//                val listView = findViewById<ListView>(R.id.lvPickImageLayout)
                val arrayAdapter: ArrayAdapter<*>
                val list = arrayOf(
                    "Pick from gallery",
                    "Click from camera",
                    "Remove image",
                )
                arrayAdapter = ArrayAdapter(this, R.layout.pick_image_layout, list)


//                listView.adapter = arrayAdapter

                val builder = MaterialAlertDialogBuilder(this)
                builder.setTitle(R.string.uploadImages)
                builder.setAdapter(arrayAdapter) { dialog, which ->
                    when (which) {
                        0 -> {
                            val galleryIntent = Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                                startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO)
                        }
                        1 -> captureImage()
                        2 -> imageView.setImageResource(R.drawable.ic_launcher_background)
                    }
                }.show()
            }
            R.id.upload -> uploadFile()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO || requestCode == REQUEST_PICK_PHOTO) {
                if (data != null) {
                    // Get the Image from data
                    val selectedImage = data.data
                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

                    val cursor = contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
                    assert(cursor != null)
                    cursor!!.moveToFirst()

                    val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                    mediaPath = cursor.getString(columnIndex)
                    // Set the Image in ImageView for Previewing the Media
                    imageView.setImageBitmap(BitmapFactory.decodeFile(mediaPath))
                    cursor.close()


                    postPath = mediaPath
                }


            } else if (requestCode == CAMERA_PIC_REQUEST) {
                if (Build.VERSION.SDK_INT > 21) {

                    Glide.with(this).load(mImageFileLocation).into(imageView)
                    postPath = mImageFileLocation

                } else {
                    Glide.with(this).load(fileUri).into(imageView)
                    postPath = fileUri!!.path

                }

            }

        } else if (resultCode != Activity.RESULT_CANCELED) {
            Toast.makeText(this, "Sorry, there was an error!", Toast.LENGTH_LONG).show()
        }
    }

    protected fun initDialog() {

        pDialog = ProgressDialog(this)
        pDialog.setMessage(getString(R.string.msg_loading))
        pDialog.setCancelable(true)
    }


    protected fun showpDialog() {

        if (!pDialog.isShowing) pDialog.show()
    }

    protected fun hidepDialog() {

        if (pDialog.isShowing) pDialog.dismiss()
    }


    /**
     * Launching camera app to capture image
     */
    private fun captureImage() {
        if (Build.VERSION.SDK_INT > 21) { //use this if Lollipop_Mr1 (API 22) or above
            println("if: ${Build.VERSION.SDK_INT}")
            val callCameraApplicationIntent = Intent()
            callCameraApplicationIntent.action = MediaStore.ACTION_IMAGE_CAPTURE
//
            // We give some instruction to the intent to save the image
            var photoFile: File? = null
//
            try {
                // If the createImageFile will be successful, the photo file will have the address of the file
                photoFile = createImageFile()
                // Here we call the function that will try to catch the exception made by the throw function
            } catch (e: IOException) {
                Logger.getAnonymousLogger().info("Exception error in generating the file")
                e.printStackTrace()
            }
//
//            // Here we add an extra file to the intent to put the address on to. For this purpose we use the FileProvider, declared in the AndroidManifest.
            val outputUri = FileProvider.getUriForFile(
                this,
                BuildConfig.APPLICATION_ID + ".provider",
                photoFile!!)
            callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri)
//
//            // The following is a new line with a trying attempt
//            callCameraApplicationIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
//
//            Logger.getAnonymousLogger().info("Calling the camera App by intent")
//
//            // The following strings calls the camera app and wait for his file in return.
//            startActivityForResult(callCameraApplicationIntent, CAMERA_PIC_REQUEST)
        } else {
            println("else: ${Build.VERSION.SDK_INT}")
//            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//
//            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE)
//
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
//
//            // start the image capture Intent
//            startActivityForResult(intent, CAMERA_PIC_REQUEST)
        }


    }

    @Throws(IOException::class)
    internal fun createImageFile(): File {
        Logger.getAnonymousLogger().info("Generating the image - method started")

        // Here we create a "non-collision file name", alternatively said, "an unique filename" using the "timeStamp" functionality
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmSS").format(Date())
        val imageFileName = "IMAGE_" + timeStamp
        // Here we specify the environment location and the exact path where we want to save the so-created file
        val storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/photo_saving_app")
        Logger.getAnonymousLogger().info("Storage directory set")

        // Then we create the storage directory if does not exists
        if (!storageDirectory.exists()) storageDirectory.mkdir()

        // Here we create the file using a prefix, a suffix and a directory
        val image = File(storageDirectory, imageFileName + ".jpg")
        // File image = File.createTempFile(imageFileName, ".jpg", storageDirectory);

        // Here the location is saved into the string mImageFileLocation
        Logger.getAnonymousLogger().info("File name and path set")

        mImageFileLocation = image.absolutePath
        // fileUri = Uri.parse(mImageFileLocation);
        // The file is returned to the previous intent across the camera application
        return image
    }


    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri")
    }


    /**
     * Receiving activity result method will be called after closing the camera
     */

    /**
     * ------------ Helper Methods ----------------------
     */

    /**
     * Creating file uri to store image/video
     */
    fun getOutputMediaFileUri(type: Int): Uri {
        return Uri.fromFile(getOutputMediaFile(type))
    }

    // Uploading Image/Video
    private fun uploadFile() {
        if (postPath == null || postPath == "") {
            Toast.makeText(this, "please select an image ", Toast.LENGTH_LONG).show()
            return
        } else {
            showpDialog()

            // Map is used to multipart the file using okhttp3.RequestBody
            val map = HashMap<String, RequestBody>()
            val file = File(postPath!!)

            // Parsing any Media type file
            val requestBody = file.asRequestBody("*/*".toMediaTypeOrNull())
            map.put("file\"; filename=\"" + file.name + "\"", requestBody)
            val getResponse = AppConfig().getRetrofit()?.create(ApiConfig::class.java)
            println("get response")
//            lifecycleScope.launchWhenCreated {
//                val response = try {
//                    getResponse?.upload("multipart/form-data", map)
//                } catch (e: IOException) {
//                    println("IOException")
//                    return@launchWhenCreated
//                } catch (e: HttpException) {
//                    println("HttpException")
//                    return@launchWhenCreated
//                }
//
//                if (response != null) {
//                    if (response.isSuccessful && response.body() != null) {
//                        hidepDialog()
//                        val serverResponse = response.body()
//                        if (serverResponse != null) {
//                            Toast.makeText(applicationContext, "${serverResponse.message}, ${serverResponse.status}", Toast.LENGTH_SHORT).show()
//                        }
//                        println("Response Success")
//                    } else {
//                        println("Response Failed")
//                    }
//                } else {
//                    println("Response Null")
//                }
//            }
            val call = getResponse?.upload("multipart/form-data", map)
            call?.enqueue(object : Callback<ServerRes> {
                override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                    if (response.isSuccessful) {
                        println("response successful")
                        if (response.body() != null) {
                            println("body not empty")
                            hidepDialog()
                            val serverResponse = response.body()
                            if (serverResponse != null) {
                                Toast.makeText(applicationContext, serverResponse.message, Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            println("body mpty")
                        }
                    } else {
                        println("response failed")
                        hidepDialog()
                        Toast.makeText(applicationContext, "problem uploading image", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                    hidepDialog()
                    Log.v("Response gotten is", t.message!!)
                }
            })
        }
    }

    companion object {
        private val REQUEST_TAKE_PHOTO = 0
        private val REQUEST_PICK_PHOTO = 2
        private val CAMERA_PIC_REQUEST = 1111

        private val TAG = ImageActivity::class.java.getSimpleName()

        private val CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100

        val MEDIA_TYPE_IMAGE = 1
        val IMAGE_DIRECTORY_NAME = "Android File Upload"

        /**
         * returning image / video
         */
        private fun getOutputMediaFile(type: Int): File? {

            // External sdcard location
            val mediaStorageDir = File(
                Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME)

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d(TAG, "Oops! Failed create "
                            + IMAGE_DIRECTORY_NAME + " directory")
                    return null
                }
            }

            // Create a media file name
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(Date())
            val mediaFile: File
            if (type == MEDIA_TYPE_IMAGE) {
                mediaFile = File(mediaStorageDir.path + File.separator
                        + "IMG_" + ".jpg")
            } else {
                return null
            }

            return mediaFile
        }
    }


}