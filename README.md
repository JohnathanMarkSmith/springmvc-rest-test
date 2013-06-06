### SpringMVC-helloworld-text

This is a very basic example of using Spring MVC with JavaConfig to make a helloworld web apps and shows you how to test the web app with Spring Test Framework.

The first part of this is to create a configuration class for the web app.  below is a sample of the configuration class we are going to use:

    Configuration
    @EnableWebMvc
    @ComponentScan(basePackages = {"com.johnathanmsmith.mvc.web"})
    public class WebMVCConfig extends WebMvcConfigurerAdapter
    {

        private static final Logger logger = LoggerFactory.getLogger(WebMVCConfig.class);

        @Bean
        public ViewResolver resolver()
        {
            UrlBasedViewResolver url = new UrlBasedViewResolver();
            url.setPrefix("views/");
            url.setViewClass(JstlView.class);
            url.setSuffix(".jsp");
            return url;
        }

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry)
        {
            logger.debug("setting up resource handlers");
            registry.addResourceHandler("/resources/").addResourceLocations("/resources/**");
        }

        @Override
        public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
        {
            logger.debug("configureDefaultServletHandling");
            configurer.enable();
        }

        @Bean
        public SimpleMappingExceptionResolver simpleMappingExceptionResolver()
        {
            SimpleMappingExceptionResolver b = new SimpleMappingExceptionResolver();

            Properties mappings = new Properties();
            mappings.put("org.springframework.web.servlet.PageNotFound", "p404");
            mappings.put("org.springframework.dao.DataAccessException", "dataAccessFailure");
            mappings.put("org.springframework.transaction.TransactionException", "dataAccessFailure");
            b.setExceptionMappings(mappings);
            return b;
        }
    }


Next you have to setup the web.xml file to use the above configuration class, we do this but setting the contectConfigLocation to the package of the config class. see below:

    <web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xmlns="http://java.sun.com/xml/ns/javaee"
             xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
             version="2.5">

        <servlet>
            <servlet-name>Spring MVC Dispatcher Servlet</servlet-name>
            <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
            <init-param>
                <param-name>contextClass</param-name>
                <param-value>org.springframework.web.context.support.AnnotationConfigWebApplicationContext</param-value>
            </init-param>
            <init-param>
                <param-name>contextConfigLocation</param-name>
                <param-value>com.johnathanmsmith.mvc.web.config, com.johnathanmsmith.mvc.web.controller</param-value>
            </init-param>
            <load-on-startup>1</load-on-startup>
        </servlet>

        <servlet-mapping>
            <servlet-name>Spring MVC Dispatcher Servlet</servlet-name>
            <url-pattern>/</url-pattern>
        </servlet-mapping>
    </web-app>

Now lets setup a basic controller to display a page and my name on the page:

    @Controller
    @RequestMapping("/ask")
    class IndexController
    {

        private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

        @RequestMapping(method = RequestMethod.GET)
        public ModelAndView displayRequestPage()
        {
            logger.debug("made it to controller");

            /*

                Going to put my name in the model so it can be displayed on the page,
                this is also going to be used in the test to see if my name did make it
                to the page

            */

            Map<String, Object> model = new HashMap<String, Object>();
            model.put("user", "Johnathan Mark Smith");

            return new ModelAndView("helloworld", model);


        }


    }


Thats all it takes..

## Getting The Project and Running It

To get this project and run it you will need to follow the following steps:

    git clone  git@github.com:JohnathanMarkSmith/springmvc-helloworld.git
    cd springmvc-helloworld/
    mvn tomcat7:run

Now open your web brower and goto http://127.0.0.1:8080/springmvc-helloworld/

you can see the project run and play around with it for sometime but now lets look at the testing code.

## Testing with Spring Test Framework

We are not going to take a look at the test class I setup for test examples.

You can see that the class is named "TestHelloWorldWeb" and is using the same configuration class as the main project, in the real would you would want to use a test class.


    @RunWith(SpringJUnit4ClassRunner.class)
    @ContextConfiguration(classes = {WebMVCConfig.class})
    @WebAppConfiguration
    public class TestHelloWorldWeb
    {
        @Autowired
        private WebApplicationContext wac;

        private MockMvc mockMvc;

        @Before
        public void setup() {
            this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        }

        @Test
        public void getFoo() throws Exception {
            /*
                This following code will do 'GET' to the web apps
                and check that the return view is "helloworld"
                and also that it has a attribute "user" to "Johnathan Mark Smith"

             */
            this.mockMvc.perform(get("/ask")
                    .accept(MediaType.TEXT_HTML))
                    .andExpect(status().isOk())
                    .andExpect(view().name("helloworld"))
                    .andExpect(MockMvcResultMatchers.model().attribute("user", "Johnathan Mark Smith"))
                    ;


        }
    }


you can see in the above code that it will do a post and then test that my name is on the view helloworld. Its so easy

This its... Have run with it...

If you have any questions or comments please email me at john@johnathanmarksmith.com or checkout my web site http://JohnathanMarkSmith.com

