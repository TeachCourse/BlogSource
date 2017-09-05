package cn.teachcourse.view.drawingtool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by http://teachcourse.cn on 2017/6/1.
 */

public class PointHistory {
    private static PointHistory mInstance = null;
    public List<PointModel> redoList = new ArrayList<>();
    public List<PointModel> restoreList = new ArrayList<>();

    public static PointHistory getInstance() {
        if (mInstance == null) {
            synchronized (PointHistory.class) {
                if (mInstance == null) {
                    mInstance = new PointHistory();
                }
            }
        }
        return mInstance;
    }

    /**
     * 保存
     *
     * @param model
     */
    public void addPointModel(PointModel model) {

        redoList.add(model);
    }

    /**
     * 撤销
     */
    public void redo() {
        if (redoList.size() > 0) {
            PointModel model = redoList.get(redoList.size() - 1);
            redoList.remove(model);
            restoreList.add(model);
        }
    }

    /**
     * 恢复
     */
    public void restore() {
        if (restoreList.size() > 0) {
            PointModel model = restoreList.get(restoreList.size() - 1);
            restoreList.remove(model);
            redoList.add(model);
        }
    }
}
