package hvqzao.testbed.webapp;

import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.http.HttpServletRequest;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.JdkIdGenerator;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {

    private String var;
    private final Token token;

    public WebController() {
        this.var = "<progress/>";
        this.token = new Token();
    }

    @RequestMapping(value = "/", method = GET)
    public String welcomePage(Model model) {
        model.addAttribute("token", token.get());
        model.addAttribute("p", var);
        return "welcome";
    }

    @RequestMapping(value = "/error/404")
    public String pageNotFound(Model model) {
        return "error/404";
    }

    @RequestMapping(value = "/error/500")
    public String internalServerError(Model model) {
        return "error/500";
    }

    @RequestMapping(value = "/reflected-xss")
    public String reflectedXss(@RequestParam(value = "p", required = false, defaultValue = "") String p,
            Model model) {
        model.addAttribute("p", p);
        return "reflected-xss";
    }

    @RequestMapping(value = "/stored-xss-set")
    public String storedXssSet(@RequestParam(value = "p", required = false, defaultValue = "") String p,
            Model model) {
        var = p;
        model.addAttribute("p", p);
        return "stored-xss-set";
    }

    @RequestMapping(value = "/stored-xss-get")
    public String storedXssSet(Model model) {
        model.addAttribute("p", var);
        return "stored-xss-get";
    }

    @RequestMapping(value = "/issue-token")
    public String issueToken(Model model) {
        model.addAttribute("token", token.issue());
        return "issue-token";
    }

    @RequestMapping(value = "/reflected-xss-with-token")
    public String reflectedXssWithToken(@RequestParam(value = "p", required = false, defaultValue = "") String p,
            @RequestParam(value = "token", required = false, defaultValue = "") String token,
            Model model) {
        model.addAttribute("token", this.token.get().equals(token));
        model.addAttribute("p", p);
        return "reflected-xss-with-token";
    }

    @RequestMapping(value = "/stored-xss-get-with-token")
    public String storedXssGetWithToken(@RequestParam(value = "token", required = false, defaultValue = "") String token,
            Model model) {
        model.addAttribute("token", this.token.get().equals(token));
        model.addAttribute("p", var);
        return "stored-xss-get-with-token";
    }

    //@RequestMapping(value = "/spel-verbose")
    //public String spelRce(@RequestParam(value = "p", required = false, defaultValue = "") String p,
    //        Model model, HttpServletRequest request) {
    //    model.addAttribute("p", p);
    //    try {
    //        ExpressionParser parser = new SpelExpressionParser();
    //        String result = parser.parseExpression(p, new TemplateParserContext("${", "}")).getValue(String.class);
    //        model.addAttribute("result", result);
    //    } catch (EvaluationException | ParseException e) {
    //        StringWriter stacktrace = new StringWriter();
    //        e.printStackTrace(new PrintWriter(stacktrace));
    //        model.addAttribute("stacktrace", stacktrace);
    //    }
    //    return "spel-verbose";
    //}

    private final class Token {

        private final JdkIdGenerator generator;

        private String token;

        public Token() {
            generator = new JdkIdGenerator();
            token = issue();
        }

        public String get() {
            return token;
        }

        public String issue() {
            token = generator.generateId().toString();
            return token;
        }
    }
}
