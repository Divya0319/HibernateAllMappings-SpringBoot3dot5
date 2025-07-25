package com.fastturtle.hibernateallmappingsspringboot.aspect;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.fastturtle.hibernateallmappingsspringboot.entity.BookReferred;
import com.fastturtle.hibernateallmappingsspringboot.entity.Coder;
import com.fastturtle.hibernateallmappingsspringboot.entity.CoderDetail;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	
	Logger theLogger = LoggerFactory.getLogger(this.getClass());
	
	@Pointcut("execution(* com.fastturtle.hibernateallmappingsspringboot.rest.CoderRestController.*(..))")
	private void forControllerPackage() {}
	
	@AfterReturning(pointcut="forControllerPackage()", returning="result")
	public void afterReturningMethod(JoinPoint theJoinPoint, Object result) {
		
		Calendar calendar = new GregorianCalendar();
		
		String currentTime = calendar.getTime().toString();
		
		MethodSignature methodSig = (MethodSignature)theJoinPoint.getSignature();
		
		theLogger.info("----->>>>> TimeStamp -> " + currentTime);
		
		theLogger.info("----->>>>> Calling method -> " + methodSig.toString());
		
		Object[] args = theJoinPoint.getArgs();
		
		for(Object tempArg : args) {
			
			if(tempArg instanceof BookReferred) {
				
				BookReferred bRef = (BookReferred)tempArg;
				
				bRef.setCoder(null);
				
				theLogger.info("---->>> Request\n "+ bRef);
				
			} else if(tempArg instanceof Coder) {
				
				Coder tCoder = (Coder)tempArg;
				
				tCoder.setBooksReferred(null);
				tCoder.setCoderDetail(null);
				
				theLogger.info("---->>> Request\n "+ tCoder);
				
			} else if(tempArg instanceof CoderDetail) {
				
				CoderDetail cDet = (CoderDetail) tempArg;
				
				cDet.setCoder(null);
				
				theLogger.info("---->>> Request\n "+ cDet);
				
			}	else {
				
				theLogger.info("---->>> Request\n"+ tempArg);
			}

		}
		
		theLogger.info("---->>> Response :\n" + result);
		
	}

}
