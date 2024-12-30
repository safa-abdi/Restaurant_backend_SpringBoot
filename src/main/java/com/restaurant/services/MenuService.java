package com.restaurant.services;

import com.restaurant.entities.Menu;
import com.restaurant.repositories.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public Menu createMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    public Optional<Menu> getMenuById(Long id) {
        return menuRepository.findById(id);
    }

    public Menu getMenuByStartDate(LocalDate startDate) {
        return menuRepository.findByStartDate(startDate);
    }

    public void deleteMenu(Long id) {
        menuRepository.deleteById(id);
    }
}
