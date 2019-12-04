package spring.source;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.UnknownHostException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.parsing.Location;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.beans.factory.parsing.ProblemReporter;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ConfigurationCondition.ConfigurationPhase;
import org.springframework.context.annotation.DeferredImportSelector.Group;
import org.springframework.core.NestedIOException;
import org.springframework.core.OrderComparator;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
class ConfigurationClassParser {

    private static final PropertySourceFactory DEFAULT_PROPERTY_SOURCE_FACTORY = new DefaultPropertySourceFactory();

    private static final Comparator<ConfigurationClassParser.DeferredImportSelectorHolder> DEFERRED_IMPORT_COMPARATOR =
            (o1, o2) -> AnnotationAwareOrderComparator.INSTANCE.compare(o1.getImportSelector(), o2.getImportSelector());

    private final Log logger = LogFactory.getLog(getClass());

    private final MetadataReaderFactory metadataReaderFactory;

    private final ProblemReporter problemReporter;

    private final Environment environment;

    private final ResourceLoader resourceLoader;

    private final BeanDefinitionRegistry registry;

    private final spring.source.ComponentScanAnnotationParser componentScanParser;

    private final spring.source.ConditionEvaluator conditionEvaluator;

    private final Map<spring.source.ConfigurationClass, spring.source.ConfigurationClass> configurationClasses = new LinkedHashMap<>();

    private final Map<String, spring.source.ConfigurationClass> knownSuperclasses = new HashMap<>();

    private final List<String> propertySourceNames = new ArrayList<>();

    private final ConfigurationClassParser.ImportStack importStack = new ConfigurationClassParser.ImportStack();

    @Nullable
    private List<ConfigurationClassParser.DeferredImportSelectorHolder> deferredImportSelectors;


    /**
     * Create a new {@link ConfigurationClassParser} instance that will be used
     * to populate the set of configuration classes.
     */
    public ConfigurationClassParser(MetadataReaderFactory metadataReaderFactory,
                                    ProblemReporter problemReporter, Environment environment, ResourceLoader resourceLoader,
                                    BeanNameGenerator componentScanBeanNameGenerator, BeanDefinitionRegistry registry) {

        this.metadataReaderFactory = metadataReaderFactory;
        this.problemReporter = problemReporter;
        this.environment = environment;
        this.resourceLoader = resourceLoader;
        this.registry = registry;
        this.componentScanParser = new spring.source.ComponentScanAnnotationParser(
                environment, resourceLoader, componentScanBeanNameGenerator, registry);
        this.conditionEvaluator = new spring.source.ConditionEvaluator(registry, environment, resourceLoader);
    }


    public void parse(Set<BeanDefinitionHolder> configCandidates) {
        this.deferredImportSelectors = new LinkedList<>();

        for (BeanDefinitionHolder holder : configCandidates) {
            BeanDefinition bd = holder.getBeanDefinition();
            try {
                if (bd instanceof AnnotatedBeanDefinition) {
                    parse(((AnnotatedBeanDefinition) bd).getMetadata(), holder.getBeanName());
                }
                else if (bd instanceof AbstractBeanDefinition && ((AbstractBeanDefinition) bd).hasBeanClass()) {
                    parse(((AbstractBeanDefinition) bd).getBeanClass(), holder.getBeanName());
                }
                else {
                    parse(bd.getBeanClassName(), holder.getBeanName());
                }
            }
            catch (BeanDefinitionStoreException ex) {
                throw ex;
            }
            catch (Throwable ex) {
                throw new BeanDefinitionStoreException(
                        "Failed to parse configuration class [" + bd.getBeanClassName() + "]", ex);
            }
        }

        processDeferredImportSelectors();
    }

    protected final void parse(@Nullable String className, String beanName) throws IOException {
        Assert.notNull(className, "No other class name for configuration class other definition");
        MetadataReader reader = this.metadataReaderFactory.getMetadataReader(className);
        processConfigurationClass(new spring.source.ConfigurationClass(reader, beanName));
    }

    protected final void parse(Class<?> clazz, String beanName) throws IOException {
        processConfigurationClass(new spring.source.ConfigurationClass(clazz, beanName));
    }

    protected final void parse(AnnotationMetadata metadata, String beanName) throws IOException {
        processConfigurationClass(new spring.source.ConfigurationClass(metadata, beanName));
    }

    /**
     * Validate each {@link spring.source.ConfigurationClass} object.
     * @see spring.source.ConfigurationClass#validate
     */
    public void validate() {
        for (spring.source.ConfigurationClass configClass : this.configurationClasses.keySet()) {
            configClass.validate(this.problemReporter);
        }
    }

    public Set<spring.source.ConfigurationClass> getConfigurationClasses() {
        return this.configurationClasses.keySet();
    }


