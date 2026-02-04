package com.ruoyi.sast.sonar;

/**
 * 【规则12】异味 - 阻断
 * Child class fields should not shadow parent class fields
 * 子类字段不应遮蔽父类字段
 *
 * 问题：子类定义与父类同名的字段会导致混淆和潜在的bug
 */
public class ChildFieldShadowsParent {

    // 父类
    static class Animal {
        protected String name = "Animal";
        protected int age = 0;
        public String type = "Unknown";
        
        public void printInfo() {
            System.out.println("Name: " + name + ", Age: " + age);
        }
    }

    // 🚨 违规：子类字段遮蔽父类字段
    static class Dog extends Animal {
        // 🚨 违规：遮蔽了父类的name字段
        protected String name = "Dog";
        
        // 🚨 违规：遮蔽了父类的age字段
        protected int age = 1;
        
        // 🚨 违规：遮蔽了父类的type字段
        public String type = "Canine";
        
        public void showDetails() {
            // 这里访问的是子类的字段
            System.out.println("Dog name: " + name);
            
            // 访问父类字段需要使用super
            System.out.println("Animal name: " + super.name);
        }
    }

    // 🚨 违规：多层继承中的字段遮蔽
    static class Puppy extends Dog {
        // 🚨 违规：第三层遮蔽
        protected String name = "Puppy";
        protected int age = 0;
        
        public void display() {
            System.out.println("Puppy: " + name);           // Puppy的name
            System.out.println("Dog: " + super.name);       // Dog的name
            // 无法直接访问Animal的name
        }
    }

    // 演示问题
    public static void main(String[] args) {
        Dog dog = new Dog();
        Animal animal = dog;
        
        // 🚨 问题：字段访问取决于引用类型，而不是对象类型
        System.out.println(dog.name);     // 输出 "Dog"
        System.out.println(animal.name);  // 输出 "Animal" - 可能不是预期的结果！
        
        dog.printInfo();  // 输出 "Name: Animal, Age: 0" - 使用的是父类字段！
    }
}
