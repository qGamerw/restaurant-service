import {Button, Form, Input, message, Select,} from 'antd';
import authService from "../services/auth.service";
import {useNavigate} from "react-router-dom";
import {LockOutlined, MailOutlined, UserOutlined} from "@ant-design/icons";
import {login} from "../slices/authSlice";
import {useDispatch} from "react-redux";
import categoriesService from "../services/categoriesService";

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
const RegistrationPage = () => {
    const [form] = Form.useForm();
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const onFinish = async (values) => {
        try {
            await authService.register(values);

            await authService.login(values).then((user) => {
                    dispatch(login(user));
                    navigate("/all-task");
                    categoriesService.getCategories(dispatch);
                },
                (error) => {
                    message.error("Данные введены неверно");
                    const _content = (error.response && error.response.data) ||
                        error.message ||
                        error.toString();
                    console.error(_content)
                });
        } catch (error) {
            console.log(error);
            const handleButtonClick = () => {
                setTimeout(() => {
                    message.success('Зачем ты вводишь занятые логины ?!', 3);
                }, 100);
            };
            handleButtonClick();
        }
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
                name="email"
                label="E-mail"
                rules={[
                    {
                        type: 'email',
                        message: 'Введите email!',
                    },
                    {
                        required: true,
                        message: 'Введите email!',
                    },
                ]}
            >
                <Input prefix={<MailOutlined className="site-form-item-icon"/>}
                       placeholder="E-mail"
                />
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
                <Button type="primary" htmlType="submit">
                    Зарегистрироваться
                </Button>
            </Form.Item>
        </Form>
    );
};
export default RegistrationPage;