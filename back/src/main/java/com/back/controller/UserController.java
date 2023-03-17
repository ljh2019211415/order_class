package com.back.controller;


import com.back.common.lang.Result;
import com.back.entity.*;
import com.back.mapper.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author campus
 * @since 2023-03-17
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private ExamineTableMapper examineTableMapper;

    @Autowired
    private RecordMapper recordMapper;


    @PostMapping("/sign")
    public Object sign(@RequestBody User user){
        try{
            userMapper.insert(user);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("注册失败");
        }
        return Result.succ();
    }

    @PostMapping("/login")
    public Object login(@RequestBody User user){
        int adminLevel=0;
        try{
            User user1 = userMapper.selectById(user.getId());
            if(user.getPassword()!=user1.getPassword())
                return Result.fail(404,"密码错误",null);
            adminLevel=user1.getAdmin();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("登陆失败");
        }
        return Result.succ(adminLevel);
    }

    @PostMapping("/forgetPwd")
    public Object forgetPwd(@RequestBody User user){
        try{
            User user1 = userMapper.selectById(user.getId());
            if(user1.getAnswer()!=user.getAnswer())
                return Result.fail(404,"密保错误",null);
            else
                userMapper.updateById(user);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("forgetPwd失败");
        }
        return Result.succ();
    }

    @PostMapping("/getInfo")
    public Object getInfo(@RequestBody User user){
        try{
            User user1 = userMapper.selectById(user.getId());
            return Result.succ(user1);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("getInfo失败");
        }
    }

    @PostMapping("/getTeacherList")
    public Object getTeacherList(@RequestBody User user){
        try{
            List<Teacher> teachers = teacherMapper.selectList(new LambdaQueryWrapper<Teacher>());
            return Result.succ(teachers);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("getTeacherList失败");
        }
    }

    @PostMapping("/getCourseInfo")
    public Object getCourseInfo(@RequestBody Teacher t){
        try{
            LambdaQueryWrapper<Course> lqw =new LambdaQueryWrapper<>();
            lqw.eq(Course::getTeacher,t.getId())
                    .orderByAsc(Course::getDate)
                    .orderByAsc(Course::getTime);
            List<Course> courses = courseMapper.selectList(lqw);
            return Result.succ(courses);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("getCourseInfo失败");
        }
    }

    @PostMapping("/getTable")
    public Object getTable(@RequestBody User user){
        try{
            LambdaQueryWrapper<ExamineTable> lqw=new LambdaQueryWrapper<>();
            lqw.eq(ExamineTable::getNewer,true);
            List<ExamineTable> examineTables = examineTableMapper.selectList(lqw);
            return Result.succ(examineTables);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("getTable失败");
        }
    }

    @PostMapping("/addRecord")
    public Object sign(@RequestBody Record record){
        try{
            User user = userMapper.selectById(record.getUserId());
            if(user.getAdmin()==0)
                Result.fail(404,"当前账户不是督导员，没有打分权限",null);
            else
                recordMapper.insert(record);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("addRecord失败");
        }
        return Result.succ();
    }

    @PostMapping("/getScore")
    public Object getScore(@RequestBody Teacher t){
        try{
            double[] scoreList={0,0,0,0,0};
            int[] targetList= {0,0,0,0,0};
            LambdaQueryWrapper<Record> lqw=new LambdaQueryWrapper<>();
            lqw.eq(Record::getTeacherId,t.getId());
            for (Record record : recordMapper.selectList(lqw)) {
                ExamineTable table = examineTableMapper.selectById(record.getTableId());
                ArrayList<Integer> target = strToInt(table.getTarget().split(","));
                ArrayList<Integer> score = strToInt(record.getScore().split(","));
                for (int i=0;i<score.size();i++)
                {
                    int index=target.get(i);
                    scoreList[index]+=score.get(i);
                    targetList[index]++;
                }
            }
            for (int i = 0; i < 5; i++) {
                if(targetList[i]!=0)
                    scoreList[i]=scoreList[i]/targetList[i];
                else scoreList[i]=0;
            }
            return Result.succ(scoreList);

        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("getScore失败");
        }
    }

    /*---------------以下为读到管理专属功能-----------------*/
    @PostMapping("/getAdmin")
    public Object getAdmin(@RequestBody User user){
        try{
            LambdaQueryWrapper<User> lqw=new LambdaQueryWrapper<>();
            lqw.eq(User::getAdmin,1);
            List<User> users = userMapper.selectList(lqw);
            return Result.succ(users);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("getAdmin失败");
        }
    }

    @PostMapping("/setAdmin")
    public Object setAdmin(@RequestBody User user){
        try{
            int i = userMapper.updateById(user);
            if(i==0)
                Result.fail(404,"失败，检查账号是否正确",null);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("setAdmin失败");
        }
        return Result.succ();
    }

    @PostMapping("/setTable")
    public Object setTable(@RequestBody ExamineTable table){
        try{
            LambdaQueryWrapper<ExamineTable> lqw=new LambdaQueryWrapper<>();
            lqw.eq(ExamineTable::getNewer,true);
            ExamineTable e=new ExamineTable();
            e.setNewer(false);
            examineTableMapper.update(e,lqw);
            examineTableMapper.insert(table);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("setTable失败");
        }
        return Result.succ();
    }

    @PostMapping("/getRecord")
    public Object getRecord(@RequestBody Teacher t){
        try{
            List<scoreTable> scoreTables=new ArrayList<>();
            LocalDateTime localDate = LocalDateTime.now();
            LocalDateTime week = localDate.plusDays(-7);

            LambdaQueryWrapper<Record> lqw=new LambdaQueryWrapper<>();
            lqw.ge(Record::getDate,week)
                    .eq(t.getId()!=null,Record::getTeacherId,t.getId())
                    .orderByDesc(Record::getDate);
            List<Record> records = recordMapper.selectList(lqw);
            for (Record record : records) {
                scoreTable s=new scoreTable();
                User user = userMapper.selectById(record.getUserId());
                s.setUid(user.getId());
                s.setUsername(user.getUsername());

                Teacher teacher = teacherMapper.selectById(record.getTeacherId());
                s.setTId(teacher.getId());
                s.setTName(teacher.getName());

                s.setDate(record.getDate().toLocalDate());
                s.setComment(record.getComment());

                ExamineTable examineTable = examineTableMapper.selectById(record.getTableId());
                s.setE(examineTable);

                s.setScore(record.getScore());

            }

            return Result.succ(scoreTables);

        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("getRecord失败");
        }
    }

    private ArrayList<Integer> strToInt(String[] str)
    {
        ArrayList<Integer> list=new ArrayList();
        for (String s : str) {
            int i = Integer.parseInt(s);
            list.add(i);
        }
        return list;
    }

}
