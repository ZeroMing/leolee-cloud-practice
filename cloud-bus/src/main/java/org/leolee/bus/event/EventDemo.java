package org.leolee.bus.event;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.EventListener;

/**
 * @author: zeroming@163.com
 * @version:
 * @date: 2019年12月23 17时35分
 */
public class EventDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(EventDemo.class);
//        applicationContext.addApplicationListener(e -> {
//            System.out.println("触发事件:"+e.getClass().getSimpleName());
//        });
        // applicationContext.start();
        applicationContext.refresh();
        applicationContext.publishEvent(new LeoLeeEvent("LeoLee"));
        // 暂停
        applicationContext.stop();
        // 关闭
        applicationContext.close();
        // applicationContext.start();

    }

    static class LeoLeeEvent extends ApplicationEvent{
        public LeoLeeEvent(Object source) {
            super(source);
        }

    }

    @EventListener
    public void listener(ApplicationEvent applicationEvent){
        System.out.println("listener："+applicationEvent.getClass().getSimpleName());
    }
}
