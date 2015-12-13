package tk.mengxin.httpurlconnectionrest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import tk.mengxin.httpurlconnectionrest.util.RestTask;
import tk.mengxin.httpurlconnectionrest.util.RestUtil;

//public class PostActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//}
public class PostActivity extends Activity implements
        RestTask.ProgressCallback, RestTask.ResponseCallback {
    private static final String POST_URI =
            "http://httpbin.org/post";
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
//Simple POST
//            List<NameValuePair> parameters =
//                    new ArrayList<NameValuePair>();
//            parameters.add(new BasicNameValuePair("title",
//                    "Android Recipes"));
//            parameters.add(new BasicNameValuePair("summary",
//                    "Learn Android Quickly"));
//            parameters.add(new BasicNameValuePair("author",
//                    "Smith"));

            ContentValues parameters =
                    new ContentValues();
            parameters.put("title", "Android Recipes");
            parameters.put("summary", "Learn Android Quickly");
            parameters.put("author", "Smith");

            RestTask postTask = RestUtil.obtainFormPostTask(
                    POST_URI, parameters);
            postTask.setResponseCallback(this);
            postTask.setProgressCallback(this);
            postTask.execute();
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