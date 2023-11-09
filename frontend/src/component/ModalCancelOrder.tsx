import React, { useEffect, useState } from 'react';
import { Button, Input, message, Modal } from 'antd';
import orderService from '../services/orderService';
import { useDispatch } from 'react-redux';

interface Props {
    id: number;
}

const App: React.FC<Props> = ({ id }) => {
    const dispatch = useDispatch();
    const [loading, setLoading] = useState(false);
    const [open, setOpen] = useState(false);
    const [inputValue, setInputValue] = useState('');
    const [error, setError] = useState('');
    const [isUpdate, setIsUpdate] = useState(false);

    const showModal = () => {
        setOpen(true);
    };

    const handleOk = () => {
        if (inputValue.trim() === '') {
            setError('Please enter some text');
            return;
        }

        orderService.cancelOrderById(id, inputValue, dispatch).then(() => {
            console.log('Success: cancel');
            setIsUpdate(true);

        }, (error) => {
            const _content = (error.response && error.response.data)
            console.log(_content);
            message.error("id не найден");
        });

        setLoading(true);
        setTimeout(() => {
            setLoading(false);
            setOpen(false);
        }, 1500);
    };

    const handleCancel = () => {
        setOpen(false);
        setError('');
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setInputValue(e.target.value);
        setError('');
    };

    const handleKeyPress = (event: React.KeyboardEvent<HTMLInputElement>) => {
        if (event.key === 'Enter') {
            handleOk();
        }
    };

    useEffect(() => {
        orderService.getListOrders(dispatch);
        setIsUpdate(false);
    }, [isUpdate, dispatch]);

    return (
        <>
            <Button type="primary" onClick={showModal}>
                Cancel
            </Button>
            <Modal
                visible={open}
                title="Cancel order"
                onOk={handleOk}
                onCancel={handleCancel}
                footer={[
                    <Button key="back" onClick={handleCancel}>
                        Return
                    </Button>,
                    <Button key="submit" type="primary" loading={loading} onClick={handleOk}>
                        Submit
                    </Button>,
                ]}
            >
                <Input value={inputValue} onChange={handleChange} onKeyDown={handleKeyPress} placeholder="Enter text" />
                {error && <p style={{ color: 'red' }}>{error}</p>}
            </Modal>
        </>
    );
};

export default App;