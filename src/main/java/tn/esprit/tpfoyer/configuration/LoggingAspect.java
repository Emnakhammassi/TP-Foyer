package tn.esprit.tpfoyer.configuration;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    //  s'exécute avant l'appel de chaque méthode dans FoyerServiceImpl
    // Il récupère le nom de la méthode et l'affiche dans les logs pour suivre les appels.
    @Before("execution(* tn.esprit.tpfoyer.services.FoyerServiceImpl.*(..))")
    public void logMethodEntry(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info(" Entrée dans la méthode : " + methodName);
    }

    // Log après l'exécution d'une méthode du service FoyerServiceImpl
    @After("execution(* tn.esprit.tpfoyer.services.FoyerServiceImpl.*(..))")
    public void logMethodExit(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info("Sortie de la méthode : " + methodName);
    }

    // Après le retour d'une méthode avec succès
    @AfterReturning(pointcut = "execution(* tn.esprit.tpfoyer.services.FoyerServiceImpl.*(..))", returning = "result")
    public void logMethodReturn(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        log.info("La méthode {} a retourné : {}", methodName, result);
    }

    // En cas d'exception
    @AfterThrowing(pointcut = "execution(* tn.esprit.tpfoyer.services.FoyerServiceImpl.*(..))", throwing = "ex")
    public void logMethodException(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().getName();
        log.error("La méthode {} a lancé une exception : {}", methodName, ex.getMessage());
    }


}
