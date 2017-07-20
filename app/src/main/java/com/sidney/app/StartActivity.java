package com.sidney.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.sidney.devlib.ui.BaseActivity;
import com.sidney.devlib.utils.PermissionUtils;

public class StartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        PermissionUtils.requestPermission(mActivity, PermissionUtils.CODE_READ_EXTERNAL_STORAGE, mPermissionGrant);
        //PermissionUtils.requestPermission(mActivity, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE, mPermissionGrant);

        //PermissionUtils.requestMultiPermissions(this, mPermissionGrant);//一次申请多个权限
    }


    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
//                case PermissionUtils.CODE_RECORD_AUDIO:
//                    Toast.makeText(mActivity, "Result Permission Grant CODE_RECORD_AUDIO", Toast.LENGTH_SHORT).show();
//                    break;
//                case PermissionUtils.CODE_GET_ACCOUNTS:
//                    Toast.makeText(mActivity, "Result Permission Grant CODE_GET_ACCOUNTS", Toast.LENGTH_SHORT).show();
//                    break;
//                case PermissionUtils.CODE_READ_PHONE_STATE:
//                    Toast.makeText(mActivity, "Result Permission Grant CODE_READ_PHONE_STATE", Toast.LENGTH_SHORT).show();
//                    break;
//                case PermissionUtils.CODE_CALL_PHONE:
//                    Toast.makeText(mActivity, "Result Permission Grant CODE_CALL_PHONE", Toast.LENGTH_SHORT).show();
//                    break;
//                case PermissionUtils.CODE_CAMERA:
//                    Toast.makeText(mActivity, "Result Permission Grant CODE_CAMERA", Toast.LENGTH_SHORT).show();
//                    break;
//                case PermissionUtils.CODE_ACCESS_FINE_LOCATION:
//                    Toast.makeText(mActivity, "Result Permission Grant CODE_ACCESS_FINE_LOCATION", Toast.LENGTH_SHORT).show();
//                    break;
//                case PermissionUtils.CODE_ACCESS_COARSE_LOCATION:
//                    Toast.makeText(mActivity, "Result Permission Grant CODE_ACCESS_COARSE_LOCATION", Toast.LENGTH_SHORT).show();
//                    break;
                case PermissionUtils.CODE_READ_EXTERNAL_STORAGE:
                    Toast.makeText(mActivity, "========Result Permission Grant CODE_READ_EXTERNAL_STORAGE====", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent it = new Intent(mActivity, MainActivity.class);
                            startActivity(it);
                        }
                    }, 2000);
                    break;
//                case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
//                    Toast.makeText(mActivity, "Result Permission Grant CODE_WRITE_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
//                    break;
                default:
                    break;
            }
        }
    };

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.d("ppppppp====", "===========" + requestCode + "=====" + permissions + "===========" + grantResults);
        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionGrant);
    }

}