    protected void processConfigurationClass(spring.source.ConfigurationClass configClass) throws IOException {
        if (this.conditionEvaluator.shouldSkip(configClass.getMetadata(), ConfigurationPhase.PARSE_CONFIGURATION)) {
            return;
        }

        spring.source.ConfigurationClass existingClass = this.configurationClasses.get(configClass);
        if (existingClass != null) {
            if (configClass.isImported()) {
                if (existingClass.isImported()) {
                    existingClass.mergeImportedBy(configClass);
                }
                // Otherwise ignore new imported config class; existing non-imported class overrides it.
                return;
            }
            else {
                // Explicit other definition found, probably replacing an import.
                // Let's remove the old one and go with the new one.
                this.configurationClasses.remove(configClass);
                this.knownSuperclasses.values().removeIf(configClass::equals);
            }
        }

        // Recursively process the configuration class and its superclass hierarchy.
        ConfigurationClassParser.SourceClass sourceClass = asSourceClass(configClass);
        do {
            sourceClass = doProcessConfigurationClass(configClass, sourceClass);
        }
        while (sourceClass != null);

        this.configurationClasses.put(configClass, configClass);
    }

    /**
     * Apply processing and build a complete {@link spring.source.ConfigurationClass} by reading the
     * annotations, members and methods from the source class. This method can be called
     * multiple times as relevant sources are discovered.
     * @param configClass the configuration class being build
     * @param sourceClass a source class
     * @return the superclass, or {@code null} if none found or previously processed
     */
    @Nullable
    protected final ConfigurationClassParser.SourceClass doProcessConfigurationClass(spring.source.ConfigurationClass configClass, ConfigurationClassParser.SourceClass sourceClass)
            throws IOException {

        // Recursively process any member (nested) classes first
        processMemberClasses(configClass, sourceClass);

        // Process any @PropertySource annotations
        for (AnnotationAttributes propertySource : spring.source.AnnotationConfigUtils.attributesForRepeatable(
                sourceClass.getMetadata(), PropertySources.class,
                org.springframework.context.annotation.PropertySource.class)) {
            if (this.environment instanceof ConfigurableEnvironment) {
                processPropertySource(propertySource);
            }
            else {
                logger.warn("Ignoring @PropertySource annotation on [" + sourceClass.getMetadata().getClassName() +
                        "]. Reason: Environment must implement ConfigurableEnvironment");
            }
        }

        // Process any @ComponentScan annotations
        Set<AnnotationAttributes> componentScans = spring.source.AnnotationConfigUtils.attributesForRepeatable(
                sourceClass.getMetadata(), ComponentScans.class, ComponentScan.class);
        if (!componentScans.isEmpty() &&
                !this.conditionEvaluator.shouldSkip(sourceClass.getMetadata(), ConfigurationPhase.REGISTER_BEAN)) {
            for (AnnotationAttributes componentScan : componentScans) {
                // The config class is annotated with @ComponentScan -> perform the scan immediately
                Set<BeanDefinitionHolder> scannedBeanDefinitions =
                        this.componentScanParser.parse(componentScan, sourceClass.getMetadata().getClassName());
                // Check the set of scanned definitions for any further config classes and parse recursively if needed
                for (BeanDefinitionHolder holder : scannedBeanDefinitions) {
                    BeanDefinition bdCand = holder.getBeanDefinition().getOriginatingBeanDefinition();
                    if (bdCand == null) {
                        bdCand = holder.getBeanDefinition();
                    }
                    if (ConfigurationClassUtils.checkConfigurationClassCandidate(bdCand, this.metadataReaderFactory)) {
                        parse(bdCand.getBeanClassName(), holder.getBeanName());
                    }
                }
            }
        }

        // Process any @Import annotations
        processImports(configClass, sourceClass, getImports(sourceClass), true);

        // Process any @ImportResource annotations
        AnnotationAttributes importResource =
                AnnotationConfigUtils.attributesFor(sourceClass.getMetadata(), ImportResource.class);
        if (importResource != null) {
            String[] resources = importResource.getStringArray("locations");
            Class<? extends BeanDefinitionReader> readerClass = importResource.getClass("reader");
            for (String resource : resources) {
                String resolvedResource = this.environment.resolveRequiredPlaceholders(resource);
                configClass.addImportedResource(resolvedResource, readerClass);
            }
        }

        // Process individual @Bean methods
        Set<MethodMetadata> beanMethods = retrieveBeanMethodMetadata(sourceClass);
        for (MethodMetadata methodMetadata : beanMethods) {
            //configClass.addBeanMethod(new BeanMethod(methodMetadata, configClass));
        }

        // Process default methods on interfaces
        processInterfaces(configClass, sourceClass);

        // Process superclass, if any
        if (sourceClass.getMetadata().hasSuperClass()) {
            String superclass = sourceClass.getMetadata().getSuperClassName();
            if (superclass != null && !superclass.startsWith("java") &&
                    !this.knownSuperclasses.containsKey(superclass)) {
                this.knownSuperclasses.put(superclass, configClass);
                // Superclass found, return its annotation metadata and recurse
                return sourceClass.getSuperClass();
            }
        }

        // No superclass -> processing is complete
        return null;
    }

