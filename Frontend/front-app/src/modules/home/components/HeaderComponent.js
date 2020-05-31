import React from "react";
import {connect} from 'react-redux';
import {logOutRequest} from '../../app/actions/appActions';
import '../styles.css';


class HeaderComponent extends React.Component{

    logOut = (event) => {
        event.preventDefault();
        this.props.logOutRequest();
    }

    render(){
        return(
            <div className="header-container">

                <div class="left">
                    <ul>
                        <li>Баланс:</li>
                        <li><b>{this.props.balance}</b></li>
                    </ul>
                </div>
                
                <div class="right">
                    <ul>
                    <li>{this.props.date}</li>
                    <li>{this.props.username}</li>
                    <li>                       
                        <button className="btn btn-settings" title="Настройки"/>
                    </li>
                    <li>Иконка</li>
                    <li>
                        <button onClick={this.logOut} className="btn btn-logout"/>
                    </li>
                    </ul>
                </div>
            </div>
        );
    }
}
const mapDispatchProps = {
    logOutRequest,
}
export default connect(null, mapDispatchProps)(HeaderComponent);
