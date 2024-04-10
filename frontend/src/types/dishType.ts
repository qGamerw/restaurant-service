import React from "react";
import {AnyAction} from "@reduxjs/toolkit";
import {Dispatch} from "redux";

export const dishPath = '/';
export const dishAPIPath = "dishes"
export const categoryAPIPath = "categories"


export function ChangeCategory(str: string) {
    switch (str) {
        case 'SALAD':
            return 'Салат';
        case 'ROLLS':
            return 'Роллы';
        case 'SECOND_COURSES':
            return 'Вторые блюда';
        case 'PIZZA':
            return 'Пицца';
        case 'DRINKS':
            return 'Напитки';
        default:
            return 'None';
    }
}

export function ChangeCategoryRevers(str: string) {
    switch (str) {
        case 'Салат':
            return 'SALAD';
        case 'Роллы':
            return 'ROLLS';
        case 'Вторые блюда':
            return 'SECOND_COURSES';
        case 'Пицца':
            return 'PIZZA';
        case 'Напитки':
            return 'DRINKS';
        default:
            return 'None';
    }
}

export interface DishCategory {
    id: number;
    category: string;
}

export interface DishData {
    id: number;
    name: string;
    description: string;
    urlImage: string;
    category: DishCategory;
    price: number;
    weight: number;
}
export interface DishNewData {
    name: string;
    description: string;
    urlImage: string;
    category: {id: number};
    price: number;
    weight: number;
}

export interface DishTableData {
    key: React.Key;
    id: number;
    name: string;
    description: string;
    urlImage: string;
    category: DishCategory;
    price: number;
    weight: number;
}

export interface DishUpdateName {
    name: string;
    newName: string;
}

export interface ModalDishNewProperty {
    modalNewDish: boolean;
    isAllDish: boolean;
    dispatch: Dispatch<AnyAction>;
    dish: DishData;
    onClose: () => void;
    setIsUpdate: React.Dispatch<React.SetStateAction<boolean>>,
    isUpdate: boolean
}

export interface ModalDishProperty {
    modalNewDish: boolean;
    category: DishCategory[];
    onClose: () => void;
}

export interface AddNewDish {
    dispatch: Dispatch;
    category: DishCategory[];
}