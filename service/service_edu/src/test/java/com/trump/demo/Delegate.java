package com.trump.demo;

import java.lang.reflect.Proxy;

public class Delegate{
        private Object obj;

        public void setObj(Object obj) {
            this.obj = obj;
        }

    public static Object getProxyInstance(Object obj) {
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(),
                obj.getClass().getInterfaces(),
                (proxy, method, args) ->{
                    System.out.println("前置增强");
                    method.invoke(obj, args);
                    System.out.println("后置增强");
                    return null;
                });
    }

}
class TestDelegate{
    public static void main(String[] args) {
        Person person = new SuperMan();
        Object proxy = Delegate.getProxyInstance(person);
        Person proxy2 = (Person) Delegate.getProxyInstance(person);
//        proxy2.skill();
    }
}
