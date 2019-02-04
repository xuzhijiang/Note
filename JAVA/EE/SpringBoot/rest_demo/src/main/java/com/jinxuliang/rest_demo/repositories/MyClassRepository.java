package com.jinxuliang.rest_demo.repositories;

import com.jinxuliang.rest_demo.domain.MyClass;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MyClassRepository {

    private static List<MyClass> myClasses=new ArrayList<>();
    static{
        for(int i=1;i<=10;i++){
            myClasses.add(new MyClass(i,"myClass of id:"+i));
        }
    }

    public MyClass findById(int id){
        Optional<MyClass> myClass=myClasses.stream().filter(m->m.getId()==id).findFirst();
        if(myClass.isPresent()){
            return myClass.get();
        }
        else{
            return null;
        }
    }

    public void save(MyClass obj){
        if(obj!=null){
            if(obj.getId()==0){
                obj.setId(myClasses.size());
            }
            myClasses.add(obj);
        }
    }
}
