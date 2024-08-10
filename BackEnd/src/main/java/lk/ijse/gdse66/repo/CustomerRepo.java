package lk.ijse.gdse66.repo;

import lk.ijse.gdse66.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : Kavithma Thushal
 * @project : SpringBoot-CRUD
 * @since : 7:35 AM - 6/18/2024
 **/
public interface CustomerRepo extends JpaRepository<Customer, String> {
}
