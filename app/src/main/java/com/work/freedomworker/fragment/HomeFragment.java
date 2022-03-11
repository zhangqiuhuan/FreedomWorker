package com.work.freedomworker.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.work.freedomworker.R;
import com.work.freedomworker.adapter.CityListAdapter;
import com.work.freedomworker.adapter.HomeBrokerListAdapter;
import com.work.freedomworker.adapter.HomeMenuListAdapter;
import com.work.freedomworker.adapter.HomeTaskSquareListAdapter;
import com.work.freedomworker.adapter.LevelListAdapter;
import com.work.freedomworker.adapter.ProvinceListAdapter;
import com.work.freedomworker.base.BaseLazyLoadFragment;
import com.work.freedomworker.model.BannerModel;
import com.work.freedomworker.model.BaseModel;
import com.work.freedomworker.model.CityModel;
import com.work.freedomworker.model.HomeBrokerModel;
import com.work.freedomworker.model.LevelModel;
import com.work.freedomworker.model.MenuModel;
import com.work.freedomworker.model.HomeTaskSquareModel;
import com.work.freedomworker.model.ProvinceModel;
import com.work.freedomworker.net.ApiUtils;
import com.work.freedomworker.utils.GsonUtil;
import com.work.freedomworker.utils.LoadDialog;
import com.kelin.banner.BannerEntry;
import com.kelin.banner.view.BannerView;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class HomeFragment extends BaseLazyLoadFragment {
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.tv_search_keyword)
    TextView tvSearchKeyword;
    @BindView(R.id.bv_advertising)
    BannerView bvAdvertising;

    @BindView(R.id.recycler_menu)
    RecyclerView recyclerMenu;

    @BindView(R.id.ll_task_square)
    LinearLayout llTaskSquare;
    @BindView(R.id.tv_task_square)
    TextView tvTaskSquare;
    @BindView(R.id.view_task_square)
    View viewTaskSquare;

    @BindView(R.id.ll_broker)
    LinearLayout llBroker;
    @BindView(R.id.tv_broker)
    TextView tvBroker;
    @BindView(R.id.view_broker)
    View viewBroker;

    @BindView(R.id.ll_task_square_info)
    LinearLayout llTaskSquareInfo;
    @BindView(R.id.ll_broker_info)
    LinearLayout llBrokerInfo;

    @BindView(R.id.tv_near)
    TextView tvNear;
    @BindView(R.id.tv_new)
    TextView tvNew;
    @BindView(R.id.ll_shareearn)
    LinearLayout llShareearn;
    @BindView(R.id.tv_shareearn)
    TextView tvShareearn;
    @BindView(R.id.iv_shareearn)
    ImageView ivShareearn;
    @BindView(R.id.ll_area)
    LinearLayout llArea;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.iv_area)
    ImageView ivArea;

    @BindView(R.id.tv_broker_near)
    TextView tvBrokerNear;
    @BindView(R.id.tv_broker_new)
    TextView tvBrokerNew;
    @BindView(R.id.tv_broker_recommend)
    TextView tvBrokerRecommend;

    @BindView(R.id.ll_broker_area)
    LinearLayout llBrokerArea;
    @BindView(R.id.tv_broker_area)
    TextView tvBrokerArea;
    @BindView(R.id.iv_broker_area)
    ImageView ivBrokerArea;
    @BindView(R.id.ll_broker_level)
    LinearLayout llBrokerLevel;
    @BindView(R.id.tv_broker_level)
    TextView tvBrokerLevel;
    @BindView(R.id.iv_broker_level)
    ImageView ivBrokerLevel;

    @BindView(R.id.recycler_task_square)
    RecyclerView recyclerTaskSquare;

    @BindView(R.id.recycler_broker)
    RecyclerView recyclerBroker;

    //板块
    HomeMenuListAdapter mainListAdapter;
    List<MenuModel.MenuBean> mainModelList = new ArrayList<>();

    //任务广场
    HomeTaskSquareListAdapter homeTaskSquareListAdapter;
    List<HomeTaskSquareModel.HomeTaskSquareBean.HomeTaskSquareEntry> taskSquareNearEntryList = new ArrayList<>();

    //经纪人
    HomeBrokerListAdapter homeBrokerListAdapter;
    List<HomeBrokerModel.HomeBrokerBean.HomeBrokerEntry> brokerEntryList = new ArrayList<>();

    private int listType = 1;//1：任务广场；2：经纪人

    //省份
    ProvinceListAdapter provinceListAdapter;
    List<ProvinceModel.ProvinceBean> provinceBeanList = new ArrayList<>();
    private int provinceIndex;//选中省份下标

    //城市
    CityListAdapter cityListAdapter;
    List<CityModel.CityBean> cityBeanList = new ArrayList<>();
    private int cityIndex;//选中城市下标

    LevelListAdapter levelListAdapter;
    List<LevelModel.LevelBean> levelBeanList = new ArrayList<>();
    private int levelIndex;

    private double lng = 0;
    private double lat = 0;

    private String provinceName;//省份
    private String cityName;//城市名
    private String level;//等级

    public static HomeFragment newInstance(String text) {

        Bundle args = new Bundle();
        args.putString("text", text);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onBaseFragmentVisibleChange(boolean isVisible) {

    }

    @Override
    protected void initView() {
        //设置显示任务广场下面view
        setViewTaskSquareHeight();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkPermission = mContext.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS}, 1);
            } else {
                jingwd();
            }
        } else {
            jingwd();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //获取经纬度
            jingwd();
        }
    }


    @Override
    protected void initData() {
        getBannerPicData();//获取banner
        getMenuData();//板块列表数据

        provinceListAdapter = new ProvinceListAdapter(mContext, provinceBeanList);

        cityListAdapter = new CityListAdapter(mContext, cityBeanList);

        levelListAdapter = new LevelListAdapter(mContext, levelBeanList);

        getProvinceData();//获取省份列表

        getLevelData();//获取等级列表


        mainListAdapter = new HomeMenuListAdapter(mContext, mainModelList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 5);//第二个参数为网格的列数
        recyclerMenu.setNestedScrollingEnabled(false);
        recyclerMenu.setLayoutManager(gridLayoutManager);
        recyclerMenu.setAdapter(mainListAdapter);

        homeTaskSquareListAdapter = new HomeTaskSquareListAdapter(mContext, taskSquareNearEntryList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerTaskSquare.setNestedScrollingEnabled(false);
        recyclerTaskSquare.setLayoutManager(layoutManager);
        recyclerTaskSquare.setAdapter(homeTaskSquareListAdapter);

        homeBrokerListAdapter = new HomeBrokerListAdapter(mContext, brokerEntryList);
        LinearLayoutManager layoutManagerbroker = new LinearLayoutManager(mContext);
        recyclerBroker.setNestedScrollingEnabled(false);
        recyclerBroker.setLayoutManager(layoutManagerbroker);
        recyclerBroker.setAdapter(homeBrokerListAdapter);
    }

    /**
     * 获取任务广场附近、最新、分享赚列表数据
     */
    private void getBrokerData(int type) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("lng", lng);//求职者经度
        paramsMap.put("lat", lat);//求职者纬度
//        paramsMap.put("province", label);//省份code
//        paramsMap.put("city", label);//城市code
//        paramsMap.put("position", label);//岗位

        if (null == paramsMap) {
            paramsMap = new HashMap<>();
        }
        Log.e("broker/recommend--", JSON.toJSONString(paramsMap) + " method：" + type);
        String apiurl = "";
        if (type == 1) {
            apiurl = "index/search_bro/recommend";
        } else if (type == 2) {
            apiurl = "index/search_bro/near";
        } else {
            apiurl = "index/search_bro";
        }
        ApiUtils.getInstance().get(apiurl, paramsMap, new StringCallback() {
            // final LoadDialog loadDialog = new LoadDialog();

            @Override
            public void onStart(Request<String, ? extends Request> request) {
                super.onStart(request);
                // loadDialog.showProgressDialog(mContext, "请稍后....");
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Log.e("TAG", response.code() + "");
                // loadDialog.closeProgressDialog();
            }

            @Override
            public void onSuccess(Response<String> response) {
                // loadDialog.closeProgressDialog();
                Log.e("broker/recommend", response.body());
                BaseModel baseModel = GsonUtil.parseJson(response.body(), BaseModel.class);

                try {
                    if (baseModel.getCode() == 200) {
                        HomeBrokerModel homeBrokerModel = GsonUtil.parseJson(response.body(), HomeBrokerModel.class);

                        if (brokerEntryList.size() > 0) {
                            brokerEntryList.clear();
                        }
                        if (homeBrokerModel.getData().getData().size() > 0) {
                            brokerEntryList.addAll(homeBrokerModel.getData().getData());
                        }
                        Log.e("TAG22", brokerEntryList.size() + "");
                        homeBrokerListAdapter.notifyDataSetChanged();


                    } else {
                        showToast(getResources().getString(R.string.broker_callback_failed));
                        return;
                    }
                } catch (Exception e) {
                    showToast(getResources().getString(R.string.data_analysis_failed));
                }

            }
        });

    }

    /**
     * 获取任务广场附近、最新、分享赚列表数据
     */
    private void getTaskSquareNearData(int type) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("lng", lng);//求职者经度
        paramsMap.put("lat", lat);//求职者纬度
