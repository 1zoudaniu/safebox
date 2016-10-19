package safebox.yiye.com.safebox.fragment;


import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.Projection;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import safebox.yiye.com.safebox.R;
import safebox.yiye.com.safebox.beans.GuijiExpandGroupBean;
import safebox.yiye.com.safebox.beans.GuijiExpandItemBean;
import safebox.yiye.com.safebox.holder.JuijiExpandGroupHolder;
import safebox.yiye.com.safebox.utils.ToastUtil;

public class GuijiFirstFragment extends Fragment implements AdapterView.OnItemClickListener
     {
    private AMap childmMap;

    private List<GuijiExpandItemBean> childArray;
    private List<GuijiExpandGroupBean> groupArray;
    private ExpandableListView expandableListView;

    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient = new AMapLocationClient(getActivity());
    private AMapLocationClientOption mLocationOption;
    private ExpandableListViewaAdapter guijiListViewAdapter;
    private GeocodeSearch geocoderSearch;
    private Marker regeoMarker;
    private LatLonPoint latLonPoint;
    private LatLng latLng;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list_fragment, container, false);

        initData();

        return view;
    }

    private void initData() {
        groupArray = new ArrayList<GuijiExpandGroupBean>();

        for (int i = 0; i < 20; i++) {
            GuijiExpandGroupBean guijiExpandGroupBean = new GuijiExpandGroupBean();
            guijiExpandGroupBean.setCarid("沪C" + new Random().nextInt(10000));
            guijiExpandGroupBean.setCarstatus("行驶中");
            guijiExpandGroupBean.setCarkm(new Random().nextInt(200) + "km");
            guijiExpandGroupBean.setLatitude(31.216524f);
            guijiExpandGroupBean.setLongitude(121.61471f);
            groupArray.add(guijiExpandGroupBean);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        expandableListView = (ExpandableListView) getView().findViewById(R.id.expandableListView);
//        expandableListView.setTransitionEffect(new GrowEffect());

        guijiListViewAdapter = new ExpandableListViewaAdapter();
        expandableListView.setAdapter(guijiListViewAdapter);

        expandableListView.setOnItemClickListener(this);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                ToastUtil.startShort(getActivity(), "onGroupExpand  childmMap");
                guijiListViewAdapter.notifyDataSetChanged();

//                childmMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f).title("啦啦").snippet("知道啦"));

//                childmMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f).position(new LatLng(31.2396997086, 121.4995909338)).icon(BitmapDescriptorFactory
//                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))    // 将Marker设置为贴地显示，可以双指下拉看效果
//                        .setFlat(true));
//                RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
//                        GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
//                geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
//                geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
//                    /**
//                     * 逆地理编码回调
//                     */
//                    @Override
//                    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
//                        if (rCode == 1000) {
//                            if (result != null && result.getRegeocodeAddress() != null
//                                    && result.getRegeocodeAddress().getFormatAddress() != null) {
//                                childmMap.clear();
//                                String addressName = result.getRegeocodeAddress().getFormatAddress()
//                                        + "附近";
//                                childmMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                                        AMapUtil.convertToLatLng(latLonPoint), 15));
//                                regeoMarker.setPosition(AMapUtil.convertToLatLng(latLonPoint));
//                                regeoMarker.setAnchor(0.5f, 0.5f);
//                                MarkerOptions markOptiopns = new MarkerOptions();
//                                markOptiopns.position(latLng).icon(BitmapDescriptorFactory
//                                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))    // 将Marker设置为贴地显示，可以双指下拉看效果
//                                        .setFlat(true);
//                                TextView textView = new TextView(getActivity());
//                                textView.setText(groupArray.get(2).getCarid() +
//                                        "    " + groupArray.get(2).getCarstatus() +
//                                        "     " + groupArray.get(2).getCarkm() + "\n" + addressName);
//                                textView.setGravity(Gravity.LEFT);
//                                textView.setTextColor(Color.BLACK);
//                                textView.setBackgroundResource(R.drawable.custom_info_bubble);
//                                markOptiopns.icon(BitmapDescriptorFactory.fromView(textView));
//
//                                Marker marker = childmMap.addMarker(markOptiopns);
//                                marker.setAnchor(0.5f, 0.5f);
//
//                                jumpPoint(marker, latLng);
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
//
//                    }
//                });
            }
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                ToastUtil.startShort(getActivity(), "onGroupCollapse  childmMap");

                guijiListViewAdapter.notifyDataSetChanged();

            }
        });
    }


    /**
     * 方法必须重写
     * map的生命周期方法
     */
    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * 方法必须重写
     * map的生命周期方法
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     * map的生命周期方法
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
//        mapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        expandableListView.smoothScrollToPosition(position);
        expandableListView.setSelection(position);

        Float latitude = groupArray.get(position).getLatitude();
        Float longitude = groupArray.get(position).getLongitude();
        latLonPoint = new LatLonPoint(latitude, longitude);
        latLng = new LatLng(latitude, longitude);


    }
    //////////
    class ExpandableListViewaAdapter extends BaseExpandableListAdapter implements LocationSource, AMapLocationListener {

        /**
         * marker点击时跳动一下
         */
        public void jumpPoint(final Marker marker, final LatLng latLng) {
            final Handler handler = new Handler();
            final long start = SystemClock.uptimeMillis();
            Projection proj = childmMap.getProjection();
            Point startPoint = proj.toScreenLocation(latLng);
            startPoint.offset(0, -100);
            final LatLng startLatLng = proj.fromScreenLocation(startPoint);
            final long duration = 1500;
            final Interpolator interpolator = new BounceInterpolator();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    long elapsed = SystemClock.uptimeMillis() - start;
                    float t = interpolator.getInterpolation((float) elapsed
                            / duration);
                    double lng = t * latLng.longitude + (1 - t)
                            * startLatLng.longitude;
                    double lat = t * latLng.latitude + (1 - t)
                            * startLatLng.latitude;
                    marker.setPosition(new LatLng(lat, lng));
                    if (t < 1.0) {
                        handler.postDelayed(this, 16);
                    }
                }
            });

        }


        public ExpandableListViewaAdapter() {
        }

        /*-----------------Child */
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            // TODO Auto-generated method stub
            return childArray.get(groupPosition).getMapViewBean();
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            // TODO Auto-generated method stub
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            convertView = View.inflate(getContext(), R.layout.guiji_expand_childitem, null);

            if (childmMap == null) {
                childmMap = ((SupportMapFragment) getActivity().getSupportFragmentManager()
                        .findFragmentById(R.id.expan_item_mapview)).getMap();

                MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(31.2396997086, 121.4995909338))
                        .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))    // 将Marker设置为贴地显示，可以双指下拉看效果
                        .setFlat(true);

                TextView textView = new TextView(getActivity());
                textView.setText("上海市陆家嘴");
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(Color.BLACK);
                textView.setBackgroundResource(R.drawable.custom_info_bubble);
                markerOptions.icon(BitmapDescriptorFactory.fromView(textView));


                Marker marker = childmMap.addMarker(markerOptions);
