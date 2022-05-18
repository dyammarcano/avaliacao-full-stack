package com.example.tokio.service;

import com.example.tokio.bo.FeeCalculatorBO;
import com.example.tokio.dao.ScheduleTransactionEntity;
import com.example.tokio.dto.ScheduleTransactionDTO;
import com.example.tokio.model.ScheduleModel;
import com.example.tokio.repository.ScheduleRepository;
import com.example.tokio.util.Util;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ModelMapper modelMapper;

    public ScheduleService(ScheduleRepository scheduleRepository, ModelMapper modelMapper) {

        this.scheduleRepository = scheduleRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * add schedule transaction using schedule transaction dto.
     *
     * @param scheduleTransactionDTO schedule transaction dto
     * @return ScheduleTransactionEntity
     */
    @Transactional
    public ScheduleTransactionEntity add(ScheduleTransactionDTO scheduleTransactionDTO) {

        ScheduleModel scheduleModel = modelMapper.map(scheduleTransactionDTO, ScheduleModel.class);

        try {
            double fee = FeeCalculatorBO.feeByAmount(scheduleModel.getDate(), scheduleModel.getSchedule(), scheduleModel.getAmount());
            scheduleModel.setUuid(Util.generateUUID());
            scheduleModel.setFee(fee);
        } catch (Exception e) {
            e.printStackTrace();
        }

        scheduleModel = scheduleRepository.save(scheduleModel);
        ScheduleTransactionEntity map = modelMapper.map(scheduleModel, ScheduleTransactionEntity.class);
        map.setId(scheduleModel.getUuid());
        return map;
    }

    /**
     * update schedule transaction by id and schedule transaction dto.
     *
     * @param scheduleTransactionDTO schedule transaction dto
     * @param id schedule transaction id
     * @return ScheduleTransactionEntity
     */
    public ScheduleTransactionEntity update(ScheduleTransactionDTO scheduleTransactionDTO, String id) {

        ScheduleModel scheduleModel = modelMapper.map(scheduleTransactionDTO, ScheduleModel.class);

        double fee = 0;

        try {
            fee = FeeCalculatorBO.feeByAmount(scheduleModel.getDate(), scheduleModel.getSchedule(), scheduleModel.getAmount());
        } catch (Exception e) {
            e.printStackTrace();
        }

        double finalFee = fee;
        ScheduleModel finalScheduleModel = scheduleModel;
        scheduleModel = scheduleRepository.findByUuid(Util.stringToBinary(id)).map(schedule -> {
            schedule.setSchedule(finalScheduleModel.getSchedule());
            schedule.setAmount(finalScheduleModel.getAmount());
            schedule.setDate(finalScheduleModel.getDate());
            schedule.setSender(finalScheduleModel.getSender());
            schedule.setReceiver(finalScheduleModel.getReceiver());
            schedule.setFee(finalFee);
            schedule.setStatus(finalScheduleModel.getStatus());
            schedule.setType(finalScheduleModel.getType());

            return scheduleRepository.save(schedule);
        }).orElseGet(() -> scheduleRepository.save(finalScheduleModel));

        ScheduleTransactionEntity map = modelMapper.map(scheduleModel, ScheduleTransactionEntity.class);
        map.setId(scheduleModel.getUuid());
        return map;
    }

    /**
     * list all schedule transaction and return list of ScheduleTransactionEnties.
     *
     * @return List<ScheduleTransactionEntity>
     */
    public List<ScheduleTransactionEntity> getAll() {

        List<ScheduleTransactionEntity> scheduleTransactionEntities = new ArrayList<>();
        scheduleRepository.findAll().forEach(schedule -> {
            ScheduleTransactionEntity scheduleTransactionEntity = new ScheduleTransactionEntity();
            scheduleTransactionEntity.setId(schedule.getUuid());
            scheduleTransactionEntity.setSchedule(schedule.getSchedule());
            scheduleTransactionEntity.setAmount(schedule.getAmount());
            scheduleTransactionEntity.setDate(schedule.getDate());
            scheduleTransactionEntity.setSender(schedule.getSender());
            scheduleTransactionEntity.setReceiver(schedule.getReceiver());
            scheduleTransactionEntity.setFee(schedule.getFee());
            scheduleTransactionEntity.setStatus(schedule.getStatus());
            scheduleTransactionEntity.setType(schedule.getType());
            scheduleTransactionEntities.add(scheduleTransactionEntity);
        });

        return scheduleTransactionEntities;
    }

    /**
     * get schedule by id and return scheduleTransactionEntity.
     *
     * @param id schedule id
     * @return scheduleTransactionEntity
     */
    public ScheduleTransactionEntity getById(String id) {

        ScheduleTransactionEntity scheduleTransactionEntity = new ScheduleTransactionEntity();
        scheduleRepository.findByUuid(Util.stringToBinary(id)).ifPresent(schedule -> {
            scheduleTransactionEntity.setId(schedule.getUuid());
            scheduleTransactionEntity.setSchedule(schedule.getSchedule());
            scheduleTransactionEntity.setAmount(schedule.getAmount());
            scheduleTransactionEntity.setDate(schedule.getDate());
            scheduleTransactionEntity.setSender(schedule.getSender());
            scheduleTransactionEntity.setReceiver(schedule.getReceiver());
            scheduleTransactionEntity.setFee(schedule.getFee());
            scheduleTransactionEntity.setStatus(schedule.getStatus());
            scheduleTransactionEntity.setType(schedule.getType());
        });

        return scheduleTransactionEntity;
    }

    /**
     * delete schedule transaction by id.
     *
     * @param id schedule transaction id
     */
    public void delete(String id) {

        scheduleRepository.deleteByUuid(Util.stringToBinary(id));
    }
}
