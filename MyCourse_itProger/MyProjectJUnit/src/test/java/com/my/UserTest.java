package com.my;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    @Nested
    @DisplayName("Проверка создания объектов")
    class UserCreate {
        User u;

        @Test
        @DisplayName("Объект User - без параметров")
        public void testEmptyUser() {
            u = new User();
            assertEquals(null, u.getLogin());
            assertEquals(null, u.getEmail());
        }

        @Test
        @DisplayName("Объект User - с двумя параметрами")
        public void testCreateUser() {
            u = new User("Alexis", "alexis@mail.ru");
            assertEquals("Alexis", u.getLogin());
            assertEquals("alexis@mail.ru", u.getEmail());
        }
    }

    @Nested
    @DisplayName("Проверка установки значений")
    class UserSetUp {
        User u = new User();

        @Test
        @DisplayName("Установка Login")
        public void testLogin() {
            u.setLogin("Maximus");
            assertEquals("Maximus", u.getLogin());
        }

        @Test
        @DisplayName("Установка Email")
        public void testEmail() {
            u.setEmail("maxim@mail.ru");
            assertEquals("maxim@mail.ru", u.getEmail());
        }
    }

    @Nested
    @DisplayName("Проверка валидации значений")
    class UserValid {
        User u = new User();

        @Test
        @DisplayName("Login - 3 знака")
        public void testLogin3() {
            u.setLogin("Max");
            assertEquals(null, u.getLogin());
        }

        @Test
        @DisplayName("Login - 4 знака")
        public void testLogin4() {
            u.setLogin("Alex");
            assertEquals(null, u.getLogin());
        }

        @Test
        @DisplayName("Login - 5 знаков")
        public void testLogin5() {
            u.setLogin("Clear");
            assertEquals("Clear", u.getLogin());
        }


        @Test
        @DisplayName("Email - без знака \"@\"")
        public void testEmail1() {
            u.setEmail("alexmail.ru");
            assertEquals(null, u.getEmail());
        }

        @Test
        @DisplayName("Email - со знаком \"@\" и \".\"")
        public void testEmail2() {
            u.setEmail("alex@mail.ru");
            assertEquals("alex@mail.ru", u.getEmail());
        }

        @Test
        @DisplayName("Email - со знаком \"@\", но без \".\"")
        public void testEmail3() {
            u.setEmail("alex@mailru");
            assertEquals(null, u.getEmail());
        }

        @Test
        @DisplayName("Email - со знаком \"@\" в начале строки")
        public void testEmail4() {
            u.setEmail("@alexmail.ru");
            assertEquals(null, u.getEmail());
        }
    }
}