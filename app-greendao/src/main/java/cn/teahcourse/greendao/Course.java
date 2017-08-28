package cn.teahcourse.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by http://teachcourse.cn on 2017/8/16.
 */
@Entity(indexes = {
        @Index(value = "name DESC", unique = true)
})
public class Course {
    @Id
    private long id;
    @NotNull
    private String name;

    @Generated(hash = 981929113)
    public Course(long id, @NotNull String name) {
        this.id = id;
        this.name = name;
    }

    @Generated(hash = 1355838961)
    public Course() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
