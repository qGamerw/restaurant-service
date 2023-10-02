import {LockOutlined, UserOutlined} from '@ant-design/icons';
import {Button, Form, Input, message, Select} from 'antd';
import {login} from "../slices/authSlice";
import {useDispatch} from "react-redux";
import {useNavigate} from "react-router-dom";
import authService from "../services/auth.service";

const {} = Select;

const formItemLayout = {
    labelCol: {
        xs: {
            span: 24,
        },
        sm: {
            span: 8,
        },
    },
    wrapperCol: {
        xs: {
            span: 24,
        },
        sm: {
            span: 16,
        },
    },
};

const tailFormItemLayout = {
    wrapperCol: {
        xs: {
            span: 24,
            offset: 0,
        },
        sm: {
            span: 16,
            offset: 8,
        },
    },
};
const LoginPage = () => {
    const [form] = Form.useForm();
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const onFinish = (values) => {
        authService.login(values).then((user) => {
            console.log(user)
            dispatch(login(user))

            if (user){
                const handleButtonClick = () => {
                    setTimeout(() => {
                        message.success('Я надеюсь, что ты выполнишь хотя бы одну задачу!', 3);
                    }, 100);
                };
                handleButtonClick();
                navigate("/all-task");
            }
        })
    };

    return (
        <Form
            {...formItemLayout}
            form={form}
            name="register"
            className="login-form"
            onFinish={onFinish}
            style={{
                padding: '20px 50px',
                width: '30%',
                justifyContent: 'center',
                minWidth: 400
            }}
            scrollToFirstError
        >
            <Form.Item
                name="username"
                label="Логин"
                rules={[
                    {
                        required: true,
                        whitespace: true,
                    },
                ]}
            >
                <Input
                    prefix={<UserOutlined className="site-form-item-icon"/>}
                    placeholder="Username"/>
            </Form.Item>

            <Form.Item
                name="password"
                label="Пароль"
                rules={[
                    {
                        required: true,
                        message: 'Введите пароль!',
                    },
                ]}
                hasFeedback
            >
                <Input.Password
                    prefix={<LockOutlined className="site-form-item-icon"/>}
                    placeholder="Password"
                />
            </Form.Item>

            <Form.Item {...tailFormItemLayout}>
                <Button type="primary" htmlType="submit" className="login-form-button">
                    Войти
                </Button>

                <Button type="link" htmlType="button" onClick={() => {navigate("/api/auth/signup")}}>
                    Регистрация
                </Button>
            </Form.Item>
        </Form>
    );
};
export default LoginPage;