    /**
     * Register member (nested) classes that happen to be configuration classes themselves.
     */
    private void processMemberClasses(spring.source.ConfigurationClass configClass, ConfigurationClassParser.SourceClass sourceClass) throws IOException {
        Collection<ConfigurationClassParser.SourceClass> memberClasses = sourceClass.getMemberClasses();
        if (!memberClasses.isEmpty()) {
            List<ConfigurationClassParser.SourceClass> candidates = new ArrayList<>(memberClasses.size());
            for (ConfigurationClassParser.SourceClass memberClass : memberClasses) {
                if (ConfigurationClassUtils.isConfigurationCandidate(memberClass.getMetadata()) &&
                        !memberClass.getMetadata().getClassName().equals(configClass.getMetadata().getClassName())) {
                    candidates.add(memberClass);
                }
            }
            OrderComparator.sort(candidates);
            for (ConfigurationClassParser.SourceClass candidate : candidates) {
                if (this.importStack.contains(configClass)) {
                    this.problemReporter.error(new ConfigurationClassParser.CircularImportProblem(configClass, this.importStack));
                }
                else {
                    this.importStack.push(configClass);
                    try {
                        processConfigurationClass(candidate.asConfigClass(configClass));
                    }
                    finally {
                        this.importStack.pop();
                    }
                }
            }
        }
    }

    /**
     * Register default methods on interfaces implemented by the configuration class.
     */
    private void processInterfaces(spring.source.ConfigurationClass configClass, ConfigurationClassParser.SourceClass sourceClass) throws IOException {
        for (ConfigurationClassParser.SourceClass ifc : sourceClass.getInterfaces()) {
            Set<MethodMetadata> beanMethods = retrieveBeanMethodMetadata(ifc);
            for (MethodMetadata methodMetadata : beanMethods) {
                if (!methodMetadata.isAbstract()) {
                    // A default method or other concrete method on a Java 8+ interface...
                    //configClass.addBeanMethod(new BeanMethod(methodMetadata, configClass));
                }
            }
            processInterfaces(configClass, ifc);
        }
    }

    /**
     * Retrieve the metadata for all <code>@Bean</code> methods.
     */
    private Set<MethodMetadata> retrieveBeanMethodMetadata(ConfigurationClassParser.SourceClass sourceClass) {
        AnnotationMetadata original = sourceClass.getMetadata();
        Set<MethodMetadata> beanMethods = original.getAnnotatedMethods(Bean.class.getName());
        if (beanMethods.size() > 1 && original instanceof StandardAnnotationMetadata) {
            // Try reading the class file via ASM for deterministic declaration order...
            // Unfortunately, the JVM's standard reflection returns methods in arbitrary
            // order, even between different runs of the same application on the same JVM.
            try {
                AnnotationMetadata asm =
                        this.metadataReaderFactory.getMetadataReader(original.getClassName()).getAnnotationMetadata();
                Set<MethodMetadata> asmMethods = asm.getAnnotatedMethods(Bean.class.getName());
                if (asmMethods.size() >= beanMethods.size()) {
                    Set<MethodMetadata> selectedMethods = new LinkedHashSet<>(asmMethods.size());
                    for (MethodMetadata asmMethod : asmMethods) {
                        for (MethodMetadata beanMethod : beanMethods) {
                            if (beanMethod.getMethodName().equals(asmMethod.getMethodName())) {
                                selectedMethods.add(beanMethod);
                                break;
                            }
                        }
                    }
                    if (selectedMethods.size() == beanMethods.size()) {
                        // All reflection-detected methods found in ASM method set -> proceed
                        beanMethods = selectedMethods;
                    }
                }
            }
            catch (IOException ex) {
                logger.debug("Failed to read class file via ASM for determining @Bean method order", ex);
                // No worries, let's continue with the reflection metadata we started with...
            }
        }
        return beanMethods;
    }


