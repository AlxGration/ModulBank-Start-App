import React from "react";
import AccountListContainer from '../../accounts/containers/AccountListContainer';
import {connect} from 'react-redux';
import '../styles.css'
import HeaderComponent from '../components/HeaderComponent';

class Home extends React.Component{

    
    render(){
        return (
            <div>
                <div className="header">
                    <HeaderComponent balance="8950" username="Alexandr" date="25.05.2020"/>
                </div>
                <div class="main">
                    <h1>Личный кабинет</h1>
                    <AccountListContainer/>
                </div>
            </div>
        );
    }
};

const mapDispatchProps = {
    
}

export default connect(null, mapDispatchProps)(Home);