//                jumpPoint(marker, new LatLng(31.2396997086, 121.4995909338));

                UiSettings uiSettings = childmMap.getUiSettings();

                // 自定义系统定位小蓝点
                MyLocationStyle myLocationStyle = new MyLocationStyle();
                myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                        .fromResource(R.drawable.location_marker));// 设置小蓝点的图标
                myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
                myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
                myLocationStyle.strokeWidth(12f);// 设置圆形的边框粗细
                childmMap.setMyLocationStyle(myLocationStyle);
                childmMap.setMyLocationRotateAngle(180);
                childmMap.setLocationSource(this);// 设置定位监听
                uiSettings.setMyLocationButtonEnabled(false); // 是否显示默认的定位按钮
                uiSettings.setTiltGesturesEnabled(false);// 设置地图是否可以倾斜
                uiSettings.setScaleControlsEnabled(true);// 设置地图默认的比例尺是否显示
                uiSettings.setZoomControlsEnabled(true);
                CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(
                        //15是缩放比例，0是倾斜度，30显示比例
                        new CameraPosition(new LatLng(31.2396997086, 121.4995909338), 13, 60, BitmapDescriptorFactory.HUE_ROSE));//这是地理位置，就是经纬度。
                childmMap.moveCamera(cameraUpdate);//定位的方法

            }
            return convertView;
        }


        @Override
        public int getChildrenCount(int groupPosition) {
            return 1;
        }

        /* ----------------------------Group */
        @Override
        public Object getGroup(int groupPosition) {
            return getGroup(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return groupArray.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {

            JuijiExpandGroupHolder groupHolder = null;
            GuijiExpandGroupBean guijiExpandGroupBean = groupArray.get(groupPosition);
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.guiji_expand_groupitem, null);
                groupHolder = new JuijiExpandGroupHolder();

                groupHolder.carid = (TextView) convertView.findViewById(R.id.tv_group_carid);
                groupHolder.carstatus = (TextView) convertView.findViewById(R.id.tv_group_status);
                groupHolder.carkm = (TextView) convertView.findViewById(R.id.tv_group_km);

                convertView.setTag(groupHolder);

            } else {
                groupHolder = (JuijiExpandGroupHolder) convertView.getTag();
            }
            groupHolder.carid.setText(guijiExpandGroupBean.getCarid());
            groupHolder.carstatus.setText(guijiExpandGroupBean.getCarstatus());
            groupHolder.carkm.setText(guijiExpandGroupBean.getCarkm());

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        @Override
        public void activate(OnLocationChangedListener listener) {
            mListener = listener;
            if (mlocationClient == null) {
                mlocationClient = new AMapLocationClient(getActivity());
                mLocationOption = new AMapLocationClientOption();
                //设置定位监听
                mlocationClient.setLocationListener(this);
                //设置为高精度定位模式
                mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                //设置定位参数
                mlocationClient.setLocationOption(mLocationOption);
                // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
                // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
                // 在定位结束后，在合适的生命周期调用onDestroy()方法
                // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
                mlocationClient.startLocation();
            }
        }

        @Override
        public void deactivate() {
            mListener = null;
            if (mlocationClient != null) {
                mlocationClient.stopLocation();
                mlocationClient.onDestroy();
            }
            mlocationClient = null;
        }

        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (mListener != null && amapLocation != null) {
                if (amapLocation != null
                        && amapLocation.getErrorCode() == 0) {

                    mListener.onLocationChanged(amapLocation);// 显示系统小蓝点

                } else {
                    String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                    Log.e("AmapErr", errText);
                }
            }
        }
    }
}

