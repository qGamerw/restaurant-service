import React, {useEffect, useState} from 'react';
import {Badge, Button, Collapse, message, Tabs} from 'antd';
import orderService from "../services/orderService";
import {useDispatch, useSelector} from "react-redux";
import ModalCancelOrder from "../component/ModalCancelOrder";

const {Panel} = Collapse;

const OrderPage: React.FC = () => {
    const dispatch = useDispatch();
    const allOrders = useSelector((store: any) => store.order.allOrders);
    const [isUpdate, setIsUpdate] = useState(false);

    useEffect(() => {
        orderService.getListOrders(dispatch);
    }, []);

    const onAccept = (id: number) => {

        orderService.updateOrderStatusById(id, 'COOKING', dispatch).then(() => {
            console.log('Success: update');
            setIsUpdate(true);

        }, (error) => {
            const _content = (error.response && error.response.data)
            console.log(_content);
            message.error("id не найден");
        })
    };

    const onEndCooking = (id: number) => {

        orderService.updateOrderStatusById(id, 'COOKED', dispatch).then(() => {
            console.log('Success: update');
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

    const itemsReview = allOrders.filter((item: any) => item.status === 'REVIEW').map((order: any) => (
        {
            key: order.id,
            label: (
                <div>
                    {/*<Button type="primary" style={{ marginRight: '10px' }} />*/}
                    <p>{order.address}</p>
                </div>
            ),
            items: (
                <div>
                    <p>{order.clientName}</p>
                    <p>{order.clientPhone}</p>
                    <p>{order.description}</p>
                    <p>{order.branchAddress}</p>
                    <p>{order.orderTime}</p>
                    <p>Dishes:</p>
                    {order.dishesOrders.map((order: any) => (<p>{order.dishName}</p>))}
                    <Button type="primary" onClick={() => onAccept(order.id)} style={{marginRight: 10}}>Accept</Button>
                    <ModalCancelOrder id={order.id} />
                </div>
            ),
        }
    ));

    const itemsCooking = allOrders.filter((item: any) => item.status === 'COOKING').map((order: any) => (
        {
            key: order.id,
            label: (
                <div>
                    {/*<Button type="primary" style={{ marginRight: '10px' }} />*/}
                    <p>{order.address}</p>
                </div>
            ),
            items: (
                <div>
                    <p>{order.clientName}</p>
                    <p>{order.clientPhone}</p>
                    <p>{order.description}</p>
                    <p>{order.branchAddress}</p>
                    <p>{order.orderTime}</p>
                    <p>Dishes:</p>
                    {order.dishesOrders.map((order: any) => (<p>{order.dishName}</p>))}

                    <Button type="primary" onClick={() => onEndCooking(order.id)} >Complete</Button>
                </div>
            ),
        }
    ));

    const itemsCooked = allOrders.filter((item: any) => item.status === 'COOKED').map((order: any) => (
        {
            key: order.id,
            label: (
                <div>
                    {/*<Button type="primary" style={{ marginRight: '10px' }} />*/}
                    <p>{order.address}</p>
                </div>
            ),
            items: (
                <div>
                    <p>{order.clientName}</p>
                    <p>{order.clientPhone}</p>
                    <p>{order.description}</p>
                    <p>{order.branchAddress}</p>
                    <p>{order.orderTime}</p>
                    <p>Dishes:</p>
                    {order.dishesOrders.map((order: any) => (<p>{order.dishName}</p>))}
                </div>
            ),
        }
    ));

    const itemsAll = allOrders.map((order: any) => (
        {
            key: order.id,
            label: (
                <div>
                    {/*<Button type="primary" style={{ marginRight: '10px' }} />*/}
                    <p>{order.address}</p>
                </div>
            ),
            items: (
                <div>
                    <p>{order.clientName}</p>
                    <p>{order.clientPhone}</p>
                    <p>{order.description}</p>
                    <p>{order.branchAddress}</p>
                    <p>{order.orderTime}</p>
                    <p>Dishes:</p>
                    {order.dishesOrders.map((order: any) => (<p>{order.dishName}</p>))}
                </div>
            ),
        }
    ));

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
                            <Panel key={item.key} header={item.label}>
                                {/*<Skeleton active loading={false}>*/}
                                {item.items}
                                {/*</Skeleton>*/}
                            </Panel>
                        ))}
                    </Collapse>
                </Tabs.TabPane>

                <Tabs.TabPane tab={<Badge count={countCooking} offset={[10, 0]}>Cooking</Badge>} key={'2'}>
                    <Collapse accordion>
                        {itemsCooking.map((item: any) => (
                            <Panel key={item.key} header={item.label}>
                                {/*<Skeleton active loading={false}>*/}
                                {item.items}
                                {/*</Skeleton>*/}
                            </Panel>
                        ))}
                    </Collapse>
                </Tabs.TabPane>

                <Tabs.TabPane tab={<Badge count={countCooked} offset={[10, 0]}>Cooked</Badge>} key={'3'}>
                    <Collapse accordion>
                        {itemsCooked.map((item: any) => (
                            <Panel key={item.key} header={item.label}>
                                {/*<Skeleton active loading={false}>*/}
                                {item.items}
                                {/*</Skeleton>*/}
                            </Panel>
                        ))}
                    </Collapse>
                </Tabs.TabPane>

                <Tabs.TabPane tab={<Badge count={countAll} offset={[10, 0]}>All</Badge>} key={'4'}>
                    <Collapse accordion>
                        {itemsAll.map((item: any) => (
                            <Panel key={item.key} header={item.label}>
                                {/*<Skeleton active loading={false}>*/}
                                {item.items}
                                {/*</Skeleton>*/}
                            </Panel>
                        ))}
                    </Collapse>
                </Tabs.TabPane>

            </Tabs>
        </div>
    );
};

export default OrderPage;