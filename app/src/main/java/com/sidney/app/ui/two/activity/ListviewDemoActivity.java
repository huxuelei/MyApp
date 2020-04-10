package com.sidney.app.ui.two.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.sidney.app.R;
import com.sidney.devlib.comment.baseAdapter.CommonAdapter;
import com.sidney.devlib.comment.baseAdapter.ViewHolder;
import com.sidney.devlib.ui.BaseActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListviewDemoActivity extends BaseActivity {

    private List<String> mDatas = new ArrayList<>(Arrays.asList("MultiItem ListView",
            "RecyclerView",
            "MultiItem RecyclerView"));
    private ListView mListView;
    private View mEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_demo);
        mListView = ((ListView) findViewById(R.id.id_listview_list));
        mEmptyView = findViewById(R.id.id_empty_view);
        mListView.setAdapter(new CommonAdapter<String>(this, R.layout.item_list, mDatas) {
            @Override
            public void convert(ViewHolder holder, String o, int pos) {
                holder.setText(R.id.id_item_list_title, o);
            }

            @Override
            public void onViewHolderCreated(ViewHolder holder, View itemView) {
                super.onViewHolderCreated(holder, itemView);
            }
        });

        mListView.setEmptyView(mEmptyView);
//        TextView t1 = new TextView(this);
//        t1.setText("Header 1");
//        TextView t2 = new TextView(this);
//        t2.setText("Header 2");
//        mListView.addHeaderView(t1);
//        mListView.addHeaderView(t2);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position) {
                    case 0:
                    default:
                        //intent = new Intent(mActivity, MultiItemListViewActivity.class);
                        break;
                    case 1:
                        //intent = new Intent(mActivity, RecyclerViewActivity.class);
                        break;
                    case 2:
                        //intent = new Intent(mActivity, MultiItemRvActivity.class);
                        break;

                }
                if (intent != null)
                    startActivity(intent);
            }
        });

        Message message = new Message();
        message.what = 1;
        message.obj = "11";
        handler.sendMessage(message);
    }

    private HandlerHolder handler = new HandlerHolder(new OnReceiveMessageListener() {

        @Override

        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String ss= (String) msg.obj;
                    break;
            }
        }
    });

    public static class HandlerHolder extends Handler {

        private WeakReference<OnReceiveMessageListener> mListenerWeakReference;

        /**
         * @param listener 收到消息回调接口
         */
        HandlerHolder(OnReceiveMessageListener listener) {
            mListenerWeakReference = new WeakReference<>(listener);
        }


        @Override

        public void handleMessage(Message msg) {
            if (mListenerWeakReference != null && mListenerWeakReference.get() != null) {
                mListenerWeakReference.get().handlerMessage(msg);
            }
        }
    }

    /**
     * 收到消息回调接口
     */
    public interface OnReceiveMessageListener {
        void handlerMessage(Message msg);
    }
}
