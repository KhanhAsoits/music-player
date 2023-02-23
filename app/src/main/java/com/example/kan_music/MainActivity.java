package com.example.kan_music;
import static com.example.kan_music.utils.PermissionHelper.onProgressPermission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.example.kan_music.controllers.MusicController;
import com.example.kan_music.models.MusicViewModel;

public class MainActivity extends AppCompatActivity {
    public static MusicViewModel musicViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        musicViewModel = new ViewModelProvider(this).get(MusicViewModel.class);
        musicViewModel.setMusicContext(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, MusicListFragment.class, null)
                    .commit();
        }
        // get permission
        try {
            if (ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},999);
            }
        }catch (Exception e){
            Log.d("Get Perm Error : ",e.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 999){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                // do something Denied
                this.finishAffinity();
            }
        }

    }
}