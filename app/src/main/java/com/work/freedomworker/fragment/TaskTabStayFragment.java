package com.work.freedomworker.fragment;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.work.freedomworker.R;
import com.work.freedomworker.adapter.TaskListAdapter;
import com.work.freedomworker.base.BaseLazyLoadFragment;
import com.work.freedomworker.model.BaseModel;
import com.work.freedomworker.model.LoginTokenModel;
import com.work.freedomworker.model.TaskModel;
import com.work.freedomworker.net.ApiUtils;
import com.work.freedomworker.utils.GsonUtil;
import com.work.freedomworker.utils.LoadDialog;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;

public class TaskTabStayFragment extends BaseLazyLoadFragment {
    private static final String TAG = "TaskTabFragment";

    @BindView(R.id.smartrefresh_task)
    SmartRefreshLayout smartrefreshTask;
    @BindView(R.id.classicsheader_task)
    ClassicsHeader classicsheaderTask;
    @BindView(R.id.classicsfooter_task)
    ClassicsFooter classicsfooterTask;

    @BindView(R.id.recycler_task_list)
    RecyclerView recyclerTaskList;

    @BindView(R.id.ll_task_tab_list)
    LinearLayout llTaskTabList;
    @BindView(R.id.ll_task_tab_nothing)
    LinearLayout llTaskTabNothing;

    TaskListAdapter taskListAdapter;
    List<TaskModel.TaskBean.TaskEntry> taskEntryList=new ArrayList<>();

    private int pageSize=15;//页数查询数量
    private int currentPage=1;//数据当前页
    private int total;//获取列表总数
    private String keyword="";//搜索关键字
    private int dateType=1;//数据类型

    @Override
    protected int setContentView() {
        return R.layout.fragment_task_tab;
    }

    @Override
    protected void onBaseFragmentVisibleChange(boolean isVisible) {
        if (isVisible){

        }
    }


    @Override
    protected void initView() {



    }

    @Override
    protected void initData() {
       // login();
        token="rzY5nxtD1tYDIPiTcW9WzIXuItSyN268XNO31Mx4WTbQpcLej8YXRjKmDkh3ivKmC6kNInWrOFM3QKCiZWhFm94dMriefThkMtT3f2P8qMzfydToUt9qIHPTBqrJNdRkdJ5NdRFkqCq9QX1WimaNTd0GzNdwR9rH";
        getTaskListData();
        taskListAdapter=new TaskListAdapter(mContext,taskEntryList);
        recyclerTaskList.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerTaskList.setAdapter(taskListAdapter);
        int delta = 8 * 60 * 60 * 1000;
        classicsheaderTask.setEnableLastTime(true);
        classicsheaderTask.setLastUpdateTime(new Date(System.currentTimeMillis() + delta));
        classicsheaderTask.setTimeFormat(new SimpleDateFormat("更新于 MM-dd HH:mm", Locale.CHINA));
        smartrefreshTask.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh( RefreshLayout refreshLayout) {
                currentPage = 1;
                getTaskListData();
                refreshLayout.finishRefresh();
            }
        });
        smartrefreshTask.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore( RefreshLayout refreshLayout) {
                currentPage++;
                getTaskListData();
                refreshLayout.finishLoadmore();
            }
        });
    }

    String token;
    /**
     * 获取等级列表数据
     */
    private void login() {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("phone","15011447970");
        paramsMap.put("code","1111");
        if (null == paramsMap) {
            paramsMap = new HashMap<>();
        }


        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("Content-Type","application/json");
        if (null == headerMap) {
            headerMap = new HashMap<>();
        }

        Log.e(TAG,"login--"+ JSON.toJSONString(paramsMap));
        ApiUtils.getInstance().postHeader("auth/login/phone", paramsMap,headerMap, new StringCallback() {
            final LoadDialog loadDialog = new LoadDialog();

            @Override
            public void onStart(Request<String, ? extends Request> request) {
                super.onStart(request);
                //loadDialog.showProgressDialog(mContext, "请稍后....");
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Log.e(TAG, response.code() + "");
                //loadDialog.closeProgressDialog();
            }

            @Override
            public void onSuccess(Response<String> response) {
                //  loadDialog.closeProgressDialog();
                Log.e(TAG,"login"+ response.body());
                BaseModel baseModel = GsonUtil.parseJson(response.body(), BaseModel.class);

                try {
                    if (baseModel.getCode() == 200) {
                        LoginTokenModel loginTokenModel = GsonUtil.parseJson(response.body(), LoginTokenModel.class);
                        token=loginTokenModel.getData().getToken();

                        getTaskListData();
                    } else {
                        showToast(getResources().getString(R.string.login_callback_failed));
                        return;
                    }
                } catch (Exception e) {
                    showToast(getResources().getString(R.string.data_analysis_failed));
                }

            }
        });

    }

    private void getTaskListData() {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("type",dateType);
        paramsMap.put("page",currentPage);
        paramsMap.put("page_size",pageSize);
        if (keyword!=null||keyword.length()>0) {
              paramsMap.put("word",keyword);
        }
        if (null == paramsMap) {
            paramsMap = new HashMap<>();
        }


        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("Accept","application/json");
        headerMap.put("Content-Type","application/json");
        headerMap.put("Authorization","Bearer "+token);
        if (null == headerMap) {
            headerMap = new HashMap<>();
        }

        Log.e("task_job--", JSON.toJSONString(paramsMap));
        ApiUtils.getInstance().getHeader("task_job", paramsMap,headerMap, new StringCallback() {
            final LoadDialog loadDialog = new LoadDialog();

            @Override
            public void onStart(Request<String, ? extends Request> request) {
                super.onStart(request);
                //loadDialog.showProgressDialog(mContext, "请稍后....");
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Log.e(TAG, response.code() + "");
                //loadDialog.closeProgressDialog();
            }

            @Override
            public void onSuccess(Response<String> response) {
                //  loadDialog.closeProgressDialog();
                Log.e(TAG,"task_job"+response.body());
                BaseModel baseModel = GsonUtil.parseJson(response.body(), BaseModel.class);

                try {
                    if (baseModel.getCode() == 200) {
                        TaskModel taskModel = GsonUtil.parseJson(response.body(), TaskModel.class);

                        total=taskModel.getData().getPage().getTotal();
                        if (currentPage==1) {
                            if (taskEntryList.size() > 0) {
                                taskEntryList.clear();
                            }
                        }

                        if (taskModel.getData().getData().size() > 0) {
                            taskEntryList.addAll(taskModel.getData().getData());

                            llTaskTabList.setVisibility(View.VISIBLE);
                            llTaskTabNothing.setVisibility(View.GONE);
                        }else{
                            llTaskTabNothing.setVisibility(View.VISIBLE);
                            llTaskTabList.setVisibility(View.GONE);
                        }


                        if ((currentPage * pageSize) >= total) {//判断是否大于总数，如果大于或者等于，设置不可上拉加载
                            smartrefreshTask.setEnableLoadmore(false);
                        }

                        taskListAdapter.notifyDataSetChanged();

                    } else {
                        showToast(getResources().getString(R.string.task_list_callback_failed));
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

    }
}
