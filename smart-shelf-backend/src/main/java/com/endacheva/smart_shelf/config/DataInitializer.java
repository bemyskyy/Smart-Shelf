package com.endacheva.smart_shelf.config;

import com.endacheva.smart_shelf.model.Item;
import com.endacheva.smart_shelf.model.User;
import com.endacheva.smart_shelf.model.enums.ItemStatus;
import com.endacheva.smart_shelf.repository.ItemRepository;
import com.endacheva.smart_shelf.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            log.info("База данных уже инициализирована, пропускаем загрузку тестовых данных.");
            return;
        }

        log.info("Загрузка тестовых пользователей и инвентаря...");

        User maria = User.builder()
                .username("maria_endacheva")
                .password(passwordEncoder.encode("12345"))
                .build();

        User petr = User.builder()
                .username("petr_popkin")
                .password(passwordEncoder.encode("12345"))
                .build();

        userRepository.saveAll(List.of(maria, petr));

        Item mariaItem1 = Item.builder()
                .title("Книга 'Чистая архитектура'")
                .description("Роберт Мартин. Состояние идеальное, прочитана один раз.")
                .owner(maria)
                .status(ItemStatus.AVAILABLE)
                .build();

        Item mariaItem2 = Item.builder()
                .title("Перфоратор Makita")
                .description("Мощный, с набором буров. Возвращать чистым!")
                .owner(maria)
                .status(ItemStatus.AVAILABLE)
                .build();

        Item petrItem1 = Item.builder()
                .title("Настольная игра 'Каркассон'")
                .description("Полный комплект с двумя дополнениями. Отличная игра на вечер.")
                .owner(petr)
                .status(ItemStatus.AVAILABLE)
                .build();

        Item petrItem2 = Item.builder()
                .title("Палатка 2-х местная")
                .description("Легкая палатка для походов выходного дня. Вес 2.5 кг.")
                .owner(petr)
                .status(ItemStatus.AVAILABLE)
                .build();

        itemRepository.saveAll(List.of(mariaItem1, mariaItem2, petrItem1, petrItem2));

        log.info("✅ Тестовые данные успешно загружены! Пароль для входа: 12345");
    }
}