    /**
     * Process the given <code>@PropertySource</code> annotation metadata.
     * @param propertySource metadata for the <code>@PropertySource</code> annotation found
     * @throws IOException if loading a property source failed
     */
    private void processPropertySource(AnnotationAttributes propertySource) throws IOException {
        String name = propertySource.getString("name");
        if (!StringUtils.hasLength(name)) {
            name = null;
        }
        String encoding = propertySource.getString("encoding");
        if (!StringUtils.hasLength(encoding)) {
            encoding = null;
        }
        String[] locations = propertySource.getStringArray("value");
        Assert.isTrue(locations.length > 0, "At least one @PropertySource(value) location is required");
        boolean ignoreResourceNotFound = propertySource.getBoolean("ignoreResourceNotFound");

        Class<? extends PropertySourceFactory> factoryClass = propertySource.getClass("factory");
        PropertySourceFactory factory = (factoryClass == PropertySourceFactory.class ?
                DEFAULT_PROPERTY_SOURCE_FACTORY : BeanUtils.instantiateClass(factoryClass));

        for (String location : locations) {
            try {
                String resolvedLocation = this.environment.resolveRequiredPlaceholders(location);
                Resource resource = this.resourceLoader.getResource(resolvedLocation);
                addPropertySource(factory.createPropertySource(name, new EncodedResource(resource, encoding)));
            }
            catch (IllegalArgumentException | FileNotFoundException | UnknownHostException ex) {
                // Placeholders not resolvable or resource not found when trying to open it
                if (ignoreResourceNotFound) {
                    if (logger.isInfoEnabled()) {
                        logger.info("Properties location [" + location + "] not resolvable: " + ex.getMessage());
                    }
                }
                else {
                    throw ex;
                }
            }
        }
    }

    private void addPropertySource(PropertySource<?> propertySource) {
        String name = propertySource.getName();
        MutablePropertySources propertySources = ((ConfigurableEnvironment) this.environment).getPropertySources();

        if (this.propertySourceNames.contains(name)) {
            // We've already added a version, we need to extend it
            PropertySource<?> existing = propertySources.get(name);
            if (existing != null) {
                PropertySource<?> newSource = (propertySource instanceof ResourcePropertySource ?
                        ((ResourcePropertySource) propertySource).withResourceName() : propertySource);
                if (existing instanceof CompositePropertySource) {
                    ((CompositePropertySource) existing).addFirstPropertySource(newSource);
                }
                else {
                    if (existing instanceof ResourcePropertySource) {
                        existing = ((ResourcePropertySource) existing).withResourceName();
                    }
                    CompositePropertySource composite = new CompositePropertySource(name);
                    composite.addPropertySource(newSource);
                    composite.addPropertySource(existing);
                    propertySources.replace(name, composite);
                }
                return;
            }
        }

        if (this.propertySourceNames.isEmpty()) {
            propertySources.addLast(propertySource);
        }
        else {
            String firstProcessed = this.propertySourceNames.get(this.propertySourceNames.size() - 1);
            propertySources.addBefore(firstProcessed, propertySource);
        }
        this.propertySourceNames.add(name);
    }


    /**
     * Returns {@code @Import} class, considering all meta-annotations.
     */
    private Set<ConfigurationClassParser.SourceClass> getImports(ConfigurationClassParser.SourceClass sourceClass) throws IOException {
        Set<ConfigurationClassParser.SourceClass> imports = new LinkedHashSet<>();
        Set<ConfigurationClassParser.SourceClass> visited = new LinkedHashSet<>();
        collectImports(sourceClass, imports, visited);
        return imports;
    }

    /**
     * Recursively collect all declared {@code @Import} values. Unlike most
     * meta-annotations it is valid to have several {@code @Import}s declared with
     * different values; the usual process of returning values from the first
     * meta-annotation on a class is not sufficient.
     * <p>For example, it is CommonSource for a {@code @Configuration} class to declare direct
     * {@code @Import}s in addition to meta-imports originating from an {@code @Enable}
     * annotation.
     * @param sourceClass the class to search
     * @param imports the imports collected so far
     * @param visited used to track visited classes to prevent infinite recursion
     * @throws IOException if there is any problem reading metadata from the named class
     */
    private void collectImports(ConfigurationClassParser.SourceClass sourceClass, Set<ConfigurationClassParser.SourceClass> imports, Set<ConfigurationClassParser.SourceClass> visited)
            throws IOException {

        if (visited.add(sourceClass)) {
            for (ConfigurationClassParser.SourceClass annotation : sourceClass.getAnnotations()) {
                String annName = annotation.getMetadata().getClassName();
                if (!annName.startsWith("java") && !annName.equals(Import.class.getName())) {
                    collectImports(annotation, imports, visited);
                }
            }
            imports.addAll(sourceClass.getAnnotationAttributes(Import.class.getName(), "value"));
        }
    }

