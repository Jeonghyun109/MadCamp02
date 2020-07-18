package com.example.test1.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.test1.R;

public class GalleryFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);

        return root;
    }

//    /**
//     * Upload Image Client Code
//     */
//    private void uploadImage() {
//
//        /**
//         * Progressbar to Display if you need
//         */
//        final ProgressDialog progressDialog;
//        progressDialog = new ProgressDialog(MainActivity.this);
//        progressDialog.setMessage(getString(R.string.string_title_upload_progressbar_));
//        progressDialog.show();
//
//        //Create Upload Server Client
//        ApiService service = RetroClient.getApiService();
//
//        //File creating from selected URL
//        File file = new File(imagePath);
//
//        // create RequestBody instance from file
//        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//
//        MultipartBody.Part body =
//                MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestFile);
//
//        Call<Result> resultCall = service.uploadImage(body);
//
//        resultCall.enqueue(new Callback<Result>() {
//            @Override
//            public void onResponse(Call<Result> call, Response<Result> response) {
//
//                progressDialog.dismiss();
//
//                // Response Success or Fail
//                if (response.isSuccessful()) {
//                    if (response.body().getResult().equals("success"))
//                        Snackbar.make(parentView, R.string.string_upload_success, Snackbar.LENGTH_LONG).show();
//                    else
//                        Snackbar.make(parentView, R.string.string_upload_fail, Snackbar.LENGTH_LONG).show();
//
//                } else {
//                    Snackbar.make(parentView, R.string.string_upload_fail, Snackbar.LENGTH_LONG).show();
//                }
//
//                /**
//                 * Update Views
//                 */
//                imagePath = "";
//                textView.setVisibility(View.VISIBLE);
//                imageView.setVisibility(View.INVISIBLE);
//            }
//
//            @Override
//            public void onFailure(Call<Result> call, Throwable t) {
//                progressDialog.dismiss();
//            }
//        });
//    }

}