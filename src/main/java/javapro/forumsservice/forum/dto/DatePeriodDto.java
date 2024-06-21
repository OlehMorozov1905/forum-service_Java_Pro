package javapro.forumsservice.forum.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class DatePeriodDto {
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
}