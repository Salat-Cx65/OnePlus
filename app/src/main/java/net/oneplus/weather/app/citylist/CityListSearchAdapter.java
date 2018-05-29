package net.oneplus.weather.app.citylist;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import net.oneplus.weather.model.CommonCandidateCity;
import net.oneplus.weather.util.StringUtils;

public class CityListSearchAdapter extends ArrayAdapter<CommonCandidateCity> implements Filterable {
    private List<CommonCandidateCity> mCandidateList;
    private Context mContext;

    public CityListSearchAdapter(Context context, List<CommonCandidateCity> candidateList) {
        super(context, 2131492899, candidateList);
        this.mCandidateList = new ArrayList();
        this.mContext = context;
        this.mCandidateList = candidateList;
    }

    public CommonCandidateCity getItem(int position) {
        return this.mCandidateList != null ? (CommonCandidateCity) this.mCandidateList.get(position) : null;
    }

    public int getCount() {
        return this.mCandidateList != null ? this.mCandidateList.size() : 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View result = convertView;
        if (result == null) {
            result = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.citylist_search, parent, false);
        }
        TextView tv = (TextView) result.findViewById(R.id.cityListSearchResult);
        CommonCandidateCity city = (CommonCandidateCity) this.mCandidateList.get(position);
        String name = city.getCityName(this.mContext);
        String cityInfo = StringUtils.EMPTY_STRING;
        if (!TextUtils.isEmpty(name)) {
            cityInfo = cityInfo + name;
        }
        if (!TextUtils.isEmpty(city.getCityProvince(this.mContext))) {
            if (!TextUtils.isEmpty(cityInfo)) {
                cityInfo = cityInfo + "  ";
            }
            cityInfo = cityInfo + city.getCityProvince(this.mContext);
        }
        if (!TextUtils.isEmpty(city.getCityCountry(this.mContext))) {
            if (!TextUtils.isEmpty(cityInfo)) {
                cityInfo = cityInfo + "  ";
            }
            cityInfo = cityInfo + city.getCityCountry(this.mContext);
        }
        tv.setText(cityInfo);
        return result;
    }

    public long getItemId(int position) {
        return (this.mCandidateList == null || this.mCandidateList.size() <= 0) ? 0 : (long) ((CommonCandidateCity) this.mCandidateList.get(position)).hashCode();
    }
}
