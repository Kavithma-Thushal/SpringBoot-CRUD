package lk.ijse.gdse66.pos.service.impl;

import lk.ijse.gdse66.pos.dto.ItemDTO;
import lk.ijse.gdse66.pos.entity.Item;
import lk.ijse.gdse66.pos.repo.ItemRepo;
import lk.ijse.gdse66.pos.service.ItemService;
import lk.ijse.gdse66.pos.util.EmailSender;
import lk.ijse.gdse66.pos.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author : Kavithma Thushal
 * @project : Simple-POS-BackEnd-SpringBoot
 * @since : 7:25 AM - 6/18/2024
 **/
@Slf4j
@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailSender emailSender;

    @Override
    public ResponseUtil<String> saveItem(ItemDTO itemDTO) {

        if (!itemRepo.existsById(itemDTO.getCode())) {
            itemRepo.save(modelMapper.map(itemDTO, Item.class));

            String successResponse = "Item Saved Successfully...!";
            log.info("\u001B[34m{}\u001B[0m", successResponse);
//            try {
//                emailSender.sendEmail("kavithmathushal451@gmail.com", "Item Management", successResponse);
//            } catch (Exception e) {
//                log.error("\u001B[31m{}\u001B[0m", "Failed to Send Email...!");
//            }
            return new ResponseUtil<>(successResponse, HttpStatus.OK, null);

        } else {
            String errorResponse = "Duplicate Item Code: " + itemDTO.getCode();
            log.error("\u001B[31m{}\u001B[0m", errorResponse);
            return new ResponseUtil<>(errorResponse, HttpStatus.BAD_REQUEST, null);
        }
    }

    @Override
    public ResponseUtil<ItemDTO> searchItem(String code) {

        Optional<Item> item = itemRepo.findById(code);
        if (item.isPresent()) {
            ItemDTO itemDTO = modelMapper.map(item.get(), ItemDTO.class);

            String successResponse = "Item Searched Successfully...!";
            log.info("\u001B[34m{}\u001B[0m", successResponse);
            return new ResponseUtil<>(successResponse, HttpStatus.OK, itemDTO);

        } else {
            String errorResponse = "Item Not Found: " + code;
            log.error("\u001B[31m{}\u001B[0m", errorResponse);
            return new ResponseUtil<>(errorResponse, HttpStatus.NOT_FOUND, null);
        }
    }

    @Override
    public ResponseUtil<String> updateItem(ItemDTO itemDTO) {

        if (itemRepo.existsById(itemDTO.getCode())) {
            itemRepo.save(modelMapper.map(itemDTO, Item.class));

            String successResponse = "Item Updated Successfully...!";
            log.info("\u001B[34m{}\u001B[0m", successResponse);
            return new ResponseUtil<>(successResponse, HttpStatus.OK, null);

        } else {
            String errorResponse = "Item Not Found: " + itemDTO.getCode();
            log.info("\u001B[31m{}\u001B[0m", errorResponse);
            return new ResponseUtil<>(errorResponse, HttpStatus.BAD_REQUEST, null);
        }
    }

    @Override
    public ResponseUtil<String> deleteItem(String code) {

        Optional<Item> item = itemRepo.findById(code);
        if (item.isPresent()) {

            itemRepo.deleteById(code);
            String successResponse = "Item Deleted Successfully...!";
            log.info("\u001B[34m{}\u001B[0m", successResponse);
            return new ResponseUtil<>(successResponse, HttpStatus.OK, null);

        } else {
            String errorResponse = "Item Not Found: " + code;
            log.error("\u001B[31m{}\u001B[0m", errorResponse);
            return new ResponseUtil<>(errorResponse, HttpStatus.NOT_FOUND, null);
        }
    }

    @Override
    public ResponseUtil<List<ItemDTO>> loadAllItems() {

        List<Item> itemList = itemRepo.findAll();
        List<ItemDTO> itemDTOList = itemList.stream()
                .map(item -> modelMapper.map(item, ItemDTO.class))
                .collect(Collectors.toList());

        if (!itemDTOList.isEmpty()) {
            String successResponse = "Items Loaded Successfully...!";
            log.info("\u001B[34m{}\u001B[0m", successResponse);
            return new ResponseUtil<>(successResponse, HttpStatus.OK, itemDTOList);

        } else {
            String errorResponse = "No Items Found in DB";
            log.info("\u001B[33m{}\u001B[0m", errorResponse);
            return new ResponseUtil<>(errorResponse, HttpStatus.NO_CONTENT, null);
        }
    }

    @Override
    public ResponseUtil<String> generateItemCode() {
        String lastItemCode = itemRepo.findTopByOrderByCodeDesc().map(Item::getCode).orElse("I00-000");

        String successResponse = "Last Item Code Retrieved Successfully...!";
        log.info("\u001B[34m{}\u001B[0m", successResponse);
        return new ResponseUtil<>(successResponse, HttpStatus.OK, lastItemCode);
    }

    @Override
    public ResponseUtil<Integer> getItemCount() {
        Integer itemCount = itemRepo.countBy();

        String successResponse = "Item Count Retrieved Successfully...!";
        log.info("\u001B[34m{}\u001B[0m", successResponse);
        return new ResponseUtil<>(successResponse, HttpStatus.OK, itemCount);
    }
}