import React from 'react';
import {Link, Redirect} from 'react-router-dom';
import {connect} from 'react-redux';
import {postSignInRequest} from '../actions/authActions';
import '../../../App.css';


class SignInContainer extends React.Component  {

    onSubmit = (event) => {
        event.preventDefault();
        
        let email = event.target.email.value;
        let password = event.target.password.value;

        const data = {
            email, password
        };

        this.props.dispatch(postSignInRequest(data));
        //this.props.login(data);
    }
    
    render(){

        const{ 
            authorized, 
            errorMessage, 
            loading} = this.props;
    
        if (authorized){
            return(<Redirect to="/private" />);
        }

        if (loading){
            return (<div>Loading...</div>);
        }
        
        return (
            <div>
                <h1>Авторизация</h1>

                { errorMessage ?  <div className="error-message">{errorMessage}</div> :<div/>}
                
                <form onSubmit={this.onSubmit}>
                    <input type='text' 
                        id='email'
                        placeholder='Email' 
                    />
                    
                    <br/>
                    <br/>

                    <input type='password'
                        id='password'
                        placeholder='Пароль'
                    />
                    <br/>
                    <button>Войти</button>
                </form>
            
                <br/>
                <Link to='/signup'>
                    Регистрация
                </Link>
            </div>
        );
    }
};

const mapStateToProps = (state) => ({
    loading: state.signIn.loading,
    errorMessage: state.signIn.errorMessage,
});

export default connect(mapStateToProps)(SignInContainer);
