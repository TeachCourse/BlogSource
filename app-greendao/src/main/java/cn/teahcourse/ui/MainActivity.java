package cn.teahcourse.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.greendao.query.Query;

import java.util.List;

import cn.teahcourse.App;
import cn.teahcourse.greendao.DaoSession;
import cn.teahcourse.greendao.R;
import cn.teahcourse.greendao.Student;
import cn.teahcourse.greendao.StudentDao;

public class MainActivity extends AppCompatActivity {
    private int number=1;
    private Student student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void insert(View view){
        student= newStudent();
        DaoSession daoSession=((App)getApplication()).getDaoSession();
        StudentDao studentDao=daoSession.getStudentDao();
        studentDao.insert(student);
        Toast.makeText(this,"添加学生信息："+student.toString(),Toast.LENGTH_SHORT).show();
        Log.d("DaoExample", "Inserted new student, ID: " + student.getId());
        number++;
    }

    public void delete(View view){
        if(student==null)
            return;
        DaoSession daoSession=((App)getApplication()).getDaoSession();
        StudentDao studentDao=daoSession.getStudentDao();
        studentDao.delete(student);
        Toast.makeText(this,"删除学生信息："+student.toString(),Toast.LENGTH_SHORT).show();
        Log.d("DaoExample", "Delete a student, ID: " + student.getId());
        number--;
    }

    public void update(View view){
        if(student==null)
            return;
        student.setName("张翰");
        student.setGrade("大三");
        student.setAge(26);
        DaoSession daoSession=((App)getApplication()).getDaoSession();
        StudentDao studentDao=daoSession.getStudentDao();
        studentDao.update(student);
        Toast.makeText(this,"更新学生信息："+student.toString(),Toast.LENGTH_SHORT).show();
        Log.d("DaoExample", "Update a student, ID: " + student.getId());
        number--;
    }

    public void query(View view){
        DaoSession daoSession=((App)getApplication()).getDaoSession();
        StudentDao studentDao=daoSession.getStudentDao();
        Query<Student> studentQuery=studentDao.queryBuilder().orderAsc(StudentDao.Properties.Name).build();
        List<Student> studentList = studentQuery.list();
        for (Student student:studentList) {
            Log.d("DaoExample", "Query students, ID: " + student.getId());
        }
    }

    private Student newStudent() {
        Student student=new Student();
        student.setName("张杰"+number);
        student.setAge(25);
        student.setGrade("大二");
        return student;
    }

}
