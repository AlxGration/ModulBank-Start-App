import React from "react";
import AccountListContainer from '../../accounts/containers/AccountListContainer'
import {connect} from 'react-redux';
import {logOutRequest} from '../../main/actions/appActions'

class Home extends React.Component{

    logOut = (event) => {
        event.preventDefault();
        
        console.log(" log out clicked ");
        this.props.dispatch(logOutRequest());
    }


    render(){
        return (
            <div>
                <h1>Личный кабинет</h1>
                <button onClick={this.logOut}>Выйти</button>
                <AccountListContainer/>
            </div>
        );
    }
};

const mapStateToProps = state =>({
});

export default connect(mapStateToProps)(Home);