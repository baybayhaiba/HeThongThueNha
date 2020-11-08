package com.example.hethongthuenha.CreateRoom.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hethongthuenha.R;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class fragment_image extends Fragment {




    public fragment_image() {
        // Required empty public constructor
    }



    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    private ImageView[] listImgRoom=new ImageView[4];
    private int imageAdded=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);

        Button btnFinishStage3=view.findViewById(R.id.btnFinishStage3);

        for(int i=0;i<4;i++){
            String textViewStageId="imgRoom"+(i+1);
            int resId=getResources().getIdentifier(textViewStageId,"id",getActivity().getPackageName());
            listImgRoom[i]=view.findViewById(resId);

            listImgRoom[i].setOnClickListener(v -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE);
                    } else {
                        PickImageFromGallery();
                    }
                } else
                    PickImageFromGallery();
            });
        }



        btnFinishStage3.setOnClickListener(v -> {
            fragment_utilities fragment = new fragment_utilities();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameContainer, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                PickImageFromGallery();
            else
                Toast.makeText(getActivity(), "Permisstion deny", Toast.LENGTH_SHORT).show();
        }
    }
    public void PickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            Uri uri=data.getData();


            File file=new File(getPath(uri));
//            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//            listImgRoom[imageAdded].setImageBitmap(myBitmap);
            Uri newUri=Uri.parse(uri.toString());

            listImgRoom[imageAdded].setImageURI(newUri);

            listImgRoom[imageAdded].setContentDescription("Added");

            imageAdded++;
        }
    }

    public String getPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index =             cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }
}