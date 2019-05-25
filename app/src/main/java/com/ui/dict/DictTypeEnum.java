package com.ui.dict;

/**
 * Created by Administrator on 2019/3/6 0006.
 */

public enum DictTypeEnum {
    TODAY(0),
    YESDAY(1),
    MARK(2),
    SEARCH(3),
    LJ(4);
    private int value=0;
    DictTypeEnum(int value) {    //    必须是private的，否则编译错误
        this.value = value;
    }
    public int value() {
        return this.value;
    }
}
