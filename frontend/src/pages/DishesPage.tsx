import React, {useEffect, useState} from 'react';
import {Button, Card, Col, Input, Row, Select, Typography} from 'antd';
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "../store";
import logo from "../images/logo.jpg"
import {Dish, UpdateDish} from "../types/types";
import ModalNewDish from "../component/ModalNewDish";
import {useNavigate} from "react-router-dom";
import {ChangeCategory} from "../types/dishType";

const {Paragraph} = Typography;

// не смотрел
const DishesPage = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const allDishes = useSelector((store: RootState) => store.dishes.allDishes);
    const branchDishes = useSelector((store: RootState) => store.dishes.allBranchDishes);
    const categoryList = useSelector((store: RootState) => store.category.category);

    const [modal2Open, setModal2Open] = useState(false);
    const [isUpdate, setIsUpdate] = useState(false);
    const [isAllDish, setIsAllDish] = useState(false);
    const [searchQuery, setSearchQuery] = useState("");
    const [categoryFilter, setCategoryFilter] = useState<number>(-1);

    // useEffect(() => {
    //     const f = sessionStorage.getItem('auth-date') != null;
    //     console.log(f);
    //
    // }, []);


    function updateDish(dish: Dish, newStr: UpdateDish) {
        // let updatedDish: Dish;
        //
        // if (newStr.name === 'price' || newStr.name === 'weight') {
        //     updatedDish = {...dish, [newStr.name]: parseInt(newStr.newName)};
        // } else {
        //     updatedDish = {...dish, [newStr.name]: newStr.newName};
        // }
        //
        // dishService.updateDish(updatedDish, dispatch)
        //     .then(() => {message.warning("Добавление успешно выполнилось!");}
        //     , (error) => {
        //     const _content = (error.response && error.response.data)
        //     console.log(_content);
        //     message.error("Недостаточно прав!");
        // });
    }

    // useEffect(() => {
    //     if (isAllDish) {
    //         dishService.getListDish(0, 50, dispatch);
    //     } else {
    //         dishService.getListDishByBranch(dispatch);
    //     }
    //     setIsUpdate(false);
    //
    // }, [isUpdate, dispatch]);

    function viewBranchDishes() {
        setIsAllDish(false);
    }

    function viewAllDishes() {
        setIsAllDish(true);
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



    return (
        <>
            <ModalNewDish modal2Open={modal2Open} category={categoryList} onClose={() => {
                setModal2Open(false)
            }}/>

            <Button type="primary" style={{marginRight: 10, marginTop: 10}} onClick={viewBranchDishes}>
                Показать блюда в ресторане</Button>
            <Button type="primary" style={{marginRight: 10, marginTop: 10}} onClick={viewAllDishes}>
                Показать все блюда</Button>
            <Button type="primary" style={{marginRight: 10, marginTop: 10}} onClick={() => setModal2Open(true)}>
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
            <Row gutter={[16, 16]} style={{marginTop: 20, minWidth: 1320}}>
                {filteredDishes.map(dish => (
                    <Col span={8} key={dish.id}>
                        <Card key={dish.id} style={{
                            width: '400px',
                            boxShadow: '3px 3px 15px gray',
                            borderRadius: '2vh',
                            height: 'auto'
                        }}>
                            <img src={dish.urlImage ? dish.urlImage : logo} alt={"Изображение блюда:" + dish.name}
                                 style={{
                                     borderRadius: '3vh',
                                     maxWidth: '350px',
                                     maxHeight: '300px'
                                 }}
                            />

                            <p><b>Название блюда: </b><Paragraph
                                editable={isAllDish ? false : {
                                    onChange: (newStr) => updateDish(dish, {
                                        name: 'name',
                                        newName: newStr
                                    })
                                }}>{dish.name}</Paragraph>
                            </p>
                            <p><b>Цена: </b><Paragraph
                                editable={isAllDish ? false : {
                                    onChange: (newStr) => updateDish(dish, {
                                        name: 'price',
                                        newName: newStr
                                    })
                                }}>{dish.price}</Paragraph>
                            </p><br/>
                            <p><b>Спецификация блюда: </b></p>
                            <p><b>Категория: </b>{ChangeCategory(dish.category.category)}</p>
                            <p><b>Описание: </b><Paragraph
                                editable={isAllDish ? false : {
                                    onChange: (newStr) => updateDish(dish, {
                                        name: 'description',
                                        newName: newStr
                                    })
                                }}>{dish.description}</Paragraph>
                            </p>
                            <p><b>Вес: </b><Paragraph
                                editable={isAllDish ? false : {
                                    onChange: (newStr) => updateDish(dish, {
                                        name: 'weight',
                                        newName: newStr
                                    })
                                }}>{dish.weight}</Paragraph>
                            </p>

                        </Card>
                    </Col>
                ))}
            </Row>
        </>
    );
};

export default DishesPage;