//        paramsMap.put("province", label);//省份code
//        paramsMap.put("city", label);//城市code
//        paramsMap.put("position", label);//岗位

        if (null == paramsMap) {
            paramsMap = new HashMap<>();
        }
        Log.e("square/near--", JSON.toJSONString(paramsMap) + " method：" + type);
        String apiurl = "";
        if (type == 1) {
            apiurl = "index/square/near";
        } else if (type == 2) {
            apiurl = "index/square";
        } else {
            apiurl = "index/square/share_earn";
        }
        ApiUtils.getInstance().get(apiurl, paramsMap, new StringCallback() {
            //final LoadDialog loadDialog = new LoadDialog();

            @Override
            public void onStart(Request<String, ? extends Request> request) {
                super.onStart(request);
                //  loadDialog.showProgressDialog(mContext, "请稍后....");
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Log.e("TAG", response.code() + "");
                //loadDialog.closeProgressDialog();
            }

            @Override
            public void onSuccess(Response<String> response) {
                //  loadDialog.closeProgressDialog();
                Log.e("square/near", response.body());
                BaseModel baseModel = GsonUtil.parseJson(response.body(), BaseModel.class);

                try {
                    if (baseModel.getCode() == 200) {
                        HomeTaskSquareModel homeTaskSquareModel = GsonUtil.parseJson(response.body(), HomeTaskSquareModel.class);

                        if (taskSquareNearEntryList.size() > 0) {
                            taskSquareNearEntryList.clear();
                        }
                        if (homeTaskSquareModel.getData().getData().size() > 0) {
                            taskSquareNearEntryList.addAll(homeTaskSquareModel.getData().getData());
                        }
                        Log.e("TAG22", taskSquareNearEntryList.size() + "");
                        homeTaskSquareListAdapter.notifyDataSetChanged();


                    } else {
                        showToast(getResources().getString(R.string.tasksquare_callback_failed));
                        return;
                    }
                } catch (Exception e) {
                    showToast(getResources().getString(R.string.data_analysis_failed));
                }

            }
        });

    }

    /**
     * 获取板块列表数据
     */
    private void getMenuData() {
        Map<String, Object> paramsMap = new HashMap<>();

        if (null == paramsMap) {
            paramsMap = new HashMap<>();
        }
        Log.e("category--", JSON.toJSONString(paramsMap));
        ApiUtils.getInstance().get("index/category", paramsMap, new StringCallback() {
            final LoadDialog loadDialog = new LoadDialog();

            @Override
            public void onStart(Request<String, ? extends Request> request) {
                super.onStart(request);
                loadDialog.showProgressDialog(mContext, "请稍后....");
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Log.e("TAG", response.code() + "");
                loadDialog.closeProgressDialog();
            }

            @Override
            public void onSuccess(Response<String> response) {
                loadDialog.closeProgressDialog();
                Log.e("category", response.body());
                BaseModel baseModel = GsonUtil.parseJson(response.body(), BaseModel.class);

                try {
                    if (baseModel.getCode() == 200) {
                        MenuModel menuModel = GsonUtil.parseJson(response.body(), MenuModel.class);
                        if (mainModelList.size() > 0) {
                            mainModelList.clear();
                        }
                        if (menuModel.getData().size() > 0) {
                            mainModelList.addAll(menuModel.getData());
                        }

                        mainListAdapter.notifyDataSetChanged();


                    } else {
                        showToast(getResources().getString(R.string.menu_callback_failed));
                        return;
                    }
                } catch (Exception e) {
                    showToast(getResources().getString(R.string.data_analysis_failed));
                }

            }
        });

    }

    /**
     * 获取广告位图片
     */
    private void getBannerPicData() {
        Map<String, Object> paramsMap = new HashMap<>();

        if (null == paramsMap) {
            paramsMap = new HashMap<>();
        }
        Log.e("banner--", JSON.toJSONString(paramsMap));
        ApiUtils.getInstance().get("index/banner", paramsMap, new StringCallback() {
            final LoadDialog loadDialog = new LoadDialog();

            @Override
            public void onStart(Request<String, ? extends Request> request) {
                super.onStart(request);
                // loadDialog.showProgressDialog(mContext, "请稍后....");
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Log.e("TAG", response.code() + "");
                // loadDialog.closeProgressDialog();
            }

            @Override
            public void onSuccess(Response<String> response) {
                // loadDialog.closeProgressDialog();
                Log.e("banner", response.body());
                BaseModel baseModel = GsonUtil.parseJson(response.body(), BaseModel.class);

                try {
                    if (baseModel.getCode() == 200) {
                        BannerModel bannerModel = GsonUtil.parseJson(response.body(), BannerModel.class);
                        List<BannerModel.BannerBean> list = new ArrayList<>();
                        if (bannerModel != null) {
                            if (list.size() > 0) {
                                list.clear();
                            }
                            if (bannerModel.getData().size() > 0) {
                                list.addAll(bannerModel.getData());
                            }
                        }
                        Log.e("banner", JSON.toJSONString(list));
                        //设置数据源，默认会启动轮播。如果不想启动轮播-bannerView.setEntries(entries, false);
                        bvAdvertising.setEntries(list);
                    } else {
                        showToast(getResources().getString(R.string.banner_callback_failed));
                        return;
                    }
                } catch (Exception e) {
                    showToast(getResources().getString(R.string.data_analysis_failed));
                }

            }
        });

    }


    /**
     * 获取省份列表数据
     */
    private void getProvinceData() {
        Map<String, Object> paramsMap = new HashMap<>();

        if (null == paramsMap) {
            paramsMap = new HashMap<>();
        }
        Log.e("privince--", JSON.toJSONString(paramsMap));
        ApiUtils.getInstance().get("index/square/privince", paramsMap, new StringCallback() {
            final LoadDialog loadDialog = new LoadDialog();

            @Override
            public void onStart(Request<String, ? extends Request> request) {
                super.onStart(request);
                //loadDialog.showProgressDialog(mContext, "请稍后....");
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Log.e("TAG", response.code() + "");
                //loadDialog.closeProgressDialog();
            }

            @Override
            public void onSuccess(Response<String> response) {
                //  loadDialog.closeProgressDialog();
                Log.e("privince", response.body());
                BaseModel baseModel = GsonUtil.parseJson(response.body(), BaseModel.class);

                try {
                    if (baseModel.getCode() == 200) {
                        ProvinceModel provinceModel = GsonUtil.parseJson(response.body(), ProvinceModel.class);

                        if (provinceBeanList.size() > 0) {
                            provinceBeanList.clear();
                        }
                        if (provinceModel.getData().size() > 0) {
                            provinceBeanList.addAll(provinceModel.getData());
                            //默认获取第一个省份的城市
                            getCityData(provinceBeanList.get(0).getCode());
                        }
                        provinceListAdapter.notifyDataSetChanged();
                    } else {
                        showToast(getResources().getString(R.string.province_callback_failed));
                        return;
                    }
                } catch (Exception e) {
                    showToast(getResources().getString(R.string.data_analysis_failed));
                }

            }
        });

    }

    /**
     * 获取城市列表数据
     */
    private void getCityData(int provincecode) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("province_code", provincecode);
        if (null == paramsMap) {
            paramsMap = new HashMap<>();
        }
        Log.e("city--", JSON.toJSONString(paramsMap));
        ApiUtils.getInstance().get("index/square/city", paramsMap, new StringCallback() {
            // final LoadDialog loadDialog = new LoadDialog();

            @Override
            public void onStart(Request<String, ? extends Request> request) {
                super.onStart(request);
                // loadDialog.showProgressDialog(mContext, "请稍后....");
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Log.e("TAG", response.code() + "");
                //loadDialog.closeProgressDialog();
            }

            @Override
            public void onSuccess(Response<String> response) {
                // loadDialog.closeProgressDialog();
                Log.e("city", response.body());
                BaseModel baseModel = GsonUtil.parseJson(response.body(), BaseModel.class);

                try {
                    if (baseModel.getCode() == 200) {
                        CityModel cityModel = GsonUtil.parseJson(response.body(), CityModel.class);

                        if (cityBeanList.size() > 0) {
                            cityBeanList.clear();
                        }
                        if (cityModel.getData().size() > 0) {
                            CityModel.CityBean cityBean = new CityModel.CityBean();
                            cityBean.setCode(000);
                            cityBean.setName("全部");
                            cityBeanList.add(cityBean);
                            cityBeanList.addAll(cityModel.getData());
                        }
                        cityListAdapter.notifyDataSetChanged();
                    } else {
                        showToast(getResources().getString(R.string.province_callback_failed));
                        return;
                    }
                } catch (Exception e) {
                    showToast(getResources().getString(R.string.data_analysis_failed));
                }

            }
        });

    }

    /**
     * 获取等级列表数据
     */
    private void getLevelData() {
        Map<String, Object> paramsMap = new HashMap<>();

        if (null == paramsMap) {
            paramsMap = new HashMap<>();
        }
        Log.e("level--", JSON.toJSONString(paramsMap));
        ApiUtils.getInstance().get("index/search_bro/level", paramsMap, new StringCallback() {
            final LoadDialog loadDialog = new LoadDialog();

            @Override
            public void onStart(Request<String, ? extends Request> request) {
                super.onStart(request);
                //loadDialog.showProgressDialog(mContext, "请稍后....");
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Log.e("TAG", response.code() + "");
                //loadDialog.closeProgressDialog();
            }

            @Override
            public void onSuccess(Response<String> response) {
                //  loadDialog.closeProgressDialog();
                Log.e("level", response.body());
                BaseModel baseModel = GsonUtil.parseJson(response.body(), BaseModel.class);

                try {
                    if (baseModel.getCode() == 200) {
                        LevelModel levelModel = GsonUtil.parseJson(response.body(), LevelModel.class);


                        if (levelBeanList.size() > 0) {
                            levelBeanList.clear();
                        }
                        if (levelModel.getData().size() > 0) {
                            LevelModel.LevelBean levelBean = new LevelModel.LevelBean();
                            levelBean.setLevel(0);
                            levelBean.setLevel_full_exp(0);
                            levelBean.setLevel_name("不限");
                            levelBeanList.add(levelBean);
                            levelBeanList.addAll(levelModel.getData());

                        }
                        levelListAdapter.notifyDataSetChanged();
                    } else {
                        showToast(getResources().getString(R.string.level_callback_failed));
                        return;
                    }
                } catch (Exception e) {
                    showToast(getResources().getString(R.string.data_analysis_failed));
                }

            }
        });

    }

    @Override
    protected void initListener() {
        bvAdvertising.setOnPageClickListener(new BannerView.OnPageClickListener() {
            @Override
            public void onPageClick(BannerEntry entry, int index) {
                showToast("点击了"+index+entry.getValue());
            }
        });

        llTaskSquare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewTaskSquare.setVisibility(View.VISIBLE);
                viewBroker.setVisibility(View.GONE);
                setViewTaskSquareHeight();
                tvTaskSquare.setTextColor(getResources().getColor(R.color.maincolor));
                tvBroker.setTextColor(getResources().getColor(R.color.defaultcolor));

                brokerEntryList.clear();
                homeBrokerListAdapter.notifyDataSetChanged();
                llBrokerInfo.setVisibility(View.GONE);
                getTaskSquareNearData(1);
                llTaskSquareInfo.setVisibility(View.VISIBLE);
                tvSearchKeyword.setText("职位");
                listType = 1;

                //清空经纪人筛选
               clearFiltrate();

            }
        });
        llBroker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewBroker.setVisibility(View.VISIBLE);
                viewTaskSquare.setVisibility(View.GONE);
                setViewBrokerHeight();
                tvTaskSquare.setTextColor(getResources().getColor(R.color.defaultcolor));
                tvBroker.setTextColor(getResources().getColor(R.color.maincolor));

                taskSquareNearEntryList.clear();
                homeTaskSquareListAdapter.notifyDataSetChanged();
                llTaskSquareInfo.setVisibility(View.GONE);
                getBrokerData(1);
                llBrokerInfo.setVisibility(View.VISIBLE);
                tvSearchKeyword.setText("经纪人");
                listType = 2;
                //清空任务广场筛选
                clearFiltrate();
            }
        });

        tvNear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvNear.setTextColor(getResources().getColor(R.color.maincolor));
                tvNew.setTextColor(getResources().getColor(R.color.gray99));
                tvShareearn.setTextColor(getResources().getColor(R.color.gray99));
                ivShareearn.setImageResource(R.mipmap.ic_fire_unfocus);

                getTaskSquareNearData(1);
            }
        });
        tvNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvNear.setTextColor(getResources().getColor(R.color.gray99));
                tvNew.setTextColor(getResources().getColor(R.color.maincolor));
                tvShareearn.setTextColor(getResources().getColor(R.color.gray99));
                ivShareearn.setImageResource(R.mipmap.ic_fire_unfocus);
                getTaskSquareNearData(2);
            }
        });

        llShareearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvNear.setTextColor(getResources().getColor(R.color.gray99));
                tvNew.setTextColor(getResources().getColor(R.color.gray99));
                tvShareearn.setTextColor(getResources().getColor(R.color.maincolor));
                ivShareearn.setImageResource(R.mipmap.ic_fire_focus);
                getTaskSquareNearData(3);
            }
        });

        tvBrokerRecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvBrokerRecommend.setTextColor(getResources().getColor(R.color.maincolor));
                tvBrokerNear.setTextColor(getResources().getColor(R.color.gray99));
                tvBrokerNew.setTextColor(getResources().getColor(R.color.gray99));

                getBrokerData(1);
            }
        });
        tvBrokerNear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvBrokerRecommend.setTextColor(getResources().getColor(R.color.gray99));
                tvBrokerNear.setTextColor(getResources().getColor(R.color.maincolor));
                tvBrokerNew.setTextColor(getResources().getColor(R.color.gray99));

                getBrokerData(2);
            }
        });
        tvBrokerNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvBrokerRecommend.setTextColor(getResources().getColor(R.color.gray99));
                tvBrokerNear.setTextColor(getResources().getColor(R.color.gray99));
                tvBrokerNew.setTextColor(getResources().getColor(R.color.maincolor));

                getBrokerData(3);
            }
        });

        llArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAreaDialog();
            }
        });
        llBrokerArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAreaDialog();
            }
        });
        llBrokerLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLevelDialog();
            }
        });
    }

    private void clearFiltrate() {
        if (listType==2) {
            provinceIndex = -1;
            cityIndex = -1;
            tvBrokerArea.setText("区域");
            tvBrokerArea.setTextColor(getResources().getColor(R.color.gray99));
            llBrokerArea.setBackgroundResource(R.drawable.bg_area_unselect);
            ivBrokerArea.setImageResource(R.mipmap.ic_arrows_down);
            levelIndex = 0;
            llBrokerLevel.setBackgroundResource(R.drawable.bg_area_unselect);
            tvBrokerLevel.setTextColor(getResources().getColor(R.color.gray99));
            tvBrokerLevel.setText("等级");
            ivBrokerLevel.setImageResource(R.mipmap.ic_arrows_down);

            provinceListAdapter.setSelectindex(provinceIndex);
            provinceListAdapter.notifyDataSetChanged();
            cityListAdapter.setSelectindex(cityIndex);
            cityListAdapter.notifyDataSetChanged();
            levelListAdapter.setSelectindex(levelIndex);
            levelListAdapter.notifyDataSetChanged();
        }else{
            provinceIndex = -1;
            cityIndex = -1;
            tvArea.setText("区域");
            tvArea.setTextColor(getResources().getColor(R.color.gray99));
            llArea.setBackgroundResource(R.drawable.bg_area_unselect);
            ivArea.setImageResource(R.mipmap.ic_arrows_down);

            provinceListAdapter.setSelectindex(provinceIndex);
            provinceListAdapter.notifyDataSetChanged();
            cityListAdapter.setSelectindex(cityIndex);
            cityListAdapter.notifyDataSetChanged();
        }
    }

    //初始化并弹出区域对话框方法
    private void showAreaDialog() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_area_select, null, false);
        final AlertDialog dialog = new AlertDialog.Builder(mContext).setView(view).create();


        RecyclerView recyclerProvince = view.findViewById(R.id.recycler_dialog_area_province);
        RecyclerView recyclerCity = view.findViewById(R.id.recycler_dialog_area_city);
        ImageView ibtnGoback = view.findViewById(R.id.iv_dialog_area_goback);

        Button btnClear = view.findViewById(R.id.btn_dialog_area_clear);
        Button btnConfirm = view.findViewById(R.id.btn_dialog_area_confirm);


        // provinceListAdapter = new ProvinceListAdapter(mContext, provinceBeanList);
        recyclerProvince.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerProvince.setAdapter(provinceListAdapter);

        // cityListAdapter = new CityListAdapter(mContext, cityBeanList);
        recyclerCity.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerCity.setAdapter(cityListAdapter);

        provinceListAdapter.setOnItemClick(new ProvinceListAdapter.OnProvinceItemClick() {
            @Override
            public void onItemClick(int position) {
                if (provinceIndex == position) {
                    return;
                }
                provinceIndex = position;
                provinceListAdapter.setSelectindex(position);
                provinceListAdapter.notifyDataSetChanged();

                cityIndex=0;//默认选第一个全部
                cityListAdapter.setSelectindex(cityIndex);
                //选选择省份，重新加载其省份的城市
                getCityData(provinceBeanList.get(position).getCode());

            }
        });

        cityListAdapter.setOnItemClick(new CityListAdapter.OnCityItemClick() {
            @Override
            public void onItemClick(int position) {
                if (cityIndex == position) {
                    return;
                }
                cityIndex = position;
                cityListAdapter.setSelectindex(position);
                cityListAdapter.notifyDataSetChanged();

            }
        });

        ibtnGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (levelIndex==0) {
                    llBrokerLevel.setBackgroundResource(R.drawable.bg_area_unselect);
                    tvBrokerLevel.setText("等级");
                    ivBrokerLevel.setImageResource(R.mipmap.ic_arrows_down);
                }else {
                    llBrokerLevel.setBackgroundResource(R.drawable.bg_area_select);
                    tvBrokerLevel.setText(levelBeanList.get(levelIndex).getLevel_name());
                    ivBrokerLevel.setImageResource(R.mipmap.ic_arrow_down_focus);
                }


                if (listType == 1) {//任务广场的筛选
                    if (provinceIndex>=0) {
                        if (cityIndex == 0) {
                            //说明城市选择是全部，筛选显示就是省份
                            tvArea.setText(provinceBeanList.get(provinceIndex).getName());
                            tvArea.setTextColor(getResources().getColor(R.color.maincolor));
                            llArea.setBackgroundResource(R.drawable.bg_area_select);
                            ivArea.setImageResource(R.mipmap.ic_arrow_down_focus);
                        } else {
                            llArea.setBackgroundResource(R.drawable.bg_area_select);
                            tvArea.setTextColor(getResources().getColor(R.color.maincolor));
                            ivArea.setImageResource(R.mipmap.ic_arrow_down_focus);
                            tvArea.setText(cityBeanList.get(cityIndex).getName());
                        }
                    }else{
                        tvArea.setText("区域");
                        tvArea.setTextColor(getResources().getColor(R.color.gray99));
                        llArea.setBackgroundResource(R.drawable.bg_area_unselect);
                        ivArea.setImageResource(R.mipmap.ic_arrows_down);
                    }
                } else {

                    if (provinceIndex>=0) {
                        if (cityIndex == 0) {
                            //说明城市选择是全部，筛选显示就是省份
                            tvBrokerArea.setText(provinceBeanList.get(provinceIndex).getName());
                            tvBrokerArea.setTextColor(getResources().getColor(R.color.maincolor));
                            llBrokerArea.setBackgroundResource(R.drawable.bg_area_select);
                            ivBrokerArea.setImageResource(R.mipmap.ic_arrow_down_focus);
                        } else {
                            llBrokerArea.setBackgroundResource(R.drawable.bg_area_select);
                            tvBrokerArea.setTextColor(getResources().getColor(R.color.maincolor));
                            ivBrokerArea.setImageResource(R.mipmap.ic_arrow_down_focus);
                            tvBrokerArea.setText(cityBeanList.get(cityIndex).getName());
                        }
                    }else{
                        tvBrokerArea.setText("区域");
                        tvBrokerArea.setTextColor(getResources().getColor(R.color.gray99));
                        llBrokerArea.setBackgroundResource(R.drawable.bg_area_unselect);
                        ivBrokerArea.setImageResource(R.mipmap.ic_arrows_down);
                    }
                }
            }
        });


        Window win = dialog.getWindow();
        win.setGravity(Gravity.BOTTOM);   // 这里控制弹出的位置
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        // lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = (int) (d.heightPixels * 0.8); // 宽度设置为屏幕的0.8

        //lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_area_select);
        win.setAttributes(lp);
        dialog.show();
    }


    //初始化并弹出等级对话框方法
    private void showLevelDialog() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_level_select, null, false);
        final AlertDialog dialog = new AlertDialog.Builder(mContext).setView(view).create();


        RecyclerView recycleLevel = view.findViewById(R.id.recycler_dialog_level);
        ImageView ibtnGoback = view.findViewById(R.id.ivbtn_dialog_level_goback);

        Button btnClear = view.findViewById(R.id.btn_dialog_level_clear);
        Button btnConfirm = view.findViewById(R.id.btn_dialog_level_confirm);

        // cityListAdapter = new CityListAdapter(mContext, cityBeanList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);//第二个参数为网格的列数
        recycleLevel.setLayoutManager(gridLayoutManager);
        recycleLevel.setAdapter(levelListAdapter);

        levelListAdapter.setOnItemClick(new LevelListAdapter.OnLevelItemClick() {
            @Override
            public void onItemClick(int position) {
                if (provinceIndex == position) {
                    return;
                }
                levelIndex = position;
                levelListAdapter.setSelectindex(position);
                levelListAdapter.notifyDataSetChanged();


            }
        });

        ibtnGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                if (levelIndex==0) {
                    llBrokerLevel.setBackgroundResource(R.drawable.bg_area_unselect);
                    tvBrokerLevel.setTextColor(getResources().getColor(R.color.gray99));
                    tvBrokerLevel.setText("等级");
                    ivBrokerLevel.setImageResource(R.mipmap.ic_arrows_down);
                }else {
                    llBrokerLevel.setBackgroundResource(R.drawable.bg_area_select);
                    tvBrokerLevel.setText(levelBeanList.get(levelIndex).getLevel_name());
                    tvBrokerLevel.setTextColor(getResources().getColor(R.color.maincolor));
                    ivBrokerLevel.setImageResource(R.mipmap.ic_arrow_down_focus);
                }

            }
        });


        Window win = dialog.getWindow();
        win.setGravity(Gravity.BOTTOM);   // 这里控制弹出的位置
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        // lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = (int) (d.heightPixels * 0.5); // 宽度设置为屏幕的0.8

        //lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_area_select);
        win.setAttributes(lp);
        dialog.show();
    }

    private void setViewTaskSquareHeight() {
        //        使用 view的  post 方式， 用于获取它的宽度和高度
        tvTaskSquare.post(new Runnable() {
            @Override
            public void run() {
//                获取要 textview的宽度
                tvTaskSquare.measure(0, 0);
                int width = tvTaskSquare.getWidth();
//                获取 线 的 layout参数
                ViewGroup.LayoutParams lineParams = viewTaskSquare.getLayoutParams();
//                将 textview 的宽度  设置给  线的
                lineParams.width = width;
                viewTaskSquare.setLayoutParams(lineParams);
            }
        });
    }

    private void setViewBrokerHeight() {
        //        使用 view的  post 方式， 用于获取它的宽度和高度
        tvBroker.post(new Runnable() {
            @Override
            public void run() {
//                获取要 textview的宽度
                tvBroker.measure(0, 0);
                int width = tvBroker.getWidth();
//                获取 线 的 layout参数
                ViewGroup.LayoutParams lineParams = viewBroker.getLayoutParams();
//                将 textview 的宽度  设置给  线的
                lineParams.width = width;
                viewBroker.setLayoutParams(lineParams);
            }
        });
    }


    /***经纬度获取*****/
    private LocationManager locationManager;
    private String locationProvider;

    // 经纬度获取
    private void jingwd() {
        // 获取地理位置管理器
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
// 获取所有可用的位置提供器
        List providers = locationManager.getProviders(true);
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
// 如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
// 如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(mContext, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
            return;
        }
// 获取Location
        Location location = locationManager
                .getLastKnownLocation(locationProvider);
        if (location != null) {
// 不为空,显示地理位置经纬度
            showLocation(location);
        }
// 监视地理位置变化
        locationManager.requestLocationUpdates(locationProvider, 3000, 1,
                locationListener);
    }

    /**
     * 显示地理位置经度和纬度信息
     *
     * @param location
     */
    private void showLocation(Location location) {
        lng = location.getLongitude();
        lat = location.getLatitude();

        if (lng == 0 && lat == 0) {
            showToast("获取定位失败");
        } else {
            getTaskSquareNearData(1);//获取任务广场附近列表
        }

    }

    /**
     * LocationListern监听器 参数：地理位置提供器、监听位置变化的时间间隔、位置变化的距离间隔、LocationListener监听器
     */
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onStatusChanged(String provider, int status, Bundle arg2) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override

        public void onLocationChanged(Location location) {
            // 如果位置发生变化,重新显示
            //showLocation(location);

        }

    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
// 移除监听器
            locationManager.removeUpdates(locationListener);
        }

    }

}
