package com.glovoapp.backender;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.lang.reflect.Type;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@WebMvcTest(API.class)
@ContextConfiguration(classes = {API.class})
class APITest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetOrders() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/orders"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andReturn();
        Type type = new TypeToken<List<Order>>() {
        }.getType();
        String content = mvcResult.getResponse().getContentAsString();
        List<Order> orders = new Gson().fromJson(content, type);
        assertEquals(20, orders.size());
    }

    @Test
    void testAvailableOrdersForCourierWithMotorcycleAndWithBox() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/orders/:courier-1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andReturn();
        Type type = new TypeToken<List<OrderVM>>() {
        }.getType();
        String content = mvcResult.getResponse().getContentAsString();
        List<OrderVM> orders = new Gson().fromJson(content, type);
        assertEquals(20, orders.size());
        assertEquals("[OrderVM{id='order-1', description='I want a burger'}, OrderVM{id='order-13', description='2x Pork bao with Fries\n" +
                "2x Kebab with Fries\n" +
                "2x Tuna poke with Salad\n" +
                "1x Pork bao with Fries'}, OrderVM{id='order-15', description='Keys'}, OrderVM{id='order-11', description='1x chicken with Salad\n" +
                "1x Pork bao with Salad\n" +
                "2x Tuna poke with Salad'}, OrderVM{id='order-16', description='Keys'}, OrderVM{id='order-8', description='1x Pizza with Fries\n" +
                "2x Burger with Salad'}, OrderVM{id='order-5', description='1x Burger with Salad'}, OrderVM{id='order-19', description='Keys'}, OrderVM{id='order-10', description='1x Burger with Fries\n" +
                "1x Kebab with Fries\n" +
                "2x Hot dog with Fries'}, OrderVM{id='order-9', description='1x Flamingo'}, OrderVM{id='order-20', description='2x Kebab with Fries'}, OrderVM{id='order-3', description='2x Hot dog with Fries\n" +
                "1x cake'}, OrderVM{id='order-12', description='1x headphones'}, OrderVM{id='order-17', description='1x burger with Fries'}, OrderVM{id='order-14', description='An adult dog'}, OrderVM{id='order-6', description='1x chicken with Salad\n" +
                "2x Burger with Fries'}, OrderVM{id='order-2', description='Keys'}, OrderVM{id='order-4', description='1x Mac & Cheese\n" +
                "2x Burger with Fries\n" +
                "Cheese'}, OrderVM{id='order-7', description='2x Hot dog with Salad\n" +
                "1x Burger with Fries\n" +
                "1x Tuna poke with Fries\n" +
                "2x Pork bao with Fries'}, OrderVM{id='order-18', description='1x Burger with Fries'}]", orders.toString());
    }

    @Test
    void testAvailableOrdersForCourierWithMotorcycleAndWithNoBox() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/orders/:courier-2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andReturn();
        Type type = new TypeToken<List<OrderVM>>() {
        }.getType();
        String content = mvcResult.getResponse().getContentAsString();
        List<OrderVM> orders = new Gson().fromJson(content, type);
        assertEquals(17, orders.size());
        assertEquals("[OrderVM{id='order-1', description='I want a burger'}, OrderVM{id='order-13', description='2x Pork bao with Fries\n" +
                "2x Kebab with Fries\n" +
                "2x Tuna poke with Salad\n" +
                "1x Pork bao with Fries'}, OrderVM{id='order-15', description='Keys'}, OrderVM{id='order-11', description='1x chicken with Salad\n" +
                "1x Pork bao with Salad\n" +
                "2x Tuna poke with Salad'}, OrderVM{id='order-16', description='Keys'}, OrderVM{id='order-5', description='1x Burger with Salad'}, OrderVM{id='order-19', description='Keys'}, OrderVM{id='order-10', description='1x Burger with Fries\n" +
                "1x Kebab with Fries\n" +
                "2x Hot dog with Fries'}, OrderVM{id='order-20', description='2x Kebab with Fries'}, OrderVM{id='order-12', description='1x headphones'}, OrderVM{id='order-17', description='1x burger with Fries'}, OrderVM{id='order-14', description='An adult dog'}, OrderVM{id='order-6', description='1x chicken with Salad\n" +
                "2x Burger with Fries'}, OrderVM{id='order-2', description='Keys'}, OrderVM{id='order-4', description='1x Mac & Cheese\n" +
                "2x Burger with Fries\n" +
                "Cheese'}, OrderVM{id='order-7', description='2x Hot dog with Salad\n" +
                "1x Burger with Fries\n" +
                "1x Tuna poke with Fries\n" +
                "2x Pork bao with Fries'}, OrderVM{id='order-18', description='1x Burger with Fries'}]", orders.toString());
    }

    @Test
    void testAvailableOrdersForCourierWithBicycleAndWithBox() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/orders/:courier-3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andReturn();
        Type type = new TypeToken<List<OrderVM>>() {
        }.getType();
        String content = mvcResult.getResponse().getContentAsString();
        List<OrderVM> orders = new Gson().fromJson(content, type);
        assertEquals(19, orders.size());
        assertEquals("[OrderVM{id='order-1', description='I want a burger'}, OrderVM{id='order-13', description='2x Pork bao with Fries\n" +
                "2x Kebab with Fries\n" +
                "2x Tuna poke with Salad\n" +
                "1x Pork bao with Fries'}, OrderVM{id='order-15', description='Keys'}, OrderVM{id='order-11', description='1x chicken with Salad\n" +
                "1x Pork bao with Salad\n" +
                "2x Tuna poke with Salad'}, OrderVM{id='order-16', description='Keys'}, OrderVM{id='order-8', description='1x Pizza with Fries\n" +
                "2x Burger with Salad'}, OrderVM{id='order-5', description='1x Burger with Salad'}, OrderVM{id='order-19', description='Keys'}, OrderVM{id='order-10', description='1x Burger with Fries\n" +
                "1x Kebab with Fries\n" +
                "2x Hot dog with Fries'}, OrderVM{id='order-9', description='1x Flamingo'}, OrderVM{id='order-20', description='2x Kebab with Fries'}, OrderVM{id='order-3', description='2x Hot dog with Fries\n" +
                "1x cake'}, OrderVM{id='order-12', description='1x headphones'}, OrderVM{id='order-17', description='1x burger with Fries'}, OrderVM{id='order-14', description='An adult dog'}, OrderVM{id='order-6', description='1x chicken with Salad\n" +
                "2x Burger with Fries'}, OrderVM{id='order-2', description='Keys'}, OrderVM{id='order-4', description='1x Mac & Cheese\n" +
                "2x Burger with Fries\n" +
                "Cheese'}, OrderVM{id='order-7', description='2x Hot dog with Salad\n" +
                "1x Burger with Fries\n" +
                "1x Tuna poke with Fries\n" +
                "2x Pork bao with Fries'}]", orders.toString());
    }

    @Test
    void testAvailableOrdersForCourierWithBicycleAndNoBox() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/orders/:courier-4"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andReturn();
        Type type = new TypeToken<List<OrderVM>>() {
        }.getType();
        String content = mvcResult.getResponse().getContentAsString();
        List<OrderVM> orders = new Gson().fromJson(content, type);
        assertEquals(16, orders.size());
        assertEquals("[OrderVM{id='order-1', description='I want a burger'}, OrderVM{id='order-13', description='2x Pork bao with Fries\n" +
                "2x Kebab with Fries\n" +
                "2x Tuna poke with Salad\n" +
                "1x Pork bao with Fries'}, OrderVM{id='order-15', description='Keys'}, OrderVM{id='order-11', description='1x chicken with Salad\n" +
                "1x Pork bao with Salad\n" +
                "2x Tuna poke with Salad'}, OrderVM{id='order-16', description='Keys'}, OrderVM{id='order-5', description='1x Burger with Salad'}, OrderVM{id='order-19', description='Keys'}, OrderVM{id='order-10', description='1x Burger with Fries\n" +
                "1x Kebab with Fries\n" +
                "2x Hot dog with Fries'}, OrderVM{id='order-20', description='2x Kebab with Fries'}, OrderVM{id='order-12', description='1x headphones'}, OrderVM{id='order-17', description='1x burger with Fries'}, OrderVM{id='order-14', description='An adult dog'}, OrderVM{id='order-6', description='1x chicken with Salad\n" +
                "2x Burger with Fries'}, OrderVM{id='order-2', description='Keys'}, OrderVM{id='order-4', description='1x Mac & Cheese\n" +
                "2x Burger with Fries\n" +
                "Cheese'}, OrderVM{id='order-7', description='2x Hot dog with Salad\n" +
                "1x Burger with Fries\n" +
                "1x Tuna poke with Fries\n" +
                "2x Pork bao with Fries'}]", orders.toString());
    }
}
