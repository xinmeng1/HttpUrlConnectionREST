package tk.mengxin.httpurlconnectionrest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import tk.mengxin.httpurlconnectionrest.util.RestTask;
import tk.mengxin.httpurlconnectionrest.util.RestUtil;
//public class GetActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//}


public class GetActivity extends Activity implements
        RestTask.ProgressCallback, RestTask.ResponseCallback {
    private static final String SEARCH_URI =
            "https://www.googleapis.com/customsearch/v1"
                    + "?key=%s&cx=%s&q=%s";
    private static final String SEARCH_KEY =
            "AIzaSyBbW-W1SHCK4eW0kK74VGMLJj_b-byNzkI";
    private static final String SEARCH_CX =
            "008212991319514020231:1mkouq8yagw";
    private static final String SEARCH_QUERY = "Android";
    private TextView mResult;
    private ProgressDialog mProgress;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScrollView scrollView = new ScrollView(this);
        mResult = new TextView(this);
        scrollView.addView(mResult, new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        setContentView(scrollView);
        //Create the request
        try{
//Simple GET
            String url = String.format(SEARCH_URI, SEARCH_KEY,
                    SEARCH_CX, SEARCH_QUERY);
            RestTask getTask = RestUtil.obtainGetTask(url);
            getTask.setResponseCallback(this);
            getTask.setProgressCallback(this);
            getTask.execute();
//Display progress to the user
            mProgress = ProgressDialog.show(this, "Searching",
                    "Waiting For Results...", true);
        } catch (Exception e) {
            mResult.setText(e.getMessage());
        }
    }
    @Override
    public void onProgressUpdate(int progress) {
        if (progress >= 0) {
            if (mProgress != null) {
                mProgress.dismiss();
                mProgress = null;
            }
//Update user of progress
            mResult.setText( String.format(
                    "Download Progress: %d%%", progress));
        }
    }
    @Override
    public void onRequestSuccess(String response) {
//Clear progress indicator
        if(mProgress != null) {
            mProgress.dismiss();
        }
//Process the response data (here we just display it)
        mResult.setText(response);
    }
    @Override
    public void onRequestError(Exception error) {
//Clear progress indicator
        if(mProgress != null) {
            mProgress.dismiss();
        }
//Process the response data (here we just display it)
        mResult.setText("An Error Occurred: "+error.getMessage());
    }
}