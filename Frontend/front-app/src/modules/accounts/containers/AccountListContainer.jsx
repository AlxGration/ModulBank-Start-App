import React from 'react';
import {connect} from 'react-redux';
import {getAccountsRequest, setDisplayedAccount} from '../actions/accountsActions'
import {AccountsSwitcher} from '../components/AccountsSwitcher';
import {AccountInfo} from  '../components/AccountInfo';

const mapStateToProps = state =>({
    loading: state.accounts.loading,
    errorMessage: state.accounts.errorMessage,
    accounts: state.accounts.accountsList,
    displayedAccount: state.accounts.displayedAccount,
});

const mapDispatchProps = {
    getAccountsRequest,
    setDisplayedAccount,
}

class AccountListContainer extends React.Component{
    componentDidMount = () =>{
        this.props.getAccountsRequest();
    }

    render(){
        const {displayedAccount, errorMessage, accounts, loading, setDisplayedAccount} = this.props;

        if (loading){
            return <div>Loading...</div>
        }

        if (errorMessage){
            return <div>{errorMessage}</div>
        }

        if (!accounts.length){
            return <div>У вас нет открытых счетов</div>
        }

        return (
            <div>
                <AccountInfo displayedAccount ={displayedAccount}/>
                <AccountsSwitcher
                    accounts={accounts}
                    setDisplayedAccount={setDisplayedAccount}
                />  
            </div>
        );
    }
}

export default connect(
    mapStateToProps, mapDispatchProps
)(AccountListContainer);