package ru.sber.model;

import lombok.Data;

import java.util.List;

@Data
public class ListDishes {
    private List<DishId> listDishesId;
}
