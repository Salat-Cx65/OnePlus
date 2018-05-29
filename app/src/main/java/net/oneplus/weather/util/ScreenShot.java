package net.oneplus.weather.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.android.volley.DefaultRetryPolicy;
import java.util.ArrayList;
import java.util.List;
import net.oneplus.weather.R;
import net.oneplus.weather.provider.CitySearchProvider;

public class ScreenShot {
    public static Bitmap createBitmap(Context context, ListView listview, String city) {
        int i;
        BaseAdapter adapter = (BaseAdapter) listview.getAdapter();
        int itemscount = adapter.getCount();
        int allitemsheight = 0;
        int itemWidth = 0;
        List<Bitmap> bmps = new ArrayList();
        int lineH = UIUtil.dip2px(context, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        int titleSize = UIUtil.dip2px(context, 16.0f);
        int logoSize = UIUtil.dip2px(context, 10.0f);
        int leftMargin = UIUtil.dip2px(context, 33.0f);
        int rightMargin = leftMargin;
        int topMargin = UIUtil.dip2px(context, 27.0f);
        int bottomMargin = UIUtil.dip2px(context, 20.0f);
        Bitmap gpsBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_gps);
        topMargin += gpsBmp.getHeight();
        int maxHeight = BitmapUtils.getMaxCanvasHeight();
        int bitmapHeight = topMargin + bottomMargin;
        for (i = 0; i < itemscount; i++) {
            View childView = adapter.getView(i, null, listview);
            childView.measure(MeasureSpec.makeMeasureSpec(listview.getWidth(), CitySearchProvider.GET_SEARCH_RESULT_SUCC), MeasureSpec.makeMeasureSpec(0, 0));
            itemWidth = childView.getMeasuredWidth();
            int itemHeight = childView.getMeasuredHeight();
            childView.layout(0, 0, itemWidth, itemHeight);
            if (maxHeight < (allitemsheight + bitmapHeight) + itemHeight) {
                Log.e("ScreenShot", "view is too large");
            } else {
                Bitmap bitmap = Bitmap.createBitmap(itemWidth, itemHeight, Config.ARGB_8888);
                childView.draw(new Canvas(bitmap));
                bmps.add(bitmap);
                allitemsheight += itemHeight + lineH;
            }
        }
        allitemsheight -= lineH;
        Bitmap bigbitmap = Bitmap.createBitmap((itemWidth + rightMargin) + leftMargin, (allitemsheight + topMargin) + bottomMargin, Config.ARGB_8888);
        Canvas bigcanvas = new Canvas(bigbitmap);
        bigcanvas.drawColor(ContextCompat.getColor(context, R.color.settings_bkg));
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.background));
        Paint splitPaint = new Paint();
        splitPaint.setColor(ContextCompat.getColor(context, R.color.split));
        splitPaint.setStrokeWidth((float) lineH);
        Paint logoPaint = new Paint();
        logoPaint.setTextSize((float) titleSize);
        logoPaint.setColor(ContextCompat.getColor(context, R.color.city_search_item_text));
        logoPaint.setTextAlign(Align.LEFT);
        if (TextUtils.isEmpty(city)) {
            city = context.getString(R.string.current_location);
        }
        bigcanvas.drawBitmap(gpsBmp, (float) leftMargin, (float) ((topMargin - gpsBmp.getHeight()) + UIUtil.dip2px(context, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)), paint);
        String str = city;
        bigcanvas.drawText(str, (float) ((UIUtil.dip2px(context, 4.0f) + leftMargin) + gpsBmp.getWidth()), (float) topMargin, logoPaint);
        gpsBmp.recycle();
        int iHeight = topMargin;
        for (i = 0; i < bmps.size(); i++) {
            Bitmap bmp = (Bitmap) bmps.get(i);
            if (bmp != null) {
                bigcanvas.drawBitmap(bmp, (float) leftMargin, (float) iHeight, paint);
                iHeight += bmp.getHeight();
                if (i < bmps.size() - 1) {
                    bigcanvas.drawLine((float) leftMargin, (float) iHeight, (float) (leftMargin + itemWidth), (float) iHeight, splitPaint);
                }
                bmp.recycle();
            }
        }
        logoPaint.setTextSize((float) logoSize);
        Bitmap logoBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_share_oneplus);
        String oneplus_weatherStr = context.getString(R.string.share_from_oneplus_weather);
        float logoWidth = logoPaint.measureText(oneplus_weatherStr);
        bigcanvas.drawBitmap(logoBmp, ((float) ((leftMargin + itemWidth) - logoBmp.getWidth())) - logoWidth, (float) ((allitemsheight + topMargin) - logoBmp.getHeight()), paint);
        bigcanvas.drawText(oneplus_weatherStr, ((float) (leftMargin + itemWidth)) - logoWidth, (float) ((allitemsheight + topMargin) - UIUtil.dip2px(context, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)), logoPaint);
        logoBmp.recycle();
        for (i = 0; i < itemscount; i++) {
            adapter.getView(i, null, listview).setDrawingCacheEnabled(false);
        }
        return bigbitmap;
    }
}
