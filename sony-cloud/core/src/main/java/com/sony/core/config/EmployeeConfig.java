package com.sony.core.config;




import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(
        name = "Employee Details Configuration",
        description = "Configuration for Employee API Endpoint"
)
public @interface EmployeeConfig {

    @AttributeDefinition(
            name = "Employee API Endpoint",
            description = "Enter Employee API URL"
    )
    String employeeEndpoint()
            default "https://dummyjson.com/users";
}