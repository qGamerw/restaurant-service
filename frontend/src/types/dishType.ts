export const dishPath = '/';


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

export interface Category {
    id: number;
    category: string;
}

export interface Dish {
    id: number;
    name: string;
    description: string;
    urlImage: string;
    category: Category;
    price: number;
    weight: number;
}