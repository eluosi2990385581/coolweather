package com.example.ccoolweather.total.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ccoolweather.total.model.City;
import com.example.ccoolweather.total.model.County;
import com.example.ccoolweather.total.model.Province;

import java.util.ArrayList;
import java.util.List;

public class CoolWeatherDB {
    public static final String DB_NAME="cool_weather";

    public static final int VERSION=1;
    private static CoolWeatherDB coolWeatherDB;
    private SQLiteDatabase db;

    private CoolWeatherDB(Context context)
    {
        CoolWeatherOpenHelper dbHelper=new CoolWeatherOpenHelper(context,DB_NAME,null,VERSION);
        db=dbHelper.getWritableDatabase();
    }

    public synchronized static CoolWeatherDB getInstance(Context context)
    {
        if(coolWeatherDB==null)coolWeatherDB=new CoolWeatherDB(context);
        return coolWeatherDB;
    }

    private void saveProvince(Province province)
    {
        if(province!=null)
        {
            ContentValues contentValues=new ContentValues();
            contentValues.put("province_name",province.getProvinceName());
            contentValues.put("province_code",province.getProvinceCode());
            db.insert("Province",null,contentValues);
        }
    }

    public List<Province> loadProvinces()
    {
        List<Province> list=new ArrayList<Province>();
        Cursor cursor=db.query("Province",null,null,
                null,null,null,null);
        if(cursor.moveToFirst())
        {
            do {
                Province province=new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            }while(cursor.moveToNext());
        }
        return list;
    }

    public void saveCity(City city)
    {
        if(city!=null)
        {
            ContentValues contentValues=new ContentValues();
            contentValues.put("province_id",city.getProvinceId());
            contentValues.put("city_name",city.getCityName());
            contentValues.put("city_code",city.getCityCode());
            db.insert("City",null,contentValues);
        }
    }

    public List<City> loadCities(int provinceId)
    {
        List<City> list=new ArrayList<City>();
        Cursor cursor=db.query("City",null,"province_id = ?",
                new String[] {String.valueOf(provinceId)},null,null,null);
        if(cursor.moveToFirst())
        {
            do {
                City city=new City();
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setId(cursor.getInt(cursor.getColumnIndex("id" )));
                city.setCityName(cursor.getString(cursor.getColumnIndex(" city_name" )));
                city.setProvinceId(provinceId);
                list.add(city);
            }while (cursor.moveToNext());
        }


        return list;
    }

    public void saveCounty(County county) {

        if (county != null) {

            ContentValues values = new ContentValues();

            values.put(" county_name", county.getCountyName());

            values.put(" county_code", county.getCountyCode());

            values.put(" city_id", county.getCityId());

            db.insert("County", null, values);

        }

    }

    public List<County> loadCounties(int cityId) {

        List<County> list = new ArrayList<County>();

        Cursor cursor = db.query("County", null, "city_id = ?",

                new String[] { String.valueOf(cityId) }, null, null, null);

        if (cursor.moveToFirst()) {

            do {

                County county = new County();

                county.setId(cursor.getInt(cursor.getColumnIndex("id")));

                county.setCountyName(cursor.getString(cursor

                        .getColumnIndex(" county_name")));

                county.setCountyCode(cursor.getString(cursor

                        .getColumnIndex(" county_code")));

                county.setCityId(cityId);

                list.add(county);

            } while (cursor.moveToNext());

        }

        return list;

    }
}
