package cn.teahcourse.upversion.impl;

import cn.teahcourse.upversion.APKDownloadService;
import cn.teahcourse.upversion.BaseServicePresenter;
import cn.teahcourse.upversion.IContract;

/**
 * Created by http://teachcourse.cn on 2018/1/5.
 */

public class ServicePresenterImpl extends BaseServicePresenter {

    public ServicePresenterImpl(IContract.View view) {
        super(view);
    }

    @Override
    public IContract.INotify getNotify(APKDownloadService service) {
        return new NotifyPresenterImpl(service);
    }
}
