package com.example.zsombor.bus_up;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.transform.Result;

/**
 * Created by Janosi on 12/1/2017.
 */

public class Profilesecond  extends Fragment {
    private static final String TAG = "Profilesecond";
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseReference = database.getReference();
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    private FirebaseAuth mAuth;
    private static final int CAPTURE_IMAGE_ACTIVITY_CODE=1888;
    private ImageView imageView,imageView_button_to_database;
    private Bitmap bitmap;
    private File destination = null;
    private String imgPath = null;
    private Intent data;
    private  final int PICK_IMAGE_CAMERA = 1,PICK_IMAGE_GALLERY = 2;
    private File mediaFile;
    private EditText editTextname,editTextemail;
    ScrollView sv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /*public View onCreateView1(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (container == null) { return null; }

        sv = (ScrollView)inflater.inflate(R.layout.profilefirst, container, false);

        imageView_button_to_database.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Please long press the key", Toast.LENGTH_LONG );

            }});

        return sv;
    }
    private void TakePhoto() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File sdCard = Environment.getExternalStorageDirectory();
        String path = sdCard.getAbsolutePath() + "/Camera"  ;

        File dir = new File(path);
        if (!dir.exists()) {
            if (dir.mkdirs()) {

            }
        }
        String FileName = "image";
        File file = new File(path, FileName + ".jpg");
        Uri outputFileUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(intent, TAKE_PICTURE);
    }*/




    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

         View rootview = inflater.inflate(R.layout.profile,container,false);
        imageView= (ImageView) rootview.findViewById(R.id.imageView4);
        imageView_button_to_database = rootview.findViewById(R.id.edit_button);
        editTextname = (EditText) rootview.findViewById(R.id.fullname_profile);
        editTextemail = (EditText) rootview.findViewById(R.id.email_profil);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_CODE);
            }
        });
        imageView_button_to_database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDataInFirebase();
            }
        });

        return rootview;
    }

  public void onActivityResult(int requestCode, int resultCode, Intent data) {
        FileOutputStream out=null;


            if (requestCode == CAPTURE_IMAGE_ACTIVITY_CODE)

         {
            if (resultCode == Activity.RESULT_OK) {

                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                // convert byte array to Bitmap

                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                        byteArray.length);

                imageView.setImageBitmap(bitmap);


                File pictureFile = getOutputMediaFile();
                if (pictureFile == null) {
                    Log.d(TAG,
                            "Error creating media file, check storage permissions: ");// e.getMessage());
                    return;
                }
                try {
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
                    fos.close();
                } catch (FileNotFoundException e) {
                    Log.d(TAG, "File not found: " + e.getMessage());
                } catch (IOException e) {
                    Log.d(TAG, "Error accessing file: " + e.getMessage());
                }
            }

        }

  }

    private  File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getActivity().getApplicationContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        String mImageName="MI_"+ timeStamp +".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }
  /*@Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, requestCode, data);
      try {
          // When an Image is picked
          if (requestCode == 0 && resultCode == Activity.RESULT_OK && null != data) {
              Uri selectedImage = data.getData();

              imageView.setImageBitmap(BitmapFactory.decodeFile(getRealPathFromURI(selectedImage)));
          } else
              new ShowErrorToast(getActivity(), "Hey! your Android phone is busy");

      } catch (Exception e) {

      }

  }

    /*private String getRealPathFromURI(Uri selectedImage) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(data, filePathColumn, null, null, null);
        String picturePath = "";
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
        }
        return picturePath;
    }*/




    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String s = (String)getArguments().get("user");
        Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
    }

    private void updateDataInFirebase(){

        if(mediaFile != null){
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            final String userID = user.getUid();
            StorageReference picsRef = reference.child("ProfilePhotos/" + userID + ".jpg");
            picsRef.putFile(Uri.fromFile(mediaFile)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(),"Siker",Toast.LENGTH_LONG).show();
                    updateCredentials(user,userID,taskSnapshot.getDownloadUrl().toString(),editTextemail.getText().toString(),editTextname.getText().toString());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG,"Nem siker!",e.getCause());
                }
            });
        }
    }
    private  void updateCredentials(FirebaseUser user,String uid,String photourl,String email,String name){
        mDatabaseReference.child("Users").child(uid).child("email").setValue(email);
        mDatabaseReference.child("Users").child(uid).child("name").setValue(name);
        mDatabaseReference.child("Users").child(uid).child("photoURL").setValue(photourl);

        user.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.e(TAG,"nem sikerult");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG,"nem sikerult",e.getCause());
            }
        });

    }


}
