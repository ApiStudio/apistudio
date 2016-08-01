package lk.egreen.apistudio.bootstrap.module.theme;

/**
 * Created by dewmal on 7/29/16.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.server.mvc.Viewable;
import org.glassfish.jersey.server.mvc.spi.TemplateProcessor;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

@Provider
public class ThymeleafViewProcessor implements TemplateProcessor<String> {

    private static final Logger LOGGER = LogManager.getLogger(ThymeleafViewProcessor.class);


    @Context
    private HttpServletRequest request;

    @Context
    private HttpServletResponse response;

    @Context
    private ServletContext servletContext;

    private TemplateEngine templateEngine;

    private static String status = null;

    /**
     * コンストラクタ
     */
    public ThymeleafViewProcessor() {
//        LOGGER.info(status);

    }


    @PostConstruct
    private void init() {
        ServletContextTemplateResolver resolver = new ApiStudioTemplateResolver(servletContext);

//        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("/view/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setCacheTTLMs(3600000L);

        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);
        status=("ThymeleafViewProcessor end");
//        LOGGER.info(status);

    }

    /**
     * 参照させるファイル名を解決する。
     */
    @Override
    public String resolve(String name, MediaType mediaType) {
//        LOGGER.info(status);
//        LOGGER.info(name + " " + mediaType);
        return name;
    }

    @Override
    public void writeTo(String templateReference, Viewable viewable, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream out) throws IOException {
//        LOGGER.info(status);

        WebContext context = new WebContext(request, response, servletContext);

        //HTML内でモデルオブジェクトを使用できるようにしている。
        //ここはモデル名をそのまま使用できるようにした方が良い。
        context.setVariable("item", viewable.getModel());

        Writer writer = new OutputStreamWriter(out);
        templateEngine.process(templateReference, context, writer);

        writer.flush();
    }
}
