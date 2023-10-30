import React, {useEffect, useState} from 'react';
import {Button, Card, Col, Image, Input, Row, Select, Typography} from 'antd';
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "../store";
import dishService from "../services/dishService";
import logo from "../logo.svg"
import categoryService from "../services/categoryService";

const {Paragraph} = Typography;

interface Category {
    id: number;
    category: string;
}

interface UpdateDish {
    name: string;
    newName: string;
}

interface Dish {
    id: number;
    name: string;
    description: string;
    urlImage: string;
    category: Category;
    price: number;
    weight: number;
}

const DishesPage = () => {
    const dispatch = useDispatch();

    const allDishes = useSelector((store: RootState) => store.dishes.allDishes);
    const branchDishes = useSelector((store: RootState) => store.dishes.allBranchDishes);
    const categoryList = useSelector((store: RootState) => store.category.category);

    const [isUpdate, setIsUpdate] = useState(false);
    const [isAllDish, setIsAllDish] = useState(false);
    const [searchQuery, setSearchQuery] = useState("");
    const [categoryFilter, setCategoryFilter] = useState<number>(-1);

    useEffect(() => {
        categoryService.getListDishByBranch(dispatch);
        dishService.getListDish(0, 50, dispatch);
    }, []);


    const updateDish = (dish: Dish, newStr: UpdateDish) => {
        let updatedDish: Dish;

        if (newStr.name === 'price' || newStr.name === 'weight') {
            updatedDish = {...dish, [newStr.name]: parseInt(newStr.newName)};
        } else {
            updatedDish = {...dish, [newStr.name]: newStr.newName};
        }

        dishService.updateDish(updatedDish, dispatch)
            .then(() => setIsUpdate(true));
    };

    useEffect(() => {
        if (isAllDish){
            dishService.getListDish(0, 50, dispatch);
        } else {
            dishService.getListDishByBranch(dispatch);
        }
        setIsUpdate(false);

    }, [isUpdate, dispatch]);

    const viewBranchDishes = () => {
        setIsAllDish(false);
    };

    const viewAllDishes = () => {
        setIsAllDish(true);
    };

    const handleCategoryFilter = (value: string) => {
        setCategoryFilter(Number.parseInt(value));
    };

    const handleSearch = (e: React.ChangeEvent<HTMLInputElement>) => {
        setSearchQuery(e.target.value);
    };

    const filteredDishes = (isAllDish ? allDishes : branchDishes).filter(dish =>
        dish.name?.toLowerCase().includes(searchQuery.toLowerCase()) && (categoryFilter === -1 || dish.category.id === categoryFilter)
    );

    return (
        <>
            <Button type="primary" style={{marginRight: 10, marginTop: 10}} onClick={viewBranchDishes}>Показать блюда в ресторане</Button>
            <Button type="primary" style={{marginRight: 10, marginTop: 10}} onClick={viewAllDishes} >Показать все блюда</Button>
            <Input type="text" value={searchQuery} onChange={handleSearch} placeholder="Поиск..." style={{marginTop: 10}} />
            <Select style={{ marginTop: 10, width: 200 }} onChange={handleCategoryFilter} defaultValue={"All"} >
                <Select.Option key={-1} value={-1} >
                    All
                </Select.Option>
                {categoryList.map((item) => (
                    <Select.Option key={item.id} value={item.id}>
                        {item.category}
                    </Select.Option>
                ))}
            </Select>
            <Row gutter={[16, 16]} style={{marginTop: 20}}>
                {filteredDishes.map(dish => (
                    <Col span={8} key={dish.id}>
                        <Card hoverable key={dish.id}>
                            <Image width={200} src={logo} style={{
                                display: "flex",
                                justifyContent: "center",
                                alignItems: "center",
                                height: '10vh'
                            }}/>

                            <p><b>Name dish: </b><Paragraph
                                editable={isAllDish ? false :{
                                    onChange: (newStr) => updateDish(dish, {
                                        name: 'name',
                                        newName: newStr
                                    })
                                }}>{dish.name}</Paragraph>
                            </p>
                            <p>Specifications: </p>
                            <p><b>Category: </b>{dish.category.category}</p>
                            <p><b>Description: </b><Paragraph
                                editable={isAllDish ? false :{
                                    onChange: (newStr) => updateDish(dish, {
                                        name: 'description',
                                        newName: newStr
                                    })
                                }}>{dish.description}</Paragraph>
                            </p>
                            <p><b>Weight: </b><Paragraph
                                editable={isAllDish ? false :{
                                    onChange: (newStr) => updateDish(dish, {
                                        name: 'weight',
                                        newName: newStr
                                    })
                                }}>{dish.weight}</Paragraph>
                            </p>
                            <p><b>Price: </b><Paragraph
                                editable={isAllDish ? false :{
                                    onChange: (newStr) => updateDish(dish, {
                                        name: 'price',
                                        newName: newStr
                                    })
                                }}>{dish.price}</Paragraph>
                            </p>
                        </Card>
                    </Col>
                ))}
            </Row>
        </>
    );
};

export default DishesPage;