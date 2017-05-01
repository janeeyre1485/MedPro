package auth.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class BeanUtil implements ApplicationContextAware {

	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		BeanUtil.context = context;
	}
	
	 public static <T> T getBean(Class<T> beanClass) {
	        return context.getBean(beanClass);
	    }

}
