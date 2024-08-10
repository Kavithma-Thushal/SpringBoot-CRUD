package lk.ijse.gdse66.service.impl;

import lk.ijse.gdse66.dto.CustomerDTO;
import lk.ijse.gdse66.entity.Customer;
import lk.ijse.gdse66.repo.CustomerRepo;
import lk.ijse.gdse66.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : Kavithma Thushal
 * @project : SpringBoot-CRUD
 * @since : 7:25 AM - 6/18/2024
 **/
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    public CustomerRepo customerRepo;

    @Autowired
    public ModelMapper modelMapper;

    @Override
    public void saveCustomer(CustomerDTO customerDTO) {
        if (!customerRepo.existsById(customerDTO.getId())) {
            customerRepo.save(modelMapper.map(customerDTO, Customer.class));
        }
    }

    @Override
    public CustomerDTO searchCustomer(String id) {
        return customerRepo.findById(id).map(customer -> modelMapper.map(customer, CustomerDTO.class)).orElse(null);
    }

    @Override
    public void updateCustomer(CustomerDTO customerDTO) {
        if (customerRepo.existsById(customerDTO.getId())) {
            customerRepo.save(modelMapper.map(customerDTO, Customer.class));
        }
    }

    @Override
    public void deleteCustomer(String id) {
        customerRepo.deleteById(id);
    }

    @Override
    public List<Customer> loadAllCustomers() {
        return customerRepo.findAll();
    }
}