    private void processDeferredImportSelectors() {
        List<ConfigurationClassParser.DeferredImportSelectorHolder> deferredImports = this.deferredImportSelectors;
        this.deferredImportSelectors = null;
        if (deferredImports == null) {
            return;
        }

        deferredImports.sort(DEFERRED_IMPORT_COMPARATOR);
        Map<Object, ConfigurationClassParser.DeferredImportSelectorGrouping> groupings = new LinkedHashMap<>();
        Map<AnnotationMetadata, spring.source.ConfigurationClass> configurationClasses = new HashMap<>();
        for (ConfigurationClassParser.DeferredImportSelectorHolder deferredImport : deferredImports) {
            Class<? extends Group> group = deferredImport.getImportSelector().getImportGroup();
            ConfigurationClassParser.DeferredImportSelectorGrouping grouping = groupings.computeIfAbsent(
                    (group != null ? group : deferredImport),
                    key -> new ConfigurationClassParser.DeferredImportSelectorGrouping(createGroup(group)));
            grouping.add(deferredImport);
            configurationClasses.put(deferredImport.getConfigurationClass().getMetadata(),
                    deferredImport.getConfigurationClass());
        }
        for (ConfigurationClassParser.DeferredImportSelectorGrouping grouping : groupings.values()) {
            grouping.getImports().forEach(entry -> {
                spring.source.ConfigurationClass configurationClass = configurationClasses.get(entry.getMetadata());
                try {
                    processImports(configurationClass, asSourceClass(configurationClass),
                            asSourceClasses(entry.getImportClassName()), false);
                }
                catch (BeanDefinitionStoreException ex) {
                    throw ex;
                }
                catch (Throwable ex) {
                    throw new BeanDefinitionStoreException(
                            "Failed to process import candidates for configuration class [" +
                                    configurationClass.getMetadata().getClassName() + "]", ex);
                }
            });
        }
    }

    private Group createGroup(@Nullable Class<? extends Group> type) {
        Class<? extends Group> effectiveType = (type != null ? type : ConfigurationClassParser.DefaultDeferredImportSelectorGroup.class);
        Group group = BeanUtils.instantiateClass(effectiveType);
        ParserStrategyUtils.invokeAwareMethods(group,
                ConfigurationClassParser.this.environment,
                ConfigurationClassParser.this.resourceLoader,
                ConfigurationClassParser.this.registry);
        return group;
    }

    private void processImports(spring.source.ConfigurationClass configClass, ConfigurationClassParser.SourceClass currentSourceClass,
                                Collection<ConfigurationClassParser.SourceClass> importCandidates, boolean checkForCircularImports) {

        if (importCandidates.isEmpty()) {
            return;
        }

        if (checkForCircularImports && isChainedImportOnStack(configClass)) {
            this.problemReporter.error(new ConfigurationClassParser.CircularImportProblem(configClass, this.importStack));
        }
        else {
            this.importStack.push(configClass);
            try {
                for (ConfigurationClassParser.SourceClass candidate : importCandidates) {
                    if (candidate.isAssignable(ImportSelector.class)) {
                        // Candidate class is an ImportSelector -> delegate to it to determine imports
                        Class<?> candidateClass = candidate.loadClass();
                        ImportSelector selector = BeanUtils.instantiateClass(candidateClass, ImportSelector.class);
                        ParserStrategyUtils.invokeAwareMethods(
                                selector, this.environment, this.resourceLoader, this.registry);
                        if (this.deferredImportSelectors != null && selector instanceof DeferredImportSelector) {
                            this.deferredImportSelectors.add(
                                    new ConfigurationClassParser.DeferredImportSelectorHolder(configClass, (DeferredImportSelector) selector));
                        }
                        else {
                            String[] importClassNames = selector.selectImports(currentSourceClass.getMetadata());
                            Collection<ConfigurationClassParser.SourceClass> importSourceClasses = asSourceClasses(importClassNames);
                            processImports(configClass, currentSourceClass, importSourceClasses, false);
                        }
                    }
                    else if (candidate.isAssignable(ImportBeanDefinitionRegistrar.class)) {
                        // Candidate class is an ImportBeanDefinitionRegistrar ->
                        // delegate to it to register additional other definitions
                        Class<?> candidateClass = candidate.loadClass();
                        ImportBeanDefinitionRegistrar registrar =
                                BeanUtils.instantiateClass(candidateClass, ImportBeanDefinitionRegistrar.class);
                        ParserStrategyUtils.invokeAwareMethods(
                                registrar, this.environment, this.resourceLoader, this.registry);
                        configClass.addImportBeanDefinitionRegistrar(registrar, currentSourceClass.getMetadata());
                    }
                    else {
                        // Candidate class not an ImportSelector or ImportBeanDefinitionRegistrar ->
                        // process it as an @Configuration class
                        this.importStack.registerImport(
                                currentSourceClass.getMetadata(), candidate.getMetadata().getClassName());
                        processConfigurationClass(candidate.asConfigClass(configClass));
                    }
                }
            }
            catch (BeanDefinitionStoreException ex) {
                throw ex;
            }
            catch (Throwable ex) {
                throw new BeanDefinitionStoreException(
                        "Failed to process import candidates for configuration class [" +
                                configClass.getMetadata().getClassName() + "]", ex);
            }
            finally {
                this.importStack.pop();
            }
        }
    }

