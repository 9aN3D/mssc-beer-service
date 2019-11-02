package guru.springframework.msscbeerservice.web.mapper;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static java.util.Objects.isNull;

@Component
public class DataMapper {

    public OffsetDateTime asOffsetDateTime(Timestamp timestamp) {
        if (isNull(timestamp)) {
            return null;
        }
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        return OffsetDateTime.of(localDateTime.getYear(), localDateTime.getMonthValue(), localDateTime.getDayOfMonth(),
                localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond(), localDateTime.getNano(),
                ZoneOffset.UTC);
    }

    public Timestamp asTimestamp(OffsetDateTime offsetDateTime) {
        if (isNull(offsetDateTime)) {
            return null;
        }
        return Timestamp.valueOf(offsetDateTime.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
    }

}
