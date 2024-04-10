import React, {useEffect, useState} from 'react';
import {Button, Input, Select, Table, TableColumnsType} from 'antd';
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "../store";
import {ChangeCategory, DishData, DishTableData} from "../types/dishType";
import dishService from "../services/dishService";
import ModalOpenDish from "../component/ModalOpenDish";
import ModalNewDish from "../component/ModalNewDish";

const DishesPage = () => {
    const dispatch = useDispatch();

    const allDishes = useSelector((store: RootState) => store.dishes.allDishes);
    const branchDishes = useSelector((store: RootState) => store.dishes.allBranchDishes);
    const dishDefault = useSelector((store: RootState) => store.dishes.dish);
    const categoryList = useSelector((store: RootState) => store.category.category);

    const [modalNewDish, setModalNewDish] = useState(false);
    const [modalDishOpen, setModalDishOpen] = useState(false);
    const [isUpdate, setIsUpdate] = useState(false);
    const [isAllDish, setIsAllDish] = useState(true);
    const [searchQuery, setSearchQuery] = useState("");
    const [categoryFilter, setCategoryFilter] = useState(-1);

    const [selectedDish, setSelectedDish] = useState<DishData>(dishDefault);

    useEffect(() => {
        if (isAllDish && (allDishes === null || allDishes.length === 0)) {
            dishService.dishGetListByPage(0, 50, dispatch);
        } else if (!isAllDish && (branchDishes === null || branchDishes.length === 0 || isUpdate)) {
            dishService.dishGetByBranch(dispatch);
        }

        if (categoryList === null || categoryList.length === 0) {
            dishService.dishGetCategoryList(dispatch);
        }

        setIsUpdate(false);

    }, [isUpdate, isAllDish, allDishes, branchDishes, categoryList, dishDefault, dispatch]);

    function viewBranchDishes() {
        setIsAllDish(!isAllDish);
    }

    function handleCategoryFilter(value: string) {
        setCategoryFilter(Number.parseInt(value));
    }

    function handleSearch(e: React.ChangeEvent<HTMLInputElement>) {
        setSearchQuery(e.target.value);
    }

    const filteredDishes = (isAllDish ? allDishes : branchDishes).filter(dish =>
        dish.name?.toLowerCase().includes(searchQuery.toLowerCase()) && (categoryFilter === -1 || dish.category.id === categoryFilter)
    );

    const handleOpenModal = (item: DishData) => {
        setSelectedDish(item);
        setModalDishOpen(true);
    };

    const columns: TableColumnsType<DishTableData> = [
        {title: 'Название блюда', dataIndex: 'name', key: 'name'},
        {title: 'Категория', dataIndex: ['category', 'category'], key: 'category'},
        {title: 'Вес', dataIndex: 'weight', key: 'weight'},
        {title: 'Цена', dataIndex: 'price', key: 'price'},
        {
            title: 'Открыть', dataIndex: '', key: 'x', render: (item) =>
                <Button type='primary' key={item.id} onClick={() => handleOpenModal(item)}>Подробнее</Button>,
        },
    ];

    const transformedData: DishTableData[] = filteredDishes.map(dish => {
        return {
            ...dish,
            category: {
                id: dish.category.id,
                category: ChangeCategory(dish.category.category),
            },
            key: dish.id
        };
    });

    return (
        <>
            <Button type="primary" style={{marginRight: 10, marginTop: 10, minWidth: 220}} onClick={viewBranchDishes}>
                {isAllDish ? 'Показать блюда в ресторане' : 'Показать все блюда'}</Button>

            <Button type="primary" style={{marginRight: 10, marginTop: 10}} onClick={() => setModalNewDish(true)}>
                Новое блюдо</Button>
            <Input type="text" value={searchQuery} onChange={handleSearch} placeholder="Поиск по названию..."
                   style={{marginTop: 10}}/>
            <Select style={{marginTop: 10, width: 200}} onChange={handleCategoryFilter} defaultValue={"Все"}>
                <Select.Option key={-1} value={-1}>
                    Все </Select.Option>
                {categoryList.map((item) => (
                    <Select.Option key={item.id} value={item.id}>
                        {ChangeCategory(item.category)}
                    </Select.Option>
                ))}
            </Select>

            <ModalOpenDish
                modalNewDish={modalDishOpen}
                isAllDish={isAllDish}
                dish={selectedDish}
                dispatch={dispatch}
                setIsUpdate={setIsUpdate}
                isUpdate={isUpdate}
                onClose={() => setModalDishOpen(false)}/>

            <ModalNewDish
                category={categoryList}
                modalNewDish={modalNewDish}
                onClose={() => setModalNewDish(false)}/>

            <div style={{marginTop: 35}}>
                <Table
                    columns={columns}
                    expandable={{
                        expandedRowRender: (record: DishTableData) => <p style={{margin: 0}}>{record.description}</p>,
                    }}
                    dataSource={transformedData}
                    pagination={{pageSize: 8}}
                />
            </div>
        </>
    );
};

export default DishesPage;