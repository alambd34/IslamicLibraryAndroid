package com.fekracomputers.islamiclibrary.browsing.util;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.fekracomputers.islamiclibrary.browsing.activity.BookInformationActivity;
import com.fekracomputers.islamiclibrary.databases.BooksInformationDBContract;
import com.fekracomputers.islamiclibrary.databases.BooksInformationDbHelper;
import com.fekracomputers.islamiclibrary.download.downloader.BooksDownloader;
import com.fekracomputers.islamiclibrary.download.model.DownloadsConstants;
import com.fekracomputers.islamiclibrary.model.BookInfo;
import com.fekracomputers.islamiclibrary.reading.ReadingActivity;


/**
 * بسم الله الرحمن الرحيم
 * Created by Mohammd Yahia on 24/4/2017.
 */

public class BrowsingUtils {

    public static void openBookForReading(@NonNull BookInfo bookInfo, @NonNull Context context) {
        final Intent intent = new Intent(context, ReadingActivity.class);
        intent.putExtra(ReadingActivity.KEY_BOOK_ID, bookInfo.getBookId());
        context.startActivity(intent);
    }


    public static boolean openBookForReading(int bookId, int pageId, @NonNull Context context) {
        BooksInformationDbHelper booksInformationDbHelper = BooksInformationDbHelper.getInstance(context);
        if (booksInformationDbHelper != null) {
            if (booksInformationDbHelper.getBookDownloadStatus(bookId) >= DownloadsConstants.STATUS_FTS_INDEXING_ENDED) {
                Intent intent = new Intent(context, ReadingActivity.class);
                intent.putExtra(ReadingActivity.KEY_BOOK_ID, bookId);
                intent.putExtra(ReadingActivity.KEY_PAGE_ID, pageId);
                context.startActivity(intent);
            } else
                return false;
        }

        return false;
    }

    public static void startDownloadingBook(@NonNull BookInfo bookInfo, Context context) {
        BooksDownloader booksDownloader = new BooksDownloader(context);
        booksDownloader.downloadBook(bookInfo.getBookId(), bookInfo.getName(), true);
    }

    public static void openBookInformationActivity(@NonNull Context context, int book_id, String bookTitle)
    {
        Intent intent = new Intent(context, BookInformationActivity.class);
        intent.putExtra(BooksInformationDBContract.BooksCategories.COLUMN_NAME_BOOK_ID, book_id);
        intent.putExtra(BooksInformationDBContract.BookInformationEntery.COLUMN_NAME_TITLE, bookTitle);
        context.startActivity(intent);
    }

    public static void deleteBook(int bookId, @NonNull Context context) {
        BooksInformationDbHelper booksInformationDbHelper= BooksInformationDbHelper.getInstance(context);
        if (booksInformationDbHelper != null) {
            booksInformationDbHelper.deleteBook(bookId,context);
            BooksInformationDbHelper.broadCastBookDeleted(bookId,context);

        }
    }

}
