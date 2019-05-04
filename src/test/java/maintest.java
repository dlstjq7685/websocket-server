import org.hamcrest.CoreMatchers;
import org.junit.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class maintest {
}

class Employee{

    private String name;
    private int age;

    public Employee(String name,int age){
        this.name = name;
        this.age = age;

    }

    public String getName() {
        return name;
    }
}

class functest1{

    private String name = "test";

    public Function<String, Boolean> funcEmpToString = (String e)-> {
        System.out.println(name);
        return true;
    };

    public void setName(String name){
        this.name = name;
    }

}

class functest2{

    public Function f;
}