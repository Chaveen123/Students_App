package com.example.group_project;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;

public class TrackFragment extends Fragment {

    private static final int REQ_USER_CONSENT=200;
    LocationDetailsReceiver smsBroadcastReceiver;
    EditText link,lat,lon;
    Button trackButton,open;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_track, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Find my item");

        trackButton = view.findViewById(R.id.track);
        trackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                    reqMessage();
                    Toast.makeText(getActivity(), "Successful...Please check Message",Toast.LENGTH_SHORT).show();
                }else{
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.SEND_SMS},100);
                }
            }
        });

        open = view.findViewById(R.id.openlink);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUrl();
            }
        });

        lat = view.findViewById(R.id.latitude);
        lon = view.findViewById(R.id.longitude);
        link = view.findViewById(R.id.mapslink);
        startSmartUserConsent();

        return view;
    }

    private void getUrl() {
        String checklink = link.getText().toString().trim();

        if(TextUtils.isEmpty(checklink)){
            Toast.makeText(getActivity(), "Link has not yet received",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getActivity(), "Link has received",Toast.LENGTH_SHORT).show();
            Uri uri = Uri.parse(checklink);
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }

    private void reqMessage(){
        SmsManager sms=SmsManager.getDefault();
        sms.sendTextMessage("+94774556615", null, "GPS ON", null,null);
        Toast.makeText(getActivity(), "Requesting Location...!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode ==100 && grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            reqMessage();
        }else {
            Toast.makeText(getActivity(), "Permission Denied!",Toast.LENGTH_SHORT).show();
        }
    }

    private void startSmartUserConsent(){
        SmsRetrieverClient client = SmsRetriever.getClient(getActivity());
        client.startSmsUserConsent(null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_USER_CONSENT){
            if((resultCode == RESULT_OK) && (data != null)){
                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                getLinkFromMessage(message);
            }
        }
    }

    private void getLinkFromMessage(String message){
        String coorlat = message.split("Latitude=")[1].split("Longitude=")[0].trim();
        String coorlon = message.split("Longitude=")[1].split("Item")[0].trim();
        String url = message.split("is:")[1].trim();
        lat.setText(coorlat);
        lon.setText(coorlon);
        link.setText(url);
    }

    private void registerBroadcastReceiver(){
        smsBroadcastReceiver = new LocationDetailsReceiver();

        smsBroadcastReceiver.smsBroadcastReceiverListener = new LocationDetailsReceiver.SmsBroadcastReceiverListener() {
            @Override
            public void onSuccess(Intent intent) {
                startActivityForResult(intent,REQ_USER_CONSENT);
            }

            @Override
            public void onFailure() {

            }
        };
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        requireActivity().registerReceiver(smsBroadcastReceiver,intentFilter);
    }

    @Override
    public void onStart() {
        super.onStart();
        registerBroadcastReceiver();
    }

    @Override
    public void onStop() {
        super.onStop();
        requireActivity().unregisterReceiver(smsBroadcastReceiver);
    }
}

