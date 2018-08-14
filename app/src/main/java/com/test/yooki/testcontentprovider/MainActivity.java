package com.test.yooki.testcontentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


/**
 * 1.// 插入数据后通知改变
 * mContext.getContentResolver().notifyChange(uri, null);
 *
 * ContentResolver()====ContentProvider
 * 客户端拿到的是ContentResolver()
 *
 *
 * 2.ContentResolver底层数据格式？表格形式，从传的参数看，更匹配数据库。所以一般用数据库做为底层存储。
 *
 *
 * 3.遗留问题：permission问题
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "DEBUG-WCL: " + MainActivity.class.getSimpleName();

    private TextView mTvShowBooks; // 显示书籍
    private TextView mTvShowUsers; // 显示用户

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvShowBooks = (TextView) findViewById(R.id.main_tv_show_books);
        mTvShowUsers = (TextView) findViewById(R.id.main_tv_show_users);
    }

    /**
     * 添加书籍的事件监听
     *
     * @param view 视图
     */
    public void addBooks(View view) {
        Uri bookUri = BookProvider.BOOK_CONTENT_URI;
        ContentValues values = new ContentValues();
        values.put("_id", 6);
        values.put("name", "信仰上帝");
        getContentResolver().insert(bookUri, values);
    }

    /**
     * 显示书籍
     *
     * @param view 视图
     */
    public void showBooks(View view) {
        String content = "";
        Uri bookUri = BookProvider.BOOK_CONTENT_URI;
        Cursor bookCursor = getContentResolver().query(bookUri, new String[]{"_id", "name"}, null, null, null);
        if (bookCursor != null) {
            while (bookCursor.moveToNext()) {
                Book book = new Book();
                book.bookId = bookCursor.getInt(0);
                book.bookName = bookCursor.getString(1);
                content += book.toString() + "\n";
                Log.e(TAG, "query book: " + book.toString());
                mTvShowBooks.setText(content);
            }
            bookCursor.close();
        }
    }

    /**
     * 显示用户
     *
     * @param view 视图
     */
    public void showUsers(View view) {
        String content = "";
        Uri userUri = BookProvider.USER_CONTENT_URI;
        Cursor userCursor = getContentResolver().query(userUri, new String[]{"_id", "name", "sex"}, null, null, null);
        if (userCursor != null) {
            while (userCursor.moveToNext()) {
                User user = new User();
                user.userId = userCursor.getInt(0);
                user.userName = userCursor.getString(1);
                user.isMale = userCursor.getInt(2) == 1;
                content += user.toString() + "\n";
                Log.e(TAG, "query user:" + user.toString());
                mTvShowUsers.setText(content);
            }
            userCursor.close();
        }
    }
}
