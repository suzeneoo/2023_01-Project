package com.example.mt2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {

    private static final String TAG = "next";
    EditText mEmailText, mPasswordText, mPasswordcheckText, mName, mbirth ;
    Button mregisterBtn;
    private FirebaseAuth firebaseAuth;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String year;
    private String month;
    private String day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        //액션 바 등록하기

        //파이어베이스 접근 설정
        // user = firebaseAuth.getCurrentUser();
        firebaseAuth =  FirebaseAuth.getInstance();
        //firebaseDatabase = FirebaseDatabase.getInstance().getReference();

        mName = findViewById(R.id.user_name);
        mbirth = findViewById(R.id.user_birth);
        mEmailText = findViewById(R.id.user_id);
        mPasswordText = findViewById(R.id.user_password);
        mPasswordcheckText = findViewById(R.id.user_password_check);
        mregisterBtn = findViewById(R.id.register_Btn);

        mbirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), picker.class);
                startActivityForResult(intent,1000);
            }

        });

        if(picker.check){
            Intent pickerData = getIntent();
            if (pickerData.getExtras().getString("yy") != null && pickerData.getExtras().getString("mm") != null && pickerData.getExtras().getString("dd") != null) {
                year = pickerData.getExtras().getString("yy");
                month = pickerData.getExtras().getString("mm");
                day = pickerData.getExtras().getString("dd");
                mbirth.setText(year + "년" + month + "월" + day + "일");
            }
        }

        //파이어베이스 user 로 접글

        //가입버튼 클릭리스너   -->  firebase에 데이터를 저장한다.
        mregisterBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                //가입 정보 가져오기
                final String email = mEmailText.getText().toString().trim();
                String pwd = mPasswordText.getText().toString().trim();
                String pwdcheck = mPasswordcheckText.getText().toString().trim();
                String name = mName.getText().toString().trim();
                String birth = mbirth.getText().toString().trim();

                Map<String, Object> information = new HashMap<>();
                information.put("name", name);
                information.put("email",email);
                information.put("pwd", pwd);
                information.put("birth", birth);


                if(pwd.equals(pwdcheck)) {
                    Log.d(TAG, "등록 버튼 " + email + " , " + pwd);
                    final ProgressDialog mDialog = new ProgressDialog(signup.this);
                    mDialog.setMessage("가입중입니다...");
                    mDialog.show();

                    //파이어베이스에 신규계정 등록하기
                    firebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            //가입 성공시
                            if (task.isSuccessful()) {
                                mDialog.dismiss();

                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                String email = user.getEmail();
                                String uid = user.getUid();
                                String name = mName.getText().toString().trim();
                                String birth = mbirth.getText().toString().trim();

                                //해쉬맵 테이블을 파이어베이스 데이터베이스에 저장
                                HashMap<Object,String> hashMap = new HashMap<>();

                                hashMap.put("uid",uid);
                                hashMap.put("email",email);
                                hashMap.put("name",name);
                                hashMap.put("birth",birth);

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference reference = database.getReference("Users");
                                reference.child(uid).setValue(hashMap);

                                db.collection("member").document()
                                        .set(information)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error writing document", e);
                                            }
                                        });

                                //가입이 이루어져을시 가입 화면을 빠져나감.
                                Intent intent = new Intent(signup.this, login.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(signup.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();

                            } else {
                                mDialog.dismiss();
                                Toast.makeText(signup.this, "이미 존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                                return;  //해당 메소드 진행을 멈추고 빠져나감.

                            }

                        }
                    });

                    //비밀번호 오류시
                }else{

                    Toast.makeText(signup.this, "비밀번호가 틀렸습니다. 다시 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }

    public boolean onSupportNavigateUp(){
        onBackPressed();; // 뒤로가기 버튼이 눌렸을시
        return super.onSupportNavigateUp(); // 뒤로가기 버튼
    }
}