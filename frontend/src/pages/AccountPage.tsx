import React, {useState} from 'react';
import {Modal, Tabs} from "antd";
import {AppstoreOutlined, MailOutlined, SettingOutlined} from "@ant-design/icons";
import FormUserAchievements from "../component/FormUserAchievements";
import FormNewDataUser from "../component/FormNewDataUser";

const AccountPage = () => {
    const [modalOpen, setModalOpen] = useState(true);
    // {/*<Button type="primary" onClick={() => setModalOpen(true)}>*/}
    // {/*    Vertically centered modal dialog*/}
    // {/*</Button>*/}
    return (

        <Modal
            title="Настройки аккаунта"
            centered
            open={modalOpen}
            onOk={() => setModalOpen(false)}
            onCancel={() => setModalOpen(false)}
            style={{ minWidth: 600, minHeight: 600 }}
            footer={null}
        >

            <Tabs style={{ marginTop: 20,}} tabPosition={'top'}>
                <Tabs.TabPane tab={<><SettingOutlined/>Профиль</>} key={'1'}>
                    <FormNewDataUser/>
                </Tabs.TabPane>

                <Tabs.TabPane tab={<><AppstoreOutlined/>Достижения</>} key={'2'}>
                    <FormUserAchievements/>
                </Tabs.TabPane>

                <Tabs.TabPane tab={<><MailOutlined/>Сброс пароля</>} key={'3'}>
                    <>Форма для сброса пароля</>
                </Tabs.TabPane>
            </Tabs>
        </Modal>
    );
};

export default AccountPage;