    private boolean isChainedImportOnStack(spring.source.ConfigurationClass configClass) {
        if (this.importStack.contains(configClass)) {
            String configClassName = configClass.getMetadata().getClassName();
            AnnotationMetadata importingClass = this.importStack.getImportingClassFor(configClassName);
            while (importingClass != null) {
                if (configClassName.equals(importingClass.getClassName())) {
                    return true;
                }
                importingClass = this.importStack.getImportingClassFor(importingClass.getClassName());
            }
        }
        return false;
    }

    spring.source.ImportRegistry getImportRegistry() {
        return this.importStack;
    }


    /**
     * Factory method to obtain a {@link ConfigurationClassParser.SourceClass} from a {@link spring.source.ConfigurationClass}.
     */
    private ConfigurationClassParser.SourceClass asSourceClass(spring.source.ConfigurationClass configurationClass) throws IOException {
        AnnotationMetadata metadata = configurationClass.getMetadata();
        if (metadata instanceof StandardAnnotationMetadata) {
            return asSourceClass(((StandardAnnotationMetadata) metadata).getIntrospectedClass());
        }
        return asSourceClass(metadata.getClassName());
    }

    /**
     * Factory method to obtain a {@link ConfigurationClassParser.SourceClass} from a {@link Class}.
     */
    ConfigurationClassParser.SourceClass asSourceClass(@Nullable Class<?> classType) throws IOException {
        if (classType == null) {
            return new ConfigurationClassParser.SourceClass(Object.class);
        }
        try {
            // Sanity test that we can reflectively read annotations,
            // including Class attributes; if not -> fall back to ASM
            for (Annotation ann : classType.getAnnotations()) {
                AnnotationUtils.validateAnnotation(ann);
            }
            return new ConfigurationClassParser.SourceClass(classType);
        }
        catch (Throwable ex) {
            // Enforce ASM via class name resolution
            return asSourceClass(classType.getName());
        }
    }

    /**
     * Factory method to obtain {@link ConfigurationClassParser.SourceClass SourceClasss} from class names.
     */
    private Collection<ConfigurationClassParser.SourceClass> asSourceClasses(String... classNames) throws IOException {
        List<ConfigurationClassParser.SourceClass> annotatedClasses = new ArrayList<>(classNames.length);
        for (String className : classNames) {
            annotatedClasses.add(asSourceClass(className));
        }
        return annotatedClasses;
    }

    /**
     * Factory method to obtain a {@link ConfigurationClassParser.SourceClass} from a class name.
     */
    ConfigurationClassParser.SourceClass asSourceClass(@Nullable String className) throws IOException {
        if (className == null) {
            return new ConfigurationClassParser.SourceClass(Object.class);
        }
        if (className.startsWith("java")) {
            // Never use ASM for core java types
            try {
                return new ConfigurationClassParser.SourceClass(ClassUtils.forName(className, this.resourceLoader.getClassLoader()));
            }
            catch (ClassNotFoundException ex) {
                throw new NestedIOException("Failed to load class [" + className + "]", ex);
            }
        }
        return new ConfigurationClassParser.SourceClass(this.metadataReaderFactory.getMetadataReader(className));
    }


    @SuppressWarnings("serial")
    private static class ImportStack extends ArrayDeque<spring.source.ConfigurationClass> implements spring.source.ImportRegistry {

        private final MultiValueMap<String, AnnotationMetadata> imports = new LinkedMultiValueMap<>();

        public void registerImport(AnnotationMetadata importingClass, String importedClass) {
            this.imports.add(importedClass, importingClass);
        }

        @Override
        @Nullable
        public AnnotationMetadata getImportingClassFor(String importedClass) {
            return CollectionUtils.lastElement(this.imports.get(importedClass));
        }

        @Override
        public void removeImportingClass(String importingClass) {
            for (List<AnnotationMetadata> list : this.imports.values()) {
                for (Iterator<AnnotationMetadata> iterator = list.iterator(); iterator.hasNext();) {
                    if (iterator.next().getClassName().equals(importingClass)) {
                        iterator.remove();
                        break;
                    }
                }
            }
        }

