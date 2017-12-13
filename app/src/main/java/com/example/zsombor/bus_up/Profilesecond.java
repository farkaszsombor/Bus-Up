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

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
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
    private ImageView imageView,imageView_button_to_database,imageView_profile;
    private Bitmap bitmap;
    private File destination = null;
    private String imgPath = null;
    private Intent data;
    private  final int PICK_IMAGE_CAMERA = 1,PICK_IMAGE_GALLERY = 2;
    private File mediaFile;
    private EditText editTextname ;
    private EditText editTextemail;
    private UserInfo info;
    private User userData;
    ScrollView sv;
    private  String userId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userData = new User();
    }

    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

         View rootview = inflater.inflate(R.layout.profile,container,false);
        imageView= (ImageView) rootview.findViewById(R.id.imageView4);
        imageView_button_to_database = rootview.findViewById(R.id.edit_button);
        editTextemail = (EditText) rootview.findViewById(R.id.email_profil);
        imageView_profile = (ImageView) rootview.findViewById(R.id.imageView4);
        editTextname = (EditText) rootview.findViewById(R.id.fullname_profile);

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
        setUserInformationToProfile();
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





    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String s = (String)getArguments().get("user");
        Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
    }

    private void updateDataInFirebase(){

        if(mediaFile != null){//van kep ki valasztva !!!
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
        else{
            setIfeverythingIsCorrect(editTextname.getText().toString(),editTextemail.getText().toString(),userData.getPhotoURL(),userData,FirebaseAuth.getInstance().getCurrentUser().getUid());
        }
    }
    private  void updateCredentials(FirebaseUser user,String uid,String photourl,String email,String name) {

            setIfeverythingIsCorrect(name,email,photourl,userData,uid);
            user.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.e(TAG, " sikerult");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "nem sikerult", e.getCause());
                }
            });

        }

    private void setIfeverythingIsCorrect(String name, String email, String photourl, User user,String uid)
    {
        if (!name.isEmpty()&& !name.equals("")){
            if (!user.getName().equals("")){
                mDatabaseReference.child("Users").child(uid).child("name").setValue(name);
            }
        }
        if (!email.isEmpty() && !email.equals("")){
            if (!user.getEmail().equals("")){
                mDatabaseReference.child("Users").child(uid).child("email").setValue(email);
            }
        }
        if (!photourl.isEmpty() && !photourl.equals("")) {
            if (!user.getPhotoURL().equals(photourl)) {
                mDatabaseReference.child("Users").child(uid).child("pjotourl").setValue(photourl);
            }
        }
    }
    private void setUserInformationToProfile() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();


        mDatabaseReference.child("Users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              userData = dataSnapshot.getValue(User.class);

                editTextname.setText(userData.getName());
                editTextemail.setText(userData.getEmail());
                Glide.with(getActivity()).load(userData.getPhotoURL()).into(imageView_profile);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );


}
}
