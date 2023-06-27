package com.springboot.autoconfiguration.core.autoconfig;

import com.springboot.autoconfiguration.core.selector.MyImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MyImportSelector.class)
public @interface MyEnableAutoConfig {
}
