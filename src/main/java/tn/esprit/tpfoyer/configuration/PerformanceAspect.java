package tn.esprit.tpfoyer.configuration;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class PerformanceAspect {

    // Mesure le temps d'exécution des méthodes dans le package services
    @Around("execution(* tn.esprit.tpfoyer.services.*.*(..))")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();

        Object obj = pjp.proceed(); // exécution de la méthode interceptée

        long elapsedTime = System.currentTimeMillis() - start;
        log.info("Durée d'exécution de {} : {} ms", pjp.getSignature(), elapsedTime);

        return obj;
    }



}
