package was.http;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import model.User;
import org.junit.jupiter.api.Test;
import was.WasBaseTest;

public class HttpResponseTest extends WasBaseTest {
    @Test
    public void responseForward() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("HTTP_Body.txt"));
        response.responseBody(loadHtml().getBytes());
    }

    private String loadHtml() throws Exception {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("profile");

        User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
        return template.apply(user);
    }

    @Test
    public void responseRedirect() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("HTTP_Redirect.txt"));
        response.sendRedirect("/index.html");
    }

    @Test
    public void responseCookies() throws Exception {
        HttpResponse response = new HttpResponse(createOutputStream("HTTP_Cookie.txt"));
        response.addHeader("Set-Cookie", "logined=true");
        response.sendRedirect("/index.html");
    }
}