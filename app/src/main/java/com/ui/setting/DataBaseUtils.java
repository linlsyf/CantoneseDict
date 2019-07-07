package com.ui.setting;

import android.support.annotation.NonNull;

import com.business.BusinessBroadcastUtils;
import com.core.base.GlobalConstants;
import com.core.base.IBaseView;
import com.core.db.greenDao.entity.Dict;
import com.core.db.greenDao.entity.SentenceYy;
import com.easy.recycleview.bean.DyItemBean;
import com.easy.recycleview.inter.IDyItemBean;
import com.easy.recycleview.inter.IItemView;
import com.easysoft.utils.lib.system.ThreadPoolUtils;
import com.easysoft.utils.lib.system.ToastUtils;
import com.linlsyf.area.R;
import com.ui.dict.DictBeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/7/7 0007.
 */

public class DataBaseUtils {

    @NonNull
    public static List<DyItemBean> getDataDyItemBeans(final IBaseView iSafeSettingView, final SettingPresenter  presenter) {
        List<DyItemBean> dataListCustom=new ArrayList<>();

        DyItemBean importBean =new DyItemBean();

        importBean.setTitle("覆盖当前进度（默认数据在SD上）");
        importBean.setOnItemListener(new IItemView.onItemClick() {
            @Override
            public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {

                inportDictLJ(iSafeSettingView,presenter);
            }
        });


        dataListCustom.add(importBean);

        DyItemBean repairBean=new DyItemBean();
        repairBean.setTitle(iSafeSettingView.getContext().getString(R.string.repair_db));
        repairBean.setOnItemListener(new IItemView.onItemClick() {
            @Override
            public void onItemClick(IItemView.ClickTypeEnum clickTypeEnum, IDyItemBean iDyItemBean) {
                DictBeanUtils.iniDbFile(iSafeSettingView, new DictBeanUtils.parseDictcallback() {
                    @Override
                    public void parseDataBack(Object list) {

                    }

                    @Override
                    public void showMsg(String msg) {
                        iSafeSettingView.showToast(msg);
                    }
                });

            }
        });
        dataListCustom.add(repairBean);

        DyItemBean emptyBean=new DyItemBean();
        emptyBean.setTitle(iSafeSettingView.getContext().getString(R.string.empty_db));

        emptyBean.setOnItemListener(new IItemView.onItemClick() {
            @Override
            public void onItemClick(IItemView.ClickTypeEnum clickTypeEnum, IDyItemBean iDyItemBean) {
                DictBeanUtils.emptyDb();
                iSafeSettingView.showToast(iSafeSettingView.getContext().getString(R.string.exec_sucess));
                BusinessBroadcastUtils.sendBroadcast(iSafeSettingView.getContext(),BusinessBroadcastUtils.TYPE_YY_REFRESH_HOME_COUNT,null);

            }
        });
        dataListCustom.add(emptyBean);

        DyItemBean  ljBean=new DyItemBean();
        ljBean.setTitle(iSafeSettingView.getContext().getString(R.string.init_sentence));
        ljBean.setOnItemListener(new IItemView.onItemClick() {
            @Override
            public void onItemClick(IItemView.ClickTypeEnum clickTypeEnum, IDyItemBean iDyItemBean) {



                DictBeanUtils.importDbSentence(iSafeSettingView.getContext(), new DictBeanUtils.parseDictcallback() {
                    @Override
                    public void parseDataBack(Object list) {

                        List<SentenceYy> sentenceYys=(List<SentenceYy>)list;
                        presenter.sentenceYyDao.insertOrReplaceInTx(sentenceYys);
                        iSafeSettingView.showToast(iSafeSettingView.getContext().getString(R.string.exec_sucess));

                    }

                    @Override
                    public void showMsg(String msg) {

                    }
                });
            }
        });
        dataListCustom.add(ljBean);
        presenter.exportBean=new DyItemBean();
        presenter.exportBean.setTitle("备份字典数据库到SD卡");
        presenter.exportBean.setOnItemListener(new IItemView.onItemClick() {
            @Override
            public void onItemClick(IItemView.ClickTypeEnum typeEnum, IDyItemBean bean) {
                exportDict(iSafeSettingView,presenter);
            }
        });
        dataListCustom.add( presenter.exportBean);


        DyItemBean  itemResetBean=new DyItemBean();

        itemResetBean.setTitle("重置学习进度");
        itemResetBean.setOnItemListener(new IItemView.onItemClick() {
            @Override
            public void onItemClick(IItemView.ClickTypeEnum clickTypeEnum, IDyItemBean iDyItemBean) {


                iSafeSettingView.showToast("正在重置...");

                ThreadPoolUtils.execute(new Runnable() {
                    @Override
                    public void run() {

                        List<Dict>  dictList = GlobalConstants.getInstance().getDaoSession().getDictDao().loadAll();

                        for (int i = 0; i < dictList.size(); i++) {
                            Dict dict = dictList.get(i);
                            dict.setCount(0);
                            dict.setStatus(0);

                        }
                        GlobalConstants.getInstance().getDaoSession().getDictDao().updateInTx( dictList);
                       // iSafeSettingView.showToast(iSafeSettingView.getContext().getResources().getString(R.string.exec_sucess));

                    }
                });



            }
        });

        dataListCustom.add(itemResetBean);





        return dataListCustom;
    }


    private static  void inportDictLJ(final IBaseView iSafeSettingView, final SettingPresenter  presenter) {

        iSafeSettingView.showToast("开始导入:请耐心等待 不要重复点击");





        DictBeanUtils.importFrombackUp(iSafeSettingView.getContext(), new DictBeanUtils.parseDictcallback() {
            @Override
            public void parseDataBack(Object obj) {
                List<Dict> data=(List<Dict>) obj;
                if (data.size()>0){
                    iSafeSettingView.showToast("导入覆盖成功");


                    BusinessBroadcastUtils.sendBroadcast(iSafeSettingView.getContext(),BusinessBroadcastUtils.TYPE_YY_REFRESH_HOME_COUNT,null);

                }else{
                    iSafeSettingView.showToast("导入覆盖失败");
                }

            }

            @Override
            public void showMsg(String msg) {
                iSafeSettingView.showToast(msg);

            }
        });

    }


    private static  void exportDict(final IBaseView iSafeSettingView, final SettingPresenter  presenter) {//导出字典数据
        iSafeSettingView.showToast("开始导出:请耐心等待 不要重复点击");
        DictBeanUtils.exportDb(iSafeSettingView.getContext(), new DictBeanUtils.parseDictcallback() {
            @Override
            public void parseDataBack(Object obj) {
                String isSucess=(String) obj;
                if (isSucess.equals("1")){
//					exportBean.setTitle("成功导出词典成功_sd卡目录_cantonese.db");
//					iSafeSettingView.updateItem(exportBean);
                    ToastUtils.show(iSafeSettingView.getContext(),"成功导出词典成功_sd卡目录_cantonese.db");
                }else{
                    ToastUtils.show(iSafeSettingView.getContext(),"导出失败");
                }


            }

            @Override
            public void showMsg(String msg) {
                iSafeSettingView.showToast(msg);

            }
        });

    }
}
