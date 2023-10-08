import React, {useState} from 'react';
import {Button, Card, Col, List, Row, Tabs} from 'antd';
import FormRegistration from "../component/FormRegistration";
import FormLogin from "../component/FormLogin";
import Item = List.Item;

const Registration: React.FC = () => {
    const [activeTabKey, setActiveTabKey] = useState<string>('tab1');

    const handleTabChange = (key: string) => {
        setActiveTabKey(key);
    };

    const handleTabClick = (tabKey: string) => {
        if (activeTabKey !== tabKey) {
            setActiveTabKey(tabKey);
        }
    };

    const tabStyle = {
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        minHeight: 250,
        minWidth: 300,
        height: '100vh'
    };

    const cardStyle = {
        minWidth: 650,
        minHeight: 400
    };

    const pIntoStyle = {
        fontSize: '20px',
        marginTop: 100,
        height: "max-content"
    };

    return (
        <>
            <Tabs
                activeKey={activeTabKey}
                onChange={handleTabChange}
                style={tabStyle}
            >
                <Item key="tab1">
                    <Card hoverable style={cardStyle}>
                        <Row gutter={20}>
                            <Col span={8}>
                                <p style={pIntoStyle}>Please, Log In or</p>
                                <Button type={'primary'} onClick={() => handleTabClick('tab2')}>
                                    Create Account
                                </Button>
                            </Col>
                            <Col span={13}>
                                <p style={{fontSize: '20px'}}>Sign In</p>
                                <p>Use Your Email And Password To Sign In</p>
                                <FormLogin/>
                            </Col>
                        </Row>
                    </Card>
                </Item>
                <Item key="tab2">
                    <Card hoverable style={cardStyle}>
                        <Row gutter={20}>
                            <Col span={8}>
                                <p style={pIntoStyle}>Create Account or</p>
                                <Button type={'primary'} onClick={() => handleTabClick('tab1')}>
                                    Sign In
                                </Button>
                            </Col>
                            <Col span={13}>
                                <p style={{fontSize: '20px'}}>Sign Up</p>
                                <p>Please, Provide All The Correct Info To Create An Account</p>
                                <FormRegistration/>
                            </Col>
                        </Row>
                    </Card>
                </Item>
            </Tabs>
        </>
    );
};

export default Registration;