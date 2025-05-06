package withdog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;

public class ObjectMapperTest {


    @Test
    public void testMapper() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String s = mapper.writeValueAsString(Instant.now());
        System.out.println("s = " + s);

    }

    @Test
    public void dateFormatTest() throws JsonProcessingException {

        Instant now = Instant.now();
//        String formatted = now.atZone(ZoneOffset.UTC).toLocalDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String formatted = Instant.ofEpochMilli(1745456192226L).atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String formatted2 = Instant.ofEpochMilli(1745456192226L).atZone(ZoneId.of("UTC")).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("formatted = " + formatted);
        System.out.println("formatted2 = " + formatted2);
    }
}
