package com.example.tokio.dao;

import com.example.tokio.dto.ScheduleTransactionDTO;
import com.example.tokio.excepion.FeeException;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ScheduleTransactionEntityTest {

    ModelMapper modelMapper;
    ScheduleTransactionDTO scheduleTransactionDto;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {

        scheduleTransactionDto = new ScheduleTransactionDTO();
        modelMapper = new ModelMapper();
    }

    @org.junit.jupiter.api.Test
    void transactionTest() {

        scheduleTransactionDto.setAmount(1000);
        scheduleTransactionDto.setDate("2020-05-10 15:00:00.000");
        scheduleTransactionDto.setSchedule("2020-05-20 15:00:00.000");
        scheduleTransactionDto.setFrom("056923");
        scheduleTransactionDto.setTo("095184");

        ScheduleTransactionEntity scheduleTransactionEntity = modelMapper.map(scheduleTransactionDto, ScheduleTransactionEntity.class);

        assertEquals(scheduleTransactionDto.getAmount(), scheduleTransactionEntity.getAmount());
        assertEquals(scheduleTransactionDto.getDate(), scheduleTransactionEntity.getDate());
        assertEquals(scheduleTransactionDto.getSchedule(), scheduleTransactionEntity.getSchedule());
        assertEquals(scheduleTransactionDto.getFrom(), scheduleTransactionEntity.getFrom());
        assertEquals(scheduleTransactionDto.getTo(), scheduleTransactionEntity.getTo());
    }
}