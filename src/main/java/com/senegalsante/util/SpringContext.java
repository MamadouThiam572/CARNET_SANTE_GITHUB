package com.senegalsante.util;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Gestionnaire du contexte Spring pour JavaFX
 * 
 * Cette classe permet aux contrôleurs JavaFX d'accéder au contexte Spring
 * pour charger d'autres contrôleurs avec leurs dépendances injectées.
 */
@Component
public class SpringContext {

    private static ApplicationContext context;

    public SpringContext(ApplicationContext applicationContext) {
        setContext(applicationContext);
    }

    public static synchronized void setContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }
}
