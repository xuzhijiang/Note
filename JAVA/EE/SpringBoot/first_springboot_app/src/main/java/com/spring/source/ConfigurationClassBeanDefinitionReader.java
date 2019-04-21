package com.spring.source;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;

class ConfigurationClassBeanDefinitionReader {

    //private final BeanDefinitionRegistry registry;


    /**
     * Read {@code configurationModel}, registering bean definitions
     * with the registry based on its contents.
     */
//    public void loadBeanDefinitions(Set<ConfigurationClass> configurationModel) {
//        org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader.TrackedConditionEvaluator trackedConditionEvaluator = new org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader.TrackedConditionEvaluator();
//        for (ConfigurationClass configClass : configurationModel) {
//            loadBeanDefinitionsForConfigurationClass(configClass, trackedConditionEvaluator);
//        }
//    }

    /*
    private void loadBeanDefinitionsForConfigurationClass(
            ConfigurationClass configClass, org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader.TrackedConditionEvaluator trackedConditionEvaluator) {

        if (trackedConditionEvaluator.shouldSkip(configClass)) {
            String beanName = configClass.getBeanName();
            if (StringUtils.hasLength(beanName) && this.registry.containsBeanDefinition(beanName)) {
                this.registry.removeBeanDefinition(beanName);
            }
            this.importRegistry.removeImportingClass(configClass.getMetadata().getClassName());
            return;
        }

        if (configClass.isImported()) {
            registerBeanDefinitionForImportedConfigurationClass(configClass);
        }
        for (BeanMethod beanMethod : configClass.getBeanMethods()) {
            loadBeanDefinitionsForBeanMethod(beanMethod);
        }

        loadBeanDefinitionsFromImportedResources(configClass.getImportedResources());
        loadBeanDefinitionsFromRegistrars(configClass.getImportBeanDefinitionRegistrars());
    }
    */

}