        /**
         * Given a stack containing (in order)
         * <ul>
         * <li>com.acme.Foo</li>
         * <li>com.acme.Bar</li>
         * <li>com.acme.Baz</li>
         * </ul>
         * return "[Foo->Bar->Baz]".
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder("[");
            Iterator<spring.source.ConfigurationClass> iterator = iterator();
            while (iterator.hasNext()) {
                builder.append(iterator.next().getSimpleName());
                if (iterator.hasNext()) {
                    builder.append("->");
                }
            }
            return builder.append(']').toString();
        }
    }


    private static class DeferredImportSelectorHolder {

        private final spring.source.ConfigurationClass configurationClass;

        private final DeferredImportSelector importSelector;

        public DeferredImportSelectorHolder(spring.source.ConfigurationClass configClass, DeferredImportSelector selector) {
            this.configurationClass = configClass;
            this.importSelector = selector;
        }

        public spring.source.ConfigurationClass getConfigurationClass() {
            return this.configurationClass;
        }

        public DeferredImportSelector getImportSelector() {
            return this.importSelector;
        }
    }


    private static class DeferredImportSelectorGrouping {

        private final DeferredImportSelector.Group group;

        private final List<ConfigurationClassParser.DeferredImportSelectorHolder> deferredImports = new ArrayList<>();

        DeferredImportSelectorGrouping(Group group) {
            this.group = group;
        }

        public void add(ConfigurationClassParser.DeferredImportSelectorHolder deferredImport) {
            this.deferredImports.add(deferredImport);
        }

        /**
         * Return the imports defined by the group.
         * @return each import with its associated configuration class
         */
        public Iterable<Group.Entry> getImports() {
            for (ConfigurationClassParser.DeferredImportSelectorHolder deferredImport : this.deferredImports) {
                this.group.process(deferredImport.getConfigurationClass().getMetadata(),
                        deferredImport.getImportSelector());
            }
            return this.group.selectImports();
        }
    }


    private static class DefaultDeferredImportSelectorGroup implements Group {

        private final List<Entry> imports = new ArrayList<>();

        @Override
        public void process(AnnotationMetadata metadata, DeferredImportSelector selector) {
            for (String importClassName : selector.selectImports(metadata)) {
                this.imports.add(new Entry(metadata, importClassName));
            }
        }

        @Override
        public Iterable<Entry> selectImports() {
            return this.imports;
        }
    }


    /**
     * Simple wrapper that allows annotated source classes to be dealt with
     * in a uniform manner, regardless of how they are loaded.
     */
    private class SourceClass implements Ordered {

        private final Object source;  // Class or MetadataReader

        private final AnnotationMetadata metadata;

        public SourceClass(Object source) {
            this.source = source;
            if (source instanceof Class) {
                this.metadata = new StandardAnnotationMetadata((Class<?>) source, true);
            }
            else {
                this.metadata = ((MetadataReader) source).getAnnotationMetadata();
            }
        }

        public final AnnotationMetadata getMetadata() {
            return this.metadata;
        }

        @Override
        public int getOrder() {
            Integer order = ConfigurationClassUtils.getOrder(this.metadata);
            return (order != null ? order : Ordered.LOWEST_PRECEDENCE);
        }

        public Class<?> loadClass() throws ClassNotFoundException {
            if (this.source instanceof Class) {
                return (Class<?>) this.source;
            }
            String className = ((MetadataReader) this.source).getClassMetadata().getClassName();
            return ClassUtils.forName(className, resourceLoader.getClassLoader());
        }

        public boolean isAssignable(Class<?> clazz) throws IOException {
            if (this.source instanceof Class) {
                return clazz.isAssignableFrom((Class<?>) this.source);
            }
            return new AssignableTypeFilter(clazz).match((MetadataReader) this.source, metadataReaderFactory);
        }

        public spring.source.ConfigurationClass asConfigClass(spring.source.ConfigurationClass importedBy) throws IOException {
            if (this.source instanceof Class) {
                return new spring.source.ConfigurationClass((Class<?>) this.source, importedBy);
            }
            return new spring.source.ConfigurationClass((MetadataReader) this.source, importedBy);
        }

