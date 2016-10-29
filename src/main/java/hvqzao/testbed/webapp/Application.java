package hvqzao.testbed.webapp;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class Application {

    //private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    //@Bean
    //public Servlet dispatcherServlet() {
    //    return new GenericServlet() {
    //        @Override
    //        public void service(ServletRequest req, ServletResponse res)
    //                throws ServletException, IOException {
    //            res.setContentType("text/plain");
    //            res.getWriter().append("Hello World");
    //        }
    //    };
    //}

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
