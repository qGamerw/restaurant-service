import React from 'react';
import {Avatar, Tabs} from 'antd';
import account from "../employee.png"
import {AntDesignOutlined, AppstoreOutlined, MailOutlined, SettingOutlined} from '@ant-design/icons';
import RecoveryPasswordPage from "./RecoveryPasswordPage";
import FormLogin from "../component/FormLogin";
import FormNewDataUser from "../component/FormNewDataUser";
import FormUserAchievements from "../component/FormUserAchievements";


const AccountPage = () => {

    return (
        <div>
            <Avatar
                size={150}
                icon={<AntDesignOutlined/>}
                style={{marginTop: 10, marginLeft: -40}}
                src={account}
            />
                <Tabs style={{marginBottom: 24, marginTop: 20,}} tabPosition={'left'}>
                    <Tabs.TabPane tab={<><AppstoreOutlined />Достижения</>} key={'1'}>
                        <FormUserAchievements/>
                    </Tabs.TabPane>

                    <Tabs.TabPane tab={<><MailOutlined />Новости</>} key={'2'}>
                        <>Новости</>
                    </Tabs.TabPane>

                    <Tabs.TabPane tab={<><SettingOutlined />Настройки</>} key={'3'} >
                        <FormNewDataUser/>
                    </Tabs.TabPane>

                </Tabs>
        </div>
    );
};

export default AccountPage;