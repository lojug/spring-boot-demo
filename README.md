# Spring Boot Demo

## POM file savings with Spring Boot
 
Instead of dozens of maven dependencies we have 5 spring boot dependencies.

See the maven dependency tree and you will see how many dependencies there actually are. 


## JAVA Configuration that you would have to do yourself!

### Spring Web MVC Configuration

- extend WebMvcConfigurerAdapter
- define resource handlers (need this for static resources and for webjars)
- @EnableWebMvc
- @ComponentScan your controllers

- extend WebApplicationInitializer
- setup the webapp context with the servlet context and spring web configuration
- add the dispatcher servlet

#### spring boot autoconfig:
- org.springframework.boot.autoconfigure.web


### Configuration required for JPA

- Entity Manager with explicit configuration of location of Entities
- Platform Transaction Manager
- Data Source (If not managed by JNDI also need pooling such as Hikari)
- Have to remember to annotate configuration class with @EnableTransactionManagement and @EnableJpaRepositories (also need to explicitly specify repo location)

#### spring boot autoconfig:
 - org.springframework.boot.autoconfigure.orm.jpa
 - org.springframework.boot.autoconfigure.data.jpa


### Thymeleaf Configuration
- ThymeleafViewResolver
- SpringTemplateEngine
- ServletContextTemplateResolver with (prefix, suffix needing to be defined)
- SpringSecurityDialect

#### spring boot autoconfig:
- org.springframework.boot.autoconfigure.thymeleaf


### Security Configuration
- This is the only thing we really need to configure but we need to take care we aren't too strict on rules since
there is autoconfiguration for things like webjars and the h2 console.
 