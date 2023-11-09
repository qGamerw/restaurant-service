import React, {useEffect, useState} from 'react';
import {Badge, Button, Collapse, message, Tabs} from 'antd';
import orderService from "../services/orderService";
import {useDispatch, useSelector} from "react-redux";
import ModalCancelOrder from "../component/ModalCancelOrder";
import {RootState} from "../store";

const {Panel} = Collapse;

interface DishesOrder {
    id: number;
    dish_id: number;
    dish_name: number;
    quantity: number;
    order_id: number;
}

interface BranchOffice {
    id: number,
    address: string,
    status: string,
    nameCity: string
}

interface User {
    accessToken: string,
    branchOffice: BranchOffice,
    email: string
    employeeName: string,
    id: number,
    position: any,
    type: string
}

const OrderPage: React.FC = () => {
    const dispatch = useDispatch();
    const allOrders = useSelector((store: RootState) => store.orders.allOrders);
    const userStr = localStorage.getItem('user');
    const [isUpdate, setIsUpdate] = useState(false);
    const currentDate = new Date();
    const onAccept = (id: number) => {

        let branchOffice = null;
        if (userStr) {
            branchOffice = (JSON.parse(userStr)).branchOffice;
            orderService.updateOrderStatusById(id, 'COOKING', branchOffice.id, branchOffice.address, dispatch).then(() => {

                console.log('Success: Accept');
                setIsUpdate(true);

            }, (error) => {
                const _content = (error.response && error.response.data)
                console.log(_content);
                message.error("id не найден");
            })
        }
    };

    const onEndCooking = (id: number, branchId: number, branchAddress: string) => {

        orderService.updateOrderStatusById(id, 'COOKED', branchId, branchAddress, dispatch).then(() => {
            console.log('Success: EndCooking');
            setIsUpdate(true);

        }, (error) => {
            const _content = (error.response && error.response.data)
            console.log(_content);
            message.error("id не найден");
        })
    };

    useEffect(() => {
        orderService.getListOrders(dispatch);
        setIsUpdate(false);
    }, [isUpdate]);

    function mapOrderToItems(order: any) {
        return {
            key: order.id,
            label: (
                <>
                    <p><b>Address: </b>{order.address}</p>
                    <p><b>Order time: </b>{new Date(Date.parse(order.orderTime)).toLocaleString('RU')}</p>
                    <p><b>Waiting
                        order: </b>{Math.floor((currentDate.getTime() - new Date(order.orderTime).getTime()) / 60000)} min
                    </p>
                </>
            ),
            items: (
                <>
                    <p><b>Client name:</b> {order.clientName}</p>
                    <p><b>Client phone:</b> {order.clientPhone}</p>
                    <p><b>Description:</b> {order.description}</p>
                    <Collapse accordion>
                        <Panel key={order.id + 0.5} header={<b>Dishes</b>} showArrow={false}>
                            {order.dishesOrders.map((order: DishesOrder) => (
                                <p><b>Name:</b> {order.dish_name}, <b>Quantity:</b> {order.quantity}</p>
                            ))}
                        </Panel>
                    </Collapse>
                </>
            ),
        };
    }

    const itemsReview = allOrders.filter((item: any) => item.status === 'REVIEW').map((order: any) => (
        {
            key: order.id + 0.01,
            label: (
                <>
                    <p><b>Address: </b>{order.address}</p>
                    <p><b>Order time: </b>{new Date(Date.parse(order.orderTime)).toLocaleString('RU')}</p>
                    <p><b>Waiting
                        order: </b>{Math.floor((currentDate.getTime() - new Date(order.orderTime).getTime()) / 60000)} min
                    </p>
                </>
            ),
            items: (
                <>
                    <p><b>Client name:</b> {order.clientName}</p>
                    <p><b>Client phone:</b> {order.clientPhone}</p>
                    <p><b>Description:</b> {order.description}</p>
                    <Collapse accordion>
                        <Panel key={order.id + 0.1} header={<b>Dishes</b>} showArrow={false}>
                            {order.dishesOrders.map((dish: DishesOrder) => (
                                <p><b>Name:</b> {dish.dish_name}, <b>Quantity:</b> {dish.quantity}</p>))}
                        </Panel>
                    </Collapse>
                    <Button type="primary" onClick={() => onAccept(order.id)}
                            style={{marginRight: 10, marginTop: 10}}>Accept</Button>
                    <ModalCancelOrder id={order.id}/>
                </>
            ),
        }
    ));

    const itemsCooking = allOrders.filter((item: any) => item.status === 'COOKING').map((order: any) => (
        {
            key: order.id + 0.02,
            label: (
                <div>
                    <p><b>Address: </b>{order.address}</p>
                    <p><b>Order time: </b>{new Date(Date.parse(order.orderCookingTime)).toLocaleString('RU')}</p>
                    <p>
                        <b>Cooking: </b>{Math.floor((currentDate.getTime() - new Date(order.orderCookingTime).getTime()) / 60000)} min
                    </p>
                </div>
            ),
            items: (
                <div>
                    <p><b>Client name:</b> {order.clientName}</p>
                    <p><b>Client phone:</b> {order.clientPhone}</p>
                    <p><b>Description:</b> {order.description}</p>
                    <Collapse accordion>
                        <Panel key={order.id} header={<b>Dishes</b>} showArrow={false}>
                            {order.dishesOrders.map((dish: DishesOrder) => (
                                <p><b>Name:</b> {dish.dish_name}, <b>Quantity:</b> {dish.quantity}</p>))}
                        </Panel>
                    </Collapse>
                    <Button type="primary" onClick={() => onEndCooking(order.id, order.branchId, order.branchAddress)}
                            style={{marginTop: 10}}>Complete</Button>
                </div>
            ),
        }
    ));

    const itemsCooked = allOrders.filter((item: any) => item.status === 'COOKED').map((order: any) => (
        {
            key: order.id,
            label: (
                <div>
                    <p><b>Address: </b>{order.address}</p>
                    <p><b>Order time: </b>{new Date(Date.parse(order.orderCookedTime)).toLocaleString('RU')}</p>
                    <p>
                        <b>Cooked: </b>{Math.floor((currentDate.getTime() - new Date(order.orderCookedTime).getTime()) / 60000)} min
                    </p>
                </div>
            ),
            items: (
                <div>
                    <p><b>Client name:</b> {order.clientName}</p>
                    <p><b>Client phone:</b> {order.clientPhone}</p>
                    <p><b>Description:</b> {order.description}</p>
                    <Collapse accordion>
                        <Panel key={order.id} header={<b>Dishes</b>} showArrow={false}>
                            {order.dishesOrders.map((dish: DishesOrder) => (
                                <p><b>Name:</b> {dish.dish_name}, <b>Quantity:</b> {dish.quantity}</p>))}
                        </Panel>
                    </Collapse>
                </div>
            ),
        }
    ));

    const itemsAll = allOrders.map(mapOrderToItems);

    const countReview: number = itemsReview.length;
    const countCooking: number = itemsCooking.length;
    const countCooked: number = itemsCooked.length;
    const countAll: number = itemsAll.length;

    return (
        <div style={{overflow: 'auto'}}>
            <Tabs style={{marginBottom: 24, marginTop: 20,}} tabPosition={'left'}>
                <Tabs.TabPane tab={<Badge count={countReview} offset={[10, 0]}>Review</Badge>} key={'1'}>
                    <Collapse accordion>
                        {itemsReview.map((item: any) => (
                            <Panel key={item.key} header={item.label} showArrow={false}>
                                {item.items}
                            </Panel>
                        ))}
                    </Collapse>
                </Tabs.TabPane>

                <Tabs.TabPane tab={<Badge count={countCooking} offset={[10, 0]}>Cooking</Badge>} key={'2'}>
                    <Collapse accordion>
                        {itemsCooking.map((item: any) => (
                            <Panel key={item.key} header={item.label} showArrow={false}>
                                {item.items}
                            </Panel>
                        ))}
                    </Collapse>
                </Tabs.TabPane>

                <Tabs.TabPane tab={<Badge count={countCooked} offset={[10, 0]}>Cooked</Badge>} key={'3'}>
                    <Collapse accordion>
                        {itemsCooked.map((item: any) => (
                            <Panel key={item.key} header={item.label} showArrow={false}>
                                {item.items}
                            </Panel>
                        ))}
                    </Collapse>
                </Tabs.TabPane>

                <Tabs.TabPane tab={<Badge count={countAll} offset={[10, 0]}>All</Badge>} key={'4'}>
                    <Collapse accordion>
                        {itemsAll.map((item: any) => (
                            <Panel key={item.key} header={item.label} showArrow={false}>
                                {item.items}
                            </Panel>
                        ))}
                    </Collapse>
                </Tabs.TabPane>

            </Tabs>
        </div>
    );
};

export default OrderPage;