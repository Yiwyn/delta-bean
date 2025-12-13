package io.github.yiwyn.deltabean.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: DiffContent
 * @author: Yiwyn
 * @date: 2025/12/13 23:36
 * @Version: 1.0
 * @description: 差异信息
 */
public class DiffContent {


    private List<DiffItem> diffItems;


    public List<DiffItem> getDiffItems() {
        return diffItems;
    }

    public void setDiffItems(List<DiffItem> diffItems) {
        this.diffItems = diffItems;
    }

    @Override
    public String toString() {
        return "DiffContent{" +
                "diffItems=" + diffItems +
                '}';
    }
}
