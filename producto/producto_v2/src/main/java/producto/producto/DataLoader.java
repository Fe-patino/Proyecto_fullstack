package producto.producto;

import producto.producto.model.Producto;
import producto.producto.repository.ProductoRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataLoader {

    @Bean
    CommandLineRunner init(ProductoRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Producto(null, 1, 1, "Hamburguesa Clásica", "Carne, lechuga y tomate", 5990.0, true));
                repository.save(new Producto(null, 1, 1, "Hamburguesa BBQ", "Carne, cheddar y BBQ", 6990.0, true));
                repository.save(new Producto(null, 1, 2, "Papas Fritas", "Papas crocantes con sal", 2490.0, true));
                repository.save(new Producto(null, 1, 3, "Bebida 350ml", "Coca-Cola, Sprite o Fanta", 1490.0, true));
                repository.save(new Producto(null, 2, 4, "Pizza Napolitana", "Tomate, mozzarella y albahaca", 8990.0, true));
                repository.save(new Producto(null, 2, 4, "Pizza Pepperoni", "Tomate, mozzarella y pepperoni", 9490.0, true));
                repository.save(new Producto(null, 2, 5, "Garlic Bread", "Pan de ajo con mantequilla", 3490.0, true));
            }
        };
    }
}
