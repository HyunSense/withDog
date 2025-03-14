package withdog.log;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    private final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        //TODO: 의미없는 UUID 변경 필요
        String uuid = UUID.randomUUID().toString();
        request.setAttribute(LOG_ID, uuid);
        if (handler instanceof HandlerMethod) {

            HandlerMethod hm = (HandlerMethod) handler; // 호출할 컨트롤러, 정적리소스 메서드의 모든정보가 포함

        }

        log.info("REQUEST [{}] [{}] [{}]", uuid, requestURI, handler);

        return true;
    }
}
