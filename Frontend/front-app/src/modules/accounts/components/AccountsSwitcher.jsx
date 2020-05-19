import React from 'react';

class AccountsSwitcher extends React.Component{
    handleClick = accountId => event =>{
        this.props.setDisplayedAccount({accountId});
    }

    render(){
        const{accounts} = this.props;
        
        return(
            <div>
                <h1>Доступные счета:</h1>
                <ul>
                    {
                        accounts.map(i =>(
                            <li key={i.id}>
                                <button onClick={this.handleClick(i.id)}>{i.number}</button>
                            </li>
                        ))
                    }
                </ul>
            </div>
        );
    }
}
export{AccountsSwitcher}