        public Collection<ConfigurationClassParser.SourceClass> getMemberClasses() throws IOException {
            Object sourceToProcess = this.source;
            if (sourceToProcess instanceof Class) {
                Class<?> sourceClass = (Class<?>) sourceToProcess;
                try {
                    Class<?>[] declaredClasses = sourceClass.getDeclaredClasses();
                    List<ConfigurationClassParser.SourceClass> members = new ArrayList<>(declaredClasses.length);
                    for (Class<?> declaredClass : declaredClasses) {
                        members.add(asSourceClass(declaredClass));
                    }
                    return members;
                }
                catch (NoClassDefFoundError err) {
                    // getDeclaredClasses() failed because of non-resolvable dependencies
                    // -> fall back to ASM below
                    sourceToProcess = metadataReaderFactory.getMetadataReader(sourceClass.getName());
                }
            }

            // ASM-based resolution - safe for non-resolvable classes as well
            MetadataReader sourceReader = (MetadataReader) sourceToProcess;
            String[] memberClassNames = sourceReader.getClassMetadata().getMemberClassNames();
            List<ConfigurationClassParser.SourceClass> members = new ArrayList<>(memberClassNames.length);
            for (String memberClassName : memberClassNames) {
                try {
                    members.add(asSourceClass(memberClassName));
                }
                catch (IOException ex) {
                    // Let's skip it if it's not resolvable - we're just looking for candidates
                    if (logger.isDebugEnabled()) {
                        logger.debug("Failed to resolve member class [" + memberClassName +
                                "] - not considering it as a configuration class candidate");
                    }
                }
            }
            return members;
        }

        public ConfigurationClassParser.SourceClass getSuperClass() throws IOException {
            if (this.source instanceof Class) {
                return asSourceClass(((Class<?>) this.source).getSuperclass());
            }
            return asSourceClass(((MetadataReader) this.source).getClassMetadata().getSuperClassName());
        }

        public Set<ConfigurationClassParser.SourceClass> getInterfaces() throws IOException {
            Set<ConfigurationClassParser.SourceClass> result = new LinkedHashSet<>();
            if (this.source instanceof Class) {
                Class<?> sourceClass = (Class<?>) this.source;
                for (Class<?> ifcClass : sourceClass.getInterfaces()) {
                    result.add(asSourceClass(ifcClass));
                }
            }
            else {
                for (String className : this.metadata.getInterfaceNames()) {
                    result.add(asSourceClass(className));
                }
            }
            return result;
        }

        public Set<ConfigurationClassParser.SourceClass> getAnnotations() throws IOException {
            Set<ConfigurationClassParser.SourceClass> result = new LinkedHashSet<>();
            for (String className : this.metadata.getAnnotationTypes()) {
                try {
                    result.add(getRelated(className));
                }
                catch (Throwable ex) {
                    // An annotation not present on the classpath is being ignored
                    // by the JVM's class loading -> ignore here as well.
                }
            }
            return result;
        }

        public Collection<ConfigurationClassParser.SourceClass> getAnnotationAttributes(String annType, String attribute) throws IOException {
            Map<String, Object> annotationAttributes = this.metadata.getAnnotationAttributes(annType, true);
            if (annotationAttributes == null || !annotationAttributes.containsKey(attribute)) {
                return Collections.emptySet();
            }
            String[] classNames = (String[]) annotationAttributes.get(attribute);
            Set<ConfigurationClassParser.SourceClass> result = new LinkedHashSet<>();
            for (String className : classNames) {
                result.add(getRelated(className));
            }
            return result;
        }

        private ConfigurationClassParser.SourceClass getRelated(String className) throws IOException {
            if (this.source instanceof Class) {
                try {
                    Class<?> clazz = ((Class<?>) this.source).getClassLoader().loadClass(className);
                    return asSourceClass(clazz);
                }
                catch (ClassNotFoundException ex) {
                    // Ignore -> fall back to ASM next, except for core java types.
                    if (className.startsWith("java")) {
                        throw new NestedIOException("Failed to load class [" + className + "]", ex);
                    }
                    return new ConfigurationClassParser.SourceClass(metadataReaderFactory.getMetadataReader(className));
                }
            }
            return asSourceClass(className);
        }

        @Override
        public boolean equals(Object other) {
            return (this == other || (other instanceof ConfigurationClassParser.SourceClass &&
                    this.metadata.getClassName().equals(((ConfigurationClassParser.SourceClass) other).metadata.getClassName())));
        }

        @Override
        public int hashCode() {
            return this.metadata.getClassName().hashCode();
        }

        @Override
        public String toString() {
            return this.metadata.getClassName();
        }
    }


    /**
     * {@link Problem} registered upon detection of a circular {@link Import}.
     */
    private static class CircularImportProblem extends Problem {

        public CircularImportProblem(spring.source.ConfigurationClass attemptedImport, Deque<spring.source.ConfigurationClass> importStack) {
            super(String.format("A circular @Import has been detected: " +
                            "Illegal attempt by @Configuration class '%s' to import class '%s' as '%s' is " +
                            "already present in the current import stack %s", importStack.element().getSimpleName(),
                    attemptedImport.getSimpleName(), attemptedImport.getSimpleName(), importStack),
                    new Location(importStack.element().getResource(), attemptedImport.getMetadata()));
        }
    }

}
