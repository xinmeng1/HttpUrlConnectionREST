package tk.mengxin.httpurlconnectionrest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import tk.mengxin.httpurlconnectionrest.util.RestTask;
import tk.mengxin.httpurlconnectionrest.util.RestUtil;

/**
 * Created by Android Studio.
 * User: xin
 * Date: 02/12/2015
 * Time: 12:01
 * Version: V 1.0
 */

public class SearchActivity extends Activity implements
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
//File POST
            Bitmap image = BitmapFactory.decodeResource(
                    getResources(),
                    R.mipmap.ic_launcher);
            File imageFile = new File(
                    getExternalCacheDir(), "myImage.png");
            FileOutputStream out =
                    new FileOutputStream(imageFile);
            image.compress(Bitmap.CompressFormat.PNG, 0, out);
            out.flush();
            out.close();
            List<NameValuePair> fileParameters =
                    new ArrayList<NameValuePair>();
            fileParameters.add(new BasicNameValuePair("title",
                    "Android Recipes"));
            fileParameters.add(new BasicNameValuePair("desc",
                    "Image File Upload"));
            RestTask uploadTask =
                    RestUtil.obtainMultipartPostTask(
                            POST_URI, fileParameters,
                            imageFile, "avatarImage");
            uploadTask.setResponseCallback(this);
            uploadTask.setProgressCallback(this);
            uploadTask.execute();
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