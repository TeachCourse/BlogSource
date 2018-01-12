package cn.teahcourse.baseutil;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by http://teachcourse.cn on 2018/1/3.
 */

public class GenericTypeTest {

    ArrayList<Animal> mAnimals = new ArrayList<>();
    ArrayList<? extends Animal> mAnimalChildren = new ArrayList<>();
    ArrayList<? super HelloKitty> mAnimalParents = new ArrayList<>();

    public void addAll() {
        ArrayList mDataSet = new ArrayList<>();
        mDataSet.add(new Object());
        mDataSet.add(new String());
        mDataSet.add(new Integer(0));
        mDataSet.add(new Animal());

        ArrayList<String> mDataSetStr = new ArrayList<>();
        mDataSetStr.add(new String());
//        mDataSetStr.add(new Integer(0));

        //通配符单独使用
        ArrayList<?> mDataSetType = new ArrayList<>();
        //通配符结合关键字extends一起使用
        ArrayList<? extends Animal> mAnimalChildren = new ArrayList<>();
        //通配符结合关键字super一起使用
        ArrayList<? super HelloKitty> mAnimalParents = new ArrayList<>();


    }

    public void add() {
        /*指定ArrayList的泛型具体类为Animal，其对象可以存储Animal及其子类的对象*/
        mAnimals.add(new Animal());
        mAnimals.add(new Cat());
        mAnimals.add(new HelloKitty());

        //方便使用Animal内部的属性和方法
        String name = mAnimals.get(0).name;
        name = mAnimals.get(1).getName();
    }

    public void add(Cat cat) {
        /*add方法变成这样：add(? extends Animal e)，如果我们强行指定存储的子类型Cat、Animal或Object，程序报错*/
        /*但是，我们唯一可以确定的是e继承了Animal的属性和方法，可以读取继承的属性和方法*/
        ArrayList<? extends Animal> mAnimalChildren = new ArrayList<>();
//        mAnimalChildren.add(cat);


//        mAnimalChildren.add(new Animal());


//        mAnimalChildren.add(new Object());

    }

    public void add(Dog dog) {
//        mAnimalChildren.add(dog);
//        mAnimalChildren.set(0, dog);
    }

    public void add(Object obj) {
        /*add方法变成这样：add(? super HelloKitty e)，没有指定具体存储哪种超类型，如果我们强行指定存储Object、Animal或Cat类型，程序报错；*/
        /*但是，可以确定的是传入泛型 ? extends HelloKitty e或者HelloKitty e及其子类，程序能够正常识别*/
//        mAnimalParents.add(obj);
//        mAnimalParents.add(new Animal());
//        mAnimalParents.add(new Cat());
//        mAnimalParents.set(0, obj);
        mAnimalParents.add(new HelloKitty());
        mAnimalParents.add(new CuteHelloKitty());
        mAnimalParents.add(new WhiteCuteHelloKitty());
    }

    public Animal get() {
        Animal animal = mAnimals.get(0);
        return animal;
    }

    public Animal getAnimal() {
        Animal animal = mAnimalChildren.get(0);
        return animal;
    }

    public Cat getCat() {
        /*返回类型? extends Animal，无法确定具体的子类型，如果我们将引用指向Cat，程序报错（除非强转）*/
//        Cat cat = mAnimalChildren.get(0);
//        return cat;
        return null;
    }

    public Object getObj() {
        Object obj = mAnimalParents.get(0);
        return obj;
    }

    public void copy() {
        /*前者指定T为HelloKitty，后者指定为Animal*/
//        Collections.copy(mAnimalParents,mAnimalChildren);
        /*泛型：? super T e只能传入 ? extends T e*/
        ArrayList<? super Animal> mAnimalParents = new ArrayList<>();
        Collections.copy(mAnimalParents, mAnimalChildren);
        mAnimalParents.add(mAnimalChildren.get(0));
        mAnimalParents.set(0, mAnimalChildren.get(0));
    }

    /*内部类包括：Animal、Dog、Cat、HelloKitty*/
    class Animal {
        String name;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    class Dog extends Animal {

    }

    class Cat extends Animal {

    }

    class HelloKitty extends Cat {

        public String from() {
            return "I am a HelloKitty that is from China !";
        }
    }

    class CuteHelloKitty extends HelloKitty {

        public String feature() {
            return "This is a cute HelloKitty !";
        }
    }

    class WhiteCuteHelloKitty extends CuteHelloKitty {
        @Override
        public String feature() {
            return "Do you know me ? I am white and cute HelloKitty !";
        }
    }

    /**
     * 封装的一个类
     *
     * @param <T>
     */
    class AnimalName<T> {
        T e;

        public T get() {
            return e;
        }

        public T print(T e) {
            return e;
        }
    }

    public void print() {
        //一、绑定为一组继承自Animal的类，允许使用继承的属性和方法
        AnimalName<? extends Animal> animalName = new AnimalName();
        Animal animal = animalName.get();
        System.out.print(animal.getName());

        //二、绑定为一组CuteHelloKitty的类，允许使用继承的属性和方法
        AnimalName<? extends CuteHelloKitty> cuteHelloKittyName = new AnimalName();
        CuteHelloKitty cuteHelloKitty = cuteHelloKittyName.get();
        System.out.print(cuteHelloKitty.feature());

        //三、绑定为以CuteHelloKitty为下界的一组类
        AnimalName<? super CuteHelloKitty> helloKitty = new AnimalName<>();

        helloKitty.print(new CuteHelloKitty());
        helloKitty.print(new WhiteCuteHelloKitty());
    }
}
