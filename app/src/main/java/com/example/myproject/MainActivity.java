package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity{

    TextView text;
    TextView text1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.text);
        text1= findViewById(R.id.text1);
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.symptom_name, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final Spinner spinner1=(Spinner)findViewById(R.id.spinner1);
        final Spinner spinner2=(Spinner)findViewById(R.id.spinner2);
        final Spinner spinner3=(Spinner)findViewById(R.id.spinner3);
        final Spinner spinner4=(Spinner)findViewById(R.id.spinner4);
        final Spinner spinner5=(Spinner)findViewById(R.id.spinner5);
        final Spinner spinner6=(Spinner)findViewById(R.id.spinner6);



        Button predictDiseaseButton=(Button) findViewById(R.id.button);
        predictDiseaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String ab = spinner1.getSelectedItem().toString();
                final String bc = spinner2.getSelectedItem().toString();
                final String cde = spinner3.getSelectedItem().toString();
                final String de = spinner4.getSelectedItem().toString();
                final String ef = spinner5.getSelectedItem().toString();
                final String fg = spinner6.getSelectedItem().toString();
                Symptoms symptoms=new Symptoms(
                        ab,
                        bc,
                        cde,
                        de,
                        ef,
                        fg
                );
                getClient(symptoms);

            }

        });

    }

    private void getClient(Symptoms symptoms) {
        //OkHttpClient client = new OkHttpClient.Builder().build();

        //Retrofit retrofit = null;
        Retrofit.Builder builder=new Retrofit.Builder()
                .baseUrl("https://rocky-basin-82418.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit=builder.build();

        final APIInterface apiInterface=retrofit.create(APIInterface.class);
        Call<com.example.myproject.Response> call=apiInterface.predict(symptoms);

        call.enqueue(new Callback<com.example.myproject.Response>() {
            @Override
            public void onResponse(Call<com.example.myproject.Response> call, Response<com.example.myproject.Response> response) {
                //Toast.makeText(MainActivity.this,"Disease"+response.body().response,Toast.LENGTH_SHORT).show();
                Pattern p = Pattern.compile("\"([^\"]*)\"");
                if (response.body().response.equals("No Disease")){
                    text.setText("No Disease");
                    text1.setText("\t\t\t\t\t\t                   You are fit..");
                }
                else{
                    Matcher m = p.matcher(response.body().response);
                    while (m.find()) {

                        text.setText(m.group(1));
                        String ans=m.group(1);
                        final String ans1=ans;
                        Ans ob=new Ans(ans1);
                        Call<com.example.myproject.Response> call1=apiInterface.which_disease(ob);
                        call1.enqueue(new Callback<com.example.myproject.Response>() {
                                @Override
                                public void onResponse(Call<com.example.myproject.Response> call, Response<com.example.myproject.Response> response) {
                                    text1.setText(response.body().response);
                                }

                                @Override
                                public void onFailure(Call<com.example.myproject.Response> call, Throwable t) {
                                    Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });





                        }

                    }
                }


            @Override
            public void onFailure(Call<com.example.myproject.Response> call, Throwable t) {
                Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });


    }




}
