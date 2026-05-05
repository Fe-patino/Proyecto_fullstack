package com.productos.productos;

import com.productos.productos.Model.producto;
import com.productos.productos.Repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner init(ProductoRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new producto(null, 1L, 1L, "Hamburguesa Clasica", "Carne, lechuga y tomate", 5990.0, true));
                repository.save(new producto(null, 1L, 1L, "Hamburguesa BBQ", "Carne, cheddar y BBQ", 6990.0, true));
                repository.save(new producto(null, 1L, 2L, "Papas Fritas", "Papas crocantes con sal", 2490.0, true));
                repository.save(new producto(null, 1L, 3L, "Bebida 350ml", "Coca-Cola, Sprite o Fanta", 1490.0, true));
                repository.save(new producto(null, 2L, 4L, "Pizza Napolitana", "Tomate, mozzarella y albahaca", 8990.0, true));
                repository.save(new producto(null, 2L, 4L, "Pizza Pepperoni", "Tomate, mozzarella y pepperoni", 9490.0, true));
                repository.save(new producto(null, 2L, 5L, "Garlic Bread", "Pan de ajo con mantequilla", 3490.0, true));
            }
        };
    }
}
