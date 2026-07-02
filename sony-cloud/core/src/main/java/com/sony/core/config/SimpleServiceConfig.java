package com.sony.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "File Reader Configuration")
public @interface SimpleServiceConfig {

    @AttributeDefinition(name = "File Path")
    String filePath() default "/content/dam/sony/sample.txt";

    @AttributeDefinition(name = "Description")
    String description() default "Default Description";
}