package filter;   // Change to your project's package

import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.core.order.Ordered;
import io.micronaut.http.*;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import io.micronaut.security.authentication.Authentication;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;

import java.util.Optional;


@Slf4j
@Filter("/**")
public class HttpFilter implements HttpServerFilter {

    private final String domainOriginUrl = "https://interview-assist-ai-nine.vercel.app";

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
        if (request.getMethod() == io.micronaut.http.HttpMethod.OPTIONS) {
            String origin = request.getHeaders().get(HttpHeaders.ORIGIN);
            if (origin == null) {
                origin = "http://localhost:3000"; // Fallback to your frontend origin
            }

            MutableHttpResponse<?> preflightResponse = HttpResponse.status(HttpStatus.OK)
                    .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin)
                    .header(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST, GET, PUT, DELETE, OPTIONS")
                    .header(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type, Authorization, Accept")
                    .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Type, Authorization")
                    .header(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true") // Matches your frontend allow-credentials config
                    .header(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "1800");

            return Publishers.just(preflightResponse);
        }

        long requestTime = System.currentTimeMillis();
        return Publishers.map(chain.proceed(request) , response -> {
            logMessage(request , response,requestTime);
            updateSecurityHeaders(response);
            return response;
        });

    }

    private void logMessage(HttpRequest request , HttpResponse response , long requestTime){
        StringBuilder sb = new StringBuilder();
        String httpMethod = request.getMethodName();
        String path = request.getPath();
        Integer responseCode = -1;
        if(response != null){
            responseCode = response.getStatus().getCode();
        }
        long responseTime = System.currentTimeMillis();
        long duration = responseTime - requestTime;
        Optional<Object> authOpt = request.getAttribute(HttpAttributes.PRINCIPAL.toString());

        if(authOpt.isPresent()){
            Authentication auth = (Authentication) authOpt.get();
            sb.append("Auth/Customer: ").append(auth.getAttributes().get("sub"));

        }
        sb.append(",").append(" sourceIp: ").append(request.getHeaders().get("X-Forwarded-For"));
        sb.append(",").append(" app-os: ").append(request.getHeaders().get("AppOs"));
        sb.append(",").append(" method ").append(request.getMethod()) ;
        sb.append(",").append(" duration: " + duration+"ms");
        log.info(String.format("Canonical LogLine=%s", sb.toString()));
    }

    private void updateSecurityHeaders(HttpResponse httpResponse){
        MutableHttpResponse mutableHttpResponse = (MutableHttpResponse) httpResponse;
        String origin = "http://localhost:3000";
        mutableHttpResponse.header("Cache-Control" ,"no-cache");
        mutableHttpResponse.header("X-Content-Type-Options" ,"nosniff");
        mutableHttpResponse.header("X-Frame-Options" , "deny");
        mutableHttpResponse.header("X-XSS-Protection","1; mode=block");
        mutableHttpResponse.header("Strict-Transport-Security" , "max-age=86400;includedSubDomains");
        mutableHttpResponse.getHeaders().set(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
        mutableHttpResponse.getHeaders().set(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        mutableHttpResponse.getHeaders().set(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST, GET, PUT, DELETE, OPTIONS");
        mutableHttpResponse.getHeaders().set(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type, Authorization, Accept");
    }
}
