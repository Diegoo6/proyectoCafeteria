package com.example.empleado_service.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import com.example.empleado_service.model.Cargo;
import com.example.empleado_service.model.TipoCargo;
import com.example.empleado_service.repository.CargoRepository;

@Configuration
public class DataInitializer {

    @Bean
    @Transactional
    CommandLineRunner initData(CargoRepository cargoRepository) {
        return args ->
        {
            if (cargoRepository.findByTipoCargo(TipoCargo.COCINERO).isEmpty()) {
                cargoRepository.save(new Cargo(null, TipoCargo.COCINERO));
            }

            if (cargoRepository.findByTipoCargo(TipoCargo.GARZON).isEmpty()) {
                cargoRepository.save(new Cargo(null, TipoCargo.GARZON));
            }

            if (cargoRepository.findByTipoCargo(TipoCargo.VENDEDOR).isEmpty()) {
                cargoRepository.save(new Cargo(null, TipoCargo.VENDEDOR));
                
            }
        };
    }
    
}
