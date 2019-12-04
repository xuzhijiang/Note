package spring.source;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.parsing.Location;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.beans.factory.parsing.ProblemReporter;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.context.annotation.*;
import org.springframework.core.io.DescriptiveResource;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

final class ConfigurationClass {

    private final AnnotationMetadata metadata;

    private final Resource resource;

    @Nullable
    private String beanName;

    private final Set<ConfigurationClass> importedBy = new LinkedHashSet<>(1);

    private final Map<String, Class<? extends BeanDefinitionReader>> importedResources =
            new LinkedHashMap<>();

    private final Map<ImportBeanDefinitionRegistrar, AnnotationMetadata> importBeanDefinitionRegistrars =
            new LinkedHashMap<>();

    final Set<String> skippedBeanMethods = new HashSet<>();

    public ConfigurationClass(MetadataReader metadataReader, String beanName) {
        Assert.notNull(beanName, "Bean name must not be null");
        this.metadata = metadataReader.getAnnotationMetadata();
        this.resource = metadataReader.getResource();
        this.beanName = beanName;
    }

    public ConfigurationClass(MetadataReader metadataReader, @Nullable ConfigurationClass importedBy) {
        this.metadata = metadataReader.getAnnotationMetadata();
        this.resource = metadataReader.getResource();
        this.importedBy.add(importedBy);
    }

    public ConfigurationClass(Class<?> clazz, String beanName) {
        Assert.notNull(beanName, "Bean name must not be null");
        this.metadata = new StandardAnnotationMetadata(clazz, true);
        this.resource = new DescriptiveResource(clazz.getName());
        this.beanName = beanName;
    }

    public ConfigurationClass(Class<?> clazz, @Nullable ConfigurationClass importedBy) {
        this.metadata = new StandardAnnotationMetadata(clazz, true);
        this.resource = new DescriptiveResource(clazz.getName());
        this.importedBy.add(importedBy);
    }

    public ConfigurationClass(AnnotationMetadata metadata, String beanName) {
        Assert.notNull(beanName, "Bean name must not be null");
        this.metadata = metadata;
        this.resource = new DescriptiveResource(metadata.getClassName());
        this.beanName = beanName;
    }


    public AnnotationMetadata getMetadata() {
        return this.metadata;
    }

    public Resource getResource() {
        return this.resource;
    }

    public String getSimpleName() {
        return ClassUtils.getShortName(getMetadata().getClassName());
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Nullable
    public String getBeanName() {
        return this.beanName;
    }

    public boolean isImported() {
        return !this.importedBy.isEmpty();
    }

    public void mergeImportedBy(ConfigurationClass otherConfigClass) {
        this.importedBy.addAll(otherConfigClass.importedBy);
    }

    public Set<ConfigurationClass> getImportedBy() {
        return this.importedBy;
    }

    public void addImportedResource(String importedResource, Class<? extends BeanDefinitionReader> readerClass) {
        this.importedResources.put(importedResource, readerClass);
    }

    public void addImportBeanDefinitionRegistrar(ImportBeanDefinitionRegistrar registrar, AnnotationMetadata importingClassMetadata) {
        this.importBeanDefinitionRegistrars.put(registrar, importingClassMetadata);
    }

    public Map<ImportBeanDefinitionRegistrar, AnnotationMetadata> getImportBeanDefinitionRegistrars() {
        return this.importBeanDefinitionRegistrars;
    }

    public Map<String, Class<? extends BeanDefinitionReader>> getImportedResources() {
        return this.importedResources;
    }

    public void validate(ProblemReporter problemReporter) {
        // A configuration class may not be final (CGLIB limitation)
        if (getMetadata().isAnnotated(Configuration.class.getName())) {
            if (getMetadata().isFinal()) {
                problemReporter.error(new ConfigurationClass.FinalConfigurationProblem());
            }
        }

//        for (BeanMethod beanMethod : this.beanMethods) {
//            beanMethod.validate(problemReporter);
//        }
    }

    @Override
    public boolean equals(Object other) {
        return (this == other || (other instanceof ConfigurationClass &&
                getMetadata().getClassName().equals(((ConfigurationClass) other).getMetadata().getClassName())));
    }

    @Override
    public int hashCode() {
        return getMetadata().getClassName().hashCode();
    }

    @Override
    public String toString() {
        return "ConfigurationClass: beanName '" + this.beanName + "', " + this.resource;
    }


    /**
     * Configuration classes must be non-final to accommodate CGLIB subclassing.
     */
    private class FinalConfigurationProblem extends Problem {

        public FinalConfigurationProblem() {
            super(String.format("@Configuration class '%s' may not be final. Remove the final modifier to continue.",
                    getSimpleName()), new Location(getResource(), getMetadata()));
        }
    }

}
