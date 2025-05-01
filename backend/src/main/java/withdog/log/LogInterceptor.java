package withdog.log;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    private final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        String requestURI = request.getRequestURI();
//        String sessionId = Arrays.stream(request.getCookies() != null ? request.getCookies() : new Cookie[0])
//                .filter(cookie -> cookie.getName().equals("sessionId"))
//                .map(cookie -> cookie.getValue())
//                .findFirst().orElseThrow(() -> new IllegalArgumentException("Cookie not found"));
//
//        request.setAttribute(LOG_ID, sessionId);
//        if (handler instanceof HandlerMethod) {
//
//            HandlerMethod hm = (HandlerMethod) handler; // 호출할 컨트롤러, 정적리소스 메서드의 모든정보가 포함
//
//        }
//
//        log.info("REQUEST [{}] [{}] [{}]", sessionId, requestURI, handler);

        return true;
    }
}
