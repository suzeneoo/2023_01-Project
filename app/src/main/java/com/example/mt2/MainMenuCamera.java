package com.example.mt2;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions;

import java.io.IOException;

// Reference: https://www.youtube.com/watch?v=1wewsm0Av98&t=235s&ab_channel=AtifPervaiz
public class MainMenuCamera extends Fragment {
    private MaterialButton btn_inputImage;
    private MaterialButton btn_recognizedText;
    private ShapeableImageView iv_image;
    private EditText et_recognizedText;

    // TAG
    private static final String TAG = "MAIN_TAG";

    // Uri of the image that we will take from Camera/Gallery
    private Uri imageUri = null;

    // to handle the result of Camera/Gallery permissions
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;

    // arrays of permission required to pick image from Camera, Gallery
    private String[] cameraPermissions;
    private String[] storagePermissions;

    // progress dialog
    private ProgressDialog progressDialog;

    // TextRecognizer
    private TextRecognizer textRecognizer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main_menu_camera, container, false);

        btn_inputImage = v.findViewById(R.id.btn_inputImage);
        btn_recognizedText = v.findViewById(R.id.btn_recognizedText);
        iv_image = v.findViewById(R.id.iv_image);
        et_recognizedText = v.findViewById(R.id.et_recognizedText);

        // init arrays of permissions required for camera, gallery
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        // init setup the progress dialog, show while text from image is being recognized
        progressDialog = new ProgressDialog(getActivity().getApplicationContext());
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        // init TextRecognizer (ver.Korean)
        textRecognizer = TextRecognition.getClient(new KoreanTextRecognizerOptions.Builder().build());

        // handle click, show input image dialog
        btn_inputImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputImageDialog();
            }
        });

        // handle click, start recognizing text from image we took from Camera/Gallery
        btn_recognizedText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check if image is picked or ont, picked if imageUri is not null
                if(imageUri == null){
                    // imageUri is null, which means we haven't pick image yet, can't recognized text
                    Toast.makeText(getActivity().getApplicationContext(), "Pick image first...", Toast.LENGTH_SHORT).show();
                }
                else{
                    // imageUri is not null, which means we have picked image, we can recognize text
                    recognizedTextFromImage();
                    // checking imageUri's value
                    //Toast.makeText(getActivity().getApplicationContext(), imageUri.toString(), Toast.LENGTH_SHORT).show();

                }
            }
        });

        //return inflater.inflate(R.layout.fragment_main_menu_camera,container, false);
        return v;
    }

    private void recognizedTextFromImage() {
        Log.d(TAG, "recognizedTextFromImage: ");
        // set message and show progress dialog
        // progressDialog.setMessage("Rocognizing text...");
        // progressDialog.show();
        try {
            // Prepare InputImage from image uri
            InputImage inputImage = InputImage.fromFilePath(getActivity().getApplicationContext(), imageUri);
            // image prepared, we are about to start text recognition process, change progress message
            progressDialog.setMessage("Recognizing text...");
            // start text recognition process from image
            Task<Text> textTaskResult = textRecognizer.process(inputImage)
                    .addOnSuccessListener(new OnSuccessListener<Text>() {
                        @Override
                        public void onSuccess(Text text) {
                            // process completed, dismiss dialog
                            progressDialog.dismiss();
                            // get the recognized text
                            String recognizedText = text.getText();
                            Log.d(TAG, "onSuccess: recognizedText: " + recognizedText);
                            // set the recognized text to edit text
                            et_recognizedText.setText(recognizedText);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // failed recognizing text from image, dismiss dialog, show reason in Toast
                            progressDialog.dismiss();
                            Log.e(TAG, "onFailure: ", e);
                            Toast.makeText(getActivity().getApplicationContext(), "Failed recognizing text due to "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        } catch (IOException e) {
            // Exception occurred while preparing InputImage, dismiss dialog, show reason in Toast
            progressDialog.dismiss();
            Log.e(TAG, "recognizedTextFromImage: ", e);
            Toast.makeText(getActivity().getApplicationContext(), "Failed preparing image due to "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void showInputImageDialog() {
        PopupMenu popupMenu = new PopupMenu(getActivity().getApplicationContext(), btn_inputImage);

        // Add items Camera, Gallery to PopupMenu,parm 2 is menu id, param 3 is position of this menu item in menu items list, param 4 is title of the menu
        popupMenu.getMenu().add(Menu.NONE, 1, 1, "CAMERA");
        popupMenu.getMenu().add(Menu.NONE, 2, 2, "GALLERY");

        // Show PopupMenu
        popupMenu.show();

        // handle PoppuMenu item clicks
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // get item id that is clicked from PopupMenu
                int id = menuItem.getItemId();
                if(id == 1){
                    // Camera is click, check if camera permissions are granted or not
                    Log.d(TAG, "onMenuItemClick: Camera Clicked...");
                    if(checkCameraPermission()){
                        // Camera permissions granted, we can launch camera intent
                        pickImageCamera();
                    }
                    else{
                        // Camera permissions not granted, request the camera permissions
                        requestCameraPermissions();
                    }
                }
                else if(id == 2){
                    // Gallery is clicked, check if storage permission is granted or not
                    Log.d(TAG, "onMenuItemClick: Gallery Clicked...");
                    if(checkStoragePermission()){
                        // storage permission granted, we can launch the gallery intent
                        pickImageGallery();
                    }
                    else{
                        // storage permission not granted, request the storage permission
                        requestStoragePermission();
                    }
                }
                return true;
            }
        });
    }

    private void pickImageGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);

        intent.setType("image/*");
        galleryActivityResultLauncher.launch(intent);
    }

    private ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // here we will receive the image, if picked
                    if(result.getResultCode() == Activity.RESULT_OK){
                        // image picked
                        Intent data = result.getData();
                        imageUri = data.getData();
                        Log.d(TAG, "onActivityResult: imageUri "+ imageUri);
                        // set to imageview
                        iv_image.setImageURI(imageUri);
                    }
                    else{
                        Log.d(TAG, "onActivityResult: cancelled");
                        //cancelled
                        Toast.makeText(getActivity().getApplicationContext(), "Cancelled...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    private void pickImageCamera(){
        Log.d(TAG, "pickImageCamera: ");
        // get ready the image data to store in MediaStore
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Sample Title");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Sample Description");
        // Image Uri
        imageUri = requireContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        // Intent to launch camera
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        cameraActivityResultLauncher.launch(intent);
    }

    private  ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // here we will receive the image, if taken from camera
                    if(result.getResultCode() == Activity.RESULT_OK){
                        // image is taken from camera
                        // we already have the image in imageUri using function pickImageCamera()
                        Log.d(TAG, "onActivityResult: imageUri "+imageUri);
                        iv_image.setImageURI(imageUri);
                    }
                    else{
                        //cancelled
                        Log.d(TAG, "onActivityResult: cancelled");
                        Toast.makeText(getActivity().getApplicationContext(), "Cancelled...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    private boolean checkStoragePermission(){
        /* check if storage permission is allowed or not
        return true if allowed, false if not allowed */
        boolean result = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result;
    }

    private void requestStoragePermission() {
        // request storage permission (for gallery image pick)
        ActivityCompat.requestPermissions(requireActivity(), storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission(){
        /* check if camera & storage permissions are allowed or not
        return true if allowed, false if not allowed */
        boolean cameraResult = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean storageResult = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return cameraResult && storageResult;
    }

    private void requestCameraPermissions(){
        // request camera permissions (for camera intent)
        ActivityCompat.requestPermissions(requireActivity(), cameraPermissions, CAMERA_REQUEST_CODE);
    }

    //handle permission results
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                // Check if some action from permission dialog performed or not Allow/Deny
                if(grantResults.length > 0){
                    // Check if Camera, Storage permissions granted, contains boolean results either true or false
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    // Check if both permissions are granted or not
                    if(cameraAccepted && storageAccepted){
                        // both permissions (Camera & Gallery) are granted, we can launch camera intent
                        pickImageCamera();
                    }
                    else{
                        // one or both permissions are denied, can't launch camera intent
                        Toast.makeText(getActivity().getApplicationContext(), "Camera & Storage permissions are required", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    // Nether allowed not denied, rather cancelled
                    Toast.makeText(getActivity().getApplicationContext(), "Cancelled...", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            case STORAGE_REQUEST_CODE:{
                // Check if some action from permission dialog performed or not Allow/Deny
                if(grantResults.length > 0){
                    // Check if Storage permissions granted, contains boolean results either true or false
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    // Check if storage permission is granted or not
                    if(storageAccepted){
                        // storage permission granted, we can launch gallery intent
                        pickImageGallery();
                    }
                    else{
                        // storage permission denied, can't launch gallery intent
                        Toast.makeText(getActivity().getApplicationContext(), "Storage permission is required", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
    }
}