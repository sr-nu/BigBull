package com.tw.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.tw.services.UpdateProfileService;
import com.tw.utils.NetworkUtils;

import java.io.IOException;

public class UpdateProfile extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile);
        Button updateProfile = (Button)findViewById(R.id.update_profile);
        updateProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                UpdateProfileService updateProfileService = new UpdateProfileService();
                try {
                    updateProfileService.updateProfile(new NetworkUtils(), "ausmarton", new Integer(100000));
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        });

    }
}
