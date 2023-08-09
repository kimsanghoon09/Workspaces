package com.sh.app.tv;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TvMain {

	/**
	 * 빈(bean) - 스프링이 관리하는 자바객체
	 * - xml
	 * - @Configuration
	 * - xml + @Component
	 * 
	 */
	public static void main(String[] args) {
		String configLocation = "applicationContext.xml";
		ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
		System.out.println("====== 스프링 컨테이너 초기화 완료 =====");
		
		// 빈 가져오기
		Tv tv1 = (Tv) context.getBean("samsungTv");
		tv1.powerOn();
		tv1.powerOff();
		
		// scope=prototype
		Tv tv2 = context.getBean(LgTv.class);
		tv2.powerOn();
		tv2.powerOff();
		
		Tv tv3 = context.getBean(LgTv.class);
		tv3.powerOn();
		tv3.powerOff();
		
		System.out.println(tv2 == tv3);
		
		Tv tv4 = context.getBean(SamsungTv.class);
		System.out.println(tv1 == tv4);
		
		
		RemoteControl rc = context.getBean(XiaomiRemocon.class);
		rc.channelTo(100);
		
		Tv tv = context.getBean("lgTv", Tv.class);
		LgTv lgTv = (LgTv) tv;
		System.out.println(lgTv);
		
	}

}
