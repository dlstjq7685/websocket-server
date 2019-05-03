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

    @Test
    public void corebasetest_1(){

        functest1 f1 = new functest1();
        functest2 f2 = new functest2();

        f2.f = f1.funcEmpToString;

        f2.f.apply("test2");
        f1.setName("test2");
        f2.f.apply("test2");
    }

    @Test
    public void jdk_test(){
        System.out.println("test");

        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5);

        //JDK11      JDK10
        // var stream = integerList.stream();

        integerList.stream()
            .filter(i -> i %2 == 0)
            .forEach(i -> System.out.println(i));
    }


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