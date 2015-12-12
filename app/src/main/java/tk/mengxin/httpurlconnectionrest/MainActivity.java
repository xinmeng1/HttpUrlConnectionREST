package tk.mengxin.httpurlconnectionrest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_search_activity)
    void OnClick(){
        Intent intent = new Intent(this,SearchActivity.class);
        this.startActivity(intent);
    }
    @OnClick(R.id.btn_get_activity)
    void OnClickGetActivity(){
        Intent intent = new Intent(this,GetActivity.class);
        this.startActivity(intent);
    }

    @OnClick(R.id.btn_post_activity)
    void OnClickPostActivity() {
        Intent intent = new Intent(this,PostActivity.class);
        this.startActivity(intent);
    }
}
