package com.cricket.crickais.freecode;


import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class st1 extends Fragment {


    RelativeLayout log;
    private ArrayList<Model> list;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference("MatchSh");
    ProgressBar progressBar;
    private RecyclerView recview;
    private RecyclerView recyclerView1;
    private MyAdapter2 adapter2;
    List<homing> homes;

    WebView webView;
    FirebaseDatabase firebaseDatabase;
    public st1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.st1, container, false);
        webView = rootView.findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        String stage = getArguments().getString("sl");
        firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference databaseReference1=firebaseDatabase.getReference("Series").child("International").child(stage);
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    Model model = snapshot.getValue(Model.class);
                if(model!=null) {
                    String asd = model.getTs1();
                    webView.loadUrl(asd);
                    progressBar = rootView.findViewById(R.id.progressBar);
                    progressBar.setVisibility(View.GONE);

                    webView.canGoBack();
                    webView.setOnKeyListener(new View.OnKeyListener() {

                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_BACK
                                    && event.getAction() == MotionEvent.ACTION_UP
                                    && webView.canGoBack()) {
                                webView.goBack();
                                return true;
                            }
                            return false;
                        }
                    });

                    webView.setWebViewClient(new WebViewClient() {

                        @Override
                        public void onReceivedError(WebView view, int errorCode,
                                                    String description, String failingUrl) {

                            view.loadUrl("about:blank");
                            Toast.makeText(getContext(), "Please check newtwork connectivity", Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return rootView;
    }

}